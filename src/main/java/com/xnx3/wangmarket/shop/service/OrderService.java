package com.xnx3.wangmarket.shop.service;

import com.xnx3.wangmarket.shop.entity.Address;
import com.xnx3.wangmarket.shop.entity.Order;
import com.xnx3.wangmarket.shop.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.vo.GoodsListVO;
import com.xnx3.wangmarket.shop.vo.GoodsTypeListVO;
import com.xnx3.wangmarket.shop.vo.OrderVO;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

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
	
}
