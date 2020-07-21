package com.xnx3.wangmarket.plugin.sell.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.CacheUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

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
			return redirect("404.do");
		}
		Object obj = CacheUtil.get("shorturl:"+key);
		if(obj == null){
			return redirect("404.do");
		}
		ConsoleUtil.log(key+" : "+obj.toString());
		return redirect(obj.toString());
	}
	
}