package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.wangmarket.shop.core.entity.StoreData;

/**
 * 店铺相关
 * @author 管雷鸣
 *
 */
public interface StoreService {
	
	/**
	 * 从缓存中获取店铺的变长表信息
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的是哪个商城的
	 * @return 当前店铺的 {@link StoreData} 如果返回null，则是这个店铺没有 {@link StoreData}信息
	 */
	public StoreData getStoreDataByCache(int storeid);
	
	/**
	 * 清理掉某个店铺的变产表的缓存
	 * @param storeid 要清理的是哪个店铺
	 */
	public void clearStoreDataCache(int storeid);
}