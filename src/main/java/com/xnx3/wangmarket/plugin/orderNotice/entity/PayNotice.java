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
public class PayNotice extends BaseEntity implements java.io.Serializable{
	/**
	 * 是否是在使用， 1使用
	 */
	public final static Short IS_USE_YES = 1;
	/**
	 * 是否是在使用， 0不使用
	 */
	public final static Short IS_USE_NO = 0;
	
	private Integer id;			//对应 store.id， 是哪个商家的规则
	private Short isUse;		//是否是在使用， 1使用，0不使用
	private String phone;		//发送短信通知，发送到哪个手机号
	
	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '对应 store.id， 是哪个商家的规则'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "is_use", columnDefinition="int(11) comment '是否是在使用， 1使用，0不使用' default '0' ")
	public Short getIsUse() {
		return isUse;
	}

	public void setIsUse(Short isUse) {
		this.isUse = isUse;
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
		return "PayNotice [id=" + id + ", isUse=" + isUse + ", phone=" + phone + "]";
	}
	
}