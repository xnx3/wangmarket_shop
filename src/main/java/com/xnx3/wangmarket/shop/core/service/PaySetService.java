package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;

/**
 * 商铺的支付设置
 * @author 管雷鸣
 *
 */
public interface PaySetService {
	
	/**
	 * 获取某个商铺的支付设置
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的支付设置是哪个商城的
	 * @return 当前店铺的规则。如果店铺号不准确或者什么其他错误，那么都会返回一个 new {@link OrderRule}
	 */
	public PaySet getPaySet(int storeid);
	
	/**
	 * 将某个商家的支付设置加入持久缓存
	 * @param paySet 商家的支付设置
	 */
	public void setPaySet(PaySet paySet);
	
	/**
	 * 获取系统全局的，服务商的参数。这里也就是相当于  getPaySet(0)
	 */
	public PaySet getSerivceProviderPaySet();
}