<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="开通子用户"/>
</jsp:include>

<style>
.xnx3_input{
}
</style>
<form class="layui-form" action="" style="padding:15px;">
	<!-- 编辑时此处有用 -->
	<input type="hidden" value="${user.id }" name="userid" />	
	
	<div class="layui-form-item" id="username_div">
		<label class="layui-form-label">登陆账号</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="username" placeholder="限20个英文或数字" required  lay-verify="required" value="${user.username }" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item" id="password_div">
		<label class="layui-form-label">登陆密码</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="password" placeholder="限20个英文或数字。登陆后台所使用的密码" autocomplete="off" class="layui-input">
		</div>
	</div>
<script>
//编辑时，用户是看不到用户名密码的
if('${user.id}' > 0){
	document.getElementById('username_div').style.display='none';
	document.getElementById('password_div').style.display='none';
}
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
  <tbody>
  	<c:forEach items="${map}" var="item">
  		<tr>
			<td style="width:150px;">
				<span id="pid${item.value.id }">
					<input type="checkbox" name="menu" value="${item.value.id }" title="${item.value.name }" <c:if test="${item.value.isUse == 1 }"> checked</c:if>>
	        	</span>
			</td>
	        <td>
	        	<c:forEach items="${item.value.subList }" var="subItem">
	        		<span id="pid${subItem.id }">
						<input type="checkbox" name="menu" title="${subItem.name }" <c:if test="${subItem.isUse=='1' }"> checked</c:if> value="${subItem.id }">
					</span>
				</c:forEach>
	        </td>
	    </tr>
	</c:forEach>
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
		parent.iw.loading('创建中...');
		var d=$("form").serialize();
        $.post("save.do", d, function (result) { 
        	parent.iw.loadClose();
        	var obj = JSON.parse(result);
        	if(obj.result == '1'){
        		parent.parent.iw.msgSuccess('操作成功')
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

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>