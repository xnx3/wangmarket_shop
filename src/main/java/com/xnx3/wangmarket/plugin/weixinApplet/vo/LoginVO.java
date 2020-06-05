package com.xnx3.wangmarket.plugin.weixinApplet.vo;

import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;
import com.xnx3.wangmarket.shop.core.vo.UserVO;

/**
 * 微信小程序登录，返回的响应
 * @author 管雷鸣
 *
 */
public class LoginVO extends UserVO{
	private UserWeiXin userWeiXin;	//对应 user 的weixin信息
	private String sessionKey;		//小程序使用code登录之后，会返回一个 sessionKey
	private String token;			//云商城用户登录的sessionid

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginVO [userWeiXin=" + userWeiXin + ", sessionKey=" + sessionKey + ", token=" + token + ", getUser()="
				+ getUser() + ", getResult()=" + getResult() + ", getInfo()=" + getInfo() + "]";
	}
	
}