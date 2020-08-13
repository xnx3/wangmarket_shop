package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;

import java.util.List;

/**
 * 商品轮播图片
 * @author 刘鹏
 *
 */
public class GoodsImageListVO extends BaseVO{
	private List<GoodsImage> list;	//轮播图列表
	private int	id;	// 如修改操作，传入修改的数据id，添加测不传参
	private Page page;				//分页信息

	public List<GoodsImage> getList() {
		return list;
	}

	public void setList(List<GoodsImage> list) {
		this.list = list;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GoodsImageListVO{" +
				"list=" + list +
				", id=" + id +
				", page=" + page +
				'}';
	}
}
