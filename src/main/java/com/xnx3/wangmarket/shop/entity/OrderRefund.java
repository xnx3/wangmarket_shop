package com.xnx3.wangmarket.shop.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 订单退单记录表，用户提出退单时，便会创建一条记录
 * @author 管雷鸣
 *
 */
@Entity()
@Table(name = "shop_order_refund", indexes={@Index(name="suoyin_index",columnList="userid,orderid,storeid,state")})
public class OrderRefund {

	/**
	 * 退单申请中
	 */
	public static final Short STATE_ING = 2;
	/**
	 * 退单同意
	 */
	public static final Short STATE_AGREE = 1;
	/**
	 * 退单拒绝
	 */
	public static final Short STATE_REFUSE = 0;
	
	
	private Integer id;			//自动编号
	private Integer userid;		//订单所属用户id，对应 User.id
	private Integer orderid;	//关联的订单id,对应 Order.id
	private Integer storeid;	//订单所属的商家id，对应 Store.id
	private Short state;		//当前退单状态	
	private Integer addtime;	//添加时间，也就是提交时间,10位时间戳
	private String reason;		//退单的理由,限制100字符
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '订单属于哪个用户的，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "orderid", columnDefinition="int(11) comment '属于哪个订单的，对应 Order.id'")
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '属于哪个店铺的，对应 Store.id'")
	public Integer getStoreid() {
		return storeid;
	}
	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "state", columnDefinition="tinyint(2) comment '该退单的状态，是否已经退单成功，还是正在进行中'")
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '添加时间，也就是提交时间,10位时间戳'")
	public Integer getAddtime() {
		return addtime;
	}
	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "reason", columnDefinition="char(100) comment '退单的理由，原因'")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		return "OrderRefund [id=" + id + ", userid=" + userid + ", orderid=" + orderid + ", storeid=" + storeid
				+ ", state=" + state + ", addtime=" + addtime + ", reason=" + reason + "]";
	}
	
}
