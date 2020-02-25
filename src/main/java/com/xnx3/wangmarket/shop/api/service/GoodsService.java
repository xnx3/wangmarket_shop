package com.xnx3.wangmarket.shop.api.service;

import com.xnx3.wangmarket.shop.api.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.api.vo.GoodsListVO;
import com.xnx3.wangmarket.shop.api.vo.GoodsTypeListVO;

/**
 * 商品相关
 * @author 管雷鸣
 */
public interface GoodsService {
	
	/**
	 * 获取某个店铺的商品分类
	 * @param storeid 店铺id，对应 Store.id ,要获取的是哪个店铺的商品分类
	 * @return {@link GoodsTypeListVO} 若不存在，则会new一个返回过来，不会存在null的情况
	 */
	public GoodsTypeListVO getStoreGoodsType(int storeid);
	
	/**
	 * 获取商品列表
	 * @param storeid 要获取哪个商铺的商品，这里是商店的ID，Store.id。  如果传入0，则是查询所有商铺的商品
	 * @param typeid 要查询商铺中，哪个分类的商品。如果传入0，则是查询这个商店中所有的商品
	 * @param orderBy 商品排序方式
	 * 				<ul>
	 * 					<li>0:默认排序方式，也就是根据销量由高往低来排序</li>
	 * 					<li>1:按总销量由高往低来排序</li>
	 * 					<li>2:最新商品，按发布时间，由高往低来排序，最后发布的商品在最前面</li>
	 * 				</ul>
	 * @return {@link GoodsListVO}
	 */
	public GoodsListVO getGoodsList(int storeid, int typeid, int orderBy);
	
	/**
	 * 获取某个商品的商品详情，包括商品信息、详情信息等。商品详情查看页面使用到
	 * @param goodsid 要查看的商品的id编号
	 * @return {@link GoodsDetailsVO}
	 */
	public GoodsDetailsVO getGoodsDetail(int goodsid);
	
}
