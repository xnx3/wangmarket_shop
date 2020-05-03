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
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller(value="ShopSuperAdminLoginController")
@RequestMapping("/shop/superadmin/login/")
public class LoginController extends BaseController {
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
		return "/shop/superadmin/login/login";
	}

	/**
	 * 登陆请求验证
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交三个参数：username(用户名/邮箱)、password(密码)、code（图片验证码的字符）
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="loginSubmit${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO loginSubmit(HttpServletRequest request,Model model){
		LoginVO vo = new LoginVO();
		
		//验证码校验
		BaseVO capVO = com.xnx3.j2ee.util.CaptchaUtil.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			ActionLogUtil.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			vo.setBaseVO(capVO);
			return vo;
		}else{
			//验证码校验通过
			
			BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
			vo.setBaseVO(baseVO);
			if(baseVO.getResult() == BaseVO.SUCCESS){
				User user = getUser();
				
				//判断是否有总管理后台的权利
				if(!Func.isAuthorityBySpecific(user.getAuthority(), Global.roleId_admin+"")){
					//退出登录
					SessionUtil.isLogin();
					vo.setBaseVO(LoginVO.FAILURE, "无权使用");
					return vo;
				}
				ActionLogUtil.insert(request, "用户名密码模式登录成功");
				
				//将sessionid加入vo返回
				HttpSession session = request.getSession();
				vo.setToken(session.getId());
				
				//加入user信息
				vo.setUser(getUser());
			}else{
				ActionLogUtil.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
			}
			
			return vo;
		}
	}
	
}
