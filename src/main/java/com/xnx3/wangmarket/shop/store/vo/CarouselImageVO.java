package com.xnx3.wangmarket.shop.store.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;

/**
 * 轮播图信息
 * @author 刘鹏
 *
 */
public class CarouselImageVO extends BaseVO {
   private CarouselImage carouselImage;    //轮播图

    public CarouselImage getCarouselImage() {
        return carouselImage;
    }

    public void setCarouselImage(CarouselImage carouselImage) {
        this.carouselImage = carouselImage;
    }

    @Override
    public String toString() {
        return "CarouselImageVo{" +
                "carouselImage=" + carouselImage +
                '}';
    }
}
