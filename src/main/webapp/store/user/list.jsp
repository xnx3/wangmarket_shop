<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="用户列表"/>
</jsp:include>

<table class="layui-table iw_table">
 <thead>
    <tr>
		<th>用户ID</th>
        <th>登录用户名</th>
        <th>注册时间</th>
        <th>注册IP</th>
    </tr> 
 </thead>
 <tbody>
	<tr v-for="item in list">
		<td>{{item.id}}</td>
		<td>{{item.username}}</td>
		<td>{{formatTime(item.regtime,'Y-M-D h:m:s')}}</td>
		<td>{{item.regip}}</td>
	</tr>
 </tbody>
 </table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" />
<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
parent.layer.iframeAuto(index);

//刚进入这个页面，加载第一页的数据
wm.list(1,'/shop/store/api/user/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>