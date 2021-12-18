package com.xnx3.wangmarket.shop.store.controller.api;

import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;
import com.xnx3.wangmarket.shop.store.vo.CarouselImageListVO;
import com.xnx3.wangmarket.shop.store.vo.CarouselImageVO;
import org.springframework.stereotype.Controller;
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
	 * 获取轮播图列表
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @param everyNumber 每页显示多少条数据。取值 1～100，
     *                  <p>最大显示100条数据，若传入超过100，则只会返回100条<p>
     *                  <p>若不传，默认显示15条</p>
	 * @return 若请求成功，则显示轮播图列表
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json" ,method = {RequestMethod.POST})
	public CarouselImageListVO list(HttpServletRequest request,
			@RequestParam(value = "everyNumber", required = false, defaultValue = "15") int everyNumber) {
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
		Page page = new Page(count, everyNumber, request);
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
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @param id 轮播图id
     * @return 若请求成功，则可获取轮播图信息
	 * @author 刘鹏
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/getCarouselImage.json" ,method = {RequestMethod.POST})
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
	 * @param name 轮播图的名字，更多的是备注作用，给自己看的。用户看到的只是图片而已。限制40个字符 <example=1号轮播图>
     * @param rank 排序，数字越小越靠前
     * @param type <ul><li>1:点击后到某个商品上<li>2:打开某个分类，进入分类列表<li>3:点击后打开某个url，也就是打开一个h5页面</ul>
     * @param imgvalue 根据type不同，值也不同<ul><li>type:1 这里是商品id<li>type:2 这里是商品分类的id<li>type:3 这里是跳转到的H5页面的url</ul>
     * @param imageurl 轮播图url，绝对路径
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
     * @return 若请求成功，则可添加修改轮播图          
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="/save.json" ,method = {RequestMethod.POST})
	public BaseVO save(HttpServletRequest request,CarouselImage carouselImage) {
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
	 * 
	 * @param id 删除的轮播图id
	 * @param token 当前操作用户的登录标识 <required>
     *           <p>可通过 <a href="shop.store.api.login.login.json.html">/shop/store/api/login/login.json</a> 取得 </p>
	 * @return 若请求成功，则可以删除轮播图
	 * @author 刘鹏
	 */
	@ResponseBody
	@RequestMapping(value="/delete.json",method = {RequestMethod.POST})
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
