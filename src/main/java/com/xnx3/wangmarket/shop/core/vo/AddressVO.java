package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.vo.bean.AddressBean;

/**
 * 返回某个地址信息
 * @author 管雷鸣
 */
public class AddressVO extends BaseVO {
	private AddressBean address;

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		AddressBean addressBean = new AddressBean();
		if(address != null) {
			addressBean.setAddress(address.getAddress());
			addressBean.setDefaultUse(address.getDefaultUse());
			addressBean.setId(address.getId());
			addressBean.setLatitude(address.getLatitude());
			addressBean.setLongitude(address.getLongitude());
			addressBean.setPhone(address.getPhone());
			addressBean.setUsername(address.getUsername());
			addressBean.setSheng(address.getSheng());
			addressBean.setShi(address.getShi());
			addressBean.setQu(address.getQu());
			addressBean.setHouse(address.getHouse());
		}
		this.address = addressBean;
	}

	@Override
	public String toString() {
		return "AddressVO [address=" + address + "]";
	}
	
}

