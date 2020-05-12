package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.wangmarket.shop.core.entity.StoreData;
import com.xnx3.wangmarket.shop.core.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {
	@Resource
	private SqlCacheService sqlCacheSetService;
	
	@Override
	public StoreData getStoreDataByCache(int storeid) {
		return sqlCacheSetService.findById(StoreData.class, storeid);
	}

	@Override
	public void clearStoreDataCache(int storeid) {
		sqlCacheSetService.deleteCacheById(StoreData.class, storeid);
	}
	
}
