package com.xnx3.wangmarket.plugin.sell.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 佣金记录，每一笔佣金收入都会记录到此
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_sell_commission_log", indexes={@Index(name="suoyin_index",columnList="userid,storeid,addtime,transfer_state")})
public class SellCommissionLog extends BaseEntity implements java.io.Serializable{
	/**
	 * 是否是在使用， 1使用
	 */
	public final static Short IS_USE_YES = 1;
	/**
	 * 是否是在使用， 0不使用
	 */
	public final static Short IS_USE_NO = 0;
	
	private Integer id;			//自增，自动编号
	private Integer orderid;	//订单id， Order.id ，该佣金是哪个订单产生的
	private Integer userid;		//用户id，该佣金是属于哪个用户的
	private Integer storeid;	//店铺id，该佣金是属于哪个店铺，要哪个店铺发放
	private Integer addtime;	//此条记录产生的时间
	private Integer money;		//收入的金额，单位是分
	private Short transferState;	//转账状态，结算状态，是否已经跟用户结算了。 1 已结算， 0未结算
	
	public SellCommissionLog() {
		this.addtime = 0;
		this.transferState = 0;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '自增，自动编号'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "orderid", columnDefinition="int(11) comment '订单id， Order.id ，该佣金是哪个订单产生的'")
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "userid", columnDefinition="int(11) comment '用户id，该佣金是属于哪个用户的'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "storeid", columnDefinition="int(11) comment '店铺id，该佣金是属于哪个店铺，要哪个店铺发放'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '此条记录产生的时间'")
	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "money", columnDefinition="int(11) comment '收入的金额，单位是分'")
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
	@Column(name = "transfer_state", columnDefinition="tinyint(2) comment '转账状态，结算状态，是否已经跟用户结算了。 1 已结算， 0未结算'")
	public Short getTransferState() {
		return transferState;
	}

	public void setTransferState(Short transferState) {
		this.transferState = transferState;
	}

	@Override
	public String toString() {
		return "SellCommissionLog [id=" + id + ", orderid=" + orderid + ", userid=" + userid + ", storeid=" + storeid
				+ ", addtime=" + addtime + ", money=" + money + ", transferState=" + transferState + "]";
	}

}