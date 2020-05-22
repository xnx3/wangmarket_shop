package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 店铺自己的短信接口设置
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_sms_set")
public class SmsSet extends BaseEntity implements java.io.Serializable{
	
	private Integer id;			//编号，对应 store.id
	private Short useSms;		//是否使用短信发送功能，启用短信接口的短信发送功能。1使用；0不使用。默认不使用。 
	private int uid;			//短信平台登录的uid
	private String password;	//短信平台登录的密码，30个字符内
	
	private int quotaDayIp;		//发送限制，此店铺下某个ip一天最多能发多少条短信，默认30
	private int quotaDayPhone;	//发送限制，此店铺下某个手机号一天最多能发多少条短信。默认五条
	
	public SmsSet() {
		this.useSms = 0;	// 默认不使用
		this.quotaDayPhone = 5;	
		this.quotaDayIp = 30;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "use_sms", columnDefinition="tinyint(2) comment '是否使用短信发送功能，启用短信接口的短信发送功能。1使用；0不使用。默认不使用。 ' default '0'")
	public Short getUseSms() {
		return useSms;
	}

	public void setUseSms(Short useSms) {
		this.useSms = useSms;
	}
	
	@Column(name = "uid", columnDefinition="int(11) comment '短信平台登录的uid' default '0'")
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	@Column(name = "password", columnDefinition="char(30) comment '短信平台登录的密码，30个字符内' default ''")
	public String getPassword() {
		return password;
	}
	
	@Column(name = "quota_day_ip", columnDefinition="int(11) comment '发送限制，此店铺下某个ip一天最多能发多少条短信，默认30' default '30'")
	public int getQuotaDayIp() {
		return quotaDayIp;
	}

	public void setQuotaDayIp(int quotaDayIp) {
		this.quotaDayIp = quotaDayIp;
	}
	
	@Column(name = "quota_day_phone", columnDefinition="int(11) comment '发送限制，此店铺下某个手机号一天最多能发多少条短信。默认五条' default '5'")
	public int getQuotaDayPhone() {
		return quotaDayPhone;
	}

	public void setQuotaDayPhone(int quotaDayPhone) {
		this.quotaDayPhone = quotaDayPhone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SmsSet [id=" + id + ", useSms=" + useSms + ", uid=" + uid + ", password=" + password + ", quotaDayIp="
				+ quotaDayIp + ", quotaDayPhone=" + quotaDayPhone + "]";
	}
	
}