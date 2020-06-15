package com.xnx3.wangmarket.plugin.sell.vo;

import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;

/**
 * 佣金记录列表
 * @author 管雷鸣
 *
 */
public class CommissionLogListVO extends BaseVO{
	private List<SellCommissionLog> list;	//列表
	private Page page;				//分页信息

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<SellCommissionLog> getList() {
		return list;
	}

	public void setList(List<SellCommissionLog> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "CommissionLogListVO [list=" + list + ", page=" + page + "]";
	}
	
}
