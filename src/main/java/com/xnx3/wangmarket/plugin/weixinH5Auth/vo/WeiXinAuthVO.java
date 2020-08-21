package com.xnx3.wangmarket.plugin.weixinH5Auth.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 微信网页授权
 * @author 刘鹏
 */
public class WeiXinAuthVO extends BaseVO {

    Store store ;
    PaySet payset;
    //判断是否设置了微信公众号，或者使用服务商的
    boolean setgongzhonghoa;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public PaySet getPayset() {
        return payset;
    }

    public void setPayset(PaySet payset) {
        this.payset = payset;
    }

    public boolean isSetgongzhonghoa() {
        return setgongzhonghoa;
    }

    public void setSetgongzhonghoa(boolean setgongzhonghoa) {
        this.setgongzhonghoa = setgongzhonghoa;
    }

    @Override
    public String toString() {
        return "WeiXinAuthVO{" +
                "store=" + store +
                ", payset=" + payset +
                ", setgongzhonghoa=" + setgongzhonghoa +
                '}';
    }
}
