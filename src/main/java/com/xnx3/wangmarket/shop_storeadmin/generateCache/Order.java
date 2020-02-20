package com.xnx3.wangmarket.shop_storeadmin.generateCache;

import org.springframework.stereotype.Component;
import com.xnx3.j2ee.generateCache.BaseGenerate;

/**
 * 订单状态
 * @author 关光礼
 */
@Component(value="ShopOrderGenerate")
public class Order extends BaseGenerate {
	
	public Order(){
		state();
	}
	
	
	public void state() {
		createCacheObject("state");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_CANCELMONEY_FINISH, "下单后已退款");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_MY_CANCEL, "主动取消");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_CANCELMONEY_ING, "退款中");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_CREATE_BUT_NO_PAY, "待付款");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_PAY, "已付款");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_PAYTIMEOUT_CANCEL, "超时取消");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_RECEIVE_GOODS, "已确认收货");
		cacheAdd(com.xnx3.wangmarket.shop.entity.Order.STATE_DISTRIBUTION_ING, "配送中");
		generateCacheFile();
	}
}
