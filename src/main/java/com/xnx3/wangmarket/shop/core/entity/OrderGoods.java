package com.xnx3.wangmarket.shop.core.entity;

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
public class OrderGoods implements java.io.Serializable{
	private Integer id;				//自动编号
	private Integer orderid;		//订单的ID，对应 Order.id
	private Integer goodsid;		//商品的id，对应 Goods.id
	private Integer userid;			//购买者的用户ID，对应User.id
	private String title;		//商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像
	private Integer price;		//商品单价，单位是分，如果商品有规格，那么这里存的是具体规格的价格，如果没有规格，这里存的是对应 Goods.price
	private String units;		//商品单位，对应 Goods.units
	private String titlepic;	//商品的标题图片，列表图，对应 Goods.titlepic	
	private Integer number;			//该商品的购买的数量
	
	//购买的商品是什么规格，这里是规格的名字。
	//比如goods.specification 为 [{"黄色":901},{"黑色":800},{"白色":705}] ，那么这里存的便是 黄色 。
	//如果商品没有规格，那这里则是空字符串。 v1.6增加, 限制30个字符以内
	private String specificationName;	
	
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
	
	@Column(name = "title", columnDefinition="char(40) comment '商品名字，对应 Goods.title ，就是吧Goods的信息复制过来了，相当于给商品做了一个镜像'")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "price", columnDefinition="int(11) comment '商品单价，单位是分，如果商品有规格，那么这里存的是具体规格的价格，如果没有规格，这里存的是对应 Goods.price'")
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Column(name = "units", columnDefinition="char(5) comment '商品单位，对应 Goods.units'")
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	@Column(name = "titlepic", columnDefinition="char(150) comment '商品的标题图片，列表图，对应 Goods.titlepic'")
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
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
	
	@Column(name = "specification_name", columnDefinition="char(30) comment '购买的商品是什么规格，这里是规格的名字。如果商品没有规格，那这里则是空字符串。 '")
	public String getSpecificationName() {
		if(specificationName == null) {
			return "";
		}
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	@Override
	public String toString() {
		return "OrderGoods [id=" + id + ", orderid=" + orderid + ", goodsid=" + goodsid + ", userid=" + userid
				+ ", title=" + title + ", price=" + price + ", units=" + units + ", titlepic=" + titlepic + ", number="
				+ number + "]";
	}
}
