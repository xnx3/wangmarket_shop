package com.xnx3.wangmarket.shop.api.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;
import com.xnx3.wangmarket.shop.api.vo.bean.CarouselImageBean;

/**
 * 首页显示的轮播图列表
 * @author 管雷鸣
 *
 */
public class CarouselImageVO extends BaseVO{
	private List<CarouselImageBean> list;	//轮播图列表，已经根据rank排序好的

	public List<CarouselImageBean> getList() {
		return list;
	}

	public void setList(List<CarouselImage> list) {
		List<CarouselImageBean> beanList = new ArrayList<CarouselImageBean>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				CarouselImage carouselImage = list.get(i);
				CarouselImageBean carouselImageBean = new CarouselImageBean();
				carouselImageBean.setImageUrl(carouselImage.getImageUrl());
				carouselImageBean.setImgValue(carouselImage.getImgValue());
				carouselImageBean.setName(carouselImage.getName());
				carouselImageBean.setType(carouselImage.getType());
				carouselImageBean.setStoreId(carouselImage.getStoreid());
				beanList.add(carouselImageBean);
			}
		}
		this.list = beanList;
	}

	@Override
	public String toString() {
		return "CarouselImageVO [list=" + list + "]";
	}
	
}
