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
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.PaySetService;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.wangmarket.shop.core.vo.weixin.JsapiTicketVO;
import com.xnx3.weixin.SignUtil;
import com.xnx3.weixin.WeiXinUtil;
import com.xnx3.weixin.vo.WebShareVO;


/**
 * 分享相关。
 * 其中有url作为参数的，url中 ? & 都会替换为 _3F 、_26 等
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

	/**
	 * 商品的分享，商品详情中调起分享操作
	 * @param goodsid 要分享的商品的id
	 * @param url 分享的页面url，传入如 http://demo.imall.net.cn/goods.html
	 * @param domain 分享的页面domain，传入格式如 http://demo.imall.net.cn/  后面要有/ 。  注意，domain必须是包含在url 中的，也就是 url.indexOf(domain) > -1 
	 * 
	 * 
	 */
	@RequestMapping("goodsShare${api.suffix}")
	@ResponseBody
	public H5ShareVO goodsShare(HttpServletRequest request,Model model,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "url", required = false, defaultValue="") String url,
			@RequestParam(value = "domain", required = false, defaultValue="") String domain){
		H5ShareVO vo = new H5ShareVO();
		if(goodsid < 1){
			vo.setBaseVO(H5ShareVO.FAILURE, "请传入商品编号");
			return vo;
		}
		if(url.indexOf(domain) == -1 ){
			vo.setBaseVO(H5ShareVO.FAILURE, "domain 必须是跟 url 是同一个域名");
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
		JsapiTicketVO jsapiTicketVO = weiXinService.getJsapiTicket(goods.getStoreid());
		if(jsapiTicketVO.getResult() - JsapiTicketVO.FAILURE == 0){
			vo.setBaseVO(H5ShareVO.FAILURE, jsapiTicketVO.getInfo());
			return vo;
		}
		WebShareVO webShareVO = SignUtil.generateSign(jsapiTicketVO.getJsapiTicket().getTicket(), url);
		vo.setWebShareVO(webShareVO);
		vo.setAppId(util.appId);
		
		/*** 生成分享出去的url ***/
		String shareUrl = SystemUtil.get("MASTER_SITE_URL") + "plugin/weixinH5Auth/hiddenAuthJump.do?storeid="+goods.getStoreid()+"&referrerid="+getUserId()+"&url="+domain+"token.html?storeid="+goods.getStoreid()+"_26url=goods.html?goodsid="+goodsid;
		vo.setShareUrl(shareUrl);
		
		ConsoleUtil.info("js_token:"+util.getJsapiTicket());
		ActionLogUtil.insert(request, goods.getId(), "微信获取商品的分享配置", vo.toString());
		return vo;
	}
	

	/**
	 * 店铺首页的分享，调起分享操作
	 * @param storeid 要分享的店铺的id
	 * @param url 所在页面的url，是在哪个页面准备调起微信分享。传入如 http://demo.imall.net.cn/index.html
	 * @param domain 分享的页面domain，传入格式如 http://demo.imall.net.cn/  后面要有/ 。  注意，domain必须是包含在url 中的，也就是 url.indexOf(domain) > -1 
	 * 
	 */
	@RequestMapping("indexShare${api.suffix}")
	@ResponseBody
	public H5ShareVO indexShare(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "url", required = false, defaultValue="") String url,
			@RequestParam(value = "domain", required = false, defaultValue="") String domain){
		H5ShareVO vo = new H5ShareVO();
		if(storeid < 1){
			vo.setBaseVO(H5ShareVO.FAILURE, "请传入店铺编号");
			return vo;
		}
		if(url.indexOf(domain) == -1 ){
			vo.setBaseVO(H5ShareVO.FAILURE, "domain 必须是跟 url 是同一个域名");
			return vo;
		}
		Store store = sqlCacheService.findById(Store.class, storeid);
		if(store == null){
			vo.setBaseVO(H5ShareVO.FAILURE, "店铺不存在");
			return vo;
		}
		
		WeiXinUtil util = weiXinService.getWeiXinUtil(store.getId());
		if(util == null){
			vo.setBaseVO(H5ShareVO.FAILURE, "当前店铺未设置微信公众号");
			return vo;
		}
		JsapiTicketVO jsapiTicketVO = weiXinService.getJsapiTicket(store.getId());
		if(jsapiTicketVO.getResult() - JsapiTicketVO.FAILURE == 0){
			vo.setBaseVO(H5ShareVO.FAILURE, jsapiTicketVO.getInfo());
			return vo;
		}
		
		WebShareVO webShareVO = SignUtil.generateSign(jsapiTicketVO.getJsapiTicket().getTicket(), url);
		vo.setWebShareVO(webShareVO);
		vo.setAppId(util.appId);
		
		/*** 生成分享出去的url ***/
		String shareUrl = SystemUtil.get("MASTER_SITE_URL") + "plugin/weixinH5Auth/hiddenAuthJump.do?storeid="+store.getId()+"&referrerid="+getUserId()+"&url="+domain+"token.html?storeid="+store.getId()+"_26url=index.html";
		vo.setShareUrl(shareUrl);
		
		ConsoleUtil.info("js_token:"+util.getJsapiTicket()+", storeid:"+store.getId());
		ActionLogUtil.insert(request, store.getId(), "微信获取商铺的分享配置", vo.toString());
		return vo;
	}
}