package com.xnx3.wangmarket.plugin.weixinAppletLogin.util;

import com.xnx3.wangmarket.plugin.weixinAppletLogin.entity.WeiXinUser;

/**
 * 微信小程序、公众号等相关的sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//微信用户登录后，用户关注公众号之后的openid、unionid等跟微信相关的信息
	public static final String PLUGIN_NAME_WEIXIN_USER = "wangmarket:user:weixinappletlogin:{userid}";
	
	/**
	 * 获取当前登录用户相关的微信方面信息。若是不存在，则返回null
	 */
	public static WeiXinUser getWeiXinUser(){
		return getPlugin(PLUGIN_NAME_WEIXIN_USER);
	}
	
	/**
	 * 设置当前登录用户相关的微信方面信息
	 * @param weiXinUser {@link WeiXinUser} 当前登录用户相关的微信方面信息
	 */
	public static void setWeiXinUser(WeiXinUser weiXinUser){
		if(weiXinUser == null){
			return;
		}
		setPlugin(PLUGIN_NAME_WEIXIN_USER.replace("{userid}", weiXinUser.getUserid()+""), weiXinUser);
	}
	
}

