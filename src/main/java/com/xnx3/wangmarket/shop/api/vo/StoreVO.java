package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 店铺信息
 * @author 管雷鸣
 *
 */
public class StoreVO extends BaseVO{
	private Store store;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
}
