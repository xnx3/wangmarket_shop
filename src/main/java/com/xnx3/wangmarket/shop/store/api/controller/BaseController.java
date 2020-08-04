package com.xnx3.wangmarket.shop.store.api.controller;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 这个包下所有Controller父类
 * @author 管雷鸣
 */
public class BaseController extends BasePluginController{
	
	/**
	 * 获取当前登录商家的 {@link Store} 店铺信息
	 */
	public Store getStore(){
		return com.xnx3.wangmarket.shop.store.util.SessionUtil.getStore();
	}
	
	/**
	 * 获取当前登录店铺的store.id
	 * @return 如果没有，则返回0
	 */
	public int getStoreId(){
		Store store = getStore();
		if(store == null){
			return 0;
		}else{
			return store.getId();
		}
	}
}
