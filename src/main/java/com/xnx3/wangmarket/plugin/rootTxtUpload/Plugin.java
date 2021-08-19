package com.xnx3.wangmarket.plugin.rootTxtUpload;

import com.xnx3.j2ee.pluginManage.PluginRegister;

/**
 * txt文件上传，可以直接通过域名的根目录访问到，主要用于微信小程序认证使用
 * @author 薛浩
 */
@PluginRegister(menuTitle = "txt文件上传",menuHref="/plugin/rootTxtUpload/index.jsp", applyToCMS=true, intro="txt文件上传，可以直接通过域名的根目录访问到，", version="1.0", versionMin="1.0")
public class Plugin {
	
}