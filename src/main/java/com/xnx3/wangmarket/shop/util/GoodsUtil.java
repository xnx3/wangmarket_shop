package com.xnx3.wangmarket.shop.util;

import com.xnx3.wangmarket.shop.entity.Goods;

/**
 * 商品相关
 * @author 管雷鸣
 *
 */
public class GoodsUtil {
	
	/**
	 * 根据 {@link Goods} 来判断用户购物车中的商品当前是否是已上架状态
	 * <ul>
	 * 		<li>未设置定时上架时间，未设置定时下架时间，那么以goods.putaway为准</li>
	 * 		<li>已设置定时上架时间，未设置定时下架时间，那么定时上架时间之前商品是未上架状态，定时上架时间之后商品便是已上架状态</li>
	 * 		<li>未设置定时上架时间，已设置定时下架时间，那么定时下架时间之前商品是已上架状态，定时下架时间之后商品便是已下架状态</li>
	 * 		<li>已设置定时上架时间，已设置定时下架时间，那么定时上架时间之前商品是未上架状态，定时上架时间～定时下架时间之间为上架状态，定时下架时间之后商品便是已下架状态</li>
	 * </ul>
	 * @param goods {@link Goods}
	 * @return true已上架，false已下架
	 */
	public static boolean isPutaway(Goods goods){
		if(goods == null){
			return false;
		}
		
		if(goods.getPutaway() - Goods.PUTAWAY_SELL == 0){
			//正常出售中
			return true;
		}else{
			//商品已下架
			return false;
		}
	}
	
}
