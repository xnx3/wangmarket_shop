package com.xnx3.wangmarket.plugin.sell.controller.store;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 二级分销，商家管理后台
 * @author 管雷鸣
 */
@Controller(value="SellStoreTixianPluginController")
@RequestMapping("/plugin/sell/store/tixian/")
public class TixianController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	

	/**
	 * 查看提现申请记录列表
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
		sql.setSearchTable("plugin_sell_tixian_log");
		//查询条件
		sql.appendWhere("storeid = " + store.getId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"userid","state"});
		// 查询数据表的记录总条数
		int count = sqlService.count("plugin_sell_tixian_log", sql.getWhere());
		
		// 配置每页显示30条
		Page page = new Page(count, 30, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM plugin_sell_tixian_log ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		// 按照上方条件查询出该实体总数 用集合来装
		List<SellTiXianLog> list = sqlService.findBySql(sql,SellTiXianLog.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看提现申请记录列表");
		return "plugin/sell/store/tixian/list";
	}
	
	/**
	 * 查看提现详情
	 * @param id {@link SellTiXianLog}.id 要查看的是哪个提现的信息
	 */
	@RequestMapping("/detail${url.suffix}")
	public String detail(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		if(!haveStoreAuth()){
			return error(model,"请先登录");
		}
		Store store = SessionUtil.getStore();
		
		SellTiXianLog log = sqlService.findById(SellTiXianLog.class, id);
		if(log == null){
			return error(model, "提现记录不存在");
		}
		if(log.getStoreid() - store.getId() != 0){
			return error(model, "记录不属于你，无权查看");
		}
		
		
		model.addAttribute("log", log);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看提现申请记录", log.toString());
		return "plugin/sell/store/tixian/detail";
	}
	
	/**
	 * 已转账，也就是同意提现，并已给对方转账完毕。
	 * @param id {@link SellTiXianLog}.id 要操作的是哪个提现的信息
	 * @param state 审核，要将state字段变为的值，可传入 1审核通过、2拒绝
	 */
	@ResponseBody
	@RequestMapping("/audit${api.suffix}")
	public BaseVO audit(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "state", required = false, defaultValue = "0") short state) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		Store store = SessionUtil.getStore();
		
		SellTiXianLog log = sqlService.findById(SellTiXianLog.class, id);
		if(log == null){
			return error("提现记录不存在");
		}
		if(log.getStoreid() - store.getId() != 0){
			return error("记录不属于你，无权查看");
		}
		if(log.getState() - 0 != 0){
			return error("状态不符，请不要重复操作");
		}
		
		if(state == 1 || state == 2){
			log.setState(state);
		}else{
			return error("state参数不符");
		}
		sqlService.save(log);
		
		if(state - 1 == 0){
			//同意提现申请，并且处理完成了，那么要将这个提现申请的addtime之前的 SellCommissionLog 的 transferState 变为已提现
			String sql = "UPDATE plugin_sell_commission_log SET transfer_state = 1 WHERE userid = "+log.getUserid()+" AND storeid = "+log.getStoreid()+" AND addtime <= "+log.getAddtime()+" AND transfer_state = 0";
			ConsoleUtil.log(sql);
			sqlService.executeSql(sql);
		}
		
		//日志记录
		ActionLogUtil.insert(request, log.getId(), "提现申请审核", log.toString());
		return success();
	}
	
}