package com.xnx3.wangmarket.plugin.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
/**
 * 用于设置分销奖励，设置页面
 * @author 刘鹏
 */
public class SellStoreSetVO  extends BaseVO{

    private SellStoreSet set;

    public SellStoreSet getSet() {
        return set;
    }

    public void setSet(SellStoreSet set) {
        this.set = set;
    }

    @Override
    public String toString() {
        return "SellStoreSetVO{" +
                "set=" + set +
                '}';
    }
}
