package com.xnx3.wangmarket.plugin.rootTxtUpload.controller;

import com.xnx3.BaseVO;
import com.xnx3.Lang;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.plugin.rootTxtUpload.Global;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 微信小程序认证
 * @author 薛浩
 */
@Controller(value = "RootTxtUploadAdminPluginController")
@RequestMapping("/plugin/api/rootTxtUpload/store/")
public class RootTxtUploadController extends BasePluginController {

    /**
     * 上传txt文件
     */
    @ResponseBody
    @RequestMapping(value = "/upload.do")
    public BaseVO upload(HttpServletRequest request, MultipartFile file) throws IOException {
        if(!haveUser()){
            return error("请先登陆");
        }

        String suffix = Lang.findFileSuffix(file.getOriginalFilename());
        if(suffix == null || !suffix.equalsIgnoreCase("txt")){
            return error("只允许上传txt后缀的文件");
        }

        if(file.getSize() > Global.uploadFileMaxSize){
            return error("只允许上传1KB大小之内的文件");
        }

        if(AttachmentUtil.getTextByPath(Global.TXT_FILE_PATH+file.getOriginalFilename()) != null){
            return error("已经有此名字的文件，请更换名字后上传");
        }

        //上传到指定路径
        UploadFileVO vo = AttachmentUtil.uploadFile(Global.TXT_FILE_PATH + file.getOriginalFilename() , file.getInputStream());
        ActionLogUtil.insert(request, getUserId(), "在微信小程序插件上传了一个文件");
        return vo;
    }
}
