package com.xnx3.wangmarket.shop_storeadmin.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.xnx3.j2ee.pluginManage.interfaces.SpringMVCInterceptorInterface;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.util.SessionUtil;

/**
 * shop项目,拦截器，拦截是否已登陆，未登录，跳转到登录页面
 * @author 管雷鸣
 *
 */
public class SpringMVCInterceptorLogin implements SpringMVCInterceptorInterface{

	@Override
	public List<String> pathPatterns() {
		List<String> list = new ArrayList<String>();
 		list.add("/shop/storeadmin/carouselImage/**");
 		list.add("/shop/storeadmin/goods/**");
 		list.add("/shop/storeadmin/goodsType/**");
 		list.add("/shop/storeadmin/index/**");
 		list.add("/shop/storeadmin/user/**");
 		return list;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		
		//未登录
		if(!SessionUtil.isLogin()){
			response.sendRedirect("/shop/storeadmin/login/login.do");
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
