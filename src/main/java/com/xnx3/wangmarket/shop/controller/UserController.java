package com.xnx3.wangmarket.shop.controller;

import java.awt.Font;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.media.CaptchaUtil;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop.util.SessionUtil;
import com.xnx3.wangmarket.shop.vo.UserVO;

/**
 * 当前登录的用户相关
 * @author 管雷鸣
 */
@Controller(value="ShopUserController")
@RequestMapping("/shop/user/")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	

	/**
	 * 获取当前登录的用户信息
	 */
	@RequestMapping("/getUser${url.suffix}")
	public UserVO getUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ActionLogUtil.insert(request, "获取当前登录的用户信息");
	    
		UserVO vo = new UserVO();
		User user = getUser();
		vo.setUser(user);
		return vo;
	}
	
}
