package com.xnx3.wangmarket.plugin.seoUrlPush;

import com.xnx3.j2ee.pluginManage.PluginRegister;

/**
 * SEO网址推送
 * @author 管雷鸣
 */
@PluginRegister(menuTitle = "SEO网址推送",menuHref="/plugin/seoUrlPush/siteadmin/set.do", applyToCMS=true, intro="SEO工具，当有人访问网站某个页面时，会自动将当前页面的链接提交到百度，有利于百度收录", version="1.0", versionMin="5.0")
public class Plugin{
}