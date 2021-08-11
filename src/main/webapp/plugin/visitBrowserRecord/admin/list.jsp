<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="用户访问记录列表"/>
</jsp:include>


<table class="layui-table iw_table">
	<thead>
	<tr>
		<th>头像</th>
		<th>昵称</th>
		<th>手机号</th>
		<th>访问时间</th>
		<th>离开时间</th>
		<th>耗时</th>
		<th>记录创造时间</th>
	</tr>
	</thead>
	<tbody>
	<tr v-for="item in jsonArray">
		<td>{{item.head}}</td>
		<td>{{item.nickname}}</td>
		<td>{{item.phone}}</td>
		<td>{{item.startTime}}</td>
		<td>{{item.endTime}}</td>
		<td>{{item.useTime}}</td>
		<td>{{item.time}}</td>
	</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp"></jsp:include>

<script type="text/javascript">
	// //根据id删除
	// function deleteItem(id,name){
	// 	var dtp_confirm = layer.confirm('确定要删除人员“'+name+'”？删除后不可恢复！', {
	// 		btn: ['删除','取消'] //按钮
	// 	}, function(){
	// 		layer.close(dtp_confirm);
	//
	// 		parent.msg.loading("删除中");    //显示“操作中”的等待提示
	// 		$.post('delete.do?id='+id, function(data){
	// 			parent.msg.close();    //关闭“操作中”的等待提示
	// 			if(data.result == '1'){
	// 				parent.msg.success('操作成功');
	// 				window.location.reload();	//刷新当前页
	// 			}else if(data.result == '0'){
	// 				parent.msg.failure(data.info);
	// 			}else{
	// 				parent.msg.failure();
	// 			}
	// 		});
	//
	// 	}, function(){
	// 	});
	// }
	//
	// //修改
	// function editItem(id, name){
	// 	layer.open({
	// 		type: 2,
	// 		title:id > 0? '修改人员信息&nbsp;[&nbsp;'+name+'&nbsp;]&nbsp;':'添加人员',
	// 		area: ['580px', '500px'],
	// 		shadeClose: true, //开启遮罩关闭
	// 		content: 'edit.do?id='+id
	// 	});
	// }

	//刚进入这个页面，自动加载第一页的数据
	wm.list(1,"/plugin/visitBrowserRecord/admin/list.json");
</script>

<jsp:include page="/wm/common/foot.jsp"></jsp:include>