package com.xnx3.wangmarket.shop.store.controller;
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
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.wangmarket.shop.Global;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.store.util.SessionUtil;

/**
 * 代理后台
 * @author 管雷鸣
 */
@Controller(value="ShopStoreIndexPluginController")
@RequestMapping("/shop/store/index/")
public class IndexController extends BaseController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 登录成功后的首页
	 */
	@RequestMapping("index${url.suffix}")
	public String index(HttpServletRequest request,Model model){
		return "/shop/store/index/index";
	}
	
	/**
	 * 登录成功后的欢迎页面
	 */
	@RequestMapping("welcome${url.suffix}")
	public String welcome(HttpServletRequest request,Model model){
		Store store = sqlService.findById(Store.class, getStoreId());
		
		model.addAttribute("store", store);
		return "/shop/store/index/welcome";
	}
	
	/**
	 * 上传商家图标
	 * @author 关光礼
	 * @param storeId 商家id
	 * @param file 上传的图片文件
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@ResponseBody
	@RequestMapping(value = "/uploadImg${url.suffix}",method = {RequestMethod.POST})
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
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, storeId, "Id为" + storeId + "的商家修改商家图片", "上传图片返回路径:" + vo.getUrl());
		return success();
	}
	
	/**
	 * 跳转到编辑区域信息
	 * @author 关光礼
	 * @param storeId 商家id
	 * @param type 1是修改经纬度，2是修改纬度
	 */
	//@RequiresPermissions("slideshowUploadImg")
	@RequestMapping(value = "/toEditPage${url.suffix}")
	public String toEditPage(HttpServletRequest request ,Model model,
			@RequestParam(value = "type", required = false, defaultValue = "0") int type) {
		// 查找商家
		Store store = sqlService.findById(Store.class, getStoreId());
		if(store == null || store.getId() - getStoreId() != 0) {
			return error(model,"商家信息异常");
		}
		model.addAttribute("item", store);
		model.addAttribute("type", type);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, store.getId(), "Id为" + store.getId() + "的商家的区域信息编辑页面");
		return "/shop/store/index/edit";
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
	@RequestMapping(value = "/save${url.suffix}",method = {RequestMethod.POST})
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
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, store.getId(), "Id为" + store.getId() + "的商家修改信息", "修改信息:" + store.toString());
		return success();
	}
	
}