package com.xnx3.wangmarket.shop.core.pluginManage.controller;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.shop.core.Global;

/**
 * 所有shop插件的 Controller 都继承此
 * @author 管雷鸣
 */
public class BasePluginController extends com.xnx3.j2ee.pluginManage.controller.BasePluginController {

	/**
	 * 判断当前用户是否是商城管理员，有商城管理后台权限
	 * @return true:有商城管理后台的权限；  false：没有
	 */
	public static boolean haveStoreAuth(){
		User user = SessionUtil.getUser();
		if(user == null){
			//未登陆，那就直接是false
			return false;
		}
		
		if(com.xnx3.j2ee.Func.isAuthorityBySpecific(user.getAuthority(), Global.STORE_ROLE_ID+"")){
			return true;
		}
		return false;
	}
	
}
