package com.xnx3.wangmarket.shop.store.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Store;


/**
 * 用户控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreUserController")
@RequestMapping("/shop/store/user")
public class UserController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查看用户列表
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		User user = getUser();
		Store store = getStore();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store_user");
		sql.appendWhere("shop_store_user.storeid = "+store.getId());
		//查询条件
		//配置按某个字端搜索内容
		//sql.setSearchColumn(new String[] {"phone","userid"});
		int count = sqlService.count("shop_store_user", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		sql.appendWhere("shop_store_user.userid = user.id");
		sql.setSelectFromAndPage("SELECT user.* FROM user,shop_store_user", page);
		System.out.println(sql.getSql());
		// 按照上方条件查询出该实体总数 用集合来装
		List<Map<String,Object>> list = sqlService.findMapBySql(sql);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看用户列表");
		return "/shop/store/user/list";
			
	}
}
