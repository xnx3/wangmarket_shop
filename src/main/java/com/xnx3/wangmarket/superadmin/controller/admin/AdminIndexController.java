package com.xnx3.wangmarket.superadmin.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.admin.G;

/**
 * 管理后台首页
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/admin/index")
public class AdminIndexController extends BaseController{
	
	/**
	 * 总管理后台，登陆成功后的欢迎页面
	 */
	@RequestMapping("welcome${url.suffix}")
	public String welcome(HttpServletRequest request, Model model){
		User user = getUser();
		ActionLogUtil.insert(request, "总管理后台，登陆成功后的欢迎页面");
		
		model.addAttribute("user", user);
		model.addAttribute("version", G.VERSION);	//版本号
		return "/iw_update/admin/index/welcome";
	}
	
}
