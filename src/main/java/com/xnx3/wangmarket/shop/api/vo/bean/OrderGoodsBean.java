package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderGoods;

/**
 * {@link OrderGoods} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderGoodsBean {
	private Integer orderid;		//订单的ID，对应 Order.id
	private Integer goodsid;		//商品的id，对应 Goods.id
	private String goodsTitle;		//商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像
	private Integer goodsPrice;		//商品单价，单位是分，对应 Goods.price
	private String goodsUnits;		//商品单位，对应 Goods.units
	private String goodsTitlepic;	//商品的标题图片，列表图，对应 Goods.titlepic	
	private Integer number;			//该商品的购买的数量
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public Integer getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsUnits() {
		return goodsUnits;
	}
	public void setGoodsUnits(String goodsUnits) {
		this.goodsUnits = goodsUnits;
	}
	public String getGoodsTitlepic() {
		return goodsTitlepic;
	}
	public void setGoodsTitlepic(String goodsTitlepic) {
		this.goodsTitlepic = goodsTitlepic;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
}
