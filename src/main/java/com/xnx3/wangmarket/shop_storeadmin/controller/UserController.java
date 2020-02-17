package com.xnx3.wangmarket.shop_storeadmin.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;


/**
 * 用户控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreAdminUserController")
@RequestMapping("/shop/storeadmin/user")
public class UserController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查看用户列表
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("user");
		//查询条件
		//配置按某个字端搜索内容
		//sql.setSearchColumn(new String[] {"phone","userid"});
		// 查询数据表的记录总条数
		//int count = sqlService.count("shop_address", sql.getWhere());
		int count = sqlService.count("user", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		
		String sqlQuery = "SELECT * FROM user";
		
		// 查询出总页数
		sql.setSelectFromAndPage(sqlQuery, page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<Map<String,Object>> list = sqlService.findMapBySql(sql);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看用户列表");
		return "/shop/storeadmin/user/list";
			
	}
}
