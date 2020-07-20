package com.xnx3.wangmarket.plugin.sell.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.CacheUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.vo.CommissionLogListVO;
import com.xnx3.wangmarket.plugin.sell.vo.ShareUrlVO;
import com.xnx3.wangmarket.plugin.sell.vo.SubUserListVO;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.vo.StoreUserListVO;

/**
 * 短网址
 * @author 管雷鸣
 */
@Controller(value="SellShortUrlPluginController")
@RequestMapping("/")
public class ShortUrlController extends BasePluginController {
	@RequestMapping(value="u")
	public String gainShareUrl(HttpServletRequest request, Model model) {
		String key = request.getQueryString();
		if(key == null || key.length() == 0){
			return "error";
		}
		System.out.println(key);
		Object obj = CacheUtil.get("shorturl:"+key);
		if(obj == null){
			return error(model, "短网址不存在");
		}
		
		return redirect(obj.toString());
	}
	
}