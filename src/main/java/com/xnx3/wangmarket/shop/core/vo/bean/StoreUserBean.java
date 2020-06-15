package com.xnx3.wangmarket.shop.core.vo.bean;

import com.xnx3.wangmarket.shop.core.entity.StoreUser;

/**
 * {@link StoreUser} 的简化，不必要返回的数据去掉
 * @author 管雷鸣
 */
public class StoreUserBean implements java.io.Serializable{
	private Integer userid;	//用户的id

	public Integer getUserId() {
		return userid;
	}
	public void setUserId(Integer userid) {
		if(userid == null){
			this.userid = 0;
		}else{
			this.userid = userid;
		}
	}
}