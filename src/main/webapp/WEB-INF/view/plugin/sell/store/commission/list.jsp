<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="../../../../iw/common/head.jsp">
	<jsp:param name="name" value="佣金产生记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellCommissionLog_transfer_state.js"></script>
<style type="text/css">
.layui-table img {
    max-width: 49px;
    max-height:49px;
}
.toubu_xnx3_search_form {
    padding-top: 0px;
    padding-bottom: 10px;
}
</style>

<div style="height:10px;"></div>
<jsp:include page="../../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

<jsp:include page="../../../../iw/common/list/formSearch_input.jsp">
	<jsp:param name="iw_label" value="是否已结算"/>
	<jsp:param name="iw_name" value="transfer_state"/>
	<jsp:param name="iw_type" value="select"/>
</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">记录编号</th>
		<th style="text-align:center;">收入佣金</th>
		<th style="text-align:center;">订单id</th>
        <th style="text-align:center;">用户id</th>
        <th style="text-align:center;">时间</th>
        <th style="text-align:center;">是否结算</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.money/100 }</td>
			<td style="text-align:center;">${item.orderid }</td>
			<td style="text-align:center;">${item.userid }</td>
			<td style="text-align:center;">
				<x:time linuxTime="${item.addtime }"></x:time>
			</td>
			<td style="text-align:center;">
				<script>document.write(transfer_state[${item.transferState}]);</script>
			</td>
		</tr>
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../../iw/common/page.jsp" />

<jsp:include page="../../../../iw/common/foot.jsp"></jsp:include>