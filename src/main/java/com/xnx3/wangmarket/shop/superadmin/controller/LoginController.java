package com.xnx3.wangmarket.shop.superadmin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller(value="ShopSuperAdminLoginController")
@RequestMapping("/shop/superadmin/login/")
public class LoginController extends BasePluginController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	
	/**
	 * 登陆页面
	 */
	@RequestMapping("login${url.suffix}")
	public String login(HttpServletRequest request,Model model){
		if(getUser() != null){
			ActionLogUtil.insert(request, "进入登录页面", "已经登录成功，无需再登录，进行跳转");
			return redirect("");
		}
		
		ActionLogUtil.insert(request, "进入登录页面");
		return redirect("wm/login/login.jsp");
	}
	
}
