<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="代理欢迎页面"/>
</jsp:include>


<style>
.iw_table tbody tr .iw_table_td_view_name{
	width:50%;
	padding-left:25%;
}
</style>

<div style="text-align:center; font-size:29px; padding-top:35px; padding-bottom: 10px;">
	欢迎使用 <%=Global.get("SITE_NAME") %> 云商城系统
</div>

<table class="layui-table iw_table" lay-even lay-skin="nob" style="margin:3%; width:94%;">
	<tbody>
		<tr>
			<td class="iw_table_td_view_name">店铺名称</td>
			<td>${store.name }</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺状态</td>
			<td>${store.state }
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">开店时间</td>
			<td>
				<c:if test="${store.addtime != null }"> <x:time linuxTime="${store.addtime }"></x:time></c:if>
			</td>
		</tr>
		
		

    </tbody>
</table>


<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  