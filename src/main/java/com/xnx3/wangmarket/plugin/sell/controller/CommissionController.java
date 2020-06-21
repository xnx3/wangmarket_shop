package com.xnx3.wangmarket.plugin.sell.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.DateUtil;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;
import com.xnx3.wangmarket.plugin.sell.vo.CommissionLogListVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

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
	 * 查看自己的佣金记录列表
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
	
	/**
	 * 获取当前待提现的佣金，也就是可结算的佣金，还未提现的佣金
	 * @param storeid 当前商铺的id
	 * @return {@link BaseVO} result:1 成功，可用getInfo() 获取到待结算佣金数目，单位是分。 如果result为0，那么执行失败，用 getInfo() 获取失败原因显示给用户
	 */
	@ResponseBody
	@RequestMapping(value="/getWaitWithdraw${api.suffix}",method= {RequestMethod.POST})
	public BaseVO getWaitWithdraw(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		User user = getUser();
		
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery("SELECT SUM(money) AS allmoney FROM plugin_sell_commission_log WHERE userid = "+user.getId()+" AND storeid = "+ storeid+" AND transfer_state = 0");
		if(list.size() > 0){
			Map<String, Object> map = list.get(0);
			if(map.get("allmoney") != null){
				int allmoney = (int) map.get("allmoney");
				//日志记录
				ActionLogUtil.insert(request,"获取当前待提现的佣金", allmoney+"");
				return success(allmoney+"");
			}
		}
		
		return error("执行错误");
	}
	

	/**
	 * 获取当前已提现成功的佣金总数
	 * @param storeid 当前商铺的id
	 * @return {@link BaseVO} result:1 成功，可用getInfo() 获取到待结算佣金数目，单位是分
	 */
	@ResponseBody
	@RequestMapping(value="/getAlreadyWithdraw${api.suffix}",method= {RequestMethod.POST})
	public BaseVO getAlreadyWithdraw(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		User user = getUser();
		
		int allmoney = ketixianAllMoney(user.getId(), storeid, DateUtil.timeForUnix10());
		//日志记录
		ActionLogUtil.insert(request,"获取当前待提现的佣金", allmoney+"");
		return success(allmoney+"");
	}
	
	/**
	 * 获取当前用户的可提现金额
	 * @param userid User.id
	 * @param storeid Store.id
	 * @return 可提现金额，单位是分。如果出错，则返回0
	 */
	private int ketixianAllMoney(int userid, int storeid, int addtime){
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery("SELECT SUM(money) AS allmoney FROM plugin_sell_commission_log WHERE userid = "+userid+" AND storeid = "+ storeid+" AND transfer_state = 0 AND addtime >="+addtime);
		if(list.size() > 0){
			Map<String, Object> map = list.get(0);
			if(map.get("allmoney") != null){
				int allmoney = (int) map.get("allmoney");
				return allmoney;
			}
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping(value="/applyWithdraw${api.suffix}",method= {RequestMethod.POST})
	public BaseVO applyWithdraw(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid,
			@RequestParam(value = "card", required = false, defaultValue = "") String card,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "username", required = false, defaultValue = "") String username) {
		User user = getUser();
		
		//此接口10秒内最多可以请求一次
		
		
		
		//获取店铺的二级分销设置
		SellStoreSet sellStoreSet = sqlCacheService.findById(SellStoreSet.class, storeid);
		
		//当前时间
		int currentTime = DateUtil.timeForUnix10();
		//可提现金额
		int allmoney = ketixianAllMoney(user.getId(), storeid, currentTime);
		
		//判断可提现金额是否已经达到了提现标准
		if(allmoney < sellStoreSet.getMoney()){
			return error("未达到可提现标准，当前可提现金额"+(allmoney/100)+"元，需要超过"+(sellStoreSet.getMoney()/100)+"元才可以申请提现");
		}
		
		//添加提现记录
		SellTiXianLog tixian = new SellTiXianLog();
		tixian.setAddtime(currentTime);
		tixian.setCard(StringUtil.filterXss(card));
		tixian.setMoney(allmoney);
		tixian.setPhone(StringUtil.filterXss(phone));
		tixian.setState((short) 0);
		tixian.setStoreid(storeid);
		tixian.setUserid(user.getId());
		tixian.setUsername(StringUtil.filterXss(username));
		sqlService.save(tixian);
		
		//记录日志
		ActionLogUtil.insertUpdateDatabase(request,"发起提现申请", tixian.toString());
		
		return success(tixian.getId()+"");
	}
}