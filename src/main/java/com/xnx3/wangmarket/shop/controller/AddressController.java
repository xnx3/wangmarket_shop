package com.xnx3.wangmarket.shop.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.StringUtil;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.wangmarket.shop.entity.Address;
import com.xnx3.wangmarket.shop.vo.AddressVO;

/**
 * 用户收货地址相关
 * @author 关光礼
 */
@Controller("ShopAddressController")
@RequestMapping("/shop/address/")
public class AddressController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	//创建返回对象
	AddressVO vo = new AddressVO();
	
	/**
	 *查找地址信息
	 * @author 关光礼
	 * @return ddressVO
	 */
	@RequestMapping(value="/selectAddress${url.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO selectAddress(HttpServletRequest request) {
		
		//查询用户地址信息
		Address address = sqlService.findAloneByProperty(Address.class, "userid",getUserId());
		vo.setAddress(address);
		
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看地址用户详情");
		return vo;
	}
	
	/**
	 * 用户保存地址
	 * @author 关光礼
	 * @param username 收货人名字
	 * @param phone 收货人电话
	 * @param inputaddress 收货人地址
	 * @return AddressVO
	 */
	@RequestMapping(value="/save${url.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO save(HttpServletRequest request,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "inputaddress", required = false, defaultValue = "") String inputaddress){
		
		//判断参数
		if(username.trim().equals("")) {
			vo.setBaseVO(AddressVO.FAILURE,"请传入收货人用户名");
		}
		if(phone.trim().equals("")) {
			vo.setBaseVO(AddressVO.FAILURE,"请传入收货人电话");
		}
		if(inputaddress.trim().equals("")) {
			vo.setBaseVO(AddressVO.FAILURE,"请传入收货人地址");
		}
		
		//创建地址实体，并赋值
		Address address;
		address = sqlService.findAloneByProperty(Address.class, "userid",getUserId());
		//先查找用户是否有地址信息
		if(address == null) {
			address = new Address();
			address.setUserid(getUserId());
		}
		
		address.setAddress(StringUtil.filterXss(inputaddress));
		address.setPhone(StringUtil.filterXss(phone));
		address.setUsername(StringUtil.filterXss(username));
		sqlService.save(address);
		
		vo.setResult(AddressVO.SUCCESS);
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, address.getId(),"ID是" +  address.getId() + "的地址信息修改", "修改内容:" + address.toString());
		
		return vo;
	}
	
	/**
	 * 查询用户地址列表
	 * @author 关光礼
	 * @return AddressVO
	 */
	@RequestMapping(value="/add${url.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO list(HttpServletRequest request) {
		
		//查询用户地址信息
		String sql = "SELECT * FROM shop_address WHERE userid = " + getUserId();
		List<Address> list = sqlService.findBySqlQuery(sql, Address.class);
		
		vo.setAddressList(list);
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看地址列表");
		return vo;
	}
	
	@RequestMapping(value="/delete${url.suffix}",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO delete(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
		
		//判断输入参数
		if(id < 1) {
			vo.setBaseVO(AddressVO.FAILURE, "请传入要删除地址id");
			return vo;
		}
		
		//查找该地址信息
		Address address = sqlService.findById(Address.class, id);
		if(address == null) {
			vo.setBaseVO(AddressVO.FAILURE, "根据ID，未查到该地址");
			return vo;
		}
		sqlService.delete(address);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id,"删除ID是" + id + "的地址信息", "删除内容:" + address.toString());
		
		vo.setResult(AddressVO.SUCCESS);
		return vo;
	}
	
	
	
}
