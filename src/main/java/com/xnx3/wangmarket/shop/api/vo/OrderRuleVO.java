package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.OrderRuleBean;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;

/**
 * 店铺设置的订单规则流程信息
 * @author 管雷鸣
 *
 */
public class OrderRuleVO extends BaseVO{
	private OrderRuleBean orderRule;

	public OrderRuleBean getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(OrderRule orderRule) {
		if(orderRule == null){
			this.orderRule = new OrderRuleBean();
		}else{
			OrderRuleBean bean = new OrderRuleBean();
			bean.setDistribution(orderRule.getDistribution());
			bean.setRefund(orderRule.getRefund());
			this.orderRule = bean;
		}
	}
	
}
