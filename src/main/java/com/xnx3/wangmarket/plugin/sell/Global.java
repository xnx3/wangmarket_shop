package com.xnx3.wangmarket.plugin.sell;

/**
 * 插件全局设置
 * @author 管雷鸣
 *
 */
public class Global {
	/**
	 * applyWithdraw.json 接口有请求时间限制，每10秒最多请求一次。如果10秒内多次请求，是会被拦截的
	 */
	public final static String CACHE_KEY_APPLY_WITHDRAW_ALLOW = "shop:plugin:sell:applyWithdraw:allow:{storeid}:{userid}";
}
