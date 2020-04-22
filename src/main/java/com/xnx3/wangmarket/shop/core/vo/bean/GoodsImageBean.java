package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.GoodsImage;

/**
 * {@link GoodsImage} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class GoodsImageBean {
	private String imageUrl;	//图片的url绝对路径

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		if(imageUrl == null) {
			this.imageUrl = "";
		}else {
			this.imageUrl = imageUrl;
		}
	}
}
