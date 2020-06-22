package com.xnx3.wangmarket.plugin.sell.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;

/**
 * 提现记录详情
 * @author 管雷鸣
 *
 */
public class SellTiXianLogVO extends BaseVO{
	private SellTiXianLog sellTiXianLog;

	public SellTiXianLog getSellTiXianLog() {
		return sellTiXianLog;
	}

	public void setSellTiXianLog(SellTiXianLog sellTiXianLog) {
		this.sellTiXianLog = sellTiXianLog;
	}

	@Override
	public String toString() {
		return "SellTiXianLogVO [sellTiXianLog=" + sellTiXianLog + "]";
	}
	
}
