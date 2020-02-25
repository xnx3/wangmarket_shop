package com.xnx3.wangmarket.shop.api.vo;

import java.util.Map;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 订单状态统计，当前登录用户，在某个店铺中，各个状态的订单分别有多少
 * @author 管雷鸣
 *
 */
public class OrderStateStatisticsVO extends BaseVO{
	/*
	 * key: state
	 * value: 这个状态下有几个订单
	 */
	private Map<String, Integer> map;
	
	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "OrderStateStatisticsVO [map=" + map + "]";
	}
	
}
/**
 * 每个状态-这个状态下有几个订单
 * @author 管雷鸣
 *
 */
class stateNumber{
	private String state;
	private int number;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}