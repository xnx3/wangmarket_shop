package com.xnx3.wangmarket.shop.vo;

import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.GoodsType;

/**
 * 商品的分类列表，商品的种类。也就是 GoodsType 表存储的
 * @author 管雷鸣
 */
public class GoodsTypeListVO extends BaseVO {
	private List<GoodsType> goodsTypeList;	//商品分类的列表。这里是已经根据 GoodsType.rank 排序好

	public List<GoodsType> getGoodsTypeList() {
		return goodsTypeList;
	}

	public void setGoodsTypeList(List<GoodsType> goodsTypeList) {
		this.goodsTypeList = goodsTypeList;
	}

}

