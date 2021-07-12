<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
    <jsp:param name="name" value="开店申请"/>
</jsp:include>
<div style="height:10px;"></div>

<table class="layui-table iw_table">
 <thead>
    <tr>
		<th>ID</th>
        <th>姓名</th>
        <th>电话</th>
        <th>微信</th>
        <th>申请时间</th>
    </tr> 
 </thead>
 <tbody>
	<tr v-for="item in list">
		<td>{{item.id}}</td>
		<td>{{item.contacts}}</td>
		<td>{{item.phone}}</td>
		<td>{{item.address}}</td>
		<td>{{formatTime(item.addtime,'Y-M-D h:m:s')}}</td>
	</tr>
 </tbody>
 </table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" />
<script type="text/javascript">

//刚进入这个页面，加载第一页的数据
wm.list(1,'/plugin/api/autoApplyStore/store/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>