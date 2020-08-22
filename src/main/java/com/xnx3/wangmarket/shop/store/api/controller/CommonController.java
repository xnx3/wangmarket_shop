package com.xnx3.wangmarket.shop.store.api.controller;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.Global;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 公共的一些方法的控制器
 * @author 刘鹏
 */
@Controller(value="ShopStoreApiCommonController")
@RequestMapping("/shop/store/api/common")
public class CommonController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 上传图片接口
	 */
	@RequestMapping(value="uploadImage${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public UploadFileVO uploadImage(HttpServletRequest request){
		String path = Global.ATTACHMENT_FILE_CAROUSEL_IMAGE.replace("{storeid}", getStoreId()+"");
		UploadFileVO uploadFileVO = AttachmentUtil.uploadImage(path, request, "image", 0);
		
		if(uploadFileVO.getResult() == UploadFileVO.SUCCESS){
			//上传成功，写日志
			ActionLogUtil.insert(request, "商家上传图片", uploadFileVO.getPath());
		}
		
		return uploadFileVO;
	}
}
