<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="退单详情"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Account_state.js"></script>
<script src="/<%=Global.CACHE_FILE %>Account_is_top.js"></script>
<style>
	tr,td{
		text-align: center;
	}
</style>

<table class="layui-table">
	<colgroup>
		<col width="150">
		<col width="200">
		<col>
	</colgroup>
	<tbody>
		<tr>
			<td>ID</td>
			<td>${log.id }</td>
		</tr>
		<tr>
			<td>订单id</td>
			<td>${log.orderid }</td>
		</tr>
		<tr>
			<td>用户id</td>
			<td>${log.userid }</td>
		</tr>
		<tr>
			<td>添加时间</td>
			<td>
			<c:if test="${log.addtime != null }"> <x:time linuxTime="${log.addtime }"></x:time></c:if>
			</td>
		</tr>
		<tr>
			<td>理由</td>
			<td>${log.reason }</td>
		</tr>
		<tr>
			<td>状态</td>
			<td>
				<c:if test="${log.state ==1 }"> 退单同意</c:if>
			</td>
			<td>
				<c:if test="${log.state ==2 }"> 退单申请中</c:if>
			</td>
			<td>
				<c:if test="${log.state ==3 }"> 退单拒绝</c:if>
			</td>
		</tr>
		
	</tbody>
</table>

<style type="text/css">
	
	.layui-table img {
    max-width: 40px;
    max-height:40px;
}
</style>


<script type="text/javascript">

	//自适应弹出层大小
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	layui.use('element', function(){
		var element = layui.element;
	});
	


</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>