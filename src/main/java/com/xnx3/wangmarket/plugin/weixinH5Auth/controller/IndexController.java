package com.xnx3.wangmarket.plugin.weixinH5Auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.net.HttpResponse;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinUtil;

import net.sf.json.JSONObject;

/**
 * 跳转页面，获取用户openid
 * @author 管雷鸣
 */
@Controller(value="WeixinH5AuthIndexPluginController")
@RequestMapping("/plugin/weixinH5Auth/")
public class IndexController extends BasePluginController {
	@Resource
	private WeiXinService weiXinService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	@Resource
	private PaySetService paySetService;

	/**
	 * 微信h5授权获取用户openid，也仅仅只是获取用户的openid，获取不到其他信息。
	 * 进入后会直接进行重定向到传入的url页面，在这个url页面即可或得到用户的openid 。注意，传入的url，域名一定是提前在微信公众号接口权限表-网页服务-网页帐号-网页授权获取用户基本信息 中配置的网址
	 * @param storeid 进入的是哪个店铺
	 * @param referrerid 推荐人id，这个在创建用户信息时，会计入用户的 user.referrerid。 可不填
	 * @param url 登录成功后要跳转到的url页面，格式如： http://demo.imall.net.cn/index.html?a=1&b=2 
	 */
	@RequestMapping("hiddenAuthJump${url.suffix}")
	public String hiddenAuthJump(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "referrerid", required = false, defaultValue="0") int referrerid,
			@RequestParam(value = "url", required = false, defaultValue="") String url){
		if(storeid < 1){
			return error(model, "请传入店铺编号");
		}
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx07f3db3a6bbedfbe&redirect_uri=http://shop.imall.net.cn/plugin/weixinH5Auth/wxAuthLogin.do?storeid=1%26url=http://demo.imall.net.cn&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
		WeiXinUtil util = weiXinService.getWeiXinUtil(storeid);
		if(util == null){
			return error(model, "当前店铺未设置微信公众号");
		}
		
		url = url.replace("?", "%3F").replaceAll("&", "%26");
		url = SystemUtil.get("MASTER_SITE_URL")+"plugin/weixinH5Auth/wxAuthLogin.do?storeid=1%26url="+url;
		String jumpUrl = util.getOauth2ExpertUrl(url);
		ConsoleUtil.log(jumpUrl);
		return redirect(jumpUrl);
	}
	
	/**
	 * 微信登录 
	 * @param code 微信网页授权的code
	 * @param storeid 要登录的是哪个店铺，必须的
	 * @param referrerid 推荐人id，这个在创建用户信息时，会计入用户的 user.referrerid。 可不填
	 * @param url 授权拿到openid登录成功后，要跳转到的url页面。格式如： http://demo.imall.net.cn/index.html%3Fa=1%26b=2  注意，像是 ? & 要传入URL转码字符
	 * 				<ul>
	 * 					<li>站内跳转，如：user/info.do	内网页面，前面无须/，默认自动补齐之前路径。此便是跳转到当前项目根目录下/user/info.do页面，
	 * 					<li>站外跳转，如：http://www.xnx3.com	外网页面，写全即可，也或者 //www.xnx3.com 也是直接跳转到外网
	 * 				</ul>
	 */
	@RequestMapping("wxAuthLogin${url.suffix}")
	public String wxAuthLogin(HttpServletRequest request,Model model,
			@RequestParam(value = "code", required = false, defaultValue="") String code,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "referrerid", required = false, defaultValue="0") int referrerid,
			@RequestParam(value = "url", required = false, defaultValue="") String url){
		ConsoleUtil.log(request.getQueryString());
		if(code.length() == 0){
			return error(model,"code未发现");
		}
		if(storeid < 1){
			return error(model,"请传入店铺编号");
		}
		
		/**** 获取用户的openid ***/
		String openid = null;
		//判断一下该用户使用的是否是服务商模式
		PaySet paySet = paySetService.getPaySet(storeid);
		if(paySet.getUseWeixinServiceProviderPay() - 1 == 0){
			//使用服务商模式
			PaySet serivcePaySet = paySetService.getSerivceProviderPaySet();
			System.out.println("serivcePaySet:"+serivcePaySet.toString());
			
			com.xnx3.net.HttpUtil httpUtil = new com.xnx3.net.HttpUtil();
			HttpResponse httpResponse = httpUtil.get(WeiXinUtil.OAUTH2_ACCESS_TOKEN_URL.replace("APPID", serivcePaySet.getWeixinOfficialAccountsAppid()).replace("SECRET", serivcePaySet.getWeixinOfficialAccountsAppSecret()).replace("CODE", code));
			JSONObject json = JSONObject.fromObject(httpResponse.getContent());
			if(json.get("errcode") == null){
				//没有出错，获取网页access_token成功
				openid = json.getString("openid");
			}else{
				ConsoleUtil.debug("获取网页授权openid失败！返回值："+httpResponse.getContent());
			}
		}else{
			//使用自己的公众号
			WeiXinUtil util = weiXinService.getWeiXinUtil(storeid);
			if(util == null){
				return error(model,"当前店铺未设置微信公众号");
			}
			openid = util.getOauth2OpenId(code);
		}
		if(openid == null || openid.length() == 0){
			return error(model,"获取用户openid失败");
		}
		
		//判断此人是否已注册过
		UserWeiXin userWeixin = sqlCacheService.findById(UserWeiXin.class, openid);
		User user = null;
		if(userWeixin == null){
			//此用户还未注册，进行注册用户
			String username = StringUtil.intTo36(DateUtil.timeForUnix10())+StringUtil.getRandom09AZ(6);
			user = new User();
			user.setUsername(username);
			user.setPassword(Lang.uuid());
			user.setNickname("nick name");
			
			//判断其是否有推荐人
			if(referrerid > 0){
				//从user表中，看是否有这个userid,也就是这个推荐人是否真的存在
				User referrerUser = sqlCacheService.findById(User.class, referrerid);
				if(referrerUser != null){
					user.setReferrerid(referrerid);
				}
			}
			
			BaseVO regVO = userService.reg(user, request);
			if(regVO.getResult() - BaseVO.SUCCESS == 0){
				//自动注册成功，保存 WeixinUser
				userWeixin = new UserWeiXin();
				userWeixin.setUserid(user.getId());
				userWeixin.setOpenid(openid);
				userWeixin.setUnionid("");
				userWeixin.setStoreid(storeid);
				ConsoleUtil.log(userWeixin.toString());
				sqlService.save(userWeixin);
				
				//保存 store user 的关系
				StoreUser storeUser = new StoreUser();
				storeUser.setStoreid(storeid);
				storeUser.setUserid(user.getId());
				sqlService.save(storeUser);
				
				ConsoleUtil.info("reg --- > "+user.toString());
			}else{
				//自动注册失败
				return error(model,regVO.getInfo());
			}
		}else{
			//用户已经注册过了
			//设置当前用户为登陆的状态
			BaseVO loginVO = userService.loginForUserId(request, userWeixin.getUserid());
			if(loginVO.getResult() - BaseVO.FAILURE == 0){
				return error(model,loginVO.getInfo());
			}
		}
		
		String sessionid = request.getSession().getId();
		if(url.indexOf("?") > -1){
			url = url + "&";
		}else{
			url = url + "?";
		}
		url = url + "token="+sessionid;
		return redirect(url);
	}
	
	
}