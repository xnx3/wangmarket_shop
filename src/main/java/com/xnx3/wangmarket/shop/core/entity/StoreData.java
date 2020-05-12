package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * store 表的分表，存变长字段
 * @author 管雷鸣
 */
@Entity
@Table(name = "shop_store_data")
public class StoreData implements java.io.Serializable {
	private Integer id;		//对应 store.id
	private String notice;	//店铺的公告内容


	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "notice", columnDefinition="text comment '店铺公告'")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Override
	public String toString() {
		return "StoreData [id=" + id + ", notice=" + notice + "]";
	}

}