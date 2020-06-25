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
import com.xnx3.CacheUtil;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.Global;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;
import com.xnx3.wangmarket.plugin.sell.vo.SellTiXianLogVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 用户自己的佣金管理
 * @author 管雷鸣
 */
@Controller(value="SellTixianPluginController")
@RequestMapping("/plugin/sell/tixian/")
public class TixianController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	
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
		
		int allmoney = ketixianAllMoney(user.getId(), storeid, DateUtil.timeForUnix10());
		//日志记录
		ActionLogUtil.insert(request,"获取当前待提现的佣金", allmoney+"");
		return success(allmoney+"");
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
		
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery("SELECT SUM(money) AS allmoney FROM plugin_sell_tixian_log WHERE userid = "+user.getId()+" AND storeid = "+ storeid+" AND state = 1 ");
		if(list.size() > 0){
			Map<String, Object> map = list.get(0);
			if(map.get("allmoney") != null){
				int allmoney = Lang.stringToInt(map.get("allmoney").toString(), 0);
				//日志记录
				ActionLogUtil.insert(request,"获取当前已提现成功的佣金总数", allmoney+"");
				return success(allmoney+"");
			}
		}
		
		return error("异常");
	}
	
	/**
	 * 获取当前用户的可提现金额
	 * @param userid User.id
	 * @param storeid Store.id
	 * @return 可提现金额，单位是分。如果出错，则返回0
	 */
	private int ketixianAllMoney(int userid, int storeid, int addtime){
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery("SELECT SUM(money) AS allmoney FROM plugin_sell_commission_log WHERE userid = "+userid+" AND storeid = "+ storeid+" AND transfer_state = 0 AND addtime <="+addtime);
		if(list.size() > 0){
			Map<String, Object> map = list.get(0);
			if(map.get("allmoney") != null){
				int allmoney = Lang.stringToInt(map.get("allmoney").toString(), 0);
				return allmoney;
			}
		}
		return 0;
	}
	
	/**
	 * 申请提现
	 * @param storeid 要申请提现的storeid
	 * @param card 提现的卡号，如支付宝账户
	 * @param phone 手机号。提现处理后，会自动向这个手机号发送一条短信通知用户（如果商家已经开通短信通道的话）
	 * @param username 提现的用户姓名，如提现到支付宝账户，这里便是支付宝账户的实名的姓名，方便转账的时候查看是否能对应起来
	 * @return 如果成功，getInfo() 返回当前提现申请记录的 id 编号，可以根据这个编号查此次提现的信息
	 */
	@ResponseBody
	@RequestMapping(value="/applyWithdraw${api.suffix}",method= {RequestMethod.POST})
	public BaseVO applyWithdraw(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid,
			@RequestParam(value = "card", required = false, defaultValue = "") String card,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "username", required = false, defaultValue = "") String username) {
		User user = getUser();
		
		//此接口10秒内最多可以请求一次
		//先判断
		String key = Global.CACHE_KEY_APPLY_WITHDRAW_ALLOW.replace("{storeid}", storeid+"").replace("{userid}", user.getId()+"");
		if(CacheUtil.get(key) != null){
			return error("安全限制10秒内只可请求一次此接口，请稍后再试");
		}
		//增加10秒限制
		CacheUtil.set(key, "1", 10);
		
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
	
	/**
	 * 查看某条提现记录
	 * @param id 申请提现记录的id
	 */
	@ResponseBody
	@RequestMapping(value="/withdrawDetail${api.suffix}",method= {RequestMethod.POST})
	public SellTiXianLogVO withdrawDetail(HttpServletRequest request,Model model,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		SellTiXianLogVO vo = new SellTiXianLogVO();
		User user = getUser();
		
		SellTiXianLog tixian = sqlService.findById(SellTiXianLog.class, id);
		if(tixian == null){
			vo.setBaseVO(BaseVO.FAILURE, "记录不存在");
			return vo;
		}
		if(tixian.getUserid() - user.getId() != 0){
			vo.setBaseVO(BaseVO.FAILURE, "记录不属于你，无法查看");
			return vo;
		}
		
		vo.setSellTiXianLog(tixian);
		//记录日志
		ActionLogUtil.insert(request,"查看提现申请", tixian.toString());
		
		return vo;
	}
	
}