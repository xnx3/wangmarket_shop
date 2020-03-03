package com.xnx3.wangmarket.shop.api.vo.bean;

/**
 * 支付设置的bean，针对实体类的简化
 * @author 管雷鸣
 *
 */
public class PaySetBean {
	private Short useAlipay;	//是否使用支付宝支付的支付方式，1使用；0不使用。默认不使用。
	private Short usePrivatePay;	//是否使用线下支付的支付方式，1使用；0不使用。默认使用。
	private Short useWeixinPay;	//是否使用微信支付的支付方式，1使用；0不使用。默认不使用。
	
	public Short getUseAlipay() {
		return useAlipay;
	}
	public void setUseAlipay(Short useAlipay) {
		this.useAlipay = useAlipay;
	}
	public Short getUsePrivatePay() {
		return usePrivatePay;
	}
	public void setUsePrivatePay(Short usePrivatePay) {
		this.usePrivatePay = usePrivatePay;
	}
	public Short getUseWeixinPay() {
		return useWeixinPay;
	}
	public void setUseWeixinPay(Short useWeixinPay) {
		this.useWeixinPay = useWeixinPay;
	}
	@Override
	public String toString() {
		return "PaySetBean [useAlipay=" + useAlipay + ", usePrivatePay=" + usePrivatePay + ", useWeixinPay="
				+ useWeixinPay + "]";
	}
	
}
