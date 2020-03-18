package com.xnx3.wangmarket.shop.core.service;

import java.util.List;

import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.entity.Freight;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;

/**
 * 商铺的运费相关
 * @author 管雷鸣
 *
 */
public interface FreightService {
	
	/**
	 * 获取某个商铺的运费设置
	 * 获取顺序，首先从缓存中获取，缓存中没有再从数据库中拉数据进行缓存
	 * @param storeid 要获取的运费设置是哪个商城的
	 * @return 当前店铺的运费规则。都会无论是否设置，都会返回一个 arrayList
	 */
	public List<Freight> getStoreFreight(int storeid);
	
	/**
	 * 刷新某个商城的运费缓存，redis、或者map缓存
	 * @param storeid 要刷新的运费缓存是哪个商城的
	 */
	public void refreshStoreFreightCache(int storeid);
}