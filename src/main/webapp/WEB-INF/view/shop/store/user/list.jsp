<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="用户列表"/>
</jsp:include>


<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">用户ID</th>
        <th style="text-align:center;">登录用户名</th>
         <th style="text-align:center;">注册时间</th>
         <th style="text-align:center;">注册IP</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.username }</td>
			<td style="text-align:center;">
				<x:time linuxTime="${item.regtime }"></x:time>
			</td>
			<td style="text-align:center;">${item.ip }</td>
		</tr>
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
parent.layer.iframeAuto(index);
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>