package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.vo.OrderVO;

/**
 * 订单相关
 * @author 管雷鸣
 */
public interface OrderService {
	
	/**
	 * 创建订单
	 * @param address 配送地址
	 * @param order 要创建的订单信息
	 * @return {@link OrderVO}
	 */
	public OrderVO createOrder(Address address, Order order);
	
	/**
	 * 取消订单，或者支付后退款，会导致商品数量进行改变，比如商品的已售数量会减少，商品库存会增加回来
	 * @param order 取消的订单，或者支付成功后退款的订单
	 * @return {@link BaseVO} result = BaseVO.SUCCESS 为成功
	 */
	public BaseVO orderCancelGoodsNumberChange(Order order);
	
	/**
	 * 确认收货
	 * @param order 要确认收货的订单。需要传入之前判断用户是否有进行确认订单的权限
	 * @return {@link OrderVO} 除了result、info外，只有order是有信息的
	 */
	public OrderVO receiveGoods(Order order);
}
