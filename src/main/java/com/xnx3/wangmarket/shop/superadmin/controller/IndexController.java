package com.xnx3.wangmarket.shop.superadmin.controller;

import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.api.controller.BaseController;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 超级管理后台
 * @author 魏繁中
 */
@Controller(value="ShopSuperAdminIndexController")
@RequestMapping("/shop/superadmin/index/")
public class IndexController extends BaseController {

    /**
     * 登录后的欢迎页面
     */
    @RequestMapping("welcome${url.suffix}")
    public String welcome(HttpServletRequest request, Model model){
        ActionLogUtil.insert(request,"总管理后台，登陆成功后的欢迎页面");

        model.addAttribute("version",Global.VERSION); // 版本号
        return "/iw_update/admin/index/welcome";
    }

}
