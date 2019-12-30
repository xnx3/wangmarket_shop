package com.xnx3.wangmarket.shop.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

/**
 * 商城购物车，多店铺
 * @author 管雷鸣
 */
public class StoreCartVO extends BaseVO {
	private StoreCart storeCart;	//某个店铺的购物车

	public StoreCart getStoreCart() {
		return storeCart;
	}

	public void setStoreCart(StoreCart storeCart) {
		this.storeCart = storeCart;
	}

	@Override
	public String toString() {
		return "StoreCartVO [storeCart=" + storeCart + "]";
	}
	
}

