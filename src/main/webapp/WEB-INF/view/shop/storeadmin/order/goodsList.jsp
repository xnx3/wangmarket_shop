<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="订单内商品列表"/>
</jsp:include>
<style type="text/css">
	
	.layui-table img {
    max-width: 49px;
    max-height:49px;
}
	.head1 .head3 {
	float:right;
}
</style>
<script src="/<%=Global.CACHE_FILE %>GoodsType_typeid.js"></script>


<table class="layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">订单ID</th>
		<th style="text-align:center;">商品ID</th>
		<th style="text-align:center;">用户ID</th>
		<th style="text-align:center;">商品名字</th>
		<th style="text-align:center;">商品的标题图片</th>
		<th style="text-align:center;">商品单价</th>
		<th style="text-align:center;">商品单位</th>
		<th style="text-align:center;">数量</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.orderid }</td>
			<td style="text-align:center;">${item.goodsid }</td>
			<td style="text-align:center;">${item.userid }</td>
			<td style="text-align:center;">${item.goodsTitle }</td>
			<td style="text-align:center;">
			<a  href="${item.goodsTitlepic}" target="_black"><img src = '${item.goodsTitlepic }' /></a>
			</td>
			<td style="text-align:center;">${item.goodsPrice }</td>
			<td style="text-align:center;">${item.goodsUnits }</td>
			<td style="text-align:center;">${item.number }</td>
		</tr>
	</c:forEach>
  </tbody>
</table>
<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引




</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>