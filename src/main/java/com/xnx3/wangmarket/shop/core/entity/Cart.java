package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 某个用户的购物车信息。购物车缓存。当用户退出后，再登录，购物车信息还能有
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_cart")
public class Cart implements java.io.Serializable {
	private Integer id;			//user.id
	private String text;		//购物车信息，json格式，也就是 CartVO 的数据
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "text", columnDefinition="text comment '购物车信息，json格式'")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		return "Cart [id=" + id + ", text=" + text + "]";
	}
	
}