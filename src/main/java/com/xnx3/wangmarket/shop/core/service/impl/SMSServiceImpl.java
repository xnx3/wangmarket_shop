package com.xnx3.wangmarket.shop.core.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xnx3.SMSUtil;
import com.xnx3.j2ee.service.SmsService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.service.SMSService;

@Service
public class SMSServiceImpl implements SMSService {
	@Resource
	private SmsService SmsService;
	
	@Override
	public BaseVO send(String phone, String content) {
//		SMSUtil util = new SMSUtil(uid, password)
		return null;
	}

	
}
