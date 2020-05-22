package com.xnx3.wangmarket.shop.store.controller;

import java.util.HashMap;
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

import com.xnx3.Lang;
import com.xnx3.SMSUtil;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SafetyUtil;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreChildUser;
import com.xnx3.wangmarket.shop.core.service.SMSService;
import com.xnx3.wangmarket.shop.core.service.SMSSetService;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * sms短信相关
 * @author 管雷鸣
 */
@Controller(value="ShopStoreSmsController")
@RequestMapping("/shop/store/sms/")
public class SmsController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SMSService smsService;
	@Resource
	private SMSSetService smsSetService;

	
	/**
	 * 进入后的页面
	 */
	@RequestMapping("index${url.suffix}")
	public String login(HttpServletRequest request,Model model){
		Store store = getStore();
		SmsSet smsSet = sqlService.findById(SmsSet.class, store.getId());
		if(smsSet == null){
			//还没设置过，那么加个默认的
			smsSet = new SmsSet();
			smsSet.setId(store.getId());
			sqlService.save(smsSet);
		}
		
		model.addAttribute("smsSet", smsSet);
		ActionLogUtil.insert(request, "进入短信设置页面");
		return "/shop/store/sms/index";
	}

	/**
	 * 更改 SmsSet 的参数
	 * @param name SmsSet实体类的字段名，传入如useSms、 quotaDayIp 等
	 * @param value 字段的值
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="update${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO update(HttpServletRequest request,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "value", required = false, defaultValue = "") String value){
		Store store = getStore();
		
		if(name.length() == 0){
			return error("name为空");
		}
		
		SmsSet smsSet = sqlService.findById(SmsSet.class, store.getId());
		if(smsSet == null){
			//还没设置过，那么加个默认的
			smsSet = new SmsSet();
			smsSet.setId(store.getId());
		}
		if(name.equals("useSms")){
			smsSet.setUseSms((short)(value.equals("1")? 1:0));
		}else if (name.equals("uid")) {
			smsSet.setUid(Lang.stringToInt(value, 0));
		}else if (name.equals("password")) {
			smsSet.setPassword(value);
		}else if (name.equals("quotaDayIp")) {
			smsSet.setQuotaDayIp(Lang.stringToInt(value, 0));
		}else if (name.equals("quotaDayPhone")) {
			smsSet.setQuotaDayPhone(Lang.stringToInt(value, 0));
		}else{
			error("name异常");
		}
		//保存进数据库
		System.out.println(smsSet);
		sqlService.save(smsSet);
		//更新缓存
		smsSetService.setSMSSet(smsSet);
		if(smsSet.getUid() != null && smsSet.getPassword() != null){
			smsService.setSMSUtil(store.getId(), new SMSUtil(smsSet.getUid(), smsSet.getPassword()));
		}
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, store.getId(), "修改smsset，name:"+name+"，value:"+SafetyUtil.filter(value));
		return success();
	}
	

	/**
	 * 获取自己短信通道的余额
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功，那么可以用 vo.getInfo() 获取短信剩余条数</li>
	 * 			</ul>
	 */
	@RequestMapping(value="getBalance${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO getBalance(HttpServletRequest request){
		Store store = getStore();
		
		SmsSet smsSet = smsSetService.getSMSSet(store.getId());
		if(smsSet == null){
			//还没设置过，那么加个默认的
			return error("您尚未设置短信接口");
		}
		
		SMSUtil smsUtil = smsService.getSMSUtil(store.getId());
		if(smsUtil == null){
			return error("尚未配置短信通道");
		}
		com.xnx3.BaseVO smsvo = smsUtil.getBalance();
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, store.getId(), "获取短信剩余条数："+smsvo.toString());
		BaseVO vo = new BaseVO();
		vo.setBaseVO(smsvo);
		return vo;
	}
	
}
