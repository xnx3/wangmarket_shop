package com.xnx3.wangmarket.shop.core.util;

import net.sf.json.JSONArray;

/**
 * 商品规格方面的工具类
 * @author 管雷鸣
 *
 */
public class GoodsSpecificationUtil {
	
	public static void main(String[] args) {
		System.out.println(getPrice("[{\"黄色\":901},{\"黑色\":800},{\"白色\":705}]", "黄色"));
	}
	
	/**
	 * 获取某个规格的具体价。
	 * @param specification 传入的是goods.specification ，也就是这种字符串： 
	 * <pre>
	 * [{"黄色":901},{"黑色":800},{"白色":705}]
	 * </pre>
	 * @param key 参考上面 specification 的传入，这里传入可以是 黄色
	 * @return 具体某种规格所对应的多少钱。int型，单位是分。如果key找不到则返回 -1
	 */
	public static int getPrice(String specification, String key) {
		/*
		 * 1. 验证specification空
		 * 2. 转换为JSON数组
		 * 3. 判断取值
		 */
		
		JSONArray jsonArray = JSONArray.fromObject(specification);
		
		
		
		return -1;
	}
	
}
