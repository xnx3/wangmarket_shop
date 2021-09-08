package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Goods;
import net.sf.json.JSONObject;

/**
 * 购物车中的单个商品,某个商品的购物车信息
 * @author 管雷鸣
 */
public class GoodsCart implements java.io.Serializable{
	/**
	 * 购物车的商品状态异常：库存不足
	 */
	public static final int EXCEPTIONAL_NOT_INVENTORY = 1;
	/**
	 * 购物车的商品状态异常：当前商品状态不可购买
	 */
	public static final int EXCEPTIONAL_SOLD_OUT = 2;
	
	/**
	 * 当前商品是否在购物车中被已选择。1已选择(默认)
	 */
	public static final int SELECTED_YES = 1;
	/**
	 * 当前商品是否在购物车中被已选择。0未选择
	 */
	public static final int SELECTED_NO = 0;
	
	private Goods goods;	//当前商品的信息
	private Integer number;		//此种商品数量，加入购物车中的数量
	private Integer money;		//此种商品加入购物车中的总金额，单位是分。也就是 goods.price * number 的值
	
	private Integer selected;	//当前商品是否在购物车中被已选择。1已选择(默认)， 0未选择。在购物车界面，用户可以选择某些商品，进行结算，这里用户每选上一个，这里就会跟着改变
	
	private Integer exceptional;	//当前商品是否是异常的， 0正常,可以正常下单,默认便是正常，  >0则是异常，如商品库存不足，商品已下架等
	private String exceptionalInfo;	//异常信息，异常说明，比如 商品已下架
	
	
	public GoodsCart() {
		this.number = 0;
		this.money = 0;
		this.exceptional = 0;
		this.selected = 1;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
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

	public Integer getExceptional() {
		return exceptional;
	}

	public void setExceptional(Integer exceptional) {
		if(exceptional == null) {
			this.exceptional = 0;
		}else {
			this.exceptional = exceptional;
		}
	}

	public String getExceptionalInfo() {
		return exceptionalInfo;
	}

	public void setExceptionalInfo(String exceptionalInfo) {
		if(exceptionalInfo == null) {
			this.exceptionalInfo = "";
		}else {
			this.exceptionalInfo = exceptionalInfo;
		}
	}

	public Integer getSelected() {
		
		return selected;
	}

	public void setSelected(Integer selected) {
		if(selected == null) {
			this.selected = 0;
		}else {
			this.selected = selected;
		}
	}
	
	/**
	 * 将 goodsCart转为json格式输出
	 * @return
	 */
	public String toJsonString(){
		String goodsJson = null;
		if(this.goods == null){
			goodsJson = "{}";
		}else{
			goodsJson = JSONObject.fromObject(this.goods).toString();
		}
		
		return "{"
				+ "\"goods\":"+goodsJson+","
				+ "\"number\":"+this.getNumber()+","
				+ "\"money\":"+this.getMoney()+","
				+ "\"selected\":"+this.getSelected()+","
				+ "\"exceptional\":"+this.getExceptional()+","
				+ "\"exceptionalInfo\":\""+this.getExceptionalInfo()+"\""
				+ "}";
	}
}
