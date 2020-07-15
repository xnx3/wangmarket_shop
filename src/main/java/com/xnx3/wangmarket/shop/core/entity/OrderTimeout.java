package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 订单是否超时记录表，比如订单下单后，未支付，过多久会自动将订单状态变为已取消，便是当下单时，自动加一条记录，将超时的时间加进来。会有一个线程，或者serverless定时扫描过期记录，处理完后会删除掉记录
 * @author 管雷鸣
 *
 */
@Entity()
@Table(name = "shop_order_timeout", indexes={@Index(name="suoyin_index",columnList="expiretime")})
public class OrderTimeout implements java.io.Serializable{
	
	private Integer id;				//对应订单id
	private String state;			//值跟订单状态一样，此记录添加时，所对应的订单状态,比如订单下单后，记录的此条信息，那么这条信息的这个状态便是刚下单但未支付的状态（跟订单状态一样）。限制20字符
	private Integer expiretime;		//订单的过期时间，如果超过这个时间，说明这条记录已到定时时间了，该判断订单状态，进行转变状态了。同样，状态转变完了，也就是订单状态跟此条状态不一致了，此条信息就没有任何价值，要删除了
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "state", columnDefinition="char(20) comment '值跟订单状态一样，此记录添加时，所对应的订单状态,比如订单下单后，记录的此条信息，那么这条信息的这个状态便是刚下单但未支付的状态（跟订单状态一样）。限制20字符' default ''")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "expiretime", columnDefinition="int(11) comment '订单的过期时间，如果超过这个时间，说明这条记录已到定时时间了，该判断订单状态，进行转变状态了。同样，状态转变完了，也就是订单状态跟此条状态不一致了，此条信息就没有任何价值，要删除了' default '0'")
	public Integer getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Integer expiretime) {
		this.expiretime = expiretime;
	}
	@Override
	public String toString() {
		return "OrderTimeout [id=" + id + ", state=" + state + ", expiretime=" + expiretime + "]";
	}
	
}
