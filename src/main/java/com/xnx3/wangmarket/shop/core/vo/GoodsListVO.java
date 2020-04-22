package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsBean;

/**
 * 商品列表
 * @author 管雷鸣
 *
 */
public class GoodsListVO extends BaseVO{
	private List<GoodsBean> list;	//商品列表
	private Page page;				//分页信息

	public List<GoodsBean> getList() {
		return list;
	}

	public void setList(List<Goods> list) {
		List<GoodsBean> beanList = new ArrayList<GoodsBean>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				Goods goods = list.get(i);
				GoodsBean goodsBean = new GoodsBean();
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
				beanList.add(goodsBean);
			}
		}
		this.list = beanList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
