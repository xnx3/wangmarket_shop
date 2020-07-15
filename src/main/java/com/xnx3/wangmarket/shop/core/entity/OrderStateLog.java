package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单状态变化的日志记录，记录订单状态的变化轨迹
 * @author 管雷鸣
 *
 */
@Entity()
@Table(name = "shop_order_state_log")
public class OrderStateLog implements java.io.Serializable{
	
	private Integer id;				//自动编号
	private Integer orderid;		//对应订单id
	private String state;			//变化之后的订单状态，新的订单状态
	private Integer addtime;		//此条记录的添加时间
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "state", columnDefinition="char(20) comment '变化之后的订单状态，新的订单状态' default ''")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '此条记录的添加时间' default '0'")
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	@Column(name = "orderid", columnDefinition="int(11) comment '此条记录对应的订单id' default '0'")
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	@Override
	public String toString() {
		return "OrderStateLog [id=" + id + ", orderid=" + orderid + ", state=" + state + ", addtime=" + addtime + "]";
	}
	
}
