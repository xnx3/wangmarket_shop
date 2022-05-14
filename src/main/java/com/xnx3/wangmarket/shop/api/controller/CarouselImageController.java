package com.xnx3.wangmarket.shop.api.controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.BaseVO;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;
import com.xnx3.wangmarket.shop.core.vo.CarouselImageVO;

/**
 * 首页的轮播图
 * @author 管雷鸣
 */
@Controller(value="ShopCarouselImageController")
@RequestMapping("/shop/api/carouselImage/")
public class CarouselImageController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 查询轮播图列表
	 * @param storeid 商铺编号，要获取的轮播图是属于哪个商铺的
	 * @author 关光礼
	 * @return 当 result:1 成功时，data 返回值：list：轮播图列表
	 */
	@ResponseBody
	@RequestMapping(value="/list.json",method= {RequestMethod.POST})
	public CarouselImageVO findCarouselImageList(HttpServletRequest request,
			@RequestParam(value = "storeid", required = true, defaultValue = "1") int storeid) {
		CarouselImageVO vo = new CarouselImageVO();
		if(storeid < 1){
			vo.setBaseVO(BaseVO.FAILURE, "请传入storeid参数");
			return vo;
		}
		//查轮播图，按顺序字段查找
		String sql = "SELECT * FROM shop_carousel_image WHERE storeid = "+storeid+" ORDER BY rank ASC";
		List<CarouselImage> list = sqlService.findBySqlQuery(sql, CarouselImage.class);
		//返回实体vo
		vo.setList(list);
		//日志记录
		ActionLogUtil.insert(request, storeid, "查看轮播图列表");
		return vo;
	}

}
