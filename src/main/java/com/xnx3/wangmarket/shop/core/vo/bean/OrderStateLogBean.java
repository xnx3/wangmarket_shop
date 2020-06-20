package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;

/**
 *  {@link OrderStateLog} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderStateLogBean {
	private String state;			//变化之后的订单状态，新的订单状态
	private Integer addtime;		//此条记录的添加时间
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
}
