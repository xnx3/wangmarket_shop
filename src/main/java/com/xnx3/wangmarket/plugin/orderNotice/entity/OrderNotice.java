package com.xnx3.wangmarket.plugin.orderNotice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 每个店铺的 支付成功通知 的规则，是否启用，以及通知到哪
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_order_notice")
public class OrderNotice extends BaseEntity implements java.io.Serializable{
	/**
	 * 是否是在使用， 1使用
	 */
	public final static Short IS_USE_YES = 1;
	/**
	 * 是否是在使用， 0不使用
	 */
	public final static Short IS_USE_NO = 0;
	
	private Integer id;			//对应 store.id， 是哪个商家的规则
	private Short payNotice;	//是否使用下单成功的通知，这里起名虽然是支付，意义是只要成功下单的就会提醒，比如在线支付、线下支付等，都会包含在内。 1使用，0不使用
	private Short refundNotice;	//是否启用申请退单通知.当用户发起申请退单时，系统会自动给商家的手机发送一条短信，通知商家有顾客退单了，请及时处理。1使用，0不使用
	private String phone;		//发送短信通知，发送到哪个手机号
	
	public OrderNotice() {
		this.payNotice = IS_USE_NO;
		this.refundNotice = IS_USE_NO;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '对应 store.id， 是哪个商家的规则'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name = "pay_notice", columnDefinition="tinyint(2) comment '是否使用下单成功的通知，这里起名虽然是支付，意义是只要成功下单的就会提醒，比如在线支付、线下支付等，都会包含在内。 1使用，0不使用' default '0' ")
	public Short getPayNotice() {
		return payNotice;
	}

	public void setPayNotice(Short payNotice) {
		this.payNotice = payNotice;
	}
	
	@Column(name = "refund_notice", columnDefinition="tinyint(2) comment '是否启用申请退单通知.当用户发起申请退单时，系统会自动给商家的手机发送一条短信，通知商家有顾客退单了，请及时处理。1使用，0不使用' default '0' ")
	public Short getRefundNotice() {
		return refundNotice;
	}

	public void setRefundNotice(Short refundNotice) {
		this.refundNotice = refundNotice;
	}

	@Column(name = "phone", columnDefinition="char(15) comment '发送短信通知，发送到哪个手机号' default '' ")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "OrderNotice [id=" + id + ", payNotice=" + payNotice + ", refundNotice=" + refundNotice + ", phone="
				+ phone + "]";
	}
	
}