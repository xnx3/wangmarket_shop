package com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.core.entity.Order;

/**
 * 文章保存时，针对news、news_date 的预处理插件
 * @author 管雷鸣
 *
 */
@Component(value="PluginManageForOrderReceiveGoods")
public class OrderReceiveGoodsPluginManage {
	//自动回复的插件，这里开启项目时，便将有关此的插件加入此处
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
				List<Class<?>> cl = ScanClassUtil.getClasses("com.xnx3.wangmarket");
				classList = ScanClassUtil.searchByInterfaceName(cl, "com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderReceiveGoodsInterface");
				
				for (int i = 0; i < classList.size(); i++) {
					ConsoleUtil.info("装载 Order Receive 插件："+classList.get(i).getName());
				}
			}
		}).start();
		
	}
	
	/**
	 * 当订单确认收货后，触发执行此
	 * @param order 确认收货的订单
	 */
	public static void orderReceiveGoodsFinish(Order order) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		for (int i = 0; i < classList.size(); i++) {
			Class<?> c = classList.get(i);
			Object invokeReply = c.newInstance();
			//运用newInstance()来生成这个新获取方法的实例  
			Method m = c.getMethod("orderReceiveGoodsFinish",new Class[]{Order.class});	//获取要调用的init方法  
			//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
			m.invoke(invokeReply, new Object[]{order});
		}
	}
	

}
