package com.xnx3.wangmarket.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 商品表，店铺所卖的商品
 */
@Entity
@Table(name = "shop_goods")
public class Goods extends BaseEntity {

	/**
	 * 上架状态，正常出售中
	 */
	public final static Short PUTAWAY_SELL = 1;
	/**
	 * 上架状态，已下架，不卖
	 */
	public final static Short PUTAWAY_NOT_SELL = 0;
	
	private Integer id;			//商品id，自动编号
	private String title;		//商品标题，限制40字符
	private Integer inventory;	//库存数量
	private Integer alarmNum;	//告警数量，库存量低于这个数，会通知商家告警，提醒商家该加库存了
	private Short putaway;		//是否上架在售，1出售中，0已下架
	private Integer soldoutCountdown;	//下架倒计时，这里是10位时间戳，指定一个具体的时间，在那个时间会自动下架。这个自动下架并不是将putaway进行了改动，而是在查询可买商品时，会把这个作为where条件进行查询
	private Integer sale;		//已售数量，只要出售的，成功的，都记入这里。当然，如果退货了，这里就要减去了。这里是根据order进行筛选的。每成功一次或者退款一次，都会重新select count 统计一次
	private String units;		//计量，单位。如个、斤、条，限制5字符
	private Double price;		//单价，单位是元，实际购买需要支付的价格	
	private Double originalPrice;	//原价，单位是元，好看用的，显示出来一个价格，加一条删除线
	private Integer addtime;	//商品添加时间，10位时间戳
	private Integer updatetime;	//商品最后更改时间，10位时间戳
	private Integer storeid;	//该商品是属于哪个店铺的，对应 Store.id
	private Integer typeid;		//该商品所属哪个分类下的，对应 GoodsType.id
	private String titlepic;	//该商品的标题图片、列表图片,图片的绝对路径
	private Short isdelete;		//该商品是否已被删除
	private Integer userBuyRestrict;	//用户购买限制。如果值是0，则可以任意购买，没有什么限制，如果是1，则代表每个用户只能购买一个，如果是2，代表每个用户只能购买2个以内，不超过2个。 只要是下单了，未退单成功的，都算是购买了

	private Integer version;	//乐观锁
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", columnDefinition="char(40) comment '商品标题'")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "inventory", columnDefinition="int(11) comment '库存数量'")
	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	@Column(name = "alarm_num", columnDefinition="int(11) comment '告警数量，库存量低于这个数，会通知商家告警，提醒商家该加库存了'")
	public Integer getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(Integer alarmNum) {
		this.alarmNum = alarmNum;
	}
	
	@Column(name = "putaway", columnDefinition="tinyint(2) comment '是否上架在售，1出售中，0已下架'")
	public Short getPutaway() {
		return putaway;
	}

	public void setPutaway(Short putaway) {
		this.putaway = putaway;
	}
	
	@Column(name = "sale", columnDefinition="int(11) comment '已售数量，只要出售的，成功的，都记入这里。当然，如果退货了，这里就要减去了。这里是根据order进行筛选的。每成功一次或者退款一次，都会重新select count 统计一次'")
	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}
	
	@Column(name = "units", columnDefinition="char(5) comment '计量，单位。如个、斤、条'")
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	@Column(name = "price", columnDefinition="double(8,2) comment '单价，单位是元'")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '商品添加时间，10位时间戳'")
	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "updatetime", columnDefinition="int(11) comment '商品最后更改时间，10位时间戳'")
	public Integer getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Integer updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '该商品是属于哪个店铺的，对应 Store.id'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "typeid", columnDefinition="int(11) comment '该商品所属哪个分类下的，对应 GoodsType.id'")
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@Column(name = "titlepic", columnDefinition="char(100) comment '该商品的标题图片、列表图片,图片的绝对路径'")
	public String getTitlepic() {
		return titlepic;
	}

	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}
	
	@Column(name = "isdelete", columnDefinition="tinyint(2) comment '该商品是否已被删除'")
	public Short getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Short isdelete) {
		this.isdelete = isdelete;
	}
	
	@Column(name = "original_price", columnDefinition="double(8,2) comment '原价，单位是元，好看用的，显示出来一个价格，加一条删除线'")
	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	@Column(name = "user_buy_restrict", columnDefinition="int(11) comment '用户购买限制。如果值是0，则可以任意购买，没有什么限制，如果是1，则代表每个用户只能购买一个，如果是2，代表每个用户只能购买2个以内，不超过2个。 只要是下单了，未退单成功的，都算是购买了'")
	public Integer getUserBuyRestrict() {
		return userBuyRestrict;
	}

	public void setUserBuyRestrict(Integer userBuyRestrict) {
		this.userBuyRestrict = userBuyRestrict;
	}
	
	@Column(name = "soldout_countdown", columnDefinition="int(11) comment '下架倒计时，这里是10位时间戳，指定一个具体的时间，在那个时间会自动下架。这个自动下架并不是将putaway进行了改动，而是在查询可买商品时，会把这个作为where条件进行查询'")
	public Integer getSoldoutCountdown() {
		return soldoutCountdown;
	}

	public void setSoldoutCountdown(Integer soldoutCountdown) {
		this.soldoutCountdown = soldoutCountdown;
	}

	@Version
	@Column(name = "version", columnDefinition="int(11) comment '乐观锁' default 0")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}