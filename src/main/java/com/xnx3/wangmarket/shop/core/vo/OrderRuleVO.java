package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;

/**
 * 店铺设置的订单规则流程信息
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"id"}, nullSetDefaultValue = true)
public class OrderRuleVO extends BaseVO{
	private OrderRule orderRule;

	public OrderRule getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(OrderRule orderRule) {
		this.orderRule = orderRule;
	}
}
