package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 订单详情
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"userid","isdelete","version","referrerUserid",  "password","salt",  "alipayAppId","alipayAppPrivateKey","alipayCertPublicKeyRSA2","alipayRootCert","alipayAppCertPublicKey","weixinOfficialAccountsAppid","weixinOfficialAccountsAppSecret","weixinOfficialAccountsToken","weixinMchId","weixinMchKey","weixinAppletAppid","weixinAppletAppSecret"}, nullSetDefaultValue = true)
public class OrderVO extends BaseVO{
	private Order order;		//订单信息
	private List<OrderGoods> goodsList;	//订单内所含的商品信息列表
	private User user;			//下单的用户的信息
	private Store store;		//该订单所属的商家信息
	private OrderAddress orderAddress;	//该订单配送的地址信息
	private PaySet paySet;		//当前商家的支付设置。如果该订单已下单，但未支付，此信息就能有用了
	private OrderRule orderRule;	//当前商家的订单规则
	
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
	public PaySet getPaySet() {
		return paySet;
	}
	public void setPaySet(PaySet paySet) {
		this.paySet = paySet;
	}
	public OrderRule getOrderRule() {
		return orderRule;
	}
	public void setOrderRule(OrderRule orderRule) {
		this.orderRule = orderRule;
	}
}
