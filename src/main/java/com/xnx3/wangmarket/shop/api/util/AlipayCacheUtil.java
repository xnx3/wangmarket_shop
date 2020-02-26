package com.xnx3.wangmarket.shop.api.util;

import java.util.HashMap;
import java.util.Map;

import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.core.entity.PaySet;

/**
 * 缓存 AlipayUtil ，每一个商家都有一个自己的  AlipayUtil
 * @author 管雷鸣
 *
 */
public class AlipayCacheUtil {
	/**
	 * 证书所在路径,每个店铺的证书都有单独的文件夹。存储时 {storeid}会替换为 store.id 数字
	 */
	private static final String CERTIFICATE_PATH = "/mnt/store/{storeid}/";
	private static Map<String, AlipayUtil> alipayUtilMap = new HashMap<String, AlipayUtil>();
	
	public static AlipayUtil getAlipayUtil(int storeid){
		String key = "store_"+storeid;
		AlipayUtil alipay = alipayUtilMap.get(key);
		if(alipay == null){
			//没有，创建一个
			PaySet payset = SpringUtil.getSqlService().findById(PaySet.class, storeid);
			if(payset == null){
				return null;
			}
			String certificatePath = CERTIFICATE_PATH.replace("{storeid}", storeid+"");
			alipay = new AlipayUtil(payset.getAlipayAppId(), payset.getAlipayAppPrivateKey(), certificatePath+payset.getAlipayAppCertPublicKey(), certificatePath+payset.getAlipayCertPublicKeyRSA2(), certificatePath+payset.getAlipayRootCert());
			//将最新的加入缓存
			alipayUtilMap.put(key, alipay);
		}
		return alipay;
	}
	
	
}
