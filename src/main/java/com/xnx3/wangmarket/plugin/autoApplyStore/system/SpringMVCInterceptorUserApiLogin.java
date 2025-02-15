package com.xnx3.wangmarket.plugin.autoApplyStore.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.xnx3.j2ee.pluginManage.interfaces.SpringMVCInterceptorInterface;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 拦截器，拦截是否已登陆
 * @author 管雷鸣
 *
 */
public class SpringMVCInterceptorUserApiLogin implements SpringMVCInterceptorInterface{

	@Override
	public List<String> pathPatterns() {
		List<String> list = new ArrayList<String>();
 		list.add("/plugin/autoApplyStore/store/*.json");
 		return list;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//未登录
		if(!SessionUtil.isLogin()){
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write("{"
    				+ "\"result\":\"" + BaseVO.NOT_LOGIN + "\","
    				+ "\"info\":\"not login\""
				+ "}");
			return false;
		}
		
		Store store = SessionUtil.getStore();
		if(store == null || store.getId() == null || store.getId() - 0 == 0){
			//无权使用
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write("{"
    				+ "\"result\":\"" + BaseVO.NOT_LOGIN + "\","
    				+ "\"info\":\"403 not auth\""
				+ "}");
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
