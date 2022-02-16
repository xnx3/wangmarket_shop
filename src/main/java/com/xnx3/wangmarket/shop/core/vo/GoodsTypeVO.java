package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;

/**
 * 根据分类的id，取商品分类信息
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {"rank","isdelete"}, nullSetDefaultValue = true)
public class GoodsTypeVO extends BaseVO {
	private GoodsType goodsType;	//商品分类

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

}
