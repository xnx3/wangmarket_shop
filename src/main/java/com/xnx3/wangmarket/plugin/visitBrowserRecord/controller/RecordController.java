package com.xnx3.wangmarket.plugin.visitBrowserRecord.controller;

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

import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ElasticSearchUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.visitBrowserRecord.Global;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 提交访问数据，搜集数据
 * @author 管雷鸣
 */
@Controller(value="VisitBrowserRecordPluginController")
@RequestMapping("/plugin/visitBrowserRecord/")
public class RecordController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 获取分享的url
	 * @param storeid 店铺的id，对应 Store.id
	 * @param head 用户头像，传入头像的绝对路径
	 * @param nickname 用户的昵称，注意，这里有可能是特殊字符
	 * @param phone 用户的手机号
	 * @param startTime 用户浏览的开始时间的10位时间戳
	 * @param endTime 用户浏览的结束时间的10位时间戳
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="add.json" ,method= {RequestMethod.POST})
	public BaseVO add(HttpServletRequest request, Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid,
			@RequestParam(value = "head", required = false, defaultValue = "") String head,
			@RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "startTime", required = false, defaultValue = "0") int startTime,
			@RequestParam(value = "endTime", required = false, defaultValue = "0") int endTime) {
		if(storeid < 1) {
			return error("请传入storeid");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeid", storeid);	
		params.put("head", head);
		params.put("nickname", nickname);
		params.put("phone", phone);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("useTime", endTime - startTime);	//耗时，也就是访客停留时间，单位是秒
		params.put("time", DateUtil.timeForUnix10());	//记录创建的时间
		
		//将其写入到 ElasticSearch 中进行保存
		ElasticSearchUtil.getElasticSearch().put(params, Global.INDEX_NAME);
		
		return success();
	}
	
}