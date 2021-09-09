package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.PaySet;

/**
 * 支付设置相关信息
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"id","alipayAppId","alipayAppPrivateKey","alipayCertPublicKeyRSA2","alipayRootCert","alipayAppCertPublicKey","weixinOfficialAccountsAppid","weixinOfficialAccountsAppSecret","weixinOfficialAccountsToken","weixinMchId","weixinMchKey","weixinAppletAppid","weixinAppletAppSecret"}, nullSetDefaultValue = true)
public class PaySetVO extends BaseVO{
	private PaySet paySet;

	public PaySet getPaySet() {
		return paySet;
	}
	public void setPaySet(PaySet paySet) {
		this.paySet = paySet;
	}
}
