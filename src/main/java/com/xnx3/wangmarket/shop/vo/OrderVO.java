package com.xnx3.wangmarket.shop.vo;

import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Order;
import com.xnx3.wangmarket.shop.entity.OrderAddress;
import com.xnx3.wangmarket.shop.entity.OrderGoods;
import com.xnx3.wangmarket.shop.entity.Store;

/**
 * 订单详情
 * @author 管雷鸣
 *
 */
public class OrderVO extends BaseVO{
	private Order order;		//订单信息
	private List<OrderGoods> goodsList;	//订单内所含的商品信息列表
	private User user;			//下单的用户的信息
	private Store store;		//该订单所属的商家信息
	private OrderAddress orderAddress;	//该订单配送的地址信息
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OrderAddress getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	@Override
	public String toString() {
		return "OrderVO [order=" + order + ", goodsList=" + goodsList + ", user=" + user + ", store=" + store
				+ ", orderAddress=" + orderAddress + "]";
	}
	
	
}
