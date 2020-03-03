package com.xnx3.wangmarket.shop.core.entity;

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
	
	@Column(name = "use_alipay", columnDefinition="tinyint(2) comment '是否使用支付宝支付的支付方式，1使用；0不使用。默认不使用。' default '0'")
	public Short getUseAlipay() {
		return useAlipay;
	}

	public void setUseAlipay(Short useAlipay) {
		this.useAlipay = useAlipay;
	}
	
	@Column(name = "use_private_pay", columnDefinition="tinyint(2) comment '是否使用线下支付的支付方式，1使用；0不使用。默认不使用。' default '0'")
	public Short getUsePrivatePay() {
		return usePrivatePay;
	}

	public void setUsePrivatePay(Short usePrivatePay) {
		this.usePrivatePay = usePrivatePay;
	}
	
	@Column(name = "use_weixin_pay", columnDefinition="tinyint(2) comment '是否使用微信支付的支付方式，1使用；0不使用。默认不使用。' default '0'")
	public Short getUseWeixinPay() {
		return useWeixinPay;
	}

	public void setUseWeixinPay(Short useWeixinPay) {
		this.useWeixinPay = useWeixinPay;
	}
	
	@Column(name = "alipay_app_id", columnDefinition="varchar(30) comment '支付宝支付的APP应用的id' default ''")
	public String getAlipayAppId() {
		return alipayAppId;
	}
	
	public void setAlipayAppId(String alipayAppId) {
		this.alipayAppId = alipayAppId;
	}
	
	@Column(name = "alipay_app_private_key", columnDefinition="varchar(4000) comment '支付宝支付的APP应用私钥' default ''")
	public String getAlipayAppPrivateKey() {
		return alipayAppPrivateKey;
	}

	public void setAlipayAppPrivateKey(String alipayAppPrivateKey) {
		this.alipayAppPrivateKey = alipayAppPrivateKey;
	}
	
	@Column(name = "alipay_cert_public_keyrsa2", columnDefinition="varchar(50) comment '支付宝公钥证书路径，存于 /mnt/shop/store/{storeid}/crt/ 目录下' default ''")
	public String getAlipayCertPublicKeyRSA2() {
		return alipayCertPublicKeyRSA2;
	}

	public void setAlipayCertPublicKeyRSA2(String alipayCertPublicKeyRSA2) {
		this.alipayCertPublicKeyRSA2 = alipayCertPublicKeyRSA2;
	}
	
	@Column(name = "alipay_root_cert", columnDefinition="varchar(50) comment '支付宝根证书路径' default ''")
	public String getAlipayRootCert() {
		return alipayRootCert;
	}

	public void setAlipayRootCert(String alipayRootCert) {
		this.alipayRootCert = alipayRootCert;
	}
	
	@Column(name = "alipay_app_cert_public_key", columnDefinition="varchar(50) comment '支付宝应用公钥证书路径' default ''")
	public String getAlipayAppCertPublicKey() {
		return alipayAppCertPublicKey;
	}

	public void setAlipayAppCertPublicKey(String alipayAppCertPublicKey) {
		this.alipayAppCertPublicKey = alipayAppCertPublicKey;
	}
	
	@Column(name = "weixin_mch_id", columnDefinition="varchar(50) comment '微信支付商户号' default ''")
	public String getWeixinMchId() {
		return weixinMchId;
	}

	public void setWeixinMchId(String weixinMchId) {
		this.weixinMchId = weixinMchId;
	}
	
	@Column(name = "weixin_mch_key", columnDefinition="varchar(100) comment '微信支付商户key，在微信商户平台-帐户设置-安全设置-API安全-API密钥-设置API密钥这个里面设置的KEY' default ''")
	public String getWeixinMchKey() {
		return weixinMchKey;
	}

	public void setWeixinMchKey(String weixinMchKey) {
		this.weixinMchKey = weixinMchKey;
	}
	
	@Column(name = "weixin_applet_appid", columnDefinition="varchar(50) comment '微信小程序id' default ''")
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