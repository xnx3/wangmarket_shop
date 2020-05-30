package com.xnx3.wangmarket.plugin.weixinH5Auth.controller.store;

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
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.firstOrderAward.entity.Award;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 商家后台的
 * @author 管雷鸣
 */
@Controller(value="WeixinH5AuthStoreIndexPluginController")
@RequestMapping("/plugin/weixinH5Auth/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private PaySetService paySetService;
	

	/**
	 * 设置首页
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request,Model model){
		if(!haveStoreAuth()){
			return error(model, "请先登录");
		}
		
		Store store = SessionUtil.getStore();
		PaySet payset = paySetService.getPaySet(store.getId());
		//判断是否设置了微信公众号，或者使用服务商的
		boolean setgongzhonghoa = (payset.getWeixinOfficialAccountsAppid().length() > 5 && payset.getWeixinOfficialAccountsAppSecret().length() > 10) || payset.getUseWeixinServiceProviderPay() - 1 == 0;
		ConsoleUtil.log("setgongzhonghoa:"+setgongzhonghoa);
		ConsoleUtil.log(payset.toString());
		
		ActionLogUtil.insertUpdateDatabase(request, "进入weixin H5授权登录的商家设置首页");
		model.addAttribute("store", store);
		model.addAttribute("setgongzhonghoa", setgongzhonghoa);
		return "plugin/weixinH5Auth/store/index";
	}
	
}