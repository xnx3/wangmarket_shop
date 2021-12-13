package com.xnx3.wangmarket.plugin.visitBrowserRecord.controller.admin;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.xnx3.BaseVO;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.ElasticSearchUtil;
import com.xnx3.wangmarket.plugin.visitBrowserRecord.Global;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.vo.ElasticSearchPageListVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 提交访问数据，搜集数据
 * @author 管雷鸣
 */
@Controller(value="VisitBrowserRecordAdminPluginController")
@RequestMapping("/plugin/visitBrowserRecord/admin/")
public class RecordAdminController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 商家管理后台，查看用户访问记录
	 */
	@ResponseBody
	@RequestMapping(value="/list.json",method= {RequestMethod.POST})
	public ElasticSearchPageListVO list(HttpServletRequest request,Model model) {
		ElasticSearchPageListVO vo = new ElasticSearchPageListVO();
		
		if(!haveStoreAuth()) {
			vo.setBaseVO(BaseVO.FAILURE, "请先登录");
			return vo;
		}
		
		if(!ElasticSearchUtil.isUsed()) {
			vo.setBaseVO(BaseVO.FAILURE, "尚未开启访问记录存储");
			return vo;
		}
		
		String query = "storeid:"+SessionUtil.getStore().getId();
		vo = ElasticSearchUtil.list(Global.INDEX_NAME,query,15,request);
		return vo;
	}

}