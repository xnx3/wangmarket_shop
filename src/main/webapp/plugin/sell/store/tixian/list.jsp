<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="提现记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellTixianLog_state.js"></script>

<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include> 
	
	<a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>
</form>

<table class="layui-table iw_table">
 <thead>
    <tr>
		<th>编号</th>
		<th>结算佣金</th>
		<th>用户id</th>
        <th>申请时间</th>
        <th>状态</th>
    </tr> 
 </thead>
 <tbody>
	<tr v-for="item in list" :onclick="'detail('+item.id+');'">
		<td>{{item.id}}</td>
		<td>{{item.money}}</td>
		<td>{{item.userid}}</td>
		<td>{{formatTime(item.addtime,'Y-M-D h:m:s')}}</td>
		<td>{{state[item.state]}}</td>
	</tr>
 </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" />

<script>
//查看详情
function detail(id){
	 layer.open({
		type: 2, 
		title:'查看提现记录详情', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/plugin/sell/store/tixian/detail.jsp?id=' + id
	});	 
}

//刚进入这个页面，加载第一页的数据
wm.list(1,'/plugin/api/sell/store/tixian/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>