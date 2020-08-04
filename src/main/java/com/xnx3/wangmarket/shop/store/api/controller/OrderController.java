package com.xnx3.wangmarket.shop.store.api.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderStateLog;
import com.xnx3.wangmarket.shop.core.service.OrderService;
import com.xnx3.wangmarket.shop.core.service.OrderStateLogService;
import com.xnx3.wangmarket.shop.store.api.vo.OrderListVO;
import com.xnx3.wangmarket.shop.store.api.vo.OrderVO;


/**
 * 订单管理
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiOrderController")
@RequestMapping("/shop/store/api/order")
public class OrderController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderStateLogService orderStateLogService;
	
	/**
	 * 查看订单列表
	 * @author 关光礼
	 */
	@ResponseBody
	@RequestMapping(value="/list${url.suffix}", method = {RequestMethod.POST})
	public OrderListVO list(HttpServletRequest request,Model model) {
		OrderListVO vo = new OrderListVO();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_order");
		//查询条件
		sql.appendWhere("storeid = " + getStoreId());
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
		
		vo.setOrderList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看订单列表");
		return vo;
	}
	
	/**
	 * 查看订单详情
	 * @author 管雷鸣
	 * @param id 订单id，order.id
	 */
	@ResponseBody
	@RequestMapping(value="detail${url.suffix}", method = {RequestMethod.POST})
	public OrderVO detail(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		OrderVO vo = new OrderVO();
		
		if(id < 1){
			vo.setBaseVO(BaseVO.FAILURE, "请传入订单号");
			return vo;
		}
		//查到订单信息
		Order order = sqlService.findById(Order.class, id);
		if(order == null){
			vo.setBaseVO(BaseVO.FAILURE, "订单不存在");
			return vo;
		}
		if(order.getStoreid() - getStoreId() != 0){
			vo.setBaseVO(BaseVO.FAILURE, "订单不属于你，无法查看");
			return vo;
		}
		vo.setOrder(order);
		
		//查到配送信息
		OrderAddress address = sqlService.findById(OrderAddress.class, id);
		vo.setOrderAddress(address);
		
		//订单内商品信息
		List<OrderGoods> goodsList = sqlService.findByProperty(OrderGoods.class, "orderid", id);
		vo.setGoodsList(goodsList);
		ActionLogUtil.insert(request, order.getId(), "查看订单详情:"+vo.toString());
		
		return vo;
	}
	

	/**
	 * 收到商品，确认收货
	 * @author 管雷鸣
	 * @param id 订单id
	 */
	@RequestMapping(value="receiveGoods${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO receiveGoods(HttpServletRequest request,
			@RequestParam(value = "orderid", required = false, defaultValue = "0") int orderid){
		//判断参数
		if(orderid < 1) {
			return error("请传入订单id");
		}
		
		//查找订单信息
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null) {
			return error("订单不存在");
		}
		if(order.getStoreid() - getStoreId() != 0) {
			return error("订单不属于你的店铺，无权操作！");
		}
		
		com.xnx3.wangmarket.shop.core.vo.OrderVO vo = orderService.receiveGoods(order);
		if(vo.getResult() - OrderVO.SUCCESS == 0){
			//写日志
			ActionLogUtil.insertUpdateDatabase(request, order.getId(), "订单确认收货成功", "在商家后台确认收货。订单:" + order.toString());
			return success();
		}else{
			ActionLogUtil.insertUpdateDatabase(request, order.getId(), "订单确认收货失败", vo.getInfo()+",订单:" + order.toString());
			return error(vo.getInfo());
		}
	}
	
	/**
	 * 退单申请,拒绝
	 * @param orderid 要拒绝退单申请的订单id
	 */
	@Transactional
	@RequestMapping(value="refundReject${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO refundReject(HttpServletRequest request,
			@RequestParam(value = "orderid", required = false, defaultValue = "0") int orderid){
		//判断参数
		if(orderid < 1) {
			return error("请传入订单ID");
		}
		
		//查找订单信息
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null) {
			return error("订单不存在");
		}
		if(order.getStoreid() - getStoreId() != 0) {
			return error("订单不属于你的店铺，无权操作");
		}
		//判断订单状态，是否允许变为拒绝退款，只有申请退单中的状态才可以拒绝退款
		if(!(order.getState().equals(Order.STATE_CANCELMONEY_ING))) {
			return error("订单状态异常");
		}
		
//		//判断当前商家是否开启了订单允许退款功能，这里就不判断了，因为是商家来进行操作的，商家就是大爷，他说了算
//		OrderRule orderRule = orderRuleService.getRole(order.getStoreid());
//		if(orderRule.getRefund() - OrderRule.OFF == 0){
//			return error("商家已设置不允许用户提出退款申请，所以你取消退款也是取消不了的");
//		}
		
		//查出这个订单申请退单之前是哪个状态
		List<OrderStateLog> stateLogList = orderStateLogService.getLogList(order.getId());
		String befureState = "";	//订单申请退单之前的状态
		for (int i = 0; i < stateLogList.size(); i++) {
			OrderStateLog log = stateLogList.get(i);
			if(log.getState().equals(order.getState())){
				//当前状态能跟日志的对起来，那么去下一条状态日志，就是退单之前的状态了
				befureState = stateLogList.get(i+1).getState();
				break;	//退出for循环
			}
		}
		
		//修改订单状态
		order.setState(befureState);
		sqlService.save(order);
		
		//订单状态改变记录
		OrderStateLog stateLog = new OrderStateLog();
		stateLog.setAddtime(DateUtil.timeForUnix10());
		stateLog.setState(order.getState());
		stateLog.setOrderid(order.getId());
		sqlService.save(stateLog);
		
		//商品的数量改动
		BaseVO vo = orderService.orderCancelGoodsNumberChange(order);
		if(vo.getResult() - BaseVO.FAILURE == 0){
			return vo;
		}
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request, orderid, "订单退单申请被商家拒绝", order.toString());
	
		return success();
	}
}
