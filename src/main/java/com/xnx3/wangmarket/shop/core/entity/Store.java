package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 店铺信息。商城中可以有多个店铺，这个店铺就类似于淘宝店的性质
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_store", indexes={@Index(name="suoyin_index",columnList="userid,state,longitude,latitude,province,city,district,sale")})
public class Store implements java.io.Serializable {
	/**
	 * 店铺状态：审核中
	 */
	public final static Short STATE_AUDIT = 0;
	/**
	 * 店铺状态：营业中
	 */
	public final static Short STATE_OPEN = 1;
	/**
	 * 店铺状态：未开张，未营业
	 */
	public final static Short STATE_CLOSE = 2;
	
	private Integer id;			//店铺编号，自动编号
	private String name;		//店铺名字，限20个字符
	private Integer userid;		//店铺所属用户，哪个用户创建的，对应 User.id
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
	
	private Integer version;	//乐观锁
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name", columnDefinition="char(20) comment '店铺名字'")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '店铺所属用户，哪个用户创建的，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "head", columnDefinition="char(100) comment '店铺图片，图标，图片的绝对路径'")
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	@Column(name = "notice", columnDefinition="char(150) comment '店铺公告'")
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	@Column(name = "state", columnDefinition="tinyint(2) comment '店铺状态，0审核中，1营业中，2已打烊'")
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
	@Column(name = "contacts", columnDefinition="char(10) comment '店铺联系人'")
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Column(name = "phone", columnDefinition="char(13) comment '店铺店家联系电话'")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "address", columnDefinition="char(100) comment '店铺地址'")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "longitude", columnDefinition="double(6,6) comment '店铺经纬度'")
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "latitude", columnDefinition="double(6,6) comment '店铺经纬度'")
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "province", columnDefinition="char(15) comment '店铺所在位置-省'")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "city", columnDefinition="char(20) comment '店铺所在位置-市'")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "district", columnDefinition="char(20) comment '店铺所在位置-区'")
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '店铺添加时间，10为时间戳'")
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "sale", columnDefinition="int(11) comment '店铺已出售的商品总数量，总销量。这个一定是真实的，如果要造假数，可以在增加一个字段' default '0'")
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}

	@Version
	@Column(name = "version", columnDefinition="int(11) comment '乐观锁' default 0")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", userid=" + userid + ", head=" + head + ", notice=" + notice
				+ ", state=" + state + ", contacts=" + contacts + ", phone=" + phone + ", address=" + address
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", province=" + province + ", city=" + city
				+ ", district=" + district + ", addtime=" + addtime + ", sale=" + sale + "]";
	}
	
}