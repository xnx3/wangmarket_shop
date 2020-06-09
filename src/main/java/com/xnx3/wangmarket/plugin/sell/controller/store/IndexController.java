package com.xnx3.wangmarket.plugin.sell.controller.store;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 二级分销，商家管理后台
 * @author 管雷鸣
 */
@Controller(value="SellStoreIndexPluginController")
@RequestMapping("/plugin/sell/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	

	/**
	 * 设置分销奖励
	 */
	@RequestMapping("index${url.suffix}")
	public String setAward(HttpServletRequest request,Model model){
		if(!haveStoreAuth()){
			return error(model, "请先登录");
		}
		
		Store store = SessionUtil.getStore();
		SellStoreSet set = sqlService.findById(SellStoreSet.class, store.getId());
		if(set == null){
			set = new SellStoreSet();
			set.setId(store.getId());
			set.setIsUse(Award.IS_USE_NO); //默认不使用
			set.setFirstCommission(0);
			set.setTwoCommission(0);
			sqlService.save(set);
		}
		
		ActionLogUtil.insertUpdateDatabase(request, "进入分销设置页面");
		model.addAttribute("set", set);
		return "plugin/sell/store/index";
	}
	
	/**
	 * 管理后台设置保存是否使用
	 * @param isUse 是否使用， 1使用， 0不使用
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="updateIsUse${api.suffix}" ,method= {RequestMethod.POST})
	public BaseVO updateIsUse(HttpServletRequest request, Model model,
			@RequestParam(value = "isUse", required = false, defaultValue = "0") int isUse) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		Store store = SessionUtil.getStore();
		SellStoreSet set = sqlService.findById(SellStoreSet.class, store.getId());
		if(set == null){
			set = new SellStoreSet();
			set.setId(store.getId());
			set.setIsUse(Award.IS_USE_NO); //默认不使用
			set.setFirstCommission(0);
			set.setTwoCommission(0);
		}
		set.setIsUse(isUse == 1? Award.IS_USE_YES:Award.IS_USE_NO);//默认不使用
		sqlService.save(set);
		
		//清理缓存
		sqlCacheService.deleteCacheById(SellStoreSet.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "分销修改 isUse 为"+(set.getIsUse()-Award.IS_USE_YES == 0? "使用":"不使用"), set.toString());
		
		return success();
	}
	

	/**
	 * 商家管理后台设置佣金奖励比例 commission
	 * @param level 佣金等级，传入 first、 two  代表一级分销、二级分销
	 * @param value 佣金的百分比的值。
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="/updateCommission${api.suffix}",method= {RequestMethod.POST})
	public BaseVO updateCommission(HttpServletRequest request, Model model,
			@RequestParam(value = "level", required = false, defaultValue = "") String level,
			@RequestParam(value = "value", required = false, defaultValue = "0") int value) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		if(value > 100){
			return error("分佣比例错误！哪有超过100%的");
		}
		if(value < 0){
			return error("分佣比例错误！请不要传入负数");
		}
		
		Store store = SessionUtil.getStore();
		SellStoreSet set = sqlService.findById(SellStoreSet.class, store.getId());
		if(set == null){
			set = new SellStoreSet();
			set.setId(store.getId());
			set.setIsUse(Award.IS_USE_NO); //默认不使用
			set.setFirstCommission(0);
			set.setTwoCommission(0);
		}
		if(level.equals("first")){
			//一级
			set.setFirstCommission(value);
		}else if (level.equals("two")) {
			set.setTwoCommission(value);
		}else{
			return error("level 传值错误");
		}
		sqlService.save(set);
		
		//清理缓存
		sqlCacheService.deleteCacheById(SellStoreSet.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "修改分佣的比例", set.toString());
		
		return success();
	}
	
}