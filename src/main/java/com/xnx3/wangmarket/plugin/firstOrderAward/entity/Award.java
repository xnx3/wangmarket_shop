package com.xnx3.wangmarket.plugin.firstOrderAward.entity;

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
@Table(name = "plugin_firstorderaward_award")
public class Award extends BaseEntity {
	/**
	 * 是否是在使用， 1使用
	 */
	public final static Short IS_USE_YES = 1;
	/**
	 * 是否是在使用， 0不使用
	 */
	public final static Short IS_USE_NO = 0;
	
	private Integer id;			//对应 store.id， 是哪个商家的奖品规则
	private Short isUse;		//是否是在使用， 1使用，0不使用
	private Integer goodsid;	//奖品的商品id，对应 goods.id ，当推荐的用户下单购买消费成功后，会自动给推荐人一个0元的商品，这个便是给的商品的id
	
	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="int(11) comment '对应 store.id， 是哪个商家的奖品规则'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "goodsid", columnDefinition="int(11) comment '奖品的商品id，对应 goods.id ，当推荐的用户下单购买消费成功后，会自动给推荐人一个0元的商品，这个便是给的商品的id'")
	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "is_use", columnDefinition="int(11) comment '是否是在使用， 1使用，0不使用' default '0' ")
	public Short getIsUse() {
		return isUse;
	}
	public void setIsUse(Short isUse) {
		this.isUse = isUse;
	}
	
	@Override
	public String toString() {
		return "Award [id=" + id + ", goodsid=" + goodsid + "]";
	}

}