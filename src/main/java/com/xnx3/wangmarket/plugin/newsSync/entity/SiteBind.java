package com.xnx3.wangmarket.plugin.newsSync.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 站点绑定，一个网站内容变动了，会同步到另一个网站。这里就是网站之间的绑定关系
 * @author 管雷鸣
 *
 */
@Entity
@Table(name = "plugin_newssync_site_bind", indexes={@Index(name="sitebind_index",columnList="source_id")})
public class SiteBind {
	private Integer id;
	private Integer sourceId;	//源站的站点id,site.id，当原站站点的内容有改动时，会自动同步到指定的目标站点中
	private Integer otherId;	//要将原站的文章同步到的网站的id,site.id，别人网站的id，同步的目标网站的id
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "source_id", columnDefinition="int(11) comment '源站的站点id,site.id，当原站站点的内容有改动时，会自动同步到指定的目标站点中'")
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name = "other_id", columnDefinition="int(11) comment '要将原站的文章同步到的网站的id,site.id，别人网站的id，同步的目标网站的id'")
	public Integer getOtherId() {
		return otherId;
	}
	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}
	@Override
	public String toString() {
		return "SiteBind [id=" + id + ", sourceId=" + sourceId + ", otherId=" + otherId + "]";
	}
	
}
