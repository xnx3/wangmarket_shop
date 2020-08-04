package com.xnx3.wangmarket.shop.store.api.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;
import com.xnx3.wangmarket.shop.store.api.vo.GoodsListVO;


/**
 * 商品管理
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiGoodsController")
@RequestMapping("/shop/store/api/goods/")
public class GoodsController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看商品列表
	 * @author 关光礼
	 */
	@ResponseBody
	@RequestMapping("/list${url.suffix}")
	public GoodsListVO list(HttpServletRequest request,Model model) {
		GoodsListVO vo = new GoodsListVO();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods");
		//查询条件
		sql.appendWhere("isdelete = " + Goods.ISDELETE_NORMAL);
		sql.appendWhere("storeid = " + getStoreId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"title","typeid"});
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
		// 按照上方条件查询出该实体总数 用集合来装
		List<Goods> list = sqlService.findBySql(sql,Goods.class);
		
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品列表");
		return vo;
	}
	
	/**
	 * 修改商品信息
	 * @param id 要修改的商品id， goods.id ， 必填
	 * @param price 要修改的商品的价格，单位是分。 非必填
	 * @param putaway 是否上架在售，1出售中，0已下架。 非必填
	 * @param units 计量，单位。如个、斤、条，限制5字符。非必填
	 * @param rank 排序，数字越小越靠前。非必填
	 */
	@ResponseBody
	@RequestMapping(value="/save${url.suffix}",method = {RequestMethod.POST})
	public BaseVO save(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue="0") int id,
			@RequestParam(value = "price", required = false, defaultValue="-1") int price,
			@RequestParam(value = "putaway", required = false, defaultValue="-1") short putaway,
			@RequestParam(value = "units", required = false, defaultValue="") String units,
			@RequestParam(value = "rank", required = false , defaultValue="-1") int rank) {
		if(id < 1) {
			return error("请传入要修改的商品id");
		}
		
		Goods goods = sqlService.findById(Goods.class, id);
		if(goods == null) {
			return error("商品不存在");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("商品不属于你");
		}
		
		/*** 进行保存商品 ***/
		if(price > -1){
			goods.setPrice(price);
		}
		if(putaway > -1){
			goods.setPutaway(putaway);
		}
		if(!units.equals("")){
			goods.setUnits(units);
		}
		if(rank > -1){
			goods.setRank(rank);
		}
		sqlService.save(goods);
		
		
		//保存完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(),"Id为" + goods.getId() + "的商品添加或修改，内容:" + goods.toString());
		return success();
	}
	
	/**
	 * 保存完成后，删除缓存，已达到更新缓存目的
	 * @param goodsid 要删除的缓存的商品id
	 */
	private void deleteGoodsCache(int goodsid){
		sqlCacheService.deleteCacheById(Goods.class, goodsid);
		sqlCacheService.deleteCacheById(GoodsData.class, goodsid);
	}
	
	/**
	 * 删除商品
	 * @author 关光礼
	 * @param id 删除商品id
	 */
	@ResponseBody
	@RequestMapping(value="/delete${url.suffix}",method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id",defaultValue = "0", required = false) int id) {
		if(id < 1) {
			return error("请传入id参数");
		}
		
		Goods goods = sqlService.findById(Goods.class, id);
		if(goods == null) {
			return error("根据ID,没查到该实体");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("商品不属于你");
		}
		goods.setIsdelete(Goods.ISDELETE_DELETE);
		sqlService.save(goods);
		
		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
				
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "删除ID是" + id + "的商品", "删除内容:" + goods.toString());
		return success();
	}
	
}
