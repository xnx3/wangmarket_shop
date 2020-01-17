package com.xnx3.wangmarket.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Goods;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop.service.CartService;
import com.xnx3.wangmarket.shop.util.GoodsUtil;
import com.xnx3.wangmarket.shop.util.SessionUtil;
import com.xnx3.wangmarket.shop.vo.CartVO;
import com.xnx3.wangmarket.shop.vo.bean.GoodsCart;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

@Service("cartService")
public class CartServiceImpl implements CartService {
	@Resource
	private SqlService sqlService;
	
	@Override
	public StoreCart getStoreCart(int storeid) {
		CartVO cartVO = SessionUtil.getCart();
		if(cartVO == null || storeid == 0){
			return new StoreCart();
		}else{
			//获取某个商店的购物车信息
			StoreCart storeCart = cartVO.getStoreCartMap().get(storeid);
			if(storeCart == null){
				//如果没有，那么new一个
				cartVO.getStoreCartMap().put(storeid, new StoreCart());
			}
			return storeCart;
		}
	}

	public CartVO cart(int goodsid ,int changeNumber){
		if(goodsid <= 0){
			CartVO cartVO = new CartVO();
			cartVO.setBaseVO(CartVO.FAILURE, "请传入要操作的商品编号");
			return cartVO;
		}
		
		//获取当前用户的购物车信息
		CartVO cartVO = getCart();
		
		//数据库中获取商品信息
		Goods goods = sqlService.findById(Goods.class, goodsid);
		if(goods == null){
			cartVO.setBaseVO(CartVO.FAILURE, "操作的商品不存在！");
			return cartVO;
		}
		if(goods.getPutaway() - Goods.PUTAWAY_NOT_SELL == 0){
			cartVO.setBaseVO(CartVO.FAILURE, "操作的商品已下架！");
			return cartVO;
		}
		
		//根据商品信息，获取此商品属于哪个店铺，是要操作哪个店铺的购物车
		StoreCart storeCart = cartVO.getStoreCartMap().get(goods.getStoreid());
		if(storeCart == null){
			storeCart = new StoreCart();
			storeCart.setStore(sqlService.findById(Store.class, goods.getStoreid()));
			cartVO.getStoreCartMap().put(goods.getStoreid(), storeCart);
		}
		
		//获取要操作的商品对象信息
		GoodsCart goodsCart = storeCart.getGoodsCartMap().get(goodsid);
		if(goodsCart == null){
			goodsCart = new GoodsCart();
			goodsCart.setGoods(goods);
			goodsCart.setNumber(0);
			storeCart.getGoodsCartMap().put(goods.getId(), goodsCart);
		}
		
		//记录商品数量变化之前，数量是多少
		int oldGoodsNumber = new Integer(goodsCart.getNumber());
		//商品数量变化,这里只是操作商品购物车的数量，金额变化等在后面在判断
		if(changeNumber > 0){
			//增加操作
			
			//判断增加后的数值，跟商品库存的比较，如果大于库存肯定是不行的
			if(goods.getInventory() - goodsCart.getNumber() - changeNumber < 0){
				cartVO.setBaseVO(CartVO.FAILURE, "增加失败，商品库存不足");
				return cartVO;
			}
			//单独的商品购物车记录重新计算
			goodsCart.setNumber(goodsCart.getNumber() + changeNumber);
		}else{
			//减少操作（0）的情况忽略
			goodsCart.setNumber(goodsCart.getNumber() + changeNumber);
		}
		
		//根据当前该商品购物车的数量，进行更新购物车其他数据
		if(goodsCart.getNumber() > 0 ){
			//大于0，那就是在购物车中还有
			
			/***  计算原本总价、现在的总价，在进行加减，可以避免购物车中加减过程中，商品价格已经更改的情况  ***/
			//该商品购物车中，原本的总价
			double oldGoodsAllMoney = new Double(goodsCart.getMoney()); 
			//该商品购物车中，现在的总价
			goodsCart.setMoney(goods.getPrice() * goodsCart.getNumber());
			
			/** 店铺购物车改动 **/
			//更改店铺购物车数量
			storeCart.setNumber(storeCart.getNumber() + changeNumber);
			//更改店铺购物车的总金额。 先减去原先的，在加上新算出来的，以保证这个单价是数据库中最新的
			storeCart.setMoney(storeCart.getMoney() - oldGoodsAllMoney + goodsCart.getMoney());
			
			/** 总购物车 CartVO 的变动 **/
			//金额变动跟店铺购物车的金额变动一样，都是重新计算该商品的总金额
			cartVO.setMoney(cartVO.getMoney() - oldGoodsAllMoney + goodsCart.getMoney());
			cartVO.setNumber(cartVO.getNumber() + changeNumber);
		}else{
			//商品数量小于等于0，那就是这个商品从购物车中移除了
			
			/** 总购物车 CartVO 的变动 **/
			cartVO.setMoney(cartVO.getMoney() - goodsCart.getMoney());
			cartVO.setNumber(cartVO.getNumber() - oldGoodsNumber);
			
			/*** 商家购物车的变动 ***/
			storeCart.setNumber(storeCart.getNumber() - oldGoodsNumber);	//减去购物车中此商品的数量
			storeCart.setMoney(storeCart.getMoney() - goodsCart.getMoney());	//该店铺购物车中的总金额金额跟随变动
			storeCart.getGoodsCartMap().remove(goodsid);		//将此商品从店铺的购物车减除
			
			//判断一下这个商家购物车中是否还有商品在里面，如果没有商品了，那直接就把这个商城购物车从总购物车中清理掉
			if(storeCart.getGoodsCartMap().size() == 0){
				cartVO.getStoreCartMap().remove(storeCart.getStore().getId());
			}
		}
		
		cartVO.setBaseVO(CartVO.SUCCESS, "success");
		//将最新的购物车记录更新到Session
		SessionUtil.setCart(cartVO);
		return cartVO;
	}

	@Override
	public CartVO getCart() {
		CartVO vo = SessionUtil.getCart();
		if(vo == null){
			vo = new CartVO();
		}
		return vo;
	}

	@Override
	public BaseVO clearStoreCart(int storeid) {
		CartVO cartVO = getCart();
		if(storeid > 0){
			//只是清空某个店铺的购物车
			StoreCart storeCart = cartVO.getStoreCartMap().get(storeid);
			if(storeCart == null){
				//本来就没有的，不用做处理
			}else{
				//这个购物车有数据，要计算出来减去之后的值
				cartVO.setNumber(cartVO.getNumber() - storeCart.getNumber());
				cartVO.setMoney(cartVO.getMoney() - storeCart.getMoney());
				
				//清除掉购物车的数据
				cartVO.getStoreCartMap().remove(storeid);
			}
		}else{
			//清空整个购物车。包含所有店铺的购物车都清理掉
			cartVO = new CartVO();
		}
		
		//将最新的购物车记录更新到Session
		SessionUtil.setCart(cartVO);
		
		return new BaseVO();
	}
	
	@Override
	public BaseVO clearCart(){
		return clearStoreCart(0);
	}

	@Override
	public CartVO refresh(int storeid) {
		CartVO cartVO = SessionUtil.getCart();
		
		if(cartVO == null){
			return new CartVO();
		}
		
		//当前要更新的商品列表
		Map<Integer, GoodsCart> goodsCartMap = new HashMap<Integer, GoodsCart>();	
		if(storeid > 0){
			//获取某个商店的购物车信息
			StoreCart storeCart = cartVO.getStoreCartMap().get(storeid);
			if(storeCart != null){
				goodsCartMap.putAll(storeCart.getGoodsCartMap());
			}
		}else{
			//购物车中所有的商品
			for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
				goodsCartMap.putAll(entry.getValue().getGoodsCartMap());
			}
		}
		if(goodsCartMap.size() == 0){
			//没有需要更新的商品，因为购物车中本身就没有商品
			return cartVO;
		}
		
		
		/**** 查询出数据库中，最新的商品信息 ****/
		StringBuffer goodsSB = new StringBuffer();
		for(Map.Entry<Integer, GoodsCart> entry:goodsCartMap.entrySet()){
			if(goodsSB.length() > 0){
				goodsSB.append(",");
			}
			goodsSB.append(entry.getKey());
		}
		String sql = "SELECT * FROM shop_goods WHERE id IN("+goodsSB.toString()+")";
		List<Goods> goodsList = sqlService.findBySqlQuery(sql, Goods.class);
		//将最新查询出来的list转化为map
		Map<Integer, Goods> goodsMap = new HashMap<Integer, Goods>();
		for (int i = 0; i < goodsList.size(); i++) {
			goodsMap.put(goodsList.get(i).getId(), goodsList.get(i));
		}
		
		/***** 将购物车中商品，用最新查询出来的商品进行替换 *****/
		if(storeid > 0){
			//获取某个商店的购物车信息
			StoreCart storeCart = cartVO.getStoreCartMap().get(storeid);
			for(Map.Entry<Integer, GoodsCart> entry:storeCart.getGoodsCartMap().entrySet()){
				GoodsCart goodsCart = entry.getValue();
				//将最新的商品信息赋予
				Goods goods = goodsMap.get(entry.getKey());
				goodsCart.setGoods(goods);
				goodsCartState(goodsCart, goods);
			}
		}else{
			//购物车中所有的商品
			for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
				for(Map.Entry<Integer, GoodsCart> subentry:entry.getValue().getGoodsCartMap().entrySet()){
					GoodsCart goodsCart = subentry.getValue();
					//将最新的商品信息赋予
					Goods goods = goodsMap.get(subentry.getKey());
					goodsCart.setGoods(goodsMap.get(subentry.getKey()));
					goodsCartState(goodsCart, goods);
				}
			}
		}
		
		return cartVO;
	}
	
	
	/**
	 * 根据从数据库中刚取出来的最新商品信息，来判断用户购物车中的商品当前是否可以正常购买
	 * @param goodsCart 用户购物车的商品数据
	 * @param goods 最新的商品实体类
	 */
	private GoodsCart goodsCartState(GoodsCart goodsCart, Goods goods){
		
		/******* 先判断商品当前是否是已上架 ********/
		if(GoodsUtil.isPutaway(goods)){
			//商品正常上架的，可以正常购买
			goodsCart.setExceptional(0);
			goodsCart.setExceptionalInfo("");
		}else{
			//商品已下架，不可购买
			goodsCart.setExceptional(GoodsCart.EXCEPTIONAL_SOLD_OUT);
			goodsCart.setExceptionalInfo("商品当前已下架不可购买");
			
			//既然已经不可购买了，下面的也没必要在判断了
			return goodsCart;
		}
		
		
		/******* 再判断商品当前的库存数量 ********/
		if(goods.getInventory() < goodsCart.getNumber()){
			//库存比用户购物车中的还少，那用户肯定是不能买的，库存不足
			goodsCart.setExceptional(GoodsCart.EXCEPTIONAL_NOT_INVENTORY);
			goodsCart.setExceptionalInfo("商品当前库存不足");
		}else{
			goodsCart.setExceptional(0);
			goodsCart.setExceptionalInfo("");
		}
		
		return goodsCart;
	}

	@Override
	public CartVO goodsCartSelected(int selected) {
		return goodsCartSelected(0, selected);
	}

	@Override
	public CartVO goodsCartSelected(int goodsid, int selected) {
		CartVO cartVO = SessionUtil.getCart();
		
		//操作所有商品
		for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
			for(Map.Entry<Integer, GoodsCart> subentry:entry.getValue().getGoodsCartMap().entrySet()){
				GoodsCart goodsCart = subentry.getValue();
				if(goodsid == 0){
					//操作所有商品
					goodsCart.setSelected(selected - 1 == 0? 1:0);
				}else{
					if(goodsid > 0 && subentry.getKey() - goodsid == 0){
						//只是操作这一个商品
						goodsCart.setSelected(selected - 1 == 0? 1:0);
						//操作完就可以跳出了。虽然还会在第一层 for 中继续循环，不过循环也消耗不了多少资源
						break;
					}
				}
			}
		}
		
		//更新Session
		SessionUtil.setCart(cartVO);
		return cartVO;
	}
	
}
