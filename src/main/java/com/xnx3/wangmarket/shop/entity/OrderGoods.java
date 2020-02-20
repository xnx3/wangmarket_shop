package com.xnx3.wangmarket.shop.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 订单内的商品
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_order_goods", indexes={@Index(name="suoyin_index",columnList="orderid,goodsid,userid")})
public class OrderGoods {
	private Integer id;				//自动编号
	private Integer orderid;		//订单的ID，对应 Order.id
	private Integer goodsid;		//商品的id，对应 Goods.id
	private Integer userid;			//购买者的用户ID，对应User.id
	private String goodsTitle;		//商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像
	private Integer goodsPrice;		//商品单价，单位是分，对应 Goods.price
	private String goodsUnits;		//商品单位，对应 Goods.units
	private String goodsTitlepic;	//商品的标题图片，列表图，对应 Goods.titlepic	
	private Integer number;			//该商品的购买的数量
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "orderid", columnDefinition="int(11) comment '订单的ID，对应 Order.id'")
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "goodsid", columnDefinition="int(11) comment '商品的id，对应 Goods.id'")
	public Integer getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '购买者的用户ID，对应User.id'")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "goods_title", columnDefinition="char(40) comment '商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像'")
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	
	@Column(name = "goods_price", columnDefinition="int(11) comment '商品单价，单位是分，对应 Goods.price'")
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	@Column(name = "goods_units", columnDefinition="char(5) comment '商品单位，对应 Goods.units'")
	public String getGoodsUnits() {
		return goodsUnits;
	}
	public void setGoodsUnits(String goodsUnits) {
		this.goodsUnits = goodsUnits;
	}
	
	@Column(name = "goods_titlepic", columnDefinition="char(100) comment '商品的标题图片，列表图，对应 Goods.titlepic'")
	public String getGoodsTitlepic() {
		return goodsTitlepic;
	}
	public void setGoodsTitlepic(String goodsTitlepic) {
		this.goodsTitlepic = goodsTitlepic;
	}
	
	@Column(name = "number", columnDefinition="int(11) comment '该商品的购买的数量'")
	public Integer getNumber() {
		if(number == null) {
			return 0;
		}
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "OrderGoods [id=" + id + ", orderid=" + orderid + ", goodsid=" + goodsid + ", userid=" + userid
				+ ", goodsTitle=" + goodsTitle + ", goodsPrice=" + goodsPrice + ", goodsUnits=" + goodsUnits
				+ ", goodsTitlepic=" + goodsTitlepic + ", number=" + number + "]";
	}
	
}
