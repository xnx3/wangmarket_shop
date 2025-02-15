package com.xnx3.wangmarket.shop.core.util;

import com.xnx3.wangmarket.shop.core.service.StoreSMSService;

/**
 * Spring 工具类
 * @author 管雷鸣
 */
public class SpringUtil extends com.xnx3.j2ee.util.SpringUtil{
	
	/**
	 * 获取 {@link StoreSMSService}
	 * @return {@link StoreSMSService}
	 */
	public static StoreSMSService getStoreSMSService() {
        return (StoreSMSService) getApplicationContext().getBean("storeSmsService");
    }
	
}
