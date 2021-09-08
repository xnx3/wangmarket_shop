package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;

/**
 * 商品列表
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"fakeSale","isdelete","alarmNum","updatetime","rank","version"}, nullSetDefaultValue = true)
public class GoodsListVO extends BaseVO{
	private List<Goods> list;	//商品列表
	private Page page;				//分页信息

	public List<Goods> getList() {
		return list;
	}

	public void setList(List<Goods> list) {
		this.list = list;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
