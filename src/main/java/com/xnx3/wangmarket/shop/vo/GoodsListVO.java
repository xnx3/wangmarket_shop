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
	private List<Goods> list;	//商品列表

	public List<Goods> getList() {
		return list;
	}

	public void setList(List<Goods> list) {
		this.list = list;
	}

	
}
