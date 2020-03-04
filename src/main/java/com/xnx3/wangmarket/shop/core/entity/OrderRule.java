package com.xnx3.wangmarket.shop.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Index;
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
	private Short refund;		//是否使用退款这个状态，也就是是否允许用户退款。1使用，0不使用。默认是1使用
	
	public OrderRule() {
		this.distribution = NORMAL;
		this.refund = NORMAL;
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

	@Override
	public String toString() {
		return "OrderRule [id=" + id + ", distribution=" + distribution + ", refund=" + refund + "]";
	}
	
}