package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.Goods;

/**
 * {@link Goods} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class GoodsBean implements java.io.Serializable{
	private Integer id;			//商品id，自动编号
	private String title;		//商品标题，限制40字符
	private Integer inventory;	//库存数量
	private Short putaway;		//是否上架在售，1出售中，0已下架
	private Integer sale;		//已售数量，有假的数量在内。这里是原本 goods.sale + goods.fakeSale 的和
	private String units;		//计量单位。如个、斤、条，限制5字符
	private Integer price;		//单价，单位是分，实际购买需要支付的价格	
	private Integer originalPrice;	//原价，单位是分，好看用的，显示出来一个价格，加一条删除线
	private Integer addtime;	//商品添加时间，10位时间戳
	private Integer storeid;	//该商品是属于哪个店铺的，对应 Store.id
	private Integer typeid;		//该商品所属哪个分类下的，对应 GoodsType.id
	private String titlepic;	//该商品的标题图片、列表图片,图片的绝对路径
	private Integer userBuyRestrict;	//用户购买限制。如果值是0，则可以任意购买，没有什么限制，如果是1，则代表每个用户只能购买一个，如果是2，代表每个用户只能购买2个以内，不超过2个。 只要是下单了，未退单成功的，都算是购买了
	private String intro;		//简介说明，限制40个字符
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		if(id == null) {
			this.id = 0;
		}else {
			this.id = id;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		if(title == null) {
			this.title = "";
		}else {
			this.title = title;
		}
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		if(inventory == null) {
			this.inventory = 0 ;
		}else {
			this.inventory = inventory;
		}
	}
	public Short getPutaway() {
		return putaway;
	}
	public void setPutaway(Short putaway) {
		if(putaway == null) {
			this.putaway = Goods.PUTAWAY_SELL;
		}else {
			this.putaway = putaway;
		}
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		if(sale == null) {
			this.sale = 0;
		}else {
			this.sale = sale;
		}
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		if(units == null) {
			this.units = "";
		}else {
			this.units = units;
		}
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		if(price == null) {
			this.price = 0;
		}else {
			this.price = price;
		}
	}
	public Integer getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Integer originalPrice) {
		if(originalPrice == null) {
			this.originalPrice = 0;
		}else {
			this.originalPrice = originalPrice;
		}
	}
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		if(addtime == null) {
			this.addtime = 0;
		}else {
			this.addtime = addtime;
		}
	}
	public Integer getStoreid() {
		return storeid;
	}
	public void setStoreid(Integer storeid) {
		if(storeid == null) {
			this.storeid = 0;
		}else {
			this.storeid = storeid;
		}
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		if(typeid == null) {
			this.typeid = 0;
		}else {
			this.typeid = typeid;
		}
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		if(titlepic == null) {
			this.titlepic = "";
		}else {
			this.titlepic = titlepic;
		}
	}
	public Integer getUserBuyRestrict() {
		return userBuyRestrict;
	}
	public void setUserBuyRestrict(Integer userBuyRestrict) {
		if(userBuyRestrict == null) {
			this.userBuyRestrict = 0;
		}else {
			this.userBuyRestrict = userBuyRestrict;
		}
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		if(intro == null){
			this.intro = "";
		}else{
			this.intro = intro;
		}
	}
}
