package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;
import com.xnx3.weixin.WeiXinAppletUtil;
import com.xnx3.weixin.WeiXinUtil;

/**
 * 微信相关
 * @author 管雷鸣
 *
 */
public interface WeiXinService {
	
	/**
	 * 获取某个商铺的微信公众号工具类
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的微信公众号工具类是哪个商城的
	 * @return 当前店铺的商铺的微信公众号工具类。如果返回null，则是当前店铺未设置使用微信公众号
	 */
	public WeiXinUtil getWeiXinUtil(int storeid);
	
	/**
	 * 获取某个商铺的微信小程序工具类
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的微信小程序工具类是哪个商城的
	 * @return 当前店铺的商铺的微信小程序工具类。如果返回null，则是当前店铺未设置使用微信小程序
	 */
	public WeiXinAppletUtil getWeiXinAppletUtil(int storeid);
}