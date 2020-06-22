package com.xnx3.wangmarket.plugin.sell.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 用户快速登录
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_sell_storeset")
public class SellStoreSet extends BaseEntity implements java.io.Serializable{
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
	private Integer firstCommission;	//一级分成， A推荐B注册，B消费完成后，A能获得百分之多少分成。 单位是百分之几。如这里填写2，则A能获得B实际支付的百分之2作为佣金
	private Integer twoCommission;		//二级分成， A推荐B注册，B推荐C注册，C消费完成后，A能获得百分之多少分成。单位是百分之几。如这里填写2，则A能获得C实际支付的百分之2作为佣金
	private Integer disposeDay;			//处理时间，也就是提交申请后，会在几个工作日内进行处理。这个字段主要是给用户端，给用户看的。
	private Integer money;				//提现时，需要金额满足多少钱，可提现金额大于这个钱，才允许提交提现申请。这里单位是分
	private Short smsNotify;			//短信通知，是否开启短信通知， 0不开启，1开启。 开启这个的前提，是已经配置了短信接口。默认不开启
	
	public SellStoreSet() {
		this.money = 10000;	//默认满100元可提现
		this.smsNotify = 1;
	}
	
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
	
	@Column(name = "first_commission", columnDefinition="int(3) comment '一级分成， A推荐B注册，B消费完成后，A能获得百分之多少分成。 单位是百分之几。如这里填写2，则A能获得B实际支付的百分之2作为佣金' default '0' ")
	public Integer getFirstCommission() {
		return firstCommission;
	}

	public void setFirstCommission(Integer firstCommission) {
		this.firstCommission = firstCommission;
	}
	
	@Column(name = "two_commission", columnDefinition="int(3) comment '二级分成， A推荐B注册，B推荐C注册，C消费完成后，A能获得百分之多少分成。单位是百分之几。如这里填写2，则A能获得C实际支付的百分之2作为佣金' default '0' ")
	public Integer getTwoCommission() {
		return twoCommission;
	}

	public void setTwoCommission(Integer twoCommission) {
		this.twoCommission = twoCommission;
	}
	
	@Column(name = "dispose_day", columnDefinition="int(2) comment '处理时间，也就是提交申请后，会在几个工作日内进行处理。这个字段主要是给用户端，给用户看的。' default '1' ")
	public Integer getDisposeDay() {
		return disposeDay;
	}

	public void setDisposeDay(Integer disposeDay) {
		this.disposeDay = disposeDay;
	}
	
	@Column(name = "money", columnDefinition="int(11) comment '提现时，需要金额满足多少钱，可提现金额大于这个钱，才允许提交提现申请。这里单位是分'")
	public Integer getMoney() {
		if(this.money == null){
			this.money = 10000;	//默认满100元可提现
		}
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
	@Column(name = "sms_notify", columnDefinition="tinyint(11) comment '短信通知，是否开启短信通知， 0不开启，1开启。 开启这个的前提，是已经配置了短信接口。'")
	public Short getSmsNotify() {
		return smsNotify;
	}

	public void setSmsNotify(Short smsNotify) {
		this.smsNotify = smsNotify;
	}

	@Override
	public String toString() {
		return "SellStoreSet [id=" + id + ", isUse=" + isUse + ", firstCommission=" + firstCommission
				+ ", twoCommission=" + twoCommission + ", disposeDay=" + disposeDay + ", money=" + money
				+ ", smsNotify=" + smsNotify + "]";
	}
	
}