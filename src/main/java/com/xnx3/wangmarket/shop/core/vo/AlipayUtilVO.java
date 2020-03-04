package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;

/**
 * 通过payService获取AlipayUtil的返回
 * @author 管雷鸣
 *
 */
public class AlipayUtilVO extends BaseVO{
	private AlipayUtil alipayUtil;

	public AlipayUtil getAlipayUtil() {
		return alipayUtil;
	}

	public void setAlipayUtil(AlipayUtil alipayUtil) {
		this.alipayUtil = alipayUtil;
	}
}
