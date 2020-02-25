package com.xnx3.wangmarket.shop.api.util;

import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.api.vo.CartVO;

/**
 * 商城相关的sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//微信用户登录后，用户关注公众号之后的openid、unionid等跟微信相关的信息
	public static final String PLUGIN_NAME_SHOP_CART = "wangmarket_shop_cart";
	//当前用户登录后，根据登录的来源是哪个店铺，将该用户归结于哪个店铺下，当用户调取如商品分类、banner等时，都会只取这个store店铺下的信息。这里便是记录当前用户登录后是关联的哪个店铺
	public static final String PLUGIN_NAME_SHOP_USER_PARENT_STORE = "wangmarket_shop_user_parent_store";
	
	/**
	 * 获取当前登录用户的购物车数据。若是不存在，则返回null
	 * 注意，直接使用此方法慎重！请使用 CartService.getCart()，会先从session取，也就是这个方法取，如果没有的话再从数据库、redis中取长期缓存的
	 * @return 如果session中没有，会返回null
	 */
	public static CartVO getCart(){
		return getPlugin(PLUGIN_NAME_SHOP_CART);
	}
	
	/**
	 * 设置当前登录用户相关的微信方面信息。
	 * 注意，禁止直接使用，请使用 CartService.setCart()，一方面能设置session，还可以将信息保存到数据库长久缓存
	 * @param cartVO {@link CartVO} 当前登录用户相关的微信方面信息
	 */
	public static void setCart(CartVO cartVO){
		setPlugin(PLUGIN_NAME_SHOP_CART, cartVO);
	}
	
	public static Store getStore(){
		return getPlugin(PLUGIN_NAME_SHOP_USER_PARENT_STORE);
	}
	
	public static void setStore(Store store){
		setPlugin(PLUGIN_NAME_SHOP_USER_PARENT_STORE, store);
	}
}

