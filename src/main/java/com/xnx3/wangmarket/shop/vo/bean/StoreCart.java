package com.xnx3.wangmarket.shop.vo.bean;

import java.util.HashMap;
import java.util.Map;
import com.xnx3.wangmarket.shop.entity.Store;

/**
 * 商城购物车，某个店铺的
 * @author 管雷鸣
 */
public class StoreCart{
	private Map<Integer, GoodsCart> goodsCartMap;	//该店铺购物车的商品列表。 key:goods.id 
	private int number;			//该店铺购物车中商品的总数量
	private double money;		//该店铺购物车中商品的总价格	
	private Store store;		//当前店铺信息。适用于多商铺一块结算使用。
	
	public StoreCart() {
		this.number = 0;
		this.money = 0d;
	}
	
	public Map<Integer, GoodsCart> getGoodsCartMap() {
		if(goodsCartMap == null){
			this.goodsCartMap = new HashMap<Integer, GoodsCart>();
		}
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	
}
