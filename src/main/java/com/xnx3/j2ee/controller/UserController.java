package com.xnx3.j2ee.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 用户User的相关操作
 * @author 管雷鸣
 */
@Controller(value="WMUserController")
@RequestMapping("/user")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	/**
	 * 用户退出，页面跳转提示。
	 */
	@RequestMapping("logout${url.suffix}")
	public String logout(Model model, HttpServletRequest request){
		ActionLogUtil.insert(request, "注销登录");
		userService.logout();
		return success(model, "注销登录成功", "login.do");
	}
	

	/**
	 * 修改密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	@RequiresPermissions("userUpdatePassword${url.suffix}")
	@RequestMapping(value="updatePassword", method = RequestMethod.POST)
	public String updatePassword(HttpServletRequest request, String oldPassword,String newPassword,Model model){
		if(oldPassword==null){
			ActionLogUtil.insert(request, "修改密码", "失败：未输入密码");
			return error(model, "请输入旧密码");
		}else{
			User uu=sqlService.findById(User.class, getUser().getId());
			//将输入的原密码进行加密操作，判断原密码是否正确
			
			if(new Md5Hash(oldPassword, uu.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString().equals(uu.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					ActionLogUtil.insertUpdateDatabase(request, "修改密码", "成功");
					return success(model, "修改成功");
				}else{
					ActionLogUtil.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(model, vo.getInfo());
				}
			}else{
				ActionLogUtil.insert(request, "修改密码", "失败：原密码错误");
				return error(model, "原密码错误！");
			}
		}
	}

}
