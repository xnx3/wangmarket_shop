package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.shop.core.entity.OrderRule;

/**
 * 商铺的订单规则
 * @author 管雷鸣
 *
 */
public interface OrderRuleService {
	
	/**
	 * 获取某个商铺的订单规则。
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid
	 * @return 当前店铺的规则。如果店铺号不准确或者什么其他错误，那么都会返回一个 new {@link OrderRule}
	 */
	public OrderRule getRole(int storeid);
	
	/**
	 * 将某个商家的订单规则加入持久缓存
	 * @param orderRule 某个商家的订单规则
	 */
	public void setRole(OrderRule orderRule);
}