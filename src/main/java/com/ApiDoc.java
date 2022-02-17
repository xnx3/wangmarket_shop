package com;

import com.xnx3.doc.JavaDoc;

public class ApiDoc {
	public static void main(String[] args) {
		JavaDoc doc = new com.xnx3.doc.JavaDoc("com.xnx3.wangmarket.shop.api.controller");
		//doc.templatePath = "/Users/apple/Downloads/javadoc/";
		doc.templatePath = "http://res.zvo.cn.obs.cn-north-4.myhuaweicloud.com/javadoc/v1.10/";
		
		doc.name = "云商城用户端-api文档";
		doc.welcome = "用户端的API文档说明，可以通过此文档，来自由定制商城界面";
		doc.domain = "http://shop.imall.net.cn";
		doc.version = "1.14";
//		
		doc.generateHtmlDoc();
	}
}
