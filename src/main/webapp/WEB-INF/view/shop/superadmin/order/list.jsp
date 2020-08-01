<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="订单列表"/>
</jsp:include>
<style type="text/css">
.remarktr {
	background:#f2f2f2;
}
.toubu_xnx3_search_form {
    padding-top: 0px;
    padding-bottom: 10px;
	}
</style>
<div style="height:10px;"></div>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单号" />
		<jsp:param name="iw_name" value="no" />
	</jsp:include>
	
	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">订单号</th>
        <th style="text-align:center;">付款金额</th>
        <th style="text-align:center;">状态</th>
        <th style="text-align:center;">下单时间</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
			<tr>
				<td style="text-align:center;">${item.id }</td>
				<td style="text-align:center;">${item.no }</td>
				<td style="text-align:center;">${item.payMoney/100 }元</td>
				<td style="text-align:center;">
				<script type="text/javascript">document.write(state['${item['state']}']);</script>
				</td>
				<td style="text-align:center;">
					<c:if test="${item.addtime != null }">
						<x:time linuxTime="${item.addtime}" format="yy-MM-dd HH:mm:ss"></x:time>
					</c:if>
				</td>
				<td style="text-align:center;">
					<a class="layui-btn layui-btn-sm" onclick="seeGoods('${item.id }')" style=""><i class="layui-icon">详情</i></a>
				</td>
			</tr>
			
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//跳转添加或者修改页面 id 订单id
function seeGoods(id){
	 layer.open({
		type: 2, 
		title:'查看订单详情', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/shop/superadmin/order/orderDetails.do?id=' + id
	});	 
}
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>