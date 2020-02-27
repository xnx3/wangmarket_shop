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
			storeBean.setAddress(storeBean.getAddress());
			storeBean.setAddtime(storeBean.getAddtime());
			storeBean.setCity(storeBean.getCity());
			storeBean.setContacts(storeBean.getContacts());
			storeBean.setDistrict(storeBean.getDistrict());
			storeBean.setHead(storeBean.getHead());
			storeBean.setId(storeBean.getId());
			storeBean.setLatitude(storeBean.getLatitude());
			storeBean.setLongitude(storeBean.getLongitude());
			storeBean.setName(storeBean.getName());
			storeBean.setNotice(storeBean.getNotice());
			storeBean.setPhone(storeBean.getPhone());
			storeBean.setProvince(storeBean.getProvince());
			storeBean.setSale(storeBean.getSale());
			storeBean.setState(storeBean.getState());
		}
		this.store = storeBean;
	}
	
}
