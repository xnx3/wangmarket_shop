package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.Lang;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.json.JSONUtil;
import com.xnx3.wangmarket.shop.core.entity.Cart;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.service.CartService;
import com.xnx3.wangmarket.shop.core.vo.CartVO;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsCart;
import com.xnx3.wangmarket.shop.core.vo.bean.StoreCart;
import com.xnx3.wangmarket.shop.api.util.GoodsUtil;
import com.xnx3.wangmarket.shop.api.util.SessionUtil;
import net.sf.json.JSONObject;

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

	public CartVO change(int goodsid ,int changeNumber){
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
		
		//记录商品数量变化之前，数量是多少
		int oldGoodsNumber = new Integer(goodsCart == null? 0:goodsCart.getNumber());
		//商品数量变化,这里只是操作商品购物车的数量，金额变化等在后面在判断
		if(changeNumber > 0){
			//增加操作
			//判断增加后的数值，跟商品库存的比较，如果大于库存肯定是不行的
			if(goods.getInventory() - (oldGoodsNumber + changeNumber) < 0){
				cartVO.setBaseVO(CartVO.FAILURE, "增加失败，商品库存不足");
				return cartVO;
			}
		}else{
			//减少操作（其中减到 0 的情况忽略，下面会进行处理 ）
		}
		if(goodsCart == null){
			goodsCart = new GoodsCart();
			goodsCart.setGoods(goods);
			goodsCart.setNumber(0);
			storeCart.getGoodsCartMap().put(goods.getId(), goodsCart);
		}
		goodsCart.setNumber(goodsCart.getNumber() + changeNumber);
		
		
		//根据当前该商品购物车的数量，进行更新购物车其他数据
		if(goodsCart.getNumber() > 0 ){
			//大于0，那就是在购物车中还有
			
			/***  计算原本总价、现在的总价，在进行加减，可以避免购物车中加减过程中，商品价格已经更改的情况  ***/
			//该商品购物车中，原本的总价
			int oldGoodsAllMoney = new Integer(goodsCart.getMoney()); 
			//该商品购物车中，现在的总价
			goodsCart.setMoney(goods.getPrice() * goodsCart.getNumber());
			
			if(goodsCart.getSelected() - GoodsCart.SELECTED_YES == 0){
				//如果当前商品在购物车中以选中，那么更改数量后，要重新计算当前支付时，汇总的价格跟数量
				
				/** 店铺购物车改动 **/
				//更改店铺购物车数量
				storeCart.setNumber(storeCart.getNumber() + changeNumber);
				//更改店铺购物车的总金额。 先减去原先的，在加上新算出来的，以保证这个单价是数据库中最新的
				storeCart.setMoney(storeCart.getMoney() - oldGoodsAllMoney + goodsCart.getMoney());
				
				/** 总购物车 CartVO 的变动 **/
				//金额变动跟店铺购物车的金额变动一样，都是重新计算该商品的总金额
				cartVO.setMoney(cartVO.getMoney() - oldGoodsAllMoney + goodsCart.getMoney());
				cartVO.setNumber(cartVO.getNumber() + changeNumber);
			}
		}else{
			//商品数量小于等于0，那就是这个商品从购物车中移除了
			
			if(goodsCart.getSelected() - GoodsCart.SELECTED_YES == 0){
				//如果当前商品在购物车中以选中，那么更改数量后，要重新计算汇总的价格跟数量
				
				/** 总购物车 CartVO 的变动 **/
				cartVO.setMoney(cartVO.getMoney() - goodsCart.getMoney());
				cartVO.setNumber(cartVO.getNumber() - oldGoodsNumber);
				
				/*** 商家购物车的变动 ***/
				storeCart.setNumber(storeCart.getNumber() - oldGoodsNumber);	//减去购物车中此商品的数量
				storeCart.setMoney(storeCart.getMoney() - goodsCart.getMoney());	//该店铺购物车中的总金额金额跟随变动
			}
			//将此商品从店铺的购物车减除
			storeCart.getGoodsCartMap().remove(goodsid);		
			
			//判断一下这个商家购物车中是否还有商品在里面，如果没有商品了，那直接就把这个商城购物车从总购物车中清理掉
			if(storeCart.getGoodsCartMap().size() == 0){
				cartVO.getStoreCartMap().remove(storeCart.getStore().getId());
			}
		}
		
		//将最新的购物车记录更新到Session
		setCart(cartVO);
		return cartVO;
	}

	@Override
	public CartVO getCart() {
		CartVO vo = SessionUtil.getCart();
		if(vo == null){
			//购物车中没有信息，那么从数据表的 shop_cart 中取之前的购物车信息。后面量大会用redis代替 shop_cart
			User user = SessionUtil.getUser();
			int userid = user == null ? 0:user.getId();
			Cart cart = sqlService.findById(Cart.class, userid);
			if(cart == null){
				cart = new Cart();
			}
			
			vo = new CartVO();
			if(cart.getText() != null && cart.getText().length() > 1){
				JSONObject json = JSONObject.fromObject(cart.getText());
				vo.setMoney(JSONUtil.getInt(json, "money"));
				vo.setNumber(JSONUtil.getInt(json, "number"));
				
				if(json.get("storeCartMap") != null){
					Map<Integer, StoreCart> storeCartMap = new HashMap<Integer, StoreCart>();
					
					JSONObject storeCartMapJson = json.getJSONObject("storeCartMap");
					if(storeCartMapJson.size() > 0){
						//有店铺购物车数据，那么取出来
						Iterator<String> iterator = storeCartMapJson.keys();
						while(iterator.hasNext()){
							String storeid = iterator.next();
							JSONObject storeCartJson = storeCartMapJson.getJSONObject(storeid);
							
							StoreCart storeCart = new StoreCart();
							storeCart.setMoney(JSONUtil.getInt(storeCartJson, "money"));	//money
							storeCart.setNumber(JSONUtil.getInt(storeCartJson, "number"));	//number
							//store数据
							if(storeCartJson.get("store") != null){
								JSONObject storeJson = JSONObject.fromObject(storeCartJson.getJSONObject("store"));
								if(storeJson != null && !storeJson.toString().equalsIgnoreCase("null")){
									Store store = new Store();
									store.setAddress(JSONUtil.getString(storeJson, "address"));
									store.setAddtime(JSONUtil.getInt(storeJson, "addtime"));
									store.setCity(JSONUtil.getString(storeJson, "city"));
									store.setContacts(JSONUtil.getString(storeJson, "contacts"));
									store.setDistrict(JSONUtil.getString(storeJson, "district"));
									store.setHead(JSONUtil.getString(storeJson, "head"));
									store.setId(JSONUtil.getInt(storeJson, "id"));
									store.setLatitude(Float.parseFloat(JSONUtil.getString(storeJson, "latitude")));
									store.setLongitude(Float.parseFloat(JSONUtil.getString(storeJson, "longitude")));
									store.setName(JSONUtil.getString(storeJson, "name"));
//									store.setNotice(JSONUtil.getString(storeJson, "notice"));
									store.setPhone(JSONUtil.getString(storeJson, "phone"));
									store.setProvince(JSONUtil.getString(storeJson, "province"));
									store.setSale(JSONUtil.getInt(storeJson, "sale"));
									store.setState((short) JSONUtil.getInt(storeJson, "state"));
									store.setUserid(JSONUtil.getInt(storeJson, "userid"));
									storeCart.setStore(store);
								}
							}
							//goodsCartMap
							if(storeCartJson.get("goodsCartMap") != null){
								Map<Integer, GoodsCart> goodsCartMap = new HashMap<Integer, GoodsCart>();
								
								JSONObject goodsCartMapJson = storeCartJson.getJSONObject("goodsCartMap");
								//遍历，取出来
								Iterator<String> goodsIterator = goodsCartMapJson.keys();
								while(goodsIterator.hasNext()){
									String goodsid = goodsIterator.next();
									JSONObject goodsCartJson = goodsCartMapJson.getJSONObject(goodsid);
									GoodsCart goodsCart = new GoodsCart();
									goodsCart.setExceptional(JSONUtil.getInt(goodsCartJson, "exceptional"));
									goodsCart.setExceptionalInfo(JSONUtil.getString(goodsCartJson, "exceptionalInfo"));
									goodsCart.setMoney(JSONUtil.getInt(goodsCartJson, "money"));
									goodsCart.setNumber(JSONUtil.getInt(goodsCartJson, "number"));
									goodsCart.setSelected(JSONUtil.getInt(goodsCartJson, "selected"));
									if(goodsCartJson.get("goods") != null){
										JSONObject goodsJson = goodsCartJson.getJSONObject("goods");
										Goods goods = new Goods();
										goods.setAddtime(JSONUtil.getInt(goodsJson, "addtime"));
										goods.setAlarmNum(JSONUtil.getInt(goodsJson, "alarmNum"));
										goods.setFakeSale(JSONUtil.getInt(goodsJson, "fakeSale"));
										goods.setId(JSONUtil.getInt(goodsJson, "id"));
										goods.setInventory(JSONUtil.getInt(goodsJson, "inventory"));
										goods.setIsdelete((short) JSONUtil.getInt(goodsJson, "isdelete"));
										goods.setOriginalPrice(JSONUtil.getInt(goodsJson, "originalPrice"));
										goods.setPrice(JSONUtil.getInt(goodsJson, "price"));
										goods.setPutaway((short) JSONUtil.getInt(goodsJson, "putaway"));
										goods.setSale(JSONUtil.getInt(goodsJson, "sale"));
										goods.setStoreid(JSONUtil.getInt(goodsJson, "storeid"));
										goods.setTitle(JSONUtil.getString(goodsJson, "title"));
										goods.setTitlepic(JSONUtil.getString(goodsJson, "titlepic"));
										goods.setTypeid(JSONUtil.getInt(goodsJson, "typeid"));
										goods.setUnits(JSONUtil.getString(goodsJson, "units"));
										goods.setUpdatetime(JSONUtil.getInt(goodsJson, "updatetime"));
										goods.setUserBuyRestrict(JSONUtil.getInt(goodsJson, "userBuyRestrict"));
										goods.setVersion(JSONUtil.getInt(goodsJson, "version"));
										goodsCart.setGoods(goods);
									}
									goodsCartMap.put(Lang.stringToInt(goodsid, 0), goodsCart);
								}
								storeCart.setGoodsCartMap(goodsCartMap);
							}
							storeCartMap.put(Lang.stringToInt(storeid, 0), storeCart);
						}
					}
					vo.setStoreCartMap(storeCartMap);
				}
			}
			
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
		setCart(cartVO);
		return new BaseVO();
	}
	
	@Override
	public BaseVO clearCart(){
		return clearStoreCart(0);
	}

	@Override
	public CartVO refresh(int storeid) {
		CartVO cartVO = getCart();
		
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
		
		setCart(cartVO);
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
	public CartVO selected(int selected) {
		CartVO cartVO = getCart();
		
		int allMoney = 0;	//总购物车中，已选商品总金额
		int allNumber = 0;	//总购物车中，已选商品总数量
		
		if(cartVO.getStoreCartMap() == null){
			return cartVO;
		}
		//操作所有商品
		for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
			StoreCart storeCart = entry.getValue();
			int storeMoney = 0;		//当前某个店铺购物车中，已选商品的总金额
			int storeNumber = 0;	//当前某个店铺购物车中，已选商品总数量
			
			for(Map.Entry<Integer, GoodsCart> subentry:storeCart.getGoodsCartMap().entrySet()){
				GoodsCart goodsCart = subentry.getValue();
				goodsCart.setSelected(selected - 1 == 0? 1:0);
				
				if(selected - GoodsCart.SELECTED_YES == 0){
					//选中
					storeMoney = storeMoney + goodsCart.getMoney();
					storeNumber = storeNumber + goodsCart.getNumber();
				}
			}
			storeCart.setMoney(storeMoney);
			storeCart.setNumber(storeNumber);
			
			if(selected - GoodsCart.SELECTED_YES == 0){
				//选中，那么要把所有商城的购物车汇总起来
				allMoney = allMoney + storeMoney;
				allNumber = allNumber + storeNumber;
			}
		}
		
		cartVO.setMoney(allMoney);
		cartVO.setNumber(allNumber);
		
		//更新Session
		setCart(cartVO);
		return cartVO;
	}

	@Override
	public CartVO selectedByGoodsId(int goodsid, int selected) {
		if(goodsid == 0 || goodsid < 0){
			CartVO cartVO = new CartVO();
			cartVO.setBaseVO(CartVO.FAILURE, "请传入商品id");
			return cartVO;
		}
		
		CartVO cartVO = getCart();
		if(cartVO.getStoreCartMap() == null){
			return cartVO;
		}
		
		//遍历所有商品
		for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
			for(Map.Entry<Integer, GoodsCart> subentry:entry.getValue().getGoodsCartMap().entrySet()){
				GoodsCart goodsCart = subentry.getValue();
				if(subentry.getKey() - goodsid == 0){
					//只是操作这一个商品
					goodsCart.setSelected(selected - 1 == 0? 1:0);
					//重新计算费用
					//原本的，改动选中之前的时候，当前店铺的总金额及数量
					int storeMoney_old = new Integer(entry.getValue().getMoney());
					int storeNumber_old = new Integer(entry.getValue().getNumber());
					
					//重新计算好的最新的
					StoreCart newStoreCart = calculateCartStoreSUM(entry.getValue());
					
					//更改总购物车信息
					cartVO.setMoney(cartVO.getMoney() - storeMoney_old + newStoreCart.getMoney());
					cartVO.setNumber(cartVO.getNumber() - storeNumber_old + newStoreCart.getNumber());
					//将最新的店铺购物车，加入总购物车中
					cartVO.getStoreCartMap().put(newStoreCart.getStore().getId(), newStoreCart);
					
					//更新Session
					setCart(cartVO);
					
					//操作完就可以跳出了
					return cartVO;
				}
			}
		}
		
		//这里实际上应该是失败的，因为传入的goodsid在购物车中没找到。这里原样返回了
		return cartVO;
	}
	
	/**
	 * 计算购物车中，某个店铺的购物车中，已选商品的总金额跟总数量.(未选中的商品不会出现在这里面)
	 * @param storeCart 某个店铺的购物车数据
	 * @return {@link StoreCart} 计算好的某个店铺的购物车数据。也就是 storeCart.money 、storeCart.number 重新计算了一遍
	 */
	private StoreCart calculateCartStoreSUM(StoreCart storeCart){
		if(storeCart == null){
			return null;
		}
		int allMoney = 0;	//购物车选中的商品的总金额
		int allNumber = 0;	//购物车选中的商品的总数量
		if(storeCart.getGoodsCartMap() == null){
			return storeCart;
		}
		
		for(Map.Entry<Integer, GoodsCart> entry:storeCart.getGoodsCartMap().entrySet()){
			GoodsCart goodsCart = entry.getValue();
			if(goodsCart.getSelected() - GoodsCart.SELECTED_YES == 0){
				//只有选中的商品，才会进行统计
				allNumber = allNumber + goodsCart.getNumber();
				allMoney = allMoney + goodsCart.getMoney();
			}
		}
		
		storeCart.setMoney(allMoney);
		storeCart.setNumber(allNumber);
		return storeCart;
	}

	@Override
	public CartVO selectedByStoreId(int storeid, int selected) {
		if(storeid == 0 || storeid < 0){
			CartVO cartVO = new CartVO();
			cartVO.setBaseVO(CartVO.FAILURE, "请传入要操作的店铺");
			return cartVO;
		}
		CartVO cartVO = getCart();
		if(cartVO.getStoreCartMap() == null){
			return cartVO;
		}
		//操作所有商品
		for(Map.Entry<Integer, StoreCart> entry:cartVO.getStoreCartMap().entrySet()){
			StoreCart storeCart = entry.getValue();
			if(storeCart.getStore().getId() - storeid == 0){
				//操作的就是这个店铺
				
				int storeMoney_old = new Integer(storeCart.getMoney());		//当前某个店铺购物车中，已选商品的总金额
				int storeNumber_old = new Integer(storeCart.getNumber());	//当前某个店铺购物车中，已选商品总数量
				
				//将这个店铺下所有商品状态改变
				for(Map.Entry<Integer, GoodsCart> subentry:storeCart.getGoodsCartMap().entrySet()){
					GoodsCart goodsCart = subentry.getValue();
					goodsCart.setSelected(selected - 1 == 0? 1:0);
				}
				//重新计算该店铺的商品汇总
				storeCart = calculateCartStoreSUM(storeCart);
				//更新总购物车中的统计
				cartVO.setMoney(cartVO.getMoney() - storeMoney_old + storeCart.getMoney());
				cartVO.setNumber(cartVO.getNumber() - storeNumber_old + storeCart.getNumber());
				//更新购物车中的该商城相关购物车信息
				cartVO.getStoreCartMap().put(storeCart.getStore().getId(), storeCart);
				
				//更新Session
				setCart(cartVO);
				return cartVO;
			}
			
		}
		
		//这里实际上应该是失败的，因为传入的goodsid在购物车中没找到。这里原样返回了
		return cartVO;
	}

	@Override
	public void setCart(CartVO cartVO) {
		cartVO.setBaseVO(CartVO.SUCCESS, "success");
		
		//存入 session
		SessionUtil.setCart(cartVO);
		
		//存入数据表，将其转化为json存储
		String jsonString = cartVO.toJsonString();
		User user = SessionUtil.getUser();
		Cart cart = sqlService.findById(Cart.class, user.getId());
		if(cart == null){
			cart = new Cart();
			cart.setId(user.getId());
		}
		cart.setText(jsonString);
		sqlService.save(cart);
	}
	
	
}
