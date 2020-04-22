package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderAddressBean;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderBean;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderGoodsBean;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderRuleBean;
import com.xnx3.wangmarket.shop.core.vo.bean.PaySetBean;
import com.xnx3.wangmarket.shop.core.vo.bean.StoreBean;
import com.xnx3.wangmarket.shop.core.vo.bean.UserBean;

/**
 * 订单详情
 * @author 管雷鸣
 *
 */
public class OrderVO extends BaseVO{
	private OrderBean order;		//订单信息
	private List<OrderGoodsBean> goodsList;	//订单内所含的商品信息列表
	private UserBean user;			//下单的用户的信息
	private StoreBean store;		//该订单所属的商家信息
	private OrderAddressBean orderAddress;	//该订单配送的地址信息
	private PaySetBean paySet;		//当前商家的支付设置。如果该订单已下单，但未支付，此信息就能有用了
	private OrderRuleBean orderRule;	//当前商家的订单规则
	
	public OrderBean getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		OrderBean bean = new OrderBean();
		if(order != null) {
			bean.setAddtime(order.getAddtime());
			bean.setId(order.getId());
			bean.setNo(order.getNo());
			bean.setPayMoney(order.getPayMoney());
			bean.setPayTime(order.getPayTime());
			bean.setRemark(order.getRemark());
			bean.setState(order.getState());
			bean.setTotalMoney(order.getTotalMoney());
		}
		this.order = bean;
	}
	public List<OrderGoodsBean> getGoodsList() {
		return goodsList;
	}
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
	public StoreBean getStore() {
		return store;
	}
	public void setStore(Store store) {
		StoreBean storeBean = new StoreBean();
		if(store != null) {
			storeBean.setAddress(storeBean.getAddress());
			storeBean.setAddtime(storeBean.getAddtime());
			storeBean.setCity(storeBean.getCity());
			storeBean.setContacts(storeBean.getContacts());
			storeBean.setDistrict(storeBean.getDistrict());
			storeBean.setHead(storeBean.getHead());
			storeBean.setId(storeBean.getId());
			storeBean.setLatitude(storeBean.getLatitude());
			storeBean.setLongitude(storeBean.getLongitude());
			storeBean.setName(storeBean.getName());
			storeBean.setNotice(storeBean.getNotice());
			storeBean.setPhone(storeBean.getPhone());
			storeBean.setProvince(storeBean.getProvince());
			storeBean.setSale(storeBean.getSale());
			storeBean.setState(storeBean.getState());
		}
		this.store = storeBean;
	}
	public PaySetBean getPaySet() {
		return paySet;
	}
	public void setPaySet(PaySet paySet) {
		if(paySet == null){
			this.paySet = new PaySetBean();
		}else{
			PaySetBean bean = new PaySetBean();
			bean.setUseAlipay(paySet.getUseAlipay());
			bean.setUsePrivatePay(paySet.getUsePrivatePay());
			bean.setUseWeixinPay(paySet.getUseWeixinPay());
			this.paySet = bean;
		}
	}
	public OrderRuleBean getOrderRule() {
		return orderRule;
	}
	public void setOrderRule(OrderRule orderRule) {
		if(orderRule == null){
			this.orderRule = new OrderRuleBean();
		}else{
			OrderRuleBean bean = new OrderRuleBean();
			bean.setDistribution(orderRule.getDistribution());
			bean.setRefund(orderRule.getRefund());
			this.orderRule = bean;
		}
	}
	
}
