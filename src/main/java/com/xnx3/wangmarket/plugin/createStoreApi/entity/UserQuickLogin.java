package com.xnx3.wangmarket.plugin.createStoreApi.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 用户快速登录
 * @author 管雷鸣
 */
@Entity()
@Table(name = "user_quick_login")
public class UserQuickLogin extends BaseEntity {
	private String id;			//快速登录的唯一码，通过这个码，来判断是哪个用户，从而进行登录
	private Integer userid;		//用户，对应 User.id
	
	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="char(64) comment '快速登录的唯一码，通过这个码，来判断是哪个用户，从而进行登录'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '该订单所属的用户，是哪个用户下的单，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}