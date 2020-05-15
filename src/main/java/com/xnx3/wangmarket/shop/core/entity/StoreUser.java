package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.User;

/**
 * 商家有哪些用户，也就是哪些用户属于这个商家的。关联 {@link User} 跟 {@link Store}
 * @author 管雷鸣
 */
@Entity
@Table(name = "shop_store_user", indexes={@Index(name="suoyin_index",columnList="userid,storeid")})
public class StoreUser implements Serializable{
	private Integer id;			//自动编号
	private Integer userid;		//用户id，对应 User.id
	private Integer storeid;	//此用户属于哪个商家，是哪个商家的用户
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '用户id，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "storeid", columnDefinition="int(11) comment '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	@Override
	public String toString() {
		return "StoreUser [id=" + id + ", userid=" + userid + ", storeid=" + storeid + "]";
	}
}	
