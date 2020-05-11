package com.xnx3.wangmarket.plugin.weixinAppletLogin.vo;

import com.xnx3.wangmarket.shop.core.entity.UserWeiXin;

/**
 * 微信小程序登录，返回的响应
 * @author 管雷鸣
 *
 */
public class LoginVO extends com.xnx3.j2ee.vo.LoginVO{
	private UserWeiXin userWeiXin;	//对应 user 的weixin信息

	public UserWeiXin getUserWeiXin() {
		return userWeiXin;
	}

	public void setUserWeiXin(UserWeiXin userWeiXin) {
		this.userWeiXin = userWeiXin;
	}

	@Override
	public String toString() {
		return "LoginVO [userWeiXin=" + userWeiXin + ", toString()=" + super.toString() + "]";
	}
	
	
}