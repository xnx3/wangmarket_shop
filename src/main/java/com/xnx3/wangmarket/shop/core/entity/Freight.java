package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 商家的运费管理
 * @author 管雷鸣
 *
 */
@Entity()
@Table(name = "shop_freight", indexes={@Index(name="suoyin_index",columnList="putaway,sale,fake_sale,price,addtime,storeid,typeid,isdelete,")})
public class Freight {
	
	private Integer id;			//自动编号
	private Integer storeid;	//商家id,属于哪个商家的
	private Integer money;		//运费，单位是分。
	private Integer orderMinMoney;	//当金额超过多少钱，使用此运费规则。单位是分
	private Integer orderMaxMoney;	//当金额超过多少钱后，就不再使用此运费规则。单位是分。比如 orderMinMoney设为0，orderMaxMoney 设为 1000，money设为500，则是订单金额低于10元时，有运费5元
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '商家id,属于哪个商家的'")
	public Integer getStoreid() {
		return storeid;
	}
	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "money", columnDefinition="int(11) comment '运费，单位是分。'")
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	
	@Column(name = "order_min_money", columnDefinition="int(11) comment '当金额超过多少钱，使用此运费规则。单位是分'")
	public Integer getOrderMinMoney() {
		return orderMinMoney;
	}
	public void setOrderMinMoney(Integer orderMinMoney) {
		this.orderMinMoney = orderMinMoney;
	}
	
	@Column(name = "order_max_money", columnDefinition="int(11) comment '当金额超过多少钱后，就不再使用此运费规则。单位是分。比如 orderMinMoney设为0，orderMaxMoney 设为 1000，money设为500，则是订单金额低于10元时，有运费5元'")
	public Integer getOrderMaxMoney() {
		return orderMaxMoney;
	}
	public void setOrderMaxMoney(Integer orderMaxMoney) {
		this.orderMaxMoney = orderMaxMoney;
	}
	@Override
	public String toString() {
		return "Freight [id=" + id + ", storeid=" + storeid + ", money=" + money + ", orderMinMoney=" + orderMinMoney
				+ ", orderMaxMoney=" + orderMaxMoney + "]";
	}
	
}
