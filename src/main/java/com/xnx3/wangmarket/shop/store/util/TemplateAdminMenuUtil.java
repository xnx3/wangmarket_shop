package com.xnx3.wangmarket.shop.store.util;

import java.util.HashMap;
import java.util.Map;
import com.xnx3.j2ee.pluginManage.PluginManage;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.FirstMenu;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.MenuBean;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.TemplateMenuEnum;

/**
 * 网站管理后台，左侧菜单相关的工具类，也可以理解为权限相关，控制左侧菜单的显示、隐藏，控制某个用户哪个菜单可见，哪个不可见
 * @author 管雷鸣
 *
 */
public class TemplateAdminMenuUtil {
	/**
	 * 将 TemplateMenuEnum 枚举中定义的菜单拿出来，等级层次分清，以便随时使用
	 * key：TemplateMenuEnum.id
	 * value:MenuBean
	 */
	public static Map<String, MenuBean> menuMap = new HashMap<String, MenuBean>();
	
	static{
		menuMap.clear();
		
		//取出所有的权限菜单-一级菜单
		for (TemplateMenuEnum e : TemplateMenuEnum.values()) {
			MenuBean menuBean = new MenuBean(e);
			if(menuBean.getParentid().length() < 2){
				//是一级菜单
				menuMap.put(menuBean.getId(), menuBean);
			}
		}
		//再取出所有的权限菜单的二级菜单
		for (TemplateMenuEnum e : TemplateMenuEnum.values()) {
			MenuBean menuBean = new MenuBean(e);
			if(menuBean.getParentid().length() > 2){
				//是二级菜单
				menuMap.get(menuBean.getParentid()).getSubList().add(menuBean);
			}
		}
		
	}
	
	/**
	 * 获取网站管理后台登陆成功后，显示的菜单列表的html。这个是根据当前用户所拥有哪些权限来显示哪些内容的
	 */
	public static String getLeftMenuHtml(){
		Map<String, String> map = SessionUtil.getStoreMenuRole();
		StringBuffer sb = new StringBuffer();
		
		//取出所有的权限菜单-一级菜单
		for (TemplateMenuEnum e : TemplateMenuEnum.values()) {
			if(map.get(e.id) != null){
				//有这一项
			}
		}
		
		//商家设置
		if(map.get(TemplateMenuEnum.SYSTEM_StoreSet.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_StoreSet.id, TemplateMenuEnum.SYSTEM_StoreSet.id, TemplateMenuEnum.SYSTEM_StoreSet.href, TemplateMenuEnum.SYSTEM_StoreSet.icon, TemplateMenuEnum.SYSTEM_StoreSet.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//轮播图
		if(map.get(TemplateMenuEnum.SYSTEM_CarouselImage.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_CarouselImage.id, TemplateMenuEnum.SYSTEM_CarouselImage.id, TemplateMenuEnum.SYSTEM_CarouselImage.href, TemplateMenuEnum.SYSTEM_CarouselImage.icon, TemplateMenuEnum.SYSTEM_CarouselImage.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//用户管理
		if(map.get(TemplateMenuEnum.SYSTEM_User.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_User.id, TemplateMenuEnum.SYSTEM_User.id, TemplateMenuEnum.SYSTEM_User.href, TemplateMenuEnum.SYSTEM_User.icon, TemplateMenuEnum.SYSTEM_User.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//商品分类
		if(map.get(TemplateMenuEnum.SYSTEM_GoodsType.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_GoodsType.id, TemplateMenuEnum.SYSTEM_GoodsType.id, TemplateMenuEnum.SYSTEM_GoodsType.href, TemplateMenuEnum.SYSTEM_GoodsType.icon, TemplateMenuEnum.SYSTEM_GoodsType.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//商品管理
		if(map.get(TemplateMenuEnum.SYSTEM_Goods.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_Goods.id, TemplateMenuEnum.SYSTEM_Goods.id, TemplateMenuEnum.SYSTEM_Goods.href, TemplateMenuEnum.SYSTEM_GoodsType.icon, TemplateMenuEnum.SYSTEM_Goods.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//订单管理
		if(map.get(TemplateMenuEnum.SYSTEM_Order.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_Order.id, TemplateMenuEnum.SYSTEM_Order.id, TemplateMenuEnum.SYSTEM_Order.href, TemplateMenuEnum.SYSTEM_Order.icon, TemplateMenuEnum.SYSTEM_Order.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//支付设置
		if(map.get(TemplateMenuEnum.SYSTEM_PaySet.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_PaySet.id, TemplateMenuEnum.SYSTEM_PaySet.id, TemplateMenuEnum.SYSTEM_PaySet.href, TemplateMenuEnum.SYSTEM_PaySet.icon, TemplateMenuEnum.SYSTEM_PaySet.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		//订单设置
		if(map.get(TemplateMenuEnum.SYSTEM_OrderRule.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_OrderRule.id, TemplateMenuEnum.SYSTEM_OrderRule.id, TemplateMenuEnum.SYSTEM_OrderRule.href, TemplateMenuEnum.SYSTEM_OrderRule.icon, TemplateMenuEnum.SYSTEM_OrderRule.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}	
		//API
		if(map.get(TemplateMenuEnum.SYSTEM_Api.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.SYSTEM_Api.id, TemplateMenuEnum.SYSTEM_Api.id, TemplateMenuEnum.SYSTEM_Api.href, TemplateMenuEnum.SYSTEM_Api.icon, TemplateMenuEnum.SYSTEM_Api.name);
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}					
		
		
		//功能插件
		if(map.get(TemplateMenuEnum.PLUGIN.id) != null){
			FirstMenu Menu = new FirstMenu("li_"+TemplateMenuEnum.PLUGIN.id, TemplateMenuEnum.PLUGIN.id, TemplateMenuEnum.PLUGIN.href, TemplateMenuEnum.PLUGIN.icon, TemplateMenuEnum.PLUGIN.name);
			
			//将加载的插件拿出来
			if(PluginManage.cmsSiteClassManage.size() > 0){
				for (Map.Entry<String, PluginRegister> entry : PluginManage.cmsSiteClassManage.entrySet()) {
					PluginRegister plugin = entry.getValue();
					Menu.addTwoMenu("dd_"+entry.getKey(), entry.getKey(), "javascript:loadIframeByUrl('"+plugin.menuHref()+"'), notUseTopTools();", plugin.menuTitle());
				}
			}
			
			//将生成的html，加入进来
			sb.append(Menu.gainMenuHTML());
		}
		
		
		return sb.toString();
	}
	
	/**
	 * 判断当前登陆的用户，对于某个 id 是否有管理操作的权力。当然，这个只是明面上能看到的权力，并不是修改的
	 * @param id
	 */
	public static boolean haveRole(String id){
		Map<String, String> map = SessionUtil.getStoreMenuRole();
		if(map.get(id) != null){
			return true;
		}else{
			return false;
		}
	}
}
