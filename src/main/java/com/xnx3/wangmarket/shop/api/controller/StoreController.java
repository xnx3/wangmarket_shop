package com.xnx3.wangmarket.shop.api.controller;

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
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.api.vo.StoreVO;

/**
 * 店铺相关
 * @author 管雷鸣
 */
@Controller(value="ShopStoreController")
@RequestMapping("/shop/api/store/")
public class StoreController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;

	/**
	 * 获取店铺信息， Store 的信息
	 * @param storeid 要获取的信息是那个店铺的，店铺的id
	 */
	@RequestMapping(value="getStore${api.suffix}", method = RequestMethod.POST)
	@ResponseBody
	public StoreVO getStore(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid){
		StoreVO vo = new StoreVO();
		if(storeid < 1){
			vo.setBaseVO(StoreVO.FAILURE, "请传入店铺编号");
			return vo;
		}
		
		Store store = sqlService.findById(Store.class, storeid);
		if(store == null){
			vo.setBaseVO(StoreVO.FAILURE, "要查看的店铺不存在");
			return vo;
		}
		
		vo.setStore(store);
		ActionLogUtil.insert(request, "获取店铺信息", store.toString());
		return vo;
	}
}
