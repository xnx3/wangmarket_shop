package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.vo.bean.GoodsCart;

/**
 * 商品的购物车信息,获取某个商品的购物车信息时会返回此
 * @author 管雷鸣
 */
@ResponseBodyManage(nullSetDefaultValue = true)
public class GoodsCartVO extends BaseVO{
	private int number;		//此种商品数量，加入购物车中的数量
	private int money;		//此种商品加入购物车中的总金额，单位是分。也就是 goods.price * number 的值
	
	private int selected;	//当前商品是否在购物车中被已选择。<ul><li>1:已选择(默认)<li>0:未选择。在购物车界面，用户可以选择某些商品，进行结算，这里用户每选上一个，这里就会跟着改变</ul>
	
	private int exceptional;	//当前商品是否是异常的<ul><li>0:正常,可以正常下单,默认便是正常<li>大于0:则是异常，如商品库存不足，商品已下架等, 具体参考如 GoodsCart.EXCEPTIONAL_SOLD_OUT</ul>
	private String exceptionalInfo;	//异常信息，异常说明，比如 商品已下架
	
	public GoodsCartVO() {
		this.number = 0;
		this.money = 0;
		this.exceptional = 0;
		this.selected = 1;
	}
	
	public GoodsCartVO(GoodsCart goodsCart){
		if(goodsCart == null){
			this.number = 0;
			this.money = 0;
			this.exceptional = 0;
			this.selected = 1;
		}else{
			this.number = goodsCart.getNumber();
			this.money = goodsCart.getMoney();
			this.exceptional = goodsCart.getExceptional();
			this.selected = goodsCart.getSelected();
		}
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

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getExceptional() {
		return exceptional;
	}

	public void setExceptional(int exceptional) {
		this.exceptional = exceptional;
	}

	public String getExceptionalInfo() {
		return exceptionalInfo;
	}

	public void setExceptionalInfo(String exceptionalInfo) {
		this.exceptionalInfo = exceptionalInfo;
	}

	@Override
	public String toString() {
		return "GoodsCartVO [number=" + number + ", money=" + money + ", selected=" + selected + ", exceptional="
				+ exceptional + ", exceptionalInfo=" + exceptionalInfo + "]";
	}
	
	
}
