<%@page import="com.xnx3.wangmarket.Authorization"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/wm/common/head.jsp">
	<jsp:param name="title" value="免费开通店铺"/>
</jsp:include>
<style>
body{
	background: white;
}
.myForm{
	margin: 0 auto;
    width: 495px;
    height: auto;
    border-width: 1px 1px 1px 1px;
    padding: 0px;
    border-radius: 20px;
    overflow: hidden;
    -webkit-box-shadow: 0 0 10px #e2e2e2;
    -moz-box-shadow: 0 0 10px #e2e2e2;
    box-shadow: 0 0 10px #e2e2e2;
    position: absolute;
    left: 50%;
    top: 45%;
    margin-left: -248px;
    margin-top: -230px;
}
#divCode{
	padding:20px;
}

@media screen and (max-width:600px){
	.myForm{
		margin: 0 auto;
	    width: 100%;
	    height: 100%;
	    border-width: 0px;
	    padding: 0px;
	    border-radius: 0px;
	    overflow: auto;
	    -webkit-box-shadow: 0 0 0px #e2e2e2;
	    -moz-box-shadow: 0 0 0px #e2e2e2;
	    box-shadow: 0 0 0px #e2e2e2;
	    position: static;
	    left: 0px;
	    top: 0px;
	    margin-left: 0px;
	    margin-top: 0px;
	}
	.touming{
    	margin-bottom: -15px;
    	margin-bottom: 5px;
	}
}
</style>

<form class="layui-form layui-elem-quote layui-quote-nm myForm" action="/regSubmit.do">
	<div class="layui-form-item touming" style="height: 70px;background-color: #eeeeee;line-height: 70px;text-align: center;font-size: 25px;color: #3F4056;">
	    云客服 平台开通
	  </div>
  <div style="padding: 30px 50px 40px 0px;">
  
  	<div class="layui-form-item">
	    <label class="layui-form-label">用户名</label>
	    <div class="layui-input-block">
	      <input id="username" type="text" name="username" required  lay-verify="required" placeholder="登录使用。只限英文或数字" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"> 密 码&nbsp;&nbsp; </label>
	    <div class="layui-input-block">
	      <input id="password" type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">手机号</label>
	    <div class="layui-input-block">
	      <input type="text" name="phone" id="phone"  lay-verify="required" placeholder="请输入您的手机号" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item" style="    padding-top: 10px;">
	    <div class="layui-input-block">
	      <a class="layui-btn" lay-submit lay-filter="formDemo" href="javascript:;">免费开通</a>
	      <a href="/store/login/login.jsp" target="_black" class="layui-btn layui-btn-primary" style="margin-left: 40px;">去登陆</a>
	    </div>
	  </div>
  </div>
</form>

<script>
layui.use('form', function(){
  var form = layui.form;

  //监听提交
  form.on('submit(formDemo)', function(data){
  	var phone = document.getElementById('phone').value;
  	phone = phone.replace(/[^\d]/g,'');
  	if(phone.length != 11){
		msg.failure('请输入正确手机号');
		return false;
	}

	msg.loading('开通中');
	var postDate = wm.getJsonObjectByForm($("form"));	//提交的表单数据
	wm.post("create.json", postDate, function (obj) {
		msg.close();
       	if(obj.result == '1'){
			wm.token.set(obj.info);
       		msg.success('开通成功', function(){
				window.location.href='/store/index/index.jsp';
			})
       	}else if(obj.result == '0'){
       		msg.failure(obj.info);
       		return;
       	}else{
       		msg.failure('操作失败');
       		return;
       	}
	});

    return false;
  });

});
</script>
<jsp:include page="../../iw/common/foot.jsp"></jsp:include>