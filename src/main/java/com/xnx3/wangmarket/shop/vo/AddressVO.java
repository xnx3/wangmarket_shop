package com.xnx3.wangmarket.shop.vo;

import java.util.List;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Address;

/**
 * 用户地址
 * @author 关光礼
 */
public class AddressVO extends BaseVO {
	
	private List<Address> addressList;
	private Address address;
	
	public List<Address> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}

