package com.xnx3.wangmarket.plugin.sell.generateCache;

import org.springframework.stereotype.Component;
import com.xnx3.j2ee.generateCache.BaseGenerate;

/**
 * SellTixianLog 状态相关
 * @author 管雷鸣
 */
@Component(value="PluginSellTixianLogGenerate")
public class SellTixianLog extends BaseGenerate {
	
	public SellTixianLog(){
		state();
	}
	
	public void state() {
		createCacheObject("state");
		cacheAdd(0, "申请中");
		cacheAdd(1, "已汇款");
		cacheAdd(2, "已拒绝");
		generateCacheFile();
	}
}
