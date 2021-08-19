package com.xnx3.wangmarket.plugin.rootTxtUpload.controller;

import com.xnx3.BaseVO;
import com.xnx3.Lang;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentMode.bean.SubFileBean;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.plugin.rootTxtUpload.vo.UploadFileListVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 微信小程序认证
 * @author 薛浩
 */
@Controller(value = "RootTxtUploadAdminPluginController")
@RequestMapping("/plugin/api/rootTxtUpload/store/")
public class RootTxtUploadController extends BasePluginController {
    /**
     * 上传文件允许的最大的大小，单位是byte 5kb
     */
    public static final long uploadFileMaxSize = 1 * 5 * 1024 ;

    /**
     * 上传txt文件
     */
    @ResponseBody
    @RequestMapping(value = "/upload.do")
    public BaseVO upload(HttpServletRequest request, MultipartFile file) throws IOException {
        Store store = SessionUtil.getStore();
        if(!haveStoreAuth()){
            return error("请先登陆");
        }
        Integer storeId = store.getId();

        String suffix = Lang.findFileSuffix(file.getOriginalFilename());
        if(suffix == null || !suffix.equalsIgnoreCase("txt")){
            return error("只允许上传txt后缀的文件");
        }

        if(file.getSize() > uploadFileMaxSize){
            return error("只允许上传5KB大小之内的文件");
        }

        //上传到指定路径
        UploadFileVO vo = AttachmentUtil.uploadFile("/store/" + storeId + "/plugin/txt/" + file.getOriginalFilename() , file.getInputStream());
        ActionLogUtil.insert(request, getUserId(), "在微信小程序插件上传了一个文件");
        return vo;
    }

    /**
     * 查看已经上传的txt文件
     */
    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.POST})
    public BaseVO list(HttpServletRequest request, Model model) {
        Store store = SessionUtil.getStore();
        if(!haveStoreAuth()){
            return error("请先登陆");
        }
        Integer storeId = store.getId();

        //遍历路径下的txt文件
        String directory = "store/" + storeId + "/plugin/txt/";
        List<SubFileBean> list = AttachmentUtil.getSubFileList(directory);
        UploadFileListVO vo = new UploadFileListVO();
        vo.setList(list);
        return vo;
    }
}
