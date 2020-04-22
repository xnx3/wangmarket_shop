package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.GoodsType;

/**
 * {@link GoodsType} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class GoodsTypeBean implements java.io.Serializable{
	private Integer id;				//分类id，编号
	private String title;		//分类的名称，限20个字符
	private String icon;		//分类的图标、图片，限制100个字符
	
	public GoodsTypeBean() {
	}
	public GoodsTypeBean(GoodsType goodsType) {
		if(goodsType != null){
			setId(goodsType.getId());
			setIcon(goodsType.getIcon());
			setTitle(goodsType.getTitle());
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		if(id == null) {
			this.id = 0;
		}else {
			this.id = id;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title == null) {
			this.title = "";
		}else {
			this.title = title;
		}
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		if(icon == null) {
			this.icon = "";
		}else {
			this.icon = icon;
		}
	}
}

