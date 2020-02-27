package com.xnx3.wangmarket.shop.api.vo;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.api.vo.bean.AddressBean;
import com.xnx3.wangmarket.shop.core.entity.Address;

/**
 * 用户地址
 * @author 管雷鸣
 */
public class AddressListVO extends BaseVO {
	private List<AddressBean> addressList;	//当前用户下的所有地址列表。注意，这里面不包含用户默认使用的那个地址
	private AddressBean defaultAddress;		//默认选中的地址
	
	public List<AddressBean> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		List<AddressBean> beanList = new ArrayList<AddressBean>();
		if(addressList != null) {
			for (int i = 0; i < addressList.size(); i++) {
				Address address = addressList.get(i);
				AddressBean addressBean = new AddressBean();
				addressBean.setAddress(address.getAddress());
				addressBean.setDefaultUse(address.getDefaultUse());
				addressBean.setId(address.getId());
				addressBean.setLatitude(address.getLatitude());
				addressBean.setLongitude(address.getLongitude());
				addressBean.setPhone(address.getPhone());
				addressBean.setUsername(address.getUsername());
				beanList.add(addressBean);
			}
		}
		this.addressList = beanList;
	}
	public AddressBean getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(Address defaultAddress) {
		AddressBean addressBean = new AddressBean();
		if(defaultAddress != null) {
			addressBean.setAddress(defaultAddress.getAddress());
			addressBean.setDefaultUse(defaultAddress.getDefaultUse());
			addressBean.setId(defaultAddress.getId());
			addressBean.setLatitude(defaultAddress.getLatitude());
			addressBean.setLongitude(defaultAddress.getLongitude());
			addressBean.setPhone(defaultAddress.getPhone());
			addressBean.setUsername(defaultAddress.getUsername());
		}
		this.defaultAddress = addressBean;
	}
	
}

