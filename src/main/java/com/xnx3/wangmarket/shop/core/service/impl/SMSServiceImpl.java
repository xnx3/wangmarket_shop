package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.xnx3.DateUtil;
import com.xnx3.SMSUtil;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.util.CacheUtil;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.util.LanguageUtil;
import com.xnx3.j2ee.util.SafetyUtil;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.SmsSet;
import com.xnx3.wangmarket.shop.core.entity.StoreSmsLog;
import com.xnx3.wangmarket.shop.core.service.SMSService;
import com.xnx3.wangmarket.shop.core.service.SMSSetService;

@Service
public class SMSServiceImpl implements SMSService {
	@Resource
	private SqlDAO sqlDAO;
	@Resource
	private SMSSetService smsSetService;
	
	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}
	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}
	
	@Override
	public BaseVO send(int storeid, String phone, String content, String type, HttpServletRequest request) {
//		SMSUtil util = new SMSUtil(uid, password)
		if(storeid < 0){
			return BaseVO.failure("storeid不能小于0");
		}
		//取得当前店铺的短信设置
		SmsSet set = smsSetService.getSMSSet(storeid);
		
		SMSUtil util = getSMSUtil(storeid);
		if(util == null){
			//util不存在，那么需要new一个
			if(set == null){
				BaseVO vo = new BaseVO();
				vo.setBaseVO(2, "当前店铺未配置短信接口");
				return vo;
			}
			if(set.getUseSms() - 0 == 0){
				BaseVO vo = new BaseVO();
				vo.setBaseVO(2, "当前店铺开启短信发送");
				return vo;
			}
		}
		
		/** 判断一下发送规则，看是否达到发送上限 **/
		if(phone==null || phone.length() != 11){
			return BaseVO.failure(LanguageUtil.show("sms_sendSmsPhoneNumberFailure"));
		}
		//获取当天凌晨的时间
		int weeHours = DateUtil.dateToInt10(DateUtil.weeHours(new Date()));	
		//查询当前手机号是否达到当天发送短信的限额
		int daysendnum = sqlDAO.count("shop_store_sms_log", "WHERE storeid = "+storeid+" AND phone = '"+Sql.filter(phone)+"' AND addtime > "+weeHours);
		if(daysendnum<set.getQuotaDayIp()){
			//正常，可继续发送
		}else{
			return BaseVO.failure("您今天短信发送太多，已限制短信使用，不可发送");
		}
		
		//查询当前ip是否已经达到当天发送限额
		if(request != null){
			String ip = IpUtil.getIpAddress(request);
			int ipsendnum = sqlDAO.count("shop_store_sms_log", "WHERE storeid = "+storeid+" AND addtime > "+weeHours + " AND ip = '"+Sql.filter(ip)+"'");
			if(ipsendnum<SmsLog.everyDayIpNum){
			}else{
				return BaseVO.failure("您IP今天短信发送太多，已限制短信使用，不可发送");
			}
		}
		
		//保存记录到数据表
		StoreSmsLog smsLog = new StoreSmsLog();
		
		String code = "000000";	//6位数字验证码。全是0则没有使用验证码
		//判断是否使用了验证码
		if(content.indexOf("{code}") > -1){
			//使用了验证码
			//发送验证码流程逻辑
			Random random = new Random();
			code = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
			smsLog.setCode(code);
		}
		
		
		
		smsLog.setAddtime(DateUtil.timeForUnix10());
		
		if(request != null){
			smsLog.setIp(IpUtil.getIpAddress(request));
		}else{
			smsLog.setIp("");
		}
		smsLog.setPhone(SafetyUtil.filter(phone));
		smsLog.setType(type);
		smsLog.setUsed(SmsLog.USED_FALSE);
		smsLog.setUserid(0);
		smsLog.setStoreid(storeid);
		sqlDAO.save(smsLog);
		
		//发送短信
		com.xnx3.BaseVO smsVO = util.send(phone, content);
		BaseVO vo = new BaseVO();
		vo.setBaseVO(smsVO);
		return vo;
	}

	@Override
	public void setSMSUtil(int storeid, SMSUtil smsUtil) {
		if(smsUtil == null){
			return;
		}
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_SMS_UTIL_SET.replace("{storeid}", storeid+"");
		CacheUtil.set(key, smsUtil);
	}
	
	@Override
	public SMSUtil getSMSUtil(int storeid) {
		String key = com.xnx3.wangmarket.shop.core.Global.CACHE_KEY_SMS_UTIL_SET.replace("{storeid}", storeid+"");
		SMSUtil util = (SMSUtil)CacheUtil.get(key);
		if(util == null){
			//取得当前店铺的短信设置
			SmsSet set = smsSetService.getSMSSet(storeid);
			if(set != null && set.getUseSms() - 1 == 0 && set.getUid() != null && set.getPassword() != null){
				//数据库中有这个数据，并且开启了使用，那么取出这个数据来，new SMSUtil 对象
				util = new SMSUtil(set.getUid(), set.getPassword());
				//将它加入缓存
				setSMSUtil(storeid, util);
			}
		}

		return util;
	}
	
}