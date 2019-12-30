package com.xnx3.wangmarket.weixin.util;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.MD5Util;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.MapUtil;
import com.xnx3.wangmarket.weixin.Global;
import com.xnx3.wangmarket.weixin.util.weixinPay.createOrder.CreateOrderInterface;
import com.xnx3.wangmarket.weixin.util.weixinPay.createOrder.PayOrderBean;
import net.sf.json.JSONObject;

/**
 * 微信支付工具类
 * @author 管雷鸣
 *
 */
public class WeiXinPayUtil {
	//统一下单.商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付。
	public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	private String xiaochengxu_appid;	//小程序ID
	private String shanghu_mch_id;	//商户号
	private String shanghu_key;		//商户key,在微信商户平台-帐户设置-安全设置-API安全-API密钥-设置API密钥这个里面设置的KEY
	
	/**
	 * 创建微信支付工具
	 * @param xiaochengxu_appid 小程序ID
	 * @param shanghu_mch_id 商户号
	 * @param shanghu_key 商户key，在微信商户平台-帐户设置-安全设置-API安全-API密钥-设置API密钥这个里面设置的KEY
	 */
	public WeiXinPayUtil(String xiaochengxu_appid, String shanghu_mch_id, String shanghu_key) {
		this.xiaochengxu_appid = xiaochengxu_appid;
		this.shanghu_mch_id = shanghu_mch_id;
		this.shanghu_key = shanghu_key;
	}
	

	public static void main(String[] args) {
//		ConsoleUtil.info = true;
		WeiXinPayUtil pay = new WeiXinPayUtil("wx2a2e6faa9b9cf759", "1544579851", "85cf732a75fe441ba734152e56b131d5");
		PayOrderBean payOrderBean = new PayOrderBean();
		payOrderBean.setBody("测试");
		payOrderBean.setClientIp("127.0.0.1");
		payOrderBean.setNotifyUrl("http://xxx.xx.com/aa.do");
		payOrderBean.setOpenid("");
		payOrderBean.setTotalFee(1);
		pay.createOrder(payOrderBean, new CreateOrderInterface() {
			public void CreateOrderSuccess(PayOrderBean payOrderBean) {
				System.out.println(payOrderBean);
			}
		});
		
		//85cf732a75fe441ba734152e56b131d5
	}
	
	/**
	 * 创建订单，在微信支付那边创建对应的订单
	 * @param bean {@link PayOrderBean} 必须赋值里面所有的参数
	 * @param createOrderInterface 订单创建成功后，要执行的操作
	 */
	public boolean createOrder(PayOrderBean bean, CreateOrderInterface createOrderInterface){
		ConsoleUtil.debug("微信 统一下单 接口调用");
    
        //接受参数(金额)
//        String amount = "200";	//支付金额，单位是元
        
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
        //String amountFen = Float.valueOf((Float.parseFloat(amount)*100)).toString();
//        String amountFen = "1";
        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置body变量 (支付成功显示在微信支付 商品详情中)
//        String body = "大潍坊脸卡";
        //设置随机字符串
        String nonceStr = Lang.uuid();
        //设置商户订单号
//        String outTradeNo = StringUtil.getRandom09AZ(2)+StringUtil.intTo36(DateUtil.timeForUnix10())+StringUtil.getRandom09AZ(2);
        
        //设置请求参数(小程序ID)
        paraMap.put("appid", this.xiaochengxu_appid);
        //设置请求参数(商户号)
        paraMap.put("mch_id", this.shanghu_mch_id);
        //设置请求参数(随机字符串)
        paraMap.put("nonce_str", nonceStr);
        //设置请求参数(商品描述)
        paraMap.put("body", bean.getBody());
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", bean.getOutTradeNo());
        //设置请求参数(总金额)
        paraMap.put("total_fee", bean.getTotalFee()+"");
        //设置请求参数(终端IP)
        paraMap.put("spbill_create_ip", bean.getClientIp());
        //设置请求参数(通知地址)
        paraMap.put("notify_url", bean.getNotifyUrl());
        //设置请求参数(交易类型)
        paraMap.put("trade_type", "JSAPI");
        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
//        paraMap.put("openid", bean.getOpenid());
        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        System.out.println(stringA+"&key="+shanghu_key);
        String sign = MD5Util.MD5(stringA+"&key="+shanghu_key).toUpperCase();
        System.out.println(sign);
        //将参数 编写XML格式
        StringBuffer paramBuffer = new StringBuffer();
        paramBuffer.append("<xml>");
        paramBuffer.append("<appid>"+xiaochengxu_appid+"</appid>");
        paramBuffer.append("<mch_id>"+shanghu_mch_id+"</mch_id>");
        paramBuffer.append("<nonce_str>"+paraMap.get("nonce_str")+"</nonce_str>");
        paramBuffer.append("<sign>"+sign+"</sign>");
        paramBuffer.append("<body>"+bean.getBody()+"</body>");
        paramBuffer.append("<out_trade_no>"+paraMap.get("out_trade_no")+"</out_trade_no>");
        paramBuffer.append("<total_fee>"+paraMap.get("total_fee")+"</total_fee>");
        paramBuffer.append("<spbill_create_ip>"+paraMap.get("spbill_create_ip")+"</spbill_create_ip>");
        paramBuffer.append("<notify_url>"+paraMap.get("notify_url")+"</notify_url>");
        paramBuffer.append("<trade_type>"+paraMap.get("trade_type")+"</trade_type>");
        paramBuffer.append("<openid>"+paraMap.get("openid")+"</openid>");
        paramBuffer.append("</xml>");
        
        ConsoleUtil.debug("paramBuffer:"+paramBuffer.toString());
        //发送请求(POST)(获得数据包ID)(这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码 就算你改成UTF8编码也一样不好使 所以修改成ISO8859-1)
        try {
			Map<String,String> map = doXMLParse(getRemotePortData(UNIFIED_ORDER, new String(paramBuffer.toString().getBytes(), "ISO8859-1")));
			ConsoleUtil.info("map ----- >  "+map.toString());
			for (Map.Entry<String, String> entry : map.entrySet()) {
				ConsoleUtil.info("---- "+entry.getKey()+" : "+entry.getValue());
			}
			if(map != null){
				if(map.get("return_code") != null && map.get("return_code").equals("FAIL")){
					ConsoleUtil.error("微信支付，创建订单失败，return_code:"+map.get("return_msg"));
					return false;
				}
				
				//创建订单成功
				createOrderInterface.CreateOrderSuccess(bean);
				return true;
			}
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return false;
	}
	

    /** 
     *  
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br> 
     * 实现步骤: <br> 
     *  
     * @param paraMap   要排序的Map对象 
     * @param urlEncode   是否需要URLENCODE 
     * @param keyToLower    是否需要将Key转换为全小写 
     *            true:key转化成小写，false:不转化 
     * @return 
     */  
    private static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower){  
        String buff = "";  
        Map<String, String> tmpMap = paraMap;  
        try  
        {  
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());  
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）  
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()  
            {  
                @Override  
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)  
                {  
                    return (o1.getKey()).toString().compareTo(o2.getKey());  
                }  
            });  
            // 构造URL 键值对的格式  
            StringBuilder buf = new StringBuilder();  
            for (Map.Entry<String, String> item : infoIds)  {
                
            	if (item.getKey() != null && item.getKey().length() > 0)  
                {  
                    String key = item.getKey();  
                    String val = item.getValue();  
                    if (urlEncode)  
                    {  
                        val = URLEncoder.encode(val, "utf-8");  
                    }  
                    if (keyToLower)  
                    {  
                        buf.append(key.toLowerCase() + "=" + val);  
                    } else  
                    {  
                        buf.append(key + "=" + val);  
                    }  
                    buf.append("&");  
                }  
   
            }  
            buff = buf.toString();  
            if (buff.isEmpty() == false)  
            {  
                buff = buff.substring(0, buff.length() - 1);  
            }  
        } catch (Exception e)  
        {  
           return null;  
        }  
        return buff;  
    }
    
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private Map<String,String> doXMLParse(String strxml) throws Exception {
        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        
        Map<String,String> m = new HashMap<String,String>();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            
            m.put(k, v);
        }
        
        //关闭流
        in.close();
        
        return m;
    }
    
    private  InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

	
	/**
     * 方法名: getRemotePortData
     * 描述: 发送远程请求 获得代码示例
     * 参数：  @param urls 访问路径
     * 参数：  @param param 访问参数-字符串拼接格式, 例：port_d=10002&port_g=10007&country_a=
     * 创建人: Xia ZhengWei
     * 创建时间: 2017年3月6日 下午3:20:32
     * 版本号: v1.0   
     * 返回类型: String
    */
    private String getRemotePortData(String urls, String param){
    	ConsoleUtil.info("港距查询抓取数据----开始抓取外网港距数据");
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            conn.setConnectTimeout(30000);
            // 设置读取超时时间
            conn.setReadTimeout(30000);
            conn.setRequestMethod("POST");
            if(param != null && param.length() > 0) {
                conn.setRequestProperty("Origin", "https://sirius.searates.com");// 主要参数
                conn.setRequestProperty("Referer", "https://sirius.searates.com/cn/port?A=ChIJP1j2OhRahjURNsllbOuKc3Y&D=567&G=16959&shipment=1&container=20st&weight=1&product=0&request=&weightcargo=1&");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");// 主要参数
            }
            // 需要输出
            conn.setDoInput(true);
            // 需要输入
            conn.setDoOutput(true);
            // 设置是否使用缓存
            conn.setUseCaches(false);
            // 设置请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            
            if(param != null && param.length() > 0) {
                // 建立输入流，向指向的URL传入参数
                DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(param);
                dos.flush();
                dos.close();
            }
            // 输出返回结果
            InputStream input = conn.getInputStream();
            int resLen =0;
            byte[] res = new byte[1024];
            StringBuilder sb=new StringBuilder();
            while((resLen=input.read(res))!=-1){
                sb.append(new String(res, 0, resLen));
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            ConsoleUtil.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            ConsoleUtil.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
        }
        ConsoleUtil.info("港距查询抓取数据----抓取外网港距数据失败, 返回空字符串");
        return "";
    }
    

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        
        return sb.toString();
    }
    

	/**
	 * 微信支付的回调
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="payCallback${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
    public String payCallback(HttpServletRequest request,HttpServletResponse response) {
		ConsoleUtil.info("微信回调接口方法 start");
		ConsoleUtil.info("微信回调接口 操作逻辑 start");
        String inputLine = "";
        String notityXml = "";
        try {
            while((inputLine = request.getReader().readLine()) != null){
                notityXml += inputLine;
            }
            //关闭流
            request.getReader().close();
            ConsoleUtil.info("微信回调内容信息："+notityXml);
            //解析成Map
            Map<String,String> map = doXMLParse(notityXml);
            //判断 支付是否成功
            if("SUCCESS".equals(map.get("result_code"))){
            	
            	/*
            	 * 签名校验
            	 */
            	String sign = map.get("sign");
            	map.remove("sign");
            	
            	
            	//调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
                String stringA = formatUrlMap(map, false, false);
                //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
                String nSign = MD5Util.MD5(stringA+"&key="+shanghu_key).toUpperCase();
                ConsoleUtil.info("sign:  "+sign+", nSign:"+nSign);
            	if(sign.equalsIgnoreCase(nSign)){
            		ConsoleUtil.info("微信回调返回是否支付成功：是");
                    //获得 返回的商户订单号
                    String outTradeNo = map.get("out_trade_no");
                    ConsoleUtil.info("微信回调返回商户订单号："+outTradeNo);
                    ConsoleUtil.info("微信支付单号："+map.get("transaction_id"));
                    
                    //支付金额，单位为分
                    String total_fee = map.get("total_fee");
                    int totalFee = Lang.stringToInt(total_fee, 0);
                    //将分转化为元
                    totalFee = Math.round(totalFee/100);
                    
                    //通知微信服务器，已经成功处理
                	return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            	}else{
            		//签名对比出错，警报！可能有人利用接口攻击了
            		ConsoleUtil.debug("签名对比出错，警报！可能有人利用接口攻击了-->"+map.toString());
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }
	
}
