package com.xnx3.wangmarket.plugin.firstOrderAward.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.SMSService;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 推广用户，用户下单消费后，推广人获赠某个商品。用户端使用的
 * @author 管雷鸣
 */
@Controller(value="FirstOrderAwardIndexPluginController")
@RequestMapping("/plugin/firstOrderAward/stire/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private SMSService smsService;
	
	/**
	 * 分享出去的链接，点击链接进入的url，也就是入口url,拿到openid自动注册为用户,并关联推荐人
	 * 
	 * http://shop.imall.net.cn/plugin/weixinH5Auth/hiddenAuthJump.do?storeid=1&url=http://shop.imall.net.cn/plugin/firstOrderAward/stire/shareEntry.do%3Finviteid=1%26storeid=1%26storeidaa=1
	 * 
	 * @param inviteid 邀请人userid
	 * @param storeid 进入的商城的id， store.id
	 * @return 若成功，info返回session id
	 */
	@RequestMapping("shareEntry${url.suffix}")
	public String shareEntry(HttpServletRequest request,Model model,
			@RequestParam(value = "inviteid", required = false, defaultValue="0") int inviteid,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		System.out.println(request.getQueryString());
		
//		smsService.send(4356, "17076012262", "恭喜您，成功邀请到一位好友", "tongzhi", request);
		
		return "plugin/firstOrderAward/store/setAward";
	}

	
}