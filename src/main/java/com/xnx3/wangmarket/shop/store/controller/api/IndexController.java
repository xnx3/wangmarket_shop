package com.xnx3.wangmarket.shop.store.controller.api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.core.service.StoreService;
import com.xnx3.wangmarket.shop.store.vo.IndexVO;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenuUtil;

/**
 * 代理后台
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiIndexPluginController")
@RequestMapping("/shop/store/api/index/")
public class IndexController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private StoreService storeService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private UserService userService;
	
	
	/**
	 * 登录成功后的首页
	 */
	@ResponseBody
	@RequestMapping(value = "index${api.suffix}" ,method = {RequestMethod.POST})
	public IndexVO index(HttpServletRequest request){
		//获取网站后台管理系统有哪些功能插件，也一块列出来,以直接在网站后台中显示出来
//		String pluginMenu = "";
//		if(PluginManage.cmsSiteClassManage.size() > 0){
//			for (Map.Entry<String, PluginRegister> entry : PluginManage.cmsSiteClassManage.entrySet()) {
//				PluginRegister plugin = entry.getValue();
//				pluginMenu += "<dd class=\"twoMenu\"><a id=\""+entry.getKey()+"\" class=\"subMenuItem\" href=\"javascript:loadUrl('"+plugin.menuHref()+"');\">"+plugin.menuTitle()+"</a></dd>";
//			}
//		}
//		model.addAttribute("pluginMenu", pluginMenu);
		
		ActionLogUtil.insert(request, "进入商家管理后台首页", getStore().toString());
		
		IndexVO vo = new IndexVO();
		vo.setMenuHtml(TemplateAdminMenuUtil.getLeftMenuHtml()); //左侧菜单
		vo.setUseTokenCodeLogin(SessionUtil.getParentToken() != null);	//是否是第三方平台通过token+code直接登录的，如果是，那么是不显示更改密码、退出登录功能的。true：是三方平台token+code登录的

		return vo;
	}
	

	
	/**
	 * 修改密码，如果使用的是账号、密码方式注册、登录的话。
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	@ResponseBody
	@RequestMapping(value="updatePassword${api.suffix}", method = RequestMethod.POST)
	public BaseVO updatePassword(HttpServletRequest request, 
			@RequestParam(value = "oldPassword", required = false, defaultValue = "") String oldPassword,
			@RequestParam(value = "newPassword", required = false, defaultValue = "") String newPassword){
		if(oldPassword.length() == 0){
			ActionLogUtil.insert(request, "修改密码", "失败：未输入旧密码");
			return error("请输入旧密码");
		}else{
			User user=sqlService.findById(User.class, getUser().getId());
			//将输入的原密码进行加密操作，判断原密码是否正确
			
			if(new Md5Hash(oldPassword, user.getSalt(),com.xnx3.j2ee.Global.USER_PASSWORD_SALT_NUMBER).toString().equals(user.getPassword())){
				BaseVO vo = userService.updatePassword(getUserId(), newPassword);
				if(vo.getResult() - BaseVO.SUCCESS == 0){
					ActionLogUtil.insertUpdateDatabase(request, "修改密码", "成功");
					return success("修改成功");
				}else{
					ActionLogUtil.insert(request, "修改密码", "失败："+vo.getInfo());
					return error(vo.getInfo());
				}
			}else{
				ActionLogUtil.insert(request, "修改密码", "失败：原密码错误");
				return error("原密码错误！");
			}
		}
	}
	
}