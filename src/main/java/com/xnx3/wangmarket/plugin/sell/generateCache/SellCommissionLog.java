package com.xnx3.wangmarket.plugin.sell.generateCache;

import org.springframework.stereotype.Component;
import com.xnx3.j2ee.generateCache.BaseGenerate;

/**
 * SellCommissionLog 状态相关
 * @author 管雷鸣
 */
@Component(value="PluginSellCommissionLogGenerate")
public class SellCommissionLog extends BaseGenerate {
	
	public SellCommissionLog(){
		transfer_state();
	}
	
	public void transfer_state() {
		createCacheObject("transfer_state");
		cacheAdd(1, "已结算");
		cacheAdd(0, "未结算");
		generateCacheFile();
	}
}
