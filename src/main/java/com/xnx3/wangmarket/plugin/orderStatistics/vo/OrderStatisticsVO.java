package com.xnx3.wangmarket.plugin.orderStatistics.vo;

import java.util.List;
import java.util.Map;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 订单统计
 * @author 管雷鸣
 *
 */
public class OrderStatisticsVO extends BaseVO{
	private List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "OrderStatisticsVO [list=" + list + "]";
	}
}
