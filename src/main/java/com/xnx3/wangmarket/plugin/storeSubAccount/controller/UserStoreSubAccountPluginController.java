package com.xnx3.wangmarket.plugin.storeSubAccount.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.BaseVO;
import com.xnx3.Lang;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.plugin.storeSubAccount.entity.UserRole;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreChildUser;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenuUtil;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.MenuBean;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum;

/**
 * 当前站点的子用户管理
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/plugin/storeSubAccount/user/")
public class UserStoreSubAccountPluginController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;

	/**
	 * 当前站点的子用户列表
	 */
	@RequestMapping("list${url.suffix}")
	public String list(HttpServletRequest request,Model model){
		if(!haveStoreAuth()){
			return error(model, "请先登录");
		}
		Store store = SessionUtil.getStore();
		
		Sql sql = new Sql(request);
//		sql.setSearchColumn(new String[]{"username","email","nickname","phone","id=","regtime(date:yyyy-MM-dd hh:mm:ss)>"});
		sql.appendWhere("shop_store_child_user.storeid="+store.getId());
		int count = sqlService.count("shop_store_child_user", sql.getWhere());
		Page page = new Page(count, SystemUtil.getInt("LIST_EVERYPAGE_NUMBER"), request);
		sql.appendWhere("shop_store_child_user.id = user.id");
		sql.setSelectFromAndPage("SELECT user.* FROM user,shop_store_child_user", page);
		sql.setDefaultOrderBy("user.id DESC");
		sql.setOrderByField(new String[]{"id","lasttime"});
		List<User> list = sqlService.findBySql(sql, User.class);
		
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		ActionLogUtil.insert(request, "查看店铺子账户列表");
		return "/plugin/storeSubAccount/user/list";
	}
	
	/**
	 * 增加/编辑用户信息
	 * @param userid 用户的id，如果是修改，则传入要修改的用户的id，如果不传递或者0，则是新增
	 */
	@RequestMapping("edit${url.suffix}")
	public String edit(HttpServletRequest request,Model model,
			@RequestParam(value = "userid", required = false , defaultValue="0") int userid){
		if(!haveStoreAuth()){
			return error(model, "请先登录");
		}
		
		//将 TemplateMenuEnum 枚举中定义的菜单拿出来，等级层次分清，以便随时使用
		Map<String, MenuBean> menuMap = new HashMap<String, MenuBean>();
		menuMap.putAll(TemplateAdminMenuUtil.menuMap);
		
		if(userid > 0){
			//修改
			//查询出当前修改的子用户的信息
			User user = sqlService.findById(User.class, userid);
			//判断该用户是不是当前网站管理者的下级用户，是否有编辑权限
			if(user.getReferrerid() - getUserId() != 0){
				return error(model, "要编辑的用户不是您的子用户，无法操作！");
			}
			
			//将map中的数据进行标注，将已有的权限标注上
			//首先将用户取得的结果数据，转化为map，以便用MenuBean.id 直接取
			Map<String, UserRole> dbMap = new HashMap<String, UserRole>();
			List<UserRole> list = sqlService.findBySqlQuery("SELECT * FROM plugin_storesubaccount_user_role WHERE userid = "+userid, UserRole.class);
			for (int i = 0; i < list.size(); i++) {
				UserRole ur = list.get(i);
				dbMap.put(ur.getMenu(), ur);
			}
			
			//遍历所有的菜单权限项，将数据库存的，也就是这个用户现有的权限，加入标注上
			for (Map.Entry<String, MenuBean> entry : menuMap.entrySet()) {
				MenuBean mb = entry.getValue();
				//判断数据库中是否有这个权限，如果有，那么标注其选中
				if(dbMap.get(mb.getId()) != null){
					//有这个权限，那么标记选中
					mb.setIsUse(1);
				}
				
				//遍历子菜单
				for (int i = 0; i < mb.getSubList().size(); i++) {
					MenuBean subMb = mb.getSubList().get(i);
					//判断数据库中是否有这个权限，如果有，那么标注其选中
					if(dbMap.get(subMb.getId()) != null){
						//有这个权限，那么标记选中
						subMb.setIsUse(1);
					}
				}
			}
			
			model.addAttribute("user", user);
		}else{
			//添加
			
		}
		
		ActionLogUtil.insert(request, "打开添加/编辑子账户页面");
		model.addAttribute("map", menuMap);
		return "/plugin/storeSubAccount/user/edit";
	}
	

	/**
	 * 用户列表，增加用户弹出页面的信息提交
	 * @param username 开通子账户的用户名，开通新子账户有效，编辑时是不会有这一项显示的
	 * @param password 开通子账户的密码，开通新子账户有效，编辑时是不会有这一项显示的
	 * @param userid 编辑用户子账户权限所对应的子账户的userid,编辑才有此项
	 * @param menus 该子账户的menu数组，该用户拥有哪些菜单显示的权限
	 */
	@RequestMapping("save${url.suffix}")
	@ResponseBody
	public BaseVO save(HttpServletRequest request,Model model,
			@RequestParam(value = "username", required = false , defaultValue="") String username,
			@RequestParam(value = "password", required = false , defaultValue="") String password,
			@RequestParam(value = "userid", required = false , defaultValue="0") int userid,
			@RequestParam(value = "menu", required = false , defaultValue="") String menu){
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		//将传入的menu进行拆分，为数组
		String[] menus = menu.split(",");
		//将数组进行过滤，将过滤结果加入新的menuList
		List<String> menuList = new ArrayList<String>();
		for (int i = 0; i < menus.length; i++) {
			String m = menus[i];
			if(m.length() > 1){
				//有值，那么加入menuList
				menuList.add(m);
			}
		}
		
		//判断以下用户自己定义的，是否是小于 TemplateMenuEnum 变量的个数，如果多，那就是非法了
		if(menuList.size() > TemplateMenuEnum.values().length){
			return error("非法操作");
		}
		
		//首先将用户取得的结果数据，转化为map，以便用MenuBean.id 直接取。如果用户已存在，就在下面修改逻辑中，将已由的权限加入map。如果用户不存在，新添加，那么这里就是空的就行了
		Map<String, UserRole> dbMap = new HashMap<String, UserRole>();
		
		Store store = SessionUtil.getStore();
		
		if(userid > 0){
			//编辑
			//查询出当前修改的子用户的信息
			User user = sqlService.findById(User.class, userid);
			//判断该用户是不是当前网站管理者的下级用户，是否有编辑权限
			if(user.getReferrerid() - getUserId() != 0){
				return error("要编辑的用户不是您的子用户，无法操作！");
			}
			
			//取出用户之前拥有的menu权限
			List<UserRole> list = sqlService.findBySqlQuery("SELECT * FROM plugin_storesubaccount_user_role WHERE userid = "+userid, UserRole.class);
			for (int i = 0; i < list.size(); i++) {
				UserRole ur = list.get(i);
				dbMap.put(ur.getMenu(), ur);
			}
			
		}else{
			//新增
			
			//当前登陆用户的user
			User currentUser = getUser();
			
			//要创建的网站的user
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setAuthority(Global.STORE_ROLE_ID+"");	//权限是商城用户
			user.setReferrerid(currentUser.getId());
			BaseVO vo = userService.createUser(user, request);
			if(vo.getResult() - BaseVO.FAILURE == 0){
				return error(vo.getInfo());
			}
			userid = Lang.stringToInt(vo.getInfo(), 0);
			if(userid == 0){
				return error("创建用户失败");
			}
			
			//成功
			StoreChildUser storeUser = new StoreChildUser();
			storeUser.setId(userid);
			storeUser.setStoreid(store.getId());
			sqlService.save(storeUser);
		}
		
		//操作用户menu权限
		//首先遍历给用户新设定的权限
		for (int i = 0; i < menuList.size(); i++) {
			String m = menuList.get(i);
			
			//判断下这个是否是已经在用户权限里面了，用户已经有了这个权限
			if(dbMap.get(m) != null){
				//用户已经有这个权限了，那么就不需要再将它往数据库 userrole 存储了。同时，将它从map中移除掉，以便最后看看到底还有哪些权限没用到
				dbMap.remove(m);
			}else{
				//用户没有这个权限，那么加入，数据表中创建关联
				UserRole userRole = new UserRole();
				userRole.setMenu(m);
				userRole.setStoreid(store.getId());
				userRole.setUserid(userid);
				sqlService.save(userRole);
			}
		}
		//最后遍历以下dbMap，看看里面还有哪些权限，是不再menulist之中的，要删除的
		for (Map.Entry<String, UserRole> entry : dbMap.entrySet()) {
			//进行删除
			sqlService.delete(entry.getValue());
		}
		
		ActionLogUtil.insertUpdateDatabase(request, "保存子账户信息");
		return success();
	}
	
	/**
	 * 删除子用户
	 * @param request
	 * @param model
	 * @param userid 要删除的子用户的user.id
	 * @return
	 */
	@RequestMapping("deleteUser${url.suffix}")
	@ResponseBody
	public BaseVO deleteUser(HttpServletRequest request,Model model,
			@RequestParam(value = "userid", required = false , defaultValue="0") int userid){
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		//查询出当前修改的子用户的信息
		User user = sqlService.findById(User.class, userid);
		if(user == null){
			return error("用户不存在");
		}
		//判断该用户是不是当前网站管理者的下级用户，是否有权限
		if(user.getReferrerid() - getUserId() != 0){
			return error("要删除的用户不是您的子用户，无法操作！");
		}
		sqlService.delete(user);
		//删除 site user表的关联
		StoreChildUser storeUser = sqlService.findById(StoreChildUser.class, user.getId());
		if(storeUser != null){
			sqlService.delete(storeUser);
		}
		
		ActionLogUtil.insertUpdateDatabase(request, userid, "删除子账户:"+user.toString());
		return success();
	}
	
	/**
	 * 修改子用户的登陆密码
	 * @param userid 要修改的子用户的id
	 * @param newPassword 要修改成的密码
	 * @return
	 */
	@RequestMapping("updatePassword${url.suffix}")
	@ResponseBody
	public BaseVO updatePassword(HttpServletRequest request,Model model,
			@RequestParam(value = "userid", required = false , defaultValue="0") int userid,
			@RequestParam(value = "newPassword", required = false , defaultValue="") String newPassword){
		if(!haveStoreAuth()){
			return error("请先登录");
		}
		
		//查询出当前修改的子用户的信息
		User user = sqlService.findById(User.class, userid);
		if(user == null){
			return error("用户不存在");
		}
		//判断该用户是不是当前网站管理者的下级用户，是否有权限
		if(user.getReferrerid() - getUserId() != 0){
			return error("要修改的用户不是您的子用户，无法操作！");
		}
		
		ActionLogUtil.insertUpdateDatabase(request, userid, "修改子账户密码，newPassword:"+newPassword);
		return userService.updatePassword(userid, newPassword);
	}
	
}