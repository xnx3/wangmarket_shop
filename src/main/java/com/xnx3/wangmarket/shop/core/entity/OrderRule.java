package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 订单规则、流程。每个商家都可以自定义一套自己的流程规则。此对象会使用map或者redis进行长久缓存
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_order_rule")
public class OrderRule extends BaseEntity implements java.io.Serializable {
	/**
	 * 正常
	 */
	public static final short NORMAL = 1;
	/**
	 * 关闭
	 */
	public static final short OFF = 0;
	
	private Integer id;			//编号，对应 store.id
	private Short distribution;	//是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。1使用，0不使用。默认是1使用
	private Short refund;		//是否使用退款这个状态，也就是是否允许用户退款。<ul><li>1:使用<li>0:不使用</ul>默认是1使用
	private Integer notPayTimeout;	//订单如果创建订单了，但未支付，多长时间会自动取消订单，订单状态变为已取消。这里的单位是秒
	private Integer receiveTime;	//订单的确认收货，超过多久没确认收货，订单自动确认收货。如果用户没有主动点击确认收货，系统是否会在超过多长时间后自动将订单变为已确认收货。注意，这里单位是分钟。如果设置为0，则是不使用系统的自动确认收货。（默认不使用）
	private Short print;		//是否启用订单打印功能。订单管理中，当查看订单详情时，是否显示 【打印】 按钮。如果关闭，那订单管理-订单详情中的打印按钮会直接不显示。这里打印的订单是类似于外卖小票，使用 57mm、58mm 热敏小票打印机 进行打印。操作的电脑中需要提前安装好打印机驱动，对接好热敏小票打印机 ，然后将此项开启，再到订单管理中，点击打印按钮打印一个订单看看效果。
	
	public OrderRule() {
		this.distribution = NORMAL;
		this.refund = NORMAL;
		this.notPayTimeout = 1800;	//默认半小时
		this.print = OFF;	//默认不开启打印功能
		this.receiveTime = 0; //默认不使用系统自动确认收货功能
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "distribution", columnDefinition="tinyint(11) comment '是否使用配送中这个状态，如果没有，订单可以有已支付直接变为已完成。1使用，0不使用。默认是1使用' default '1'")
	public Short getDistribution() {
		return distribution;
	}

	public void setDistribution(Short distribution) {
		this.distribution = distribution;
	}
	
	@Column(name = "refund", columnDefinition="tinyint(11) comment '是否使用退款这个状态，也就是是否允许用户退款。1使用，0不使用。默认是1使用' default '1'")
	public Short getRefund() {
		return refund;
	}

	public void setRefund(Short refund) {
		this.refund = refund;
	}
	
	@Column(name = "not_pay_timeout", columnDefinition="int(11) comment '订单如果创建订单了，但未支付，多长时间会自动取消订单，订单状态变为已取消。这里的单位是秒' default '1800'")
	public Integer getNotPayTimeout() {
		return notPayTimeout;
	}

	public void setNotPayTimeout(Integer notPayTimeout) {
		this.notPayTimeout = notPayTimeout;
	}

	@Column(name = "receive_time", columnDefinition="int(11) comment '订单的确认收货，超过多久没确认收货.注意，这里单位是分钟。如果设置为0，则是不使用系统的自动确认收货。（默认不使用）' default '0'")
	public Integer getReceiveTime() {
		if(receiveTime == null) {
			receiveTime = 0;
		}
		return receiveTime;
	}

	public void setReceiveTime(Integer receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	@Column(name = "print", columnDefinition="tinyint(11) comment '是否启用订单打印功能。订单管理中，当查看订单详情时，是否显示 【打印】 按钮。如果关闭，那订单管理-订单详情中的打印按钮会直接不显示。' default '0'")
	public Short getPrint() {
		return print;
	}

	public void setPrint(Short print) {
		this.print = print;
	}

	@Override
	public String toString() {
		return "OrderRule [id=" + id + ", distribution=" + distribution + ", refund=" + refund + ", notPayTimeout="
				+ notPayTimeout + ", receiveTime=" + receiveTime + ", print=" + print + "]";
	}
	
}