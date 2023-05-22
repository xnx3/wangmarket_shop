package com.xnx3.j2ee.controller;

import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.CacheUtil;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ApplicationPropertiesUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.media.CaptchaUtil;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreChildUser;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller(value="WMLoginController")
@RequestMapping("/")
public class LoginController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	

	/**
	 * 验证码图片显示，直接访问此地址可查看图片
	 */
	@RequestMapping("/captcha${url.suffix}")
	public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ActionLogUtil.insert(request, "获取验证码显示");
		
		CaptchaUtil captchaUtil = new CaptchaUtil();
	    captchaUtil.setCodeCount(5);                   //验证码的数量，若不增加图宽度的话，只能是1～5个之间
	    captchaUtil.setFont(new Font("Fixedsys", Font.BOLD, 21));    //验证码字符串的字体
	    captchaUtil.setHeight(18);  //验证码图片的高度
	    captchaUtil.setWidth(110);      //验证码图片的宽度
//	    captchaUtil.setCode(new String[]{"我","是","验","证","码"});   //如果对于数字＋英文不满意，可以自定义验证码的文字！
	    com.xnx3.j2ee.util.CaptchaUtil.showImage(captchaUtil, request, response);
	}
	
	/**
	 * 登陆页面
	 */
	@RequestMapping("login.do")
	public String login(HttpServletRequest request,Model model){
		if(getUser() != null){
			ActionLogUtil.insert(request, "进入登录页面", "已经登录成功，无需再登录，进行跳转");
			return redirect("admin/index/index.do");
		}
		
		ActionLogUtil.insert(request, "进入登录页面");
		return "/login/login";
	}

	/**
	 * 登陆请求验证
	 * @param username 登录的用户名或邮箱 <required> <example=admin>
	 * @param password 登录密码 <required> <example=admin>
	 * @param code 图片验证码的字符 <example=1234> <required>
	 * @return 登录结果
	 */
	@RequestMapping(value="loginSubmit${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO loginSubmit(HttpServletRequest request,Model model){
		LoginVO vo = new LoginVO();
		String wmLogin = ApplicationPropertiesUtil.getProperty("wm.login");
		if(wmLogin != null && wmLogin.equalsIgnoreCase("false")) {
			vo.setBaseVO(LoginVO.FAILURE, "此接口不允许被使用！您可修改application.properties中的wm.login=true ，重启项目，即可使用本接口");
			return vo;
		}
		
		//验证码校验
		BaseVO capVO = com.xnx3.j2ee.util.CaptchaUtil.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			ActionLogUtil.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			vo.setBaseVO(capVO);
			return vo;
		}else{
			//验证码校验通过
			
			BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
			vo.setBaseVO(baseVO);
			if(baseVO.getResult() == BaseVO.SUCCESS){
				ActionLogUtil.insert(request, "用户名密码模式登录成功");
				
				//判断是什么角色
				if(Func.isAuthorityBySpecific(getUser().getAuthority(), Global.STORE_ROLE_ID+"")){
					//是商家
					
					BaseVO storeVO = storeLogin();
					if(storeVO.getResult() - BaseVO.FAILURE == 0) {
						vo.setBaseVO(BaseVO.FAILURE, storeVO.getInfo());
						return vo;
					}
					vo.setInfo("/store/index/index.jsp");
				}else if(Func.isAuthorityBySpecific(getUser().getAuthority(), com.xnx3.j2ee.Global.roleId_admin+"")) {
					//是超级管理
					
					vo.setInfo("admin/index/index.do");
				}else if(Func.isAuthorityBySpecific(getUser().getAuthority(), com.xnx3.j2ee.Global.roleId_user+"")) {
					//是普通用户
					SessionUtil.logout();//退出登录
					
					vo.setBaseVO(BaseVO.FAILURE, "无权使用");
					return vo;
				}
				
				//将sessionid加入vo返回
				HttpSession session = request.getSession();
				vo.setToken(session.getId());
				
				//加入user信息
				vo.setUser(getUser());
			}else{
				ActionLogUtil.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
			}
			
			return vo;
		}
	}
	
	private BaseVO storeLogin() {
		BaseVO vo = new BaseVO();

		//得到当前登录的用户的信息
		User user = getUser();
		//得到当前用户，在网市场中，user的扩展表 site_user 的信息
		StoreChildUser storeUser = sqlService.findById(StoreChildUser.class, user.getId());
		if(storeUser == null){
			storeUser = new StoreChildUser();
		}
		//缓存进session
		com.xnx3.wangmarket.shop.store.util.SessionUtil.setStoreUser(storeUser);
		
		Store store = null;	//当前所管理的商城
		//判断 storeUser 中是否storeid，也就是是否有商家管理的子用户。因为子用户是有user.storeid的
		if(storeUser.getStoreid() != null && storeUser.getStoreid() > 0){
			//是商家管理子用户.既然是有子账户了，那肯定子账户插件是使用了，也就可以进行一下操作了
			store = sqlService.findById(Store.class, storeUser.getStoreid());
			
			//网站子用户，需要读取他拥有哪些权限，也缓存起来
			List<Map<String, Object>> menuList = sqlService.findMapBySqlQuery("SELECT menu FROM plugin_storesubaccount_user_role WHERE userid = "+user.getId());
			Map<String, String> menuMap = new HashMap<String, String>();
			for (int i = 0; i < menuList.size(); i++) {
				Map<String, Object> menu = menuList.get(i);
				if(menu.get("menu") != null){
					String m = (String) menu.get("menu");
					menuMap.put(m, "1");
				}
			}
			com.xnx3.wangmarket.shop.store.util.SessionUtil.setStoreMenuRole(menuMap);
		}else{
			//是商城管理者，拥有所有权限的
			//得到当前用户站点的相关信息，加入userBean，以存入Session缓存起来
			store = sqlService.findAloneBySqlQuery("SELECT * FROM shop_store WHERE userid = "+getUserId()+" ORDER BY id DESC", Store.class);
			ConsoleUtil.info("商城管理者，拥有网站所有权限:"+user.getUsername());
			
			//将拥有所有功能的管理权限，将功能菜单全部遍历出来，赋予这个用户
			Map<String, String> menuMap = new HashMap<String, String>();
			for (com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum e : com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum.values()) {
				menuMap.put(e.id, "1");
			}
			com.xnx3.wangmarket.shop.store.util.SessionUtil.setStoreMenuRole(menuMap);
		}
		
		if(store == null){
			vo.setResult(BaseVO.FAILURE);
			vo.setInfo("出错！所管理的商铺不存在！");
			return vo;
		}
		com.xnx3.wangmarket.shop.store.util.SessionUtil.setStore(store);	//加入session缓存
		
		//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
		vo.setInfo("/shop/store/index/index.do");
		
		return vo;
	}
	

	/**
	 * 使用用户名加密码进行登录。无需验证码。  
	 * <p>注意，此方式会进行频率拦截，同一个ip失败五次将在1小时内不允许再次尝试</p>
	 * @param username 登录的用户名或邮箱 <required> <example=admin>
	 * @param password 登录密码 <required> <example=admin>
	 * @return 登录结果
	 */
	@RequestMapping(value="loginByUsernameAndPassword.json", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO loginByUsernameAndPassword(HttpServletRequest request,Model model){
		LoginVO vo = new LoginVO();
		String wmLogin = ApplicationPropertiesUtil.getProperty("wm.login");
		if(wmLogin != null && wmLogin.equalsIgnoreCase("false")) {
			vo.setBaseVO(LoginVO.FAILURE, "此接口不允许被使用！您可修改application.properties中的wm.login=true ，重启项目，即可使用本接口");
			return vo;
		}
		
		//获取用户当前ip
		String ip = IpUtil.getIpAddress(request);
		//判断是否已被拦截控制不允许登录
		Object cacheObj = CacheUtil.get(ip);
		int ipCishu = 0;	//这个ip当前第几次请求
		if(cacheObj != null) {
			ipCishu = (int) cacheObj;
		}
		if(ipCishu > 4) {
			vo.setBaseVO(LoginVO.FAILURE, "您当前尝试过于频繁，请一小时后再试");
			return vo;
		}
		
		BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
		vo.setBaseVO(baseVO);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			ActionLogUtil.insert(request, "用户名密码模式登录成功");
			
			//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
			vo.setInfo("admin/index/index.do");
			
			//将sessionid加入vo返回
			HttpSession session = request.getSession();
			vo.setToken(session.getId());
			
			//加入user信息
			vo.setUser(getUser());
		}else {
			CacheUtil.set(ip,ipCishu++, 3600);	//记录错误次数
		}
		return vo;
	}
	
	
	
	/**
	 * 退出登录状态
	 * 推荐使用 logout.json
	 * @deprecated 
	 */
	@RequestMapping(value="logout${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO logout(HttpServletRequest request,Model model){
		SessionUtil.logout();
		return success();
	}
	
	/**
	 * 退出登录状态
	 */
	@RequestMapping(value="logout.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO logoutjson(HttpServletRequest request,Model model){
		SessionUtil.logout();
		return success();
	}
	
}
