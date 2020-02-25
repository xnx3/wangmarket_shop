package com.xnx3.wangmarket.shop.store.generateCache;

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
    	cacheAdd(com.xnx3.wangmarket.shop.core.entity.CarouselImage.TYPE_PRODUCT, "打开一个商品");
    	cacheAdd(com.xnx3.wangmarket.shop.core.entity.CarouselImage.TYPE_URL, "打开一个页面");
    	cacheAdd(com.xnx3.wangmarket.shop.core.entity.CarouselImage.TYPE_GOODSTYPE, "打开某个分类");
		generateCacheFile();
	}

}
