<%@page import="com.xnx3.wangmarket.Authorization"%>
<%@page import="com.xnx3.j2ee.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="管理后台"/>
</jsp:include>
<script src="${STATIC_RESOURCE_PATH}js/fun.js"></script>
<script src="/js/admin/commonedit.js?v="></script>

<style>
body{margin: 0;padding: 0px;height: 100%;overflow: hidden;}
#editPanel{
	position: absolute;
    top: 0px;
    width:150px;
}
#editPanel span{
	width:100%;
}

.menu{
	width:150px;
	height:100%;
	background-color: #393D49;
	position: absolute;
}
.menu ul li{
	cursor: pointer;
}

/*左侧的一级菜单的图标*/
.firstMenuIcon{
	font-size:16px;
	padding-right:8px;
	font-weight: 700;
}
/*左侧的一级菜单的文字描述*/
.firstMenuFont{
	
}

/* 二级菜单 */
.menu .layui-nav-item .layui-nav-child .subMenuItem{
	padding-left:48px;
	font-size: 13px;
}
.layui-nav-tree .layui-nav-item a:hover {
	background-color: #FFFFFF;
}
.layui-nav-tree .layui-nav-child dd.layui-this, .layui-nav-tree .layui-nav-child dd.layui-this a, .layui-nav-tree .layui-this, .layui-nav-tree .layui-this>a, .layui-nav-tree .layui-this>a:hover{
	background-color: #FFFFFF;
}

/** 功能插件的一级菜单 **/
.layui-nav-itemed>a, .layui-nav-tree .layui-nav-title a, .layui-nav-tree .layui-nav-title a:hover{
	color: rgb(51, 51, 51)!important;;
}
.layui-nav .layui-nav-more{
	border-top-color: #612525ba;
}
.layui-nav .layui-nav-mored, .layui-nav-itemed>a .layui-nav-more {
	border-color: transparent transparent #612525ba;
}

/** 功能插件二级菜单 **/
.layui-nav-tree .layui-nav-child dd.layui-this, .layui-nav-tree .layui-nav-child dd.layui-this a, .layui-nav-tree .layui-this, .layui-nav-tree .layui-this>a, .layui-nav-tree .layui-this>a:hover {
	color: rgb(51, 51, 51);
}
.layui-nav-itemed>.layui-nav-child {
    display: block;
    padding: 0;
    background-color: #eaedf0!important;
}
.layui-nav-tree .layui-nav-child a {
    color: rgb(51, 51, 51);
}
.layui-nav-tree .layui-nav-child, .layui-nav-tree .layui-nav-child a:hover {
	color: rgb(51, 51, 51);
}
</style>

<div id="leftMenu" class="layui-nav layui-nav-tree layui-nav-side menu">
	<ul class="">
	
		${menuHTML }
	
		<div id="menuAppend" style="margin-left: 3px;">
			<!-- 插件扩展菜单项。追加的值如： -->
			<!-- <li class="layui-nav-item" >
				<a href="/user/logout.do">
					<i class="layui-icon firstMenuIcon">&#xe633;</i>
					<span class="firstMenuFont">退出登陆</span>
				</a>
			</li>
			 -->
		</div>
		
		<li class="layui-nav-item" id="updatePassword">
			<a href="javascript:updatePassword();" id="xiugaimima" class="itemA">
				<i class="layui-icon firstMenuIcon">&#xe642;</i>
				<span class="firstMenuFont">更改密码</span>
			</a>
		</li>

		<li class="layui-nav-item" id="logout">
			<a href="/user/logout.do" class="itemA">
				<i class="layui-icon firstMenuIcon">&#xe633;</i>
				<span class="firstMenuFont">退出登陆</span>
			</a>
		</li>
		
	</ul>
</div>


<div id="content" style="width: 100%;height:100%;position: absolute;left: 150px;word-wrap: break-word;border-right: 150px;box-sizing: border-box; border-right-style: dotted;">
	<iframe name="iframe" id="iframe" frameborder="0" style="width:100%;height:100%;box-sizing: border-box;"></iframe>
</div>

<script>
/*** 下面四个是在网市场云建站系统中使用的 ***/
document.getElementById('updatePassword').style.display='none';
document.getElementById('logout').style.display='none';
document.getElementById('leftMenu').style.backgroundColor='#EAEDF1';
$(".itemA").css("color","#333333");


layui.use('element', function(){
  var element = layui.element;
});

/**
 * 在主体内容区域iframe中加载制定的页面
 * url 要加载的页面的url
 */
function loadUrl(url){
	document.getElementById("iframe").src=url + "?time=" + (new Date()).getTime();
}

//加载登录后的默认页面
loadUrl('welcome.do');

//向扩展菜单的div中，加入html。也就是往里再增加别的菜单。 appendHtml要追加的html，这里一般都是追加li
function menuAppend(appendHtml){
	document.getElementById("menuAppend").innerHTML = document.getElementById("menuAppend").innerHTML + appendHtml; 
}
</script>


</body>
</html>