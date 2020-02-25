package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * {@link Order} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderBean {
	private Integer id;			//订单id
	private String no;			//订单号，这个是支付宝支付、微信支付传过去的订单号，也是给用户显示出来的订单号
	private Integer addtime;	//订单创建时间，10为时间戳
	private Integer totalMoney;	//订单总金额,单位：分
	private Integer payMoney;		//需要实际支付的金额,单位：分
	private Integer payTime;	//该订单支付的时间，10位时间戳
	private String state;		//订单状态，限20个字符
	private String remark;		//用户备注，限制100个字符
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	public Integer getPayTime() {
		return payTime;
	}
	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
