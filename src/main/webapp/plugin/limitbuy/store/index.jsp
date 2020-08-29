<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<div id="shifouqiyong" class="layui-form" style="padding-top:10%; text-align:center;">
	<span id="qiyongtishi">是否启用限购功能</span> &nbsp;&nbsp;&nbsp;
	<span id="button">

	</span>
	<div style="color:gray; padding:5px; text-align: left; padding-left: 20px;">
		这里是针对整个商城所有商品的限购。用户只可以下单购买几次，用户只有设定的几次下单支付的机会。<br/>
		
		比如，设置了每个用户有一次下单购买机会，用户A注册后，下单，但尚未支付，也算是购买了一次，不能继续下单了。<br/>
		如果超时未支付，订单会自动取消，那么这次机会又会释放出来，可以再次下单购买。<br/>
		如果用户支付了，那么无论是退单、还是正常完成，都算是消耗了一次机会，无法再下单购买。
	</div>
	
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
	<!-- 默认隐藏 -->
	<div class="shiyongbuzhou" id="kaiqitext" style="display:none;">
		<h2>每个用户限购几单</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
		    <div class="layui-input-inline" style="width:300px;">
		      <input type="number" name="limitNumber" id="limitNumber" placeholder="整个商城限购单数" value="${limitBuyStore.limitNumber }"  class="layui-input" style="width:100%">
		    </div>
		    <button class="layui-btn layui-btn-primary" onclick="updateLimitNumber(document.getElementById('limitNumber').value);">保存</button>
		    <div style=" font-size: 13px; color: gray; padding-top:15px;">
		    	每个用户在本商城中，限购几单。超过后将不能下单。强烈建议设置后不要在进行修改。
		    </div>
		</div>
	</div>
		
</div>


<script type="text/javascript">
var form;
layui.use('form', function(){
	form = layui.form;
	
	form.on('switch(isUse)', function(data){
		useChange(data.elem.checked);
		updateUse(data.elem.checked? '1':'0');	//将改动同步到服务器，进行保存
		
	});
	
	//美化是否启用的开关控件
	$(".layui-form-switch").css("marginTop","-2px");
});

//是否使用的开关发生改变触发  use  true:开启使用状态
function useChange(use){
	if(use){
		//使用
		//$(".kefuSetInfo").css("opacity","1.0");
		document.getElementById('kaiqitext').style.display = '';
	}else{
		//不使用
		//$(".kefuSetInfo").css("opacity","0.3");
		
		document.getElementById('kaiqitext').style.display = 'none';
	}
}


//修改当前是否使用
function updateUse(value){
	parent.msg.loading('修改中');
	$.post("/plugin/api/limitbuy/store/updateIsUse.json?isUse="+value, function(data){
	    parent.msg.close();    //关闭“操作中”的等待提示
	    if(data.result == '1'){
	        parent.msg.success('操作成功');
	     }else if(data.result == '0'){
	         parent.msg.failure(data.info);
	     }else{
	         parent.msg.failure();
	     }
	});
}

//保存code
function updateLimitNumber(value){
	if(!value){
		parent.msg.failure("请设置值");
		return;
	}

	parent.msg.loading("保存中...");
	$.post("/plugin/api/limitbuy/store/updateLimitNumber.json?value="+value, function (result) {
       	parent.msg.close();
       	var obj = JSON.parse(result);
       	if(obj.result == '1'){
       		parent.msg.success("设置成功");
       		window.location.reload();
       	}else if(obj.result == '0'){
       		parent.msg.failure(obj.info);
       	}else{
       		parent.msg.failure("出错");
       	}
	}, "text");
}

msg.loading('加载中');
var limitBuyStore;

post('/plugin/api/limitbuy/store/index.json',{},function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//登录成功
		limitBuyStore	= data.limitBuyStore;
		useChange(limitBuyStore.isUse == 1);
		if (limitBuyStore.isUse == 1) {
			document.getElementById('button').innerHTML = '<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭" checked>';
 			document.getElementById('limitNumber').value = limitBuyStore.limitNumber;
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