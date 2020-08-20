package com.xnx3.wangmarket.plugin.limitbuy.controller.store;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.limitbuy.entity.LimitBuyStore;
import com.xnx3.wangmarket.plugin.vo.LimitBuyStoreVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 限购商家管理后台
 * @author 管雷鸣
 */
@Controller(value="LimitBuyIndexPluginApiController")
@RequestMapping("/plugin/api/limitbuy/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	

	/**
	 * 设置
	 */
	@ResponseBody
	@RequestMapping(value = "index${api.suffix}",method = {RequestMethod.POST})
	public LimitBuyStoreVO setAward(HttpServletRequest request,Model model){

		LimitBuyStoreVO vo = new LimitBuyStoreVO();

		if(!haveStoreAuth()){
			vo.setBaseVO(BaseVO.FAILURE,"请先登录");
		}
		
		Store store = SessionUtil.getStore();
		LimitBuyStore limitBuyStore = sqlService.findById(LimitBuyStore.class, store.getId());
		if(limitBuyStore == null){
			limitBuyStore = new LimitBuyStore();
			limitBuyStore.setId(store.getId());
			limitBuyStore.setIsUse(LimitBuyStore.IS_USE_NO);
			limitBuyStore.setLimitNumber(1);
			sqlService.save(limitBuyStore);
		}
		
		ActionLogUtil.insertUpdateDatabase(request, "进入限购插件设置页面");
		vo.setLimitBuyStore(limitBuyStore);
		return vo;
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
		LimitBuyStore limitBuyStore = sqlService.findById(LimitBuyStore.class, store.getId());
		if(limitBuyStore == null){
			limitBuyStore = new LimitBuyStore();
			limitBuyStore.setId(store.getId());
			limitBuyStore.setLimitNumber(1);
		}
		limitBuyStore.setIsUse(isUse == 1? LimitBuyStore.IS_USE_YES: LimitBuyStore.IS_USE_NO);//默认不使用
		sqlService.save(limitBuyStore);
		
		//清理缓存
		sqlCacheService.deleteCacheById(LimitBuyStore.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "限购商品修改 isUse 为"+(limitBuyStore.getIsUse()- LimitBuyStore.IS_USE_YES == 0? "使用":"不使用"), limitBuyStore.toString());
		
		return success();
	}
	

	/**
	 * 设置限购的数量
	 * @param value 限购的数量
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="/updateLimitNumber${api.suffix}",method= {RequestMethod.POST})
	public BaseVO updateCommission(HttpServletRequest request, Model model,
			@RequestParam(value = "value", required = false, defaultValue = "0") int value) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		if(value < 0){
			return error("数量错误！请不要传入负数");
		}
		
		Store store = SessionUtil.getStore();
		LimitBuyStore limitBuyStore = sqlService.findById(LimitBuyStore.class, store.getId());
		if(limitBuyStore == null){
			limitBuyStore = new LimitBuyStore();
			limitBuyStore.setId(store.getId());
			limitBuyStore.setIsUse(LimitBuyStore.IS_USE_NO);
		}
		limitBuyStore.setLimitNumber(value);
		sqlService.save(limitBuyStore);
		
		//清理缓存
		sqlCacheService.deleteCacheById(LimitBuyStore.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "修改限购的数量", limitBuyStore.toString());
		
		return success();
	}
	
}