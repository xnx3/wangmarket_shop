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
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.net.HttpResponse;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage.RegPluginManage;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinUtil;

import net.sf.json.JSONObject;

/**
 * 跳转页面，获取用户openid
 * 其中有url作为参数的，url中 ? & 都会替换为 _3F 、_26 等
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
	 * @param referrerid 推荐人id，这里传入的是推荐人的 user.id 这个在创建用户信息时，会跟storeid一块计入用户的 StoreUser.referrerid。 可不填
	 * @param url 登录成功后要跳转到的url页面，格式如： http://demo.imall.net.cn/index.html_3Fa=1_26b=2    其中_3F是? ， _26是&， 
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
		
		url = url.replaceAll("\\?", "_3F").replaceAll("&", "_26");
		url = SystemUtil.get("MASTER_SITE_URL")+"plugin/weixinH5Auth/wxAuthLogin.do?storeid="+storeid+"%26referrerid="+referrerid+"%26url="+url;
		String jumpUrl = util.getOauth2ExpertUrl(url);
		ActionLogUtil.insert(request, storeid, "jumpUrl:"+jumpUrl);
		return redirect(jumpUrl);
	}
	
	/**
	 * 微信登录 。 这里是用户访问 hiddenAuthJump()， 然后跳转到微信授权，授权成功后自动跳转到的这里
	 * @param code 微信网页授权的code
	 * @param storeid 要登录的是哪个店铺，必须的
	 * @param referrerid 推荐人id，这个在创建用户信息时，会计入用户的 user.referrerid。 可不填
	 * @param url 授权拿到openid登录成功后，要跳转到的url页面。格式如： http://demo.imall.net.cn/index.html_3Fa=1_26b=2  注意，像是 ? & 要传入特定转码字符
	 * 				<ul>
	 * 					<li>站内跳转，如：user/info.do	内网页面，前面无须/，默认自动补齐之前路径。此便是跳转到当前项目根目录下/user/info.do页面，
	 * 					<li>站外跳转，如：http://www.xnx3.com	外网页面，写全即可，也或者 //www.xnx3.com 也是直接跳转到外网
	 * 				</ul>
	 * @return 重定向
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
		url = url.replaceAll("_3F","?").replaceAll("_26","&");	//URL转码
		ConsoleUtil.debug("code:"+code+", storeid:"+storeid+", referrerid:"+referrerid+", url:"+url);
		
		/**** 获取用户的openid ***/
		String openid = null;
		//判断一下该用户使用的是否是服务商模式
		PaySet paySet = paySetService.getPaySet(storeid);
		if(paySet.getUseWeixinServiceProviderPay() - 1 == 0){
			//使用服务商模式
			PaySet serivcePaySet = paySetService.getSerivceProviderPaySet();
			
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
			//此openid还未注册，或者此openid已注册但是是跟之前的商铺关联，并没有跟此店铺关联，那么进行注册用户
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
				//自动注册成功，判断一下 userWeixin 是否存在，如果不存在，那么 保存 WeixinUser
				if(userWeixin == null){
					userWeixin = new UserWeiXin();
					userWeixin.setUserid(user.getId());
					userWeixin.setOpenid(openid);
					userWeixin.setUnionid("");
					if(paySet.getUseWeixinServiceProviderPay() - 1 == 0){
						//使用服务商模式，那么storeid = 0
						userWeixin.setStoreid(0);
					}else{
						//不使用服务商模式，那么就是使用的商家自己的微信号了
						userWeixin.setStoreid(storeid);
					}
					ConsoleUtil.log("新建userWeixin:"+userWeixin.toString());
					sqlService.save(userWeixin);
				}
				
				Store store = sqlCacheService.findById(Store.class, storeid);
				/*** 注册成功后触发 ***/
				try {
					RegPluginManage.regFinish(user, store);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*********/
			}else{
				//自动注册失败
				return error(model,regVO.getInfo());
			}
		}else{
			//用户已经注册过了
			user = sqlCacheService.findById(User.class, userWeixin.getUserid());
			
			//设置当前用户为登陆的状态
			BaseVO loginVO = userService.loginForUserId(request, userWeixin.getUserid());
			if(loginVO.getResult() - BaseVO.FAILURE == 0){
				return error(model,loginVO.getInfo());
			}
			ActionLogUtil.insertUpdateDatabase(request, storeid, "登录:"+getUser().toString()+", url:"+url);
		}
		
		//判断一下用户是否已经关联上这个商家了，如果没关联，还要将这个用户关联为这个商家的用户
		StoreUser storeUser = sqlCacheService.findBySql(StoreUser.class, "userid="+user.getId()+" AND storeid="+storeid);
		if(storeUser == null){
			storeUser = new StoreUser();
			storeUser.setId(user.getId()+"_"+storeid);
			storeUser.setStoreid(storeid);
			storeUser.setUserid(user.getId());
			//判断其是否有推荐人
			if(referrerid > 0){
				//从StoreUser表中，看是否有这个 id ,也就是这个推荐人是否真的存在
				StoreUser referrerStoreUser = sqlCacheService.findById(StoreUser.class, referrerid+"_"+storeid);
				if(referrerStoreUser != null){
					storeUser.setReferrerid(referrerStoreUser.getId());
				}
			}
			sqlService.save(storeUser);
			ActionLogUtil.insertUpdateDatabase(request, storeid, "注册新用户:"+user.toString()+", storeUser:"+storeUser.toString()+", url:"+url);
		}
		
		String sessionid = request.getSession().getId();
		if(url.indexOf("?") > -1){
			url = url + "&";
		}else{
			url = url + "?";
		}
		url = url + "token="+sessionid;
		ConsoleUtil.debug("redirect url: "+url);
		return redirect(url);
	}
	
	
}