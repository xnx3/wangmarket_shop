package com.xnx3.wangmarket.shop.superadmin.controller;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 超级管理后台
 * @author 魏繁中
 */
@Controller(value="ShopSuperAdminIndexController")
@RequestMapping("/shop/superadmin/index/")
public class IndexController extends BasePluginController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
    /**
     * 登录后的欢迎页面
     */
    @RequestMapping("welcome${url.suffix}")
    public String welcome(HttpServletRequest request, Model model){
        ActionLogUtil.insert(request,"总管理后台，登陆成功后的欢迎页面");

        model.addAttribute("version",Global.VERSION); // 版本号
        return "/iw_update/admin/index/welcome";
    }
    
    /**
	 * 修改密码，如果使用的是账号、密码方式注册、登录的话。
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	@ResponseBody
	@RequestMapping(value="updatePassword${url.suffix}", method = RequestMethod.POST)
	public BaseVO updatePassword(HttpServletRequest request, 
			@RequestParam(value = "oldPassword", required = false, defaultValue = "") String oldPassword,
			@RequestParam(value = "newPassword", required = false, defaultValue = "") String newPassword){
		if(oldPassword.length() == 0){
			ActionLogUtil.insert(request, "修改密码", "失败：未输入旧密码");
			return error("请输入旧密码");
		}else{
			User user=sqlService.findById(User.class, getUser().getId());
			//将输入的原密码进行加密操作，判断原密码是否正确
			
			if(new Md5Hash(oldPassword, user.getSalt(),com.xnx3.j2ee.Global.USER_PASSWORD_SALT_NUMBER).toString().equals(user.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					ActionLogUtil.insertUpdateDatabase(request, "修改密码", "成功");
					return success("修改成功");
				}else{
					ActionLogUtil.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(vo.getInfo());
				}
			}else{
				ActionLogUtil.insert(request, "修改密码", "失败：原密码错误");
				return error("原密码错误！");
			}
		}
	}
    
    
}
