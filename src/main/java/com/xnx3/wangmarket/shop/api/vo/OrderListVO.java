package com.xnx3.wangmarket.shop.api.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.OrderBean;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单列表
 * @author 管雷鸣
 */
public class OrderListVO extends BaseVO {
	private List<OrderBean> orderList;
	private Page page;
	public List<OrderBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		List<OrderBean> beanList = new ArrayList<OrderBean>();
		if(orderList != null) {
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
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
		this.orderList = beanList;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
}
