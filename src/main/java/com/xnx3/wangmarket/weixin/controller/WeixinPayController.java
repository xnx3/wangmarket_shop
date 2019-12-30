package com.xnx3.wangmarket.weixin.controller;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.MD5Util;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.controller.BaseController;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.net.MailUtil;
import com.xnx3.wangmarket.weixin.Global;

/**
 * 微信支付相关
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/wxpay/")
public class WeixinPayController extends BaseController {
	@Resource
	private SqlService sqlService;
//	@Resource
//	private CDKeyService cdkeyService;
//	@Resource
//	private CommissionService commissionService;
	
	//统一下单.商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付。
	public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 创建订单
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping(value="createOrder${url.suffix}", method = RequestMethod.POST)
//	@ResponseBody
//    public JSONObject createOrder(HttpServletRequest request,HttpServletResponse response) {
//		//接受参数(openid)
//        String openid = request.getParameter("openid");
//		
//		ConsoleUtil.debug("微信 统一下单 接口调用");
//        //设置最终返回对象
//        JSONObject resultJson = new JSONObject();
//    
//        //接受参数(金额)
//        String amount = "200";	//支付金额，单位是元
//        
//        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
//        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
//        //String amountFen = Float.valueOf((Float.parseFloat(amount)*100)).toString();
//        String amountFen = "1";
//        //创建hashmap(用户获得签名)
//        SortedMap<String, String> paraMap = new TreeMap<String, String>();
//        //设置body变量 (支付成功显示在微信支付 商品详情中)
//        String body = "大潍坊脸卡";
//        //设置随机字符串
//        String nonceStr = Lang.uuid();
//        //设置商户订单号
//        String outTradeNo = StringUtil.getRandom09AZ(2)+StringUtil.intTo36(DateUtil.timeForUnix10())+StringUtil.getRandom09AZ(2);
//        
//        
//        //设置请求参数(小程序ID)
//        paraMap.put("appid", SystemUtil.get("WEIXIN_APPLET_APPID"));
//        //设置请求参数(商户号)
//        paraMap.put("mch_id", SystemUtil.get("WEIXIN_SHANGHU_MCHID"));
//        //设置请求参数(随机字符串)
//        paraMap.put("nonce_str", nonceStr);
//        //设置请求参数(商品描述)
//        paraMap.put("body", body);
//        //设置请求参数(商户订单号)
//        paraMap.put("out_trade_no", outTradeNo);
//        //设置请求参数(总金额)
//        paraMap.put("total_fee", amountFen);
//        //设置请求参数(终端IP)
//        paraMap.put("spbill_create_ip", IpUtil.getIpAddress(request));
//        //设置请求参数(通知地址)
//        paraMap.put("notify_url", "http://xxxx/wxpay/payCallback.do");
//        //设置请求参数(交易类型)
//        paraMap.put("trade_type", "JSAPI");
//        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
//        paraMap.put("openid", openid);
//        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
//        String stringA = formatUrlMap(paraMap, false, false);
//        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
//        String sign = MD5Util.MD5(stringA+"&key="+SystemUtil.get("WEIXIN_SHANGHU_KEY")).toUpperCase();
//        //将参数 编写XML格式
//        StringBuffer paramBuffer = new StringBuffer();
//        paramBuffer.append("<xml>");
//        paramBuffer.append("<appid>"+SystemUtil.get("WEIXIN_APPLET_APPID")+"</appid>");
//        paramBuffer.append("<mch_id>"+SystemUtil.get("WEIXIN_SHANGHU_MCHID")+"</mch_id>");
//        paramBuffer.append("<nonce_str>"+paraMap.get("nonce_str")+"</nonce_str>");
//        paramBuffer.append("<sign>"+sign+"</sign>");
//        paramBuffer.append("<body>"+body+"</body>");
//        paramBuffer.append("<out_trade_no>"+paraMap.get("out_trade_no")+"</out_trade_no>");
//        paramBuffer.append("<total_fee>"+paraMap.get("total_fee")+"</total_fee>");
//        paramBuffer.append("<spbill_create_ip>"+paraMap.get("spbill_create_ip")+"</spbill_create_ip>");
//        paramBuffer.append("<notify_url>"+paraMap.get("notify_url")+"</notify_url>");
//        paramBuffer.append("<trade_type>"+paraMap.get("trade_type")+"</trade_type>");
//        paramBuffer.append("<openid>"+paraMap.get("openid")+"</openid>");
//        paramBuffer.append("</xml>");
//        
//        ConsoleUtil.debug("paramBuffer:"+paramBuffer.toString());
//        //发送请求(POST)(获得数据包ID)(这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码 就算你改成UTF8编码也一样不好使 所以修改成ISO8859-1)
//        try {
//			Map<String,String> map = doXMLParse(getRemotePortData(UNIFIED_ORDER, new String(paramBuffer.toString().getBytes(), "ISO8859-1")));
//			ConsoleUtil.info("map ----- >  "+map.toString());
//			for (Map.Entry<String, String> entry : map.entrySet()) {
//				ConsoleUtil.info("---- "+entry.getKey()+" : "+entry.getValue());
//			}
//			if(map != null){
//				//创建订单
//				
//				
//				
//				ConsoleUtil.debug("微信 统一下单 接口调用成功 并且新增支付信息成功");
//				resultJson.put("prepayId", map.get("prepay_id"));
//				resultJson.put("outTradeNo", outTradeNo);
//				return resultJson;
//			}
//        
//        } catch (Exception e) {
//			e.printStackTrace();
//		}
//        
//        return resultJson;
//    }
//	
//	
	
}
