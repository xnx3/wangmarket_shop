package com.xnx3.wangmarket.shop.superadmin.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.interfaces.SpringMVCInterceptorInterface;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;

/**
 * shop项目,拦截器，拦截是否已登陆
 * @author 管雷鸣
 *
 */
public class SpringMVCInterceptorLogin implements SpringMVCInterceptorInterface{

	@Override
	public List<String> pathPatterns() {
		List<String> list = new ArrayList<String>();
 		list.add("/shop/superadmin/index/**");
 		list.add("/shop/superadmin/store/**");
		list.add("/shop/superadmin/order/**");
		list.add("/shop/superadmin/goods/**");
 		return list;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		String path = request.getServletPath();
		
		//未登录
		if(!SessionUtil.isLogin()){
			response.sendRedirect("/shop/superadmin/login/login.do");
			return false;
		}
		
		User user = com.xnx3.j2ee.util.SessionUtil.getUser();
		if(!Func.isAuthorityBySpecific(user.getAuthority(), Global.roleId_admin+"")){
			//如果没有超级管理权限，那么无权使用
			response.sendRedirect("/403.do");
			return false;
		}
		
		return SpringMVCInterceptorInterface.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		SpringMVCInterceptorInterface.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		SpringMVCInterceptorInterface.super.afterCompletion(request, response, handler, ex);
	}
	
}
