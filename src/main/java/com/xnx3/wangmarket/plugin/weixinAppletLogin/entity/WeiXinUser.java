package com.xnx3.wangmarket.plugin.weixinAppletLogin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;

import com.xnx3.j2ee.entity.BaseEntity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 用户表。用户信息、登录信息等都是在这里
 */
@Entity
@Table(name = "plugin_weixinappletlogin_weixin_user", indexes={@Index(name="suoyin_index",columnList="openid,unionid")})
public class WeiXinUser extends BaseEntity {
	private Integer userid;		//对应user.id		
	private String openid;		//用户关注微信公众号后，获取到的用户的openid
	private String unionid;		//用户的unionid
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	@Column(name = "openid", columnDefinition="char(40) comment '用户关注微信公众号后，获取到的用户的openid'")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "unionid", columnDefinition="char(40) comment '用户的unionid'")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "WeiXinUser [userid=" + userid + ", openid=" + openid + ", unionid=" + unionid + "]";
	}
	
	
}