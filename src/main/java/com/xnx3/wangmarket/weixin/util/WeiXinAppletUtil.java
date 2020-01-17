package com.xnx3.wangmarket.weixin.util;

import com.xnx3.j2ee.util.SystemUtil;

/**
 * 微信小程序
 * @author 管雷鸣
 */
public class WeiXinAppletUtil{
	private static com.xnx3.weixin.WeiXinAppletUtil weixinAppletUtil;
	
	/**
	 * 获取 WeiXinAppletUtil
	 */
	public static com.xnx3.weixin.WeiXinAppletUtil getWeixinAppletUtil(){
		if(weixinAppletUtil == null){
			weixinAppletUtil = new com.xnx3.weixin.WeiXinAppletUtil(SystemUtil.get("WEIXIN_APPLET_APPID"), SystemUtil.get("WEIXIN__APPLET_APPSECRET"));
		}
		return weixinAppletUtil;
	}
	
}
