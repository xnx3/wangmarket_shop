package com.xnx3.wangmarket.plugin.newsSync.util;

import java.util.List;

import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.plugin.newsSync.entity.SiteBind;

/**
 * 内容同步插件的session相关
 * @author 管雷鸣
 *
 */
public class SessionUtil extends com.xnx3.wangmarket.admin.util.SessionUtil{
	//站点绑定插件的站点对应。当前网站为原站的情况下，发布文章后要同步到哪些网站去
	public static final String PLUGIN_NAME_NEWSSYNC_SITEBIND = "wangmarket_plugin_newssync_sitebind";
	
	/**
	 * 获取当前站点的目标站绑定列表。若是不存在，则返回null
	 */
	public static List<SiteBind> getSiteBindList(){
		return getPlugin(PLUGIN_NAME_NEWSSYNC_SITEBIND);
	}
	
	/**
	 * 设置当前站点的目标站绑定列表
	 * @param list {@link SiteBind}当前已️登录的网站，发布文章后要同步到哪些网站中。这个list便是对应列表
	 */
	public static void setSiteBindList(List<SiteBind> list){
		setPlugin(PLUGIN_NAME_NEWSSYNC_SITEBIND, list);
	}
	
}
