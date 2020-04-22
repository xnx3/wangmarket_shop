package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.service.GoodsTypeService;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsTypeBean;

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
	public List<GoodsTypeBean> getGoodsType(int storeid) {
		if(storeid < 1){
			return new ArrayList<GoodsTypeBean>();
		}
		String key = Global.CACHE_KEY_STORE_GOODSTYPE.replace("{storeid}", storeid+"");
		List<GoodsTypeBean> beanList = (List<GoodsTypeBean>)CacheUtil.get(key);
		if(beanList == null){
			beanList = new ArrayList<GoodsTypeBean>();
			List<GoodsType> typeList = sqlDAO.findBySqlQuery("SELECT * FROM shop_goods_type WHERE storeid = "+storeid, GoodsType.class);
			for (int i = 0; i < typeList.size(); i++) {
				beanList.add(new GoodsTypeBean(typeList.get(i)));
			}
		}
		CacheUtil.set(key, beanList, CacheUtil.EXPIRETIME);
		
		return beanList;
	}

	@Override
	public void clearCache(int storeid) {
		String key = Global.CACHE_KEY_STORE_GOODSTYPE.replace("{storeid}", storeid+"");
		CacheUtil.delete(key);
	}

	
	
}
