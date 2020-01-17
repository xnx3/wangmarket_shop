package com.xnx3.wangmarket.plugin.newsSync.controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.CaptchaUtil;
import com.xnx3.j2ee.util.LanguageUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.util.ActionLogUtil;
import com.xnx3.wangmarket.plugin.newsSync.entity.SiteBind;
import com.xnx3.wangmarket.plugin.newsSync.util.SessionUtil;

/**
 * 网站管理后台用到的设置相关
 * @author 管雷鸣
 */
@Controller(value="NewsSyncIndexPluginController")
@RequestMapping("/plugin/newsSync/")
public class IndexController extends BasePluginController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	
	/**
	 * 当点击 功能插件 下的子菜单 内容同步 时，会进入此页面
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request, Model model){
		if(!haveSiteAuth()){
			return error(model, "无权使用。请刷新页面重新登录");
		}
		Site otherSite = SessionUtil.getSite();
		
		//判断这个网站是否被绑定过了 
		SiteBind sitebind = sqlService.findAloneBySqlQuery("SELECT * FROM plugin_newssync_site_bind WHERE other_id = "+otherSite.getId(), SiteBind.class);
		if(sitebind != null){
			Site sourceSite = sqlService.findById(Site.class, sitebind.getOtherId());
			model.addAttribute("sourceSite", sourceSite);	//源站
		}
		
		ActionLogUtil.insert(request, "进入newsSync插件首页");
		model.addAttribute("sitebind", sitebind);
		return "plugin/newsSync/index";
	}
	
	/**
	 * 撤销网站绑定
	 */
	@ResponseBody
	@RequestMapping("cancelBind${url.suffix}")
	public BaseVO cancelBind(HttpServletRequest request){
		if(!haveSiteAuth()){
			return error("无权使用。请刷新页面重新登录");
		}
		Site otherSite = SessionUtil.getSite();
		
		//判断这个网站是否被绑定过了 
		SiteBind sitebind = sqlService.findAloneBySqlQuery("SELECT * FROM plugin_newssync_site_bind WHERE other_id = "+otherSite.getId(), SiteBind.class);
		if(sitebind == null){
			return error("您网站未绑定其他网站");
		}
		
		//删除绑定关系
		sqlService.delete(sitebind);
		//删除news bind
		sqlService.executeSql("DELETE FROM plugin_newssync_news_bind WHERE other_site_id = " + otherSite.getId() );
		
		ActionLogUtil.insertUpdateDatabase(request, "newsSync插件中，撤销网站绑定",sitebind.toString());
		return success();
	}
	
	
	/**
	 * 当点击 功能插件 下的子菜单 内容同步 时，会进入此页面
	 */
	@RequestMapping("login${url.suffix}")
	public String login(HttpServletRequest request, Model model){
		if(!haveSiteAuth()){
			return error(model, "无权使用。请刷新页面重新登录");
		}
		
		ActionLogUtil.insert(request, "newsSync插件中，弹出绑定登录页面");
		return "plugin/newsSync/login";
	}
	
	/**
	 * 登录提交，也就是站点绑定
	 */
	@ResponseBody
	@RequestMapping("loginSubmit${url.suffix}")
	public BaseVO loginSubmit(HttpServletRequest request,
			@RequestParam(value = "username", required = false , defaultValue="") String username,
			@RequestParam(value = "password", required = false , defaultValue="") String password){
		if(!haveSiteAuth()){
			return error("无权使用。请刷新页面重新登录");
		}
		
		//验证码校验
		BaseVO capVO = CaptchaUtil.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			ActionLogUtil.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			return error(capVO.getInfo());
		}else{
			//验证码校验通过
			
			if(username==null || username.length() == 0 ){
				return error(LanguageUtil.show("user_loginUserOrEmailNotNull"));
			}
			if(password==null || password.length() == 0){
				return error(LanguageUtil.show("user_loginPasswordNotNull"));
			}
			
			//判断是用户名还是邮箱登陆的，进而查询邮箱或者用户名，进行登录
			List<User> userList = sqlService.findByProperty(User.class, username.indexOf("@")>-1? "email":"username", username);
			
			if(userList!=null && userList.size()>0){
				User user = userList.get(0);
				
				String md5Password = new Md5Hash(password, user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
				//检验密码是否正确
				if(md5Password.equals(user.getPassword())){
					//检验此用户状态是否正常，是否被冻结
					if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
						return error(LanguageUtil.show("user_loginUserFreeze"));
					}
					
					//找到这个用户的网站
					Site site = sqlService.findAloneBySqlQuery("SELECT * FROM site WHERE userid = "+user.getId(), Site.class);
					if(site == null){
						return error("当前登录用户没有站点！注意，网站子账号插件开通的用户不行！");
					}
					
					//判断当前网站是否跟绑定的网站一样，不能绑定自己
					if(site.getId() - SessionUtil.getSite().getId() == 0){
						return error("你不能绑定自己");
					}
					
					//判断这个源网站是否被绑定过了 
					SiteBind sitebind = sqlService.findAloneBySqlQuery("SELECT * FROM plugin_newssync_site_bind WHERE source_id = "+site.getId(), SiteBind.class);
					if(sitebind != null){
						return error("这个网站已经被编号为"+sitebind.getOtherId()+"的站点绑定了，您不可以再绑定此网站。");
					}
					
					//判断当前自己的这个目标网站是否被绑定过了 
					sitebind = sqlService.findAloneBySqlQuery("SELECT * FROM plugin_newssync_site_bind WHERE source_id = "+SessionUtil.getSite().getId(), SiteBind.class);
					if(sitebind != null){
						return error("您网站已经绑定过其他网站了，不能再绑定");
					}
					
					//通过，进行站点绑定
					sitebind = new SiteBind();
					sitebind.setOtherId(SessionUtil.getSite().getId());	//当前自己的站点
					sitebind.setSourceId(site.getId());
					sqlService.save(sitebind);
					
					ActionLogUtil.insertUpdateDatabase(request, "newsSync插件中，进行网站绑定，成功",sitebind.toString());
					return success();
				}else{
					//密码错误
					ActionLogUtil.insert(request, "newsSync插件中，进行网站绑定，失败","密码错误,登录的用户名："+StringUtil.filterXss(username));
					return error("用户名或密码错误");
				}
			}else{
				//用户不存在
				ActionLogUtil.insert(request, "newsSync插件中，进行网站绑定，失败","用户不存在,登录的用户名："+StringUtil.filterXss(username));
				return error("用户名或密码错误");
			}
		}
	}
	
}