package com.xnx3.wangmarket.shop.store.controller;

import java.util.Map;
import com.xnx3.j2ee.pluginManage.interfaces.ShiroFilterInterface;

/**
 * Shiro方面拦截，放开所有对 /shop/store/ 的拦截，有springmvc拦截器控制
 * @author 管雷鸣
 *
 */
@Deprecated
public class ShiroFilter implements ShiroFilterInterface{
	@Override
	public Map<String, String> shiroFilter(Map<String, String> filterChainDefinitionMap) {
		filterChainDefinitionMap.put("/shop/store/**", "anon"); //适配旧的
		return filterChainDefinitionMap;
	}
}
