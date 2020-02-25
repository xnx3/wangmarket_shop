package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.GoodsData;

/**
 * {@link GoodsData} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class GoodsDataBean {
	private String detail;	//详情内容，富文本编辑区

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
