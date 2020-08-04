package com.xnx3.wangmarket.shop.store.api.vo;

import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单列表
 * @author 管雷鸣
 */
public class OrderListVO extends BaseVO {
	private List<Order> orderList;
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return "OrderListVO [orderList=" + orderList + ", page=" + page + "]";
	}
	
}
