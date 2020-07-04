package com.xnx3.wangmarket.shop.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage.OrderReceiveGoodsPluginManage;
import com.xnx3.wangmarket.shop.core.service.OrderService;
import com.xnx3.wangmarket.shop.core.vo.OrderVO;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	@Resource
	private SqlService sqlService;

	@Override
	public BaseVO orderCancelGoodsNumberChange(Order order) {
		BaseVO vo = new BaseVO();
		if(order == null){
			vo.setBaseVO(BaseVO.FAILURE, "订单不存在");
			return vo;
		}
		//根据订单，查询出这个订单下有哪些商品
		List<OrderGoods> orderGoodsList = sqlService.findByProperty(OrderGoods.class, "orderid", order.getId());
		for (int i = 0; i < orderGoodsList.size(); i++) {
			OrderGoods orderGoods = orderGoodsList.get(i);
			
			/*
			 * 这里是直接在for循环中读数据库，有危险。后续待改进
			 */
			Goods goods = sqlService.findById(Goods.class, orderGoods.getGoodsid());
			//库存
			goods.setInventory(goods.getInventory() + orderGoods.getNumber());
			//总的销量，已售数量
			goods.setSale(goods.getSale() - orderGoods.getNumber());
			sqlService.save(goods);
		}
		
		return vo;
	}

	@Override
	public OrderVO receiveGoods(Order order) {
		OrderVO vo = new OrderVO();
		
		if(order == null) {
			vo.setBaseVO(OrderVO.FAILURE, "订单不存在");
			return vo;
		}
		//判断订单状态是否允许变为已确认收货。 已支付、线下支付、配送中 这两种状态可以变为确认收货
		if(order.getState().equals(Order.STATE_PAY) || order.getState().equals(Order.STATE_PRIVATE_PAY) || order.getState().equals(Order.STATE_DISTRIBUTION_ING)){
			//正常，符合状态改变
		}else{
			//异常
			vo.setBaseVO(OrderVO.FAILURE, "订单状态异常");
			return vo;
		}
		
		order.setState(Order.STATE_RECEIVE_GOODS);
		sqlService.save(order);
		vo.setOrder(order);
		
		//订单状态改变记录
		OrderStateLog stateLog = new OrderStateLog();
		stateLog.setAddtime(DateUtil.timeForUnix10());
		stateLog.setState(order.getState());
		stateLog.setOrderid(order.getId());
		sqlService.save(stateLog);
		
		//功能插件
		try {
			OrderReceiveGoodsPluginManage.orderReceiveGoodsFinish(order);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return vo;
	}

	@Override
	public int getFinishOrderCount(int userid, int storeid) {
		int count = sqlService.count("shop_order", "WHERE userid = "+userid +" AND storeid = "+storeid+" AND (state = '"+Order.STATE_RECEIVE_GOODS+"' OR state = '"+Order.STATE_FINISH+"') AND pay_money > 0");
		ConsoleUtil.debug("userid:"+userid+", storeid:"+storeid+",  --> "+count);
		return count;
	}
	
}
