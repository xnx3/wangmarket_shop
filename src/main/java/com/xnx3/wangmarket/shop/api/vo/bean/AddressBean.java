package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Address;

/**
 * {@link Address} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class AddressBean {
	private Integer id;			//自动编号
	private String username;	//收货人用户姓名，限制10个字符
	private String phone;		//收货人手机号，限制13个字符
	private Double longitude;	//经纬度
	private Double latitude;	//经纬度
	private String address;		//详细地址，限制100个字符
	private Short defaultUse;	//是否是默认使用的，1是默认使用的地址，0不是默认使用的。一个用户会有多个收货地址，但一个用户默认的收货地址只有一个
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Short getDefaultUse() {
		return defaultUse;
	}
	public void setDefaultUse(Short defaultUse) {
		this.defaultUse = defaultUse;
	}
}
