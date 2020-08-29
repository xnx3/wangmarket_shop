package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.wangmarket.shop.core.vo.weixin.AccessTokenVO;
import com.xnx3.wangmarket.shop.core.vo.weixin.JsapiTicketVO;
import com.xnx3.weixin.WeiXinAppletUtil;
import com.xnx3.weixin.WeiXinUtil;
import com.xnx3.weixin.bean.AccessToken;
import com.xnx3.weixin.bean.JsapiTicket;

@Service
public class WeiXinServiceImpl implements WeiXinService {
	@Resource
	private PaySetService paySetService;

	@Override
	public WeiXinUtil getWeiXinUtil(int storeid) {
		String cacheKey = Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", storeid+"");
		WeiXinUtil util = (WeiXinUtil) CacheUtil.get(cacheKey);
		if(util == null){
			//缓存中不存在，取出用户设置的数据，new 一个新的微信工具类
			PaySet payset = paySetService.getPaySet(storeid);
			if(payset.getUseWeixinPay() - 0 == 0){
				//不使用微信支付，也就是不是用微信相关配置
				return null;
			}
			if(payset.getUseWeixinServiceProviderPay() - 1 == 0){
				//使用服务商模式
				PaySet serivcePaySet = paySetService.getSerivceProviderPaySet();
//				if(serivcePaySet.getWeixinOfficialAccountsAppid().length() < 1 || serivcePaySet.getWeixinOfficialAccountsAppSecret().length() < 1){
//					vo.setBaseVO(BaseVO.FAILURE, "服务商公众号未设置微信参数");
//					return vo;
//				}
				util = new WeiXinUtil(serivcePaySet.getWeixinOfficialAccountsAppid(), serivcePaySet.getWeixinOfficialAccountsAppSecret());
			}else{
				if(payset.getWeixinOfficialAccountsAppid() == null || payset.getWeixinOfficialAccountsAppid().length() == 0){
					//这个商户并没有设置过微信公众号相关参数
					return null;
				}
				util = new WeiXinUtil(payset.getWeixinOfficialAccountsAppid(), payset.getWeixinOfficialAccountsAppSecret());
			}
			
			setWeiXinUtilCache(util, storeid);
		}
		
		return util;
	}
	
	@Override
	public WeiXinAppletUtil getWeiXinAppletUtil(int storeid) {
		String cacheKey = Global.CACHE_KEY_STORE_WEIXIN_APPLET_UTIL.replace("{storeid}", storeid+"");
		WeiXinAppletUtil util = (WeiXinAppletUtil) CacheUtil.get(cacheKey);
		if(util == null){
			//缓存中不存在，取出用户设置的数据，new 一个新的微信工具类
			PaySet payset = paySetService.getPaySet(storeid);
			if(payset == null){
				//数据库中没有这个数据，这个店铺id根本不存在，直接返回null
				return null;
			}
			if(payset.getWeixinAppletAppid() == null || payset.getWeixinAppletAppid().length() == 0 || payset.getWeixinAppletAppSecret() == null || payset.getWeixinAppletAppSecret().length() == 0){
				//这个商户并没有设置过微信公众号相关参数
				return null;
			}
			util = new WeiXinAppletUtil(payset.getWeixinAppletAppid(), payset.getWeixinAppletAppSecret());
			CacheUtil.setWeekCache(cacheKey, util);
			System.out.println("new weixinapplet util:"+util);
		}
		
		return util;
	}
	
	@Override
	public WeiXinUtil getServiceProviderWeiXinUtil() {
		return getWeiXinUtil(0);
	}

	@Override
	public JsapiTicketVO getJsapiTicket(int storeid) {
		JsapiTicketVO vo = new JsapiTicketVO();
		if(storeid < 1){
			vo.setBaseVO(BaseVO.FAILURE, "请传入店铺id");
			return vo;
		}
		
		WeiXinUtil util = getWeiXinUtil(storeid);
		if(util == null){
			vo.setBaseVO(BaseVO.FAILURE, "该店铺未设置微信参数");
			return vo;
		}
		
		String oldJsApiTicketGainTIme = "";	//旧的jsapi ticket获取的时间
		if(util.jsapiTicket != null){
			oldJsApiTicketGainTIme = util.jsapiTicket.getGainTime()+"";
		}
		String oldAccessTokenGainTime = "";	//旧的 AccessToken获取的时间。因为jsapi_ticket 获取是使用 AccessToken 来获取的，所以 AccessToken 也要判断是否获取了新的
		if(util.accessToken != null){
			oldAccessTokenGainTime = util.accessToken.getGainTime()+"";
		}
		
		JsapiTicket jsapi_ticket = util.getJsapiTicket();
		vo.setJsapiTicket(jsapi_ticket);
		//如果获取时间改变了，那么就是 jsapi_ticket 重新获取过了，重新缓存
		if(!((jsapi_ticket.getGainTime()+"").equals(oldJsApiTicketGainTIme))){
			//不一样，那说明重新获取了
			ConsoleUtil.log("---jsapi_ticket重新获取："+jsapi_ticket.toString());
			setWeiXinUtilCache(util, storeid); 	//重新缓存
		}else if(!((util.getAccessToken().getGainTime()+"").equals(oldAccessTokenGainTime))){
			//AccessToken 的时间不一样，那说明它重新获取了
			ConsoleUtil.log("---获取jsapi_ticket时，判断到AccessToken重新获取并缓存："+util.getAccessToken().toString());
			setWeiXinUtilCache(util, storeid); 	//重新缓存
		}
		return vo;
	}
	
	/**
	 * 缓存 {@link WeiXinUtil}
	 * @param util 要缓存的 {@link WeiXinUtil}
	 * @param storeid 该 {@link WeiXinUtil} 属于哪个店铺
	 */
	private void setWeiXinUtilCache(WeiXinUtil util, int storeid){
		String cacheKey = Global.CACHE_KEY_STORE_WEIXIN_UTIL.replace("{storeid}", storeid+"");
		CacheUtil.setWeekCache(cacheKey, util);
	}

	@Override
	public AccessTokenVO getAccessToken(int storeid) {
		AccessTokenVO vo = new AccessTokenVO();
		if(storeid < 1){
			vo.setBaseVO(BaseVO.FAILURE, "请传入店铺id");
			return vo;
		}
		
		WeiXinUtil util = getWeiXinUtil(storeid);
		if(util == null){
			vo.setBaseVO(BaseVO.FAILURE, "该店铺未设置微信参数");
			return vo;
		}
		
		String oldAccessTokenGainTime = "";	//旧的access_token获取的时间
		if(util.accessToken != null){
			oldAccessTokenGainTime = util.accessToken.getGainTime()+"";
		}
		
		AccessToken token = util.getAccessToken();
		if(token == null){
			vo.setBaseVO(BaseVO.FAILURE, "获取token失败");
			return vo;
		}
		vo.setAccessToken(token);
		//如果获取时间改变了，那么就是 jsapi_ticket 重新获取过了，重新缓存
		if(!((token.getGainTime()+"").equals(oldAccessTokenGainTime))){
			//不一样，那说明重新获取了
			ConsoleUtil.log("---AccessToken重新获取并缓存："+token.toString());
			setWeiXinUtilCache(util, storeid); 	//重新缓存
		}
		return vo;
	}
}
