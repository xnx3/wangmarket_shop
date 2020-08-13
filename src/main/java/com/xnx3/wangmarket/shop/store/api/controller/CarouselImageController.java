package com.xnx3.wangmarket.shop.store.api.controller;

import com.xnx3.BaseVO;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;
import com.xnx3.wangmarket.shop.store.api.vo.CarouselImageListVO;
import com.xnx3.wangmarket.shop.store.api.vo.CarouselImageVO;
import com.xnx3.wangmarket.shop.store.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页轮播图管理
 * @author 刘鹏
 */
@Controller(value="ShopStoreApiCarouselImageController")
@RequestMapping("/shop/store/api/carouselImage/")
public class CarouselImageController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查看轮播图列表
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "/list${api.suffix}" ,method = {RequestMethod.POST})
	public CarouselImageListVO list(HttpServletRequest request) {
		CarouselImageListVO vo = new CarouselImageListVO();

		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_carousel_image");
		sql.appendWhere("storeid = "+getStoreId());
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
		
		vo.setList(list);
		vo.setPage(page);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看轮播图列表");
		return vo;
	}
	
	/**
	 * 获取轮播图信息
	 * @author 刘鹏
	 * @param id 轮播图id
	 */
	@ResponseBody
	@RequestMapping(value="/getCarouselImage${api.suffix}" ,method = {RequestMethod.POST})
	public CarouselImageVO getCarouselImage(HttpServletRequest request,
									 @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		CarouselImageVO vo = new CarouselImageVO();

		if(id != 0) {
			CarouselImage carouselImage = sqlService.findById(CarouselImage.class, id);
			if(carouselImage == null){
				vo.setBaseVO(BaseVO.FAILURE, "要修改的轮播图不存在");
			}
			if(carouselImage.getStoreid() - getStoreId() != 0){
				vo.setBaseVO(BaseVO.FAILURE, "该轮播图不属于你，无法修改");
			}
			vo.setCarouselImage(carouselImage);
			ActionLogUtil.insert(request, getUserId(), "查看轮播图ID为" + id+ "的详情，跳转到编辑页面");
		}else {
			ActionLogUtil.insert(request, getUserId(), "跳转到轮播图编辑页面");
		}
		
		return vo;
		
	}
	
	/**
	 * 添加或修改轮播图
	 * @author 刘鹏
	 * @param carouselImage 接受参数的实体类
	 */
	@ResponseBody
	@RequestMapping(value="/save${api.suffix}" ,method = {RequestMethod.POST})
	public com.xnx3.j2ee.vo.BaseVO save(HttpServletRequest request,CarouselImage carouselImage) {
		Integer id = carouselImage.getId();
		//创建一个实体
		CarouselImage fCarouselImage;
		if(id == null) {
			// 添加
			fCarouselImage = new CarouselImage();
			fCarouselImage.setStoreid(getStoreId());
		}else {
			//修改
			fCarouselImage = sqlService.findById(CarouselImage.class, id);
			if(fCarouselImage == null) {
				return error("根据ID,没查到该轮播图");
			}
			if(fCarouselImage.getStoreid() - getStoreId() != 0){
				return error("轮播图不属于你，无法保存");
			}
		}
		
		//给实体赋值
		fCarouselImage.setName(carouselImage.getName());
		fCarouselImage.setRank(carouselImage.getRank());
		fCarouselImage.setType(carouselImage.getType());
		fCarouselImage.setImgValue(carouselImage.getImgValue());
		fCarouselImage.setImageUrl(carouselImage.getImageUrl());
		
		//保存实体
		sqlService.save(fCarouselImage);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, fCarouselImage.getId(),"Id为" + fCarouselImage.getId() + "的轮播图添加或修改，内容:" + fCarouselImage.toString());
		
		return success();
	}
	
	/**
	 * 删除轮播图
	 * @author 刘鹏
	 * @param id 删除的轮播图id
	 */
	@ResponseBody
	@RequestMapping(value="/delete${api.suffix}",method = {RequestMethod.POST})
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id",defaultValue = "0", required = false) int id) {
		if(id < 1) {
			return error("请传入id参数");
		}
		
		CarouselImage carouselImage = sqlService.findById(CarouselImage.class, id);
		if(carouselImage == null) {
			return error("根据ID,没查到该实体");
		}
		if(carouselImage.getStoreid() - getStoreId() != 0){
			return error("轮播图不属于你，无法删除");
		}
		
		sqlService.delete(carouselImage);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request,id, "删除ID是" + id + "的轮播图", "删除内容:" + carouselImage.toString());
		return success();
	}

}
