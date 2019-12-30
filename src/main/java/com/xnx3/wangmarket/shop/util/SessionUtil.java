package com.xnx3.wangmarket.shop.util;

import com.xnx3.wangmarket.shop.vo.CartVO;

/**
 * 商城相关的sessison
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.j2ee.util.SessionUtil{
	//微信用户登录后，用户关注公众号之后的openid、unionid等跟微信相关的信息
	public static final String PLUGIN_NAME_SHOP_CART = "wangmarket_shop_cart";
	
	/**
	 * 获取当前登录用户的购物车数据。若是不存在，则返回null
	 */
	public static CartVO getCart(){
		return getPlugin(PLUGIN_NAME_SHOP_CART);
	}
	
	/**
	 * 设置当前登录用户相关的微信方面信息
	 * @param cartVO {@link CartVO} 当前登录用户相关的微信方面信息
	 */
	public static void setCart(CartVO cartVO){
		setPlugin(PLUGIN_NAME_SHOP_CART, cartVO);
	}
	
	
	
}

