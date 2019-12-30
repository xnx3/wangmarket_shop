package com.xnx3.wangmarket.weixin.util.weixinPay.createOrder;

import com.xnx3.DateUtil;
import com.xnx3.StringUtil;

/**
 * 支付的订单信息
 * @author 管雷鸣
 *
 */
public class PayOrderBean {
	
	private String openid;	//要支付用户的openid
	private String body;	//商品描述， 支付成功显示在微信支付 商品详情中
	private int totalFee ;	//要支付的总金额，单位：分
	private String notifyUrl;	//通知地址，url，绝对路径，当支付成功后，微信会自动请求这个路径，如： "http://xxxx/wxpay/payCallback.do"
	private String clientIp;	//客户的ip
	private String outTradeNo;	//商户订单号，这个订单号是微信那边创建的订单要保存的。如果不设置，默认生成8位数字+字母的随机数
	
	public PayOrderBean() {
		this.outTradeNo = StringUtil.getRandom09AZ(2)+StringUtil.intTo36(DateUtil.timeForUnix10());
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Override
	public String toString() {
		return "PayOrderBean [openid=" + openid + ", body=" + body + ", totalFee=" + totalFee + ", notifyUrl="
				+ notifyUrl + ", clientIp=" + clientIp + ", outTradeNo=" + outTradeNo + "]";
	}
	
}
