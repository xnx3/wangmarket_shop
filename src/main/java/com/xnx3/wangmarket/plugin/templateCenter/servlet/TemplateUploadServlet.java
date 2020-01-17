package com.xnx3.wangmarket.plugin.templateCenter.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.xnx3.BaseVO;
import com.xnx3.DateUtil;
import com.xnx3.FileUtil;
import com.xnx3.StringUtil;
import com.xnx3.ZipUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.AttachmentFile;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.wangmarket.admin.entity.Template;
import com.xnx3.wangmarket.admin.init.TemplateTemporaryFolder;
import com.xnx3.wangmarket.admin.util.AliyunLog;
import com.xnx3.wangmarket.admin.util.TemplateUtil;
import com.xnx3.wangmarket.admin.util.interfaces.TemplateUtilFileMove;
import com.xnx3.wangmarket.admin.vo.TemplateVO;
import net.sf.json.JSONObject;

@WebServlet(name="TemplatePluginUploadServlet",urlPatterns="/plugin/templateCenter/admin/importTemplate.do")
@MultipartConfig
public class TemplateUploadServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 权限判断，看是否有权上传 */
		User user = ShiroFunc.getUser();
		if(user == null){
			//未登陆，那就直接是false
			responseBody(resp, BaseVO.FAILURE, "请先登陆");
			return;
		}
		if(!com.xnx3.j2ee.Func.isAuthorityBySpecific(user.getAuthority(), Global.get("ROLE_SUPERADMIN_ID"))){
			responseBody(resp, BaseVO.FAILURE, "无权操作。只允许总管理员使用");
			return;
		}
		
		/** 取出要上传的文件 **/
		Part part = req.getPart("templateFile");//通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
		if(part == null){
			responseBody(resp, BaseVO.FAILURE, "未发现要上传的文件");
			return;
		}
		
		/* 将文件保存到本地服务器 */
		String fileName = DateUtil.timeForUnix13()+"_"+StringUtil.getRandom09AZ(20);
		//将上传的文件内容写入服务器文件中
		part.write(TemplateTemporaryFolder.folderPath+fileName+".zip");
		File file = new File(TemplateTemporaryFolder.folderPath+fileName+".zip");

		//将其解压到同文件夹中
		try {
			ZipUtil.unzip(TemplateTemporaryFolder.folderPath+fileName+".zip",TemplateTemporaryFolder.folderPath+fileName+"/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//既然已经解压出来了，那么删除掉zip文件
		file.delete();
		//判断一下解压出来的文件中，是否存在 template.wscso 这个模版文件
		File wscsoFile = new File(TemplateTemporaryFolder.folderPath+fileName+"/template.wscso");
		if(!wscsoFile.exists()){
			//不存在，那就报错吧
			responseBody(resp, BaseVO.FAILURE, "template.wscso模版文件未发现！");
			return;
		}
		
		//获取 sqlservice
		WebApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());  
		SqlService sqlService = (SqlService) context.getBean("sqlService");
		
		//更新数据库中的 Template 信息
		TemplateVO tvo = new TemplateVO();
		tvo.importText(FileUtil.read(TemplateTemporaryFolder.folderPath+fileName+"/template.wscso", FileUtil.UTF8));
		//v4.7版本以后，导出的都会有 tvo.template 对象
		String templateName = tvo.getTemplate().getName();
		//判断数据库中，是否已经有这个模版了
		com.xnx3.wangmarket.admin.entity.Template template = sqlService.findAloneByProperty(com.xnx3.wangmarket.admin.entity.Template.class, "name", templateName);
		Template temvo = tvo.getTemplate();
		if(template == null){
			//为空，没有这个模版，那么直接将导入的模版存入数据库
			template = new Template();
			
			//将这个模版模版信息记录入数据库
			template.setIscommon(com.xnx3.wangmarket.admin.entity.Template.ISCOMMON_NO); 	//用户自己导入的，默认是私有的，不加入公共模版库
			template.setAddtime(DateUtil.timeForUnix10());
			
			template.setCompanyname(temvo.getCompanyname());
			template.setName(temvo.getName());
			template.setRank(0);
			template.setRemark(temvo.getRemark());
			template.setSiteurl(temvo.getSiteurl());
			template.setTerminalDisplay(temvo.getTerminalDisplay());
			template.setTerminalIpad(temvo.getTerminalIpad());
			template.setTerminalMobile(temvo.getTerminalMobile());
			template.setTerminalPc(temvo.getTerminalPc());
			template.setType(temvo.getType());
			template.setUsername(temvo.getUsername());
			if(template.getPreviewUrl() == null || template.getPreviewUrl().length() < 6){
				if(temvo.getPreviewUrl() != null && temvo.getPreviewUrl().length() > 6){
					template.setPreviewUrl(temvo.getPreviewUrl());
				}
			}
			if(template.getWscsoDownUrl() == null || template.getWscsoDownUrl().length() < 6){
				if(temvo.getWscsoDownUrl() != null && temvo.getWscsoDownUrl().length() > 6 ){
					template.setWscsoDownUrl(temvo.getWscsoDownUrl());
				}
			}
			if(template.getZipDownUrl() == null || template.getZipDownUrl().length() < 6){
				if(temvo.getZipDownUrl() != null && temvo.getZipDownUrl().length() < 6){
					template.setZipDownUrl(temvo.getZipDownUrl());
				}
			}
			sqlService.save(template);
		}else{
			//不为空，已经有这个模版了
		}
		
		
		//将zip解压出来的文件，进行过滤，过滤掉不合法的后缀文件，将合法的文件后缀转移到新建立的模版文件夹中去
		new TemplateUtil(templateName, new TemplateUtilFileMove() {
			public void move(String path, InputStream inputStream) {
				AttachmentFile.put(path, inputStream);
			}
		}).filterTemplateFile(new File(TemplateTemporaryFolder.folderPath+fileName+"/"));
		
		//更新内存缓存
		TemplateUtil.updateDatabaseTemplateMap(template);
		
	    AliyunLog.addActionLog(user.getId(), "总管理导入更新模版", template.getName());

	    responseBody(resp, BaseVO.SUCCESS, "操作成功");
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
	public void responseBody(HttpServletResponse response, int result, String info){
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("info", info);
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");  
	    PrintWriter out = null;  
	    try { 
	        out = response.getWriter();  
	        out.append(json.toString());
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	}
}
