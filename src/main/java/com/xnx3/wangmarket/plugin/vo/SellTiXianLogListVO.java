package com.xnx3.wangmarket.plugin.vo;

import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.plugin.sell.entity.SellTiXianLog;

import java.util.List;

/**
 * 提现申请记录列表
 * @author 刘鹏
 */
public class SellTiXianLogListVO extends BaseVO {

    private List<SellTiXianLog> list;
    private Page page;

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
        return "SellTiXianLogListVO{" +
                "list=" + list +
                ", page=" + page +
                '}';
    }
}
