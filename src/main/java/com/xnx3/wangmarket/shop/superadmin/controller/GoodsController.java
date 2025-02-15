package com.xnx3.wangmarket.shop.superadmin.controller;

import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;
import com.xnx3.wangmarket.shop.store.vo.GoodsListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品管理
 * @author 刘鹏
 */
@Controller(value="ShopStoreAdminGoodsController")
@RequestMapping("/shop/superadmin/goods/")
public class GoodsController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看所有商品列表
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="list.json", method = {RequestMethod.POST})
	public GoodsListVO list(HttpServletRequest request,Model model) {
		GoodsListVO vo = new GoodsListVO();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods");
		//查询条件
		sql.appendWhere("isdelete = " + Goods.ISDELETE_NORMAL);
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"title"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods", sql.getWhere());

		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		//其余可以选择的排序字段
		sql.setOrderByField(new String[]{"id","inventory","sale","price"});
		//按照上方条件查询出该实体总数 用集合来装
		List<Goods> list = sqlService.findBySql(sql,Goods.class);
		//查询商品的分类
		//List<GoodsType> typeList = sqlService.findAll(GoodsType.class);
		//model.addAttribute("typeList", typeList);
		
		vo.setList(list);
		vo.setPage(page);
		
		//日志记录
		ActionLogUtil.insert(request, "查看商品列表");
		return vo;
	}

}
