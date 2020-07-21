package com.xnx3.wangmarket.plugin.shorturl.util;

import com.xnx3.CacheUtil;
import com.xnx3.j2ee.util.ApplicationPropertiesUtil;

/**
 * 短网址
 * 若使用此功能， application.properties 文件中增加 wm.shorturl.domain 配置
 * @author 管雷鸣
 *
 */
public class ShortUrlUtil {
	public static final String PREFIX = "shorturl:";	//redis缓存的key的前缀
	private static int useShortUrl = 0;	//是否使用短网址，0需要初始化， 1使用， 2不使用
	/**
	 * 是否使用短网址，默认是不使用
	 * @return true使用， false不使用
	 */
	public static boolean isUseShortUrl(){
		if(useShortUrl == 0){
			//赋值
			String domain = getDomain();
			if(domain.length() < 1){
				useShortUrl = 2;
			}else{
				useShortUrl = 1;
			}
		}
		
		return useShortUrl==1;
	}
	
	private static String domain;	//短网址域名
	/**
	 * 获取短网址域名。
	 * @return 如果为""空字符串，也就是长度为0，则是不使用短网址功能，否则返回具体的短网址域名，返回如：  http://wscso.com/
	 */
	public static String getDomain(){
		if(domain == null){
			domain = ApplicationPropertiesUtil.getProperty("wm.shorturl.domain");
			if(domain == null){
				domain = "";
			}
		}
		
		return domain;
	}
	
	/**
	 * 传入一个url，返回这个url的短网址
	 * @param key 要缓存的key，也就是短网址的那个码
	 * @param url 短网址对应的真实网址，传入如 http://www.xxxxxxxxxxxx
	 */
	public static void setShortUrl(String key, String url){
		Object obj = CacheUtil.get(PREFIX+key);
		if(obj != null){
			String str = obj.toString();
			if(str.equals(url)){
				//如果传入的key、url 跟缓存的一致，那么直接退出，不需要重新缓存了
				return;
			}
		}
		CacheUtil.set(PREFIX+key, url);
	}
	
	/**
	 * 根据短网址的码，获取其真实的网址
	 * @param key 短网址的码
	 * @return 真实的网址。返回如 http://www.xxxxxxxxxx  如果返回null，则是没有找到这个短网址的码
	 */
	public static String getShortUrl(String key){
		Object obj = CacheUtil.get(PREFIX+key);
		if(obj != null){
			String str = obj.toString();
			return str;
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}
}
