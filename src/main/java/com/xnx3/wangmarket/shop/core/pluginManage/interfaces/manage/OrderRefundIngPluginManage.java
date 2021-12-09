package com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 订单有用户发起退货申请时触发
 * @author 管雷鸣
 */
@Component(value="PluginManageForOrderRefundIng")
public class OrderRefundIngPluginManage {
	//开启项目时，便将有关此的插件加入此处
	public static List<Class<?>> classList;
	static{
		classList = new ArrayList<Class<?>>();
		
		new Thread(new Runnable() {
			public void run() {
				while(SpringUtil.getApplicationContext() == null){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//当 SpringUtil 被Spring 加载后才会执行
				List<Class<?>> allClassList = ScanClassUtil.getClasses("com.xnx3");
				classList = ScanClassUtil.searchByInterfaceName(allClassList, "com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderRefundIngInterface");
				for (int i = 0; i < classList.size(); i++) {
					ConsoleUtil.info("装载 OrderRefundIngInterface 插件："+classList.get(i).getName());
				}
			}
		}).start();
		
	}
	

	/**
	 * 订单有用户发起退货申请时触发
	 * @param order 创建的订单
	 */
	public static void orderRefundIng(Order order) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		for (int i = 0; i < classList.size(); i++) {
			try {
				Class<?> c = classList.get(i);
				Object invoke = null;
				invoke = c.newInstance();
				//运用newInstance()来生成这个新获取方法的实例  
				Method m = c.getMethod("orderRefundIng",new Class[]{Order.class});	//获取要调用的方法  
				//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
				m.invoke(invoke, new Object[]{order});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
