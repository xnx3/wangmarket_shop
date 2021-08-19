package com.xnx3.wangmarket.shop.core.service;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.SMSUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.OrderRule;
import com.xnx3.wangmarket.shop.core.entity.PaySet;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;

/**
 * 商铺的短信发送设置
 * @author 管雷鸣
 *
 */
public interface StoreSMSService {
	
	/**
	 * 发送短信
	 * @param storeid 商铺的id，对应 Store.id 因为每个商铺都有自己的短信通道，所以要传入商铺id，来使用某个商铺的短信通道进行发送。
	 * @param phone 要发送的手机号
	 * @param content 短信的内容，注意的是，短信签名+短信内容一共不要超过68个字符，不然计费会计多条短信的费用。验证码使用 {code} 代替。会自动替换为 6位数字验证码
	 * @param type 短信发送的类型。限制10个字符。如 reg  login   tongzhi
	 * @param request {@link HttpServletRequest}
	 * @return {@link BaseVO}。 其中 result 的值不同，结果不同 
	 * 		<ul>
	 * 			<li>result 为 {@link BaseVO}.SUCCESS 那么短信发送成功 </li>
	 * 			<li>result 为 {@link BaseVO}.FAILURE 那么短信发送失败，使用 info() 获取失败结果显示给用户 </li>
	 * 			<li>result 为 2， 那么是商家未设置或者未开启短信发送功能</li>
	 * 		</ul>
	 */
	public BaseVO send(int storeid, String phone, String content, String type, HttpServletRequest request);
	
	/**
	 * 将某个商家的短信设置加入持久缓存。这里也是作为 SmsSet 数据更改后，刷新缓存用。
	 * @param smsSet 商家的短信接口设置
	 */
	public void setSMSUtil(int storeid, SMSUtil smsUtil);
	
	/**
	 * 获取某个商家的 {@link SMSUtil}
	 * @param storeid 要获取的是哪个商家的，对应 Store.id
	 * @return 如果这个商家未设置，那么会返回null 
	 */
	public SMSUtil getSMSUtil(int storeid);
}