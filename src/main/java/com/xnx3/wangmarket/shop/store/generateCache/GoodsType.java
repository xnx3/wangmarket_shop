package com.xnx3.wangmarket.shop.store.generateCache;

import java.util.List;
import org.springframework.stereotype.Component;

import com.xnx3.ScanClassUtil;
import com.xnx3.j2ee.generateCache.BaseGenerate;
import com.xnx3.j2ee.util.ConsoleUtil;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.wangmarket.shop.core.entity.Goods;

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
		new Thread(new Runnable() {
			public void run() {
				while(SpringUtil.getApplicationContext() == null){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//当 SpringUtil 被Spring 加载后才会执行
				List<com.xnx3.wangmarket.shop.core.entity.GoodsType> list = SpringUtil.getSqlService().findBySqlQuery("SELECT * FROM shop_goods_type WHERE isdelete = " + com.xnx3.wangmarket.shop.core.entity.GoodsType.ISDELETE_NORMAL, com.xnx3.wangmarket.shop.core.entity.GoodsType.class);
				
				createCacheObject("typeid");
				for (int i = 0; i < list.size(); i++) {
					cacheAdd(list.get(i).getId(), list.get(i).getTitle());
				}
				generateCacheFile();
			}
		}).start();
		
	}
	
	public void putaway() {
		createCacheObject("putaway");
		cacheAdd(Goods.PUTAWAY_NOT_SELL, "已下架");
		cacheAdd(Goods.PUTAWAY_SELL, "上架中");
		generateCacheFile();
	}
}
