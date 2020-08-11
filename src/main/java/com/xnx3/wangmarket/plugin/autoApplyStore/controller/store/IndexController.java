package com.xnx3.wangmarket.plugin.autoApplyStore.controller.store;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Store;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

import java.util.List;

/**
 * 自助开店，商家后台
 */
@Controller(value="AutoApplyStoreStoreIndexPluginController")
@RequestMapping("/plugin/autoApplyStore/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;

	/**
	 * 查看申请开店列表
	 * @author 刘鹏
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request, Model model) {
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store");
		sql.appendWhere("referrer_userid = "+getUserId());
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_store", sql.getWhere());
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_store ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("addtime ASC");
		//按照上方条件查询出该实体总数 用集合来装
		List<Store> list = sqlService.findBySql(sql, Store.class);
		// 将信息保存到model中
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看开店申请");
		return "plugin/autoApplyStore/list";

	}
}