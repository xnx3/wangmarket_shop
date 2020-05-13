package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsBean;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsDataBean;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsImageBean;

/**
 * 商品详情，查看商品详情的时候，需要的 {@link Goods}、 {@link GoodsImage} 、 {@link GoodsData} 等信息便是从这里获取的
 * @author 管雷鸣
 *
 */
public class GoodsDetailsVO extends BaseVO{
	private GoodsBean goods;	//商品
	private List<GoodsImageBean> goodsImageList;	//商品的轮播图，已经按照 GoodsImage.rank 排序的
	private GoodsDataBean goodsData;	//商品详情信息，边长表的详细信息，比如商品详细描述，富文本编辑的内容
	
	public GoodsBean getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		GoodsBean goodsBean = new GoodsBean();
		if(goods != null){
			goodsBean.setAddtime(goods.getAddtime());
			goodsBean.setId(goods.getId());
			goodsBean.setInventory(goods.getInventory());
			goodsBean.setOriginalPrice(goods.getOriginalPrice());
			goodsBean.setPrice(goods.getPrice());
			goodsBean.setPutaway(goods.getPutaway());
			goodsBean.setSale(goods.getSale()+goods.getFakeSale());
			goodsBean.setStoreid(goods.getStoreid());
			goodsBean.setTitle(goods.getTitle());
			goodsBean.setTitlepic(goods.getTitlepic());
			goodsBean.setTypeid(goods.getTypeid());
			goodsBean.setUnits(goods.getUnits());
			goodsBean.setUserBuyRestrict(goods.getUserBuyRestrict());
			goodsBean.setIntro(goods.getIntro());
		}
		this.goods = goodsBean;
	}
	public List<GoodsImageBean> getGoodsImageList() {
		return goodsImageList;
	}
	public void setGoodsImageList(List<GoodsImage> goodsImageList) {
		List<GoodsImageBean> beanList = new ArrayList<GoodsImageBean>();
		if(goodsImageList != null){
			for (int i = 0; i < goodsImageList.size(); i++) {
				GoodsImage goodsImage = goodsImageList.get(i);
				GoodsImageBean goodsImageBean = new GoodsImageBean();
				goodsImageBean.setImageUrl(goodsImage.getImageUrl());
				beanList.add(goodsImageBean);
			}
		}
		this.goodsImageList = beanList;
	}
	public GoodsDataBean getGoodsData() {
		return goodsData;
	}
	public void setGoodsData(GoodsData goodsData) {
		GoodsDataBean goodsDataBean = new GoodsDataBean();
		if(goodsData != null){
			goodsDataBean.setDetail(goodsData.getDetail());
		}
		this.goodsData = goodsDataBean;
	}
}
