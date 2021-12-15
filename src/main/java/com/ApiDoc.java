package com;

import com.xnx3.doc.JavaDoc;

public class ApiDoc {
	public static void main(String[] args) {
		JavaDoc doc = new com.xnx3.doc.JavaDoc("com.xnx3.wangmarket.shop.api.controller");
//		doc.templatePath = "/Users/apple/Downloads/javadoc/";
		doc.templatePath = "http://res.zvo.cn.obs.cn-north-4.myhuaweicloud.com/javadoc/v1.8/";
		
//		doc.name = "网市场云商城-用户端-API文档";
//		doc.domain = "http://shop.imall.net.cn";
//		doc.version = "1.7";
//		doc.token = ""; 
//		
		doc.generateHtmlDoc();
	}
}
