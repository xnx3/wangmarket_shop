package com.xnx3.wangmarket.shop.core.vo.bean;

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
	
	//v1.3增加
	private String sheng;		//所在的省，如 山东省
	private String shi;			//所在的市，如 潍坊市
	private String qu;			//所在的区，如 寒亭区
	private String house;		//具体房间号，如 17号楼2单元202室
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		if(id == null) {
			this.id = 0;
		}else {
			this.id = id;
		}
	}
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
			this.address = "" ;
		}else {
			this.address = address;
		}
	}
	public Short getDefaultUse() {
		return defaultUse;
	}
	public void setDefaultUse(Short defaultUse) {
		if(defaultUse == null) {
			this.defaultUse = Address.ISFREEZE_NORMAL;
		}else {
			this.defaultUse = defaultUse;
		}
	}
	public String getSheng() {
		return sheng;
	}
	public void setSheng(String sheng) {
		if(sheng == null){
			this.sheng = "";
		}else{
			this.sheng = sheng;
		}
	}
	public String getShi() {
		return shi;
	}
	public void setShi(String shi) {
		if(shi == null){
			this.shi = "";
		}else{
			this.shi = shi;
		}
	}
	public String getQu() {
		return qu;
	}
	public void setQu(String qu) {
		if(qu == null){
			this.qu = "";
		}else{
			this.qu = qu;
		}
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		if(house == null){
			this.house = "";
		}else{
			this.house = house;
		}
	}
}
