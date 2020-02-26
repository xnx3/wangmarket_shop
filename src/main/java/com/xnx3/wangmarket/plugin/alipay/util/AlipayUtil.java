package com.xnx3.wangmarket.plugin.alipay.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.xnx3.wangmarket.plugin.alipay.bean.PcOrderBean;
import com.xnx3.wangmarket.plugin.alipay.bean.WapOrderBean;

/**
 * 支付宝支付工具类
 * @author 管雷鸣
 *
 */
public class AlipayUtil {
	//应用 app id
	private String appId;
	//应用私钥
	private String appPrivateKey;
	//应用公钥证书路径，在磁盘的绝对路径
	private String appCertPublicKey;
	//支付宝公钥证书路径
	private String alipayCertPublicKeyRSA2;
	//支付宝根证书路径
	private String alipayRootCert;
	
	//支付成功后异步通知的url
	public static final String NOTICE_URL = "https://api.shop.leimingyun.com/shop/api/pay/alipayCallback.do";
	public static final String RETURN_URL = "http://api.shop.leimingyun.com/plugin/alipay/alipaySuccessJumpPage.do";
	//编码
	public static final String CHARSET = "UTF-8";
	//sign type
	public static final String SIGN_TYPE = "RSA2";
	
	//实例化客户端
	private AlipayClient alipayClient;
	
	public String crtPath;	//crt所在的目录，如 /mnt/tomcat8/webapps/ROOT/WEB-INF/classes/com/xnx3/wangmarket/plugin/alipay/crt/
	public AlipayUtil(String appId, String appPrivateKey, String appCertPublicKey, String alipayCertPublicKeyRSA2, String alipayRootCert) {
		//crtPath = AlipayUtil.class.getResource("/").getPath()+"com/xnx3/wangmarket/plugin/alipay/crt/";
		this.appId = appId;
		this.appPrivateKey = appPrivateKey;
		this.appCertPublicKey = appCertPublicKey;
		this.alipayCertPublicKeyRSA2 = alipayCertPublicKeyRSA2;
		this.alipayRootCert = alipayRootCert;
	}
	
	public AlipayClient getAlipayClient() throws AlipayApiException{
		//构造client
		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
		//设置网关地址
		certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
		//设置应用Id
		certAlipayRequest.setAppId(this.appId);
		//设置应用私钥
		certAlipayRequest.setPrivateKey(this.appPrivateKey);
		//设置请求格式，固定值json
		certAlipayRequest.setFormat("json");
		//设置字符集
		certAlipayRequest.setCharset(CHARSET);
		//设置签名类型
		certAlipayRequest.setSignType(SIGN_TYPE);
		//设置应用公钥证书路径
		certAlipayRequest.setCertPath(this.appCertPublicKey);
		//设置支付宝公钥证书路径
		certAlipayRequest.setAlipayPublicCertPath(this.alipayCertPublicKeyRSA2);
		//设置支付宝根证书路径
		certAlipayRequest.setRootCertPath(this.alipayRootCert);
		//构造client
		alipayClient = new DefaultAlipayClient(certAlipayRequest);
		
		return alipayClient;
	}
	
	/**
	 * 电脑PC端支付
	 * @param pcOrderBean {@link PcOrderBean}支付的一些参数
	 */
	public String pcPay(PcOrderBean pcOrderBean) throws AlipayApiException{
		
		//构造API请求
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		request.setNotifyUrl(NOTICE_URL);
		request.setReturnUrl(RETURN_URL);
		request.setBizContent(pcOrderBean.getJsonString());
		
		AlipayClient client = getAlipayClient();
		//发送请求
		AlipayTradePagePayResponse response = client.pageExecute(request);
		if(response.isSuccess()){
			return response.getBody();
		}else{
			return "error:"+response.getCode();
		}
	}
	
	/**
	 * 手机网页WAP支付
	 * @param wapOrderBean {@link WapOrderBean}支付的一些参数
	 */
	public String wapPay(WapOrderBean wapOrderBean) throws AlipayApiException{
		
		//构造API请求
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		request.setNotifyUrl(NOTICE_URL);
		request.setReturnUrl(RETURN_URL);
		request.setBizContent(wapOrderBean.getJsonString());
		
		AlipayClient client = getAlipayClient();
		//发送请求
		AlipayTradeWapPayResponse response = client.pageExecute(request);
		if(response.isSuccess()){
			return response.getBody();
		}else{
			return "error:"+response.getCode();
		}
	}
	
	/**
	 * 支付成功后，回调、或者跳转到的页面，进行验证签名
	 * @param paramsMap 也就是AlipayUtil.requestParamsToMap(request) request中包含着支付宝传递过来的那一堆参数
	 * @return 验证成功返回true
	 */
	public boolean rsa2CertCheck(Map<String, String> paramsMap){
		try {
			boolean signVerified = AlipaySignature.rsaCertCheckV1(paramsMap, this.alipayCertPublicKeyRSA2, AlipayUtil.CHARSET, AlipayUtil.SIGN_TYPE);
			return signVerified;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		AlipayUtil alipayUtil = new AlipayUtil(
				"20210011106。。。。", 
				"MIIEvgIBAD。。。。",
				"/Users/apple/git/wangmarket/src/main/java/com/xnx3/wangmarket/plugin/alipay/crt/appCertPublicKey.crt",
				"/Users/apple/git/wangmarket/src/main/java/com/xnx3/wangmarket/plugin/alipay/crt/alipayCertPublicKey_RSA2.crt",
				"/Users/apple/git/wangmarket/src/main/java/com/xnx3/wangmarket/plugin/alipay/crt/alipayRootCert.crt");
		
//		WapOrderBean orderBean = new WapOrderBean();
//	    orderBean.setOutTradeNo(DateUtil.timeForUnix10()+"");
//	    orderBean.setSubject("xx");
//	    orderBean.setTotalAmount(0.01f);
//		try {
//			String form = alipayUtil.wapPay(orderBean);
//			System.out.println(form);
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
		
		PcOrderBean orderBean = new PcOrderBean("12345", 0.01f, "test", "");
		try {
			System.out.println(alipayUtil.pcPay(orderBean));
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将request中的参数转换成Map
	 */
    public static Map<String, String> requestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }
}
