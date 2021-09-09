package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;

/**
 * 列出store user list列表信息
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {}, nullSetDefaultValue = true)
public class StoreUserListVO extends BaseVO{
	private List<StoreUser> list;	//数据结果
	private Page page;				//分页信息

	public List<StoreUser> getList() {
		return list;
	}
	public void setList(List<StoreUser> list) {
		this.list = list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
