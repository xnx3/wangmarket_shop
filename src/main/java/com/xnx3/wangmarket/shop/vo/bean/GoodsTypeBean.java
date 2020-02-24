package com.xnx3.wangmarket.shop.vo.bean;

import com.xnx3.wangmarket.shop.entity.GoodsType;

/**
 * {@link GoodsType} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class GoodsTypeBean{
	private int id;				//分类id，编号
	private String title;		//分类的名称，限20个字符
	private String icon;		//分类的图标、图片，限制100个字符
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}

