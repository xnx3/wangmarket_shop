<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<style>
	.shiyongbuzhou{
		padding:20px; text-align: left; opacity: 0.9;
	}
	.shiyongbuzhou h2{
		padding-bottom:9px; padding-top:20px;
	}
	.layui-form-switch{
		margin-top: 0px;
	}
</style>
<div class="shiyongbuzhou" id="kaiqitext" >
	<h2>通知的手机号</h2>
	<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
		<div class="layui-input-inline" style="width:300px;">
			<input type="text" name="phone" id="phone" class="layui-input" style="width:100%">
		</div>
		<button class="layui-btn layui-btn-primary" onclick="updatePhone(document.getElementById('phone').value);">保存</button>
		<button class="layui-btn layui-btn-primary" onclick="sendSmsTest();">发送一条短信测试能否收到通知</button>
		<div style=" font-size: 13px; color: gray; padding-top:15px;">
	    	也就是商家的手机号，接收短信通知的手机号。 输入手机号，先保存，再发一条测试短信看看能不能正常收到通知
		</div>
	</div>
	<br/>
</div>		

<div id="shifouqiyong" class="layui-form" style="padding:20px; padding-top:40px; ">
	<span id="qiyongtishi">是否启用付款通知</span> &nbsp;&nbsp;&nbsp;
	<span id="button"></span>	
	<div style="color:gray;text-align: left; padding-top:10px;">
		当客户购买了自己商城的商品，付钱后，系统会自动给商家的手机发送一条短信，通知商家有顾客付钱了。
	</div>
</div>

<script type="text/javascript">
layui.use('form', function(){
	var form = layui.form;
	form.on('switch(isUse)', function(data){
		useChange(data.elem.checked);
		updateUse(data.elem.checked? '1':'0');	//将改动同步到服务器，进行保存	
	});
	//美化是否启用的开关控件
	$(".layui-form-switch").css("marginTop","-2px");
});
//是否使用的开关发生改变触发	use	true:开启使用状态
function useChange(use){
	/* if(use){
		//使用
		//$(".kefuSetInfo").css("opacity","1.0");
		document.getElementById('kaiqitext').style.display = '';
	}else{
		//不使用
		//$(".kefuSetInfo").css("opacity","0.3");
		document.getElementById('kaiqitext').style.display = 'none';
	} */
}
//修改当前是否使用
function updateUse(value){
	parent.msg.loading('修改中');
	post("/plugin/orderNotice/api/store/updateIsUse.json?isUse="+value,{}, function(data){
		parent.msg.close();	//关闭“操作中”的等待提示
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(data.result == '1'){
			parent.msg.success('操作成功');
		}else if(data.result == '0'){
			parent.msg.failure(data.info);
		}else{
			parent.msg.failure();
		}
	});
}
//保存手机号
function updatePhone(phone){
	if(phone.length < 11){
		parent.msg.failure("请输入正确的手机号");
		return;
	}
	parent.msg.loading("保存中");
	post("/plugin/orderNotice/api/store/updatePhone.json",{"phone":phone}, function (result) {
		parent.msg.close();
		checkLogin(result);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(result.result == '1'){
			parent.msg.success("设置成功");
			window.location.reload();
		}else if(result.result == '0'){
			parent.msg.failure(result.info);
		}else{
			parent.msg.failure("出错");
		}
	});
}
//发送测试短信
function sendSmsTest(){
	parent.msg.loading("发送中");
	post("/plugin/orderNotice/api/store/sendSmsTest.json",{}, function (data) {
		parent.msg.close();
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(data.result == '1'){
			parent.msg.success('发送成功');
		}else if(data.result == '0'){
			parent.msg.failure(data.info);
		}else if(data.result == '3'){
			//未配置短信通道
			parent.msg.failure('请先配置短信通道', function(){
				window.location.href="/store/sms/index.jsp";
			});
		}else{
			parent.msg.failure('发送失败');
		}
	});
}


msg.loading('加载中');
var orderNotice;
post('/plugin/orderNotice/api/store/getOrderNotice.json',{},function(data){
	msg.close();	//关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//成功
		orderNotice = data.orderNotice;
		useChange(orderNotice.isUse == 1);
		if (orderNotice.isUse == 1) {
			document.getElementById('button').innerHTML = '<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭" checked>';
			document.getElementById('phone').value = orderNotice.phone;
		}else {
			document.getElementById('button').innerHTML = '<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭">'
		}
		layui.use('form', function(){
			layui.form.render();;
		});
	}
});
</script>
<jsp:include page="../../../store/common/foot.jsp"></jsp:include>