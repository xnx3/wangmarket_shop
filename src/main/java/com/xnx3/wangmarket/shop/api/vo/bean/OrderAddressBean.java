package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.OrderAddress;

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
		if(username == null) {
			this.username = "";
		}else {
			this.username = username;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if(phone == null) {
			this.phone = "";
		}else {
			this.phone = phone;
		}
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		if(longitude == null) {
			this.longitude = 0D;
		}else {
			this.longitude = longitude;
		}
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		if(latitude == null) {
			this.latitude = 0D;
		}else {
			this.latitude = latitude;
		}
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		if(address == null) {
			this.address = "";
		}else {
			this.address = address;
		}
	}
}
