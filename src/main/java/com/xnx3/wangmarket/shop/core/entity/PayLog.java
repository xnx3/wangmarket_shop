package com.xnx3.wangmarket.shop.core.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 支付日志
 */
@Entity
@Table(name="shop_pay_log", indexes={@Index(name="suoyin_index",columnList="channel,userid,storeid,orderid")})
public class PayLog extends BaseEntity implements java.io.Serializable {

	/**
	 * 支付宝PC端电脑网页支付
	 */
	public final static String CHANNEL_ALIPAY_PC="alipay_pc";	
	/**
	 * 支付宝手机网页支付
	 */
	public final static String CHANNEL_ALIPAY_WAP="alipay_wap";	
	/**
	 * 支付宝扫码支付
	 */
	public final static String CHANNEL_ALIPAY_QR="alipay_qr";	
	/**
	 * 支付宝 PC 网页支付
	 */
	public final static String CHANNEL_ALIPAY_PC_DIRECT="alipay_pc_direct";	
	/**
	 * 银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
	 */
	public final static String CHANNEL_UPACP="upacp";	
	/**
	 * 银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
	 */
	public final static String CHANNEL_UPACP_WAP="upacp_wap";	
	/**
	 * 银联 PC 网页支付
	 */
	public final static String CHANNEL_UPACP_PC="upacp_pc";	
	/**
	 * 银联企业网银支付
	 */
	public final static String CHANNEL_CP_B2B="cp_b2b";	
	/**
	 * 微信支付
	 */
	public final static String CHANNEL_WX="wx";	
	/**
	 * 微信公众账号支付
	 */
	public final static String CHANNEL_WX_PUB="wx_pub";	
	
	/**
	 * 微信公众账号扫码支付
	 */
	public final static String CHANNEL_WX_PUB_QR="wx_pub_qr";	
	/**
	 * Apple Pay
	 */
	public final static String CHANNEL_APPLEPAY_UPACP="applepay_upacp";	
	/**
	 * 百度钱包移动快捷支付
	 */
	public final static String CHANNEL_BFB="bfb";	
	/**
	 * 百度钱包手机网页支付
	 */
	public final static String CHANNEL_BFB_WAP="bfb_wap";	
	/**
	 * 易宝手机网页支付
	 */
	public final static String CHANNEL_YEEPAY_WAP="yeepay_wap";	
	/**
	 * 京东手机网页支付
	 */
	public final static String CHANNEL_JDPAY_WAP="jdpay_wap";	
	/**
	 * 应用内快捷支付（银联）
	 */
	public final static String CHANNEL_CNP_U="cnp_u";	
	/**
	 * 应用内快捷支付（外卡）
	 */
	public final static String CHANNEL_CNP_F="cnp_f";	
	/**
	 * 分期乐支付
	 */
	public final static String CHANNEL_FQLPAY_WAP="fqlpay_wap";	
	/**
	 * 量化派支付
	 */
	public final static String CHANNEL_QGBC_WAP="qgbc_wap";	
	

	private Integer id;	//自动编号
	private String channel;	//支付方式
	private Integer addtime;	//支付时间，10位时间戳
	private Integer money;		//支付金额，单位：分
	private Integer orderid;	//支付的订单的订单id，关联 Order.id
	private Integer userid;		//支付的人的用户id，user.id ，是谁支付的
	private Integer storeid;	//当前支付记录属于那个商铺的

    @Id 
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "channel", columnDefinition="char(16) comment '支付方式' default ''")
    public String getChannel() {
        return this.channel;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    @Column(name = "addtime", columnDefinition="int(11) comment '支付时间，10位时间戳' default '0'")
    public Integer getAddtime() {
        return this.addtime;
    }
    
    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }
    
    @Column(name = "money", columnDefinition="int(11) comment '支付金额，单位是分' default '0'")
    public Integer getMoney() {
        return this.money;
    }
    
    public void setMoney(Integer money) {
        this.money = money;
    }
    
    @Column(name = "userid", columnDefinition="int(11) comment '支付的用户' default '0'")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "storeid", columnDefinition="int(11) comment '支付的订单所属哪个商家' default '0'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	
	@Column(name = "orderid", columnDefinition="int(11) comment '订单号，订单id' default '0'")
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Override
	public String toString() {
		return "PayLog [id=" + id + ", channel=" + channel + ", addtime=" + addtime + ", money=" + money + ", orderid="
				+ orderid + ", userid=" + userid + ", storeid=" + storeid + "]";
	}

}