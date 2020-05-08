package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.weixin.WeiXinPayUtil;

/**
 * 通过payService获取 WeiXinPayUtil 的返回
 * @author 管雷鸣
 *
 */
public class WeiXinPayUtilVO extends BaseVO{
	private WeiXinPayUtil weiXinPayUtil;

	public WeiXinPayUtil getWeiXinPayUtil() {
		return weiXinPayUtil;
	}

	public void setWeiXinPayUtil(WeiXinPayUtil weiXinPayUtil) {
		this.weiXinPayUtil = weiXinPayUtil;
	}

}
