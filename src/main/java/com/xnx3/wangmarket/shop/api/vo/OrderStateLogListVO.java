package com.xnx3.wangmarket.shop.api.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.OrderStateLogBean;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;

/**
 * 订单的状态变化日志列表
 * @author 管雷鸣
 */
public class OrderStateLogListVO extends BaseVO {
	private List<OrderStateLogBean> orderStateLogList;
	
	public List<OrderStateLogBean> getOrderStateLogList() {
		return orderStateLogList;
	}
	public void setOrderStateLogList(List<OrderStateLog> orderStateLogList) {
		List<OrderStateLogBean> beanList = new ArrayList<OrderStateLogBean>();
		if(orderStateLogList != null){
			for (int i = 0; i < orderStateLogList.size(); i++) {
				OrderStateLog log = orderStateLogList.get(i);
				OrderStateLogBean bean = new OrderStateLogBean();
				bean.setAddtime(log.getAddtime());
				bean.setState(log.getState());
				beanList.add(bean);
			}
		}
		this.orderStateLogList = beanList;
	}
	
}
