package com.xnx3.wangmarket.shop.api.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.vo.AddressListVO;
import com.xnx3.wangmarket.shop.core.vo.AddressVO;

/**
 * 用户收货地址
 * <p>一个用户会有多个收货地址，但一个用户默认的收货地址只有一个</p>
 * @author 管雷鸣
 */
@Controller("ShopAddressController")
@RequestMapping("/shop/api/address/")
public class AddressController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	
	/**
	 * 获取当前用户所设定的默认地址
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @author 管雷鸣
	 * @return 默认地址信息
	 */
	@RequestMapping(value="getDefault.json",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO getDefault(HttpServletRequest request) {
		AddressVO vo = new AddressVO();
		User user = getUser();
		
		//查询用户地址信息
		Address address = sqlService.findAloneBySqlQuery("SELECT * FROM shop_address WHERE userid = "+user.getId()+" AND default_use = 1", Address.class);
		vo.setAddress(address);
		
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看地址用户详情");
		return vo;
	}
	
	/**
	 * 地址保存
	 * <p>包含新增地址、修改地址，进行保存时，会通过此接口进行保存<p>
	 * @author 管雷鸣
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param id 要修改的地址id， address.id ，如果这里不传入，或者传入0，则是新增地址
	 * @param username 收货人名字
	 * @param phone 收货人电话
	 * @param address 收货人地址
	 * @param sheng 所在的省,如： 山东省
	 * @param shi 所在的市，如 潍坊市
	 * @param qu 所在的区，如 寒亭区
	 * @param house 具体房间号，如 17号楼2单元202室
	 * @param longitude 经纬度，如 12.223344
	 * @param latitude 经纬度，如 12.223344
	 * @param isDefault 当前地址是否为默认地址，若不传递此参数，默认是保存为非默认地址。 可传入值：
	 * 			<ul>
	 * 				<li>1:将当前地址设为默认地址</li>
	 * 				<li>0:将当前地址设为非默认地址(不传递此参数，默认就是这个)</li>
	 * 			</ul>
	 * @author 管雷鸣
	 * @return 操作结果，是否执行成功
	 */
	@RequestMapping(value="save.json",method = {RequestMethod.POST})
	@ResponseBody
	public BaseVO save(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") int id,
			@RequestParam(value = "username", required = false, defaultValue = "") String username,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "address", required = false, defaultValue = "") String address,
			@RequestParam(value = "sheng", required = false, defaultValue = "") String sheng,
			@RequestParam(value = "shi", required = false, defaultValue = "") String shi,
			@RequestParam(value = "qu", required = false, defaultValue = "") String qu,
			@RequestParam(value = "house", required = false, defaultValue = "") String house,
			@RequestParam(value = "longitude", required = false, defaultValue = "0") Double longitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "0") Double latitude,
			@RequestParam(value = "isDefault", required = false, defaultValue = "") String isDefault){
		User user = getUser();
		
		Address add = null;
		if(id > 0){
			//修改
			add = sqlService.findById(Address.class, id);
			if(add == null){
				return error("修改的地址不存在");
			}
			if(add.getUserid() - user.getId() != 0){
				return error("地址不属于你，无法修改");
			}
		}else{
			//新增
			add = new Address();
			add.setUserid(user.getId());
		}
		add.setAddress(StringUtil.filterXss(address));
		add.setLatitude(latitude);
		add.setLongitude(longitude);
		add.setPhone(StringUtil.filterXss(phone));
		add.setUsername(StringUtil.filterXss(username));
		add.setSheng(StringUtil.filterXss(sheng));
		add.setShi(StringUtil.filterXss(shi));
		add.setQu(StringUtil.filterXss(qu));
		add.setHouse(StringUtil.filterXss(house));
		//判断isDefault是否传入值
		isDefault = isDefault.trim();
		if(isDefault.length() > 0) {
			//默认地址有变动
			if(isDefault.equals("1")){
				//再将传入的地址设为默认
				add.setDefaultUse((short) 1);
			}else if(isDefault.equals("0")){
				// 将该地址设为非默认地址
				add.setDefaultUse((short) 0);
			}
		}
		sqlService.save(add);
		//如果是一起设置了默认地址，那么需要把之前的默认地址全部取消掉
		if(isDefault.equals("1")){
			sqlService.executeSql("UPDATE shop_address SET default_use = 0 WHERE userid = "+user.getId()+" AND default_use = 1 AND id <> "+add.getId());
		}

		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id,"保存收货地址", address.toString());
		
		return success();
	}
	
	/**
	 * 获取用户地址列表
	 * <p>包含默认地址跟其他非默认地址列表<p>
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @author 管雷鸣
	 * @return 地址列表
	 */
	@RequestMapping(value="list.json",method = {RequestMethod.POST})
	@ResponseBody
	public AddressListVO list(HttpServletRequest request) {
		AddressListVO vo = new AddressListVO();
		User user = getUser();
		
		//查询用户地址信息
		List<Address> addressList = sqlService.findBySqlQuery("SELECT * FROM shop_address WHERE userid = " + user.getId(), Address.class);
		
		/*
		 * 找出
		 * 1. 默认地址
		 * 2. 非默认地址的列表
		 */
		List<Address> list = new ArrayList<Address>();	//地址列表，这里面不包含已选中的默认地址
		for (int i = 0; i < addressList.size(); i++) {
			Address address = addressList.get(i);
			if(address.getDefaultUse() != null && address.getDefaultUse() - 1 == 0){
				//是默认的
				vo.setDefaultAddress(address);
			}else{
				//不是默认的，加到list中
				list.add(address);
			}
		}
		
		vo.setAddressList(list);
		
		//日志记录
		ActionLogUtil.insert(request, "获取用户地址列表");
		return vo;
	}
	

	/**
	 * 将某个地址设置为默认
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param id 要设置为默认的地址id， address.id
	 * @author 管雷鸣
	 * @return 操作结果，是否执行成功
	 */
	@RequestMapping(value="setDefault.json",method = {RequestMethod.POST})
	@ResponseBody
	public BaseVO setDefault(HttpServletRequest request,
			@RequestParam(value = "id", required = true, defaultValue = "0") int id) {
		//判断输入参数
		if(id < 1) {
			return error("请传入地址id");
		}
		
		//查找该地址信息
		Address address = sqlService.findById(Address.class, id);
		if(address == null) {
			return error("地址不存在");
		}
		if(address.getUserid() - getUserId() != 0){
			return error("地址不属于你，无权操作");
		}
		
		//先将该用户默认的地址设为不是默认的
		sqlService.executeSql("UPDATE shop_address SET default_use = 0 WHERE userid = "+getUserId()+" AND default_use = 1");
		
		//再将传入的地址设为默认
		address.setDefaultUse((short) 1);
		sqlService.save(address);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id,"设置默认地址", address.toString());
		
		return success();
	}
	
	/**
	 * 获取自己某个地址的详细信息
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param id 要获取的这个地址的id， address.id
	 * @author 管雷鸣
	 * @return 地址信息
	 */
	@RequestMapping(value="getAddress.json",method = {RequestMethod.POST})
	@ResponseBody
	public AddressVO getAddress(HttpServletRequest request,
			@RequestParam(value = "id", required = true, defaultValue = "0") int id) {
		AddressVO vo = new AddressVO();
		
		//判断输入参数
		if(id < 1) {
			vo.setBaseVO(BaseVO.FAILURE, "请传入地址id");
			return vo;
		}
		
		//查找该地址信息
		Address address = sqlService.findById(Address.class, id);
		if(address == null) {
			vo.setBaseVO(BaseVO.FAILURE, "地址不存在");
			return vo;
		}
		if(address.getUserid() - getUserId() != 0){
			vo.setBaseVO(BaseVO.FAILURE, "地址不属于你，无权获取");
			return vo;
		}
		vo.setAddress(address);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id,"获取用户地址信息", address.toString());
		
		return vo;
	}
	
	
	/**
	 * 删除自己的某个地址
	 * @author 管雷鸣
	 * @param token 当前操作用户的登录标识 <required>
	 * 				<p>可通过 <a href="shop.api.login.login.json.html">/shop/api/login/login.json</a> 取得 </p>
	 * @param id 要删除的地址id， address.id
	 * @return 操作结果，是否执行成功
	 */
	@RequestMapping(value="delete.json",method = {RequestMethod.POST})
	@ResponseBody
	public BaseVO delete(HttpServletRequest request,
			@RequestParam(value = "id", required = true, defaultValue = "0") int id) {
		//判断输入参数
		if(id < 1) {
			return error("请传入要删除地址id");
		}
		
		//查找该地址信息
		Address address = sqlService.findById(Address.class, id);
		if(address == null) {
			return error("地址不存在");
		}
		if(address.getUserid() - getUserId() != 0){
			return error("地址不属于你，无权删除");
		}
		sqlService.delete(address);
		
		//日志记录
		ActionLogUtil.insertUpdateDatabase(request, id,"删除用户地址信息", address.toString());
		
		return success();
	}
	
}
