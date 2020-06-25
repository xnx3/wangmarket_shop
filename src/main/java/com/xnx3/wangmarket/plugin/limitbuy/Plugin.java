package com.xnx3.wangmarket.plugin.limitbuy;

import java.util.List;
import com.xnx3.BaseVO;
import com.xnx3.CacheUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.impl.SqlCacheServiceImpl;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyStore;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyUser;
import com.xnx3.wangmarket.shop.core.bean.BuyGoods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderCreateInterface;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderPayFinishInterface;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderReceiveGoodsInterface;
import com.xnx3.wangmarket.shop.core.service.OrderService;
import com.xnx3.wangmarket.shop.core.service.impl.OrderServiceImpl;

/**
 * 限量购买。这里是针对整个商城所有商品的限购
 * 新用户只可以购买几一次，无论是哪个商品，只有设定的几次购买机会。
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "商城限购",menuHref="/plugin/limitbuy/store/index.do", applyToCMS=true, intro="新用户只可以购买一次，无论是哪个商品，只有1次购买机会。", version="1.0", versionMin="1.0")
public class Plugin implements OrderCreateInterface, OrderPayFinishInterface, OrderReceiveGoodsInterface{
	//用户在店铺的订单数（除了未付款的订单之外的）
	public final static String CACHE_KEY = "shop:plugin:limitbuy:{storeid}:{userid}:ordernumber";
	//用户在某个店铺下的这个订单是否是首单， "NO"  不是 ，缓存中为空，则可能是，需要再用sql查订单表
	public static final String CACHE_KEY_FINISH_COUNT = "shop:plugin:limitbuy:{storeid}:{userid}:isfirstorder";
	
	@Override
	public BaseVO orderCreateBefore(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store) {
		//判断此用户是否已经有过消费
		
		SqlCacheService sqlCacheService = SpringUtil.getBean(SqlCacheServiceImpl.class);
		
		//从店铺设置里取当前店铺是否开启了这个规则
		LimitBuyStore limitBuyStore = sqlCacheService.findById(LimitBuyStore.class, store.getId());
		if(limitBuyStore == null){
			//店铺也没设置，那么直接退出就好了
			return BaseVO.success("");
		}
		if(limitBuyStore.getIsUse() - LimitBuyStore.IS_USE_NO == 0){
			//店铺设置的是不使用这个规则，那么直接退出
			return BaseVO.success("");
		}
		
		SqlService sqlService = SpringUtil.getSqlService();
		
		//缓存的key
		String key = CACHE_KEY.replace("{userid}", user.getId()+"").replace("{storeid}", store.getId()+"");
		
		//这个用户当前在店铺里面的限额数量是多少
		int limitNumber = 0;
		//查出这个用户限额是多少
		LimitBuyUser limitBuyUser = sqlCacheService.findById(LimitBuyUser.class, user.getId()+"_"+store.getId());
		if(limitBuyUser != null && limitBuyUser.getLimitNumber() != null){
			limitNumber = limitBuyUser.getLimitNumber();
		}else{
			limitNumber = limitBuyStore.getLimitNumber();
		}
		
		//当前这个用户的订单，是否已经不是首次下单了
		int cacheNumber = -1;	//缓存的条数
		Object cacheNumberObject = CacheUtil.get(key);
		if(cacheNumberObject != null){
			cacheNumber = (int) cacheNumberObject;
		}
		if(cacheNumber > -1){
			//缓存中有数值了
		}else{
			//缓存中还没有数值,需要从数据库查询
			cacheNumber = sqlService.count("shop_order", "WHERE userid = "+user.getId()+" AND storeid = "+store.getId()+" AND (state != '"+Order.STATE_MY_CANCEL+"' OR state != '"+Order.STATE_PAYTIMEOUT_CANCEL+"')");
			//将数据缓存
			CacheUtil.set(key, cacheNumber);
		}
		
		if(cacheNumber >= limitNumber){
			//如果当前订单的数量大于等于限额的数量，那这个订单肯定就不能下了
			return BaseVO.failure("您只能下单"+limitNumber+"次。如果想增加购买次数，请分享给你朋友吧，你朋友通过你的分享进来，首次消费成功，你就可以获得一次继续购买的机会。");
		}
		
		//其他情况都返回成功，通过，可以创建订单
		return BaseVO.success("");
	}

	@Override
	public void orderCreateAfter(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store) {
		
	}

	@Override
	public void orderPayFinish(Order order) {
		//用户支付完成，则缓存的用户有效订单数+1
		
		//缓存的key
		String key = CACHE_KEY.replace("{userid}", order.getUserid()+"").replace("{storeid}", order.getStoreid()+"");
		
		int cacheNumber = -1;	//缓存的条数
		Object cacheNumberObject = CacheUtil.get(key);
		if(cacheNumberObject != null){
			cacheNumber = (int) cacheNumberObject;
		}
		if(cacheNumber > -1){
			//缓存中有数值了
		}else{
			//缓存中还没有数值,需要从数据库查询
			SqlService sqlService = SpringUtil.getSqlService();
			cacheNumber = sqlService.count("shop_order", "WHERE userid = "+order.getUserid()+" AND storeid = "+order.getStoreid()+" AND (state != '"+Order.STATE_MY_CANCEL+"' OR state != '"+Order.STATE_PAYTIMEOUT_CANCEL+"')");
		}
		
		//将数据缓存
		CacheUtil.set(key, cacheNumber+1);
		ConsoleUtil.log("set number : "+(cacheNumber+1));
	}

	@Override
	public void orderReceiveGoodsFinish(Order order) {
		SqlCacheService sqlCacheService = SpringUtil.getBean(SqlCacheServiceImpl.class);
		
		//获取到这个用户的上级推荐人信息
		StoreUser storeUser = sqlCacheService.findById(StoreUser.class, order.getUserid()+"_"+order.getStoreid());
		if(storeUser == null || storeUser.getReferrerid() == null || storeUser.getReferrerid().length() == 0){
			//没有上级推荐人，那直接退出就行了
			return;
		}
		//有上级推荐人，取出上级推荐人信息
		StoreUser parentStoreUser = sqlCacheService.findById(StoreUser.class, storeUser.getReferrerid());
		if(parentStoreUser == null || parentStoreUser.getUserid() == null){
			//上级推荐人信息不存在，那么也退出
			return;
		}
		
		//取出这个店铺的设置，比如是否启用这个插件
		LimitBuyStore limitBuyStore = sqlCacheService.findById(LimitBuyStore.class, order.getStoreid());
		if(limitBuyStore == null){
			//店铺也没设置，那么直接退出
			return;
		}
		if(limitBuyStore.getIsUse() - LimitBuyStore.IS_USE_NO == 0){
			//店铺设置的是不使用这个规则，那么直接退出
			return;
		}
		
		SqlService sqlService = SpringUtil.getSqlService();
		
		String key = CACHE_KEY_FINISH_COUNT.replace("{storeid}", order.getStoreid()+"").replace("{userid}", order.getUserid()+"");
		Object cacheObj = CacheUtil.get(key);
		if(cacheObj == null){
			//没有缓存，那么从数据库读数据
			OrderService orderService = SpringUtil.getBean(OrderServiceImpl.class);
			int count = orderService.getFinishOrderCount(order.getUserid(), order.getStoreid());
			if(count - 1 == 0){
				//是第一次，那么为他的上级增加可购买次数
				ConsoleUtil.log("增加上级的购买次数1次！当前完成的订单:"+order.toString());
				LimitBuyUser limitBuyUser = sqlService.findById(LimitBuyUser.class, parentStoreUser.getId());
				if(limitBuyUser == null){
					//为空，那么还要从这个商铺中，取出默认用户可以使用的次数
					limitBuyUser = new LimitBuyUser();
					limitBuyUser.setId(parentStoreUser.getId());
					limitBuyUser.setLimitNumber(limitBuyStore.getLimitNumber());
					limitBuyUser.setStoreid(order.getStoreid());
					limitBuyUser.setUseNumber(0);
					limitBuyUser.setUserid(parentStoreUser.getUserid());
				}
				limitBuyUser.setLimitNumber(limitBuyUser.getLimitNumber()+1);
				sqlService.save(limitBuyUser);
				//清除 limitByUser 的缓存
				sqlCacheService.deleteCacheById(LimitBuyUser.class, limitBuyUser.getId());
			}
			
			//将当前下订单这个用户加入缓存，标注已经有过消费了，后面不用再查他的有效订单数的数据表了
			CacheUtil.setYearCache(key, count);
		}
	}
	
}