package com.xnx3.wangmarket.plugin.weixinH5Auth.vo;

import com.xnx3.weixin.vo.WebShareVO;

/**
 * 微信网页分享的一些参数
 * @author 管雷鸣
 *
 */
public class H5ShareVO extends WebShareVO{
	private String shareUrl;	//分享出去的url
	
	public H5ShareVO() {
	}
	
	/**
	 * 将 {@link WebShareVO} 的赋予此类
	 * @param webShareVO
	 */
	public void setWebShareVO(WebShareVO webShareVO) {
		setAppId(webShareVO.getAppId());
		setBaseVO(webShareVO.getResult(), webShareVO.getInfo());
		setNonceStr(webShareVO.getNonceStr());
		setSignature(webShareVO.getSignature());
		setTimestamp(webShareVO.getTimestamp());
	}
	
	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	@Override
	public String toString() {
		return "H5ShareVO [shareUrl=" + shareUrl + ", getAppId()=" + getAppId() + ", getTimestamp()=" + getTimestamp()
				+ ", getNonceStr()=" + getNonceStr() + ", getSignature()=" + getSignature() + ", toString()="
				+ super.toString() + ", getResult()=" + getResult() + ", getInfo()=" + getInfo() + "]";
	}
		
}
