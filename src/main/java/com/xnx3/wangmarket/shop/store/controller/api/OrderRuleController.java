package com.xnx3.wangmarket.shop.store.controller.api;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.vo.OrderRuleVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 自定义订单规则
 * <p>每个商铺都可以有一套自己的订单状态规则<p>
 * @author 刘鹏
 */
@Controller(value="ShopStoreApiOrderRuleController")
@RequestMapping("/shop/store/api/orderRule/")
public class OrderRuleController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看当前规则
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @return 响应订单状态规则
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "/index.json" ,method = RequestMethod.POST)
	public OrderRuleVO index(HttpServletRequest request) {
		OrderRuleVO vo = new OrderRuleVO();
		//从数据库中获取，同时还能判断如果没有，创建这条记录
		OrderRule orderRule = sqlService.findById(OrderRule.class, getStoreId());
		if(orderRule == null){
			orderRule = new OrderRule();
			orderRule.setId(getStoreId());
			sqlService.save(orderRule);
		}
		
		vo.setOrderRule(orderRule);
		//日志记录
		ActionLogUtil.insert(request, "查看规则");
		return vo;
	}
	
	
	/**
	 * 设置某个订单状态是否启用
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @param name 订单状态，传入的也就是 {@link OrderRule} 的字段，传入有 
	 *             <ul>
	 *                 <li>distribution:是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。value 可传入：1使用，0不使用</li>
	 *                 <li>refund:是否使用退款这个状态，也就是是否允许用户退款。<ul><li>1:使用<li>0:不使用</ul>默认是1使用</li>
	 *                 <li>notPayTimeout:订单如果创建订单了，但未支付，多长时间会自动取消订单，订单状态变为已取消。这里的单位是秒</li>
	 *                 <li>receiveTime:订单的确认收货，超过多久没确认收货，订单自动确认收货。如果用户没有主动点击确认收货，系统是否会在超过多长时间后自动将订单变为已确认收货。注意，这里单位是分钟。如果设置为0，则是不使用系统的自动确认收货。（默认不使用）</li>
	 *                 <li>print:是否启用订单打印功能。订单管理中，当查看订单详情时，是否显示 【打印】 按钮。如果关闭，那订单管理-订单详情中的打印按钮会直接不显示。这里打印的订单是类似于外卖小票，使用 57mm、58mm 热敏小票打印机 进行打印。操作的电脑中需要提前安装好打印机驱动，对接好热敏小票打印机 ，然后将此项开启，再到订单管理中，点击打印按钮打印一个订单看看效果。</li>
	 *             </ul>
	 * @param value 值。0或者1  ，1使用，0不使用
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="save.json" ,method = RequestMethod.POST)
	public BaseVO save(HttpServletRequest request,
		@RequestParam(value = "name", required = true, defaultValue = "distribution") String name,
		@RequestParam(value = "value", required = true, defaultValue = "0") int value) {
		
		OrderRule orderRule = sqlService.findById(OrderRule.class, getStoreId());
		if(orderRule == null){
			orderRule = new OrderRule();
			orderRule.setId(getStoreId());
		}
		if(name.equals("distribution")){
			orderRule.setDistribution((short) value);
		}else if(name.equals("refund")){
			orderRule.setRefund((short) value);
		}else if(name.equals("notPayTimeout")){
			orderRule.setNotPayTimeout(value);
		}else if(name.equals("receiveTime")){
			orderRule.setReceiveTime(value);
		}else if(name.equals("print")){
			orderRule.setPrint((short) value);
		}else{
			ConsoleUtil.debug("异常，name:"+name);
			return error("name异常");
		}
		sqlService.save(orderRule);
		//更新持久缓存
		sqlCacheService.deleteCacheById(OrderRule.class, orderRule.getId());
		
		ActionLogUtil.insert(request, "保存订单规则的值", orderRule.toString());
		
		return success();
	}

}
