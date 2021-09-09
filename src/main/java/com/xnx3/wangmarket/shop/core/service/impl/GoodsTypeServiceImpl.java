package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.service.GoodsTypeService;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
	@Resource
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public List<GoodsType> getGoodsType(int storeid) {
		if(storeid < 1){
			return new ArrayList<GoodsType>();
		}
		String key = Global.CACHE_KEY_STORE_GOODSTYPE.replace("{storeid}", storeid+"");
		List<GoodsType> beanList = (List<GoodsType>)CacheUtil.get(key);
		
		//如果没有，从缓存中取
		if(beanList == null){
			beanList = sqlDAO.findBySqlQuery("SELECT * FROM shop_goods_type WHERE storeid = "+storeid+" AND isdelete = 0", GoodsType.class);
			CacheUtil.set(key, beanList, CacheUtil.EXPIRETIME);
		}
		
		return beanList;
	}

	@Override
	public void clearCache(int storeid) {
		String key = Global.CACHE_KEY_STORE_GOODSTYPE.replace("{storeid}", storeid+"");
		CacheUtil.delete(key);
	}

	
	
}
