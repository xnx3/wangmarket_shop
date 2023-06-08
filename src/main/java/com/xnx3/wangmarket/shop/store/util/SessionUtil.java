package com.xnx3.wangmarket.shop.store.util;

import com.xnx3.j2ee.bean.ActiveUser;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreChildUser;
import java.util.Map;

import org.springframework.beans.BeanUtils;

/**
 * sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//商家登录后，存储的商家信息
	public static final String PLUGIN_NAME_SHOP_STOREADMIN_STORE = "shop:store:data";
	//商家管理后台的左侧菜单使用权限，只限商家用户有效。key: id，也就是左侧菜单的唯一id标示，比如模版管理是template，生成整站是 shengchengzhengzhan， 至于value，无意义，1即可
	public static final String PLUGIN_NAME_SHOP_STORE_MENU_ROLE = "shop:store:menuRole";
	//shop_store_user 表的信息
	public static final String PLUGIN_NAME_SHOP_STORE_USER = "shop:store:childUser";
	//这个store商家，上级的token（64位token）
	public static final String PLUGIN_NAME_SHOP_STORE_PARENT_TOKRN = "shop:store:parent:token";
	
	/**
	 * 获取当前登录商户的Store对象信息。一定不为null，因为商家在登录的时候就会取出用户信息存入session
	 */
	public static Store getStore(){
		Object obj = getPlugin(PLUGIN_NAME_SHOP_STOREADMIN_STORE);
		
		if (obj instanceof Store) {
			return (Store) obj;
		} else {
			Store store = new Store();
			BeanUtils.copyProperties(obj, store);
			ConsoleUtil.debug("fuzhi -- "+store.toString());
			return store;
		}
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
	public static StoreChildUser getStoreUser(){
		return getPlugin(PLUGIN_NAME_SHOP_STORE_USER);
	}
	
	/**
	 * 设置当前登录的商家信息
	 * @param storeUser {@link Site}当前登录用户所管理的站点信息
	 */
	public static void setStoreUser(StoreChildUser storeUser){
		setPlugin(PLUGIN_NAME_SHOP_STORE_USER, storeUser);
	}
	
	/**
	 * 设置当前登录的商家的上级token信息
	 * @param token 此商家的上级的token， 64位字符串
	 */
	public static void setParentToken(String token){
		setPlugin(PLUGIN_NAME_SHOP_STORE_PARENT_TOKRN, token);
	}
	
	/**
	 * 获取当前登录的商家的上级token信息
	 * @return 64位的token字符串，如果为null或者长度不等于64位，那么该商家没有上级，也就是不是通过三方接口的，是属于直接通过登录后台登录进来的，而非嵌入第三方系统中是人家子页面
	 */
	public static String getParentToken(){
		return getPlugin(PLUGIN_NAME_SHOP_STORE_PARENT_TOKRN);
	}
	
	
}

