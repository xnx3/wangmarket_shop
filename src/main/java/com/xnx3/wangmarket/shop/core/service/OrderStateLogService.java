package com.xnx3.wangmarket.shop.core.service;

import java.util.List;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;

/**
 * 订单状态变动的日志相关
 * @author 管雷鸣
 *
 */
public interface OrderStateLogService {
	
	/**
	 * 从数据库中，获取某个订单的状态变动日志。
	 * @param orderid 订单id，对应order.id ，要获取的状态变动日志记录，是属于哪个订单的
	 * @return list中数据的顺序，是根据状态改变的时间排序，也就是 id DESC 进行的排序
	 */
	public List<OrderStateLog> getLogList(int orderid);
	
}