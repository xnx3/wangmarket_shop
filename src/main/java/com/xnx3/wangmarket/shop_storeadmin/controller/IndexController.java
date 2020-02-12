package com.xnx3.wangmarket.shop_storeadmin.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.entity.Store;

/**
 * 代理后台
 * @author 管雷鸣
 */
@Controller(value="ShopStoreAdminIndexPluginController")
@RequestMapping("/shop/storeadmin/index/")
public class IndexController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 登录成功后的首页
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request,Model model){
		return "/shop/storeadmin/index/index";
	}
	
	/**
	 * 登录成功后的欢迎页面
	 */
	@RequestMapping("welcome${url.suffix}")
	public String welcome(HttpServletRequest request,Model model){
		Store store = getStore();
		
		model.addAttribute("store", store);
		return "/shop/storeadmin/index/welcome";
	}
	
}