package com.xnx3.wangmarket.plugin.sell.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 我的下级，我推荐的人列表
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {"password","salt","version"}, nullSetDefaultValue = true)
public class SubUserListVO extends BaseVO{
	private List<User> list;	//数据结果
	private Page page;				//分页信息

	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
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
