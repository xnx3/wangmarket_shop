package com.xnx3.wangmarket.plugin.limitbuy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 规定用户的可购买次数。
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_limitbuy_store")
public class LimitBuyStore extends BaseEntity implements java.io.Serializable{
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
	private Integer limitNumber;	//限额的次数，限制购买多少次。（订单）
	
	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '对应 store.id， 是哪个商家的奖品规则'")
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
	
	@Column(name = "limit_number", columnDefinition="int(11) comment '限额的次数，限制购买多少次。（订单）'")
	public Integer getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(Integer limitNumber) {
		this.limitNumber = limitNumber;
	}

	@Override
	public String toString() {
		return "LimitBuyStore [id=" + id + ", isUse=" + isUse + ", limitNumber=" + limitNumber + "]";
	}
	
}