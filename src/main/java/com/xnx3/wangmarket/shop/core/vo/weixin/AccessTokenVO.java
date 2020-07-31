package com.xnx3.wangmarket.shop.core.vo.weixin;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.weixin.bean.AccessToken;

/**
 * 微信AccessToken
 * @author 管雷鸣
 *
 */
public class AccessTokenVO extends BaseVO{
	private AccessToken accessToken;

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "AccessTokenVO [accessToken=" + accessToken + "]";
	}
	
}
