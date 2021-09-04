package com.xnx3.wangmarket.shop.core.bean;

import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.util.GoodsSpecificationUtil;

/**
 * 创建订单，购买的商品
 * @author 管雷鸣
 */
public class BuyGoods {
	private int goodsid;	//购买的是那个商品，对应Goods.id
	private int num;		//购买商品的数量
	private String specificationName;	//购买商品的规格，如果没有那么这里是空字符串. 比如goods.specification 为 [{"黄色":901},{"黑色":800},{"白色":705}] ，那么这里存的便是 黄色 。
	private Goods goods;	//购买的商品信息，这个是直接从数据表取的
	
	/**
	 * 获取当前 buyGoods 购买要支付的金额，当前商品购买要实际支付的金额，会自动计算上数量、以及规格判断
	 * @return 单位是分
	 * 	<p>如果返回-1 ，那么是购买的是商品的某种规格，但是选的这个规格又不存在，没有找到其相对应的价格所致。正常操作不会出现，除非用户是提前缓存了很久，商品规格数据变动了，规格才会出现不一致</p>
	 */
	public int getBuyMoney() {
		if(this.specificationName != null && this.specificationName.length() > 0) {
			//有规格，那么获取这个规格是多少钱。
			
			int money = GoodsSpecificationUtil.getPrice(this.goods.getSpecification(), this.specificationName);
			//判断一下商品中是否有这个规格
			if(money < 0) {
				return -1;
			}
			return money;
		}else {
			//不是使用的规格，就是单纯商品，那就是直接商品价格乘以数量就是了
			return this.goods.getPrice() * this.num;
		}
	}
	public int getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(int goodsid) {
		this.goodsid = goodsid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	public String getSpecificationName() {
		if(specificationName == null) {
			return "";
		}
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	
	@Override
	public String toString() {
		return "BuyGoods [goodsid=" + goodsid + ", num=" + num + ", specificationName=" + specificationName + ", goods=" + goods
				+ "]";
	}
	
}
