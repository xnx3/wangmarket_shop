package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.UserBean;

/**
 * 获取用户信息使用
 * @author 管雷鸣
 *
 */
public class UserVO extends BaseVO{
	private UserBean user;

	public UserBean getUser() {
		return user;
	}

	public void setUser(User user) {
		UserBean userBean = new UserBean();
		if(user != null) {
			userBean.setHead(user.getHead());
			userBean.setId(user.getId());
			userBean.setNickname(user.getNickname());
			userBean.setPhone(user.getPhone());
			userBean.setUsername(user.getUsername());
		}
		this.user = userBean;
	}
	
	
}
