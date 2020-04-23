package com.xnx3.wangmarket.shop.store.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.generateCache.BaseGenerate;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.service.GoodsTypeService;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsTypeBean;


/**
 * 商品分类管理控制器
 * @author 关光礼
 */
@Controller(value="ShopStoreGoodsTypeController")
@RequestMapping("/shop/store/goodsType")
public class GoodsTypeController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private GoodsTypeService goodsTypeService;
	
	/**
	 * 查看商品分类
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_goods_type");
		//查询条件
		sql.appendWhere("isdelete = " + GoodsType.ISDELETE_NORMAL);
		sql.appendWhere("storeid = " + getStoreId());
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"title"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_goods_type", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_goods_type ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<GoodsType> list = sqlService.findBySql(sql,GoodsType.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看商品分类列表");
		return "/shop/store/goodsType/list";
	}
	
	/**
	 * 上传商品分类图片
	 * @author 关光礼
	 * @param id 上 上传商品分类id
	 * @param file 上传的图片文件
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/uploadImg${url.suffix}")
	public BaseVO goodsTypeUploadImg(HttpServletRequest request ,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			MultipartFile file) {
		
		// 校验参数
		if(id < 1) {
			return error("ID信息错误");
		}
		// 上传图片
		String path = Global.ATTACHMENT_FILE_CAROUSEL_IMAGE.replace("{storeid}", getStoreId()+"");
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile(path+"goodsType/", file);
		
		if(vo.getResult() == 0) {
			return error(vo.getInfo());
		}
		// 修改 url
		GoodsType goodsType = sqlService.findById(GoodsType.class, id);
		if(goodsType == null){
			return error("要修改的分类不存在");
		}
		if(goodsType.getStoreid() - getStoreId() != 0){
			return error("该分类不属于你，无法操作。");
		}
		goodsType.setIcon(vo.getUrl());
		sqlService.save(goodsType);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id, "Id为" + id + "的商品分类上传图片", "上传图片返回路径:" + vo.getUrl());
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
		
		if(id != 0) {
			GoodsType goodsType = sqlService.findById(GoodsType.class, id);
			if(goodsType == null){
				return error(model,"要修改的分类不存在");
			}
			if(goodsType.getStoreid() - getStoreId() != 0){
				return error(model,"该分类不属于你，无法操作。");
			}
			model.addAttribute("item", goodsType);
			ActionLogUtil.insert(request, getUserId(), "查看商品分类ID为" + id+ "的详情，跳转到编辑页面");
		}else {
			ActionLogUtil.insert(request, getUserId(), "跳转到商品分类编辑页面");
		}
		
		return "/shop/store/goodsType/edit";
		
	}
	
	/**
	 * 添加修改商品分类
	 * @author 关光礼
	 * @param goodsType 接受参数的实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save${url.suffix}",method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,GoodsType goodsType) {
		
		//System.out.println(getStoreId());
		Integer id = goodsType.getId();
		//创建一个实体
		GoodsType fGoodsType;
		if(id == null) {
			// 添加
			fGoodsType = new GoodsType();
			fGoodsType.setIsdelete(GoodsType.ISDELETE_NORMAL);
			fGoodsType.setStoreid(getStoreId());
		}else {
			//修改
			fGoodsType = sqlService.findById(GoodsType.class, id);
			if(fGoodsType == null){
				return error("要修改的分类不存在");
			}
			if(fGoodsType.getStoreid() - getStoreId() != 0){
				return error("该分类不属于你，无法操作。");
			}
		}
		
		//给实体赋值
		fGoodsType.setRank(goodsType.getRank());
		fGoodsType.setTitle(goodsType.getTitle());
		//判断是否有图片地址，有就赋值
		if(goodsType.getIcon() != null && !goodsType.getIcon().trim().equals("")) {
			fGoodsType.setIcon(goodsType.getIcon());
		}
		//保存实体
		sqlService.save(fGoodsType);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, fGoodsType.getId(),"Id为" + fGoodsType.getId() + "的商品分类添加或修改，内容:" + fGoodsType.toString());
		
		goodsTypeService.clearCache(getStoreId());//清除缓存
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
		
		GoodsType goodsType = sqlService.findById(GoodsType.class, id);
		if(goodsType == null) {
			return error("根据ID,没查到该实体");
		}
		if(goodsType.getStoreid() - getStoreId() != 0){
			return error("该分类不属于你，无法操作。");
		}
		goodsType.setIsdelete(GoodsType.ISDELETE_DELETE);
		sqlService.save(goodsType);
		
		goodsTypeService.clearCache(getStoreId());//清除缓存
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, "删除ID是" + id + "的商品分类", "删除内容:" + goodsType.toString());
		return success();
	}
	
	
	@RequestMapping(value="/getGoodsTypeJs${url.suffix}",method = {RequestMethod.GET})
	public void getGoodsTypeJs(HttpServletRequest request,HttpServletResponse response) {
		List<GoodsTypeBean> list = goodsTypeService.getGoodsType(getStoreId());
		
		BaseGenerate generate = new BaseGenerate();
		generate.createCacheObject("typeid");
		for (int i = 0; i < list.size(); i++) {
			generate.cacheAdd(list.get(i).getId(), list.get(i).getTitle());
		}
		generate.addCommonJsFunction();
		
		//输出
		response.setCharacterEncoding("utf-8");//第一句，设置服务器端编码
		response.setContentType("text/html;charset=utf-8");//第二句，设置浏览器端解码
		try {
			response.getWriter().write(generate.content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
