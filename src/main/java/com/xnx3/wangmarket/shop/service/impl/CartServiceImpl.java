package com.xnx3.wangmarket.shop.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Goods;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop.service.CartService;
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
	
}
