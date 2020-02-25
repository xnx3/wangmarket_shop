package com.xnx3.wangmarket.shop.api.controller;

import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;

/**
 * 当前包下controller都继承这个
 * @author 管雷鸣
 */
public class BaseController extends BasePluginController {
	
	/**
	 * 获取当前登录用户所在的店铺信息
	 * @return 若未登录，则会返回null
	 */
	public Store getStore(){
		return SessionUtil.getStore();
	}
	
	/**
	 * 获取当前登录用户所在的店铺的ID， store.id
	 * @return 若未登录，或没有所在店铺信息，则会返回 0
	 */
	public int getStoreId(){
		Store store = getStore();
		if(store == null || store.getId() == null){
			return 0;
		}
		return store.getId();
	}
}
