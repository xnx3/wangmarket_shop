package com.xnx3.wangmarket.shop.store.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;

/**
 * 商品分类
 * @author 刘鹏
 *
 */
public class GoodsTypeVO extends BaseVO {
    private GoodsType goodsType;    //商品分类

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "GoodsTypeVo{" +
                "goodsType=" + goodsType +
                '}';
    }
}
