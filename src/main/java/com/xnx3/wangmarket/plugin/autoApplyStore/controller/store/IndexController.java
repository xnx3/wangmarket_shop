package com.xnx3.wangmarket.plugin.autoApplyStore.controller.store;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 自助开店，商家后台
 */
@Controller(value="AutoApplyStoreStoreIndexPluginController")
@RequestMapping("/plugin/autoApplyStore/store/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	
}