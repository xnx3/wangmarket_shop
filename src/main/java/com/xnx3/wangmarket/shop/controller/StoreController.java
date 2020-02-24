package com.xnx3.wangmarket.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop.vo.StoreVO;

/**
 * 店铺相关
 * @author 管雷鸣
 */
@Controller(value="ShopStoreController")
@RequestMapping("/shop/store/")
public class StoreController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	/**
	 * 获取店铺信息， Store 的信息
	 */
	@RequestMapping(value="getStore${url.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public StoreVO getStore(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id){
		StoreVO vo = new StoreVO();
		if(id < 1){
			vo.setBaseVO(StoreVO.FAILURE, "请传入店铺编号");
			return vo;
		}
		
		Store store = sqlService.findById(Store.class, id);
		if(store == null){
			vo.setBaseVO(StoreVO.FAILURE, "要查看的店铺不存在");
			return vo;
		}
		
		vo.setStore(store);
		ActionLogUtil.insert(request, "获取店铺信息", store.toString());
		return vo;
	}
}
