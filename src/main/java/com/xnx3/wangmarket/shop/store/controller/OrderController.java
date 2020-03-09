package com.xnx3.wangmarket.shop.store.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;


/**
 * 订单管理控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreOrderController")
@RequestMapping("/shop/store/order")
public class OrderController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查看订单列表
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_order");
		//查询条件
		sql.appendWhere("storeid = " + getStoreId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"no","state"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_order", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_order ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<Order> list = sqlService.findBySql(sql,Order.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看订单列表");
		return "/shop/store/order/list";
	}
	
	
	/**
	 * 查看订单详情
	 * @author 关光礼
	 * @param id 订单id
	 */
	@RequestMapping("orderDetails${url.suffix}")
	public String orderDetails(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		if(id > 0) {
			//查到订单信息
			Order order = sqlService.findById(Order.class, id);
			if(order == null){
				return error(model, "订单不存在");
			}
			if(order.getStoreid() - getStoreId() != 0){
				return error(model, "订单不属于你，无法查看");
			}
			
			//查到配送信息
			OrderAddress address = sqlService.findById(OrderAddress.class, id);
			
			//订单内商品信息
			List<OrderGoods> goodsList = sqlService.findByProperty(OrderGoods.class, "orderid", id);
			
			model.addAttribute("order",order);
			model.addAttribute("address",address);
			model.addAttribute("goodsList",goodsList);
			ActionLogUtil.insert(request, getUserId(), "查看订单ID为" + id+ "的详情");
		}else {
			return error(model,"请传入订单ID");
		}
		
		return "/shop/store/order/orderDetails";
		
	}
	

}