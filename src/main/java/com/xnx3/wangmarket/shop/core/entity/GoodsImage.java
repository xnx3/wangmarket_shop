package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 商品图片、轮播图片
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_goods_image", indexes={@Index(name="suoyin_index",columnList="rank,goodsid")})
public class GoodsImage implements java.io.Serializable {
	private Integer id;			//自动编号
	private Integer goodsid;	//对应商品id，Goods.id
	private Integer rank;		//图片的排序，数字越小越靠前
	private String imageUrl;	//图片的url绝对路径

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "goodsid", columnDefinition="int(11) comment '对应商品id，Goods.id'")
	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}
	
	@Column(name = "rank", columnDefinition="int(11) comment '图片的排序，数字越小越靠前'")
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@Column(name = "image_url", columnDefinition="char(255) comment '图片的url绝对路径'")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "GoodsImage [id=" + id + ", goodsid=" + goodsid + ", rank=" + rank + ", imageUrl=" + imageUrl + "]";
	}
	
}