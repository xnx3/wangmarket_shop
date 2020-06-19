package com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.stereotype.Component;
import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 订单支付完成之后触发
 * @author 管雷鸣
 */
@Component(value="PluginManageForOrderPayFinish")
public class OrderPayFinishPluginManage {
	//开启项目时，便将有关此的插件加入此处
	public static List<Class<?>> classList;
	static{
		List<Class<?>> allClassList = ScanClassUtil.getClasses("com.xnx3.wangmarket");
		classList = ScanClassUtil.searchByInterfaceName(allClassList, "com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderPayFinishInterface");
		for (int i = 0; i < classList.size(); i++) {
			ConsoleUtil.info("装载 OrderPayFinishInterface 插件："+classList.get(i).getName());
		}
	}
	
	

	/**
	 * 订单创建完毕之后，接口业务逻辑等都已经执行完了，触发
	 * @param order 创建的订单
	 */
	public static void orderPayFinish(Order order) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		/**** 针对html源代码处理的插件 ****/
		for (int i = 0; i < classList.size(); i++) {
			Class<?> c = classList.get(i);
			Object invoke = null;
			invoke = c.newInstance();
			//运用newInstance()来生成这个新获取方法的实例  
			Method m = c.getMethod("orderPayFinish",new Class[]{Order.class});	//获取要调用的方法  
			//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
			m.invoke(invoke, new Object[]{order});
		}
	}
	
}
