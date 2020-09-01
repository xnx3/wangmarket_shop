package com.xnx3.wangmarket.plugin.weixinH5Auth.controller.store;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xnx3.BaseVO;
import com.xnx3.wangmarket.plugin.weixinH5Auth.vo.WeiXinAuthVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商家后台的
 * @author 管雷鸣
 */
@Controller(value="WeixinH5AuthStoreIndexPluginApiController")
@RequestMapping("/plugin/api/weixinH5Auth/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private PaySetService paySetService;


	/**
	 * 设置首页
	 * @author刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "index${api.suffix}",method = {RequestMethod.POST})
	public WeiXinAuthVO index(HttpServletRequest request, Model model){
		WeiXinAuthVO vo = new WeiXinAuthVO();
		if(!haveStoreAuth()){
			vo.setBaseVO(BaseVO.FAILURE,"请先登录");
			return vo;
		}

		Store store = SessionUtil.getStore();
		PaySet payset = paySetService.getPaySet(store.getId());
		//判断是否设置了微信公众号，或者使用服务商的
		boolean setgongzhonghoa = (payset.getWeixinOfficialAccountsAppid().length() > 5 && payset.getWeixinOfficialAccountsAppSecret().length() > 10) || payset.getUseWeixinServiceProviderPay() - 1 == 0;

		ActionLogUtil.insertUpdateDatabase(request, "进入weixin H5授权登录的商家设置首页");
		vo.setStore(store);
		vo.setSetgongzhonghoa(setgongzhonghoa);
		vo.setPayset(payset);
		return vo;
	}

}