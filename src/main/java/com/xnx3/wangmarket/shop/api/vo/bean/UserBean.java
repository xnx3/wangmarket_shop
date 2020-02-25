package com.xnx3.wangmarket.shop.api.vo.bean;

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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
