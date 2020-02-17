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
import com.xnx3.wangmarket.shop.util.SessionUtil;
import com.xnx3.wangmarket.shop.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.vo.GoodsListVO;
import com.xnx3.wangmarket.shop.vo.GoodsTypeListVO;

/**
 * 商品相关
 * @author 管雷鸣
 */
@Controller(value="ShopGoodsController")
@RequestMapping("/shop/goods/")
public class GoodsController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsService goodsService;

	/**
	 * 获取商品列表
	 * @param storeid 要获取哪个商铺的商品，这里是商店的ID，Store.id。  如果传入0，则是查询所有商铺的商品
	 * @param typeid 要查询商铺中，哪个分类的商品。如果传入0，则是查询这个商店中所有的商品
	 * @param orderBy 商品排序方式
	 * 				<ul>
	 * 					<li>0:默认排序方式，也就是根据销量由高往低来排序</li>
	 * 					<li>1:按总销量由高往低来排序</li>
	 * 					<li>2:最新商品，按发布时间，由高往低来排序，最后发布的商品在最前面</li>
	 * 				</ul>
	 * @return {@link GoodsListVO}
	 */
	@RequestMapping("list${url.suffix}")
	@ResponseBody
	public GoodsListVO list(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "typeid", required = false, defaultValue="0") int typeid,
			@RequestParam(value = "orderBy", required = false, defaultValue="0") int orderBy){
		return goodsService.getGoodsList(storeid, typeid, orderBy);
	}
	

	/**
	 * 获取某个商品的商品详情，包括商品信息、详情信息等。商品详情查看页面使用到
	 * @param goodsid 要查看的商品id，goods.id
	 * @return GoodsDetailsVO
	 */
	@RequestMapping("details${url.suffix}")
	@ResponseBody
	public GoodsDetailsVO details(HttpServletRequest request,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid){
		return goodsService.getGoodsDetails(goodsid);
	}
	

	
}