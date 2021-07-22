<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="佣金产生记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellCommissionLog_transfer_state.js"></script>

<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="是否已结算"/>
		<jsp:param name="iw_name" value="transfer_state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include>

	<a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>
</form>

<table class="layui-table iw_table">
	<thead>
		<tr>
			<th>记录编号</th>
			<th>收入佣金</th>
			<th>订单id</th>
			<th>用户id</th>
			<th>时间</th>
			<th>是否结算</th>
		</tr> 
	</thead>
	<tbody>
		<tr v-for="item in list">
			<td>{{item.id}}</td>
			<td>{{item.money}}</td>
			<td>{{item.orderid}}</td>
			<td>{{item.userid}}</td>
			<td>{{formatTime(item.addtime,'Y-M-D h:m:s')}}</td>
			<td>{{transferState[item.transferState]}}</td>
		</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp"/>
<script>

//刚进入这个页面，加载第一页的数据
wm.list(1,'/plugin/api/sell/store/commission/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>