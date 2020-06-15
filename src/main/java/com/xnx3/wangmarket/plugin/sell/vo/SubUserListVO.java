package com.xnx3.wangmarket.plugin.sell.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.vo.bean.UserBean;

/**
 * 我的下级，我推荐的人列表
 * @author 管雷鸣
 */
public class SubUserListVO extends BaseVO{
	private List<UserBean> list;	//数据结果
	private Page page;				//分页信息

	public List<UserBean> getList() {
		return list;
	}
	public void setList(List<User> list) {
		List<UserBean> beanList = new ArrayList<UserBean>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				User item = list.get(i);
				UserBean bean = new UserBean();
				bean.setHead(item.getHead());
				bean.setId(item.getId());
				bean.setNickname(item.getNickname());
				bean.setPhone(item.getPhone());
				bean.setUsername(item.getUsername());
				bean.setRegtime(item.getRegtime());
				beanList.add(bean);
			}
		}
		this.list = beanList;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "StoreUserListVO [list=" + list + ", page=" + page + "]";
	}
}
