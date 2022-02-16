package com.xnx3.wangmarket.shop.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.service.GoodsService;
import com.xnx3.wangmarket.shop.core.service.GoodsTypeService;
import com.xnx3.wangmarket.shop.core.vo.GoodsTypeListVO;
import com.xnx3.wangmarket.shop.core.vo.GoodsTypeVO;

/**
 * 商品分类
 * @author 管雷鸣
 */
@Controller(value="ShopGoodsTypeController")
@RequestMapping("/shop/api/goodsType/")
public class GoodsTypeController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private SqlCacheService sqlCacheService;
	

	/**
	 * 获取某个店铺的商品分类
	 * @param storeid 店铺id，对应 Store.id ,要获取的是哪个店铺的商品分类
	 * @return {@link GoodsTypeListVO} 若不存在，则会new一个返回过来，不会存在null的情况
	 * @author 管雷鸣
	 */
	@RequestMapping(value="list.json", method = RequestMethod.POST)
	@ResponseBody
	public GoodsTypeListVO list(HttpServletRequest request,
			@RequestParam(value = "storeid", required = true, defaultValue="1") int storeid){
		//日志记录
		ActionLogUtil.insert(request, storeid,"获取店铺内商品的分类列表");	
		return goodsService.getStoreGoodsType(storeid);
	}
	
	/**
	 * 获取单个分类的信息
	 * @param id 要获取的分类的id
	 * @return 如果不存在，result会返回失败
	 */
	@RequestMapping(value="detail.json", method = RequestMethod.POST)
	@ResponseBody
	public GoodsTypeVO detail(HttpServletRequest request,
			@RequestParam(value = "id", required = true, defaultValue="0") int id){
		GoodsTypeVO vo = new GoodsTypeVO();
		
		GoodsType goodsType = sqlCacheService.findById(GoodsType.class, id, 1800);
		if(goodsType == null) {
			vo.setBaseVO(BaseVO.FAILURE, "要获取的商品分类不存在");
			return vo;
		}
		vo.setGoodsType(goodsType);
		
		//日志记录
		ActionLogUtil.insert(request, id,"获取商品分类详情", goodsType.toString());	
		return vo;
	}
	
}