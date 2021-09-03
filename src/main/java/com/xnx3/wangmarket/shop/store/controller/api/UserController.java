package com.xnx3.wangmarket.shop.store.controller.api;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.store.vo.UserListVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户控制器
 * @author 刘鹏
 */
@Controller(value="ShopStoreApiUserController")
@RequestMapping("/shop/store/api/user")
public class UserController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	
	/**
	 * 查看用户列表
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "/list${api.suffix}" ,method = {RequestMethod.POST})
	public UserListVO list(HttpServletRequest request,
						   @RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber) {
		UserListVO vo = new UserListVO();

		User user = getUser();
		Store store = getStore();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store_user");
		sql.appendWhere("shop_store_user.storeid = "+store.getId());
		//查询条件
		//配置按某个字端搜索内容
		//sql.setSearchColumn(new String[] {"phone","userid"});
		int count = sqlService.count("shop_store_user", sql.getWhere());

		// 配置每页显示15条
		Page page = new Page(count, everyNumber, request);
		//sql.appendWhere("shop_store_user.userid = " + user.getId());
		sql.appendWhere("shop_store_user.userid = user.id");
		sql.setSelectFromAndPage("SELECT user.* FROM user,shop_store_user", page);
		sql.setDefaultOrderBy("id DESC");
		// 按照上方条件查询出该实体总数 用集合来装
		List<User> list = sqlService.findBySqlQuery(sql.getSql(),User.class);

		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看用户列表");
		return vo;
			
	}
}