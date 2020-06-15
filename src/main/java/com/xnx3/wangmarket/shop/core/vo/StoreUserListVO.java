package com.xnx3.wangmarket.shop.core.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.StoreUser;
import com.xnx3.wangmarket.shop.core.vo.bean.StoreUserBean;

/**
 * 列出store user list列表信息
 * @author 管雷鸣
 */
public class StoreUserListVO extends BaseVO{
	private List<StoreUserBean> list;	//数据结果
	private Page page;				//分页信息

	public List<StoreUserBean> getList() {
		return list;
	}
	public void setList(List<StoreUser> list) {
		List<StoreUserBean> beanList = new ArrayList<StoreUserBean>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				StoreUser item = list.get(i);
				StoreUserBean bean = new StoreUserBean();
				bean.setUserId(item.getUserid());
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
	
	
}
