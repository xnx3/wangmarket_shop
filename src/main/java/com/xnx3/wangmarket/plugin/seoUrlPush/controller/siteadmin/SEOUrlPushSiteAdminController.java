package com.xnx3.wangmarket.plugin.seoUrlPush.controller.siteadmin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.EntityUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.util.SessionUtil;
import com.xnx3.wangmarket.domain.bean.PluginMQ;
import com.xnx3.wangmarket.domain.mq.DomainMQ;
import com.xnx3.wangmarket.plugin.seoUrlPush_domain.entity.SEOUrlPush;
import net.sf.json.JSONObject;

/**
 * 网站管理后台
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/plugin/seoUrlPush/siteadmin/")
public class SEOUrlPushSiteAdminController extends BasePluginController {
	@Resource
	private SqlService sqlService;

	/**
	 * 网站管理后台设置页面
	 * @author 管雷鸣
	 */
	@RequestMapping("/set${url.suffix}")
	public String set(HttpServletRequest request, Model model) {
		if(!haveSiteAuth()){
			return error(model, "无权使用");
		}
		Site site = SessionUtil.getSite();
		
		SEOUrlPush seo = sqlService.findById(SEOUrlPush.class, site.getId());
		if(seo == null){
			//还没有，那么new一个
			seo = new SEOUrlPush();
			seo.setSiteid(site.getId());
			seo.setIsUse(SEOUrlPush.IS_USE_NO);//默认不使用
			sqlService.save(seo);
		}
		
		//日志
		ActionLogUtil.insert(request, "进入插件 seoUrlPush 设置界面");
		
		model.addAttribute("seo", seo);
		return "plugin/seoUrlPush/siteadmin/set";
	}
	
	/**
	 * 网站管理后台设置保存是否使用
	 * @param isUse 是否使用， 1使用， 0不使用
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping("/updateIsUse${url.suffix}")
	public BaseVO updateIsUse(HttpServletRequest request, Model model,
			@RequestParam(value = "isUse", required = false, defaultValue = "0") int isUse) {
		Site site = SessionUtil.getSite();
		
		SEOUrlPush seo = sqlService.findById(SEOUrlPush.class, site.getId());
		if(seo == null){
			//还没有，那么new一个
			seo = new SEOUrlPush();
			seo.setSiteid(site.getId());
		}
		seo.setIsUse(isUse == 1? SEOUrlPush.IS_USE_YES:SEOUrlPush.IS_USE_NO);//默认不使用
		sqlService.save(seo);
		
		//日志
		ActionLogUtil.insertUpdateDatabase(request, "插件 seoUrlPush 修改 isUse 为"+(seo.getIsUse()-SEOUrlPush.IS_USE_YES == 0? "使用":"不使用"));
		//MQ通知改动
		sendMQ(site, seo);
		
		return success();
	}
	
	/**
	 * 向 domain 项目发送mq更新消息
	 * @param site 当前登陆的站点
	 * @param seo 当前最新的 {@link SEOUrlPush} 对象
	 */
	private void sendMQ(Site site, SEOUrlPush seo){
		DomainMQ.send("seoUrlPush", new PluginMQ(site).jsonAppend(JSONObject.fromObject(EntityUtil.entityToMap(seo))).toString());
	}
	
}