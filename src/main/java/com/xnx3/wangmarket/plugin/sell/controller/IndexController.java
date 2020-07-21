package com.xnx3.wangmarket.plugin.sell.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SqlCacheService;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.util.SystemUtil;
import com.xnx3.wangmarket.plugin.sell.entity.SellStoreSet;
import com.xnx3.wangmarket.plugin.sell.vo.SellStoreSetVO;
import com.xnx3.wangmarket.plugin.sell.vo.ShareUrlVO;
import com.xnx3.wangmarket.plugin.sell.vo.SubUserListVO;
import com.xnx3.wangmarket.shop.core.pluginManage.controller.BasePluginController;

/**
 * 二级分销，用户端接口
 * @author 管雷鸣
 */
@Controller(value="SellIndexPluginController")
@RequestMapping("/plugin/sell/")
public class IndexController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private SqlCacheService sqlCacheService;
	
	/**
	 * 获取分享的url
	 * @param redirectUrl 分享出去后，别人点击分享链接进入的页面url，填写如 http://demo.imall.net.cn/index.html
	 * @param storeid 当前商铺的id
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="gainShareUrl${api.suffix}" ,method= {RequestMethod.POST})
	public ShareUrlVO gainShareUrl(HttpServletRequest request, Model model,
			@RequestParam(value = "redirectUrl", required = false, defaultValue = "") String redirectUrl,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		User user = getUser();	//当前登录的用户
		
		// redirectUrl 判断，若为空，给默认值，跳转到对方来源网址的首页
		if(redirectUrl.length() == 0){
			redirectUrl = request.getScheme()+ "://" + request.getServerName();
			if(request.getServerPort() != 80){
				redirectUrl = redirectUrl + ":" + request.getServerPort();
			}
			redirectUrl = redirectUrl + "/";
		}
		
		ShareUrlVO vo = new ShareUrlVO();
		vo.setWeixinH5Url(SystemUtil.get("MASTER_SITE_URL") + "plugin/weixinH5Auth/hiddenAuthJump.do?storeid="+storeid+"&referrerid="+user.getId()+"&url="+redirectUrl);
		
		//H5 Url
		if(redirectUrl.indexOf("?") > -1){
			redirectUrl = redirectUrl + "&";
		}else{
			redirectUrl = redirectUrl + "?";
		}
		redirectUrl = redirectUrl + "storeid="+storeid+"&referrerid="+user.getId();
		vo.setH5Url(redirectUrl);
		
		//日志
		ActionLogUtil.insert(request, "获取二级分销推广链接");
		return vo;
	}
	
	/**
	 * 查看自己的直属下级列表，自己推荐的一级下级，二级是不体现的
	 * @param storeid 当前商铺的id
	 */
	@ResponseBody
	@RequestMapping(value="/subUserList${api.suffix}",method= {RequestMethod.POST})
	public SubUserListVO list(HttpServletRequest request,Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		SubUserListVO vo = new SubUserListVO();
		User user = getUser();
		
		//创建Sql
		Sql sql = new Sql(request);
		//配置查询那个表
		sql.setSearchTable("shop_store_user");
		//查询条件
		sql.appendWhere("shop_store_user.referrerid = '"+user.getId()+"_"+storeid+"'");
		//配置按某个字端搜索内容
//		sql.setSearchColumn(new String[] {"typeid"});
		// 查询数据表的记录总条数
		int count = sqlService.count("shop_store_user", sql.getWhere());
		
		// 配置每页显示30条
		Page page = new Page(count, 30, request);
		// 查询出总页数
		sql.setSelectFromAndPage("SELECT user.* FROM shop_store_user,user ", page);
		sql.appendWhere("user.id = shop_store_user.userid");
		//选择排序方式 当用户没有选择排序方式时，系统默认降序排序
//		sql.setDefaultOrderBy("id DESC");
		System.out.println(sql.getSql());
		// 按照上方条件查询出该实体总数 用集合来装
		List<User> list = sqlService.findBySql(sql,User.class);
		
		vo.setList(list);
		vo.setPage(page);
		
		//日志记录
		ActionLogUtil.insert(request, getUserId(), "查看自己的下级列表");
		return vo;
	}

	/**
	 * 获取当前店铺的推广规则，比如是否开启二级分销，一级分销比例多少，二级比例多少
	 * @param storeid 当前商铺的id
	 * @author 管雷鸣
	 */
	@ResponseBody
	@RequestMapping(value="getSellSet${api.suffix}" ,method= {RequestMethod.POST})
	public SellStoreSetVO getSellSet(HttpServletRequest request, Model model,
			@RequestParam(value = "storeid", required = false, defaultValue = "0") int storeid) {
		SellStoreSetVO vo = new SellStoreSetVO();
		
		SellStoreSet storeSet = sqlCacheService.findById(SellStoreSet.class, storeid);
		if(storeSet == null){
			storeSet = new SellStoreSet();
			storeSet.setIsUse(SellStoreSet.IS_USE_NO);
		}
		vo.setSellSet(storeSet);
		//日志
		ActionLogUtil.insert(request, "获取店铺分销规则", storeSet.toString());
		return vo;
	}
	
}