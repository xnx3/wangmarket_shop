package com.xnx3.wangmarket.shop.core.vo;

import java.util.List;
import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;

/**
 * 用户地址
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {}, nullSetDefaultValue = true)
public class AddressListVO extends BaseVO {
	private List<Address> addressList;	//当前用户下的所有地址列表。注意，这里面不包含用户默认使用的那个地址
	private Address defaultAddress;		//默认选中的地址
	
	public List<Address> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	public Address getDefaultAddress() {
		return defaultAddress;
	}
	public void setDefaultAddress(Address defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}

