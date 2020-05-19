package com.xnx3.wangmarket.plugin.weixinApplet.vo;

import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;

/**
 * 微信小程序登录，返回的响应
 * @author 管雷鸣
 *
 */
public class LoginVO extends com.xnx3.j2ee.vo.LoginVO{
	private UserWeiXin userWeiXin;	//对应 user 的weixin信息
	private String sessionKey;		//小程序使用code登录之后，会返回一个 sessionKey

	public UserWeiXin getUserWeiXin() {
		return userWeiXin;
	}

	public void setUserWeiXin(UserWeiXin userWeiXin) {
		this.userWeiXin = userWeiXin;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	@Override
	public String toString() {
		return "LoginVO [userWeiXin=" + userWeiXin + ", sessionKey=" + sessionKey + "]";
	}

	
}