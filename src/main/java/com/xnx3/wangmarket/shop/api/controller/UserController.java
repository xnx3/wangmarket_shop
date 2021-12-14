package com.xnx3.wangmarket.shop.api.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UserVO;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;

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
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @author 管雷鸣
	 * @return 用户信息
	 */
	@ResponseBody
	@RequestMapping(value="getUser.json", method = RequestMethod.POST)
	public UserVO getUser(HttpServletRequest request) throws IOException{
		//日志记录
		ActionLogUtil.insert(request, "获取当前登录的用户信息");
		UserVO vo = new UserVO();
		User user = getUser();
		vo.setUser(user);
		return vo;
	}
	
	/**
	 * 修改密码，如果使用的是账号、密码方式注册、登录的话。
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @author 管雷鸣
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping(value="updatePassword", method = RequestMethod.POST)
	public BaseVO updatePassword(HttpServletRequest request, 
			@RequestParam(value = "oldPassword", required = true, defaultValue = "") String oldPassword,
			@RequestParam(value = "newPassword", required = true, defaultValue = "") String newPassword){
		if(oldPassword.length() == 0){
			//日志记录
			ActionLogUtil.insert(request, "修改密码", "失败：未输入旧密码");
			return error("请输入旧密码");
		}else{
			User user=sqlService.findById(User.class, getUser().getId());
			//将输入的原密码进行加密操作，判断原密码是否正确
			
			if(new Md5Hash(oldPassword, user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString().equals(user.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					//日志记录
					ActionLogUtil.insertUpdateDatabase(request, "修改密码", "成功");
					return success("修改成功");
				}else{
					//日志记录
					ActionLogUtil.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(vo.getInfo());
				}
			}else{
				//日志记录
				ActionLogUtil.insert(request, "修改密码", "失败：原密码错误");
				return error("原密码错误！");
			}
		}
	}
	
	/**
	 * 修改用户昵称
	 * @param nickname 要修改为的昵称。不允许为空，字符限制1～15个汉字、英文、数字
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @return 执行结果
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="updateNickname.json", method = RequestMethod.POST)
	public BaseVO updateNickname(HttpServletRequest request,
			@RequestParam(value = "nickname", required = true, defaultValue = "") String nickname){
		//日志记录
		ActionLogUtil.insert(request, "修改昵称", StringUtil.filterXss(nickname));
		return userService.updateNickname(request);
	}
	

	/**
	 * 上传头像
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param head 要上传的头像。如果上传的图片超过500，会自动被压缩为宽度500的尺寸
	 * @return 操作结果
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="uploadHead.json", method = RequestMethod.POST)
	public BaseVO uploadHead(HttpServletRequest request){
		BaseVO vo = userService.updateHeadByOSS(request, "head", 500);
		//日志记录
		ActionLogUtil.insert(request, "修改头像", getUser().toString());
		return vo;
	}
	
}
