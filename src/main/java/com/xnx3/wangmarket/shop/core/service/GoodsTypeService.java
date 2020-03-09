package com.xnx3.wangmarket.shop.core.service;

import java.util.List;
import com.xnx3.wangmarket.shop.api.vo.bean.GoodsTypeBean;

/**
 * 订单状态变动的日志相关
 * @author 管雷鸣
 *
 */
public interface GoodsTypeService {
	
	/**
	 * 获取某个店铺的的分类列表。
	 * 先从CacheUtil缓存中获取，如果缓存中没有，再从数据库中获取
	 * @param storeid 要获取的商品分类，是属于那个店铺的
	 */
	public List<GoodsTypeBean> getGoodsType(int storeid);
	
	/**
	 * 清空缓存中存储的 goodsType 列表 
	 * @param storeid 要清空的商品分类缓存，是属于那个店铺的
	 */
	public void clearCache(int storeid);
}