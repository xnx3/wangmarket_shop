package com;

import com.xnx3.doc.JavaDoc;

public class ApiDoc {
	public static void main(String[] args) {
		JavaDoc doc = new com.xnx3.doc.JavaDoc("com.xnx3.wangmarket.shop.api.controller");
		//doc.templatePath = "/Users/apple/Downloads/javadoc/";
		doc.templatePath = "http://res.zvo.cn.obs.cn-north-4.myhuaweicloud.com/javadoc/v1.10/";
		
		doc.name = "网市场云商城-用户端-API文档";
		doc.welcome = "网市场云建站系统用户端API文档说明";
		doc.domain = "http://shop.imall.net.cn";
		doc.version = "1.13";
//		
		doc.generateHtmlDoc();
	}
}
