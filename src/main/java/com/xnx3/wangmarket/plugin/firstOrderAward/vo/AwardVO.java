package com.xnx3.wangmarket.plugin.firstOrderAward.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;


/**
 * 用于推广送礼设置哪个商品作为赠品，奖品
 * @author 刘鹏
 */
public class AwardVO extends BaseVO {

    private Award award;

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    @Override
    public String toString() {
        return "AwardVO{" +
                "award=" + award +
                '}';
    }
}
