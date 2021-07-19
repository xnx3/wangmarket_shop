package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.vo.bean.OrderBean;

/**
 * 订单列表
 * @author 管雷鸣
 */
public class OrderListVO extends BaseVO {
	private List<OrderBean> list;
	/**
	 * 兼容1.4及以前版本，1.5版本都变为list返回
	 * @deprecated
	 */
	private List<OrderBean> orderList;
	private Page page;
	
	public List<OrderBean> getList() {
		return list;
	}
	public void setList(List<Order> list) {
		List<OrderBean> beanList = new ArrayList<OrderBean>();
		if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				Order order = list.get(i);
				OrderBean bean = new OrderBean();
				bean.setAddtime(order.getAddtime());
				bean.setId(order.getId());
				bean.setNo(order.getNo());
				bean.setPayMoney(order.getPayMoney());
				bean.setPayTime(order.getPayTime());
				bean.setRemark(order.getRemark());
				bean.setState(order.getState());
				bean.setTotalMoney(order.getTotalMoney());
				beanList.add(bean);
			}
		}
		this.list = beanList;
		this.orderList = beanList;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<OrderBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderBean> orderList) {
		this.orderList = orderList;
	}
	
}
