package com.xnx3.wangmarket.shop.store.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.CarouselImage;
import com.xnx3.j2ee.util.Page;

import java.util.List;

/**
 * 轮播图列表
 * @author 刘鹏
 *
 */
public class CarouselImageListVO extends BaseVO {
    private List<CarouselImage> list; //轮播图集合

    private Page page;  //分页信息

    public List<CarouselImage> getList() {
        return list;
    }

    public void setList(List<CarouselImage> list) {
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
        return "CarouselImageListVo{" +
                "list=" + list +
                ", page=" + page +
                '}';
    }
}
