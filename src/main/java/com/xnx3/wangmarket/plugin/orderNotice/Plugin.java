package com.xnx3.wangmarket.plugin.orderNotice;

import com.xnx3.BaseVO;
import com.xnx3.SMSUtil;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.plugin.orderNotice.entity.OrderNotice;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderPayFinishInterface;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderRefundIngInterface;
import com.xnx3.wangmarket.shop.core.service.StoreSMSService;
import com.xnx3.wangmarket.shop.core.util.SpringUtil;

/**
 * 订单通知。当用户付款成功后（无论是线上支付还是线下支付）、申请退款、会自动向店家的手机号发送一条短信
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "订单通知",menuHref="/plugin/orderNotice/store/index.jsp", applyToCMS=true, intro="当用户付款成功后（无论是线上支付还是线下支付）会自动向店家的手机号发送一条短信", version="1.0", versionMin="1.0")
public class Plugin implements OrderPayFinishInterface,OrderRefundIngInterface {
	public static final String PAYFINISH_SMS_TEMPLATE = "您好，刚有用户下单支付，订单号：{orderno}，订单金额：{money}元";	//下单支付成功发送短信的模板
	public static final String REFUNDING_SMS_TEMPLATE = "您好，刚有用户申请退单，订单号：{orderno}，订单金额：{money}元";	//用户有退单申请时，发送短信的模板
	
	@Override
	public void orderPayFinish(Order order) {
		SqlCacheService sqlCacheService = (SqlCacheService) SpringUtil.getBean("sqlCacheService");
		OrderNotice orderNotice = sqlCacheService.findById(OrderNotice.class, order.getStoreid());
		if(orderNotice == null || orderNotice.getPayNotice() - OrderNotice.IS_USE_NO == 0 || orderNotice.getPhone().length() < 11){
			//未配置、未启用、手机号不合规，那么不使用
			return;
		}
		
		StoreSMSService smsService = SpringUtil.getStoreSMSService();
		if(smsService == null){
			return;
		}
		SMSUtil smsUtil = smsService.getSMSUtil(order.getStoreid());
		if(smsUtil != null){
			BaseVO vo = smsUtil.send(orderNotice.getPhone(), Plugin.PAYFINISH_SMS_TEMPLATE.replace("{orderno}", order.getNo()).replace("{money}", (order.getTotalMoney()/100f)+""));
			//日志
			ActionLogUtil.insertUpdateDatabase(null, "支付通知发送短信", "payNotice:"+orderNotice.toString()+", order:"+order.toString()+", vo:"+vo.toString());
		}
	}

	@Override
	public void orderRefundIng(Order order) {
		SqlCacheService sqlCacheService = (SqlCacheService) SpringUtil.getBean("sqlCacheService");
		OrderNotice orderNotice = sqlCacheService.findById(OrderNotice.class, order.getStoreid());
		if(orderNotice == null || orderNotice.getRefundNotice() - OrderNotice.IS_USE_NO == 0 || orderNotice.getPhone().length() < 11){
			//未配置、未启用、手机号不合规，那么不使用
			return;
		}
		StoreSMSService smsService = SpringUtil.getStoreSMSService();
		if(smsService == null){
			return;
		}
		SMSUtil smsUtil = smsService.getSMSUtil(order.getStoreid());
		if(smsUtil != null){
			BaseVO vo = smsUtil.send(orderNotice.getPhone(), Plugin.REFUNDING_SMS_TEMPLATE.replace("{orderno}", order.getNo()).replace("{money}", (order.getTotalMoney()/100f)+""));
			//日志
			ActionLogUtil.insertUpdateDatabase(null, "用户发起申请退单时通知商家发送短信", "payNotice:"+orderNotice.toString()+", order:"+order.toString()+", vo:"+vo.toString());
		}
	}
	
	
}