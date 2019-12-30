package com.xnx3.wangmarket.weixin.util.weixinPay.createOrder;

/**
 * 微信支付创建订单的相关接口
 * @author 管雷鸣
 *
 */
public interface CreateOrderInterface {
	
	/**
	 * 在微信支付中，创建订单成功后执行的方法。
	 * @param payOrderBean {@link PayOrderBean}
	 */
	public void CreateOrderSuccess(PayOrderBean payOrderBean);
}
