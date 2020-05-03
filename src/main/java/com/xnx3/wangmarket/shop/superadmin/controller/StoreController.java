package com.xnx3.wangmarket.shop.superadmin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 商家管理
 * @author 管雷鸣
 */
@Controller(value="ShopSuperAdminStoreController")
@RequestMapping("/shop/superadmin/store/")
public class StoreController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	

	/**
	 * 查看商家列表
	 * @author 管雷鸣
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store");
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"name","contacts","phone"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_store", sql.getWhere());
		// 配置每页显示50条
		Page page = new Page(count, 50, request);
		sql.setSelectFromAndPage("SELECT * FROM shop_store ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		List<Store> list = sqlService.findBySql(sql,Store.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, "查看商家列表");
		return "/shop/superadmin/store/list";
	}
	

	/**
	 * 开通店铺
	 */
	@RequestMapping("add${url.suffix}")
	public String add(HttpServletRequest request, Model model){
		ActionLogUtil.insert(request, "进入开通店铺的页面");
		return "/shop/superadmin/store/edit";
	}
	
}
