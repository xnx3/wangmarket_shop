package com.xnx3.wangmarket.shop.store.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;


/**
 * 公共的一些方法的控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreCommonController")
@RequestMapping("/shop/store/common")
public class CommonController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 上传图片接口
	 */
	@RequestMapping(value="uploadImage${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public UploadFileVO uploadImage(Model model,HttpServletRequest request){
		UploadFileVO uploadFileVO = AttachmentUtil.uploadImage("goodsList/", request, "image", 0);
		
		if(uploadFileVO.getResult() == UploadFileVO.SUCCESS){
			//上传成功，写日志
			ActionLogUtil.insert(request, "商品详情里图片上传", uploadFileVO.getPath());
		}
		
		return uploadFileVO;
	}
}
