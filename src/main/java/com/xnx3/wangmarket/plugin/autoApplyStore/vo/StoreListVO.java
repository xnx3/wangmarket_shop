package com.xnx3.wangmarket.plugin.autoApplyStore.vo;

import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;

import java.util.List;

/**
 * 开店列表
 * @author 刘鹏
 */
public class StoreListVO extends BaseVO {

    private List<Store> storelist;
    private Page page;

    public List<Store> getStorelist() {
        return storelist;
    }

    public void setStorelist(List<Store> storelist) {
        this.storelist = storelist;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "StoreListVO{" +
                "storelist=" + storelist +
                ", page=" + page +
                '}';
    }
}
