package com.xnx3.wangmarket.plugin.sell.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.vo.CommissionLogListVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 用户自己的佣金管理
 * @author 管雷鸣
 */
@Controller(value="SellCommissionPluginController")
@RequestMapping("/plugin/sell/commission/")
public class CommissionController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看自己所赚取的佣金记录列表
	 * @param storeid 当前商铺的id
	 */
	@ResponseBody
	@RequestMapping(value="/list${api.suffix}",method= {RequestMethod.POST})
	public CommissionLogListVO list(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		CommissionLogListVO vo = new CommissionLogListVO();
		User user = getUser();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("plugin_sell_commission_log");
		//查询条件
		sql.appendWhere("userid = " + user.getId()+" AND storeid = "+storeid);
		//配置按某个字端搜索内容
//		sql.setSearchColumn(new String[] {"typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("plugin_sell_commission_log", sql.getWhere());
		
		// 配置每页显示30条
		Page page = new Page(count, 30, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM plugin_sell_commission_log ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		// 按照上方条件查询出该实体总数 用集合来装
		List<SellCommissionLog> list = sqlService.findBySql(sql,SellCommissionLog.class);
		
		vo.setList(list);
		vo.setPage(page);
		
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看自己的佣金记录列表");
		return vo;
	}
	
}