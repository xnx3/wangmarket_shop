package com.xnx3.wangmarket.shop.superadmin.controller;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.OrderService;
import com.xnx3.wangmarket.shop.core.service.OrderStateLogService;
import com.xnx3.wangmarket.shop.core.vo.OrderVO;
import com.xnx3.wangmarket.shop.store.vo.OrderListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单管理
 * @author 刘鹏
 */
@Controller(value="ShopSuperAdminOrderController")
@RequestMapping("/shop/superadmin/order/")
public class OrderController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderStateLogService orderStateLogService;

	/**
	 * 查看订单列表
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="list.json", method = {RequestMethod.POST})
	public OrderListVO list(HttpServletRequest request,Model model) {
		OrderListVO vo = new OrderListVO();
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_order");

		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"no","state="});
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

		// 将信息保存到vo中
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看订单列表");
		return vo;
	}

	/**
	 * 查看订单详情
	 * @author 刘鹏
	 * @param id 订单id
	 */
	@ResponseBody
	@RequestMapping(value="orderDetails.json", method = {RequestMethod.POST})
	public OrderVO orderDetails(Model model ,HttpServletRequest request,
							   @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		OrderVO vo = new OrderVO();
		if(id > 0) {
			//查到订单信息
			Order order = sqlService.findById(Order.class, id);
		
			//查到配送信息
			OrderAddress address = sqlService.findById(OrderAddress.class, id);

			//订单内商品信息
			List<OrderGoods> goodsList = sqlService.findByProperty(OrderGoods.class, "orderid", id);

			vo.setOrder(order);
			vo.setOrderAddress(address);
			vo.setGoodsList(goodsList);
			ActionLogUtil.insert(request, getUserId(), "查看订单ID为" + id+ "的详情");

		}
		return vo;
	}
}
