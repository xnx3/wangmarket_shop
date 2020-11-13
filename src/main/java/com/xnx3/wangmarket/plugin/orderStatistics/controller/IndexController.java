package com.xnx3.wangmarket.plugin.orderStatistics.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xnx3.wangmarket.plugin.sell.vo.SellStoreSetVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.plugin.orderStatistics.vo.OrderStatisticsVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 订单统计
 */
@Controller(value="OrderStatisticsIndexPluginApiController")
@RequestMapping("/plugin/orderStatistics/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 根据开始时间跟结束时间，获取订单的统计信息
	 * @param startTime 订单开始时间
	 * @param endTime 订单结束时间
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "statistics${api.suffix}",method = {RequestMethod.POST})
	public OrderStatisticsVO index(HttpServletRequest request,Model model,
			@RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
			@RequestParam(value = "endTime", required = false, defaultValue = "") String endTime){
		OrderStatisticsVO vo = new OrderStatisticsVO();
//		if(!haveStoreAuth()){
//			vo.setBaseVO(com.xnx3.BaseVO.FAILURE,"请先登录");
//			return vo;
//		}
		//当前登录的商家
		Store store = SessionUtil.getStore();
		
		
		
		/*
		 * 
		 * 需要将传入的时间转成linux时间戳（10位）。可以使用此转换： DateUtil.StringToInt(time, format)
		 * 参考文档： https://github.com/xnx3/xnx3_util/blob/master/src/main/java/com/xnx3/DateUtil.java
		 */
		
		
		//查询的sql
		String sql = "SELECT COUNT(id) AS orderNumber,SUM(pay_money) AS money,state FROM shop_order WHERE addtime > 0 GROUP BY shop_order.state";
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery(sql);
		
		ActionLogUtil.insertUpdateDatabase(request, "统计订单数据显示");
		vo.setList(list);
		return vo;
	}
}