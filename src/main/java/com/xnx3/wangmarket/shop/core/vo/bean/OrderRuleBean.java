package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderRule;

/**
 * {@link OrderRule} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderRuleBean {
	private Short distribution;	//是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。1使用，0不使用。默认是1使用
	private Short refund;		//是否使用退款这个状态，也就是是否允许用户退款。1使用，0不使用。默认是1使用
	private Integer notPayTimeout;	//订单如果创建订单了，但未支付，多长时间会自动取消订单，订单状态变为已取消。这里的单位是秒
	
	public OrderRuleBean() {
		this.distribution = 1;
		this.refund = 1;
		this.notPayTimeout = 1800;	//默认半小时
	}
	
	public Short getDistribution() {
		return distribution;
	}
	public void setDistribution(Short distribution) {
		if(distribution == null){
			this.distribution = 1;
		}else{
			this.distribution = distribution;			
		}
	}
	public Short getRefund() {
		return refund;
	}
	public void setRefund(Short refund) {
		if(refund == null){
			this.refund = 1;
		}else{
			this.refund = refund;
		}
	}

	public Integer getNotPayTimeout() {
		return notPayTimeout;
	}

	public void setNotPayTimeout(Integer notPayTimeout) {
		if(notPayTimeout == null){
			this.notPayTimeout = 1800;
		}else{
			this.notPayTimeout = notPayTimeout;
		}
	}

	@Override
	public String toString() {
		return "OrderRuleBean [distribution=" + distribution + ", refund=" + refund + ", notPayTimeout=" + notPayTimeout
				+ "]";
	}
	
}
