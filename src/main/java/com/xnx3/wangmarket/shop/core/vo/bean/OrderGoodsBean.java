package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderGoods;

/**
 * {@link OrderGoods} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderGoodsBean {
	private Integer orderid;		//订单的ID，对应 Order.id
	private Integer goodsid;		//商品的id，对应 Goods.id
	private String title;		//商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像
	private Integer price;		//商品单价，单位是分，对应 Goods.price
	private String units;		//商品单位，对应 Goods.units
	private String titlepic;	//商品的标题图片，列表图，对应 Goods.titlepic	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title == null){
			this.title = "";
		}else{
			this.title = title;
		}
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		if(price == null){
			this.price = 0;
		}else{
			this.price = price;
		}
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		if(this.units == null){
			this.units = "";
		}else{
			this.units = units;
		}
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		if(titlepic == null){
			this.titlepic = "";
		}else{
			this.titlepic = titlepic;
		}
	}
	
}
