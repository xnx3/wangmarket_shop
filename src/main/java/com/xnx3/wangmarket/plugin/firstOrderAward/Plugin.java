package com.xnx3.wangmarket.plugin.firstOrderAward;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.impl.SqlCacheServiceImpl;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderReceiveGoodsInterface;
import com.xnx3.wangmarket.shop.core.service.SMSService;
import com.xnx3.wangmarket.shop.core.service.impl.SMSServiceImpl;

/**
 * 推广送礼，推广后，客户首次下单并成功消费，推广人可以免费得到某件商品
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "推广送礼",menuHref="/plugin/firstOrderAward/store/setAward.jsp", applyToCMS=true, intro="推广后，客户首次下单并成功消费，推广人可以免费得到某件商品", version="1.0", versionMin="1.0")
public class Plugin implements OrderReceiveGoodsInterface{
	//某个用户在某个店铺下，订单完成的条数
	public static final String CACHE_KEY_FINISH_COUNT = "shop:store:{storeid}:userid_{userid}:firstOrderAward:finishcount";
	
	@Override
	public void orderReceiveGoodsFinish(Order order) {
		SqlService sqlService = SpringUtil.getSqlService();
		SqlCacheService sqlCacheService = SpringUtil.getBean(SqlCacheServiceImpl.class);
		SMSService smsService = SpringUtil.getBean(SMSServiceImpl.class);
		
		//判断这个下单成功的用户是否是有推荐人
		User user = sqlCacheService.findById(User.class, order.getUserid());
		if(user == null){
			//既然能下单，这个显然是不应该的，但是也判断一下
			ConsoleUtil.error("订单的下单用户不存在："+order.toString());
			return;
		}
		if(user.getReferrerid() != null && user.getReferrerid() > 0){
			//这个用户有上级推荐人。那么需要判断一下这个用户当前是第一次成功消费，还是多次了
			//先从缓存中取，缓存中没有，再从数据库中取
			String key = CACHE_KEY_FINISH_COUNT.replace("{storeid}", order.getStoreid()+"").replace("{userid}", order.getUserid()+"");
			Object cacheObj = CacheUtil.get(key);
			int count = 11;	//当前成功订单的次数
			if(cacheObj == null){
				//没有缓存，那么从数据库读数据
				count = sqlService.count("shop_order", "WHERE userid = "+user.getId() +" AND (state = 'receive_goods' OR state = 'finish') AND pay_money > 0");
				if(count > 1){
					//不是第一次了，那么忽略就好，并且加入缓存，避免下次在从数据库查询
					CacheUtil.setYearCache(key, count);
				}else{
					//正常，要给推荐人好处了
					//找出这个商家，给推荐人哪个商品
					Award award = sqlService.findById(Award.class, order.getStoreid());
					if(award != null && award.getIsUse() - Award.IS_USE_YES == 0 && award.getGoodsid() != null && award.getGoodsid() > 0){
						//商家也已经设置了送什么商品了，那么给推荐人自动下单
						//先查询出来，要送给用户的商品是什么
						Goods goods = sqlService.findById(Goods.class, award.getGoodsid());
						if(goods == null){
							//作为奖品的商品不存在
							ActionLogUtil.insertError(null, "推荐新用户首次下单并消费，但给推荐人奖品的时候，奖品不存在！"+order.toString()+","+award.toString());
							return;
						}
						
						//创建新订单
						Order createOrder = new Order();
						createOrder.setAddtime(DateUtil.timeForUnix10());
						createOrder.setPayMoney(0);
						createOrder.setRemark("推荐的用户id"+order.getUserid()+"首次下单并消费完成，这个0元产品是奖励给你的");
						createOrder.setState(Order.STATE_PAY);
						createOrder.setStoreid(order.getStoreid());
						createOrder.setTotalMoney(goods.getPrice());
						createOrder.setUserid(user.getReferrerid());
						createOrder.setVersion(0);
						sqlService.save(createOrder);
						//创建订单-商品的关联，这个订单下有哪些商品
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setGoodsid(goods.getId());
						orderGoods.setPrice(goods.getPrice());
						orderGoods.setTitle(goods.getTitle());
						orderGoods.setTitlepic(goods.getTitlepic());
						orderGoods.setUnits(goods.getUnits());
						orderGoods.setNumber(1);
						orderGoods.setOrderid(createOrder.getId());
						orderGoods.setUserid(createOrder.getUserid());
						sqlService.save(orderGoods);
						//增加商品销量、减去商品库存。 如果用户后面不支付，或者订单退单，那么库存、销量还会再变回来
						sqlService.executeSql("UPDATE shop_goods SET sale = sale + 1, inventory = inventory - 1  WHERE id = "+orderGoods.getGoodsid());
						//订单状态记录
						OrderStateLog stateLog = new OrderStateLog();
						stateLog.setAddtime(DateUtil.timeForUnix10());
						stateLog.setState(order.getState());
						stateLog.setOrderid(order.getId());
						sqlService.save(stateLog);
						//该店铺的已售数量增加，因为店铺下可能会同一时刻产生多个订单，version 乐观锁容易造成下单异常，阻挡正常下单，所以直接进行update 销售数量
						sqlService.executeSql("UPDATE shop_store SET sale = sale + 1 WHERE id = "+order.getStoreid());
						
						//通知邀请人这个好消息
						User parentUser = sqlCacheService.findById(User.class, user.getReferrerid());
						try {
							smsService.send(order.getStoreid(), parentUser.getPhone(), "恭喜您，您推荐的下级用户"+((user.getPhone() != null && user.getPhone().length() == 11) ? user.getPhone():"")+"首单消费完成，您拿到奖励，免费获得【"+goods.getTitle()+"】1"+goods.getUnits()+"。加油，分享给更多的人，他们首次消费完成，你都能得到奖励", "tongzhi", null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//写日志
						ActionLogUtil.insertUpdateDatabase(null, createOrder.getId(), "推荐下级首单消费，奖励产品", order.toString());
					}
				}
			}
		}
	}
	
}