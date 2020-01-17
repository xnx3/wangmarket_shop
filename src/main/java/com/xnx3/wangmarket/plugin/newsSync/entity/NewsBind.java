package com.xnx3.wangmarket.plugin.newsSync.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 一个网站中发布的文章，自动同步到另一个网站。这里记录的是同步的文章对应的id，不然另一篇文章修改、删除，怎么知道其他网站要修改、删除哪篇文章
 * @author 管雷鸣
 *
 */
@Entity
@Table(name = "plugin_newssync_news_bind", indexes={@Index(name="newsbind_index",columnList="source_id")})
public class NewsBind {
	private Integer id;	//自动编号
	private Integer sourceId;	//源站的文章id，news.id ，源网站发布了文章，同步到另一个网站。这里是源站中文章保存成功后的news.id
	private Integer otherId;	//目标网站的文章id，
	private Integer otherSiteId;	//目标站的站点id，对应 Site.id
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "source_id", columnDefinition="int(11) comment '源站的文章id，news.id ，源网站发布了文章，同步到另一个网站。这里是源站中文章保存成功后的news.id'")
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name = "other_id", columnDefinition="int(11) comment '目标网站的文章id，'")
	public Integer getOtherId() {
		return otherId;
	}
	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}
	
	@Column(name = "other_site_id", columnDefinition="int(11) comment '目标站的站点id，对应 Site.id'")
	public Integer getOtherSiteId() {
		return otherSiteId;
	}
	public void setOtherSiteId(Integer otherSiteId) {
		this.otherSiteId = otherSiteId;
	}
	
	
}
