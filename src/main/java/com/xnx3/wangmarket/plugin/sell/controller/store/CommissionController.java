package com.xnx3.wangmarket.plugin.sell.controller.store;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 二级分销，商家管理后台
 * @author 管雷鸣
 */
@Controller(value="SellStoreCommissionPluginController")
@RequestMapping("/plugin/sell/store/commission/")
public class CommissionController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	

	/**
	 * 查看佣金记录列表
	 * @author 管雷鸣
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		if(!haveStoreAuth()){
			return error(model,"请先登录");
		}
		Store store = SessionUtil.getStore();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("plugin_sell_commission_log");
		//查询条件
		sql.appendWhere("storeid = " + store.getId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"userid","transfer_state", "<=addtime"});
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
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看佣金记录列表");
		return "plugin/sell/store/commission/list";
	}
	
}