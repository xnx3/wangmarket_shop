package com.xnx3.wangmarket.plugin.rootTxtUpload.controller;

import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.wangmarket.plugin.rootTxtUpload.Global;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问上传的txt文件
 * @author 薛浩
 */
@Controller(value = "DomainVisitTxtPluginController")
@RequestMapping("/")
public class DomainVisitTxtController extends BasePluginController {

    /**
     * 通过网址访问txt文件，输出txt文件的内容
     * @return txt文件的内容
     */
    @RequestMapping("*.txt")
    @ResponseBody
    public String robots(HttpServletRequest request, HttpServletResponse response, Model model){
        String htmlFile = request.getServletPath();
        htmlFile = htmlFile.replace("/", "");	//将开头的 /去掉
        String text = AttachmentUtil.getTextByPath(Global.TXT_FILE_PATH + htmlFile);
        if(text == null){
            return "this file not find";
        }
        return text;
    }
}
