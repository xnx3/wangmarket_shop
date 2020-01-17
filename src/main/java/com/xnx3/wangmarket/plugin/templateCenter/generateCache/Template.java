package com.xnx3.wangmarket.plugin.templateCenter.generateCache;

import org.springframework.stereotype.Component;

import com.xnx3.j2ee.generateCache.BaseGenerate;

/**
 * 模版相关
 * @author 管雷鸣
 *
 */
@Component
public class Template extends BaseGenerate{
	
	public Template() {
		iscommon();
		type();
	}
	
	public void iscommon(){
		createCacheObject("iscommon");
		cacheAdd(com.xnx3.wangmarket.admin.entity.Template.ISCOMMON_YES, "公共");
		cacheAdd(com.xnx3.wangmarket.admin.entity.Template.ISCOMMON_NO, "私有");
		generateCacheFile();
	}
	
	/**
	 * 网站类型
	 */
	public void type(){
		createCacheObject("type");
		
		String str = "广告设计|学校培训|五金制造|门窗卫浴|IT互联网|化工环保|建筑能源|智能科技|房产物业|金融理财|工商法律|人力产权|生活家政|服装饰品|医疗保健|装修装饰|摄影婚庆|家具数码|茶酒果蔬|组织政府|餐饮酒店|旅游服务|汽车汽配|畜牧种植|体育健身|儿童玩具|个人博客|文档资料";
		String[] strs = str.split("\\|");
		for (int i = 0; i < strs.length; i++) {
			cacheAdd(i+"", strs[i]);
		}
		
		generateCacheFile();
	}
}
