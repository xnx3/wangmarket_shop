package com.xnx3.wangmarket.shop.superadmin.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.wangmarket.shop.core.entity.StoreData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.autoApplyStore.vo.StoreListVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.vo.StoreVO;

/**
 * 商家管理
 * @author 管雷鸣
 */
@Controller(value="ShopSuperAdminStoreController")
@RequestMapping("/shop/superadmin/store/")
public class StoreController extends BasePluginController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	/**
	 * 查看商家列表
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="list.json", method = {RequestMethod.POST})
	public StoreListVO list(HttpServletRequest request,Model model) {
		StoreListVO vo = new StoreListVO();
//		if(!haveSuperAdminAuth()){
//			return error("无权使用，请先登录");
//		}
		
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store");
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"name","contacts","phone"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_store", sql.getWhere());
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		sql.setSelectFromAndPage("SELECT * FROM shop_store ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		List<Store> list = sqlService.findBySql(sql,Store.class);
		
		// 将信息保存到vo中
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, "查看商家列表");
		return vo;
	}

	/**
	 * 开通店铺
	 */
	@ResponseBody
	@RequestMapping(value="edit.json" ,method = {RequestMethod.POST})
	public BaseVO edit(HttpServletRequest request, Model model){
		if(!haveSuperAdminAuth()){
			return error("无权使用，请先登录");
		}
		
		ActionLogUtil.insert(request, "进入开通店铺的页面");
		return success();
	}
	
	/**
	 * 开通店铺提交保存的
	 */
	@ResponseBody
	@RequestMapping(value="addSubmit.json",method = {RequestMethod.POST})
	public BaseVO addSubmit(HttpServletRequest request,
			@RequestParam(value = "username",defaultValue = "", required = false) String username,
			@RequestParam(value = "password",defaultValue = "", required = false) String password){
		if(!haveSuperAdminAuth()){
			return error("无权使用，请先登录");
		}
		
		if(username.length() < 1){
			return error("请输入店铺登录用户名");
		}
		if(password.length() < 1){
			return error("请输入店铺登录密码");
		}
		
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
		
		ActionLogUtil.insert(request, "开通店铺", store.toString());
		return success(store.getId()+"");
	}

	/**
	 * 使用店铺ID查询店铺详情信息
	 */
	@ResponseBody
	@RequestMapping(value="storeDetailsView.json", method = RequestMethod.POST)
	public StoreVO storeDetailsView(HttpServletRequest request,
						@RequestParam(value = "id", required = true) int id,Model model){
		StoreVO vo = new StoreVO();
		//查询店铺
		Store store = sqlService.findById(Store.class, id);
//		if(store == null){
//			return error(model, "要查看的店铺不存在");
//		}
		//查询店铺描述
		StoreData storeData = sqlService.findById(StoreData.class, id);
		//如果无店铺描述设置为无
		if(storeData == null){
			storeData = new StoreData();
			storeData.setId(id);
			storeData.setNotice("无");
		}
		ActionLogUtil.insert(request,store.getId(), "总管理后台-店铺详情", store.toString());

		vo.setStore(store);
		vo.setStoreData(storeData);
		return vo;
	}

	/**
	 * 网站更改密码
	 * @param userid 要更改密码的用户ID
	 * @param newPassword 要更改上的新密码
	 */
	@RequestMapping(value="updateShopPassword.json", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO storeUpdatePassword(HttpServletRequest request,
			@RequestParam(value = "userid", required = true) int userid,
			@RequestParam(value = "newPassword", required = true) String newPassword){
		User user = sqlService.findById(User.class, userid);
		if(user == null){
			return error("用户不存在");
		}
		
		ActionLogUtil.insertUpdateDatabase(request, userid, "管理后台给其下的某个商家更改密码", newPassword);
		return userService.updatePassword(userid, newPassword);
	}
}
