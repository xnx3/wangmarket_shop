package com.xnx3.wangmarket.shop.core.pluginManage.interfaces;

import com.xnx3.j2ee.entity.User;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 用户注册相关
 * @author 管雷鸣
 */
public interface RegInterface {
	
	/**
	 * 注册成功，注册完后要执行的
	 * @param user 注册成功的 {@link User}
	 * @param store 注册用户是注册的哪个 {@link Store} 商城的。注意，这里有可能会传入 null
	 */
	public void regFinish(User user, Store store);
	
}