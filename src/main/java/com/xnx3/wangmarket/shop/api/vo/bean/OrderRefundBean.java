package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderRefund;

/**
 * {@link OrderRefund} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderRefundBean {
	private Integer orderid;	//关联的订单id,对应 Order.id
	private Integer storeid;	//订单所属的商家id，对应 Store.id
	private Short state;		//当前退单状态	
	private Integer addtime;	//添加时间，也就是提交时间,10位时间戳
	private String reason;		//退单的理由,限制100字符
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public Integer getStoreid() {
		return storeid;
	}
	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
