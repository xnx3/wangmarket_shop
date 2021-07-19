package com.xnx3.wangmarket.shop.store.vo;

import com.xnx3.j2ee.vo.BaseVO;

/**
 * 管理后台首页的一些数据，如左侧菜单
 * @author 管雷鸣
 *
 */
public class IndexVO extends BaseVO{
	private String MenuHtml;		//左侧菜单的html，以后再更改优化
	private boolean useTokenCodeLogin;	//true：是三方平台token+code登录的，那么不会显示修改密码以及退出登录菜单
	
	public String getMenuHtml() {
		return MenuHtml;
	}
	public void setMenuHtml(String menuHtml) {
		MenuHtml = menuHtml;
	}
	public boolean isUseTokenCodeLogin() {
		return useTokenCodeLogin;
	}
	public void setUseTokenCodeLogin(boolean useTokenCodeLogin) {
		this.useTokenCodeLogin = useTokenCodeLogin;
	}
	@Override
	public String toString() {
		return "IndexVO [MenuHtml=" + MenuHtml + ", useTokenCodeLogin=" + useTokenCodeLogin + "]";
	}
	
}
