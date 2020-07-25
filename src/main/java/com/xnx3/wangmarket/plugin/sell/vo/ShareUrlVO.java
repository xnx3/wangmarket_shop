package com.xnx3.wangmarket.plugin.sell.vo;

import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.shorturl.util.ShortUrlUtil;

/**
 * 用户分享的vo，获取到自己推广分享的链接
 * @author 管雷鸣
 */
public class ShareUrlVO extends BaseVO{
	private String weixinH5Url;	//微信网页开发，微信内网页，分享的url。这个url进入后可以自动登录。若想使用这个，云商城需要有 weixinH5Auth 插件
	private String h5Url;		//普通的网页分享的url。可以吧这个url随便发到其他各大论坛，别人可以通过此进来，注册成功后，便是自己的下级用户
	public final String SHORT_URL = "http://wscso.com/u?";
	
	
	public String getWeixinH5Url() {
		return weixinH5Url;
	}
	public void setWeixinH5Url(String weixinH5Url, int storeid) {
		if(ShortUrlUtil.isUseShortUrl()){
			String key = get62UserId()+"_"+StringUtil.intTo62(storeid)+"_w";
			ShortUrlUtil.setShortUrl(key, weixinH5Url);
			this.weixinH5Url = ShortUrlUtil.getDomain()+"u?"+key;
		}else{
			this.weixinH5Url = weixinH5Url;
		}
	}
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url, int storeid) {
		if(ShortUrlUtil.isUseShortUrl()){
			String key = get62UserId()+"_"+StringUtil.intTo62(storeid)+"_h";
			ShortUrlUtil.setShortUrl(key, h5Url);
			this.h5Url = ShortUrlUtil.getDomain()+"u?"+key;
		}else{
			this.h5Url = h5Url;
		}
	}
	@Override
	public String toString() {
		return "ShareUrlVO [weixinH5Url=" + weixinH5Url + ", h5Url=" + h5Url + "]";
	}
	
	/**
	 * 获取36进制的Userid
	 */
	public String get62UserId(){
		return StringUtil.intTo62(SessionUtil.getUserId());
	}
}
