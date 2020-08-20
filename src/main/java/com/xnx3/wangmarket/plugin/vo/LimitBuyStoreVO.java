package com.xnx3.wangmarket.plugin.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyStore;

/**
 * 商品限购规定用户的可购买次数。
 * @author 刘鹏
 */
public class LimitBuyStoreVO extends BaseVO {

    private LimitBuyStore limitBuyStore;

    public LimitBuyStore getLimitBuyStore() {
        return limitBuyStore;
    }

    public void setLimitBuyStore(LimitBuyStore limitBuyStore) {
        this.limitBuyStore = limitBuyStore;
    }

    @Override
    public String toString() {
        return "LimitBuyStoreVO{" +
                "limitBuyStore=" + limitBuyStore +
                '}';
    }
}
