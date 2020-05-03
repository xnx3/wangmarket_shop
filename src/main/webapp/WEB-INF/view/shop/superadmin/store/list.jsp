<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="商家列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Store_state.js"></script>


<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="店铺名字"/>
		<jsp:param name="iw_name" value="name"/>
	</jsp:include>
	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系人"/>
		<jsp:param name="iw_name" value="contacts"/>
	</jsp:include>
	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系电话"/>
		<jsp:param name="iw_name" value="phone"/>
	</jsp:include>
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
	<a href="add.do" class="layui-btn layui-btn-normal" style="float: right; margin-right:10px;">开通店铺</a>
</form>	
                   
<table class="layui-table iw_table">
	<thead>
		<tr>
			<th>编号ID</th>
			<th>用户ID</th>
			<th>店铺名</th>
			<th>创建时间</th>
		</tr> 
	</thead>
	<tbody>
		<c:forEach items="${list}" var="item">
			<tr>
				<td style="width:55px;"><a href="view.do?id=${item['id'] }">${item['id'] }</a></td>
				<td onclick="userView(${item['userid'] });" style="cursor: pointer; width:55px;">${item['userid'] }</td>
				<td><a href="javascript:siteView('${item['id'] }');" title="${item['name'] }"><x:substring maxLength="20" text="${item['name'] }"></x:substring> </a></td>
				<td style="width:100px;"><x:time linuxTime="${item['addtime'] }" format="yy-MM-dd hh:mm"></x:time></td>
			</tr>
		</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" ></jsp:include>

<script>
//查看站点详情信息
function siteView(id){
	layer.open({
		type: 2, 
		title:'查看站点信息', 
		area: ['460px', '470px'],
		shadeClose: true, //开启遮罩关闭
		content: '/shop/superadmin/store/view.do?id='+id
	});
}

//查看用户信息
function userView(id){
	layer.open({
		type: 2, 
		title:'查看用户信息', 
		area: ['460px', '630px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/user/view.do?id='+id
	});
}
</script>
<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>                  