package com.xnx3.wangmarket.plugin.payNotice.controller.store;

import com.xnx3.SMSUtil;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.payNotice.Plugin;
import com.xnx3.wangmarket.plugin.payNotice.entity.PayNotice;
import com.xnx3.wangmarket.plugin.payNotice.vo.PayNoticeVO;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.StoreSMSService;
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
 * 商家管理后台
 * @author 管雷鸣
 */
@Controller(value="PayNoticeIndexPluginApiController")
@RequestMapping("/plugin/payNotice/api/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private StoreSMSService storeSmsService;
	
	
	/**
	 * 获取当前商家的支付通知设置信息
	 */
	@ResponseBody
	@RequestMapping(value = "getPayNotice${api.suffix}",method = {RequestMethod.POST})
	public PayNoticeVO getPayNotice(HttpServletRequest request, Model model){
		PayNoticeVO vo = new PayNoticeVO();
		if(!haveStoreAuth()){
			vo.setBaseVO(BaseVO.FAILURE,"请先登录");
			return vo;
		}
		
		Store store = SessionUtil.getStore();
		PayNotice payNotice = sqlCacheService.findById(PayNotice.class, store.getId());
		if(payNotice == null){
			payNotice = new PayNotice();
			payNotice.setId(store.getId());
			payNotice.setIsUse(PayNotice.IS_USE_NO);
			payNotice.setPhone("");
		}
		
		ActionLogUtil.insertUpdateDatabase(request, "进入支付通知插件设置页面");
		vo.setPayNotice(payNotice);
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
		PayNotice payNotice = sqlService.findById(PayNotice.class, store.getId());
		if(payNotice == null){
			payNotice = new PayNotice();
			payNotice.setId(store.getId());
			payNotice.setPhone("");
		}
		payNotice.setIsUse(isUse == 1? PayNotice.IS_USE_YES: PayNotice.IS_USE_NO);//默认不使用
		sqlService.save(payNotice);
		
		//清理缓存
		sqlCacheService.deleteCacheById(PayNotice.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "支付通知插件，修改 isUse 为"+(payNotice.getIsUse()- PayNotice.IS_USE_YES == 0? "使用":"不使用"), payNotice.toString());
		return success();
	}
	

	/**
	 * 设置通知的手机号
	 * @param phone 通知的手机号，店家的手机号
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="/updatePhone${api.suffix}",method= {RequestMethod.POST})
	public BaseVO updateCommission(HttpServletRequest request, Model model,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		if(phone.length() < 11){
			return error("手机号输入有误");
		}
		
		Store store = SessionUtil.getStore();
		PayNotice payNotice = sqlService.findById(PayNotice.class, store.getId());
		if(payNotice == null){
			payNotice = new PayNotice();
			payNotice.setId(store.getId());
			payNotice.setIsUse(PayNotice.IS_USE_NO);//默认不使用
		}
		payNotice.setPhone(phone);
		sqlService.save(payNotice);
		
		//清理缓存
		sqlCacheService.deleteCacheById(PayNotice.class, store.getId());
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "支付通知手机号更改", payNotice.toString());
		return success();
	}
	
	/**
	 * 测试给通知的手机号发送一条短信
	 * @return {@link BaseVO} result取值
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 				<li>2:未登录(在上一层拦截器就会被拦截)</li>
	 * 				<li>3:未配置短信通道</li>
	 * 			</ul>
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="/sendSmsTest${api.suffix}",method= {RequestMethod.POST})
	public BaseVO sendSmsTest(HttpServletRequest request, Model model) {
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		Store store = SessionUtil.getStore();
		PayNotice payNotice = sqlCacheService.findById(PayNotice.class, store.getId());
		if(payNotice == null){
			return error("请先设置用户支付成功后，要通知哪个手机");
		}
		
		if(payNotice.getPhone().length() < 11){
			return error("手机号不足11位，不是正常的手机号。");
		}
		
		SmsSet smsSet = sqlCacheService.findById(SmsSet.class, store.getId());
		if(smsSet == null || smsSet.getUseSms() - 1 != 0){
			BaseVO vo = new BaseVO();
			vo.setBaseVO(3, "请先设置短信通道");
			return vo;
		}
		
		SMSUtil smsUtil = storeSmsService.getSMSUtil(store.getId());
		if(smsUtil == null){
			BaseVO vo = new BaseVO();
			vo.setBaseVO(3, "请先设置短信通道");
			return vo;
		}
		
		com.xnx3.BaseVO vo = smsUtil.send(payNotice.getPhone(), Plugin.SMS_TEMPLATE.replace("{orderno}", "xxxxxxxx").replace("{money}", "0.00"));
		if(vo.getResult() - com.xnx3.BaseVO.FAILURE == 0){
			return error(vo.getInfo());
		}
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "支付通知发送短信测试", payNotice.toString());
		return success();
	}
	
}