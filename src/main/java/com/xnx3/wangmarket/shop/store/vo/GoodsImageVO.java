package com.xnx3.wangmarket.shop.store.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;

/**
 * 商品图片与id
 * @author 刘鹏
 *
 */
public class GoodsImageVO extends BaseVO {

    private GoodsImage goodsImage;  //商品图片

    private int id; //商品id

    public GoodsImage getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(GoodsImage goodsImage) {
        this.goodsImage = goodsImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GoodsImageVo{" +
                "goodsImage=" + goodsImage +
                ", id=" + id +
                '}';
    }
}
