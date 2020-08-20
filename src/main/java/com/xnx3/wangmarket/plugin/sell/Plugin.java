package com.xnx3.wangmarket.plugin.sell;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.impl.SqlCacheServiceImpl;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderReceiveGoodsInterface;

/**
 * 二级分销，推荐有收入。这里以用户实际支付的订单金额，根据其百分比来计算佣金收入
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "二级分销",menuHref="/plugin/sell/store/index.jsp", applyToCMS=true, intro="A用户推荐新用户B注册，B消费后，A有收入。B推荐C注册，C消费后，B跟A都有收入。", version="1.0", versionMin="1.0")
public class Plugin implements OrderReceiveGoodsInterface{

	@Override
	public void orderReceiveGoodsFinish(Order order) {
		SqlCacheService sqlCacheService = SpringUtil.getBean(SqlCacheServiceImpl.class);
		
		//判断这个店铺是否开启了分销
		SellStoreSet storeSet = sqlCacheService.findById(SellStoreSet.class, order.getStoreid());
		if(storeSet == null || storeSet.getIsUse() == null || storeSet.getIsUse() - SellStoreSet.IS_USE_NO == 0){
			//这个商家不使用分销功能
			return;
		}
		
		//判断这个下单成功的用户是否是有推荐人
		StoreUser storeUser = sqlCacheService.findById(StoreUser.class, order.getUserid()+"_"+order.getStoreid());
		if(storeUser == null){
			//既然能下单，这个显然是不应该的，但是也判断一下
			ConsoleUtil.error("订单的下单用户不存在："+order.toString());
			return;
		}
		if(storeUser.getReferrerid() == null || storeUser.getReferrerid().length() < 1){
			//当前消费订单，没有上级推荐人，那么就不需要分佣了
			return;
		}
		
		//有一级推荐人，也就是直属上级，查出信息
		StoreUser parentFirstStoreUser = sqlCacheService.findById(StoreUser.class, storeUser.getReferrerid());
		if(parentFirstStoreUser == null){
			return;
		}
		/* 有推荐人，就要开始分佣了 */
		SqlService sqlService = SpringUtil.getSqlService();
		int currentTime = DateUtil.timeForUnix10();
		
		//一级上级
		if(storeSet.getFirstCommission() != null && storeSet.getFirstCommission() > 0){
			//只有商家后台设置了一级有分佣，那才进行记录
			SellCommissionLog firstLog = new SellCommissionLog();
			firstLog.setAddtime(currentTime);
			firstLog.setOrderid(order.getId());
			firstLog.setStoreid(order.getStoreid());
			firstLog.setUserid(parentFirstStoreUser.getUserid());
			firstLog.setMoney(order.getPayMoney() * storeSet.getFirstCommission() / 100);
			if(firstLog.getMoney() > 0){
				//金额大于0，才有必要存入数据库
				sqlService.save(firstLog);
			}
		}
		
		
		//二级上级，查询是否存在
		if(parentFirstStoreUser.getReferrerid() == null || parentFirstStoreUser.getReferrerid().length() < 1){
			//没有二级上级，退出
			return;
		}
		StoreUser parentTwoStoreUser = sqlCacheService.findById(StoreUser.class, parentFirstStoreUser.getReferrerid());
		if(parentTwoStoreUser == null){
			//没二级上级，也退出。
			return;
		}
		//有二级上级，计算佣金
		if(storeSet.getFirstCommission() != null && storeSet.getFirstCommission() > 0){
			//只有商家后台设置了一级有分佣，那才进行记录
			SellCommissionLog twoLog = new SellCommissionLog();
			twoLog.setAddtime(currentTime);
			twoLog.setOrderid(order.getId());
			twoLog.setStoreid(order.getStoreid());
			twoLog.setUserid(parentTwoStoreUser.getUserid());
			twoLog.setMoney(order.getPayMoney() * storeSet.getTwoCommission() / 100);
			if(twoLog.getMoney() > 0){
				//金额大于0，才有必要存入数据库
				sqlService.save(twoLog);
			}
		}
	}
	
}