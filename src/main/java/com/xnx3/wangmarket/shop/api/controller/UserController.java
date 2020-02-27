package com.xnx3.wangmarket.shop.api.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.wangmarket.shop.api.vo.UserVO;

/**
 * 当前登录的用户相关
 * @author 管雷鸣
 */
@Controller(value="ShopUserController")
@RequestMapping("/shop/api/user/")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	

	/**
	 * 获取当前登录的用户信息
	 */
	@ResponseBody
	@RequestMapping("/getUser${api.suffix}")
	public UserVO getUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ActionLogUtil.insert(request, "获取当前登录的用户信息");
		UserVO vo = new UserVO();
		User user = getUser();
		vo.setUser(user);
		return vo;
	}
	
}
