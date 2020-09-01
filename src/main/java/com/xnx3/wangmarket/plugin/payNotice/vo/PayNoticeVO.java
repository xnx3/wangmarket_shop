package com.xnx3.wangmarket.plugin.payNotice.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.payNotice.entity.PayNotice;

/**
 * 商品限购规定用户的可购买次数。
 * @author 刘鹏
 */
public class PayNoticeVO extends BaseVO {
    private PayNotice payNotice;

	public PayNotice getPayNotice() {
		return payNotice;
	}

	public void setPayNotice(PayNotice payNotice) {
		this.payNotice = payNotice;
	}

	@Override
	public String toString() {
		return "PayNoticeVO [payNotice=" + payNotice + "]";
	}
    
}
