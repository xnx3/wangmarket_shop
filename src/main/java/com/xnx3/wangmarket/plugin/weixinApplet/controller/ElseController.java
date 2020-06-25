package com.xnx3.wangmarket.plugin.weixinApplet.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.SessionUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.core.service.WeiXinService;
import com.xnx3.weixin.WeiXinAppletUtil;
import com.xnx3.weixin.vo.PhoneVO;

/**
 * 微信小程序模块-其他功能，如获取手机号
 * @author 管雷鸣
 */
@Controller(value="WeixinAppletElsePluginController")
@RequestMapping("/plugin/weixinApplet/")
public class ElseController extends BasePluginController {
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private SqlService sqlService;
	@Resource
	private WeiXinService weiXinService;
	

	/**
	 * 保存用户手机号。这个手机号是通过微信小程序获取手机号按钮获取加密数据，在这里解密，在保存到 user.phone 字段中
	 * @param sessionKey 通过小程序code登录后获得的sessionKey
	 * @param storeid 当前是操作的那个商城，Store.id
	 * @param encryptedData 小程序通过 open-type="getPhoneNumber" 按钮获取的手机号加密信息
	 * @param iv 小程序通过 open-type="getPhoneNumber" 按钮获取的
	 * @return 成功后，info会传回手机号
	 */
	@RequestMapping(value="/savePhone${api.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public BaseVO savePhone(HttpServletRequest request, Model model,
			@RequestParam(value = "sessionKey", required = false, defaultValue="") String sessionKey,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "encryptedData", required = false, defaultValue="") String encryptedData,
			@RequestParam(value = "iv", required = false, defaultValue="") String iv){
		if(storeid < 1){
			return error("请传入您的商铺编号storeid");
		}
		if(sessionKey.length() == 0){
			return error("sessionKey不存在");
		}
		if(!haveUser()){
			return error("尚未登录");
		}
		User user = getUser();
		
        if(encryptedData.length() == 0){
        	return error("请求数据不存在");
        }
		
        WeiXinAppletUtil util = weiXinService.getWeiXinAppletUtil(storeid);
        if(util == null){
        	return error("店铺为设置微信小程序参数");
        }
        PhoneVO phoneVO = util.getPhone(sessionKey, encryptedData, iv);
        if(phoneVO.getResult() - PhoneVO.SUCCESS == 0){
        	//成功
        	
        	//判断一下这个用户数据库中是否有手机号，如果有，是否跟当前获得的不一样，有没有必要更新
        	if(user.getPhone() != null && user.getPhone().equals(phoneVO.getPhone())){
        		//一样，那么久没必要在保存一次了
        	}else{
        		//更新手机号
        		//保存到该用户的User表
        		User updateUser = sqlService.findById(User.class, user.getId());
        		updateUser.setPhone(phoneVO.getPhone());
        		sqlService.save(updateUser);
        		//更新缓存
        		SessionUtil.setUser(updateUser);
        	}
        	ActionLogUtil.insertUpdateDatabase(request, storeid, "保存用户手机号:"+user.toString()+",phoneVO:"+phoneVO.toString());
        	return success(phoneVO.getPhone());
        }else{
        	//失败
        	return error(phoneVO.getInfo());
        }
	}
	
}