<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
//标题
String title = request.getParameter("title");
if(title == null ){
	title = Global.get("SITE_NAME");
}else{
	title=title+"_"+Global.get("SITE_NAME");
}

//关键字
String keywords = request.getParameter("keywords");
if(keywords == null ){
	keywords = Global.get("SITE_KEYWORDS");
}

//描述
String description = request.getParameter("description");
if(description == null ){
	description = Global.get("SITE_DESCRIPTION");
}
%><!DOCTYPE html>
<html lang="en" style="font-size:16px;">
<head>
<meta charset="utf-8">
<title><%=title %></title>
<meta name="keywords" content="<%=keywords %>" />
<meta name="description" content="<%=description %>" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="管雷鸣">

<!-- layer 、 layui -->
<link rel="stylesheet" href="/module/layui/css/layui.css">
<script src="/module/layui/layui.js"></script>
<script src="/module/msg/msg-1.0.1.js"></script>
<script src="https://res.weiunity.com/request/request.js"></script>
<!-- 商城及接口相关 -->
<script src="https://res.weiunity.com/shop/shop.js"></script>
<script>
//加载 layer 模块
layui.use('layer', function(){
  layer = layui.layer;
});
</script>

<script src="/js/jquery-2.1.4.js"></script>

<!-- order by 列表的排序 -->
<!--  <script src="/js/iw.js"></script>  -->

<style>

/*列表页头部form搜索框*/
.toubu_xnx3_search_form{
	padding-top:10px;
	padding-bottom: 10px;
}
/*列表页头部搜索，form里面的搜索按钮*/
.iw_list_search_submit{
	margin-left:22px;
}
/* 列表页，数据列表的table */
.iw_table{
	margin:0px;
}
/* 详情页，table列表详情展示，这里是描述，名字的td */
.iw_table_td_view_name{
	width:150px;
}
</style>
<script type="text/javascript">
shop.host = window.location.origin+'/';
/**
 * 网络请求，都是用此
 * api 请求的api接口，可以传入如 api.login_token
 * data 请求的数据，如 {"goodsid":"1"} 
 * func 请求完成的回调，传入如 function(data){}
 */
function post(api, data, func){
	data['storeid'] = shop.storeid;
	if(shop.getToken() != null){
		data['token'] = shop.getToken();
	}
	var headers = {'content-type':'application/x-www-form-urlencoded'};
	request.send(shop.host+api, data, func, 'post', true, headers, function(xhr){
		msg.failure('http code:'+xhr.status);
	});
}
/**
 * 获取网址的get参数。
 * @param name get参数名
 * @returns value
 */
function getUrlParams(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
/** 
 * 时间戳转化为年 月 日 时 分 秒 
 * number: 传入时间戳 如 1587653254
 * format：返回格式，如 'Y-M-D h:m:s'
*/
function formatTime(number,format) {
  var formateArr  = ['Y','M','D','h','m','s'];
  var returnArr   = [];
  var date = new Date(number * 1000);
  returnArr.push(date.getFullYear());
  returnArr.push(formatNumber(date.getMonth() + 1));
  returnArr.push(formatNumber(date.getDate()));
  returnArr.push(formatNumber(date.getHours()));
  returnArr.push(formatNumber(date.getMinutes()));
  returnArr.push(formatNumber(date.getSeconds()));
  for (var i in returnArr){
    format = format.replace(formateArr[i], returnArr[i]);
  }
  return format;
}
//时间戳转时间的数据转化
function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

//如果未登录，那么拦截，弹出提示，并跳转到登录页面
function checkLogin(data){
	if(data.result == '2'){
		//未登录
		msg.info('请先登录', function(){
			window.location.href="/store/login/login.jsp";
		});
	}
}
</script>
</head>
<body>