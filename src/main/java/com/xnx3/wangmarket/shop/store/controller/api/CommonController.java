package com.xnx3.wangmarket.shop.store.controller.api;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 公共上传图片的接口
 * @author 刘鹏
 */
@Controller(value="ShopStoreApiCommonController")
@RequestMapping("/shop/store/api/common")
public class CommonController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 上传图片接口
	 * @param multipartFile 上传文件
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @return 若请求成功，则可以上传图片
	 * @author 管雷鸣
	 */
	@RequestMapping(value="uploadImage.json", method = RequestMethod.POST)
	@ResponseBody
	public UploadFileVO uploadImage(HttpServletRequest request,
			@RequestParam("file") MultipartFile multipartFile){
		String path = Global.ATTACHMENT_FILE_CAROUSEL_IMAGE.replace("{storeid}", getStoreId()+"");

		UploadFileVO uploadFileVO = AttachmentUtil.uploadFile(path, multipartFile);
		if(uploadFileVO.getResult() == UploadFileVO.SUCCESS){
			//上传成功，写日志
			ActionLogUtil.insert(request, "商家上传图片", uploadFileVO.getPath());
		}
		
		return uploadFileVO;
	}
}
