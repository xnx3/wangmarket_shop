package com.xnx3.wangmarket.shop.core.pluginManage.interfaces.manage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.xnx3.BaseVO;
import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.core.bean.BuyGoods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 超级管理后台首页的html源码处理
 * @author 管雷鸣
 *
 */
@Component(value="PluginManageForOrderCreate")
public class OrderCreatePluginManage {
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
				List<Class<?>> allClassList = ScanClassUtil.getClasses("com.xnx3.wangmarket");
				classList = ScanClassUtil.searchByInterfaceName(allClassList, "com.xnx3.wangmarket.shop.core.pluginManage.interfaces.OrderCreateInterface");
				for (int i = 0; i < classList.size(); i++) {
					ConsoleUtil.info("装载 OrderCreateInterface 插件："+classList.get(i).getName());
				}
			}
		}).start();
		
	}
	
	/**
	 * 订单创建之前触发
	 * @param order 创建的订单。还未创建，id 还是 null
	 * @param buyGoodsList 此订单内购买的商品列表信息
	 * @param orderAddress 该订单的配送地址信息。其中 id  还是null
	 * @param user 要创建这个订单的用户 user 
	 * @param store 要下单的商品属于哪个店铺
	 * @return {@link BaseVO}
	 */
	public static BaseVO orderCreateBefore(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		/**** 针对html源代码处理的插件 ****/
		for (int i = 0; i < classList.size(); i++) {
			Class<?> c = classList.get(i);
			Object invoke = null;
			invoke = c.newInstance();
			//运用newInstance()来生成这个新获取方法的实例  
			Method m = c.getMethod("orderCreateBefore",new Class[]{Order.class, List.class, OrderAddress.class, User.class, Store.class});	//获取要调用的方法  
			//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
			Object o = m.invoke(invoke, new Object[]{order, buyGoodsList, orderAddress, user, store});
			if(o != null){
				//将其转为 BaseVO
				BaseVO vo = (BaseVO)o;
				if(vo.getResult() - BaseVO.FAILURE == 0){
					return vo;
				}
			}
		}
		
		//只要没返回失败，都认为是成功
		return BaseVO.success("success");
	}
	

	/**
	 * 订单创建之后触发
	 * @param order 创建的订单
	 * @param buyGoodsList 此订单内购买的商品列表信息
	 * @param orderAddress 该订单的配送地址信息
	 * @param user 要创建这个订单的用户 user 
	 * @param store 要下单的商品属于哪个店铺
	 * @return {@link BaseVO}
	 */
	public static void orderCreateAfter(Order order, List<BuyGoods> buyGoodsList, OrderAddress orderAddress, User user, Store store) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		/**** 针对html源代码处理的插件 ****/
		for (int i = 0; i < classList.size(); i++) {
			Class<?> c = classList.get(i);
			Object invoke = null;
			invoke = c.newInstance();
			try {
				//运用newInstance()来生成这个新获取方法的实例  
				Method m = c.getMethod("orderCreateAfter",new Class[]{Order.class, List.class, OrderAddress.class, User.class, Store.class});	//获取要调用的方法  
				//动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
				m.invoke(invoke, new Object[]{order, buyGoodsList, orderAddress, user, store});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
