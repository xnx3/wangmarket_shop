package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreData;

/**
 * 店铺信息
 * @author 管雷鸣
 *
 */
@ResponseBodyManage(ignoreField = {"userid","referrerUserid","version"}, nullSetDefaultValue = true)
public class StoreVO extends BaseVO{
	private Store store;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 设置 storeData 变长表的信息
	 * @param storeData 变长表的信息
	 */
	public void setStoreData(StoreData storeData) {
		if(storeData == null){
			return;
		}
		if(this.store == null){
			this.store = new Store();
		}
		this.store.setNotice(storeData.getNotice());
	}
	
}
