package com.xnx3.wangmarket.shop.core.vo;

import java.util.Map;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsCart;

/**
 * 商城购物车，多店铺情况下，某一个店铺的购物车信息
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {"fakeSale","isdelete","alarmNum","updatetime","rank","version"}, nullSetDefaultValue = true)
public class StoreCartVO extends BaseVO {
	private Map<Integer, GoodsCart> goodsCartMap;	//该店铺购物车的商品列表。 key:goods.id 
	private int number;			//该店铺购物车中商品的总数量
	private int money;			//该店铺购物车中商品的总价格，单位是分
	
	public Map<Integer, GoodsCart> getGoodsCartMap() {
		return goodsCartMap;
	}

	public void setGoodsCartMap(Map<Integer, GoodsCart> goodsCartMap) {
		this.goodsCartMap = goodsCartMap;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "StoreCartVO [goodsCartMap=" + goodsCartMap + ", number=" + number + ", money=" + money + "]";
	}
	
}

