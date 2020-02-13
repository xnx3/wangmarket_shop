package com.xnx3.wangmarket.shop_storeadmin.controller;

import java.util.List;
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
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.entity.Goods;
import com.xnx3.wangmarket.shop.entity.GoodsData;
import com.xnx3.wangmarket.shop.entity.GoodsImage;


/**
 * 商品分类管理控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreAdminGoodsController")
@RequestMapping("/shop/storeadmin/goods/")
public class GoodsController extends BaseController {
	@Resource
	private SqlService sqlService;
	
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
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"title","typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("id DESC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<Goods> list = sqlService.findBySql(sql,Goods.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品列表");
		return "/appletAdmin/goods/list";
	}
	
	/**
	 * 上传商品图片
	 * @author 关光礼
	 * @param id 上 上传商品id
	 * @param file 上传的图片文件
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/uploadImg${url.suffix}" ,method = {RequestMethod.POST})
	public BaseVO goodsUploadImg(HttpServletRequest request ,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			MultipartFile file) {
		
		// 校验参数
		if(id < 1) {
			return error("ID信息错误");
		}
		// 上传图片
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile("Goods/", file);
		
		if(vo.getResult() == 0) {
			return error("上传失败");
		}
		// 修改 url
		Goods goods = sqlService.findById(Goods.class, id);
		goods.setTitlepic(vo.getUrl());
		sqlService.save(goods);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品上传图片", "上传图片返回路径:" + vo.getUrl());
		return success();
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
			model.addAttribute("item", goods);
			//查找商品描述
			GoodsData goodsData = sqlService.findAloneByProperty(GoodsData.class, "id", id);
			model.addAttribute("data", goodsData);
		}
		ActionLogUtil.insert(request, getUserId(), "查看商品ID为" + (id == 0 ? "":id)+ "的详情，跳转到编辑页面");
		
		return "/appletAdmin/goods/edit";
		
	}
	
	/**
	 * 添加修改商品分类
	 * @author 关光礼
	 * @param Goods 接受参数的实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save${url.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,Goods inputGoods) {
		
		Integer id = inputGoods.getId();
		//创建一个实体
		Goods goods;
		if(id == null) {
			// 添加
			goods = new Goods();
			goods.setIsdelete(Goods.ISDELETE_NORMAL);
			goods.setAddtime(DateUtil.timeForUnix10());
			goods.setSale(0);
			goods.setStoreid(getStoreId());
			goods.setUserBuyRestrict(0);
		}else {
			//修改
			goods = sqlService.findById(Goods.class, id);
			if(goods == null) {
				return error("根据ID,没查到该商品分类");
			}
		}
		//接受时间参数
		
		//给实体赋值
		goods.setAlarmNum(inputGoods.getAlarmNum());
		goods.setFakeSale(inputGoods.getFakeSale());
		goods.setInventory(inputGoods.getInventory());
		goods.setOriginalPrice(inputGoods.getOriginalPrice());
		goods.setPrice(inputGoods.getPrice());
		goods.setPutaway(inputGoods.getPutaway());
		goods.setTitle(inputGoods.getTitle());
		goods.setTypeid(inputGoods.getTypeid());
		goods.setUnits(inputGoods.getUnits());
		goods.setUpdatetime(DateUtil.timeForUnix10());
		goods.setTitlepic(inputGoods.getTitlepic());
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
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, goods.getId(),"Id为" + goods.getId() + "的商品添加或修改，内容:" + goods.toString());
		
		return success();
	}
	
	/**
	 * 删除商品分类
	 * @author 关光礼
	 * @param id 删除商品分类id
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
		goods.setIsdelete(Goods.ISDELETE_DELETE);
		sqlService.save(goods);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "删除ID是" + id + "的商品", "删除内容:" + goods.toString());
		return success();
	}
	
	/**
	 * 跳转添加或修改商品描述页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@RequestMapping("toDetailPage${url.suffix}")
	public String toDescriptionPage(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		if(id < 1) {
			return error(model,"请传入ID信息" );
		}else {
			GoodsData goodsData = sqlService.findById(GoodsData.class, id);
			model.addAttribute("item", goodsData);
			ActionLogUtil.insert(request, id, "查看商品ID为" + id+ "的描述，跳转到编辑页面");
		}
		
		model.addAttribute("id", id);
		return "/appletAdmin/goods/detail";
	}
	
	/**
	 * 添加修改商品描述
	 * @author 关光礼
	 * @param Goods 接受参数的实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveDetail${url.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO saveDetail(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "detail", required = false, defaultValue = "") String detail) {
		
		if(id < 1) {
			return error("请传入ID信息" );
		}
		
		if(detail.trim().equals("")) {
			return error("请传入商品描述信息" );
		}
		GoodsData goodsData = sqlService.findById(GoodsData.class, id);
		
		//判断是否为空，为空创建实体，并设置id信息
		if(goodsData == null) {
			goodsData = new GoodsData();
			goodsData.setId(id);
		}
		//赋值
		goodsData.setDetail(detail);
		sqlService.save(goodsData);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, goodsData.getId(),"Id为" + goodsData.getId() + "的商品描述添加或修改，内容:" + goodsData.toString());
		
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
		return "/appletAdmin/goods/imgList";
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
		sqlService.delete(img);
		//日志记录
				ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品列表图片删除", "删除内容" + img.toString());
		return success();
	}
	
	/**
	 * 上传商品详情里图片列表
	 * @author 关光礼
	 * @param id 上 上传商品id
	 * @param file 上传的图片文件
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/uploadImgList${url.suffix}" ,method = {RequestMethod.POST})
	public BaseVO uploadImgList(HttpServletRequest request ,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "goodId", required = false, defaultValue = "0") int goodId,
			MultipartFile file) {
		

		if(goodId < 1) {
			return error("请传入商品ID信息" );
		}
		// 上传图片
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile("Goods/", file);
		
		if(vo.getResult() == 0) {
			return error("上传失败");
		}
		// 修改 url
		String sql = "SELECT * FROM shop_goods_image WHERE id = " + id + " AND goodsid= " + goodId;
		GoodsImage goodsImage = sqlService.findAloneBySqlQuery(sql, GoodsImage.class);
		if(goodsImage == null) {
			goodsImage = new GoodsImage();
		}
		goodsImage.setImageUrl(vo.getUrl());
		goodsImage.setGoodsid(goodId);
		sqlService.save(goodsImage);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品列表上传图片", "上传图片返回路径:" + vo.getUrl());
		
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
		return "/appletAdmin/goods/editImg";
		
	}
	
	/**
	 * 添加修改商品分类
	 * @author 关光礼
	 * @param Goods 接受参数的实体类
	 * @return
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
		if(id == null) {
			//添加
			img = new GoodsImage();
			img.setGoodsid(inputImg.getGoodsid());
		}else {
			//修改
			// 修改 url
			String sql = "SELECT * FROM shop_goods_image WHERE id = " + id + " AND goodsid= " + inputImg.getGoodsid();
			img = sqlService.findAloneBySqlQuery(sql, GoodsImage.class);
			if(img == null) {
				return error("根据ID,没查到该商品分类");
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
	 * 上传图片接口
	 */
	@RequestMapping(value="uploadImage${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public UploadFileVO uploadImage(Model model,HttpServletRequest request){
		UploadFileVO uploadFileVO = AttachmentUtil.uploadImage("goodsList/", request, "image", 0);
		
		if(uploadFileVO.getResult() == UploadFileVO.SUCCESS){
			//上传成功，写日志
			ActionLogUtil.insert(request, "商品详情里图片上传", uploadFileVO.getPath());
		}
		
		return uploadFileVO;
	}

}
