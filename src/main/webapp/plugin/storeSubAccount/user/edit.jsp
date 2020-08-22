<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="开通子用户"/>
</jsp:include>

<style>
	.xnx3_input{
	}
</style>
<form class="layui-form" action="" style="padding:15px;">
	<!-- 编辑时此处有用 -->
	<input type="hidden" name="userid" id="userid" />

	<div class="layui-form-item" id="username_div">
		<label class="layui-form-label">登陆账号</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="username" id="username" placeholder="限20个英文或数字" required  lay-verify="required" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item" id="password_div">
		<label class="layui-form-label">登陆密码</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="password" placeholder="限20个英文或数字。登陆后台所使用的密码" autocomplete="off" class="layui-input">
		</div>
	</div>
	<script>

	</script>

	<div style="padding-top:15px; padding-bottom:8px;color: gray;">
		以下是该用户的权限，选上后，该用户登陆，即可看到相应的菜单。未选中的菜单该用户看不到
	</div>

<table class="layui-table iw_table">
	<thead>
	<tr>
		<th>主菜单</th>
		<th>子菜单</th>
	</tr>
	</thead>
	<tbody id="tbody">
	<tr>
		<td style="width:150px;">
			<span id="pid{id}">
				{input}
			</span>
		</td>

		<lable id="td">
				<span id="pid{subListValueId}">
					{subInput}
				</span>
		</lable>
		<td>
			{subList}
		</td>

	</tr>

	</tbody>
</table>




<div class="layui-form-item" style="margin-top:20px;">
	<div class="layui-input-block">
		<button class="layui-btn" lay-submit lay-filter="formDemo">确认开通</button>
		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
	</div>
</div>
</form>

<script type="text/javascript">

//form组件，开启select
layui.use(['form'], function(){
	var form = layui.form;
	//监听提交
	form.on('submit(formDemo)', function(data){
		parent.msg.loading('创建中...');
		var d=$("form").serialize();
		$.post("/plugin/api/storeSubAccount/user/save.json", d, function (result) {
			parent.msg.close();
			var obj = JSON.parse(result);
			if(obj.result == '1'){
				parent.parent.msg.success('操作成功')
				parent.location.reload();
			}else if(obj.result == '0'){
				layer.msg(obj.info, {shade: 0.3})
			}else{
				layer.msg(result, {shade: 0.3})
			}
		}, "text");

		return false;
	});
});

var authorityTemplate = document.getElementById("td").innerHTML;
function authorityTwoReplace(item){
	return authorityTemplate
			.replace(/\{subListValueId\}/g, item.id)
			.replace(/\{subInput\}/g,item.isUse == 1 ? '<input type=checkbox name=menu value='+ item.id + ' title='+ item.name +' checked '+' >'
					: '<input type=checkbox name=menu value='+ item.id + ' title='+ item.name +'>' )
			;
}

//列表的模版
var subList = '';
var authorityOneTemplate = document.getElementById("tbody").innerHTML;
function  authorityOneReplace(item){
	if(item.subList.length > 0 ){
		for(var i=0;i<item.subList.length;i++){
			subList = subList + authorityTwoReplace(item.subList[i])
		}
	}


	return authorityOneTemplate
			.replace(/\{id\}/g, item.id)
			.replace(/\{subList\}/g, item.subList)
			.replace(/\{input\}/g,item.isUse == 1 ? '<input type=checkbox name=menu value='+ item.id + ' title='+ item.name +' checked '+' >'
					: '<input type=checkbox name=menu value='+ item.id + ' title='+ item.name +'>' )
			;
}

var userId = getUrlParams('userid');
msg.loading('加载中');

post('/plugin/api/storeSubAccount/user/edit.json?userid=' + userId,{},function(data){
	msg.close();    //关闭“更改中”的等待提示

	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result !== '1'){
		var user = data.user;
		var menuMap = data.menuMap;
		var html = '';
		$.map(menuMap,function(key,value){

			html = html + authorityOneReplace(key)
		});
		document.getElementById("tbody").innerHTML = html;
		//重新渲染layui选择框样式
		layui.use('form', function(){
		  var form = layui.form;
		  form.render();
		});
		
		document.getElementById('td').style.display = 'none'; // 按 id 获取要删除的元素
		// elem.parentNode.removeChild(elem)

		//编辑时，用户是看不到用户名密码的
		if(user !=null && user.id > 0 ){
			document.getElementById('username_div').style.display='none';
			document.getElementById('password_div').style.display='none';
			document.getElementById("username").value = user.username;
			document.getElementById("userid").value = user.id;
		}
	
	}

});
</script>

<jsp:include page="../../../store/common/foot.jsp"></jsp:include>