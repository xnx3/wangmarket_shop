package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.Lang;

/**
 * 使用此需配置src下的systemConfig.xml文件下的sms节点
 */
@Entity
@Table(name = "shop_store_sms_log", indexes={@Index(name="suoyin_index",columnList="code,userid,used,type,addtime,phone,ip,storeid")})
public class StoreSmsLog implements java.io.Serializable {
	/**
	 * 已使用
	 */
	public final static short USED_TRUE = 1;
	
	/**
	 * 未使用
	 */
	public final static short USED_FALSE = 0;
	
	/**
	 * 验证码发出去后，使用的有效期，多长时间之内使用有效。单位：秒，0为不限制时间，只要验证码未用过就可以使用
	 * (在systemConfig.xml的sms节点配置)
	 */
	public static int codeValidity = 0;
	
	/**
	 * 短信同一手机号，某个功能每天发送的条数限制，超过这个条数，这个功能便无法再次发送短信了。
	 * (在systemConfig.xml的sms节点配置)
	 */
	public static int everyDayPhoneNum = 0;
	/**
	 * 同上，只不过这个是针对ip。介于一个wifi有很多终端，都是同一个ip，这个数值可能比较大
	 * (在systemConfig.xml的sms节点配置)
	 */
	public static int everyDayIpNum = 0;	
	
	static{
		everyDayPhoneNum = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.everyDayPhoneNum"), 0);
		everyDayIpNum = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.everyDayIpNum"), 0);
		codeValidity = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.codeValidity"), 0);
	}
	
	private Integer id;
	private String code;		//发送的验证码，6位数字
	private Integer userid;		//哪个用户使用了此验证码
	private Short used;			//是否被使用了
	private String type;		//验证码所属功能类型，限制10位字符串，如 reg 注册    login  登录
	private Integer addtime;	//发送时间
	private String phone;		//发送到的手机号
	private String ip;			//触发发送操作的客户ip地址
	private Integer storeid;	//是哪个商家发送的验证码

	/** default constructor */
	public StoreSmsLog() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 发送的验证码，6位数字
	 * @return
	 */
	@Column(name = "code", columnDefinition="char(6) comment '发送的验证码，6位数字'")
	public String getCode() {
		return this.code;
	}
	/**
	 * 发送的验证码，6位数字
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 哪个用户使用了此验证码
	 * @return
	 */
	@Column(name = "userid", columnDefinition="int(11) comment '哪个用户'")
	public Integer getUserid() {
		return this.userid;
	}
	/**
	 * 哪个用户使用了此验证码
	 * @param userid
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	/**
	 * 是否被使用了
	 * 		<ul>
	 * 			<li> {@link StoreSmsLog#USED_TRUE}：已经使用了 </li>
	 * 			<li> {@link StoreSmsLog#USED_FALSE}：未使用 </li>
	 * 		</ul>
	 * @return
	 */
	@Column(name = "used", columnDefinition="tinyint(2) comment '是否被使用了,1已使用，0未使用'")
	public Short getUsed() {
		return this.used;
	}
	/**
	 * 是否被使用了
	 * 		<ul>
	 * 			<li> {@link SmsLog#USED_TRUE}：已经使用了 </li>
	 * 			<li> {@link SmsLog#USED_FALSE}：未使用 </li>
	 * 		</ul>
	 * @param used
	 */
	public void setUsed(Short used) {
		this.used = used;
	}
	
	/**
	 * 添加时间
	 * @return
	 */
	@Column(name = "addtime", columnDefinition="int(11) comment '添加时间'")
	public Integer getAddtime() {
		return this.addtime;
	}
	/**
	 * 添加时间
	 * @param addtime
	 */
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	/**
	 * 发送到的手机号
	 * @return
	 */
	@Column(name = "phone", columnDefinition="char(11) comment '发送到的手机号'")
	public String getPhone() {
		return phone;
	}
	/**
	 * 发送到的手机号
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 触发发送操作的客户ip地址
	 * @return
	 */
	@Column(name = "ip", columnDefinition="char(22) comment '触发发送操作的客户ip地址'")
	public String getIp() {
		return ip;
	}
	/**
	 * 触发发送操作的客户ip地址
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "type", columnDefinition="char(10) comment '类型'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "storeid", columnDefinition="int(11) comment '哪个店铺的'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}


	@Override
	public String toString() {
		return "StoreSmsLog [id=" + id + ", code=" + code + ", userid=" + userid + ", used=" + used + ", type=" + type
				+ ", addtime=" + addtime + ", phone=" + phone + ", ip=" + ip + ", storeid=" + storeid + "]";
	}
	
}