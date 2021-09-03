package com.xnx3.wangmarket.shop.store.vo;

import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单列表
 * @author 管雷鸣
 */
public class OrderListVO extends BaseVO {
	private List<Order> list;
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<Order> getList() {
		return list;
	}
	public void setList(List<Order> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "OrderListVO [list=" + list + ", page=" + page + "]";
	}
	
}
