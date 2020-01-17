package com.xnx3.wangmarket.plugin.templateCenter.controller.admin;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.SystemService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.service.TemplateService;
import com.xnx3.wangmarket.admin.util.AliyunLog;
import com.xnx3.wangmarket.plugin.base.controller.BasePluginController;
import com.xnx3.j2ee.entity.System;

/**
 * 模版相关操作
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/plugin/templateCenter/admin/set/")
public class SetTemplateCenterController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private TemplateService templateService;
	@Resource
	private SystemService systemService;
	

	/**
	 * 系统设置
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request, Model model){
		if(!haveSuperAdminAuth()){
			return error(model, "无权操作");
		}
	    AliyunLog.addActionLog(getSiteId(), "总管理后台查看模版中心首页-设置");
	    
	    
	    //是否使用云端模版库
	    model.addAttribute("useCloudTemplate", Global.get("PLUGIN_TEMPLATECENTER_USE_ClOUDTEMPLATE"));
	    //网站主域名
	    model.addAttribute("MASTER_SITE_URL", Global.get("MASTER_SITE_URL"));
	    return "plugin/templateCenter/admin/set/index";
	}
	
	/**
	 * 更改云端模版是否使用
	 * @param isuse 是否使用，1:使用，0不使用
	 */
	@RequestMapping(value="updateCloudIsUse${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO updateCloudIsUse(HttpServletRequest request,
			@RequestParam(value = "isuse", required = true) int isuse){
		if(!haveSuperAdminAuth()){
			return error("无权操作");
		}
		
		List<System> sysList = sqlService.findByProperty(System.class, "name", "PLUGIN_TEMPLATECENTER_USE_ClOUDTEMPLATE");
		System sys;
		if(sysList.size() == 0){
			sys = new System();
			sys.setName("PLUGIN_TEMPLATECENTER_USE_ClOUDTEMPLATE");
			sys.setLasttime(DateUtil.timeForUnix10());
			sys.setDescription("是否使用云端模版库。若是0则是不使用，1跟其他都是使用");
		}else{
			sys = sysList.get(0);
		}
		sys.setValue(isuse == 1 ? "1":"0");
		sqlService.save(sys);
		
		//刷新缓存
		systemService.refreshSystemCache();
		
	    AliyunLog.addActionLog(getSiteId(), "模版库更改是否使用云端模版",sys.getValue().equals("1") ? "使用":"不使用");
	    return success();
	}
	
}