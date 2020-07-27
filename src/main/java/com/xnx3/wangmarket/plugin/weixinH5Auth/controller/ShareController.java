package com.xnx3.wangmarket.plugin.weixinH5Auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.plugin.weixinH5Auth.vo.H5ShareVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.SignUtil;
import com.xnx3.weixin.WeiXinUtil;
import com.xnx3.weixin.vo.WebShareVO;


/**
 * 分享相关
 * @author 管雷鸣
 */
@Controller(value="WeixinH5SharePluginController")
@RequestMapping("/plugin/weixinH5Auth/share/")
public class ShareController extends BasePluginController {
	@Resource
	private WeiXinService weiXinService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private SqlService sqlService;
	@Resource
	private PaySetService paySetService;

	/**
	 * 商品的分享，商品详情中调起分享操作
	 * @param goodsid 要分享的商品的id
	 * @param url 分享的页面url，传入如 http://demo.imall.net.cn/goods.html
	 * 
	 */
	@RequestMapping("goodsShare${api.suffix}")
	@ResponseBody
	public H5ShareVO hiddenAuthJump(HttpServletRequest request,Model model,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "url", required = false, defaultValue="") String url){
		
		H5ShareVO vo = new H5ShareVO();
		if(goodsid < 1){
			vo.setBaseVO(H5ShareVO.FAILURE, "请传入商品编号");
			return vo;
		}
		Goods goods = sqlCacheService.findById(Goods.class, goodsid);
		if(goods == null){
			vo.setBaseVO(H5ShareVO.FAILURE, "商品不存在");
			return vo;
		}
		
		WeiXinUtil util = weiXinService.getWeiXinUtil(goods.getStoreid());
		if(util == null){
			vo.setBaseVO(H5ShareVO.FAILURE, "当前店铺未设置微信公众号");
			return vo;
		}
		
		WebShareVO webShareVO = SignUtil.generateSign(util.getJsapiTicket().getTicket(), url);
		vo.setWebShareVO(webShareVO);
		vo.setAppId(util.appId);
		
		/*** 生成分享出去的url ***/
		String shareUrl = SystemUtil.get("MASTER_SITE_URL") + "plugin/weixinH5Auth/hiddenAuthJump.do?storeid="+goods.getStoreid()+"&referrerid="+getUserId()+"&url="+url;
		vo.setShareUrl(shareUrl);
		
		ConsoleUtil.info(vo.toString());
		ActionLogUtil.insert(request, goods.getId(), "微信获取商品的分享配置", vo.toString());
		return vo;
	}
	
}