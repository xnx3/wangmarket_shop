package com.xnx3.wangmarket.shop;

import java.util.Map;
import org.springframework.context.annotation.Configuration;
import com.xnx3.j2ee.pluginManage.interfaces.ShiroFilterInterface;

/**
 * shop 商城项目
 * @author 管雷鸣
 */
public class Plugin implements ShiroFilterInterface{
	@Override
	public Map<String, String> shiroFilter(Map<String, String> filterChainDefinitionMap) {
		filterChainDefinitionMap.put("/shop/goods/**.do", "anon");
		filterChainDefinitionMap.put("/shop/goodsType/**.do", "anon");
		filterChainDefinitionMap.put("/shop/cart/**.do", "authc");
		return filterChainDefinitionMap;
	}
}
