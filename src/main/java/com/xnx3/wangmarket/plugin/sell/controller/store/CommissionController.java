package com.xnx3.wangmarket.plugin.sell.controller.store;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xnx3.BaseVO;
import com.xnx3.wangmarket.plugin.sell.vo.SellCommissionLogListVO;
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
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 二级分销，商家管理后台
 * @author 管雷鸣
 */
@Controller(value="SellStoreCommissionPluginApiController")
@RequestMapping("/plugin/api/sell/store/commission/")
public class CommissionController extends BasePluginController {
	@Resource
	private SqlService sqlService;


	/**
	 * 查看佣金记录列表
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value = "/list${api.suffix}",method = {RequestMethod.POST})
	public SellCommissionLogListVO list(HttpServletRequest request, Model model,
										@RequestParam(value = "everyNumber", required = false, defaultValue = "30") int everyNumber) {

		SellCommissionLogListVO vo = new SellCommissionLogListVO();

		if(!haveStoreAuth()){
			vo.setBaseVO(BaseVO.FAILURE,"请先登录");
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
		Page page = new Page(count, everyNumber, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM plugin_sell_commission_log ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		// 按照上方条件查询出该实体总数 用集合来装
		List<SellCommissionLog> list = sqlService.findBySql(sql,SellCommissionLog.class);

		// 将信息保存到model中 
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看佣金记录列表");
		return vo;
	}

}