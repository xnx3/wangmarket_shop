<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="title" value="子用户列表"/>
</jsp:include>
	<a href="javascript:menu(0,'');" id="add_first_permission_aTag" class="layui-btn layui-btn-normal" style="float: right; margin:10px;"><i class="layui-icon">&#xe654;</i>&nbsp;开通子用户</a>

<table class="layui-table iw_table">
	<thead>
		<tr>
			<th>登录用户名</th>
			<th>账户开通时间</th>
			<th>最后上线时间</th>
			<th>操作</th>
		</tr> 
	</thead>
	<tbody>
	<tr v-for="item in list">
		<td>{{item.username}}</td>
		<td>{{formatTime(item.regtime,'Y-M-D h:m:s')}}</td>
		<td>{{formatTime(item.lasttime,'Y-M-D h:m:s')}}</td>
		<td style="width: 200px;">
			<botton class="layui-btn layui-btn-sm" :onclick="'updatePassword('+item.id+', \''+item.username+'\');'" style="margin-left: 3px;">改密码</botton>
			<botton class="layui-btn layui-btn-sm" :onclick="'menu('+item.id+', \''+item.username+'\');'" style="margin-left: 3px;">权限</botton>
			<botton class="layui-btn layui-btn-sm" :onclick="'deleteUser('+item.id+', \''+item.username+'\');'" style="margin-left: 3px;">删除</botton>
		</td>
	</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp"></jsp:include>
<script type="text/javascript">
//根据id删除用户
function deleteUser(id,name){
	var dtp_confirm = layer.confirm('确定要删除子用户“'+name+'”？删除后不可恢复！', {
		btn: ['删除','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		post('/plugin/api/storeSubAccount/user/deleteUser.json?userid='+id,{}, function(data){
			parent.msg.close();	//关闭“操作中”的等待提示
			checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
			if(data.result == '1'){
				parent.msg.success('操作成功');
				window.location.reload();	//刷新当前页
			}else if(data.result == '0'){
				parent.msg.failure(data.info);
			}else{
				parent.msg.failure();
			}
		});
	}, function(){
	});
}
/**
 * 添加账户/修改权限
 * id 要修改的子用户的userid
 * username 要修改用户的登陆的username
 */
function menu(id,username){
	layer.open({
		type: 2, 
		title: id<1 ? '添加子用户':'修改用户【 '+username+' 】的管理权限', 
		area: ['1000px', id>0? '630px':'750px'],
		shadeClose: true, //开启遮罩关闭
		content: '/plugin/storeSubAccount/user/edit.jsp?userid='+id
		// content:	'/plugin/api/storeSubAccount/user/edit.do?userid='+id
	});
}
//给我子用户修改密码
function updatePassword(userid, name){
	layer.prompt({
		formType: 0,
		value: '',
		title: '给'+name+'改密码，请输入新密码',
	}, function(value, index, elem){
		parent.msg.loading("更改中");		//显示“更改中”的等待提示
		post("/plugin/api/storeSubAccount/user/updatePassword.json",
			{ "newPassword": value, userid:userid }, 
			function(data){
				parent.msg.close();    //关闭“更改中”的等待提示
				checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
				if(data.result != '1'){
					parent.msg.failure(data.info);
				}else{
					parent.msg.success("修改成功");
					location.reload();
				}
			}, 
		"json");
	});
}
//刚进入这个页面，加载第一页的数据
wm.list(1,'/plugin/api/storeSubAccount/user/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>