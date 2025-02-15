package com.xnx3.wangmarket.shop.core.vo;

import com.xnx3.j2ee.system.responseBody.ResponseBodyManage;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;

/**
 * 返回某个地址信息
 * @author 管雷鸣
 */
@ResponseBodyManage(ignoreField = {}, nullSetDefaultValue = true)
public class AddressVO extends BaseVO {
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "AddressVO [address=" + address + "]";
	}
}

