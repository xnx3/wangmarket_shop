package com.xnx3.wangmarket.shop.controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.entity.CarouselImage;
import com.xnx3.wangmarket.shop.vo.CarouselImageVO;

/**
 * 首页的轮播图
 */
@Controller(value="ShopCarouselImageController")
@RequestMapping("/shop/carouselImage/")
public class CarouselImageController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查询轮播图列表
	 * @author 关光礼
	 * @return 当 result:1 成功时，data 返回值：list：轮播图列表
	 */
	@ResponseBody
	@RequestMapping(value="/list${url.suffix}",method= {RequestMethod.POST,RequestMethod.GET})
	public CarouselImageVO findCarouselImageList(HttpServletRequest request) {
		
		//查轮播图，按顺序字段查找
		String sql = "SELECT * FROM shop_carousel_image ORDER BY rank ASC";
		List<CarouselImage> list = sqlService.findBySqlQuery(sql, CarouselImage.class);
		
		//返回实体vo
		CarouselImageVO vo = new CarouselImageVO();
		vo.setList(list);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看轮播图列表");
		return vo;
	}

}
