package com.xnx3.wangmarket.shop.core.pluginManage.interfaces;

import java.util.List;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.entity.User;
import com.xnx3.wangmarket.shop.core.bean.BuyGoods;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 订单创建相关
 * @author 管雷鸣
 */
public interface OrderCreateInterface {
	
	/**
	 * 订单创建之前触发
	 * @param order 创建的订单。还未创建，id 还是 null
	 * @param buyGoodsList 此订单内购买的商品列表信息
	 * @param orderAddress 该订单的配送地址信息。其中 id  还是null
	 * @param user 要创建这个订单的用户 user 
	 * @param store 要下单的商品属于哪个店铺
	 * @return {@link BaseVO} 返回的result ：
	 * 	<ul>
	 * 		<li>getResult=BaseVO.SUCCESS 那么执行成功，放过，可以正常创建订单</li>
	 * 		<li>getResult=BaseVO.FAILURE 执行失败，拦截下来，不会再往下执行，不会创建订单。创建订单接口会返回此创建订单失败的 vo 。也就是 getInfo() 是失败原因</li>
	 * 	</ul>
	 */
	public BaseVO before(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store);
	
	/**
	 * 订单创建完毕之后，接口业务逻辑等都已经执行完了，触发
	 * @param order 创建的订单
	 * @param buyGoodsList 此订单内购买的商品列表信息
	 * @param orderAddress 该订单的配送地址信息
	 * @param user 要创建这个订单的用户 user 
	 * @param store 要下单的商品属于哪个店铺
	 */
	public void after(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store);
}