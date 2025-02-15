<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<div id="shifouqiyong" class="layui-form" style="padding-top:10%; text-align:center;">
	<span id="qiyongtishi">是否启用二级分销功能</span> &nbsp;&nbsp;&nbsp;
	<span id="button"></span>
	<div style="color:gray; padding:5px; text-align: left; padding-left: 20px;">
		用户A推荐用户B注册，用户B推荐用户C注册。每当用户C成功付款并订单状态变为已完成时，用户A、B都可获得佣金分红
		<br/>注意：
		<br/>1. 用户C如果再推荐用户D注册，用户D付款消费并订单变为已完成时，用户C、B都会获得佣金分红，但用户A没有。也就是最多支持两级上级分红
		<br/>2. 分红是按照订单实际支付的金额进行的分红。例如订单原价100元，实际支付时用了各种优惠活动实际支付了30元，那么分红是按照30元进行的佣金百分比计算分红
		<br/>3. 只要用户C长期有消费，那么用户A、B将长期有分佣。
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
		<h2>一级分红佣金百分比</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
			<div class="layui-input-inline" style="width:300px;">
				<input type="number" name="firstCommission" id="firstCommission" placeholder="分佣百分比" class="layui-input" style="width:100%">
			</div>
			<button class="layui-btn layui-btn-primary" onclick="update_commission('first', document.getElementById('firstCommission').value);">保存</button>
			<div style=" font-size: 13px; color: gray; padding-top:15px;">
				也就是上面示例中，用户C消费后，用户B能拿到百分之多少的分佣。填写如： 10
			</div>
		</div>
		<br/>
		<h2>二级分红佣金百分比</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
		    <div class="layui-input-inline" style="width:300px;">
				<input type="number" name="twoCommission" id="twoCommission" placeholder="分佣百分比"  class="layui-input" style="width:100%">
		    </div>
			<button class="layui-btn layui-btn-primary" onclick="update_commission('two', document.getElementById('twoCommission').value);">保存</button>
			<div style=" font-size: 13px; color: gray; padding-top:15px;">
				也就是上面示例中，用户C消费后，用户A能拿到百分之多少的分佣。填写如： 3
			</div>
		</div>
		<h2>几个工作日内处理审核，进行打款</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
			<div class="layui-input-inline" style="width:300px;">
				<input type="number" name="disposeDay" id="disposeDay" placeholder=""   class="layui-input" style="width:100%">
			</div>
			<button class="layui-btn layui-btn-primary" onclick="update_commission('disposeDay', document.getElementById('disposeDay').value);">保存</button>
			<div style=" font-size: 13px; color: gray; padding-top:15px;">
				处理时间，也就是提交申请后，会在几个工作日内进行处理。这个字段主要是给用户端，给用户看的。
			</div>
		</div>
		<h2>最低提现金额</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
			<div class="layui-input-inline" style="width:300px;">
				<input type="number" name="money" id="money" placeholder=""  class="layui-input" style="width:100%">
			</div>
			<button class="layui-btn layui-btn-primary" onclick="update_commission('money', document.getElementById('money').value);">保存</button>
			<div style=" font-size: 13px; color: gray; padding-top:15px;">
				提现时，需要金额满足多少钱，可提现金额大于这个钱，才允许提交提现申请。这里单位是分
			</div>
		</div>
		<br/>
		<br/>
		<br/>
		<div id="list">
			<h2>当前佣金生成解释：</h2>
			用户A推荐用户B注册，用户B推荐用户C注册。每当用户C成功付款100元，并当订单状态变为已完成时：
			<br/>用户B获得的分佣：分佣比例 {firstCommission}% , 也就是 100 * {firstCommission}% = {firstCommission} 元
			<br/>用户A获得的分佣：分佣比例 {twoCommission}% , 也就是 100 * {twoCommission}% = {twoCommission} 元
		</div>
		<br/>
		<br/>
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
	post("/plugin/api/sell/store/updateIsUse.json?isUse="+value,{}, function(data){
		parent.msg.close();    //关闭“操作中”的等待提示
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
//保存code
function update_commission(level, value){
	if(!value){
		parent.msg.failure("请设置值");
		return;
	}
	parent.msg.loading("保存中...");
	post("/plugin/api/sell/store/updateCommission.json?level="+level+"&value="+value,{}, function (result) {
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
	}, "text");
}
//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate
		.replace(/\{firstCommission\}/g, item.firstCommission)
		.replace(/\{twoCommission\}/g, item.twoCommission)
		;
}
msg.loading('加载中');
var set;
post('/plugin/api/sell/store/set.json',{},function(data){
	msg.close();	//关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//登录成功
		set	= data.sellSet;
		useChange(set.isUse == 1);
		if (set.isUse == 1) {
			document.getElementById('button').innerHTML = '<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭" checked>';
			document.getElementById('firstCommission').value = set.firstCommission;
			document.getElementById('disposeDay').value = set.disposeDay;
			document.getElementById('twoCommission').value = set.twoCommission;
			document.getElementById('money').value = set.money;
			document.getElementById("list").innerHTML = templateReplace(set);;
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