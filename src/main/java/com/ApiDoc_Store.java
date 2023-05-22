package com;

import com.xnx3.doc.JavaDoc;

/**
 * 商家店铺的API DOC
 * @author 管雷鸣
 *
 */
public class ApiDoc_Store {
	public static void main(String[] args) {
		JavaDoc doc = new com.xnx3.doc.JavaDoc("com.xnx3.wangmarket.shop.store.controller");
		//doc.templatePath = "/Users/apple/Downloads/javadoc/";
//		doc.templatePath = "http://res.zvo.cn.obs.cn-north-4.myhuaweicloud.com/javadoc/v1.10/";
		
//		doc.name = "云商城用户端";
//		//doc.welcome = "用户端的API文档说明，可以通过此文档，来自由定制开发用户端商城";
//		doc.domain = "http://www.shop.zvo.cn";
//		doc.version = "2.0.0.20230522";
//		
		doc.name = "云商城商家端";
		//doc.welcome = "用户端的API文档说明，可以通过此文档，来自由定制开发用户端商城";
		doc.domain = "http://www.shop.zvo.cn";
		doc.version = "2.0.0.20230522";
		
		
		doc.generateHtmlDoc();
	}
}
