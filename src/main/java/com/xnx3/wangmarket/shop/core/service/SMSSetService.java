package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.shop.core.entity.SmsSet;

/**
 * 商铺的短信发送设置
 * @author 管雷鸣
 *
 */
public interface SMSSetService {

	/**
	 * 获取某个商铺的短信设置
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的是哪个商城的
	 * @return 当前店铺的规则。如果店铺号不准确或者什么其他错误，那么都会返回一个 new {@link SmsSet}
	 */
	public SmsSet getSMSSet(int storeid);
	
	/**
	 * 将某个商家的短信设置加入持久缓存
	 * @param smsSet 商家的短信接口设置
	 */
	public void setSMSSet(SmsSet smsSet);
}