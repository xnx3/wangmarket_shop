package com.xnx3.wangmarket.shop.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

import com.xnx3.j2ee.entity.BaseEntity;


/**
 * Order 订单表
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_order", indexes={@Index(name="suoyin_index",columnList="no,storeid,userid,state")})
public class Order extends BaseEntity {
	
	/**
	 * 待付款，刚创建订单，尚未付款（未支付、或未选择支付方式）
	 */
	public static final String STATE_CREATE_BUT_NO_PAY = "generate_but_no_pay";	
	/**
	 * 已取消，订单超时未支付，由系统自动取消，释放库存
	 */
	public static final String STATE_PAYTIMEOUT_CANCEL = "pay_timeout_cancel";	
	/**
	 * 取消订单，订单未支付时，主动取消
	 */
	public static final String STATE_MY_CANCEL = "my_cancel";	
	/**
	 * 已付款，通过支付宝、微信支付等支付完成，订单进入配送中
	 */
	public static final String STATE_PAY = "pay";
	/**
	 * 线下支付，下单后，点击线下支付，将订单转为线下支付状态。此状态跟 STATE_PAY 已付款 状态是并列的
	 */
	public static final String STATE_PRIVATE_PAY = "private_pay";
	/**
	 * 退款中，用户点击申请退款，就会变成退款中的状态
	 */
	public static final String STATE_CANCELMONEY_ING = "refund_ing";	
	/**
	 * 已退款
	 * 当用户下单后
	 */
	public static final String STATE_CANCELMONEY_FINISH = "refund_finish";	
	/**
	 * 已收到货物，已确定收货
	 */
	public static final String STATE_RECEIVE_GOODS = "receive_goods";	
	/**
	 * 支付完成，配送中
	 */
	public static final String STATE_DISTRIBUTION_ING = "distribution_ing";	
	
	private Integer id;			//订单id
	private String no;			//订单号，这个是支付宝支付、微信支付传过去的订单号，也是给用户显示出来的订单号
	private Integer storeid;	//商家id，这个订单购买的商品是哪个商家的。如果一次购买多个店铺的商品，那么最终订单会分解为每个店铺一个订单
	private Integer addtime;	//订单创建时间，10为时间戳
	private Integer totalMoney;	//订单总金额,单位：分
	private Integer payMoney;		//需要实际支付的金额,单位：分
	private Integer payTime;	//该订单支付的时间，10位时间戳
	private Integer userid;		//该订单所属的用户，是哪个用户下的单，对应 User.id
	private String state;		//订单状态，限20个字符
	private String remark;		//用户备注，限制100个字符
	
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
	
	@Column(name = "no", columnDefinition="char(10) comment '订单号，这个是支付宝支付、微信支付传过去的订单号，也是给用户显示出来的订单号'")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '商家id，这个订单购买的商品是哪个商家的。如果一次购买多个店铺的商品，那么最终订单会分解为每个店铺一个订单'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	@Column(name = "addtime", columnDefinition="int(11) comment '订单创建时间，10为时间戳'")
	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "total_money", columnDefinition="int(11) comment '订单总金额,单位：元'")
	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	@Column(name = "pay_money", columnDefinition="int(11) comment '需要实际支付的金额,单位：元'")
	public Integer getPayMoney() {
		if(payMoney == null) {
			return 0;
		}
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	
	@Column(name = "pay_time", columnDefinition="int(11) comment '该订单支付的时间，10位时间戳'")
	public Integer getPayTime() {
		return payTime;
	}

	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '该订单所属的用户，是哪个用户下的单，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "state", columnDefinition="char(20) comment '订单状态'")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "remark", columnDefinition="char(100) comment '买家的备注'")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Version
	@Column(name = "version", columnDefinition="int(11) comment '乐观锁' default 0")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", no=" + no + ", storeid=" + storeid + ", addtime=" + addtime + ", totalMoney="
				+ totalMoney + ", payMoney=" + payMoney + ", payTime=" + payTime + ", userid=" + userid + ", state="
				+ state + ", remark=" + remark + ", version=" + version + "]";
	}

}