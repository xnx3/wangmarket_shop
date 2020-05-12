package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * {@link Store} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class StoreBean {
	private Integer id;			//店铺编号，自动编号
	private String name;		//店铺名字，限20个字符
	private String head;		//店铺图片，图标，图片的绝对路径
//	private String notice;		//店铺公告，限制150个字符
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
	
	/*
	 * 下面字段是从 StoreData 变长表取出来的
	 */
	private String notice;		//店铺公告 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		if(id == null){
			this.id = 0;
		}else{
			this.id = id;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name == null){
			this.name = "";
		}else{
			this.name = name;
		}
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		if(head == null){
			this.head = "";
		}else{
			this.head = head;
		}
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		if(notice == null){
			this.notice = "";
		}else{
			this.notice = notice;
		}
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		if(state == null){
			//如果为null，那就是未开业
			this.state = Store.STATE_CLOSE;
		}else{
			this.state = state;
		}
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		if(contacts == null){
			this.contacts = "";
		}else{
			this.contacts = contacts;
		}
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if(phone == null){
			this.phone = "";
		}else{
			this.phone = phone;
		}
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		if(address == null){
			this.address = "";
		}else{
			this.address = address;
		}
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		if(longitude == null){
			this.longitude = 0f;
		}else{
			this.longitude = longitude;
		}
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		if(latitude == null){
			this.latitude = 0f;
		}else{
			this.latitude = latitude;
		}
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		if(province == null){
			this.province = "";
		}else{
			this.province = province;
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if(city == null){
			this.city = "";
		}else{
			this.city = city;
		}
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		if(district == null){
			this.district = null;
		}else{
			this.district = district;
		}
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		if(addtime == null){
			this.addtime = 0;
		}else{
			this.addtime = addtime;
		}
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		if(sale == null){
			this.sale = 0;
		}else{
			this.sale = sale;
		}
	}
}