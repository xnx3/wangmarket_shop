package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;

/**
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
        this.smsSet = smsSet;
    }

    @Override
    public String toString() {
        return "SmsSetVO{" +
                "smsSet=" + smsSet +
                '}';
    }
}
