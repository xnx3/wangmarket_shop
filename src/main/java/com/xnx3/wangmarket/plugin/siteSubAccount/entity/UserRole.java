package com.xnx3.wangmarket.plugin.siteSubAccount.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 子账户插件所用，某个用户对应有哪些菜单操作权
 * @author 管雷鸣
 */
@Entity
@Table(name = "plugin_storesubaccount_user_role", indexes={@Index(name="userid_index",columnList="userid,siteid")})
public class UserRole implements java.io.Serializable {

	private Integer id;	
	private Integer userid;	//用户id
	private Integer storeid;	//对应商城id， store.id
	private String menu;	//所管理的功能,比如 template 、 system等


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false )
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "menu", columnDefinition="char(20) comment '所管理的功能'")
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}