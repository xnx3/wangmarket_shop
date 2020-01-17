package com.xnx3.wangmarket.plugin.seoUrlPush_domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;

/**
 * 论坛帖子主表，变长的内容存在于分表
 * @author 管雷鸣
 *
 */
@Entity
@Table(name = "plugin_seo_url_push")
public class SEOUrlPush extends BaseEntity {
	/**
	 * 是否是在使用， 1使用
	 */
	public final static Short IS_USE_YES = 1;
	/**
	 * 是否是在使用， 0不使用
	 */
	public final static Short IS_USE_NO = 0;
	
	private Integer siteid;		//站点的id编号，对应 site.id，也是主健
	private Short isUse;		//是否是在使用， 1使用，0不使用
	
	@Id
	@Column(name = "siteid", columnDefinition="int(11) comment '站点的id编号，对应 site.id' default '0' ")
	public Integer getSiteid() {
		return siteid;
	}
	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
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
		return "SEOUrlPush [siteid=" + siteid + ", isUse=" + isUse + "]";
	}

}