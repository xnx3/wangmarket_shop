package com.xnx3.wangmarket.plugin.orderNotice.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.orderNotice.entity.OrderNotice;

/**
 * 商品限购规定用户的可购买次数。
 * @author 刘鹏
 */
public class OrderNoticeVO extends BaseVO {
    private OrderNotice orderNotice;

	public OrderNotice getOrderNotice() {
		return orderNotice;
	}

	public void setOrderNotice(OrderNotice orderNotice) {
		this.orderNotice = orderNotice;
	}
    
}
