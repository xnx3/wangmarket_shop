package com.xnx3.wangmarket.plugin.limitbuy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 规定用户的可购买次数（订单）。购买时先从这里取，如果这里取不到某个用户限制的最大购买次数，那就再从 LimitBuyStore 中取店铺设置的新用户可以购买几次
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_limitbuy_user")
public class LimitBuyUser extends BaseEntity implements java.io.Serializable{
	private String id;			//userid_storeid的组合体， 如  219_1
	private Integer userid;		//用户id，对应 User.id
	private Integer storeid;	//此用户属于哪个商家，是哪个商家的用户
	private Integer limitNumber;	//限额的次数，限制购买多少次。（订单）。默认是0，表示不使用此数，从 LimitBuyStore.limitNumber 中取
	private Integer useNumber;	//已使用的次数。当用户付款支付后，便记为一次有效使用
	
	public LimitBuyUser() {
		this.limitNumber = 0;
	}
	
	@Id
	@Column(name = "id", columnDefinition="char(20) comment 'userid_storeid的组合体， 如  219_1'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '用户id，对应 User.id'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "storeid", columnDefinition="int(11) comment '此用户拥有哪个站点的管理权。开通子账号会用到这个。如果这个有值，那么就是子账号了。对应 store.id'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "limit_number", columnDefinition="int(11) comment '限额的次数，限制购买多少次。（订单）'")
	public Integer getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(Integer limitNumber) {
		this.limitNumber = limitNumber;
	}
	
	@Column(name = "use_number", columnDefinition="int(11) comment '限额的次数，限制购买多少次。（订单）'")
	public Integer getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(Integer useNumber) {
		this.useNumber = useNumber;
	}

	@Override
	public String toString() {
		return "LimitBuyUser [id=" + id + ", userid=" + userid + ", storeid=" + storeid + ", limitNumber=" + limitNumber
				+ "]";
	}
	
}