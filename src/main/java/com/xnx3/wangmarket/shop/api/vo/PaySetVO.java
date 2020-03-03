package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.PaySetBean;
import com.xnx3.wangmarket.shop.api.vo.bean.StoreBean;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 支付设置相关信息
 * @author 管雷鸣
 *
 */
public class PaySetVO extends BaseVO{
	private PaySetBean paySet;

	public PaySetBean getPaySet() {
		return paySet;
	}

	public void setPaySet(PaySet paySet) {
		if(paySet == null){
			this.paySet = new PaySetBean();
		}else{
			PaySetBean bean = new PaySetBean();
			bean.setUseAlipay(paySet.getUseAlipay());
			bean.setUsePrivatePay(paySet.getUsePrivatePay());
			bean.setUseWeixinPay(paySet.getUseWeixinPay());
			this.paySet = bean;
		}
	}
	public void setPaySet(PaySetBean paySetBean) {
		this.paySet = paySetBean;
	}
	
}
