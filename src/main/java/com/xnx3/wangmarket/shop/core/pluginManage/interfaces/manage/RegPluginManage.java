package com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.stereotype.Component;
import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 超级管理后台首页的html源码处理
 * @author 管雷鸣
 *
 */
@Component(value="PluginManageForReg")
public class RegPluginManage {
	//开启项目时，便将有关此的插件加入此处
	public static List<Class<?>> classList;
	static{
		List<Class<?>> allClassList = ScanClassUtil.getClasses("com.xnx3.wangmarket");
		classList = ScanClassUtil.searchByInterfaceName(allClassList, "com.xnx3.wangmarket.shop.core.pluginManage.interfaces.RegInterface");
		for (int i = 0; i < classList.size(); i++) {
			ConsoleUtil.info("装载 RegInterface 插件："+classList.get(i).getName());
		}
	}
	
	/**
	 * 注册成功，注册完后要执行的
	 * @param user 注册成功的 {@link User}
	 * @param store 注册用户是注册的哪个 {@link Store} 商城的
	 */
	public static void regFinish(User user, Store store) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		/**** 针对html源代码处理的插件 ****/
		for (int i = 0; i < classList.size(); i++) {
			Class<?> c = classList.get(i);
			Object invoke = null;
			invoke = c.newInstance();
			//运用newInstance()来生成这个新获取方法的实例  
			Method m = c.getMethod("regFinish",new Class[]{User.class, Store.class});	//获取要调用的方法  
			//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
			m.invoke(invoke, new Object[]{user, store});
		}
	}
	
}
