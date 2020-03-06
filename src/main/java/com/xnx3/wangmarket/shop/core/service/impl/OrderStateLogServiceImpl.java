package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.OrderStateLogService;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;

@Service
public class OrderStateLogServiceImpl implements OrderStateLogService {
	@Resource
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public List<OrderStateLog> getLogList(int orderid) {
		if(orderid < 1){
			return new ArrayList<OrderStateLog>();
		}
		
		return sqlDAO.findBySqlQuery("SELECT * FROM shop_order_state_log WHERE orderid = "+orderid+" ORDER BY id DESC", OrderStateLog.class);
	}
	
}
