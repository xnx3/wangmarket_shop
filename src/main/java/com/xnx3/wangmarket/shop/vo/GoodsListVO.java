package com.xnx3.wangmarket.shop.vo;

import java.util.List;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Goods;

/**
 * 商品列表
 * @author 管雷鸣
 *
 */
public class GoodsListVO extends BaseVO{
	private List<Goods> goodsList;	//商品列表

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	
	
}
