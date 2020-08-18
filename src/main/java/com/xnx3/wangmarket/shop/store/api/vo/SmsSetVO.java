package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;

/**
 *
 * 短信相关
 * @author 刘鹏
 *
 */
public class SmsSetVO extends BaseVO {

    private SmsSet smsSet; //短信相关

    public SmsSet getSmsSet() {
        return smsSet;
    }

    public void setSmsSet(SmsSet smsSet) {
        SmsSet smsSetDate = new SmsSet();
        smsSetDate.setId(smsSet.getId());
        smsSetDate.setUid(smsSet.getUid());
        smsSetDate.setPassword(substring(smsSet.getPassword()));
        smsSetDate.setQuotaDayIp(smsSet.getQuotaDayIp());
        smsSetDate.setQuotaDayPhone(smsSet.getQuotaDayPhone());
        smsSetDate.setUseSms(smsSet.getUseSms());

        this.smsSet = smsSetDate;
    }

    //截取字符串
    private String substring(String text){
        if(text == null){
            return "";
        }
        if (text.length()>4){
            return text.substring(0,4) + "****";
        }
        return text;
    }
    @Override
    public String toString() {
        return "SmsSetVO{" +
                "smsSet=" + smsSet +
                '}';
    }
}
