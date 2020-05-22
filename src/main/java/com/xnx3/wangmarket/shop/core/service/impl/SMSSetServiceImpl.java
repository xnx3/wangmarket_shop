package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.SMSSetService;

@Service
public class SMSSetServiceImpl implements SMSSetService {
	@Resource
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public SmsSet getSMSSet(int storeid) {
		SmsSet set;
		if(storeid < 0){
			set = new SmsSet();
			return set;
		}
		
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_SMS_SET.replace("{storeid}", storeid+"");
		set = (SmsSet)CacheUtil.get(key);
		
		if(set == null){
			//缓存中不存在，去数据库取数据
			set = sqlDAO.findById(SmsSet.class, storeid);
			if(set != null){
				//数据库中有这个数据，取出这个数据来了，那么将至加入缓存
				setSMSSet(set);
			}
		}
		if(set == null){
			//如果依旧还是null，那可能这个数据就是不存在的，new一个新对象
			set = new SmsSet();
		}
		
		return set;
	}

	@Override
	public void setSMSSet(SmsSet smsSet) {
		if(smsSet == null){
			return;
		}
		if(smsSet.getId() == null){
			return;
		}
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_SMS_SET.replace("{storeid}", smsSet.getId()+"");
		CacheUtil.set(key, smsSet);
	}
	
	
	
}
