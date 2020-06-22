package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 订单内的商品
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_order_address")
public class OrderAddress {
	private Integer id;				//自动对应 order.id
	private String username;	//收货人用户姓名，限制10个字符
	private String phone;		//收货人手机号，限制13个字符
	private Double longitude;	//经纬度
	private Double latitude;	//经纬度
	private String address;		//详细地址，限制100个字符

	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "username", columnDefinition="varchar(50) comment '收货人用户姓名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "phone", columnDefinition="varchar(100) comment '收货人手机号'")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "longitude", columnDefinition="double(9,6) comment '经纬度'")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "latitude", columnDefinition="double(9,6) comment '经纬度'")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "address", columnDefinition="varchar(1000) comment '具体地址'")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "OrderAddress [id=" + id + ", username=" + username + ", phone=" + phone + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", address=" + address + "]";
	}
	
}
