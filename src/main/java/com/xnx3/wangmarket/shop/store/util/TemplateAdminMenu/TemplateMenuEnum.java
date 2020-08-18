package com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu;
/**
 * 网站管理后台的左侧菜单项的id唯一标示
 * @author 管雷鸣
 *
 */
public enum TemplateMenuEnum {
	SYSTEM_StoreSet("storeset", "商家设置", "javascript:loadUrl('/shop/store/index/welcome.do');", "&#xe60b;", ""),
	SYSTEM_CarouselImage("carouselImage", "轮播图", "javascript:loadUrl('/store/carouselImage/list.jsp');", "&#xe634;", ""),
	SYSTEM_User("user", "用户管理", "javascript:loadUrl('/shop/store/user/list.do');", "&#xe66f;", ""),
	SYSTEM_GoodsType("goodsTyle", "商品分类", "javascript:loadUrl('/store/goodsType/list.jsp');", "&#xe62a;", ""),
	SYSTEM_Goods("goods", "商品管理", "javascript:loadUrl('/store/goods/list.jsp');", "&#xe600;", ""),
	SYSTEM_Order("order", "订单管理", "javascript:loadUrl('/store/order/list.jsp');", "&#xe60a;", ""),
	SYSTEM_PaySet("paySet", "支付设置", "javascript:loadUrl('/store/paySet/index.jsp');", "&#xe620;", ""),
	SYSTEM_OrderRule("paySet", "订单设置", "javascript:loadUrl('/store/orderRule/index.jsp');", "&#xe620;", ""),
	SYSTEM_SmsSet("smsSet", "短信接口", "javascript:loadUrl('/store/sms/index.jsp');", "&#xe678;", ""),
	SYSTEM_Api("Api", "API接口", "javascript:loadUrl('http://shop.wang.market/');", "&#xe620;", ""),
	
	SYSTEM_XiuGaiMiMa("xiugaimima", "修改密码", "javascript:updatePassword();", "", ""),

	PLUGIN("plugin", "功能插件", "javascript:;", "&#xe857;", "");
	
	
	public final String id;	//id，如 jibenxinxi 一级menu是直接就是id，但是二级不是直接用，加tag前缀，如 dd_jibenxinxi 、 a_jibenxinxi
	public final String name;	//菜单所显示的文字，如 基本信息
	public final String href;	//a标签的href的值
	public final String icon;	//一级菜单才有，也就是顶级菜单，前面会有个图标。这个就是。值如：&#xe620;
	public final String parentid;	//父菜单，上级菜单的id，如果已经是顶级菜单，这里没有值，为“”空字符串
	
	private TemplateMenuEnum(String id, String name, String href, String icon, String parentid) { 
		this.id = id; 
		this.name = name;
		this.href = href;
		this.icon = icon;
		this.parentid = parentid;
	}
}
