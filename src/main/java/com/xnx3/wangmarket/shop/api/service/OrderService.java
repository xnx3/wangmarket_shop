package com.xnx3.wangmarket.shop.api.service;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.api.vo.OrderVO;

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
}
