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