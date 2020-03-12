package com.xnx3.wangmarket.plugin.huaweicloudSMS;

import com.xnx3.j2ee.pluginManage.interfaces.DatabaseLoadFinishInterface;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.net.HuaweiSMSUtil;

/**
 * 华为云短信
 * @author 管雷鸣
 *
 */
public class Plugin implements DatabaseLoadFinishInterface{

	@Override
	public void databaseLoadFinish() {
		String appKey = SystemUtil.get("HUAWEIYUN_SMS_APP_KEY");
		String appSecret = SystemUtil.get("HUAWEIYUN_SMS_APP_SECRET");
		String sender = SystemUtil.get("HUAWEIYUN_SMS_SENDER");
		String signature = SystemUtil.get("HUAWEIYUN_SMS_SIGNATURE");
		
		if(appKey == null || appKey.length() == 0){
			return;
		}
		if(appSecret == null || appSecret.length() == 0){
			return;
		}
		if(sender == null || sender.length() == 0){
			return;
		}
		if(signature == null || signature.length() == 0){
			return;
		}
		
		Global.huaweiSmsUtil = new HuaweiSMSUtil(appKey, appSecret, sender, signature);
	}
	
}
