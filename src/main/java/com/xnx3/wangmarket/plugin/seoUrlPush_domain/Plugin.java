package com.xnx3.wangmarket.plugin.seoUrlPush_domain;

import java.util.Map;
import com.xnx3.wangmarket.admin.pluginManage.interfaces.AutoLoadSimpleSitePluginTableDateInterface;
import com.xnx3.wangmarket.domain.bean.RequestInfo;
import com.xnx3.wangmarket.domain.bean.SimpleSite;
import com.xnx3.wangmarket.domain.pluginManage.interfaces.CreateReceiveMQInterface;
import com.xnx3.wangmarket.domain.pluginManage.interfaces.DomainVisitInterface;
import com.xnx3.wangmarket.domain.util.PluginCache;

/**
 * SEO网址推送
 * @author 管雷鸣
 *
 */
public class Plugin implements DomainVisitInterface, AutoLoadSimpleSitePluginTableDateInterface, CreateReceiveMQInterface{
	//放在网页上面的js代码,百度搜索引擎
	public static final String JS_BAIDU = "<script>try{ (function(){var bp=document.createElement(\"script\");var curProtocol=window.location.protocol.split(\":\")[0];if(curProtocol===\"https\"){bp.src=\"https://zz.bdstatic.com/linksubmit/push.js\"}else{bp.src=\"http://push.zhanzhang.baidu.com/push.js\"}var s=document.getElementsByTagName(\"script\")[0];s.parentNode.insertBefore(bp,s)})(); }catch(err){}</script>";
	//放在网页上面的js代码,360搜索引擎
	public static final String JS_360 = "<script>try{ (function(){var src=\"https://jspassport.ssl.qhimg.com/11.0.1.js?d182b3f28525f2db83acfaaf6e696dba\";document.write('<script src=\"'+src+'\" id=\"sozz\"><\\/script>');})();  }catch(err){}</script>";
	
	public String htmlManage(String html, SimpleSite simpleSite, RequestInfo requestInfo) {
		Map<String,Object> map = PluginCache.getPluginMap(simpleSite.getSiteid(), "seoUrlPush");
		
		if(map == null){
			//为空，则是没有使用到该插件，将html 原样返回
			return html;
		}
		if(map.get("is_use").toString().equals("1")){
			//如果当前网站启用了插件，那么就要向html末尾追加js
			html = html + JS_BAIDU + JS_360;
		}
		
		return html;
	}

	@Override
	public void createReceiveMQForDomain() {
	}

	@Override
	public String autoLoadSimpleSitePluginTableDate() {
		return "SELECT * FROM plugin_seo_url_push";
	}
}
