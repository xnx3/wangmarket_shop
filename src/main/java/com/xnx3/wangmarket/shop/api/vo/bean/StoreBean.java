package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * {@link Store} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class StoreBean {
	private Integer id;			//店铺编号，自动编号
	private String name;		//店铺名字，限20个字符
	private String head;		//店铺图片，图标，图片的绝对路径
	private String notice;		//店铺公告，限制150个字符
	private Short state;		//店铺状态，0审核中，1营业中，2已打烊
	private String contacts;	//店铺店家联系人，限制10个字符
	private String phone;		//店铺店家联系电话
	private String address;		//店铺地址，限制100个字符
	private Float longitude;	//店铺所在经纬度
	private Float latitude;		//店铺所在经纬度
	private String province;	//店铺所在位置-省
	private String city;		//店铺所在位置-市，城市
	private String district;	//店铺所在位置-市，区，如 寒亭区
	private Integer addtime;	//店铺添加时间，开通时间。10位时间戳
	private Integer sale;		//店铺已出售的商品总数量，总销量。这个一定是真实的，如果要造假数，可以在增加一个字段
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}
}