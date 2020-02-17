package com.xnx3.wangmarket.shop.vo;

import java.util.HashMap;
import java.util.Map;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.vo.bean.GoodsCart;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

import net.sf.json.JSONObject;

/**
 * 商城购物车，多店铺
 * @author 管雷鸣
 */
public class CartVO extends BaseVO implements java.io.Serializable{
	Map<Integer, StoreCart> storeCartMap;	//每个店铺id编号都有一个自己的购物车。 key:store.id
	private int number;			//该多店铺购物车中所有商品的总数量
	private int money;		//该多店铺购物车中所有商品的总价格	 ，单位是分

	public Map<Integer, StoreCart> getStoreCartMap() {
		if(storeCartMap == null){
			this.storeCartMap = new HashMap<Integer, StoreCart>();
		}
		return storeCartMap;
	}

	public void setStoreCartMap(Map<Integer, StoreCart> storeCartMap) {
		this.storeCartMap = storeCartMap;
	}
	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	/**
	 * 将 goodsCart转为json格式输出
	 * @return
	 */
	public String toJsonString(){
		StringBuffer storeCartMapStringBuffer = new StringBuffer();
		storeCartMapStringBuffer.append("{");
		if(this.storeCartMap != null){
			int i = this.storeCartMap.size();
			for(Map.Entry<Integer, StoreCart> entry : this.storeCartMap.entrySet()){
				storeCartMapStringBuffer.append("\""+entry.getKey()+"\":"+entry.getValue().toJsonString());
				if(--i > 0){
					storeCartMapStringBuffer.append(",");
				}
			}
		}
		storeCartMapStringBuffer.append("}");
		
		return "{"
				+ "\"storeCartMap\":"+storeCartMapStringBuffer.toString()+","
				+ "\"number\":"+this.getNumber()+","
				+ "\"money\":"+this.getMoney()
				+ "}";
	}
}

