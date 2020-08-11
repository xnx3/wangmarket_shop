package com.xnx3.wangmarket.shop.store.system;

import java.util.Map;
import com.xnx3.j2ee.pluginManage.interfaces.ShiroFilterInterface;

/**
 * Shiro方面拦截，放开所有对 /shop/store/ 的拦截，有springmvc拦截器控制
 * @author 管雷鸣
 *
 */
public class ShiroFilter implements ShiroFilterInterface{
	@Override
	public Map<String, String> shiroFilter(Map<String, String> filterChainDefinitionMap) {
		filterChainDefinitionMap.put("/shop/store/**", "anon");
		filterChainDefinitionMap.put("/store/**", "anon");	//临时放开/store/...模板测试用
		return filterChainDefinitionMap;
	}
}
