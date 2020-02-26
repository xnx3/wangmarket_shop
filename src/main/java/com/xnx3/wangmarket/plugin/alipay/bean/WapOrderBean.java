package com.xnx3.wangmarket.plugin.alipay.bean;

/**
 * 手机网页支付，统一下单支付，里面的值参考 https://docs.open.alipay.com/api_1/alipay.trade.wap.pay
 * @author 管雷鸣
 *
 */
public class WapOrderBean extends BaseOrderBean{
	private String quitUrl;		//用户付款中途退出返回商户网站的地址
	
	public WapOrderBean() {
	}

	/**
	 * 创建手机wap端网页支付的支付信息
	 * @param outTradeNo 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
	 * @param totalAmount 支付金额，订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
	 * @param subject 订单标题，256字符以内
	 * @param passbackParams 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。最大长度 512。如果不使用，传入空字符串"" 即可
	 * @param quitUrl 用户付款中途退出返回商户网站的地址
	 */
	public WapOrderBean(String outTradeNo, float totalAmount, String subject, String passbackParams, String quitUrl) {
		super.setOutTradeNo(outTradeNo);
		super.setTotalAmount(totalAmount);
		super.setSubject(subject);
		super.setPassbackParams(passbackParams);
		this.quitUrl = quitUrl;
	}
	
	public String getQuitUrl() {
		return quitUrl;
	}
	public void setQuitUrl(String quitUrl) {
		this.quitUrl = quitUrl;
	}


	/**
	 * 获取当前对象的 json 格式字符串
	 */
	public String getJsonString(){
		return "{"
				+ "\"out_trade_no\":\""+super.getOutTradeNo()+"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
				+ "\"total_amount\":\""+super.getTotalAmount()+"\","
				+ "\"quit_url\":\""+this.getQuitUrl()+"\","
				+ "\"subject\":\""+super.getSubject()+"\","
				+ "\"passback_params\":\""+super.getPassbackParams()+"\""
				+ "}";
	}
	
}
