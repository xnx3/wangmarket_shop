package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsTypeBean;

/**
 * 商品的分类列表，商品的种类。也就是 GoodsType 表存储的
 * @author 管雷鸣
 */
public class GoodsTypeListVO extends BaseVO {
	private List<GoodsTypeBean> list;	//商品分类的列表。这里是已经根据 GoodsType.rank 排序好

	public List<GoodsTypeBean> getList() {
		return list;
	}

	public void setList(List<GoodsType> list) {
		List<GoodsTypeBean> beanList = new ArrayList<GoodsTypeBean>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				GoodsType goodsType = list.get(i);
				GoodsTypeBean goodsTypeBean = new GoodsTypeBean();
				goodsTypeBean.setId(goodsType.getId());
				goodsTypeBean.setIcon(goodsType.getIcon());
				goodsTypeBean.setTitle(goodsType.getTitle());
				beanList.add(goodsTypeBean);
			}
		}
		this.list = beanList;
	}

}
