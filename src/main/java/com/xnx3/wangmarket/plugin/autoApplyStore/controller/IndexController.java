package com.xnx3.wangmarket.plugin.autoApplyStore.controller;

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
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 自助开店，用户端
 * @author 管雷鸣
 */
@Controller(value="AutoApplyStoreIndexPluginController")
@RequestMapping("/plugin/autoApplyStore/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 提交开店申请
	 * @param contacts 联系人姓名
	 * @param phone 联系手机号
	 * @param weixin 微信号
	 * @param storeid 来源店铺的id,从哪个店铺内提交的申请
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="submitApply${api.suffix}" ,method= {RequestMethod.POST})
	public BaseVO submitApply(HttpServletRequest request, Model model,
			@RequestParam(value = "contacts", required = false, defaultValue = "") String contacts,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "weixin", required = false, defaultValue = "") String weixin,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		
		//来源商铺
		Store referrerStore = sqlCacheService.findById(Store.class, storeid);
		
		//新申请的商铺
		Store store = new Store();
		store.setAddress("weixin:"+StringUtil.filterXss(weixin));
		store.setAddtime(DateUtil.timeForUnix10());
		store.setContacts(StringUtil.filterXss(contacts));
		store.setPhone(StringUtil.filterXss(phone));
		store.setReferrerUserid(referrerStore == null? 0:referrerStore.getUserid());
		sqlService.save(store);
		
		//日志
		ActionLogUtil.insert(request, "申请入驻开通店铺："+store.toString());
		return success();
	}
	
	
}