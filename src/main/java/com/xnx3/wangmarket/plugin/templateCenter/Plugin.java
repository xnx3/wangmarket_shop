package com.xnx3.wangmarket.plugin.templateCenter;

import com.xnx3.wangmarket.admin.pluginManage.anno.PluginRegister;

/**
 * CMS模式模版开发插件。只是适用于本地开发模版使用，仅限localhost或者 127.0.0.1请求时可用
 * @author 管雷鸣
 */
@PluginRegister(id="templateCenter" , menuTitle = "模版库",menuHref="../../plugin/templateCenter/admin/index.do", applyToSuperAdmin=true, intro="模版中心，在总管理后台使用", version="1.2", versionMin="4.9")
public class Plugin{
	
}