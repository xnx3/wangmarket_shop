package com.xnx3.wangmarket.weixin.util;

import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;

/**
 * 微信公众号的工具类
 * @author 管雷鸣
 *
 */
public class WeiXinUtil extends com.xnx3.weixin.WeiXinUtil{

	public WeiXinUtil(String appId, String appSecret, String token) {
		super(appId, appSecret, token);
	}

	/**
	 * 根据一个id，获取其独有带参数的微信二维码，永久有效  
	 * @param scene_str 二维码带的参数，字符串类型，长度限制为1到64
	 * @return 若result为success，则可通过 getInfo() 获取上传到自己服务器(或云存储) 的图片url绝对路径
	 */
	public BaseVO getParamQrcode(String scene_str){
		int i=0;
		com.xnx3.BaseVO tvo = null;	//专门获取ticket的
		for (i=0; i < 4; i++) {
			if(tvo == null || tvo.getResult() - BaseVO.FAILURE == 0){
				tvo = getParamQrcodeTicket(scene_str);
			}else{
				i = 101;
			}
		}
		if(i > 100){
			//微信获取ticket失败！
			//报警
			if(tvo == null){
				return BaseVO.failure("微信获取ticket失败！");
			}
		}
		//获取ticket 失败，直接将其返回
		if(tvo.getResult() - BaseVO.FAILURE == 0){
			return BaseVO.failure(tvo.getInfo());
		}
		
		/* 获取成功，继续向下执行 */
		
		/*
		 * 
		 * 将获取到的 ticket 提取二维码，并将其存入 OSS ，将其 CDN 展示的url地址赋予 info 返回
		 * 
		 */
		String ticket = tvo.getInfo();
		UploadFileVO uploadFileVO = AttachmentUtil.putImageByUrl("weixinParamQrcode/"+scene_str, "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		return uploadFileVO;
	}
}
