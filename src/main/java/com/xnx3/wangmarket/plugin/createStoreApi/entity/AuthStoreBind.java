package com.xnx3.wangmarket.plugin.createStoreApi.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.xnx3.j2ee.entity.BaseEntity;


/**
 * 绑定这个店铺是属于哪个授权码创建的。一个授权码会对应多个店铺。同样，这个店铺是通过这个授权码过来创建的，这个店铺属于授权码的公司旗下
 * @author 管雷鸣
 */
@Entity()
@Table(name = "plugin_createstoreapi_authstorebind")
public class AuthStoreBind extends BaseEntity {
	private Integer id;			//店铺的id，对应 store.id
	private String auth;		//64位授权码
	
	@Id
	@Column(name = "id", columnDefinition="int(11) comment '店铺的id，对应 store.id'")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "auth", columnDefinition="char(64) comment '64位授权码'")
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "AuthStoreBind [id=" + id + ", auth=" + auth + "]";
	}

}