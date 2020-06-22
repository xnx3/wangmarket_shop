<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="../../../../iw/common/head.jsp">
	<jsp:param name="name" value="提现记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellTixianLog_state.js"></script>
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
		<jsp:param name="iw_label" value="状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">编号</th>
		<th style="text-align:center;">结算佣金</th>
        <th style="text-align:center;">用户id</th>
        <th style="text-align:center;">申请时间</th>
        <th style="text-align:center;">状态</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr style="cursor:pointer;" onclick="detail(${item.id});">
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.money/100 }元</td>
			<td style="text-align:center;">${item.userid }</td>
			<td style="text-align:center;">
				<x:time linuxTime="${item.addtime }"></x:time>
			</td>
			<td style="text-align:center;">
				<script>document.write(state[${item.state}]);</script>
			</td>
		</tr>
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../../iw/common/page.jsp" />

<script>
//查看详情
function detail(id){
	 layer.open({
		type: 2, 
		title:'查看提现记录详情', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: 'detail.do?id=' + id 
	});	 
}
</script>

<jsp:include page="../../../../iw/common/foot.jsp"></jsp:include>