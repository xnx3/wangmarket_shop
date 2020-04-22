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
	 * @param orderid 要确认收货的订单id，对应 order.id
	 * @param userid 判断这个要确认收货的订单是否是这个用户的。如果userid 传入 -1 那么忽略这个判断。如果大于等于0，则取出订单的 order.userid 来判断是否跟此处传入的相等，如果相等，才能操作，如果不相等，则无权操作 
	 * @return {@link OrderVO} 除了result、info外，只有order是有信息的
	 */
	public OrderVO receiveGoods(int orderid, int userid);
}
