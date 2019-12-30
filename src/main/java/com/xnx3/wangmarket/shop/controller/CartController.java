package com.xnx3.wangmarket.shop.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Goods;
import com.xnx3.wangmarket.shop.service.CartService;
import com.xnx3.wangmarket.shop.vo.CartVO;
import com.xnx3.wangmarket.shop.vo.StoreCartVO;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

/**
 * 商城相关功能
 * @author 管雷鸣
 */
@Controller(value="ShopCartController")
@RequestMapping("/shop")
public class CartController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	@Resource
	private CartService cartService;
	
	/**
	 * 操作购物车中的商品
	 * @param goodsid 要修改的购物车中的商品编号，必传，对应 {@link Goods}.id
	 * @param changeNumber 要修改购物车中对应商品编号的商品，是增加，还是减少，这里便是增加或者减少的数值。
	 * 				<ul>
	 * 					<li>正数，增加
	 * 					<li>负数，减少。最多能减少到0，如果到0，也就是将这个商品从购物车中清除掉了。例如，想要清除掉某个商品，那么可以传入 -99999
	 * 				</ul>
	 * @return {@link CartVO}购物车记录
	 */
	@RequestMapping("cart${url.suffix}")
	@ResponseBody
	public CartVO cart(HttpServletRequest request,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "changeNumber", required = false, defaultValue="0") int changeNumber){
		//购物车的数据存在于Session中
		CartVO cartVO = cartService.cart(goodsid, changeNumber);
		return cartVO;
	}
	
	/**
	 * 获取购物车数据
	 * <br/>若storeid、areaid两者为0或者不传，则获取整个购物车内所有店铺的购物车数据
	 * @param storeid 要获取的哪个店铺的购物车数据
	 * @return 
	 */
	@RequestMapping("getStoreCart${url.suffix}")
	@ResponseBody
	public StoreCartVO getStoreCart(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		StoreCartVO vo = new StoreCartVO();
		StoreCart storeCart = cartService.getStoreCart(storeid);
		vo.setStoreCart(storeCart);
		return vo;
	}
	
	/**
	 * 清空购物车数据
	 * @param storeid 要清空的哪个店铺的购物车数据
	 */
	@RequestMapping("clearShopCart${url.suffix}")
	@ResponseBody
	public BaseVO clearShopCart(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		return cartService.clearStoreCart(storeid);
	}
}