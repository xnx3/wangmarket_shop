package com.xnx3.wangmarket.shop.store.api.vo;

import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;

/**
 * 商品分类列表
 * @author 管雷鸣
 *
 */
public class GoodsTypeListVO extends BaseVO{
	private List<GoodsType> list;
	private Page page;
	
	public List<GoodsType> getList() {
		return list;
	}
	public void setList(List<GoodsType> list) {
		this.list = list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "GoodsTypeListVO [list=" + list + ", page=" + page + "]";
	}
	
}
