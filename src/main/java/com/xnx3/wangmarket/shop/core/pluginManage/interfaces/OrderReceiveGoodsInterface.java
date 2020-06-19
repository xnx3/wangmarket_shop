package com.xnx3.wangmarket.shop.core.pluginManage.interfaces;

import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单完成时（确认收货）触发
 * @author 管雷鸣
 */
public interface OrderReceiveGoodsInterface {
	
	/**
	 * 订单完成时（确认收货）触发。这个订单完成，指以下这两种情况：
	 * 	<ul>
	 * 		<li>当商家在管理后台，将订单状态变为已完成时</li>
	 * 		<li>当用户在自己的订单里面，将订单确认收货，变为已收货时</li>
	 * 	</ul>
	 */
	public void orderReceiveGoodsFinish(Order order);
	

}