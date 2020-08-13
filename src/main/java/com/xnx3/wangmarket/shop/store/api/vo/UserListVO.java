package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.vo.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户列表使用
 * @author 刘鹏
 *
 */
public class UserListVO extends BaseVO{
	private List<UserBean> list;	//User的简化版只显示一部分信息

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<UserBean> getList() {
		return list;
	}

	public void setList(List<User> goodsList) {
		List<UserBean> beanList = new ArrayList<>();
		if(goodsList != null) {
			for (int i = 0; i < goodsList.size(); i++) {
				User user = goodsList.get(i);
				UserBean userBean = new UserBean();
				userBean.setHead(user.getHead());
				userBean.setId(user.getId());
				userBean.setNickname(user.getNickname());
				userBean.setPhone(user.getPhone());
				userBean.setUsername(user.getUsername());
				userBean.setRegtime(user.getRegtime());
				beanList.add(userBean);
			}
		}
		this.list = beanList;
	}

	@Override
	public String toString() {
		return "UserVO{" +
				"list=" + list +
				", page=" + page +
				'}';
	}
}
