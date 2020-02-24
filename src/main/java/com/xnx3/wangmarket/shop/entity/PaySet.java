package com.xnx3.wangmarket.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 收货地址
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_pay_set")
public class PaySet extends BaseEntity {
	
	private Integer id;			//编号，对应 store.id
	private Short useAlipay;	//是否使用支付宝支付的支付方式，1使用；0不使用。默认不使用。
	private Short usePrivatePay;	//是否使用线下支付的支付方式，1使用；0不使用。默认使用。
	private Short useWeixinPay;	//是否使用微信支付的支付方式，1使用；0不使用。默认不使用。
	
	private String alipayAppId;				//支付宝支付的APP应用的id
	private String alipayAppPrivateKey;		//支付宝支付的APP应用私钥
	private String alipayCertPublicKeyRSA2;	//支付宝公钥证书路径，存于 /mnt/shop/store/{storeid}/crt/ 目录下
	private String alipayRootCert;			//支付宝根证书路径
	private String alipayAppCertPublicKey;	//支付宝应用公钥证书路径
	
	private String weixinMchId;		//微信支付商户号
	private String weixinMchKey;	//微信支付商户key，在微信商户平台-帐户设置-安全设置-API安全-API密钥-设置API密钥这个里面设置的KEY
	private String weixinAppletAppid;	//微信小程序id
	
	public PaySet() {
		this.useAlipay = ISFREEZE_FREEZE;
		this.useWeixinPay = ISFREEZE_FREEZE;
		this.usePrivatePay = ISFREEZE_NORMAL;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

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

	public String getAlipayAppId() {
		return alipayAppId;
	}

	public void setAlipayAppId(String alipayAppId) {
		this.alipayAppId = alipayAppId;
	}

	public String getAlipayAppPrivateKey() {
		return alipayAppPrivateKey;
	}

	public void setAlipayAppPrivateKey(String alipayAppPrivateKey) {
		this.alipayAppPrivateKey = alipayAppPrivateKey;
	}

	public String getAlipayCertPublicKeyRSA2() {
		return alipayCertPublicKeyRSA2;
	}

	public void setAlipayCertPublicKeyRSA2(String alipayCertPublicKeyRSA2) {
		this.alipayCertPublicKeyRSA2 = alipayCertPublicKeyRSA2;
	}

	public String getAlipayRootCert() {
		return alipayRootCert;
	}

	public void setAlipayRootCert(String alipayRootCert) {
		this.alipayRootCert = alipayRootCert;
	}

	public String getAlipayAppCertPublicKey() {
		return alipayAppCertPublicKey;
	}

	public void setAlipayAppCertPublicKey(String alipayAppCertPublicKey) {
		this.alipayAppCertPublicKey = alipayAppCertPublicKey;
	}

	public String getWeixinMchId() {
		return weixinMchId;
	}

	public void setWeixinMchId(String weixinMchId) {
		this.weixinMchId = weixinMchId;
	}

	public String getWeixinMchKey() {
		return weixinMchKey;
	}

	public void setWeixinMchKey(String weixinMchKey) {
		this.weixinMchKey = weixinMchKey;
	}

	public String getWeixinAppletAppid() {
		return weixinAppletAppid;
	}

	public void setWeixinAppletAppid(String weixinAppletAppid) {
		this.weixinAppletAppid = weixinAppletAppid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PaySet [id=" + id + ", useAlipay=" + useAlipay + ", usePrivatePay=" + usePrivatePay + ", useWeixinPay="
				+ useWeixinPay + ", alipayAppId=" + alipayAppId + ", alipayAppPrivateKey=" + alipayAppPrivateKey
				+ ", alipayCertPublicKeyRSA2=" + alipayCertPublicKeyRSA2 + ", alipayRootCert=" + alipayRootCert
				+ ", alipayAppCertPublicKey=" + alipayAppCertPublicKey + ", weixinMchId=" + weixinMchId
				+ ", weixinMchKey=" + weixinMchKey + ", weixinAppletAppid=" + weixinAppletAppid + "]";
	}
	
}