package com.xnx3.wangmarket.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.service.GoodsService;
import com.xnx3.wangmarket.shop.vo.GoodsTypeListVO;

/**
 * 商品分类相关
 * @author 管雷鸣
 */
@Controller(value="ShopGoodsTypeController")
@RequestMapping("/shop/goodsType/")
public class GoodsTypeController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsService goodsService;

	/**
	 * 获取某个店铺的商品分类
	 * @param storeid 店铺id，对应 Store.id ,要获取的是哪个店铺的商品分类
	 * @return {@link GoodsTypeListVO} 若不存在，则会new一个返回过来，不会存在null的情况
	 */
	@RequestMapping("list${url.suffix}")
	@ResponseBody
	public GoodsTypeListVO list(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		return goodsService.getStoreGoodsType(storeid);
	}

}