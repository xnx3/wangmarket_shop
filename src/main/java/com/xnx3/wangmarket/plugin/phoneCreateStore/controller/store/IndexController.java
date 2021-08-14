package com.xnx3.wangmarket.plugin.phoneCreateStore.controller.store;

import com.xnx3.DateUtil;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 利用手机号自助开通客服
 * @author 管雷鸣
 */
@Controller(value="LeimingyunPhoneCreateStoreController")
@RequestMapping("/plugin/phoneCreateStore/")
public class IndexController extends BasePluginController{
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;

	/**
	 * 通过手机验证注册开通
	 * @param inviteid 上级id，推荐者id，user.id
	 */
	@RequestMapping("reg${url.suffix}")
	public String reg(HttpServletRequest request, Model model){
		if(getUser() != null){
			//已登陆
			return error(model, "您已登陆，不可再开通了。");
		}

		ActionLogUtil.insert(request, "phoneCreateKefu plugin 打开根据手机号注册页面");
		return "/plugin/phoneCreateStore/reg";
	}

	/**
	 * 用户开通账户并创建客服平台，进行提交保存
	 * @param username 用户名
	 * @param password 密码
	 * @param phone 手机号
	 * @return 若成功，info返回后台登录成功的sessionid
	 */
	@RequestMapping(value="create.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO create(HttpServletRequest request,
			@RequestParam(value = "username", required = false , defaultValue="") String username,
			@RequestParam(value = "password", required = false , defaultValue="") String password,
			@RequestParam(value = "phone", required = false , defaultValue="") String phone
			){
		username = StringUtil.filterXss(username);
		phone = filter(phone);

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setReferrerid(getUserId());
		user.setAuthority(com.xnx3.wangmarket.shop.core.Global.STORE_ROLE_ID+"");
		BaseVO vo = userService.createUser(user, request);
		if(vo.getResult() - BaseVO.FAILURE == 0){
			//创建用户失败
			return vo;
		}

		//创建用户成功，再创建店铺
		Store store = new Store();
		store.setAddtime(DateUtil.timeForUnix10());
		store.setName("店铺名称");
		store.setSale(0);
		store.setState(Store.STATE_OPEN);
		store.setUserid(user.getId());
		sqlService.save(store);

		//店铺、用户 都创建完毕，那么创建用户跟店铺关联
		StoreUser storeUser = new StoreUser();
		storeUser.setId(user.getId()+"_"+store.getId());
		storeUser.setStoreid(store.getId());
		storeUser.setUserid(user.getId());
		sqlService.save(storeUser);

		//设置当前用户为登录用户
		userService.loginForUserId(request, user.getId());
		//将store加入session
		SessionUtil.setStore(store);
		//将拥有所有功能的管理权限，将功能菜单全部遍历出来，赋予这个用户
		Map<String, String> menuMap = new HashMap<String, String>();
		for (com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum e : com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum.values()) {
			menuMap.put(e.id, "1");
		}
		SessionUtil.setStoreMenuRole(menuMap);

		ActionLogUtil.insertUpdateDatabase(request, "通过手机号创建店铺成功", user.toString());
		return success(request.getSession().getId());
	}
}
