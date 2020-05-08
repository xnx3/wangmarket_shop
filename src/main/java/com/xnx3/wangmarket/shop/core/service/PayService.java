package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;
import com.xnx3.wangmarket.shop.core.vo.WeiXinPayUtilVO;
import com.xnx3.weixin.WeiXinPayUtil;

/**
 * 商铺的支付相关
 * @author 管雷鸣
 *
 */
public interface PayService {
	
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
	 * 获取支付宝支付的 {@link AlipayUtil}
	 * @param storeid 要获取的支付方式是哪个店铺的。每个店铺都有自己的一个支付方式
	 * @return  result=1 ，则是成功，否则用info可以获取失败原因
	 */
	public AlipayUtilVO getAlipayUtil(int storeid);
	
	/**
	 * 获取微信支付的 {@link WeiXinPayUtil}
	 * @param storeid 要获取的支付方式是哪个店铺的。每个店铺都有自己的一个支付方式
	 * @return  result=1 ，则是成功，否则用info可以获取失败原因
	 */
	public WeiXinPayUtilVO getWeiXinPayUtil(int storeid);
}