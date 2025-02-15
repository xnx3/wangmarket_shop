package com.xnx3.wangmarket.shop.store.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;

/**
 * 商品详情，查看商品详情的时候，需要的 {@link Goods}、、 {@link GoodsData} 等信息便是从这里获取的
 * @author 刘鹏
 *
 */
public class GoodsDetailsVO extends BaseVO{

	private Goods goods;	//商品
	private GoodsData goodsData;	//商品详情信息，边长表的详细信息，比如商品详细描述，文本编辑的内容
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public void setGoodsData(GoodsData goodsData) {
		this.goodsData = goodsData;
	}

	public GoodsData getGoodsData() {
		return goodsData;
	}
}