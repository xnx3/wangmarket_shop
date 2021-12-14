package com.xnx3.wangmarket.shop.core.system;

import java.util.List;
import org.springframework.stereotype.Component;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;
import com.xnx3.wangmarket.shop.core.entity.OrderTimeout;

/**
 * 针对订单各种超时的监听
 * @author 管雷鸣
 *
 */
@Component(value="WangmarketShopOrderTimeoutListenerThread")
public class OrderTimeoutListenerThread extends Thread{
	private static OrderTimeoutListenerThread thread;
	static{
		if(thread == null){
			thread = new OrderTimeoutListenerThread();
			thread.start();
		}
	}
	
	@Override
	public void run() {
		while(Global.system.size() < 1){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//当数据库加载完毕之后，在启动此线程
		
		ConsoleUtil.log("OrderTimeoutListenerThread start");
		while (true) {
			try {
				Thread.sleep(3*1000);	//3秒一次 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				//执行查询处理
				select();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void select(){
		//获取sqlservice
		SqlService sqlservice = SpringUtil.getSqlService();
		//当前时间戳
		int currentTime = DateUtil.timeForUnix10();
		//查询出超时的要处理的订单，一次最多查出1000条
		List<OrderTimeout> orderTimeoutList = sqlservice.findBySqlQuery("SELECT * FROM shop_order_timeout WHERE expiretime < "+currentTime+" ORDER BY expiretime ASC LIMIT 1000", OrderTimeout.class);
		//将超时该处理的订单遍历，进行处理
		for (int i = 0; i < orderTimeoutList.size(); i++) {
			OrderTimeout orderTimeout = orderTimeoutList.get(i);
			Order order = sqlservice.findById(Order.class, orderTimeout.getId());
			if(order != null && order.getState().equals(orderTimeout.getState())){
				//状态符合，进行处理
				if(order.getState().equals(Order.STATE_CREATE_BUT_NO_PAY)){
					//已下单待付款，超时，那变为订单已取消
					order.setState(Order.STATE_PAYTIMEOUT_CANCEL);
					//取消订单了，同时释放库存
					int row = sqlservice.executeSql("UPDATE shop_order SET state = '"+Order.STATE_PAYTIMEOUT_CANCEL+"', version = version+1 WHERE id = "+order.getId()+" AND version = "+order.getVersion());
					if(row == 0){
						continue;
					}
					
					//订单状态改变记录
					OrderStateLog stateLog = new OrderStateLog();
					stateLog.setAddtime(DateUtil.timeForUnix10());
					stateLog.setState(order.getState());
					stateLog.setOrderid(order.getId());
					sqlservice.save(stateLog);

					//查询出这个订单下有哪些商品
					List<OrderGoods> orderGoodsList = sqlservice.findBySqlQuery("SELECT * FROM shop_order_goods WHERE orderid = "+order.getId(), OrderGoods.class);
					//统计一共有多少商品
					int allNumber = 0;
					for (int j = 0; j < orderGoodsList.size(); j++) {
						OrderGoods orderGoods = orderGoodsList.get(j);
						allNumber = allNumber + orderGoods.getNumber();
						//商品Goods销售数量-，库存+
						sqlservice.executeSql("UPDATE shop_goods SET inventory = inventory +"+orderGoods.getNumber()+", sale = sale - "+orderGoods.getNumber()+" WHERE id = "+orderGoods.getGoodsid());
					}
					//商铺的销量-
					sqlservice.executeSql("UPDATE shop_store SET sale = sale - " + allNumber + " WHERE id = "+order.getStoreid());
				}else if(order.getState().equals(Order.STATE_PAY) || order.getState().equals(Order.STATE_PRIVATE_PAY) || order.getState().equals(Order.STATE_DISTRIBUTION_ING)){
					//已在线支付或者线下支付、或者配送中， 那应该就是要确认收货
					//更改订单状态,变为确认收货
					int row = sqlservice.executeSql("UPDATE shop_order SET state = '"+Order.STATE_RECEIVE_GOODS+"', version = version+1 WHERE id = "+order.getId()+" AND version = "+order.getVersion());
					if(row == 0){
						continue;
					}
					
					//订单状态改变记录
					OrderStateLog stateLog = new OrderStateLog();
					stateLog.setAddtime(DateUtil.timeForUnix10());
					stateLog.setState(Order.STATE_RECEIVE_GOODS);
					stateLog.setOrderid(order.getId());
					sqlservice.save(stateLog);
				}else{
					//其他状态
				}
			}else{
				//状态不符合，那么就是这个状态已经处理过了，忽略，同时删除此订单超时记录 order_timeout
				sqlservice.delete(orderTimeout);
			}
		}
	}
	
}