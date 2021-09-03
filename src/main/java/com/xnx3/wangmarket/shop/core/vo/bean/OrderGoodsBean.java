package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderGoods;

/**
 * {@link OrderGoods} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderGoodsBean {
	private Integer id;			//OrderGoods.id
	private Integer orderid;		//订单的ID，对应 Order.id
	private Integer goodsid;		//商品的id，对应 Goods.id
	private String title;		//商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像
	private Integer price;		//商品单价，单位是分，如果商品有规格，那么这里存的是具体规格的价格，如果没有规格，这里存的是对应 Goods.price
	private String units;		//商品单位，对应 Goods.units
	private String titlepic;	//商品的标题图片，列表图，对应 Goods.titlepic	
	private Integer number;			//该商品的购买的数量
	
	//购买的商品是什么规格，这里是规格的名字。
	//比如goods.specification 为 [{"黄色":901},{"黑色":800},{"白色":705}] ，那么这里存的便是 黄色 。
	//如果商品没有规格，那这里则是空字符串。 v1.6增加
	private String specificationName;	
	
	public OrderGoodsBean() {
	}
	
	/**
	 * 通过实体类初始化给bean赋值
	 * @param orderGoods 实体类
	 */
	public OrderGoodsBean(OrderGoods orderGoods) {
		if(orderGoods != null) {
			this.setGoodsid(orderGoods.getId());
			this.setPrice(orderGoods.getPrice());
			this.setTitle(orderGoods.getTitle());
			this.setTitlepic(orderGoods.getTitlepic());
			this.setUnits(orderGoods.getUnits());
			this.setNumber(orderGoods.getNumber());
			this.setOrderid(orderGoods.getOrderid());
			this.setId(orderGoods.getId());
			this.setSpecificationName(orderGoods.getSpecificationName());
		}
	}
	
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
		if(units == null){
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		if(id == null){
			this.id = 0;
		}else{
			this.id = id;
		}
	}
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		if(specificationName == null) {
			this.specificationName = "";
		}else {
			this.specificationName = specificationName;
		}
	}
	
}
