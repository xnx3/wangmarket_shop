package com.xnx3.wangmarket.shop.api.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.CarouselImage;

/**
 * {@link CarouselImage} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class CarouselImageBean {
	private String name;		//轮播图的名字，更多的是备注作用，给自己看的。用户看到的只是图片而已。限制40个字符
	private String imageUrl;	//轮播图url，绝对路径
	private Short type;			//类型，1：点击后到某个商品上，2：打开某个分类，进入分类列表，3点击后打开某个url，也就是打开一个h5页面
	private String imgValue; 	//值，如url的路径、商品的id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getImgValue() {
		return imgValue;
	}
	public void setImgValue(String imgValue) {
		this.imgValue = imgValue;
	}
}
