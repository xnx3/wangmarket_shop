package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 收货地址
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_address", indexes={@Index(name="suoyin_index",columnList="longitude,latitude,userid,sheng,shi,qu,default_use")})
public class Address extends BaseEntity {
	private Integer id;			//自动编号
	private String username;	//收货人用户姓名，限制10个字符
	private String phone;		//收货人手机号，限制13个字符
	private Double longitude;	//经纬度
	private Double latitude;	//经纬度
	private String address;		//详细地址，限制150个字符
	private Short defaultUse;	//是否是默认使用的，1是默认使用的地址，0不是默认使用的。一个用户会有多个收货地址，但一个用户默认的收货地址只有一个
	private Integer userid;		//改地址所属用户，属于那个用户的，对应User.id
	
	//v1.3增加
	private String sheng;		//所在的省，如 山东省
	private String shi;			//所在的市，如 潍坊市
	private String qu;			//所在的区，如 寒亭区
	private String house;		//具体房间号，如 17号楼2单元202室
	
	public Address() {
		this.defaultUse = 0;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "username", columnDefinition="char(10) comment '收货人用户姓名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "phone", columnDefinition="char(13) comment '收货人手机号'")
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
	
	@Column(name = "address", columnDefinition="char(150) comment '具体地址'")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '改地址所属用户，属于那个用户的，对应User.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "default_use", columnDefinition="tinyint(2) comment '是否是默认使用的，1是默认使用的地址，0不是默认使用的。一个用户会有多个收货地址，但一个用户默认的收货地址只有一个' default '0'")
	public Short getDefaultUse() {
		return defaultUse;
	}

	public void setDefaultUse(Short defaultUse) {
		this.defaultUse = defaultUse;
	}
	
	@Column(name = "sheng", columnDefinition="char(20) comment '所在的省，如 山东省' default ''")
	public String getSheng() {
		return sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}
	
	@Column(name = "shi", columnDefinition="char(20) comment '所在的市，如 潍坊市' default ''")
	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	@Column(name = "qu", columnDefinition="char(20) comment '所在的区，如 寒亭区' default ''")
	public String getQu() {
		return qu;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}
	
	@Column(name = "house", columnDefinition="char(80) comment '具体房间号，如 17号楼2单元202室' default ''")
	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", username=" + username + ", phone=" + phone + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", address=" + address + ", defaultUse=" + defaultUse + ", userid="
				+ userid + ", sheng=" + sheng + ", shi=" + shi + ", qu=" + qu + ", house=" + house + "]";
	}
	
}