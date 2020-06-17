package com.xnx3.wangmarket.shop.core.bean;

import com.xnx3.wangmarket.shop.core.entity.Goods;

/**
 * 创建订单，购买的商品
 * @author 管雷鸣
 */
public class BuyGoods {
	private int goodsid;	//购买的是那个商品，对应Goods.id
	private int num;		//购买商品的数量
	private Goods goods;	//购买的商品信息，这个是直接从数据表取的
	
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@Override
	public String toString() {
		return "BuyGoods [goodsid=" + goodsid + ", num=" + num + ", goods=" + goods + "]";
	}
	
}
