package com.xnx3.wangmarket.shop.core;

/**
 * 项目中的一些缓存、参数等
 * @author 管雷鸣
 *
 */
public class Global {
	/**
	 * 商家的自定义订单规则的持久化缓存Key {storeid} 便是替换为 store.id
	 */
	public final static String CACHE_KEY_ORDER_RULE = "shop:store:orderRule:{storeid}";
	/**
	 * 商家的支付方式设置的持久化缓存Key {storeid} 便是替换为 store.id
	 */
	public final static String CACHE_KEY_PAY_SER = "shop:store:paySet:{storeid}";
	/**
	 * 证书所在服务器路径,每个店铺的证书都有单独的文件夹。存储时 {storeid}会替换为 store.id 数字
	 */
	public static final String CERTIFICATE_PATH = "/mnt/store/{storeid}/";
	
	
}
