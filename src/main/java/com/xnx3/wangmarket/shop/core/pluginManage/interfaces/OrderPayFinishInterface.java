package com.xnx3.wangmarket.shop.core.pluginManage.interfaces;

import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单支付完成触发
 * @author 管雷鸣
 */
public interface OrderPayFinishInterface {
	
	/**
	 * 订单创建完毕之后，接口业务逻辑等都已经执行完了，触发
	 * @param order 创建的订单
	 */
	public void orderPayFinish(Order order);
}