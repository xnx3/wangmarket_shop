package com.xnx3.wangmarket.shop.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.vo.OrderRuleVO;

/**
 * 自定义订单规则
 * @author 管雷鸣
 */
@Controller(value="ShopOrderRuleController")
@RequestMapping("/shop/api/orderRule/")
public class OrderRuleController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 获取某个店铺的订单规则流程
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param storeid 要获取的信息是那个店铺的，店铺的id
	 * @author 管雷鸣
	 * @return 订单规则
	 */
	@RequestMapping(value="getRule.json", method = RequestMethod.POST)
	@ResponseBody
	public OrderRuleVO getRule(HttpServletRequest request,
			@RequestParam(value = "storeid", required = true, defaultValue = "0") int storeid) {
		OrderRule orderRule = sqlCacheService.findById(OrderRule.class, storeid);
		OrderRuleVO vo = new OrderRuleVO();
		vo.setOrderRule(orderRule);
		//写日志
		ActionLogUtil.insert(request, "获取店铺的订单规则", orderRule.toString());
		return vo;
	}
	
}