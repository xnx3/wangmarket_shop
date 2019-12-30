package com.xnx3.wangmarket.shop.vo.bean;

import com.xnx3.wangmarket.shop.entity.Goods;

/**
 * 购物车中的单个商品,某个商品的购物车信息
 * @author 管雷鸣
 */
public class GoodsCart {
	private Goods goods;	//当前商品的信息
	private int number;		//此种商品数量，加入购物车中的数量
	private Double money;	//此种商品加入购物车中的总金额，也就是 goods.price * number 的值
	
	public GoodsCart() {
		this.number = 0;
		this.money = 0d;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	

	
}
