package com.xnx3.wangmarket.plugin.sell.system;

import java.util.Map;
import com.xnx3.j2ee.pluginManage.interfaces.ShiroFilterInterface;

/**
 * Shiro方面拦截，放开所有对 /u 的拦截
 * @author 管雷鸣
 *
 */
public class ShiroFilter implements ShiroFilterInterface{
	@Override
	public Map<String, String> shiroFilter(Map<String, String> filterChainDefinitionMap) {
		filterChainDefinitionMap.put("/u", "anon");
		return filterChainDefinitionMap;
	}
}
