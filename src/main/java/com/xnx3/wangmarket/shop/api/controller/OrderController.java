package com.xnx3.wangmarket.shop.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.json.JSONUtil;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.OrderAddress;
import com.xnx3.wangmarket.shop.core.entity.OrderGoods;
import com.xnx3.wangmarket.shop.core.entity.OrderRefund;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.OrderTimeout;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.service.OrderRuleService;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.api.service.CartService;
import com.xnx3.wangmarket.shop.api.service.OrderService;
import com.xnx3.wangmarket.shop.api.util.GoodsUtil;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;
import com.xnx3.wangmarket.shop.api.vo.OrderListVO;
import com.xnx3.wangmarket.shop.api.vo.OrderStateStatisticsVO;
import com.xnx3.wangmarket.shop.api.vo.OrderVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单相关
 * @author 管雷鸣
 */
@Controller(value="ShopOrderController")
@RequestMapping("/shop/api/order/")
public class OrderController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private CartService cartService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderRuleService orderRuleService;
	@Resource
	private PayService payService;
	
	/**
	 * 创建订单
	 * 创建订单后，将会减掉商品库存、同时购物车中，订单创建的商品也会减掉
	 * @param buygoods 购买的商品，必须传入，json格式，如：  [{"goodsid":20,"num":2},{"goodsid":24,"num":1}]
	 * @param remark 订单备注，可不传
	 * @param username 收货人姓名，可不传
	 * @param phone 收货人手机号/电话，可不传
	 * @param longitude 收货人经纬度，可不传
	 * @param latitude 收货人经纬度，可不传
	 * @param address 收货地址，可不传。传入如 山东省潍坊市寒亭区华亚国际酒店E1308室
	 * @return {@link OrderVO},若成功，则可以获取到 order 数据
	 */
	@Transactional
	@RequestMapping(value="create${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public OrderVO create(HttpServletRequest request,
			@RequestParam(value = "buygoods", required = false, defaultValue = "") String buygoods,
			@RequestParam(value = "remark", required = false, defaultValue = "") String remark,
			@RequestParam(value = "username", required = false, defaultValue = "") String addressUsername,
			@RequestParam(value = "phone", required = false, defaultValue = "") String addressPhone,
			@RequestParam(value = "longitude", required = false, defaultValue = "") Double addressLongitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "") Double addressLatitude,
			@RequestParam(value = "address", required = false, defaultValue = "") String addressAddress
			){
		OrderVO vo = new OrderVO();
		
		if(buygoods.length() < 2){
			vo.setBaseVO(OrderVO.FAILURE, "请传入要购买的商品信息");
			return vo;
		}
		
		//将传入的要购买的商品列表，转化为list形式,这一步主要是用来验证购买商品列表数据完整性
		List<BuyGoods> buyGoodsList = new ArrayList<BuyGoods>();
		JSONArray goodsArray = JSONArray.fromObject(buygoods);
		if(goodsArray.size() == 0){
			vo.setBaseVO(OrderVO.FAILURE, "请传入要购买的商品信息");
			return vo;
		}
		
		for (int i = 0; i < goodsArray.size(); i++) {
			JSONObject json = goodsArray.getJSONObject(i);
			BuyGoods buyGoods = new BuyGoods();
			buyGoods.setGoodsid(JSONUtil.getInt(json, "goodsid"));
			buyGoods.setNum(JSONUtil.getInt(json, "num"));
			if(buyGoods.getGoodsid() == 0 || buyGoods.getNum() == 0){
				vo.setBaseVO(OrderVO.FAILURE, "goodsid为0或num为0，数据异常！接收到的数据："+buygoods);
				return vo;
			}
			buyGoodsList.add(buyGoods);
		}
		int storeid = 0;	//商家id
		//这个是从数据库中查出来的最新的的商品列表信息，也就是当前订单下单的商品列表
		Map<Integer, Goods> goodsMap = new HashMap<Integer, Goods>();
		//从数据表中，取出当前的商品信息
		for (int i = 0; i < buyGoodsList.size(); i++) {
			BuyGoods buyGoods = buyGoodsList.get(i);	//购物车中要购买的商品
			Goods goods = sqlService.findById(Goods.class, buyGoods.getGoodsid());
			if(goods == null){
				vo.setBaseVO(OrderVO.FAILURE, "要购买的商品不存在");
				return vo;
			}
			if(storeid > 0 && storeid - goods.getStoreid() != 0){
				vo.setBaseVO(OrderVO.FAILURE, "要购买的商品不在同一个店铺！");
				return vo;
			}
			//将商品的storeid拿出来，作为创建订单的店铺关联
			storeid = goods.getStoreid();
			
			//判断当前商品现在是否是上架状态
			if(!GoodsUtil.isPutaway(goods)){
				vo.setBaseVO(OrderVO.FAILURE, "当前商品未上架，不可购买");
				return vo;
			}
			//判断当前商品库存是否够，是否可购买
			if(goods.getInventory() < buyGoods.getNum()){
				//库存比用户购物车中的还少，那用户肯定是不能买的，库存不足
				vo.setBaseVO(OrderVO.FAILURE, "商品"+goods.getTitle()+"当前库存不足，不可购买");
				return vo;
			}
			buyGoodsList.get(i).setGoods(goods);
			//加入 goodsMap
			goodsMap.put(goods.getId(), goods);
		}
		//查询店铺信息,看是否正常
		Store store = sqlService.findById(Store.class, storeid);
		if(store.getState() - Store.STATE_OPEN != 0){
			//店铺状态不正常，不是正常的营业中，判断一下
			if(store.getState() - Store.STATE_CLOSE == 0){
				vo.setBaseVO(OrderVO.FAILURE, "当前店铺已打烊关店！不可下单");
				return vo;
			}
			
			vo.setBaseVO(OrderVO.FAILURE, "当前店铺状态已不是正常营业，无法下单");
			return vo;
		}
		
		//计算出当前要购买商品的总金额
		int allMoney = 0;
		int allNumber = 0;	//计算出当前购买商品的总数量
		for (int i = 0; i < buyGoodsList.size(); i++) {
			BuyGoods buyGoods = buyGoodsList.get(i);	//购物车中要购买的某个商品
			allMoney = allMoney + buyGoods.getGoods().getPrice() * buyGoods.getNum();
			allNumber = allNumber + buyGoods.getNum();
		}
		
		//当前登录的用户
		User user = SessionUtil.getUser();
		
		/**** 创建订单 ****/
		Order order = new Order();
		order.setAddtime(DateUtil.timeForUnix10());
		order.setPayMoney(allMoney);
//		order.setPayMoney(1);//测试使用，0.01支付
		order.setRemark(StringUtil.filterXss(remark));
		order.setState(Order.STATE_CREATE_BUT_NO_PAY);
		order.setStoreid(storeid);
		order.setTotalMoney(allMoney);
		order.setUserid(user.getId());
		String no = (StringUtil.intTo36(DateUtil.timeForUnix10())+StringUtil.getRandom09AZ(4)).toUpperCase() ; //小写字母变大写
		order.setNo(no);	//10位
		order.setVersion(0);
		sqlService.save(order);
		if(order.getId() == null || order.getId() == 0){
			vo.setBaseVO(OrderVO.FAILURE, "订单创建失败！");
			return vo;
		}
		
		/***** 创建订单-商品的关联，这个订单下有哪些商品 *****/
		for (int i = 0; i < buyGoodsList.size(); i++) {
			BuyGoods buyGoods = buyGoodsList.get(i);	//购物车中要购买的商品
			
			Goods goods = goodsMap.get(buyGoods.getGoods().getId());
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setGoodsid(goods.getId());
			orderGoods.setPrice(goods.getPrice());
			orderGoods.setTitle(goods.getTitle());
			orderGoods.setTitlepic(goods.getTitlepic());
			orderGoods.setUnits(goods.getUnits());
			orderGoods.setNumber(buyGoods.getNum());
			orderGoods.setOrderid(order.getId());
			orderGoods.setUserid(user.getId());
			sqlService.save(orderGoods);
			
			//减库存，如果用户后面不支付，或者订单退单，那么库存还会再加回来
			goods.setInventory(goods.getInventory() - buyGoods.getNum());
			sqlService.save(goods);
		}
		
		//创建订单对应的地址信息
		OrderAddress orderAddress = new OrderAddress();
		orderAddress.setId(order.getId());
		orderAddress.setLatitude(addressLatitude);
		orderAddress.setLongitude(addressLongitude);
		orderAddress.setPhone(StringUtil.filterXss(addressPhone));
		orderAddress.setAddress(StringUtil.filterXss(addressAddress));
		orderAddress.setUsername(StringUtil.filterXss(addressUsername));
		sqlService.save(orderAddress);
		
		//创建订单未支付超时监控
		OrderTimeout orderTimeout = new OrderTimeout();
		orderTimeout.setId(order.getId());
		orderTimeout.setState(order.getState());
		//先统一设定半小时后未支付，就自动取消
		orderTimeout.setExpiretime(DateUtil.timeForUnix10()+(30*60));
		sqlService.save(orderTimeout);
		
		//该店铺的已售数量增加，因为店铺下可能会同一时刻产生多个订单，version 乐观锁容易造成下单异常，阻挡正常下单，所以直接进行update 销售数量
		sqlService.executeSql("UPDATE shop_store SET sale = sale + " + allNumber + " WHERE id = "+store.getId());
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request, order.getId(), "创建订单", "no:"+order.getNo());
		
		//购物车中，将这些下单的商品去掉
		for (int i = 0; i < buyGoodsList.size(); i++) {
			Goods goods = buyGoodsList.get(i).getGoods();
			cartService.change(goods.getId(), -99999);
		}
		
		vo.setOrder(order);
		return vo;
	}
	
	
	
	/**
	 * 我的订单列表
	 * @param state 搜索的订单的状态，多个用,分割 传入如 generate_but_no_pay,pay_timeout_cancel
	 * @param everyNumber 每页显示多少条数据。取值 1～100，最大显示100条数据，若传入超过100，则只会返回100条
	 * @param currentPage 要查看第几页，如要查看第2页，则这里传入 2
	 * @author 管雷鸣
	 */
	@RequestMapping(value="list${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public OrderListVO list(HttpServletRequest request,
			@RequestParam(value = "state", required = false, defaultValue = "") String state,
			@RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber) {
		OrderListVO vo = new OrderListVO();
		if(everyNumber > 100){
			everyNumber = 100;
		}
		
		//判断出要取的订单状态
		StringBuffer stateSB = new StringBuffer();
		if(state.length() > 0){
			String[] states = state.split(",");
			for (int i = 0; i < states.length; i++) {
				if(states[i].length() > 0){
					if(stateSB.length() > 1){
						stateSB.append(" OR");
					}
					stateSB.append(" shop_order.state = '"+states[i]+"'");
				}
			}
		}
		String stateSql = stateSB.toString();	//此字符串的格式如 state = 'generate_but_no_pay' OR state = 'pay_timeout_cancel'
		
		//当前登录的用户
		User user = SessionUtil.getUser();
		
		Sql sql = new Sql(request);
	    /*
	     * 设置可搜索字段。这里填写的跟user表的字段名对应。只有这里配置了的字段，才会有效。这里没有配置，则不会进行筛选
	     * 具体规则可参考： http://note.youdao.com/noteshare?id=3ccef2de6a5cda01f95f832b02e356d0&sub=D53E681BBFF04822977C7CFBF8827863
	     */
//	    sql.setSearchColumn(new String[]{"state="});
	    sql.appendWhere("shop_order.userid = "+user.getId() + (stateSql.length() > 1 ? " AND ("+stateSql+")":""));
	    //查询user数据表的记录总条数。 传入的user：数据表的名字为user
	    int count = sqlService.count("shop_order", sql.getWhere());
	    //创建分页，并设定每页显示15条
	    Page page = new Page(count, everyNumber, request);
	    //创建查询语句，只有SELECT、FROM，原生sql查询。其他的where、limit等会自动拼接
	    sql.setSelectFromAndPage("SELECT * FROM shop_order", page);
	    sql.setDefaultOrderBy("shop_order.id DESC");
	    //因只查询的一个表，所以可以将查询结果转化为实体类，用List接收。
	    List<Order> list = sqlService.findBySql(sql, Order.class);
	    vo.setOrderList(list);
	    vo.setPage(page);
	    
		//写日志
		ActionLogUtil.insert(request, getUserId(), "查看我的订单列表");
		return vo;
	}
	

	/**
	 *查看订单详情
	 * @author 管雷鸣
	 * @param orderid 订单id，order.id，要查看哪个订单的信息
	 */
	@RequestMapping(value="detail${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public OrderVO detail(HttpServletRequest request,
			@RequestParam(value = "orderid", required = false, defaultValue = "0") int orderid){
		OrderVO vo = new OrderVO();
		
		//判断参数
		if(orderid < 1) {
			vo.setBaseVO(OrderVO.FAILURE, "请传入订单id");
			return vo;
		}
		
		//当前用户信息
		User user = getUser();
		vo.setUser(user);
		
		//查找订单信息
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null) {
			vo.setBaseVO(OrderVO.FAILURE, "根据订单ID，未查到订单信息！");
			return vo;
		}
		if(order.getUserid() - getUserId() != 0) {
			vo.setBaseVO(OrderVO.FAILURE, "订单不属于你，无权查看！");
			return vo;
		}
		vo.setOrder(order);
		
		//查找订单内商品信息
		String sql = "SELECT * FROM shop_order_goods WHERE orderid = " + orderid;
		List<OrderGoods> list = sqlService.findBySqlQuery(sql, OrderGoods.class);
		vo.setGoodsList(list);
		
		//查找订单的收货地址信息
		OrderAddress orderAddress = sqlService.findById(OrderAddress.class, orderid);
		if(orderAddress == null){
			//为了避免之前版本的订单没有address，兼容之前的
			orderAddress = new OrderAddress();
		}
		vo.setOrderAddress(orderAddress);
		
		//查出订单所属的商家
		Store store = sqlService.findById(Store.class, order.getStoreid());
		vo.setStore(store);
		
		//加入商家支付设置
		vo.setPaySet(payService.getPaySet(order.getStoreid()));
		
		//写日志
		ActionLogUtil.insert(request, orderid, "查看订单详情", "id:"+orderid+", no:"+order.getNo());
		return vo;
	}
	
	/**
	 * 申请退款
	 * @author 管雷鸣
	 * @param orderid 订单id
	 * @param reason 退款理由，非必填，如果想作为必填项，可以在客户端进行必填的判断
	 */
	@RequestMapping(value="refund${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO refund(HttpServletRequest request,
			@RequestParam(value = "orderid", required = false, defaultValue = "0") int orderid,
			@RequestParam(value = "reason", required = false, defaultValue = "") String reason){
		//判断参数
		if(orderid < 1) {
			return error("请传入订单ID");
		}
		
		//查找订单信息
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null) {
			return error("订单不存在");
		}
		if(order.getUserid() - getUserId() != 0) {
			return error("订单不属于你，无权操作");
		}
		//判断订单状态，是否允许变为申请退款， 已支付、线下支付 这两种状态允许申请退款
		if(!(order.getState().equals(Order.STATE_PAY) || order.getState().equals(Order.STATE_PRIVATE_PAY))) {
			return error("订单状态异常");
		}
		
		//判断当前商家是否开启了订单允许退款功能
		OrderRule orderRule = orderRuleService.getRole(order.getStoreid());
		if(orderRule.getRefund() - OrderRule.OFF == 0){
			return error("商家已设置不允许用户提出退款申请");
		}
		
		//修改订单状态
		order.setState(Order.STATE_CANCELMONEY_ING);
		sqlService.save(order);
		
		//创建退款记录
		OrderRefund log = new OrderRefund();
		log.setAddtime(DateUtil.timeForUnix10());
		log.setOrderid(orderid);
		log.setReason(StringUtil.filterXss(reason));
		log.setState(OrderRefund.STATE_ING);
		log.setStoreid(order.getStoreid());
		log.setUserid(getUserId());
		sqlService.save(log);
		
		//商品的数量改动
		BaseVO vo = orderService.orderCancelGoodsNumberChange(order);
		if(vo.getResult() - BaseVO.FAILURE == 0){
			return vo;
		}
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request, orderid, "订单申请退款", "订单id："+order.getId()+"，no:" + order.getNo() + "， 退单理由：" + log.getReason());
	
		return success();
	}
	
	/**
	 * 收到商品，确认收货
	 * @author 管雷鸣
	 * @param id 订单id
	 */
	@RequestMapping(value="receiveGoods${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public OrderVO receiveGoods(HttpServletRequest request,
			@RequestParam(value = "orderid", required = false, defaultValue = "0") int orderid){
		OrderVO vo = new OrderVO();
		
		//判断参数
		if(orderid < 1) {
			vo.setBaseVO(OrderVO.FAILURE, "请传入ID");
			return vo;
		}
		
		//查找订单信息
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null) {
			vo.setBaseVO(OrderVO.FAILURE, "订单不存在");
			return vo;
		}
		if(order.getUserid() - getUserId() != 0) {
			vo.setBaseVO(OrderVO.FAILURE, "订单不属于你，无权查看！");
			return vo;
		}
		//判断订单状态是否允许变为已确认收货。 已支付、线下支付、配送中 这两种状态可以变为确认收货
		if(order.getState().equals(Order.STATE_PAY) || order.getState().equals(Order.STATE_PRIVATE_PAY) || order.getState().equals(Order.STATE_DISTRIBUTION_ING)){
			//正常，符合状态改变
		}else{
			//异常
			vo.setBaseVO(OrderVO.FAILURE, "订单状态异常");
			return vo;
		}
		
		order.setState(Order.STATE_RECEIVE_GOODS);
		sqlService.save(order);
		
		//写日志
		ActionLogUtil.insert(request, orderid, "订单确认收货", "订单:" + order.toString());
		return vo;
	}
	
	/**
	 * 取消订单。当订单未支付时，可以取消订单
	 * @author 关光礼
	 * @param id 订单id
	 */
	@RequestMapping(value="cancel${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO cancel(HttpServletRequest request,
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
		if(order.getUserid() - getUserId() != 0) {
			return error("订单不属于你，无权操作");
		}
		
		if(!order.getState().equals(Order.STATE_CREATE_BUT_NO_PAY)) {
			return error("订单状态异常，取消失败");
		}
		
		//修改订单状态
		order.setState(Order.STATE_MY_CANCEL);
		sqlService.save(order);
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request, orderid,"取消订单", order.toString());
		
		return success();
	}
	

	/**
	 * 订单状态统计，统计当前登录用户在某个店铺下，各个状态的订单分别有多少
	 * @param storeid 要统计的所在店铺id
	 * @author 管雷鸣
	 */
	@RequestMapping(value="statistics${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public OrderStateStatisticsVO statistics(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid){
		OrderStateStatisticsVO vo = new OrderStateStatisticsVO();
		//判断参数
		if(storeid < 1) {
			vo.setBaseVO(BaseVO.FAILURE, "请传入店铺ID");
			return vo;
		}
		String sql = "SELECT state,count(*) AS number FROM shop_order WHERE storeid = "+storeid+" AND userid = "+getUserId()+" GROUP BY state";
		List<Map<String, Object>> list = sqlService.findMapBySqlQuery(sql);
		
		/*
		 * 将 list 遍历出来，赋予这个map
		 * key: state
		 * value: 这个状态下有几个订单
		 */
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			String state = (String) m.get("state");
			int number = Lang.stringToInt(m.get("number").toString(), 0);
			map.put(state, number);
		}
		vo.setMap(map);
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request,"统计用户在某个店铺下，各个状态的订单数", "userid:"+getUserId()+", storeid:"+storeid);
		return vo;
	}
	
}

/**
 * 创建订单，购买的商品
 * @author 管雷鸣
 *
 */
class BuyGoods {
	
	private int goodsid;	//购买的是那个商品，对应Goods.id
	private int num;		//购买商品的数量
	private Goods goods;	//购买的商品信息，这个是直接从数据表取的
	
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}