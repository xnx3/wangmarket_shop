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
 * 商品分类，如水产品、奶产品、饼干、零食，每个都是一个分类
 * @author 管雷鸣
 */
@Entity()
@Table(name = "shop_goods_type", indexes={@Index(name="suoyin_index",columnList="rank,storeid,isdelete")})
public class GoodsType extends BaseEntity {
	
	private Integer id;			//分类id，自动编号
	private String title;		//分类的名称，限20个字符
	private String icon;		//分类的图标、图片，限制100个字符
	private Integer rank;		//分类间的排序，数字越小越靠前
	private Integer storeid;	//对应店铺id, Store.id ， 该分类是属于那个店铺的
	private Short isdelete;		//是否被删除

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "title", columnDefinition="char(20) comment '分类的名称'")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "rank", columnDefinition="int(11) comment '分类间的排序，数字越小越靠前'")
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@Column(name = "isdelete", columnDefinition="tinyint(2) comment '是否被删除'")
	public Short getIsdelete() {
		return isdelete;
	}
	
	public void setIsdelete(Short isdelete) {
		this.isdelete = isdelete;
	}
	
	@Column(name = "icon", columnDefinition="char(100) comment '分类的图标、图片'")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "storeid", columnDefinition="int(11) comment '对应店铺id, Store.id ， 该分类是属于那个店铺的'")
	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	@Override
	public String toString() {
		return "GoodsType [id=" + id + ", title=" + title + ", icon=" + icon + ", rank=" + rank + ", isdelete="
				+ isdelete + "]";
	}

}