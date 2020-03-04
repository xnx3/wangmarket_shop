package com.xnx3.wangmarket.shop.api.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.alipay.bean.PcOrderBean;
import com.xnx3.wangmarket.plugin.alipay.bean.WapOrderBean;
import com.xnx3.wangmarket.plugin.alipay.util.AlipayUtil;
import com.xnx3.wangmarket.shop.api.util.AlipayCacheUtil;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;
import com.xnx3.wangmarket.shop.api.vo.PaySetVO;
import com.xnx3.wangmarket.shop.api.vo.bean.PaySetBean;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.entity.PayLog;
import com.xnx3.wangmarket.shop.core.service.PayService;
import com.xnx3.wangmarket.shop.core.vo.AlipayUtilVO;
import net.sf.json.JSONObject;

/**
 * 给网站管理后台显示的
 * @author 管雷鸣
 */
@Controller(value="ShopApiPayController")
@RequestMapping("/shop/api/pay/")
public class PayController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private PayService payService;
	
	
	/**
	 * 获取当前商铺的支付列表，列出哪个支付使用，哪个支付不使用
	 */
	@ResponseBody
	@RequestMapping(value="getUsableList${api.suffix}", method = RequestMethod.POST)
	public BaseVO getUsableList(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid){
		PaySetVO vo = new PaySetVO();
		
		if(storeid < 1){
			return error("请传入storeid");
		}
		List<Map<String,Object>> list = sqlService.findMapBySqlQuery("SELECT use_alipay,use_private_pay,use_weixin_pay FROM shop_pay_set WHERE id = " + storeid);
		Map<String,Object> map;
		if(list.size() == 0){
			map = new HashMap<String, Object>();
		}else{
			map = list.get(0);
		}
		PaySetBean paySetBean = new PaySetBean();
		paySetBean.setUseAlipay((short) (map.get("use_alipay") != null ? Lang.stringToInt(map.get("use_alipay").toString() , 0):0));
		paySetBean.setUsePrivatePay((short) (map.get("use_private_pay") != null ? Lang.stringToInt(map.get("use_private_pay").toString() , 0):0));
		paySetBean.setUseWeixinPay((short) (map.get("use_weixin_pay") != null ? Lang.stringToInt(map.get("use_weixin_pay").toString() , 0):0));
		vo.setPaySet(paySetBean);
		
		return vo;
	}
	
	/**
	 * 线下付款。此接口为标注订单状态为 线下支付
	 * @author 管雷鸣
	 * @param orderid 订单id
	 */
	@RequestMapping(value="privatePay${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO privatePay(HttpServletRequest request,
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
		//判断订单状态，是否允许变为申请退款
		if(!order.getState().equals(Order.STATE_CREATE_BUT_NO_PAY)) {
			return error("订单状态异常");
		}
		
		//修改订单状态
		order.setState(Order.STATE_PRIVATE_PAY);
		sqlService.save(order);
		
		//写日志
		ActionLogUtil.insertUpdateDatabase(request, orderid, "订单线下支付", "订单id："+order.getId()+"，no:" + order.getNo());
		return success();
	}
	
	/**
	 * 发起支付宝支付请求
	 * @param orderid 要支付的订单的订单号，order.id
	 * @param channel 支付方式，可传入：
	 * 			<ul>
	 * 				<li>alipay_pc:支付宝PC端电脑网页支付</li>
	 * 				<li>alipay_wap:支付宝手机端网页支付</li>
	 * 			</ul>
	 * @return {@link BaseVO} result 返回1 那么 info 返回要跳转到的支付页面url
	 */
	@ResponseBody
	@RequestMapping(value="alipay${api.suffix}", method = RequestMethod.POST)
	public BaseVO alipay(HttpServletRequest request,Model model,
			@RequestParam(value = "orderid", required = false, defaultValue="0") int orderid,
			@RequestParam(value = "channel", required = false, defaultValue="") String channel){
		if(orderid < 1){
			return error("请传入要支付订单的订单号");
		}
		Order order = sqlService.findById(Order.class, orderid);
		if(order == null){
			return error("订单不存在");
		}
		if(order.getUserid() - getUserId() != 0){
			return error("订单不属于你，操作失败");
		}
		if(!order.getState().equals(Order.STATE_CREATE_BUT_NO_PAY)){
			return error("订单状态异常");
		}
		
		/**** 订单校验完毕，可以支付 ****/
		AlipayUtilVO vo = payService.getAlipayUtil(order.getStoreid());
		if(vo.getResult() - AlipayUtilVO.FAILURE == 0){
			//如果当前支付宝支付方式不符合，那么返回错误提示
			return error(vo.getInfo());
		}
		
		if(channel.equalsIgnoreCase("alipay_pc")){
			//支付宝PC端电脑网页支付
			PcOrderBean orderBean = new PcOrderBean(order.getNo(), order.getPayMoney()/100f, "商城支付", "");
			try {
				String form = vo.getAlipayUtil().pcPay(orderBean);
				SessionUtil.setAlipayForm(form);
			} catch (AlipayApiException e) {
				e.printStackTrace();
				return error(e.getMessage());
			}
		}else if(channel.equalsIgnoreCase("alipay_wap")){
			//支付宝手机端网页支付
			WapOrderBean orderBean = new WapOrderBean(order.getNo(), order.getPayMoney()/100f, "商城支付", "", "http://www.leimingyun.com");
			try {
				String form = vo.getAlipayUtil().wapPay(orderBean);
				SessionUtil.setAlipayForm(form);
			} catch (AlipayApiException e) {
				e.printStackTrace();
				return error(e.getMessage());
			}
		}else{
			return error("channel not find");
		}
		
		ActionLogUtil.insert(request, "发起支付请求", order.toString());
		return success(SystemUtil.get("MASTER_SITE_URL")+"shop/api/pay/pay.do?token="+request.getSession().getId());
	}
	
	/**
	 * 请求这个网址就是要进行支付了
	 * 来源页面需要使用 {@link SessionUtil#setAlipayForm(String)} 来设置跳转支付的表单
	 */
	@RequestMapping("pay${url.suffix}")
	public String pay(HttpServletRequest request,Model model){
		String form = SessionUtil.getAlipayForm();
		if(form == null || form.length() == 0){
			form = "please first call pay/alipay.json interface to initiate the payment request.";
		}
		model.addAttribute("form", form);
		
		ActionLogUtil.insert(request, "打开支付的html页面");
		return "plugin/alipay/pay";
	}
	
	/**
	 * 支付成功的回调
	 */
	@ResponseBody
	@RequestMapping("alipayCallback${url.suffix}")
	public String alipayCallback(HttpServletRequest request){
		Map<String, String> paramsMap = AlipayUtil.requestParamsToMap(request);
		ConsoleUtil.info("callback:"+JSONObject.fromObject(paramsMap).toString());
		//获取该订单的订单号
        String out_trade_no = paramsMap.get("out_trade_no");
        if(out_trade_no == null || out_trade_no.length() < 1){
        	return "订单号未发现";
        }
        Order order = sqlService.findAloneByProperty(Order.class, "no", out_trade_no);
        if(order == null){
        	return "订单不存在";
        }
        
		BaseVO vo = payFinish(request, order);
		if(vo.getResult() - BaseVO.SUCCESS == 0){
			//成功
			ActionLogUtil.insert(request, "支付宝支付支付异步回调-处理成功", JSONObject.fromObject(paramsMap).toString());
			return "success";
		}else{
			//失败
			ActionLogUtil.insert(request, "支付宝支付支付异步回调-处理失败："+vo.getInfo(), JSONObject.fromObject(paramsMap).toString());
			return vo.getInfo();
		}
	}
	
	/**
	 * 支付成功的返回跳转页面
	 */
	@RequestMapping("alipaySuccessJumpPage${url.suffix}")
	public String alipaySuccessJumpPage(HttpServletRequest request,Model model){
		Map<String, String> paramsMap = AlipayUtil.requestParamsToMap(request);
		//获取该订单的订单号
        String out_trade_no = paramsMap.get("out_trade_no");
        if(out_trade_no == null || out_trade_no.length() < 1){
        	return error(model, "订单号未发现");
        }
        Order order = sqlService.findAloneByProperty(Order.class, "no", out_trade_no);
        if(order == null){
        	return error(model, "订单不存在");
        }
        
		
		ConsoleUtil.info("payFinishJumpPage");
		BaseVO vo = payFinish(request, order);
		if(vo.getResult() - BaseVO.SUCCESS == 0){
			//成功
			ActionLogUtil.insert(request, "支付宝支付支付同步回调-处理成功", JSONObject.fromObject(paramsMap).toString());
			return success(model, "支付成功!", SystemUtil.get("MASTER_SITE_URL")+"login.do");
		}else{
			//失败
			ActionLogUtil.insert(request, "支付宝支付支付同步回调-处理失败："+vo.getInfo(), JSONObject.fromObject(paramsMap).toString());
			return vo.getInfo();
		}
	}
	
	private BaseVO payFinish(HttpServletRequest request, Order order){
		Map<String, String> paramsMap = AlipayUtil.requestParamsToMap(request);
		ConsoleUtil.info("支付宝回调："+JSONObject.fromObject(paramsMap).toString());
        
		AlipayUtil alipayUtil = AlipayCacheUtil.getAlipayUtil(order.getStoreid());
        //验证签名
        boolean signVerified = alipayUtil.rsa2CertCheck(paramsMap);
        if (signVerified) {
        	ConsoleUtil.info("支付宝回调签名认证成功");
        	String trade_status = paramsMap.get("trade_status");
            
            /*
             * 对结果进行详细数据验证
             * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
             * 4、验证app_id是否为该商户本身。
             */
            // 支付成功
            if (trade_status != null && trade_status.equalsIgnoreCase("TRADE_SUCCESS")){
                // 处理支付成功逻辑
                
            	String total_amount = paramsMap.get("total_amount");
            	DecimalFormat df = new DecimalFormat("#########.##");
            	String moneyFen = df.format(Float.parseFloat(total_amount)*100);
            	int money = Integer.parseInt(moneyFen);	//得到支付金额，单位是分
            	
            	BaseVO vo = paySuccess(request, order, money);
            	return vo;
            } else {
                //其他的状态通知
            }
            // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
            // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
            return success();
        } else {
            return error("支付宝回调签名认证失败");
        }
	}
	
	/**
	 * 支付成功后，进行的逻辑处理
	 * @param out_trade_no 支付宝返回支付成功的订单号
	 * @param money 支付宝返回支付成功的金额，单位是分
	 */
	@Transactional
	private BaseVO paySuccess(HttpServletRequest request, Order order, int money){
		//将订单状态变为支付完成
		order.setState(Order.STATE_PAY);
		sqlService.save(order);
		
		//记录支付信息
		PayLog payLog = new PayLog();
		payLog.setAddtime(DateUtil.timeForUnix10());
		payLog.setChannel(PayLog.CHANNEL_ALIPAY_PC);
		payLog.setMoney(order.getPayMoney());
		payLog.setOrderid(order.getId());
		payLog.setStoreid(order.getStoreid());
		payLog.setUserid(order.getUserid());
		sqlService.save(payLog);
		
		ActionLogUtil.insertUpdateDatabase(request, "支付成功", "payLog:"+payLog.toString());
		return success();
	}
}