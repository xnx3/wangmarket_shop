package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.StringUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.PaySet;

/**
 * 支付相关设置
 * @author 刘鹏
 *
 */
public class PaySetVO extends BaseVO {
    private PaySet paySet;

    public PaySet getPaySet() {
        return paySet;
    }

    public void setPaySet(PaySet paySeDate) {
        PaySet paySet = new PaySet();

        paySet.setId(paySeDate.getId());
        paySet.setUseAlipay(paySeDate.getUseAlipay());
        paySet.setUsePrivatePay(paySeDate.getUsePrivatePay());
        paySet.setUseWeixinPay(paySeDate.getUseWeixinPay());
        paySet.setUseWeixinServiceProviderPay(paySeDate.getUseWeixinServiceProviderPay());

        paySet.setAlipayAppId(paySeDate.getAlipayAppId());
        paySet.setAlipayAppPrivateKey(substring(paySeDate.getAlipayAppPrivateKey()));
        paySet.setAlipayCertPublicKeyRSA2(substringTen(paySeDate.getAlipayCertPublicKeyRSA2()));
        paySet.setAlipayRootCert(paySeDate.getAlipayRootCert());
        paySet.setAlipayAppCertPublicKey(paySeDate.getAlipayAppCertPublicKey());

        paySet.setWeixinOfficialAccountsAppid(paySeDate.getWeixinOfficialAccountsAppid());
        paySet.setWeixinOfficialAccountsAppSecret(substring(paySeDate.getWeixinOfficialAccountsAppSecret()));
        paySet.setWeixinOfficialAccountsToken(paySeDate.getWeixinOfficialAccountsToken());
        paySet.setWeixinMchId(paySeDate.getWeixinMchId());
        paySet.setWeixinMchKey(substring(paySeDate.getWeixinMchKey()));
        paySet.setWeixinAppletAppid(paySeDate.getWeixinAppletAppid());
        paySet.setWeixinAppletAppSecret(substring(paySeDate.getWeixinAppletAppSecret()));
        paySet.setWeixinSerivceProviderSubMchId(substringTen(paySeDate.getWeixinSerivceProviderSubMchId()));

        this.paySet = paySet;

    }

    /**
     * 字符串截取
     * @param text 要截取的字符串
     * @return
     */
    private String substring(String text){
        if(text == null){
            return "";
        }
        if(text.length() > 10){
            return text.substring(0,10)+"******";
        }
        if(text.length() > 5){
            return text.substring(0,5)+"******";
        }

        return text;
    }

    /**
     * 字符串截取
     * @param text 要截取的字符串
     * @return
     */
    private String substringTen(String text){
        if(text == null){
            return "";
        }
        if(text.length() > 10){
            return text.substring(0,10);
        }else {

            return text;
        }

    }

    @Override
    public String toString() {
        return "PaySetVO{" +
                "paySet=" + paySet +
                '}';
    }
}
