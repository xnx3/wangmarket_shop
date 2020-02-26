package com.xnx3.wangmarket.plugin.alipay.bean;

/**
 * 支付时，传递的参数的父类，PC电脑支付、手机支付都需要传递这些共同的参数
 * @author 管雷鸣
 */
class BaseOrderBean {
	private String outTradeNo;	//商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
	private float totalAmount;	//支付金额，订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
	private String subject;		//订单标题，256字符以内
	private String passbackParams;	//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。最大长度 512
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPassbackParams() {
		return passbackParams;
	}
	/**
	 * 选填，非必填
	 * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。最大长度 512
	 * @param passbackParams
	 */
	public void setPassbackParams(String passbackParams) {
		this.passbackParams = passbackParams;
	}
	
}
