package com.xnx3.wangmarket.shop.store.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.xnx3.j2ee.pluginManage.interfaces.SpringMVCInterceptorInterface;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * shop项目,拦截器，拦截是否已登陆，未登录，跳转到登录页面
 * @author 管雷鸣
 *
 */
public class SpringMVCInterceptorLogin implements SpringMVCInterceptorInterface{

	@Override
	public List<String> pathPatterns() {
		List<String> list = new ArrayList<String>();
 		list.add("/shop/store/carouselImage/**");
 		list.add("/shop/store/common/**");
 		list.add("/shop/store/goods/**");
 		list.add("/shop/store/goodsType/**");
 		list.add("/shop/store/index/**");
 		list.add("/shop/store/order/**");
 		list.add("/shop/store/orderRule/**");
 		list.add("/shop/store/paySet/**");
 		list.add("/shop/store/user/**");
 		list.add("/shop/store/sms/**");
 		list.add("/shop/store/email/**");
 		return list;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		
		//未登录
		if(!SessionUtil.isLogin()){
			response.sendRedirect("/shop/store/login/login.do");
			return false;
		}
		
		Store store = SessionUtil.getStore();
		if(store == null || store.getId() == null || store.getId() - 0 == 0){
			//无权使用，自己没有所管理的店铺
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
