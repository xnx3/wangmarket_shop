package com.xnx3.wangmarket.shop.store.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;


/**
 * 订单规则管理。每个商铺都可以有一套自己的订单状态规则
 * @author 管雷鸣
 */
@Controller(value="ShopStoreOrderRuleController")
@RequestMapping("/shop/store/orderRule")
public class OrderRuleController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看当前规则
	 * @author 管雷鸣
	 */
	@RequestMapping("/index${url.suffix}")
	public String index(HttpServletRequest request,Model model) {
		//从数据库中获取，同时还能判断如果没有，创建这条记录
		OrderRule orderRule = sqlService.findById(OrderRule.class, getStoreId());
		if(orderRule == null){
			orderRule = new OrderRule();
			orderRule.setId(getStoreId());
			sqlService.save(orderRule);
		}
		
		model.addAttribute("orderRule", orderRule);
		//日志记录
		ActionLogUtil.insert(request, "查看规则");
		return "/shop/store/orderRule/index";
	}
	
	
	/**
	 * 设置某个订单状态是否启用
	 * @author 管雷鸣
	 * @param name 订单状态，传入的也就是 {@link OrderRule} 的字段，传入如 distribution 、refund 、 notPayTimeout
	 * @param value 值。0或者1  ，1使用，0不使用
	 */
	@ResponseBody
	@RequestMapping(value="save${url.suffix}", method = RequestMethod.POST)
	public BaseVO save(Model model ,HttpServletRequest request,
		@RequestParam(value = "name", required = false, defaultValue = "") String name,
		@RequestParam(value = "value", required = false, defaultValue = "0") int value) {
		
		OrderRule orderRule = sqlService.findById(OrderRule.class, getStoreId());
		if(orderRule == null){
			orderRule = new OrderRule();
			orderRule.setId(getStoreId());
		}
		if(name.equals("distribution")){
			orderRule.setDistribution((short) value);
		}else if(name.equals("refund")){
			orderRule.setRefund((short) value);
		}else if(name.equals("notPayTimeout")){
			orderRule.setNotPayTimeout(value);
		}else{
			ConsoleUtil.debug("异常，name:"+name);
			return error("name异常");
		}
		sqlService.save(orderRule);
		//更新持久缓存
		sqlCacheService.deleteCacheById(OrderRule.class, orderRule.getId());
		
		ActionLogUtil.insert(request, "保存订单规则的值", orderRule.toString());
		
		return success();
	}

}
