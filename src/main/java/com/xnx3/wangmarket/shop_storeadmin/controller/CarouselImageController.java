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
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.entity.CarouselImage;

/**
 * 首页轮播图管理
 * @author 关光礼
 */
@Controller(value="ShopStoreAdminCarouselImageController")
@RequestMapping("/shop/storeadmin/carouselImage/")
public class CarouselImageController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查看轮播图列表
	 * @author 关光礼
	 */
	@RequestMapping("/list${url.suffix}")
	public String list(HttpServletRequest request,Model model) {
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_carousel_image");
		//查询条件
		//配置按某个字端搜索内容
		sql.setSearchColumn(new String[] {"name","type"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_carousel_image", sql.getWhere());
		
		// 配置每页显示15条
		Page page = new Page(count, 15, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT * FROM shop_carousel_image ", page);
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
		sql.setDefaultOrderBy("rank ASC");
		
		// 按照上方条件查询出该实体总数 用集合来装
		List<CarouselImage> list = sqlService.findBySql(sql,CarouselImage.class);
		
		// 将信息保存到model中 
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看轮播图列表");
		return "/shop/storeadmin/carouselImage/list";
	}
	
	/**
	 * 上传录播图图片
	 * @author 关光礼
	 * @param slideshowId 上传的录播图信息id
	 * @param file 上传的图片文件
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/uploadImg${url.suffix}",method = {RequestMethod.POST})
	public BaseVO carouselImageUploadImg(HttpServletRequest request ,
			@RequestParam(value = "slideshow_id", required = false, defaultValue = "0") int slideshowId,
			MultipartFile file) {
		
		// 校验参数
		if(slideshowId < 1) {
			return error("ID信息错误");
		}
		// 上传图片
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile("slideshow/", file);
		
		if(vo.getResult() == 0) {
			return error("轮播图上传失败");
		}
		// 修改 url
		CarouselImage carouselImage = sqlService.findById(CarouselImage.class, slideshowId);
		carouselImage.setImageUrl(vo.getUrl());
		sqlService.save(carouselImage);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, slideshowId, "Id为" + slideshowId + "的轮播图上传图片", "上传图片返回路径:" + vo.getUrl());
		return success();
	}
	
	/**
	 * 跳转添加。修改页面
	 * @author 关光礼
	 * @param id 如修改操作，传入修改的数据id，添加测不传参
	 */
	@RequestMapping(value="toAddPage${url.suffix}")
	public String toAddPage(Model model ,HttpServletRequest request,
		@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		if(id != 0) {
			CarouselImage carouselImage = sqlService.findById(CarouselImage.class, id);
			model.addAttribute("item", carouselImage);
			ActionLogUtil.insert(request, getUserId(), "查看轮播图ID为" + id+ "的详情，跳转到编辑页面");
		}else {
			ActionLogUtil.insert(request, getUserId(), "跳转到轮播图编辑页面");
		}
		
		return "/shop/storeadmin/carouselImage/edit";
		
	}
	
	/**
	 * 添加修改轮播图
	 * @author 关光礼
	 * @param CarouselImage 接受参数的实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save${url.suffix}" ,method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,CarouselImage carouselImage) {
		
		Integer id = carouselImage.getId();
		//创建一个实体
		CarouselImage fCarouselImage;
		if(id == null) {
			// 添加
			fCarouselImage = new CarouselImage();
		}else {
			//修改
			fCarouselImage = sqlService.findById(CarouselImage.class, id);
			if(fCarouselImage == null) {
				return error("根据ID,没查到该轮播图");
			}
		}
		
		//给实体赋值
		fCarouselImage.setName(carouselImage.getName());
		fCarouselImage.setRank(carouselImage.getRank());
		fCarouselImage.setType(carouselImage.getType());
		fCarouselImage.setImgValue(carouselImage.getImgValue());
		//保存实体
		sqlService.save(fCarouselImage);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, fCarouselImage.getId(),"Id为" + fCarouselImage.getId() + "的轮播图添加或修改，内容:" + fCarouselImage.toString());
		
		return success();
	}
	
	/**
	 * 删除轮播图
	 * @author 关光礼
	 * @param id 删除的轮播图id
	 */
	@ResponseBody
	@RequestMapping(value="/delete${url.suffix}",method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id",defaultValue = "0", required = false) int id) {
		
		if(id < 1) {
			return error("请传入id参数");
		}
		
		CarouselImage carouselImage = sqlService.findById(CarouselImage.class, id);
		if(carouselImage == null) {
			return error("根据ID,没查到该实体");
		}
		
		sqlService.delete(carouselImage);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request,id, "删除ID是" + id + "的轮播图", "删除内容:" + carouselImage.toString());
		return success();
	}

}
