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
		if(orderid == null) {
			this.orderid = 0;
		}else {
			this.orderid = orderid;
		}
	}
	public Integer getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(Integer goodsid) {
		if(goodsid == null) {
			this.goodsid = 0;
		}else {
			this.goodsid = goodsid;
		}
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		if(goodsTitle == null) {
			this.goodsTitle = "";
		}else {
			this.goodsTitle = goodsTitle;
		}
	}
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		if(goodsPrice == null) {
			this.goodsPrice = 0;
		}else {
			this.goodsPrice = goodsPrice;
		}
	}
	public String getGoodsUnits() {
		return goodsUnits;
	}
	public void setGoodsUnits(String goodsUnits) {
		if(goodsUnits == null) {
			this.goodsUnits = "";
		}else {
			this.goodsUnits = goodsUnits;
		}
	}
	public String getGoodsTitlepic() {
		return goodsTitlepic;
	}
	public void setGoodsTitlepic(String goodsTitlepic) {
		if(goodsTitlepic == null) {
			this.goodsTitlepic = "";
		}else {
			this.goodsTitlepic = goodsTitlepic;
		}
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		if(number == null) {
			this.number = 0;
		}else {
			this.number = number;
		}
	}
}
