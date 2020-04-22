package com.xnx3.wangmarket.shop.core.vo.bean;

import java.util.HashMap;
import java.util.Map;
import com.xnx3.wangmarket.shop.core.entity.Store;
import net.sf.json.JSONObject;

/**
 * 商城购物车，某个店铺的
 * @author 管雷鸣
 */
public class StoreCart implements java.io.Serializable{
	private Map<Integer, GoodsCart> goodsCartMap;	//该店铺购物车的商品列表。 key:goods.id 
	private Integer number;			//该店铺购物车中商品的总数量
	private Integer money;			//该店铺购物车中商品的总价格，单位是分
	private StoreBean store;		//当前店铺信息。适用于多商铺一块结算使用。
	
	public StoreCart() {
		this.number = 0;
		this.money = 0;
	}
	
	public Map<Integer, GoodsCart> getGoodsCartMap() {
		if(goodsCartMap == null){
			this.goodsCartMap = new HashMap<Integer, GoodsCart>();
		}
		return goodsCartMap;
	}

	public void setGoodsCartMap(Map<Integer, GoodsCart> goodsCartMap) {
		if(goodsCartMap == null) {
			this.goodsCartMap = new HashMap<Integer, GoodsCart>();
		}else {
			this.goodsCartMap = goodsCartMap;
		}
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		if(number == null) {
			this.number = 0;
		}else {
			this.number = number;
		}
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		if(money == null) {
			this.money = 0;
		}else {
			this.money = money;
		}
	}

	public StoreBean getStore() {
		return store;
	}

	public void setStore(Store store) {
		StoreBean storeBean = new StoreBean();
		if(store != null){
			storeBean.setAddress(store.getAddress());
			storeBean.setAddtime(store.getAddtime());
			storeBean.setCity(store.getCity());
			storeBean.setContacts(store.getContacts());
			storeBean.setDistrict(store.getDistrict());
			storeBean.setHead(store.getHead());
			storeBean.setId(store.getId());
			storeBean.setLatitude(store.getLatitude());
			storeBean.setLongitude(store.getLongitude());
			storeBean.setName(store.getName());
			storeBean.setNotice(store.getNotice());
			storeBean.setPhone(store.getPhone());
			storeBean.setProvince(store.getProvince());
			storeBean.setSale(store.getSale());
			storeBean.setState(store.getState());
		}
		this.store = storeBean;
	}
	
	/**
	 * 将 goodsCart转为json格式输出
	 * @return
	 */
	public String toJsonString(){
		StringBuffer goodsCartMapStringBuffer = new StringBuffer();
		goodsCartMapStringBuffer.append("{");
		if(this.goodsCartMap != null){
			int i = this.goodsCartMap.size();
			for(Map.Entry<Integer, GoodsCart> entry : this.goodsCartMap.entrySet()){
				goodsCartMapStringBuffer.append("\""+entry.getKey()+"\":"+entry.getValue().toJsonString());
				if(--i > 0){
					goodsCartMapStringBuffer.append(",");
				}
			}
		}
		goodsCartMapStringBuffer.append("}");
		
		String storeJson = null;
		if(this.store != null){
			storeJson = JSONObject.fromObject(this.store).toString();
		}
		
		return "{"
				+ "\"goodsCartMap\":"+goodsCartMapStringBuffer.toString()+","
				+ "\"number\":"+this.getNumber()+","
				+ "\"money\":"+this.getMoney()+","
				+ "\"store\":"+storeJson+""
				+ "}";
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
