package com.xnx3.wangmarket.shop.core.pluginManage.interfaces;

import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单有用户发起退货申请时触发
 * @author 管雷鸣
 */
public interface OrderRefundIngInterface {
	
	/**
	 * 订单有用户发起退货申请时触发
	 * 	<ul>
	 * 		<li>当用户在自己的订单里面，将订单申请退单时触发</li>
	 * 	</ul>
	 */
	public void orderRefundIng(Order order);
	

}