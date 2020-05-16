package com.xnx3.wangmarket.plugin.createStoreApi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ApplicationPropertiesUtil;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.LoginVO;
import com.xnx3.net.AuthHttpUtil;
import com.xnx3.net.HttpResponse;
import com.xnx3.wangmarket.Authorization;
import com.xnx3.wangmarket.plugin.createStoreApi.entity.UserQuickLogin;
import com.xnx3.wangmarket.plugin.createStoreApi.vo.RegVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

import net.sf.json.JSONObject;

/**
 * 通过api开通商铺
 * @author 管雷鸣
 */
@Controller(value="CreateStoreApiPluginController")
@RequestMapping("/plugin/storeApi/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private UserService userService;
	
	//登录、注册都要用，验证来源是否合法
	private static final String TOKNE_MY = "a3o837geo3y7t03y70yfniutho734y738oehfsyn83go";
	
	/**
	 * 开通的请求验证
	 * @param username 要注册用户的用户名（必填）
	 * @param password 要注册当用户的密码（必填）
	 * @param referrerid 注册成功的这个用户，他的上级是哪个。这个就是上级的user.id
	 * @param regstoreid 要注册开通的这个商铺的id，这里制定商铺的id编号。如果没有指定，则是随机分配一个id。要废弃这个，用自动生成，不传入
	 * @return vo result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功。用info获取64位登录码</li>
	 * 			</ul>
	 */
	@RequestMapping(value="reg${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public RegVO reg(HttpServletRequest request,Model model,
			@RequestParam(value = "username", required = false, defaultValue="") String username,
			@RequestParam(value = "password", required = false, defaultValue="") String password,
			@RequestParam(value = "referrerid", required = false, defaultValue="1") int referrerid,
			@RequestParam(value = "regstoreid", required = false, defaultValue="0") int regstoreid,
			@RequestParam(value = "token", required = false, defaultValue="") String token){
		RegVO vo = new RegVO();
		if(!TOKNE_MY.equals(token)){
			if(!checkAuthorize(token)){
				//既不是网市场本身的，又不是授权用户的，那么失败
				vo.setBaseVO(BaseVO.FAILURE, "token failure");
				return vo;
			}
		}
		
		if(username.length() == 0){
			vo.setBaseVO(BaseVO.FAILURE, "请传入用户名");
			return vo;
		}
		if(password.length() == 0){
			vo.setBaseVO(BaseVO.FAILURE, "请传入密码");
			return vo;
		}
		
		User user = new User();
		user.setUsername(StringUtil.filterXss(username));
		user.setPassword(password);
		user.setReferrerid(referrerid);
		user.setAuthority(Global.STORE_ROLE_ID+"");
		BaseVO baseVO = userService.createUser(user, request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			int userid = Lang.stringToInt(baseVO.getInfo(), 0);
			ActionLogUtil.insert(request,userid, "注册成功","通过用户名密码, createStoreApi plugin");
			
			//开通完用户，在开通商铺
			String insertSql = "";
			if(regstoreid > 0){
				insertSql = "INSERT INTO shop_store(id, addtime, name, state, userid) VALUES("+regstoreid+", "+DateUtil.timeForUnix10()+", '商铺名字', "+Store.STATE_OPEN+", "+userid+");";
			}else{
				insertSql = "INSERT INTO shop_store(addtime, name, state, userid) VALUES("+DateUtil.timeForUnix10()+", '商铺名字', "+Store.STATE_OPEN+", "+userid+");";
			}
			sqlService.executeSql(insertSql);
			
			//保存用户快速登录码
			UserQuickLogin userQuickLogin = new UserQuickLogin();
			userQuickLogin.setId(Lang.uuid()+Lang.uuid());
			userQuickLogin.setUserid(userid);
			sqlService.save(userQuickLogin);
			
			//将当前用户的登录码加入info返回
			vo.setInfo(userQuickLogin.getId());
			
			//将当前用户变为已登陆状态
			userService.loginForUserId(request, userid);
			
			//将sessionid加入vo返回
			HttpSession session = request.getSession();
			vo.setToken(session.getId());
			
			//取得用户开通的商铺的id
			Store store = sqlService.findAloneBySqlQuery("SELECT * FROM shop_store WHERE userid = "+userid, Store.class);
			if(store != null){
				vo.setStoreid(store.getId());
			}else{
				vo.setStoreid(0);
			}
			
			//加入user信息
			vo.setUser(getUser());
		}else{
			vo.setBaseVO(BaseVO.FAILURE, baseVO.getInfo());
			ActionLogUtil.insert(request, "用户名密码模式注册失败",baseVO.getInfo());
		}
		
		return vo;
	}
	
	/**
	 * 进行登录操作
	 * @param code 64位登录码
	 * @param token 约定的token
	 * @return 若成功，info返回session id
	 */
	@RequestMapping("login${url.suffix}")
	public String login(HttpServletRequest request,Model model,
			@RequestParam(value = "code", required = false, defaultValue="") String code,
			@RequestParam(value = "token", required = false, defaultValue="") String token){
		if(!TOKNE_MY.equals(token)){
			if(!checkAuthorize(token)){
				//既不是网市场本身的，又不是授权用户的，那么失败
				return error(model, "token failure");
			}
		}
		
		if(code.length() != 64){
			return error(model,"登录码长度不合格");
		}
		
		UserQuickLogin u = sqlCacheService.findById(UserQuickLogin.class, code);
		if(u == null){
			return error(model,"用户不存在");
		}
		//将当前用户变为已登陆状态
		userService.loginForUserId(request, u.getUserid());
		//判断当前用户是否是商家，看用户是否对应 Store
		Store store = sqlCacheService.findAloneByProperty(Store.class, "userid", u.getUserid());
//		Store store = sqlService.findAloneBySqlQuery("SELECT * FROM shop_store WHERE userid = "+u.getUserid(), Store.class);
		if(store == null){
			SessionUtil.logout();	//退出登录
			return error(model,"您不是商铺管理人员，无法登陆");
		}
		SessionUtil.setStore(store);	//加入session缓存
		
		//将拥有所有功能的管理权限，将功能菜单全部遍历出来，赋予这个用户
		Map<String, String> menuMap = new HashMap<String, String>();
		for (com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum e : com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum.values()) {
			menuMap.put(e.id, "1");
		}
		SessionUtil.setStoreMenuRole(menuMap);
		
		ActionLogUtil.insertUpdateDatabase(request, "用户登录成功","userid:"+u.getUserid());
		return redirect("shop/store/index/index.do");
	}
	
	/**
	 * 判断当前token（授权码）是否有效
	 * @param token
	 * @return true 有效，可用
	 */
	public boolean checkAuthorize(String token){
		String CACHE_KEY = "shop:authorize:"+token;
		Object cache = CacheUtil.get(CACHE_KEY);
		if(cache != null && cache.toString().length() > 0){
			//如果缓存中有，直接返回true
			return true;
		}
		
		String checkAuthorizeUrl = ApplicationPropertiesUtil.getProperty("plugin.createStoreApi.tokenUrl");
		String domain = ApplicationPropertiesUtil.getProperty("plugin.createStoreApi.domain");
		
		AuthHttpUtil http = new AuthHttpUtil();
		Map<String, String> params = new HashMap<String, String>();
		params.put("auth", token);
		params.put("domain", domain);
		params.put("version", "1.0");
		
		HttpResponse hr = null;
		try {
			hr = http.post(checkAuthorizeUrl, params);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(hr.getCode() == 200){
			//正常，响应 200
			JSONObject json = JSONObject.fromObject(hr.getContent().trim());
			if(json.get("result") != null){
				String result = json.getString("result");
				if(result.equals("1")){
					//授权，加入缓存
					CacheUtil.setWeekCache(CACHE_KEY, token);
					return true;
				}
			}
		}
		return false;
	}
}