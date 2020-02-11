package com.xnx3.wangmarket.shop.vo;

import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.CarouselImage;

/**
 * 首页显示的轮播图列表
 * @author 管雷鸣
 *
 */
public class CarouselImageVO extends BaseVO{
	private List<CarouselImage> list;	//轮播图列表，已经根据rank排序好的

	public List<CarouselImage> getList() {
		return list;
	}

	public void setList(List<CarouselImage> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "CarouselImageVO [list=" + list + "]";
	}
	
}
