package com.xnx3.wangmarket.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品详情
 * @author 管雷鸣
 */
@Entity(name="shop_goods_data")
@Table(name = "shop_goods_data")
public class GoodsData implements java.io.Serializable {
	private Integer id;		//对应商品编号，也就是 Goods.id
	private String detail;	//详情内容，富文本编辑区

	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "detail", columnDefinition="mediumtext comment '详情内容，富文本编辑区'")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "GoodsData [id=" + id + ", detail=" + detail + "]";
	}
	
}