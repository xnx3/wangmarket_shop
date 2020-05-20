package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;

/**
 * 商铺的短信发送设置
 * @author 管雷鸣
 *
 */
public interface SMSService {
	
	public BaseVO send(String phone, String content);
}