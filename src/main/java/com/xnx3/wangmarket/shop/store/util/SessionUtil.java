package com.xnx3.wangmarket.shop.store.util;

import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import java.util.Map;

/**
 * sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//商家登录后，存储的商家信息
	public static final String PLUGIN_NAME_SHOP_STOREADMIN_STORE = "wangmarket.shop.store.data";
	//商家管理后台的左侧菜单使用权限，只限商家用户有效。key: id，也就是左侧菜单的唯一id标示，比如模版管理是template，生成整站是 shengchengzhengzhan， 至于value，无意义，1即可
	public static final String PLUGIN_NAME_SHOP_STORE_MENU_ROLE = "wangmarket.shop.store.menuRole";
	//shop_store_user 表的信息
	public static final String PLUGIN_NAME_SHOP_STORE_USER = "wangmarket.shop.store.childUser";
	
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
	
	/**
	 * 管理后台的左侧菜单使用权限
	 * @return key: id，也就是左侧菜单的唯一id标示，比如模版管理是template，生成整站是 shengchengzhengzhan， 至于value，无意义，1即可
	 */
	public static Map<String, String> getStoreMenuRole(){
		return getPlugin(PLUGIN_NAME_SHOP_STORE_MENU_ROLE);
	}
	
	/**
	 * 管理后台的左侧菜单使用权限
	 * @param map key: id，也就是左侧菜单的唯一id标示，比如模版管理是template，生成整站是 shengchengzhengzhan， 至于value，无意义，1即可
	 */
	public static void setStoreMenuRole(Map<String, String> map){
		setPlugin(PLUGIN_NAME_SHOP_STORE_MENU_ROLE, map);
	}
	

	/**
	 * 获取当前用户登陆的商家信息。若是不存在，则返回null
	 * @return
	 */
	public static StoreUser getStoreUser(){
		return getPlugin(PLUGIN_NAME_SHOP_STORE_USER);
	}
	
	/**
	 * 设置当前登录的商家信息
	 * @param storeUser {@link Site}当前登录用户所管理的站点信息
	 */
	public static void setStoreUser(StoreUser storeUser){
		setPlugin(PLUGIN_NAME_SHOP_STORE_USER, storeUser);
	}
	
}

