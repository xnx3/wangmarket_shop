package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;

/**
 * 订单的状态变化日志列表
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {"id","orderid"}, nullSetDefaultValue = true)
public class OrderStateLogListVO extends BaseVO {
	private List<OrderStateLog> orderStateLogList;
	
	public List<OrderStateLog> getOrderStateLogList() {
		return orderStateLogList;
	}
	public void setOrderStateLogList(List<OrderStateLog> orderStateLogList) {
		this.orderStateLogList = orderStateLogList;
	}
}
