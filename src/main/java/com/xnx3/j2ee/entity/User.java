package com.xnx3.j2ee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表。用户信息、登录信息等都是在这里
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity {
	
	private Integer id;		//用户id
	private String username;	//用户名，限制40字符
	private String email;		//邮箱
	private String password;	//加密后的密码
	private String head;		//头像,图片文件名，如 29.jpg
	private String nickname;	//昵称
	private String authority;	//用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5   目前最多存放10个字符
	private Integer regtime;	//注册时间,时间戳
	private Integer lasttime;	//最后登录时间,时间戳
	private String regip;		//注册ip
	private String lastip;		//最后一次登陆的ip
	private String salt;		//shiro加密使用
	private String phone;		//手机号
	private Integer currency;	//资金，可以是积分、金币、等等站内虚拟货币。具体货币名字在system表，name=CURRENCY_NAME 通过后台－系统设置进行配置
	private Integer referrerid;	//推荐人的用户id。若没有推荐人则默认为0。推荐人可在system表中设置四级下线是否开启以及奖励
	private float money;		//账户可用余额，金钱,RMB，单位：元
	private float freezemoney;	//账户冻结余额，金钱,RMB，单位：元
	private Short isfreeze;		//是否已冻结，1已冻结（拉入黑名单），0正常
	private String sex;			//性别，三个值：男、女、未知
	private String sign;		//用户签名,限制100个字符
	
	private Integer version;

	/** default constructor */
	public User() {
	}
	/**
	 * 手机号
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 手机号
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 用户id
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	/**
	 * 用户id
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 用户名
	 * @return
	 */
	@Column(name = "username", columnDefinition="char(40) comment '用户名，登录使用'")
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * 用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 邮箱
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}
	/**
	 * 邮箱
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 密码，+salt加密后的密码
	 * @return
	 */
	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}
	/**
	 * 密码，+salt加密后的密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 头像,图片文件名，如 29.jpg
	 * @return
	 */
	@Column(name = "head", length = 15)
	public String getHead() {
		return this.head;
	}
	/**
	 * 头像,图片文件名，如 29.jpg
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}
	
	/**
	 * 昵称
	 * @return
	 */
	@Column(name = "nickname", columnDefinition="char(40) comment '用户名昵称，仅仅只是显示'")
	public String getNickname() {
		return this.nickname;
	}
	
	/**
	 * 昵称
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5   目前最多存放10个字符
	 * @return
	 */
	@Column(name = "authority", nullable = false)
	public String getAuthority() {
		return this.authority;
	}
	/**
	 * 用户权限,主要纪录表再user_role表，一个用户可以有多个权限。多个权限id用,分割，如2,3,5   目前最多存放10个字符
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * 注册时间,时间戳
	 * @return
	 */
	@Column(name = "regtime", nullable = false)
	public Integer getRegtime() {
		return this.regtime;
	}
	/**
	 * 注册时间,时间戳
	 * @param regtime
	 */
	public void setRegtime(Integer regtime) {
		this.regtime = regtime;
	}

	@Column(name = "lasttime", nullable = false)
	public Integer getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Integer lasttime) {
		this.lasttime = lasttime;
	}
	/**
	 * 注册ip
	 * @return
	 */
	@Column(name = "regip", nullable = false, length = 15)
	public String getRegip() {
		return this.regip;
	}
	/**
	 * 注册ip
	 * @param regip
	 */
	public void setRegip(String regip) {
		this.regip = regip;
	}
	/**
	 * shiro加密使用
	 * @return
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * shiro加密使用
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * 资金，可以是积分、金币、等等站内虚拟货币。具体货币名字在system表，name=CURRENCY_NAME 通过后台－系统设置进行配置
	 * @return
	 */
	public Integer getCurrency() {
		return currency;
	}
	/**
	 * 资金，可以是积分、金币、等等站内虚拟货币。具体货币名字在system表，name=CURRENCY_NAME 通过后台－系统设置进行配置
	 * @param currency
	 */
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}
	/**
	 * 推荐人的用户id。若没有推荐人则默认为0。推荐人可在system表中设置四级下线是否开启以及奖励
	 * @return
	 */
	public Integer getReferrerid() {
		return referrerid;
	}
	/**
	 * 推荐人的用户id。若没有推荐人则默认为0。推荐人可在system表中设置四级下线是否开启以及奖励
	 * @param referrerid
	 */
	public void setReferrerid(Integer referrerid) {
		this.referrerid = referrerid;
	}
	/**
	 * 最后一次登陆的ip
	 * @return
	 */
	public String getLastip() {
		return lastip;
	}
	/**
	 * 最后一次登陆的ip
	 * @param lastip
	 */
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	/**
	 * 账户可用余额，金钱,RMB，单位：元
	 * @return
	 */
	public float getMoney() {
		return money;
	}
	/**
	 * 账户可用余额，金钱,RMB，单位：元
	 * @param money
	 */
	public void setMoney(float money) {
		this.money = money;
	}
	/**
	 * 账户冻结余额，金钱,RMB，单位：元
	 * @return
	 */
	public float getFreezemoney() {
		return freezemoney;
	}
	/**
	 * 账户冻结余额，金钱,RMB，单位：元
	 * @param freezemoney
	 */
	public void setFreezemoney(float freezemoney) {
		this.freezemoney = freezemoney;
	}
	/**
	 * 是否已冻结
	 * 		<ul>
	 * 			<li> {@link User#ISFREEZE_NORMAL}：正常 </li>
	 * 			<li> {@link User#ISFREEZE_FREEZE}：已冻结（拉入黑名单） </li>
	 * 		</ul>
	 * @return
	 */
	public Short getIsfreeze() {
		return isfreeze;
	}
	/**
	 * 是否已冻结
	 * 		<ul>
	 * 			<li> {@link User#ISFREEZE_NORMAL}：正常 </li>
	 * 			<li> {@link User#ISFREEZE_FREEZE}：已冻结（拉入黑名单） </li>
	 * 		</ul>
	 * @param isfreeze
	 */
	public void setIsfreeze(Short isfreeze) {
		this.isfreeze = isfreeze;
	}
	
	/**
	 * 性别，三个值：男、女、未知
	 * @return 三个值：
	 * 		<ul>
	 * 			<li>男
	 * 			<li>女
	 * 			<li>未知
	 * 		</ul>
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 性别，三个值：男、女、未知
	 * @param sex 值：
	 * 		<ul>
	 * 			<li>男
	 * 			<li>女
	 * 			<li>未知
	 * 		</ul>
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 用户签名,限制50个汉字或100个英文字符
	 * @return
	 */
	public String getSign() {
		return sign;
	}
	/**
	 * 用户签名,限制50个汉字或100个英文字符
	 * @param sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Version
	public Integer getVersion() {
	    return this.version;
	}
	public void setVersion(Integer version) {
	    this.version = version;
	}
}