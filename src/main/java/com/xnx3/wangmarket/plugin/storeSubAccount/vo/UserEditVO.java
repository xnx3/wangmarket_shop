package com.xnx3.wangmarket.plugin.storeSubAccount.vo;


import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.store.util.TemplateAdminMenu.MenuBean;

import java.util.Map;

/**
 * 增加/编辑用户信息
 * @author 刘鹏
 */
public class UserEditVO extends BaseVO {

    private User user;
    private Map<String, MenuBean> menuMap;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, MenuBean> getMenuMap() {
        return menuMap;
    }

    public void setMenuMap(Map<String, MenuBean> menuMap) {
        this.menuMap = menuMap;
    }

    @Override
    public String toString() {
        return "UserEditVO{" +
                "user=" + user +
                ", menuMap=" + menuMap +
                '}';
    }
}
