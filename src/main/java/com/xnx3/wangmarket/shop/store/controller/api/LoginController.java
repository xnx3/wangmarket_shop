package com.xnx3.wangmarket.shop.store.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreChildUser;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 商家登录
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiLoginController")
@RequestMapping("/shop/store/api/login/")
public class LoginController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	
	/**
	 * 登陆请求验证
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交三个参数：username(用户名/邮箱)、password(密码)、code（图片验证码的字符）
	 * @return vo.result:
	 * 			<ul>
	 * 				<li>0:失败</li>
	 * 				<li>1:成功</li>
	 * 			</ul>
	 */
	@RequestMapping(value="login${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO login(HttpServletRequest request,Model model){
		BaseVO vo = new BaseVO();
		
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

				//判断是否拥有商家后台管理权限，也就是user.authority 是否是商家权限
				if(!Func.isAuthorityBySpecific(getUser().getAuthority(), Global.STORE_ROLE_ID+"")){
					SessionUtil.logout();//退出登录
					return error("不是商家，无权使用！");
				}
				
				//得到当前登录的用户的信息
				User user = getUser();
				//得到当前用户，在网市场中，user的扩展表 site_user 的信息
				StoreChildUser storeUser = sqlService.findById(StoreChildUser.class, user.getId());
				if(storeUser == null){
					storeUser = new StoreChildUser();
				}
				//缓存进session
				SessionUtil.setStoreUser(storeUser);
				
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
					SessionUtil.setStoreMenuRole(menuMap);
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
					SessionUtil.setStoreMenuRole(menuMap);
				}
				
				if(store == null){
					vo.setResult(BaseVO.FAILURE);
					vo.setInfo("出错！所管理的商铺不存在！");
					return vo;
				}
				SessionUtil.setStore(store);	//加入session缓存
				
				//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
				vo.setInfo("/shop/store/index/index.do");
			}else{
				ActionLogUtil.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
			}
			
			return vo;
		}
	}
	
}
