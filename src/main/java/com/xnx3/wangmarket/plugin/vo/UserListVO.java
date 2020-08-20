package com.xnx3.wangmarket.plugin.vo;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.vo.BaseVO;

import java.util.List;

/**
 * 当前商城的子用户列表
 * @author 刘鹏
 */
public class UserListVO extends BaseVO {

    private List<User> list;

    private Page page;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
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
        return "UserListVO{" +
                "list=" + list +
                ", page=" + page +
                '}';
    }
}
