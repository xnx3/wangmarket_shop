package com.xnx3.wangmarket.shop_storeadmin.generateCache;

import org.springframework.stereotype.Component;
import com.xnx3.j2ee.generateCache.BaseGenerate;

/**
 * 轮播图类型
 * @author 关光礼
 */
@Component(value="ShopCarouselImageGenerate")
public class CarouselImage extends BaseGenerate {
	
	public CarouselImage(){
		type();
	}
	
	public void type(){
		createCacheObject("type");
    	cacheAdd(com.xnx3.wangmarket.shop.entity.CarouselImage.TYPE_PRODUCT, "跳到商品");
    	cacheAdd(com.xnx3.wangmarket.shop.entity.CarouselImage.TYPE_URL, "跳到页面");
		generateCacheFile();
	}

}
