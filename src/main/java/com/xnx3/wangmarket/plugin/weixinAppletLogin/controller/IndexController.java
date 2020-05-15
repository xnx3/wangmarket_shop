package com.xnx3.wangmarket.plugin.weixinAppletLogin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.Lang;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UserVO;
import com.xnx3.wangmarket.plugin.weixinAppletLogin.vo.LoginVO;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinAppletUtil;
import com.xnx3.weixin.vo.Jscode2sessionResultVO;

/**
 * 推广用户，用户下单消费后，推广人获赠某个商品。用户端使用的
 * @author 管雷鸣
 */
@Controller(value="WeixinAppletLoginIndexPluginController")
@RequestMapping("/plugin/weixinAppletLogin/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	@Resource
	private WeiXinService weiXinService;
	

	/**
	 * 登陆   wx.login
	 * 1. 微信获取随机code，传入此处； 
	 * 2. 服务器通过code拉取用户 unionid；  
	 * 3. 利用 unionid 来判断用户是否存在及设置用户自动登陆状态
	 * @param code 微信小程序获取的随机code
	 * @param storeid 要登录哪个商铺，也就是要使用哪个商铺的微信设置的参数
	 * @param referrerid 推荐人id，这个在创建用户信息时，会计入用户的 user.referrerid
	 * @return {@link LoginVO} 登陆结果。根据getResult() 进行判断
	 * 		<ul>
	 * 			<li> {@link LoginVO}.getResult() == {@link LoginVO}.SUCCESS : 自动登陆成功。 同时，有一种情况，用户第一次登陆，也就是刚注册时，getInfo() 会返回字符串 "reg" ,小程序端需要根据此判断，若用户是第一次注册，那么需要将用户微信的 userInfo 信息传给服务器,以充实 User 数据表  </li>
	 * 			<li>{@link LoginVO}.getResult() == {@link LoginVO}.FAILURE : 自动登陆失败 ，可用 getInfo() 获取失败原因</li>
	 * 		</ul>
	 */
	@RequestMapping(value="/loginByOpenid${api.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public LoginVO loginByCode(HttpServletRequest request, Model model,
			@RequestParam(value = "code", required = false, defaultValue="") String code,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "referrerid", required = false, defaultValue="0") int referrerid){
		LoginVO vo = new LoginVO();
		if(storeid < 1){
			vo.setBaseVO(LoginVO.FAILURE, "请传入您的商铺编号storeid");
			return vo;
		}
		vo.setToken(request.getSession().getId());
		
		//判断当前用户是否已经登录过了
		if(haveUser()){
			//已登录过了
//			vo.setBaseVO(LoginVO.FAILURE, "请传入您的商铺编号storeid");
			vo.setUser(getUser());
			vo.setInfo("您已经是登录状态了，所以不再返回 userWeixin 字段了。只有未登录状态下，登录成功，才会返回 userWeixin");
//			vo.setUserWeiXin(sqlCacheService.findAloneByProperty(UserWeiXin.class, , value));
			return vo;
		}
		if(code.length() == 0){
			vo.setBaseVO(LoginVO.FAILURE, "请传入您的微信小程序获取到的code");
			return vo;
		}
		
		WeiXinAppletUtil weixinAppletUtil = weiXinService.getWeiXinAppletUtil(storeid);
		if(weixinAppletUtil == null){
			vo.setBaseVO(LoginVO.FAILURE, "请先为您的商铺设置微信小程序的appid、appsecret。在本系统的商家后台-支付设置中，进行设置");
			return vo;
		}
		Jscode2sessionResultVO jvo = weixinAppletUtil.jscode2session(code);
		if(jvo.getResult() - Jscode2sessionResultVO.SUCCESS == 0){
			//登陆成功
			
			UserWeiXin userWeixin = sqlCacheService.findById(UserWeiXin.class, jvo.getOpenid());
			User user = null;
			if(userWeixin == null){
				//此用户还未注册，进行注册用户
				user = new User();
				user.setUsername(Lang.uuid());
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
				if(regVO.getResult() - BaseVO.FAILURE == 0){
					//自动注册失败
					vo.setBaseVO(regVO);
					return vo;
				}
				//自动注册成功，保存 WeixinUser
				userWeixin = new UserWeiXin();
				userWeixin.setUserid(user.getId());
				userWeixin.setOpenid(jvo.getOpenid());
				userWeixin.setUnionid(jvo.getUnionid());
				userWeixin.setStoreid(storeid);
				ConsoleUtil.log(userWeixin.toString());
				sqlService.save(userWeixin);
				//缓存
//				SessionUtil.setWeiXinUser(weixinUser);
				
				ConsoleUtil.info("reg --- > "+user.toString());
				vo.setUser(getUser());
				vo.setInfo("reg");
				vo.setUserWeiXin(userWeixin);
			}else{
				//用户已经注册过了
				
				//设置当前用户为登陆的状态
				BaseVO loginVO = userService.loginForUserId(request, userWeixin.getUserid());
				if(loginVO.getResult() - BaseVO.FAILURE == 0){
					vo.setBaseVO(UserVO.FAILURE, loginVO.getInfo());
					return vo;
				}
				
				user = getUser();
				//缓存微信相关信息
//				SessionUtil.setWeiXinUser(weiXinUser);
				vo.setUser(user);
				vo.setUserWeiXin(userWeixin);
			}
			
			//判断一下用户是否已经关联上这个商家了，如果没关联，还要将这个用户关联为这个商家的用户
			StoreUser storeUser = sqlCacheService.findBySql(StoreUser.class, "userid="+user.getId()+" AND storeid="+storeid);
			if(storeUser == null){
				storeUser = new StoreUser();
				storeUser.setStoreid(storeid);
				storeUser.setUserid(user.getId());
				sqlService.save(storeUser);
			}
		}else{
			// jscode2session 获取失败
			vo.setBaseVO(BaseVO.FAILURE, jvo.getInfo());
		}
		
		return vo;
	}
	
}