package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单列表
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {"userid","isdelete","version"}, nullSetDefaultValue = true)
public class OrderListVO extends BaseVO {
	private List<Order> list;
	/**
	 * 兼容1.4及以前版本，1.5版本都变为list返回
	 * @deprecated
	 */
	private List<Order> orderList;
	private Page page;
	
	public List<Order> getList() {
		return list;
	}
	public void setList(List<Order> list) {
		this.list = list;
		this.orderList = list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<Order> getOrderList() {
		return list;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
}
