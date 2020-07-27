package com.xnx3.wangmarket.shop.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.xnx3.BaseVO;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;


/**
 * 商品分类管理控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreGoodsController")
@RequestMapping("/shop/store/goods/")
public class GoodsController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 查看商品列表
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		
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
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品列表");
		return "/shop/store/goods/list";
	}
	
	
	/**
	 * 跳转添加。修改页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@RequestMapping("toEditPage${url.suffix}")
	public String toEditPage(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		if(id > 0) {
			//查找商品
			Goods goods = sqlService.findById(Goods.class, id);
			if(goods == null){
				return error(model, "商品不存在");
			}
			if(goods.getStoreid() - getStoreId() != 0){
				return error(model, "商品不属于你");
			}
			model.addAttribute("item", goods);
			//查找商品描述
			GoodsData goodsData = sqlService.findAloneByProperty(GoodsData.class, "id", id);
			model.addAttribute("data", goodsData);
			//查找商品顶部轮播图
			GoodsImage  imgs = sqlService.findAloneByProperty(GoodsImage.class, "goodsid", id);
		} 
		ActionLogUtil.insert(request, getUserId(), "查看商品ID为" + (id == 0 ? "":id)+ "的详情，跳转到编辑页面");
		return "/shop/store/goods/edit";
		
	}
	
	/**
	 * 添加修改商品
	 * @author 关光礼
	 * @param inputGoods 接受参数的实体类
	 */
	@ResponseBody
	@RequestMapping(value="/save${url.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,Goods inputGoods) {
		
		Integer id = inputGoods.getId();
		//创建一个实体
		Goods goods;
		if(id == null || id - 0 == 0) {
			// 添加
			goods = new Goods();
			goods.setIsdelete(Goods.ISDELETE_NORMAL);
			goods.setAddtime(DateUtil.timeForUnix10());
			goods.setSale(0);
			goods.setStoreid(getStoreId());
			goods.setUserBuyRestrict(0);
			goods.setStoreid(getStoreId());
		}else {
			//修改
			goods = sqlService.findById(Goods.class, id);
			if(goods == null) {
				return error("根据ID,没查到该商品");
			}
			if(goods.getStoreid() - getStoreId() != 0){
				return error("商品不属于你");
			}
		}
		//接受时间参数
		
		//给实体赋值
		if(inputGoods.getAlarmNum() == null) {
			goods.setAlarmNum(0);
		}else {
			goods.setAlarmNum(inputGoods.getAlarmNum());
		}
		
		if(inputGoods.getFakeSale() == null) {
			goods.setFakeSale(0);
		}else {
			goods.setFakeSale(inputGoods.getFakeSale());
		}
		
		if(inputGoods.getPutaway() == null) {
			goods.setPutaway(Goods.PUTAWAY_SELL);
		}else {
			goods.setPutaway(inputGoods.getPutaway());
		}
		
		if(inputGoods.getUnits() == null || inputGoods.getUnits().equals("")) {
			goods.setUnits("个");
		}else {
			goods.setUnits(inputGoods.getUnits());
		}
		goods.setInventory(inputGoods.getInventory());
		goods.setOriginalPrice(inputGoods.getOriginalPrice());
		goods.setPrice(inputGoods.getPrice());
		goods.setTitle(inputGoods.getTitle());
		goods.setTypeid(inputGoods.getTypeid());
		goods.setUpdatetime(DateUtil.timeForUnix10());
		goods.setTitlepic(inputGoods.getTitlepic());
		goods.setIntro(inputGoods.getIntro());
		//保存实体
		sqlService.save(goods);
		
		//保存商品详情
		String detail = request.getParameter("detail");
		//根据商品id查找详情
		GoodsData data = sqlService.findAloneByProperty(GoodsData.class, "id", goods.getId());
		//如果为空就new一个
		if(data == null) {
			data = new GoodsData();
			data.setId(goods.getId());
		}
		data.setDetail(detail);
		sqlService.save(data);
		
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
	
	/**
	 * 修改商品的上下架
	 * @author 关光礼
	 * @param id 删除商品id
	 */
	@ResponseBody
	@RequestMapping(value="/updatePutaway${url.suffix}",method = {RequestMethod.POST})
	public BaseVO updatePutaway(HttpServletRequest request,
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
		//判断并修改状态
		if(goods.getPutaway() == Goods.PUTAWAY_NOT_SELL) {
			goods.setPutaway(Goods.PUTAWAY_SELL);
		}else {
			goods.setPutaway(Goods.PUTAWAY_NOT_SELL);
		}
		sqlService.save(goods);
		
		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "ID是" + id + "的商品的状态修改", "修改后状态:" + goods.getPutaway());
		return success();
	}

	/**
	 * 跳转添加或修改商品图片列表页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@RequestMapping("imgList${url.suffix}")
	public String imgList(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		if(id < 1) {
			return error(model,"请传入ID信息" );
		}
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods_image");
		//查询条件
		sql.appendWhere("goodsid = " + id);
		//配置按某个字端搜索内容
		//sql.setSearchColumn(new String[] {"title","typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods_image", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods_image ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<GoodsImage> list = sqlService.findBySql(sql,GoodsImage.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品id为" + id + "图片列表");
		
		model.addAttribute("goodId", id);
		return "/shop/store/goods/imgList";
	}
	
	/**
	 * 删除商品图片列表页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@ResponseBody
	@RequestMapping("deleteImg${url.suffix}")
	public BaseVO deleteImg(Model model ,HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		if(id < 1) {
			return error("请传入图片ID信息" );
		}
		GoodsImage img = sqlService.findById(GoodsImage.class, id);
		if(img == null) {
			return error("根据ID,没查到该实体");
		}
		//查出商品信息，从而判断这个图片是不是这个人的
		Goods goods = sqlService.findById(Goods.class, img.getGoodsid());
		if(goods == null) {
			return error("该图所属商品不存在");
		}
		if(goods.getStoreid() - getStoreId() != 0){
			return error("该图不属于你，无法操作");
		}
		
		sqlService.delete(img);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品列表图片删除", "删除内容" + img.toString());
		return success();
	}
	
	/**
	 * 跳转添加。修改页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@RequestMapping("toEditImgPage${url.suffix}")
	public String toEditImgPage(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id,
		@RequestParam(value = "goodId", required = false, defaultValue = "0") int goodId) {
		if(goodId < 1) {
			return error(model, "请传入商品Id信息");
		}
		if(id > 0) {
			GoodsImage img = sqlService.findById(GoodsImage.class, id);
			model.addAttribute("img", img);
		}
		ActionLogUtil.insert(request, getUserId(), "查看商品图片ID为" + (id == 0 ? "":id)+ "的详情，跳转到编辑页面");
		
		model.addAttribute("goodId", goodId);
		return "/shop/store/goods/editImg";
	}
	
	/**
	 * 添加、修改商品轮播图
	 * @author 关光礼
	 * @param inputImg 接受参数的实体类
	 */
	@ResponseBody
	@RequestMapping(value="/imgSave${url.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO imgSave(HttpServletRequest request,GoodsImage inputImg) {
		if(inputImg.getGoodsid() == null) {
			return error("请传入商品ID信息");
		}
		
		Integer id = inputImg.getId();
		//创建一个实体
		GoodsImage img;
		if(id == null || id - 0 == 0) {
			//添加
			img = new GoodsImage();
			img.setGoodsid(inputImg.getGoodsid());
		}else {
			//修改
			String sql = "SELECT * FROM shop_goods_image WHERE id = " + id + " AND goodsid= " + inputImg.getGoodsid();
			img = sqlService.findAloneBySqlQuery(sql, GoodsImage.class);
			if(img == null) {
				return error("根据ID,没查到该商品分类");
			}
			//查出商品信息，从而判断修改的这个图片是不是这个人的
			Goods goods = sqlService.findById(Goods.class, id);
			if(goods == null) {
				return error("该图所属商品不存在");
			}
			if(goods.getStoreid() - getStoreId() != 0){
				return error("该图不属于你，无法操作");
			}
		}
		//给实体赋值
		if(inputImg.getRank() == null) {
			img.setRank(0);
		}else {
			img.setRank(inputImg.getRank());
		}
		img.setImageUrl(inputImg.getImageUrl());
		//保存实体
		sqlService.save(img);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, img.getId(),"Id为" + img.getId() + "的商品的图片添加或修改，内容:" + img.toString());
		
		return success();
	}
	/**
	 * 更改商品排序
	 * @param id 栏目id
	 * @param rank 排序编号。数字越小越靠前
	 * @return
	 */
	@RequestMapping(value="updateRank${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public BaseVO updateRank(HttpServletRequest request,
			@RequestParam(value = "id", required = false , defaultValue="0") int id,
			@RequestParam(value = "rank", required = false , defaultValue="0") int rank){
		Goods goods = new Goods();
		if(id < 1){
			return error("请传入要操作的栏目编号");
		}
		goods = sqlService.findById(Goods.class, id);
		if(goods == null){
			return error("要操作的栏目不存在");
		}
		
		goods.setRank(rank);
		sqlService.save(goods);
		
		//操作完成后，删除缓存，已达到更新缓存目的
		deleteGoodsCache(goods.getId());
		
		//记录日志
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(), "更改栏目排序", rank+"");
		
		return success();
	}
}
