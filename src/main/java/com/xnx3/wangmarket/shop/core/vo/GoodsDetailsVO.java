package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;

/**
 * 商品详情，查看商品详情的时候，需要的 {@link Goods}、 {@link GoodsImage} 、 {@link GoodsData} 等信息便是从这里获取的
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"fakeSale","isdelete","alarmNum","updatetime","rank","version","goodsid"}, nullSetDefaultValue = true)
public class GoodsDetailsVO extends BaseVO{
	private Goods goods;	//商品
	private List<GoodsImage> goodsImageList;	//商品的轮播图，已经按照 GoodsImage.rank 排序的
	private GoodsData goodsData;	//商品详情信息，边长表的详细信息，比如商品详细描述，富文本编辑的内容
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public List<GoodsImage> getGoodsImageList() {
		return goodsImageList;
	}
	public void setGoodsImageList(List<GoodsImage> goodsImageList) {
		this.goodsImageList = goodsImageList;
	}
	public GoodsData getGoodsData() {
		return goodsData;
	}
	public void setGoodsData(GoodsData goodsData) {
		this.goodsData = goodsData;
	}
}
