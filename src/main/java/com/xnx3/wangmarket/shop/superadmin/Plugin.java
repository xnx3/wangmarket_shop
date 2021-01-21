package com.xnx3.wangmarket.shop.superadmin;

import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.pluginManage.interfaces.SuperAdminIndexInterface;

/**
 * 超级管理后台的扩展
 * @author 管雷鸣
 *
 */
@PluginRegister(applyToSuperAdmin=true, menuHref="/shop/superadmin/store/list.do", menuTitle="店铺管理")
public class Plugin implements SuperAdminIndexInterface{

	@Override
	public String superAdminIndexAppendHtml() {
		String storeMenu = "<li class=\"layui-nav-item\">"
							+ "<a href=\"javascript:loadUrl(\\'/superadmin/store/list.jsp\\');\">"
									+ "<i class=\"layui-icon firstMenuIcon\">&#xe653;</i>"
									+ "<span class=\"firstMenuFont\">店铺管理</span>"
							+ "</a>"
						+ "</li>";
		return "<script> document.getElementById('menuAppend').innerHTML = document.getElementById('menuAppend').innerHTML+'"+storeMenu+"'; console.log('superadmin.plugin');<script>";
	}

}
