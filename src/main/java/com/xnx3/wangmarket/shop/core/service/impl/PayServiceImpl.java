package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;

@Service
public class PayServiceImpl implements PayService {
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
		if(storeid < 1){
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
	
	
	public AlipayUtilVO getAlipayUtil(int storeid){
		AlipayUtilVO vo = new AlipayUtilVO();
		PaySet paySet = getPaySet(storeid);
		if(paySet.getUseAlipay() - 0 == 0){
			//支付设置
			vo.setBaseVO(BaseVO.FAILURE, "该商家未开启支付宝支付");
			return vo;
		}
		
		String path = Global.CERTIFICATE_PATH.replace("{storeid}", storeid+"");
		AlipayUtil alipayUtil = new AlipayUtil(paySet.getAlipayAppId(), paySet.getAlipayAppPrivateKey(), path+paySet.getAlipayAppCertPublicKey(), path+paySet.getAlipayCertPublicKeyRSA2(), path+paySet.getAlipayRootCert());
		vo.setAlipayUtil(alipayUtil);
		return vo;
	}
	
	
}
