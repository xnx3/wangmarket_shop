package com.xnx3.wangmarket.plugin.limitbuy;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.impl.SqlCacheServiceImpl;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyStore;
import com.xnx3.wangmarket.shop.core.bean.BuyGoods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderCreateInterface;
import java.util.List;

/**
 * 限量购买。这里是针对整个商城所有商品的限购
 * 新用户只可以购买几一次，无论是哪个商品，只有设定的几次购买机会。
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "商城限购",menuHref="/plugin/limitbuy/store/index.jsp", applyToCMS=true, intro="新用户只可以购买一次，无论是哪个商品，只有1次购买机会。", version="1.0", versionMin="1.0")
public class Plugin implements OrderCreateInterface{
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
		int limitNumber = limitBuyStore.getLimitNumber();
		
		//当前这个用户的订单，是否已经不是首次下单了
		int cacheNumber = sqlService.count("shop_order", "WHERE userid = "+user.getId()+" AND storeid = "+store.getId()+" AND state != '"+Order.STATE_MY_CANCEL+"' AND state != '"+Order.STATE_PAYTIMEOUT_CANCEL+"'");
		if(cacheNumber >= limitNumber){
			//如果当前订单的数量大于等于限额的数量，那这个订单肯定就不能下了
			return BaseVO.failure("每人只能下单"+limitNumber+"次。如果你之前未支付退出了，可以等待半小时后再试");
		}
		
		//其他情况都返回成功，通过，可以创建订单
		return BaseVO.success("");
	}

	@Override
	public void orderCreateAfter(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user,
			Store store) {
	}

	
}