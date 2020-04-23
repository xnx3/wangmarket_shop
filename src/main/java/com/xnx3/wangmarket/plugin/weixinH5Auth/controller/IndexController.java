package com.xnx3.wangmarket.plugin.weixinH5Auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.StringUtil;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinUtil;

/**
 * 跳转页面，获取用户openid
 * @author 管雷鸣
 */
@Controller(value="WeixinH5AuthIndexPluginController")
@RequestMapping("/plugin/weixinH5Auth/")
public class IndexController extends BasePluginController {
	@Resource
	private WeiXinService weiXinService;
	

	/**
	 * 微信h5授权获取用户openid，也仅仅只是获取用户的openid，获取不到其他信息。
	 * 进入后会直接进行重定向到传入的url页面，在这个url页面即可或得到用户的openid 。注意，传入的url，域名一定是提前在微信公众号接口权限表-网页服务-网页帐号-网页授权获取用户基本信息 中配置的网址
	 * @param url 要跳转到的url页面，这里可以拿到用户的openid。格式如： http://demo.imall.net.cn/index.html%3Fa=1%26b=2  注意，像是 ? & 要传入URL转码字符 
	 */
	@RequestMapping("hiddenAuthJump${url.suffix}")
	public String hiddenAuthJump(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "url", required = false, defaultValue="") String url){
		if(storeid < 1){
			return error(model, "请传入店铺编号");
		}
		
		WeiXinUtil util = weiXinService.getWeiXinUtil(storeid);
		if(util == null){
			return error(model, "当前店铺未设置微信公众号");
		}
		String jumpUrl = util.getOauth2SimpleUrl(url);
		return redirect(StringUtil.urlToString(jumpUrl));
	}
	
	
}