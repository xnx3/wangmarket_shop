package com.xnx3.wangmarket.plugin.createStoreApi.vo;

import com.xnx3.j2ee.vo.LoginVO;

/**
 * 注册开通商铺
 * @author 管雷鸣
 *
 */
public class RegVO extends LoginVO{
	private int storeid;	//注册开通的商铺id

	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	@Override
	public String toString() {
		return "RegVO [storeid=" + storeid + "]";
	}
}
