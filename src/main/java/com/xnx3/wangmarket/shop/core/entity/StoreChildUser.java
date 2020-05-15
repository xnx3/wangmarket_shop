package com.xnx3.wangmarket.shop.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.User;

/**
 * 商家子账户功能，原本 {@link User} 的扩展。相当于在User表中又增加了几个字段，只是不破坏原本User表而已，所以又增加了一个数据表
 * @author 管雷鸣
 */
@Entity
@Table(name = "shop_store_child_user")
public class StoreChildUser implements Serializable{
	private Integer id;		//用户id，对应 User.id
	private Integer storeid;	//此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	
}	
