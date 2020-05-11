package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.PaySetService;

@Service
public class PaySetServiceImpl implements PaySetService {
	@Resource
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public PaySet getPaySet(int storeid) {
		PaySet paySet;
		if(storeid < 0){
			paySet = new PaySet();
			return paySet;
		}
		
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_PAY_SER.replace("{storeid}", storeid+"");
		paySet = (PaySet)CacheUtil.get(key);
		
		if(paySet == null){
			//缓存中不存在，去数据库取数据
			paySet = sqlDAO.findById(PaySet.class, storeid);
			if(paySet != null){
				//数据库中有这个数据，取出这个数据来了，那么将至加入缓存
				setPaySet(paySet);
			}
		}
		if(paySet == null){
			//如果依旧还是null，那可能这个数据就是不存在的，new一个新对象
			paySet = new PaySet();
		}
		
		return paySet;
	}

	@Override
	public void setPaySet(PaySet paySet) {
		if(paySet == null){
			return;
		}
		if(paySet.getId() == null){
			return;
		}
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_PAY_SER.replace("{storeid}", paySet.getId()+"");
		CacheUtil.set(key, paySet);
	}

	@Override
	public PaySet getSerivceProviderPaySet() {
		int storeid = 0;	//服务商的payset.id = 0
		return getPaySet(storeid);
	}
	
	
	
}
