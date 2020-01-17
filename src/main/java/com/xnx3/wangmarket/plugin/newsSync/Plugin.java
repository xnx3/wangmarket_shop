package com.xnx3.wangmarket.plugin.newsSync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.SpringUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.bean.NewsDataBean;
import com.xnx3.wangmarket.admin.entity.News;
import com.xnx3.wangmarket.admin.entity.NewsData;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.entity.SiteColumn;
import com.xnx3.wangmarket.admin.pluginManage.interfaces.GenerateSiteInterface;
import com.xnx3.wangmarket.admin.pluginManage.interfaces.NewsInterface;
import com.xnx3.wangmarket.admin.service.TemplateService;
import com.xnx3.wangmarket.admin.service.impl.TemplateServiceImpl;
import com.xnx3.wangmarket.plugin.newsSync.entity.NewsBind;
import com.xnx3.wangmarket.plugin.newsSync.entity.SiteBind;
import com.xnx3.wangmarket.plugin.newsSync.util.SessionUtil;

/**
 * 网站内容同步插件，可以将一个网站的内容同步到另一个网站
 * @author 管雷鸣
 *
 */
@PluginRegister(menuTitle="内容同步", menuHref="/plugin/newsSync/index.do", intro="可以指定一个其他网站，当对方网站内容管理中，文章有增加、修改、删除时，会自动将最新的文章同步到当前网站。", version="1.0", versionMin="5.0", applyToCMS=true)
public class Plugin implements GenerateSiteInterface, NewsInterface{
	private SqlService sqlService;
	public Plugin() {
		this.sqlService = SpringUtil.getSqlService();
	}

	@Override
	public News newsSaveBefore(HttpServletRequest request, News news) {
		return null;
	}

	@Override
	public void newsSaveFinish(HttpServletRequest request, News news) {
		//找到有多少网站对应，当前这个网站是否设定了要同步到其他站点
		List<SiteBind> siteBindList = getSiteBindList();
		if(siteBindList.size() == 0){
			//不需要同步
			return;
		}
		
		//有文章保存，先判断以下这个文章有没有对应过，如果没有对应过，那这个文章就是新增的
		List<NewsBind> list = sqlService.findByProperty(NewsBind.class, "sourceId", news.getId());
		if(list.size() == 0){
			//目标网站中，没有与之对应的文章，那么有两种情况，1新增，2独立页面的修改
			
			//目标网站的站点id
			int otherSiteId = siteBindList.get(0).getOtherId(); 
			//同步到的目标网站的栏目id， sitecolumn.id
			int otherSiteColumnId = getOtherSiteColumnId(news.getCid());
			
			//判断目标站中是否有这个对应的栏目
			if(otherSiteColumnId == 0){
				//没有栏目对应，退出
				return;
			}
			
			News otherNews;	//目标网站要保存的News
			//先判断是否是独立页面的修改
			if(news.getType() - SiteColumn.TYPE_ALONEPAGE == 0 || news.getType() - SiteColumn.TYPE_PAGE == 0){
				//是独立页面，只是第一次修改独立页面的内容，newsbind 还没有关联。
				
				//查询出目标网站的news，跟其对应
				otherNews = sqlService.findAloneBySqlQuery("SELECT * FROM news WHERE cid = "+otherSiteColumnId, News.class);
				if(otherNews == null){
					//对方网站独立页面栏目下没有文章，可能是模版式的，直接退出
					return;
				}
			}else{
				//列表页面
				otherNews = new News();
			}
			newsClone(news, otherNews);	//数据复制
			otherNews.setCid(otherSiteColumnId);
			otherNews.setSiteid(otherSiteId);
			sqlService.save(otherNews);
			
			//保存 newsbind 对应关系
			NewsBind newsBind = new NewsBind();
			newsBind.setSourceId(news.getId());
			newsBind.setOtherId(otherNews.getId());
			newsBind.setOtherSiteId(otherSiteId);
			sqlService.save(newsBind);
		}else{
			//修改
			
			String otherNewsIds = getOtherNewsId(list);
			List<News> otherNewsList = sqlService.findBySqlQuery("SELECT * FROM news WHERE id IN("+otherNewsIds+");", News.class);
			for (int i = 0; i < otherNewsList.size(); i++) {
				News otherNews = otherNewsList.get(i);
				newsClone(news, otherNews);	//数据复制
				sqlService.save(otherNews);
			}
		}
		
	}

	@Override
	public NewsData newsDataSaveBefore(HttpServletRequest request, NewsData newsData) {
		return null;
	}

	@Override
	public void newsDataSaveFinish(HttpServletRequest request, NewsData newsData) {
		//找到有多少网站对应，当前这个网站是否设定了要同步到其他站点
		List<SiteBind> siteBindList = getSiteBindList();
		if(siteBindList.size() == 0){
			//不需要同步
			return;
		}
		
		//有文章保存，先判断以下这个文章有没有对应过，如果没有对应过，那这个文章就是新增的
		List<NewsBind> list = sqlService.findByProperty(NewsBind.class, "sourceId", newsData.getId());
		if(list.size() == 0){
			//新增，这个不可能新增，因为会先保存 news ，保存了就已经有了
			
		}else{
			//修改
			
			//目标网站的news.id
			int otherNewsId = list.get(0).getOtherId();
			
			NewsData otherNewsData = sqlService.findById(NewsData.class, otherNewsId);
			if(otherNewsData == null){
				otherNewsData = new NewsData();
			}
			otherNewsData.setExtend(newsData.getExtend());
			otherNewsData.setText(newsData.getText());
			otherNewsData.setId(otherNewsId);
			sqlService.save(otherNewsData);
		}
	}

	@Override
	public void newsDeleteFinish(HttpServletRequest request, News news) {
		List<SiteBind> sitebindList = getSiteBindList();
		if(sitebindList.size() == 0){
//			没有
			return;
		}
		
		//取出原站的这篇文章，同步哪些文章
		List<NewsBind> list = sqlService.findByProperty(NewsBind.class, "sourceId", news.getId());
		if(list.size() == 0){
			//没有绑定的文章，直接退出
			return;
		}
		//取出目标news.id，多个用","拼接
		String newsids = getOtherNewsId(list);
		
		sqlService.executeSql("DELETE FROM news WHERE id IN("+newsids+");");
		sqlService.executeSql("DELETE FROM news_data WHERE id IN("+newsids+");");
	}

	@Override
	public BaseVO generateSiteBefore(HttpServletRequest request, Site site) {
		return null;
	}

	@Override
	public void generateSiteFinish(HttpServletRequest request, Site site, Map<String, SiteColumn> siteColumnMap,
			Map<String, List<News>> newsMap, Map<Integer, NewsDataBean> newsDataMap) {
		List<SiteBind> siteBindList = getSiteBindList();
		if(siteBindList.size() == 0){
			return;
		}
		
		TemplateService templateService = SpringUtil.getBean(TemplateServiceImpl.class);
		for (int i = 0; i < siteBindList.size(); i++) {
			SiteBind siteBind = siteBindList.get(i);
			templateService.generateSiteHTML(request, sqlService.findById(Site.class, siteBind.getOtherId()));
		}
	}
	
	/**
	 * 传入绑定的文章的目标网站文章id
	 * @param list 文章绑定列表
	 * @return 格式如 1,2,3  多个用, 分割，可以直接加入 sql 的 in 进行查询
	 */
	private String getOtherNewsId(List<NewsBind> list){
		//取出目标news.id，多个用","拼接
		StringBuffer newsidSB = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			NewsBind newsBind = list.get(i);
			if(newsidSB.length() > 0){
				newsidSB.append(",");
			}
			newsidSB.append(newsBind.getOtherId()+"");
		}
		return newsidSB.toString();
	}
	
	/**
	 * 获取当前站点的绑定。先从session中取，没有再从数据库取
	 */
	private List<SiteBind> getSiteBindList(){
		List<SiteBind> list = SessionUtil.getSiteBindList();
		if(list == null){
			Site site = SessionUtil.getSite();	//当前登录的网站
			if(site == null){
				return new ArrayList<SiteBind>();
			}
			
			list = sqlService.findByProperty(SiteBind.class, "sourceId", site.getId());
			SessionUtil.setSiteBindList(list);
		}
		return list;
	}
	
	/**
	 * News 复制。将 news 的数据赋予 otherNews。
	 * 其中cid、id、siteid这几个是不会复制的
	 * @param news {@link News} 源
	 * @param otherNews {@link News} 将news的复制到这里
	 * @return 克隆的news
	 */
	private void newsClone(News news, News otherNews){
		otherNews.setAddtime(news.getAddtime());
//		otherNews.setCid(otherSiteColumnId);
		otherNews.setCommentnum(news.getCommentnum());
		otherNews.setIntro(news.getIntro());
		otherNews.setLegitimate(news.getLegitimate());
		otherNews.setOpposenum(news.getOpposenum());
		otherNews.setReadnum(news.getReadnum());
		otherNews.setReserve1(news.getReserve1());
		otherNews.setReserve2(news.getReserve2());
//		otherNews.setSiteid(otherSiteId);
		otherNews.setStatus(news.getStatus());
		otherNews.setTitle(news.getTitle());
		otherNews.setTitlepic(news.getTitlepic());
		otherNews.setType(news.getType());
		otherNews.setUserid(news.getUserid());
	}
	
	/**
	 * 传入源站栏目的id，取得目标网站栏目对应的id。如果返回0，则是目标站没有对应栏目
	 * @param sourceNewsCid 源站栏目的id， siteColumn.id
	 * @return 如果返回0，则是目标站没有对应栏目
	 */
	private int getOtherSiteColumnId(int sourceNewsCid){
		//先查询出当前文章所属的栏目的栏目代码，然后到目标网站中，找到这个栏目代码对应的栏目id是多少
		String sqlGainOtherColumnId = "SELECT site_column.id "
				+ "FROM site_column "
				+ "WHERE code_name = ("
				+ "SELECT site_column.code_name FROM site_column WHERE site_column.id = "+sourceNewsCid
				+ ") AND siteid = 222";
		List<Map<String, Object>> otherColumnIdList = sqlService.findMapBySqlQuery(sqlGainOtherColumnId);
		if(otherColumnIdList.size() == 0){
			//这篇文章没有栏目可以同步的
			return 0;
		}
		//同步到的目标网站的sitecolumn.id
		int otherSiteColumnId = (int) otherColumnIdList.get(0).get("id"); 
		return otherSiteColumnId;
	}
}
