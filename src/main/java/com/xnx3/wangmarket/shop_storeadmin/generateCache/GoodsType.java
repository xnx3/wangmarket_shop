package com.xnx3.wangmarket.shop_storeadmin.generateCache;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xnx3.j2ee.generateCache.BaseGenerate;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.entity.Goods;

/**
 * 根据商品分类Id去分类名称
 * @author 关光礼
 */
@Component(value="ShopGoodsTypeGenerate")
public class GoodsType extends BaseGenerate {
	
	public GoodsType(){
		typeid();
		putaway();
	}
	
	public void typeid(){
		List<com.xnx3.wangmarket.shop.entity.GoodsType> list = SpringUtil.getSqlService().findBySqlQuery("SELECT * FROM shop_goods_type WHERE isdelete = " + com.xnx3.wangmarket.shop.entity.GoodsType.ISDELETE_NORMAL, com.xnx3.wangmarket.shop.entity.GoodsType.class);
		
		createCacheObject("typeid");
		for (int i = 0; i < list.size(); i++) {
			cacheAdd(list.get(i).getId(), list.get(i).getTitle());
		}
		generateCacheFile();
	}
	
	public void putaway() {
		createCacheObject("putaway");
		cacheAdd(Goods.PUTAWAY_NOT_SELL, "已下架");
		cacheAdd(Goods.PUTAWAY_SELL, "上架中");
		generateCacheFile();
	}
}
