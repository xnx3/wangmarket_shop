package com.xnx3.wangmarket.shop.store.api.controller;
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
import com.xnx3.StringUtil;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.core.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreData;
import com.xnx3.wangmarket.shop.core.service.StoreService;
import com.xnx3.wangmarket.shop.core.vo.StoreVO;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 商家自身信息参数的设置
 * @author 管雷鸣
 */
@Controller(value="ShopStoreApiStorePluginController")
@RequestMapping("/shop/store/api/store/")
public class StoreController extends BaseController {
	@Resource
	private SqlService sqlService;
	@Resource
	private StoreService storeService;
	@Resource
	private SqlCacheService sqlCacheService;
	@Resource
	private UserService userService;

	/**
	 * 获取当前登录的店铺信息
	 */
	@ResponseBody
	@RequestMapping("getStore${api.suffix}")
	public StoreVO getStore(HttpServletRequest request){
		StoreVO vo = new StoreVO();
		
		int storeid = getStoreId();
		Store store = sqlCacheService.findById(Store.class, storeid);
		StoreData storeData = sqlCacheService.findById(StoreData.class, storeid);
		if(storeData == null){
			storeData = new StoreData();
		}
		
		ActionLogUtil.insert(request, "获取当前登录商家的信息", store.toString());
		vo.setStore(store);
		vo.setStoreData(storeData);
		return vo;
	}
	

	/**
	 * 上传商家图标
	 * @author 关光礼
	 * @param storeId 商家id
	 * @param file 上传的图片文件
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImg${api.suffix}",method = {RequestMethod.POST})
	public BaseVO carouselImageUploadImg(HttpServletRequest request ,
			@RequestParam(value = "storeId", required = false, defaultValue = "0") int storeId,
			MultipartFile file) {
		
		// 校验参数
		if(storeId < 1) {
			return error("ID信息错误");
		}
		// 上传图片
		String path = Global.ATTACHMENT_FILE_CAROUSEL_IMAGE.replace("{storeid}", getStoreId()+"");
		UploadFileVO vo = AttachmentUtil.uploadImageByMultipartFile(path, file);
		
		if(vo.getResult() == 0) {
			return error(vo.getInfo());
		}
		// 查找商家
		Store store = sqlService.findById(Store.class, storeId);
		store.setHead(vo.getUrl());
		sqlService.save(store);
		
		//删除store缓存
		deleteStoreCache(store.getId());
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, storeId, "Id为" + storeId + "的商家修改商家图片", "上传图片返回路径:" + vo.getUrl());
		return success();
	}
	

	/**
	 * 商家信息修改
	 * @author 关光礼
	 * @param storeId 商家id
	 * @param name 店铺名字
	 * @param state 状态
	 * @param contacts 联系人
	 * @param phone 电话
	 * @param address 地址
	 * @param province 省会
	 * @param city 城市
	 * @param district 区
	 * @param longitude 经纬度
	 * @param latitude 经纬度
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/save${api.suffix}",method = {RequestMethod.POST})
	public BaseVO save(HttpServletRequest request ,Model model,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "state", required = false, defaultValue = "0") short state,
			@RequestParam(value = "contacts", required = false, defaultValue = "") String contacts,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "address", required = false, defaultValue = "") String address,
			@RequestParam(value = "province", required = false, defaultValue = "") String province,
			@RequestParam(value = "city", required = false, defaultValue = "") String city,
			@RequestParam(value = "longitude", required = false, defaultValue = "0") float longitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "0") float latitude,
			@RequestParam(value = "district", required = false, defaultValue = "") String district) {
		
		//查找商家
		Store store = sqlService.findById(Store.class, getStoreId());
		if(store.getId() - getStoreId() != 0) {
			return error("身份信息错误");
		}
		
		//判断属性是否有值
		if(!name.trim().equals("")) {
			store.setName(StringUtil.filterXss(name));
		}
		
		if(state == 1 || state == 2 ) {
			if(store.getState() == 0) {
				return error("商店在审核中，修改失败");
			}
			store.setState(state);
		}
		
		if(!contacts.trim().equals("")) {
			store.setContacts(StringUtil.filterXss(contacts));
		}
		
		if(!phone.trim().equals("")) {
			store.setPhone(StringUtil.filterXss(phone));
		}
		
		if(!address.trim().equals("")) {
			store.setAddress(StringUtil.filterXss(address));
		}
		
		if(longitude > 0 && latitude > 0) {
			store.setLongitude(longitude);
			store.setLatitude(latitude);
		}
		
		if(!province.trim().equals("")) {
			store.setProvince(province);
		}
		
		if(!city.trim().equals("")) {
			store.setCity(city);
		}
		
		if(!district.trim().equals("")) {
			store.setDistrict(district);
		}
		
		sqlService.save(store);
		model.addAttribute("store", store);
		
		//修改缓存的商家信息
		SessionUtil.setStore(store);
		//删除store缓存
		deleteStoreCache(store.getId());
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, store.getId(), "Id为" + store.getId() + "的商家修改信息", "修改信息:" + store.toString());
		return success();
	}
	

	/**
	 * 修改商家的变长表字段的信息
	 * @param notice 公告
	 */
	@ResponseBody
	@RequestMapping(value = "/saveStoreData${api.suffix}",method = {RequestMethod.POST})
	public BaseVO saveStoreData(HttpServletRequest request ,
			@RequestParam(value = "notice", required = false, defaultValue = "") String notice) {
		//查找商家
		StoreData storeData = sqlService.findById(StoreData.class, getStoreId());
		if(storeData == null){
			storeData = new StoreData();
			storeData.setId(getStoreId());
		}
		if(notice.length() > 0){
			storeData.setNotice(notice);
		}
		
		//保存
		sqlService.save(storeData);
		
		//清理缓存
		sqlCacheService.deleteCacheById(StoreData.class, storeData.getId());
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, storeData.getId(), "Id为" + storeData.getId() + "的商家修改storeData信息");
		return success();
	}
	

	/**
	 * 保存完成后，删除缓存，以达到更新缓存目的
	 */
	private void deleteStoreCache(int storeid){
		sqlCacheService.deleteCacheById(Store.class, storeid);
	}
}