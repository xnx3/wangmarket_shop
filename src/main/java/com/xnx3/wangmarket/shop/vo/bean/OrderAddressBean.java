package com.xnx3.wangmarket.shop.vo.bean;

import com.xnx3.wangmarket.shop.entity.OrderAddress;

/**
 * {@link OrderAddress} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class OrderAddressBean {
	private String username;	//收货人用户姓名，限制10个字符
	private String phone;		//收货人手机号，限制13个字符
	private Double longitude;	//经纬度
	private Double latitude;	//经纬度
	private String address;		//详细地址，限制100个字符
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
}
