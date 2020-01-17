package com.xnx3.wangmarket.plugin.templateCenter.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xnx3.DateUtil;
import com.xnx3.StringUtil;
import com.xnx3.ZipUtil;
import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.System;
import com.xnx3.j2ee.func.AttachmentFile;
import com.xnx3.j2ee.func.Language;
import com.xnx3.j2ee.func.Log;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.SystemService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.entity.SiteColumn;
import com.xnx3.wangmarket.admin.entity.Template;
import com.xnx3.wangmarket.admin.init.TemplateTemporaryFolder;
import com.xnx3.wangmarket.admin.service.TemplateService;
import com.xnx3.wangmarket.admin.util.AliyunLog;
import com.xnx3.wangmarket.admin.util.TemplateUtil;
import com.xnx3.wangmarket.admin.util.interfaces.TemplateUtilFileMove;
import com.xnx3.wangmarket.admin.vo.TemplatePageListVO;
import com.xnx3.wangmarket.admin.vo.TemplateVO;
import com.xnx3.wangmarket.plugin.base.controller.BasePluginController;

import net.sf.json.JSONObject;

/**
 * 模版相关操作
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/plugin/templateCenter/admin/")
public class TemplateCenterController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private TemplateService templateService;
	@Resource
	private SystemService systemService;
	

	/**
	 * 后台首页
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request, Model model){
		if(!haveSuperAdminAuth()){
			return error(model, "无权操作");
		}
		
	    AliyunLog.addActionLog(getSiteId(), "总管理后台查看模版中心首页");
	    return "plugin/templateCenter/admin/index";
	}
	
	
	/**
	 * 模版列表
	 */
	@RequestMapping("list${url.suffix}")
	public String list(HttpServletRequest request, Model model){
		if(!haveSuperAdminAuth()){
			return error(model, "无权操作");
		}
		
		
		Sql sql = new Sql(request);
	    sql.setSearchTable("template");
	    sql.setSearchColumn(new String[]{"name","companyname","username","type=","terminal_mobile","terminal_pc","terminal_ipad","terminal_display","iscommon"});
	    int count = sqlService.count("template", sql.getWhere());
	    Page page = new Page(count, 50, request);
	    //创建查询语句，只有SELECT、FROM，原生sql查询。其他的where、limit等会自动拼接
	    sql.setSelectFromAndPage("SELECT * FROM template", page);
	    sql.setDefaultOrderBy("template.rank ASC");
	    List<Template> list = sqlService.findBySql(sql, Template.class);
	    
	    AliyunLog.addActionLog(page.getCurrentPageNumber(), "总管理后台查看模版列表","查看第"+page.getCurrentPageNumber()+"页");
	    
	    //将数据记录传到页面以供显示
	    model.addAttribute("list", list);
	    //将分页信息传到页面以供显示
	    model.addAttribute("page", page);
	    return "plugin/templateCenter/admin/list";
	}
	

	/**
	 * 模版详情
	 */
	@RequestMapping("view${url.suffix}")
	public String view(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false , defaultValue="0") int id){
		if(!haveSuperAdminAuth()){
			return error(model, "无权操作");
		}
		if(id == 0){
			return error(model, "请传入模版编号");
		}
		
		Template template = sqlService.findById(Template.class, id);
		if(template == null){
			return error(model, "要查看的模版不存在");
		}
		
		model.addAttribute("template", template);
		model.addAttribute("attachmentUrl", AttachmentFile.netUrl());
	    return "plugin/templateCenter/admin/view";
	}
	
	
	/**
	 * 更改模版的公共、私有状态
	 */
	@RequestMapping("updateIscommon${url.suffix}")
	@ResponseBody
	public BaseVO updateIscommon(HttpServletRequest request,
			@RequestParam(value = "iscommon", required = true , defaultValue="0") Short iscommon,
			@RequestParam(value = "templateName", required = true , defaultValue="") String templateName){
		if(!haveSuperAdminAuth()){
			return error("无权操作");
		}
		
		Template template = sqlService.findAloneByProperty(Template.class, "name", templateName);
		if(template == null){
			return error("修改的模版不存在");
		}
		template.setIscommon(iscommon);
		sqlService.save(template);
		
		//更新内存缓存
		TemplateUtil.updateDatabaseTemplateMap(template);
		
	    AliyunLog.addActionLog(getSiteId(), "总管理更改模版开放方式iscommon",template.getIscommon()+"");
	    return success();
	}
	
	/**
	 * 更改某项的值
	 * @param request
	 * @param templateName 要该的模版名字
	 * @param columnName 数据库中列的名字 ，可更改列包含：
	 * @param value 此项的value值
	 * @return
	 */
	@RequestMapping("updateSave${url.suffix}")
	@ResponseBody
	public BaseVO updateSave(HttpServletRequest request,
			@RequestParam(value = "templateName", required = true ) String templateName,
			@RequestParam(value = "columnName", required = true ) String columnName,
			@RequestParam(value = "value", required = true) String value){
		if(!haveSuperAdminAuth()){
			return error("无权操作");
		}
		value = filter(value);
		
		Template template = sqlService.findAloneByProperty(Template.class, "name", templateName);
		if(template == null){
			return error("修改的模版不存在");
		}
		
		

		switch (columnName) {
		case "previewUrl":
			template.setPreviewUrl(value);
			break;
		case "wscsoDownUrl":
			template.setWscsoDownUrl(value);
			break;
		case "zipDownUrl":
			template.setZipDownUrl(value);
			break;
		default:
			return error("列不存在");
		}
		
		sqlService.save(template);
		
		//更新内存缓存
		TemplateUtil.updateDatabaseTemplateMap(template);
		
	    AliyunLog.addActionLog(getSiteId(), "总管理更改模版属性",columnName+"="+value);
	    return success();
	}
	

	
}