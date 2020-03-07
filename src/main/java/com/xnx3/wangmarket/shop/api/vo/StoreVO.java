package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.StoreBean;
import com.xnx3.wangmarket.shop.core.entity.Store;

/**
 * 店铺信息
 * @author 管雷鸣
 *
 */
public class StoreVO extends BaseVO{
	private StoreBean store;

	public StoreBean getStore() {
		return store;
	}

	public void setStore(Store store) {
		StoreBean storeBean = new StoreBean();
		if(store != null) {
			storeBean.setAddress(store.getAddress());
			storeBean.setAddtime(store.getAddtime());
			storeBean.setCity(store.getCity());
			storeBean.setContacts(store.getContacts());
			storeBean.setDistrict(store.getDistrict());
			storeBean.setHead(store.getHead());
			storeBean.setId(store.getId());
			storeBean.setLatitude(store.getLatitude());
			storeBean.setLongitude(store.getLongitude());
			storeBean.setName(store.getName());
			storeBean.setNotice(store.getNotice());
			storeBean.setPhone(store.getPhone());
			storeBean.setProvince(store.getProvince());
			storeBean.setSale(store.getSale());
			storeBean.setState(store.getState());
		}
		this.store = storeBean;
	}
	
}
