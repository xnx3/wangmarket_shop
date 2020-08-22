package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.j2ee.entity.User;

/**
 * {@link User} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class UserBean {
	private Integer id;		//用户id
	private String username;	//用户名，限制40字符
	private String head;		//头像,图片文件名，如 29.jpg
	private String nickname;	//昵称
	private String phone;		//手机号
	private Integer regtime;	//用户注册的时间
	private String regip;		//用户注册ip
	
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
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		if(head == null) {
			this.head = "";
		}else {
			this.head = head;
		}
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		if(nickname == null) {
			this.nickname = "";
		}else {
			this.nickname = nickname;
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
	public Integer getRegtime() {
		return regtime;
	}
	public void setRegtime(Integer regtime) {
		if(id == null) {
			this.regtime = 0;
		}else {
			this.regtime = regtime;
		}
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		if(regip.isEmpty()) {
			this.regip = "0.0.0.0";
		}else {
			this.regip = regip;
		}
	}
}