package com.xnx3.wangmarket.plugin.vo;

import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellCommissionLog;

import java.util.List;

/**
 *  佣金记录列表
 * @author 刘鹏
 */
public class SellCommissionLogListVO extends BaseVO {

    private List<SellCommissionLog> list;
    private Page page;

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
        return "SellCommissionLogListVO{" +
                "list=" + list +
                ", page=" + page +
                '}';
    }
}
