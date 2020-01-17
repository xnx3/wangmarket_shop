package com.xnx3.wangmarket.shop.util;

import com.xnx3.DateUtil;
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
		//是否上架，true上架
		boolean putaway = false;	
		//当前时间戳
		int currentTime = DateUtil.timeForUnix10();	
		
		if(goods.getOnlineCountdown() < 1 && goods.getSoldoutCountdown() < 1){
			//未设置定时上架时间，未设置定时下架时间，那么以goods.putaway为准
			
			if(goods.getPutaway() - Goods.PUTAWAY_SELL == 0){
				//正常出售中
				putaway = true;
			}else{
				//商品已下架
				putaway = false;
			}
		}else if(goods.getOnlineCountdown() > 0 && goods.getSoldoutCountdown() < 1){
			//已设置定时上架时间，未设置定时下架时间，那么定时上架时间之前商品是未上架状态，定时上架时间之后商品便是已上架状态
			
			//判断一下当前时间戳，是否是在上架的时间
			if(goods.getOnlineCountdown() > currentTime){
				//商品设定的定时上架时间，大于当前时间，那么商品当前是已上架状态
				putaway = true;
			}else{
				putaway = false;
			}
		}else if(goods.getOnlineCountdown() < 1 && goods.getSoldoutCountdown() > 0){
			//未设置定时上架时间，已设置定时下架时间，那么定时下架时间之前商品是已上架状态，定时下架时间之后商品便是已下架状态
			
			if(currentTime < goods.getSoldoutCountdown()){
				//当前时间，小于商品设定的定时下架时间，那么商品当前是上架状态
				putaway = true;
			}else{
				putaway = false;
			}
		}else{
			//已设置定时上架时间，已设置定时下架时间，那么定时上架时间之前商品是未上架状态，定时上架时间～定时下架时间之间为上架状态，定时下架时间之后商品便是已下架状态
			
			if(currentTime > goods.getOnlineCountdown() && currentTime < goods.getSoldoutCountdown()){
				//当前时间在 定时上架时间跟定时下架时间之间，那么商品当前状态是上架的
				putaway = true;
			}else{
				putaway = false;
			}
		}
		
		return putaway;
	}
	
}
