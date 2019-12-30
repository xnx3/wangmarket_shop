package com.xnx3.wangmarket.shop.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.xnx3.j2ee.entity.BaseEntity;


/**
 * Order 订单表
 * @author 管雷鸣
 */
@Entity
@Table(name = "shop_order")
public class Order extends BaseEntity {
	
	/**
	 * 待付款，刚创建订单，尚未付款（未支付、或未选择支付方式）
	 */
	public static final String STATE_CREATE_BUT_NO_PAY = "create_but_no_pay";	
	/**
	 * 已取消，订单超时未支付，由系统自动取消，释放库存
	 */
	public static final String STATE_PAYTIMEOUT_CANCEL = "pay_timeout_cancel";	
	/**
	 * 已付款，通过支付宝、微信支付等支付完成
	 */
	public static final String STATE_PAY = "pay";	
	/**
	 * 已付款，线下支付，用不到，预留
	 */
	public static final String STATE_OFFLINE_PAY = "offline_pay";	
	/**
	 * 待发货，商家已确认，等待发货
	 */
	public static final Short STATE_WAIT_SEND_GOODS = 21;	
	/**
	 * 拒绝接单，店家拒绝接单，因各种原因无法提供商品
	 */
	public static final Short STATE_REJECT = 22;	
	/**
	 * 退款待确认。此状态为，商家已确认订单以后，由用户发起的退款，需要商家确认是否给予退款
	 */
	public static final Short STATE_REFUND_BY_USER = 31;	
	/**
	 * 待退款，退款明确，等待客服接手退款操作
	 */
	public static final Short STATE_DELAY_CANCELMONEY = 32;
	/**
	 * 待退款。此状态为，商家在接单之后由于商家的原因商家主动退单，不用经过用户同意，直接进行退款，等待客服接手退款操作。
	 */
	public static final Short STATE_CANCLE_BY_STORE = 34;
	/**
	 * 已退款
	 * 退款不是实时的，有个延迟。这里的退款只是进行退款操作，真正的退款是银行处理，有延期，要加说明给用户：退款已处理，将在3个工作日内退回您的付款账户
	 */
	public static final Short STATE_CANCELMONEY_FINISH = 33;	
	/**
	 * 配送中
	 */
	public static final Short STATE_TRANSPORTING = 41;	
	/**
	 * 卖家提出：货已送到，完成订单（当卖家主动订单时有效，卖家改变此状态后可直接使订单完结，无需买家同意；当买家主动收货有效时，此处仅只是卖家的一个标注作用）
	 */
	public static final Short STATE_APPLY_FINISH = 56;	
	/**
	 * 买家7天之内未确认，系统自动判定其已收货，完成订单
	 */
	public static final Short STATE_APPLY_OVERTIME_FINISH = 57;	
	/**
	 * 买家主动确认已收货，完成订单，已签收
	 */
	public static final Short STATE_SIGN_RECEIVE = 58;	
	/**
	 * 买家已评论
	 */
	public static final Short STATE_BUY_COMMENT_FINISH = 61;	
	/**
	 * 卖家已评论
	 */
	public static final Short STATE_SELL_COMMENT_FINISH = 62;	
	/**
	 * 评论完成(卖家－卖家都已评论了)，订单就此彻底完结
	 */
	public static final Short STATE_COMMENT_FINISH = 63;	
	

	private Integer id;			//订单id
	private String no;			//订单号，这个是支付宝支付、微信支付传过去的订单号，也是给用户显示出来的订单号
	private Integer storeid;	//商家id，这个订单购买的商品是哪个商家的。如果一次购买多个店铺的商品，那么最终订单会分解为每个店铺一个订单
	private Integer addtime;	//订单创建时间，10为时间戳
	private Double totalMoney;	//订单总金额,单位：元
	private Double payMoney;	//需要实际支付的金额,单位：元
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
	
	@Column(name = "total_money", columnDefinition="double(8,2) comment '订单总金额,单位：元'")
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	@Column(name = "pay_money", columnDefinition="double(8,2) comment '需要实际支付的金额,单位：元'")
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
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

}