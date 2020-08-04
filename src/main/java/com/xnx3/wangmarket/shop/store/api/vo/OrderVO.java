package com.xnx3.wangmarket.shop.store.api.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderAddressBean;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderGoodsBean;
import com.xnx3.wangmarket.shop.core.vo.bean.UserBean;

/**
 * 订单详情
 * @author 管雷鸣
 *
 */
public class OrderVO extends BaseVO{
	private Order order;
	private UserBean user;			//下单的用户的信息
	private OrderAddressBean orderAddress;	//该订单配送的地址信息
	private List<OrderGoodsBean> goodsList;	//订单内所含的商品信息列表
	
	public void setGoodsList(List<OrderGoods> goodsList) {
		 List<OrderGoodsBean> beanList = new ArrayList<OrderGoodsBean>();
		 if(goodsList != null) {
			 for (int i = 0; i < goodsList.size(); i++) {
				 OrderGoods orderGoods = goodsList.get(i);
				 OrderGoodsBean bean = new OrderGoodsBean();
				 bean.setGoodsid(orderGoods.getId());
				 bean.setPrice(orderGoods.getPrice());
				 bean.setTitle(orderGoods.getTitle());
				 bean.setTitlepic(orderGoods.getTitlepic());
				 bean.setUnits(orderGoods.getUnits());
				 bean.setNumber(orderGoods.getNumber());
				 bean.setOrderid(orderGoods.getOrderid());
				 beanList.add(bean);
			}
		 }
		this.goodsList = beanList;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(User user) {
		UserBean userBean = new UserBean();
		if(user != null) {
			userBean.setHead(user.getHead());
			userBean.setId(user.getId());
			userBean.setNickname(user.getNickname());
			userBean.setPhone(user.getPhone());
			userBean.setUsername(user.getUsername());
			userBean.setRegtime(user.getRegtime());
		}
		this.user = userBean;
	}
	public OrderAddressBean getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(OrderAddress orderAddress) {
		OrderAddressBean orderAddressBean = new OrderAddressBean();
		if(orderAddress != null) {
			orderAddressBean.setAddress(orderAddress.getAddress());
			orderAddressBean.setLatitude(orderAddress.getLatitude());
			orderAddressBean.setLongitude(orderAddress.getLongitude());
			orderAddressBean.setPhone(orderAddress.getPhone());
			orderAddressBean.setUsername(orderAddress.getUsername());
		}
		this.orderAddress = orderAddressBean;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<OrderGoodsBean> getGoodsList() {
		return goodsList;
	}
	@Override
	public String toString() {
		return "OrderVO [order=" + order + ", user=" + user + ", orderAddress=" + orderAddress + ", goodsList="
				+ goodsList + ", getResult()=" + getResult() + ", getInfo()=" + getInfo() + "]";
	}
}
