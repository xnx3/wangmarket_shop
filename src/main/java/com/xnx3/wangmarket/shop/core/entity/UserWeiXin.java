package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * User 表中， 扩展的微信相关字段。用户的openid等
 * @author 管雷鸣
 *
 */
@Entity
@Table(name = "user_weixin", indexes={@Index(name="suoyin_index",columnList="storeid,unionid,userid")})
public class UserWeiXin implements java.io.Serializable{
	private String openid;		//用户在微信的openid
	private Integer userid;		//对应user.id	
	private Integer storeid;	//对应store.id，用户是通过哪个店铺注册获取的openid。如果店铺用的服务商的公众号，那么这里是0
	private String unionid;		//用户的unionid
	
	@Id
	@Column(name = "openid", columnDefinition="char(40) comment '用户在微信公众号的openid'")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '对应user.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	

	@Column(name = "unionid", columnDefinition="char(40) comment '用户的unionid'")
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '对应store.id，用户是通过哪个店铺注册获取的openid。如果店铺用的服务商的公众号，那么这里是0'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	@Override
	public String toString() {
		return "UserWeiXin [openid=" + openid + ", userid=" + userid + ", storeid=" + storeid + ", unionid=" + unionid
				+ "]";
	}
	
}
