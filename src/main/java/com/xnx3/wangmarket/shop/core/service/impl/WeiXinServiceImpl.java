package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinUtil;

@Service
public class WeiXinServiceImpl implements WeiXinService {
	@Resource
	private PayService payService;

	@Override
	public WeiXinUtil getWeiXinUtil(int storeid) {
		String cacheKey = Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", storeid+"");
		WeiXinUtil util = (WeiXinUtil) CacheUtil.get(cacheKey);
		if(util == null){
			//缓存中不存在，取出用户设置的数据，new 一个新的微信工具类
			PaySet payset = payService.getPaySet(storeid);
			if(payset == null){
				//数据库中没有这个数据，这个店铺id根本不存在，直接返回null
				return null;
			}
			if(payset.getWeixinOfficialAccountsAppid() == null || payset.getWeixinOfficialAccountsAppid().length() == 0){
				//这个商户并没有设置过微信公众号相关参数
				return null;
			}
			
			util = new WeiXinUtil(payset.getWeixinOfficialAccountsAppid(), payset.getWeixinOfficialAccountsAppSecret(), payset.getWeixinOfficialAccountsToken());
			CacheUtil.setWeekCache(cacheKey, util);
			System.out.println("new yige util:"+util);
		}
		
		return util;
	}
	
}
