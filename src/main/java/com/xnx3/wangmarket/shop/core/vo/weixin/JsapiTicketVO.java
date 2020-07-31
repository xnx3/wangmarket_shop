package com.xnx3.wangmarket.shop.core.vo.weixin;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.weixin.bean.JsapiTicket;

/**
 * jsapi_ticket
 * @author 管雷鸣
 *
 */
public class JsapiTicketVO extends BaseVO{
	private JsapiTicket jsapiTicket;

	public JsapiTicket getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(JsapiTicket jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	@Override
	public String toString() {
		return "JsapiTicketVO [jsapiTicket=" + jsapiTicket + ", getJsapiTicket()=" + getJsapiTicket() + "]";
	}
}
