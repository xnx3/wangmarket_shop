package com.xnx3.wangmarket.shop;

import java.util.Map;
import com.xnx3.j2ee.pluginManage.interfaces.ShiroFilterInterface;

/**
 * shop 商城项目
 * @author 管雷鸣
 */
public class Plugin implements ShiroFilterInterface{
	@Override
	public Map<String, String> shiroFilter(Map<String, String> filterChainDefinitionMap) {
		filterChainDefinitionMap.put("/shop/**", "anon");
		return filterChainDefinitionMap;
	}
}
