<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="开通下级代理"/>
</jsp:include>

<form class="layui-form" action="" style="padding-top:3%; padding-left:15%; padding-right:15%; padding-bottom: 3%;">

	<div class="layui-form-item">
		<label class="layui-form-label">登陆账号</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="username" id = "username" placeholder="限20个英文或汉字，开通代理后，用户用此账号进行登陆" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">登陆密码</label>
		<div class="layui-input-block xnx3_input">
			<input type="text" name="password" id = "password" placeholder="限20个英文或汉字，用户进入代理后台登陆的密码" autocomplete="off" class="layui-input">
		</div>
	</div>
	
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="formDemo">开通店铺</button>
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
		if($("#username").val() == '') {
			layer.msg('请输入登录账号');
			return false;
		}
		if($("#password").val() == '') {
			layer.msg('请输入登录密码');
			return false;
		}
		
		parent.msg.loading('开通中');
		var d=$("form").serialize();
        $.post("addSubmit.do", d, function (result) { 
        	parent.msg.close();
        	var obj = JSON.parse(result);
        	if(obj.result == '1'){
        		parent.msg.success('开通成功');
        		window.location.href="list.do?orderBy=addtime_DESC";
        	}else if(obj.result == '0'){
        		parent.msg.failure(obj.info);
        	}else{
        		parent.msg.failure(result);
        	}
         }, "text");
		
		return false;
	});
});
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>