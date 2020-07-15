package com.xnx3.wangmarket.plugin.sell.vo;

import java.util.List;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;

/**
 * 查看我的提现申请记录列表
 * @author 管雷鸣
 *
 */
public class SellTiXianLogListVO extends BaseVO{
	private List<SellTiXianLog> list;	//数据结果
	private Page page;				//分页信息
	
	public List<SellTiXianLog> getList() {
		return list;
	}
	public void setList(List<SellTiXianLog> list) {
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
		return "SellTiXianLogListVO [list=" + list + ", page=" + page + "]";
	}

}
