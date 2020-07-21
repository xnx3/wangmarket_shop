package com.xnx3.wangmarket.plugin.sell.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;

/**
 * 获取店铺设置
 * @author 管雷鸣
 *
 */
public class SellStoreSetVO extends BaseVO{
	private SellStoreSet sellSet;

	public SellStoreSet getSellSet() {
		return sellSet;
	}

	public void setSellSet(SellStoreSet sellSet) {
		this.sellSet = sellSet;
	}

	@Override
	public String toString() {
		return "SellStoreSetVO [sellSet=" + sellSet + "]";
	}
	
}
