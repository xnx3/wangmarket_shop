package com.xnx3.wangmarket.plugin.sitemap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.j2ee.pluginManage.PluginRegister;
import com.xnx3.j2ee.util.AttachmentUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.admin.Func;
import com.xnx3.wangmarket.admin.bean.NewsDataBean;
import com.xnx3.wangmarket.admin.entity.News;
import com.xnx3.wangmarket.admin.entity.Site;
import com.xnx3.wangmarket.admin.entity.SiteColumn;
import com.xnx3.wangmarket.admin.pluginManage.interfaces.GenerateSiteInterface;

/**
 * 生成整站时，根目录下自动生成sitemap.xml文件
 * @author 管雷鸣
 *
 */
@PluginRegister(menuTitle="sitemap", intro="当网站点击生成整站后，会自动生成当前网站的sitemap.xml文件", version="1.0", versionMin="5.0")
public class Plugin implements GenerateSiteInterface{
	@Override
	public void generateSiteFinish(HttpServletRequest request, Site site, Map<String, SiteColumn> siteColumnMap,
			Map<String, List<News>> newsMap, Map<Integer, NewsDataBean> newsDataMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<urlset\n"
				+ "\txmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n"
				+ "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
				+ "\txsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n"
				+ "\t\thttp://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">\n");
		
		//网站首页
		String indexUrl = "http://"+Func.getDomain(site);
		//xml加入首页
		sb.append(getSitemapUrl(indexUrl, "1.00"));
		
		//记录站内所有信息列表栏目下的文章列表。要用map遍历去重。 key:news.id  value:0，无意义
		Map<Integer, Integer> newsIdMap= new HashMap<Integer, Integer>();
		
		//遍历栏目
		for(Map.Entry<String, SiteColumn> entry : siteColumnMap.entrySet()){
			SiteColumn column = entry.getValue();
			
			//xml加入栏目页面
			sb.append(getSitemapUrl(indexUrl+"/"+column.getCodeName()+".html", "0.8"));
			
			if(column.getType() - SiteColumn.TYPE_LIST == 0 || column.getType() - SiteColumn.TYPE_NEWS == 0 || column.getType() - SiteColumn.TYPE_IMAGENEWS == 0){
				//是信息列表的栏目，那么加入详细信息文章页面
				//找到这个栏目的文章列表
				List<News> list = newsMap.get(column.getCodeName());
				for (int i = 0; i < list.size(); i++) {
					newsIdMap.put(list.get(i).getId(), 0);
				}
			}
		}
		
		//xml加入文章内容页面
		for(Map.Entry<Integer, Integer> entry : newsIdMap.entrySet()){
			sb.append(getSitemapUrl(indexUrl+"/"+entry.getKey()+".html", "0.4"));
		}
		
		//增加xml的末尾闭合标签
		sb.append("</urlset>");
		//生成 sitemap.xml
		AttachmentUtil.putStringFile("site/"+site.getId()+"/sitemap.xml", sb.toString());
	}
	

	/**
	 * SiteMap生成的url项 
	 * @param loc url
	 * @param priority 权重，如 1.00 、 0.5
	 * @return url标签的xml
	 */
	private String getSitemapUrl(String loc, String priority){
		if(loc.indexOf("http:") == -1){
			loc = "http://"+loc;
		}
		return "<url>\n"
				+ "\t<loc>"+loc+"</loc>\n"
				+ "\t<priority>"+priority+"</priority>\n"
				+ "</url>\n";
	}

	@Override
	public BaseVO generateSiteBefore(HttpServletRequest request, Site site) {
		// TODO Auto-generated method stub
		return null;
	}

}
