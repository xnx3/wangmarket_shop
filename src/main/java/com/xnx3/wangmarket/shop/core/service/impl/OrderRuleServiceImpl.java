package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.service.OrderRuleService;

@Service
public class OrderRuleServiceImpl implements OrderRuleService {
	@Resource
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public OrderRule getRole(int storeid) {
		OrderRule orderRule;
		if(storeid < 1){
			orderRule = new OrderRule();
			return orderRule;
		}
		
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_ORDER_RULE.replace("{storeid}", storeid+"");
		orderRule = (OrderRule)CacheUtil.get(key);
		
		if(orderRule == null){
			//缓存中不存在，去数据库取数据
			orderRule = sqlDAO.findById(OrderRule.class, storeid);
			if(orderRule != null){
				//数据库中有这个数据，取出这个数据来了，那么将至加入缓存
				setRole(orderRule);
			}
		}
		if(orderRule == null){
			//如果依旧还是null，那可能这个数据就是不存在的，new一个新对象
			orderRule = new OrderRule();
		}
		
		return orderRule;
	}

	@Override
	public void setRole(OrderRule orderRule) {
		if(orderRule == null){
			return;
		}
		if(orderRule.getId() == null){
			return;
		}
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_ORDER_RULE.replace("{storeid}", orderRule.getId()+"");
		CacheUtil.set(key, orderRule);
	}

}
