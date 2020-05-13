package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Store;
import com.xnx3.wangmarket.shop.core.entity.StoreData;
import com.xnx3.wangmarket.shop.core.vo.bean.StoreBean;

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
//			storeBean.setNotice(store.getNotice());
			storeBean.setPhone(store.getPhone());
			storeBean.setProvince(store.getProvince());
			storeBean.setSale(store.getSale());
			storeBean.setState(store.getState());
		}
		this.store = storeBean;
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
			this.store = new StoreBean();
		}
		this.store.setNotice(storeData.getNotice());
	}
	
}
