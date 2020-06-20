package com.xnx3.wangmarket.plugin.limitbuy;

import java.util.List;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.impl.SqlCacheServiceImpl;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyStore;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyUser;
import com.xnx3.wangmarket.shop.core.bean.BuyGoods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderCreateInterface;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderPayFinishInterface;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.RegInterface;

/**
 * 限量购买。这里是针对整个商城所有商品的限购
 * 新用户只可以购买几一次，无论是哪个商品，只有设定的几次购买机会。
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "商城限购",menuHref="/plugin/limitbuy/store/index.do", applyToCMS=true, intro="新用户只可以购买一次，无论是哪个商品，只有1次购买机会。", version="1.0", versionMin="1.0")
public class Plugin implements OrderCreateInterface, RegInterface, OrderPayFinishInterface{
	//用户在店铺的订单数（除了未付款的订单之外的）
	public final static String CACHE_KEY = "shop:plugin:sell:{storeid}:{userid}:ordernumber";
	
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
	public void regFinish(User user, Store store) {
		ConsoleUtil.log("user:"+user.toString());
		ConsoleUtil.log("store:"+store.toString());
		
		int storeid = 0;
		if(store != null && store.getId() != null){
			storeid = store.getId();
		}
		
		//新用户注册成功后，要清除其上级的订单数缓存
		if(user.getReferrerid() != null && user.getReferrerid() > 0){
			//有上级推荐人，那么吧上级的可下单次数增加
			
			SqlCacheService sqlCacheService = SpringUtil.getBean(SqlCacheServiceImpl.class);
			SqlService sqlService = SpringUtil.getSqlService();
			LimitBuyStore limitBuyStore = sqlCacheService.findById(LimitBuyStore.class, storeid);
			if(limitBuyStore == null){
				//店铺也没设置，那么直接退出
				return;
			}
			if(limitBuyStore.getIsUse() - LimitBuyStore.IS_USE_NO == 0){
				//店铺设置的是不使用这个规则，那么直接退出
				return;
			}
			
			//查出这个用户限额是多少，进行增加
			LimitBuyUser limitBuyUser = sqlService.findById(LimitBuyUser.class, user.getId()+"_"+store.getId());
			if(limitBuyUser != null){
				limitBuyUser.setUseNumber(limitBuyUser.getUseNumber()+1);
			}else{
				limitBuyUser = new LimitBuyUser();
				limitBuyUser.setLimitNumber(limitBuyStore.getLimitNumber());
				limitBuyUser.setStoreid(storeid);
				limitBuyUser.setUseNumber(0);
				limitBuyUser.setUserid(user.getId());
			}
			sqlService.save(limitBuyUser);
			//清理limitBuyUser 的 cache缓存
			sqlCacheService.deleteCacheById(LimitBuyUser.class, user.getId()+"_"+store.getId());
		}
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
	
	
}