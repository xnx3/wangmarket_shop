package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 首页轮播图
 * @author 管雷鸣
 *
 */
@Entity()
@Table(name = "shop_carousel_image", indexes={@Index(name="suoyin_index",columnList="storeid,rank")})
public class CarouselImage extends BaseEntity{
	/**
	 * 1：点击后到某个商品上
	 */
	public final static Short TYPE_PRODUCT=1;
	/**
	 * 2：点击后到某个商品的模块上去
	 */
	public final static Short TYPE_GOODSTYPE=2;
	/**
	 * 3点击后打开某个url，也就是打开一个h5页面
	 */
	public final static Short TYPE_URL=3;
	
	private Integer id;			//自动编号
	private String name;		//轮播图的名字，更多的是备注作用，给自己看的。用户看到的只是图片而已。限制40个字符
	private String imageUrl;	//轮播图url，绝对路径
	private Short type;			//类型<ul><li>1:点击后到某个商品上<li>2:打开某个分类，进入分类列表<li>3:点击后打开某个url，也就是打开一个h5页面</ul>
	private String imgValue; 	//值，根据type不同，值也不同<ul><li>type:1 这里是商品id<li>type:2 这里是商品分类的id<li>type:3 这里是跳转到的H5页面的url</ul>
	private Integer rank;		//排序，数字越小越靠前
	private Integer storeid;	//这个轮播图是哪个商家的，对应 store.id
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name", columnDefinition="char(40) comment '轮播图的名字，更多的是备注作用，给自己看的。用户看到的只是图片而已'")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "image_url", columnDefinition="char(150) comment '轮播图url，绝对路径'")
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Column(name = "type", columnDefinition="tinyint(2) comment '类型，1：点击后到某个商品上，2：打开某个分类，进入分类列表，3点击后打开某个url，也就是打开一个h5页面'")
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	
	@Column(name = "rank", columnDefinition="int(11) comment '排序，数字越小越靠前'")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@Column(name = "img_value", columnDefinition="char(40) comment '值，如url的路径、商品的id'")
	public String getImgValue() {
		return imgValue;
	}
	public void setImgValue(String imgValue) {
		this.imgValue = imgValue;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '这个轮播图是哪个商家的，对应 store.id' default '0'")
	public Integer getStoreid() {
		return storeid;
	}
	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	@Override
	public String toString() {
		return "CarouselImage [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + ", type=" + type
				+ ", imgValue=" + imgValue + ", rank=" + rank + "]";
	}
	
}
