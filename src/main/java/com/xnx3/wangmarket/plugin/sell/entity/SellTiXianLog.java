package com.xnx3.wangmarket.plugin.sell.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.User;

/**
 * 用户申请提现
 * @author 管雷鸣
 */
@Entity
@Table(name = "plugin_sell_tixian", indexes={@Index(name="suoyin_index",columnList="userid,storeid,addtime,state")})
public class SellTiXianLog implements Serializable{
	private Integer id;			//自动编号
	private Integer userid;		//用户id，对应 User.id
	private Integer storeid;	//此用户属于哪个商家，是哪个商家的用户
	private Integer money;		//此次提现金额，单位是分
	private Integer addtime;	//此次申请提现的时间，10位时间戳
	private Short state;		//当前状态。0申请中，1已通过并汇款，2已拒绝。拒绝后店家会主动联系这个客户说明情况，就不需要走线上了
	private String phone;		//联系手机，如果出现什么问题，会打电话沟通
	private String username;	//收款人姓名，收款的是谁
	private String card;		//卡号,收款账号
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '自增，自动编号'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	@Column(name = "money", columnDefinition="int(11) comment '此次提现金额，单位是分'")
	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
	@Column(name = "addtime", columnDefinition="int(11) comment '此次申请提现的时间，10位时间戳'")
	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}
	
	@Column(name = "state", columnDefinition="tinyint(2) comment '当前状态。0申请中，1已通过并汇款，2已拒绝。拒绝后店家会主动联系这个客户说明情况，就不需要走线上了'")
	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}
	
	@Column(name = "phone", columnDefinition="char(15) comment '联系手机，如果出现什么问题，会打电话沟通'")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "username", columnDefinition="char(10) comment '收款人姓名，收款的是谁'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "card", columnDefinition="char(30) comment '卡号,收款账号'")
	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "SellTiXian [id=" + id + ", userid=" + userid + ", storeid=" + storeid + ", money=" + money
				+ ", addtime=" + addtime + ", state=" + state + ", phone=" + phone + ", username=" + username
				+ ", card=" + card + "]";
	}
	
}	
