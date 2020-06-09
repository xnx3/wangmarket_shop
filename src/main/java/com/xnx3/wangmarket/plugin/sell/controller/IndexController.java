package com.xnx3.wangmarket.plugin.sell.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.plugin.sell.vo.ShareUrlVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 二级分销，用户端接口
 * @author 管雷鸣
 */
@Controller(value="SellIndexPluginController")
@RequestMapping("/plugin/sell/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 获取分享的url
	 * @param redirectUrl 分享出去后，别人点击分享链接进入的页面url
	 * @param storeid 当前商铺的id
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="gainShareUrl${api.suffix}" ,method= {RequestMethod.POST})
	public ShareUrlVO gainShareUrl(HttpServletRequest request, Model model,
			@RequestParam(value = "redirectUrl", required = false, defaultValue = "") String redirectUrl,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		User user = getUser();	//当前登录的用户
		
		// redirectUrl 判断，若为空，给默认值，跳转到对方来源网址的首页
		if(redirectUrl.length() == 0){
			redirectUrl = request.getScheme()+ "://" + request.getServerName();
			if(request.getServerPort() != 80){
				redirectUrl = redirectUrl + ":" + request.getServerPort();
			}
			redirectUrl = redirectUrl + "/";
		}
		
		ShareUrlVO vo = new ShareUrlVO();
		vo.setWeixinH5Url(SystemUtil.get("MASTER_SITE_URL") + "plugin/weixinH5Auth/hiddenAuthJump.do?storeid="+storeid+"&referrerid="+user.getId()+"&url="+redirectUrl);
		
		//H5 Url
		if(redirectUrl.indexOf("?") > -1){
			redirectUrl = redirectUrl + "&";
		}else{
			redirectUrl = redirectUrl + "?";
		}
		redirectUrl = redirectUrl + "storeid="+storeid+"&referrerid="+user.getId();
		vo.setH5Url(redirectUrl);
		
		//日志
		ActionLogUtil.insert(request, "获取二级分销推广链接");
		return vo;
	}
	
}