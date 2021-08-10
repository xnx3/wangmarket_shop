package com.xnx3.wangmarket.plugin.visitBrowserRecord;

import org.springframework.stereotype.Component;
import com.xnx3.j2ee.util.ElasticSearchUtil;

/**
 * 此插件的全局参数
 * @author 管雷鸣
 *
 */
@Component
public class Global {
	public static final String INDEX_NAME = "plugin_visit_browser_record"; //操作的 index name， 会在项目启动时自动创建
	
	static {
		//tomcat启动后自动检测有没有这个 index name，如果当前项目中application.prpperties中使用了 elasticsearch ，但是是第一次启动还没有这个index name，那么自动创建
		ElasticSearchUtil.projectStartCheckCreateIndex(INDEX_NAME);
	}
}
