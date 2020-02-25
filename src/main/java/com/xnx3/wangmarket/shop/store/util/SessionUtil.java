package com.xnx3.wangmarket.shop.store.util;

import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.api.vo.CartVO;

/**
 * sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//商家登录后，存储的商家信息
	public static final String PLUGIN_NAME_SHOP_STOREADMIN_STORE = "wangmarket_shop_storeadmin_store";
	
	/**
	 * 获取当前登录商户的Store对象信息。一定不为null，因为商家在登录的时候就会取出用户信息存入session
	 */
	public static Store getStore(){
		return getPlugin(PLUGIN_NAME_SHOP_STOREADMIN_STORE);
	}
	
	/**
	 * 设置当前登录用户相关的微信方面信息。
	 * 注意，禁止直接使用，请使用 CartService.setCart()，一方面能设置session，还可以将信息保存到数据库长久缓存
	 * @param cartVO {@link CartVO} 当前登录用户相关的微信方面信息
	 */
	public static void setStore(Store store){
		setPlugin(PLUGIN_NAME_SHOP_STOREADMIN_STORE, store);
	}
	
}

