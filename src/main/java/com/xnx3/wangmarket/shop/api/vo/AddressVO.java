package com.xnx3.wangmarket.shop.api.vo;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.AddressBean;
import com.xnx3.wangmarket.shop.core.entity.Address;

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
		}
		this.address = addressBean;
	}

	@Override
	public String toString() {
		return "AddressVO [address=" + address + "]";
	}
	
}

