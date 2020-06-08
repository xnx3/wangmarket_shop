package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;
import com.xnx3.wangmarket.shop.core.vo.WeiXinPayUtilVO;
import com.xnx3.weixin.WeiXinPayUtil;

@Service
public class PayServiceImpl implements PayService {
	@Resource
	private SqlDAO sqlDAO;
	@Resource
	private PaySetService paySetService;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}
	
	public PaySetService getPaySetService() {
		return paySetService;
	}

	public void setPaySetService(PaySetService paySetService) {
		this.paySetService = paySetService;
	}

	public AlipayUtilVO getAlipayUtil(int storeid){
		AlipayUtilVO vo = new AlipayUtilVO();
		PaySet paySet = paySetService.getPaySet(storeid);
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

	@Override
	public WeiXinPayUtilVO getWeiXinPayUtil(int storeid) {
		WeiXinPayUtilVO vo = new WeiXinPayUtilVO();
		PaySet paySet = paySetService.getPaySet(storeid);
		if(paySet.getUseWeixinPay() - 0 == 0){
			//支付设置
			vo.setBaseVO(BaseVO.FAILURE, "该商家未开启微信支付");
			return vo;
		}
		
		//服务商payset设置
		PaySet servicePaySet = paySetService.getSerivceProviderPaySet();
		
		WeiXinPayUtil util;
		//判断一下当前是用的300元认证的公众号，还是免认证的我们服务商通道
		if(paySet.getUseWeixinServiceProviderPay() - 1 == 0){
			//使用我们服务商通道
			util = new WeiXinPayUtil(servicePaySet.getWeixinOfficialAccountsAppid(), servicePaySet.getWeixinMchId(), servicePaySet.getWeixinMchKey());
			util.openServiceProviderMode(paySet.getWeixinSerivceProviderSubMchId());
			util.setServiceProviderSubAppletAppid(paySet.getWeixinAppletAppid());
		}else{
			//正常微信支付
			util = new WeiXinPayUtil(paySet.getWeixinOfficialAccountsAppid(), paySet.getWeixinMchId(), paySet.getWeixinMchKey());
		}
		
		util.setApplet_appid(paySet.getWeixinAppletAppid());
		util.setOfficialAccounts_appid(paySet.getWeixinOfficialAccountsAppid());
		util.setServiceProvider_officialAccounts_appid(servicePaySet.getWeixinOfficialAccountsAppid());
		
		vo.setWeiXinPayUtil(util);
		return vo;
	}
	
	
}
