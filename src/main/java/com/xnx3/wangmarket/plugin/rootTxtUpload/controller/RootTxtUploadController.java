package com.xnx3.wangmarket.plugin.rootTxtUpload.controller;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.AttachmentMode.bean.SubFileBean;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.plugin.rootTxtUpload.vo.Upfilevo;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 微信小程序认证
 * @author 薛浩
 */
@Controller(value = "WeChatMiniAdminPluginController")
@RequestMapping("/plugin/api/weChatMiniAuth/store/")
public class RootTxtUploadController extends BasePluginController {
    @Resource
    private SqlService sqlService;

    /**
     * 上传txt文件
     */
    @ResponseBody
    @RequestMapping(value = "/upload.do")
    public BaseVO upload(HttpServletRequest request, MultipartFile file) throws IOException {
        Store store = SessionUtil.getStore();
        if(!haveStoreAuth()){
            return error("login");
        }
        Integer storeId = store.getId();

        //上传路径
        String directory = "/store/" + storeId + "/plugin/txt/";
        File d = new File(directory);
        //若目录不存在则创建目录
        if (!d.exists()) {
            d.mkdirs();
        }
        //上传到指定路径
        UploadFileVO vo = AttachmentUtil.uploadFile("/store/" + storeId + "/plugin/txt/" + file.getOriginalFilename() , file.getInputStream());

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
            return error("login");
        }
        Integer storeId = store.getId();

        //遍历路径下的txt文件
        String directory = "store/" + storeId + "/plugin/txt/";
        File d = new File(directory);
        //若目录不存在则创建目录
        if (!d.exists()) {
            d.mkdirs();
        }
        List<SubFileBean> list = AttachmentUtil.getSubFileList(directory);
        Upfilevo vo = new Upfilevo();
        vo.setList(list);
        return vo;
    }
}
