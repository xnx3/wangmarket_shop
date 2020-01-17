package com.xnx3.wangmarket.weixin.vo;

import com.xnx3.wangmarket.weixin.entity.WeiXinUser;

/**
 * 微信小程序登录，返回的响应
 * @author 管雷鸣
 *
 */
public class LoginVO extends com.xnx3.j2ee.vo.LoginVO{
	private WeiXinUser weixinUser;	//对应 user 的weixin信息

	public WeiXinUser getWeixinUser() {
		return weixinUser;
	}

	public void setWeixinUser(WeiXinUser weixinUser) {
		this.weixinUser = weixinUser;
	}
	
}
