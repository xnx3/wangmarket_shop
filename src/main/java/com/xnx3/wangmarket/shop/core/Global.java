package com.xnx3.wangmarket.shop.core;

import com.xnx3.j2ee.util.VersionUtil;
import com.xnx3.wangmarket.Authorization;

/**
 * 项目中的一些缓存、参数等
 * @author 管雷鸣
 *
 */
public class Global {
	/**
	 * 当前的版本号。
	 */
	public static final String VERSION = "1.4";

	/**
	 * 商家的自定义订单规则的持久化缓存Key {storeid} 便是替换为 store.id
	 */
	public final static String CACHE_KEY_ORDER_RULE = "shop:store:orderRule:{storeid}";
	/**
	 * 商家的支付方式设置的持久化缓存Key {storeid} 便是替换为 store.id
	 */
	public final static String CACHE_KEY_PAY_SET = "shop:store:paySet:{storeid}";
	//商家的短信接口设置的持久化缓存key 
	public final static String CACHE_KEY_SMS_SET = "shop:store:sms:set:{storeid}";
	//商家短信接口发送类 SMSUtil 的缓存
	public final static String CACHE_KEY_SMS_UTIL_SET = "shop:store:sms:util:{storeid}";
	//商家短信接口发送短信的限额，记录用户发送的频率。控制避免短信通道被他人利用
	public final static String CACHE_KEY_SMS_QUOTA = "shop:store:sms:quota:{storeid}";
	
	
	/**
	 * 商家的商品分类列表缓存，只获取正常状态的。像是已删除的就不会获取
	 */
	public static final String CACHE_KEY_STORE_GOODSTYPE = "shop:store:normalGoodsType:{storeid}";
	//商家设置的微信相关参数，进而缓存的 WeiXinUtil 工具类
	public static final String CACHE_KEY_STORE_WEIXIN_UTIL = "shop:store:weixin:util:{storeid}";
	//商家设置的微信相关参数，进而缓存的 WeiXinAppletUtil 工具类
	public static final String CACHE_KEY_STORE_WEIXIN_APPLET_UTIL = "shop:store:weixin:applet_util:{storeid}";
	//商家图片存放目录。存放的图片如轮播图、分类图片、产品列表图等
	public final static String ATTACHMENT_FILE_CAROUSEL_IMAGE = "store/{storeid}/images/";
	
	/**
	 * 证书所在服务器路径,每个店铺的证书都有单独的文件夹。存储时 {storeid}会替换为 store.id 数字
	 */
	public static final String CERTIFICATE_PATH = "/mnt/store/{storeid}/";
	
	//权限相关,商城的角色id
	public static final int STORE_ROLE_ID = 8;
	
	static{
		Authorization.setVersion(VersionUtil.strToInt(VERSION));
		Authorization.setSoftType(2);
		new Thread(new Runnable() {
			public void run() {
				while(true){
					System.out.println("auth:"+Authorization.copyright);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
