<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../../store/common/head.jsp">
	<jsp:param name="name" value="提现详情"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellTixianLog_state.js"></script>
<style>
h2{
	padding-top: 12px;
    padding-bottom: 5px;
}
</style>

<div style="margin-top: 10px;">
<table class="layui-table iw_table"  style="color: black;font-size: 14px;" id="tbody">
  <tr>
  	<td style="width:120px;">编号</td>
  	<td>{id}</td>
  </tr>
  <tr>
  	<td style="width:120px;">提现用户id</td>
  	<td>{userid}</td>
  </tr>
  <tr>
  	<td >订单金额</td>
  	<td>
  		{money}元
  	</td>
  </tr>
  <tr>
  	<td >当前状态</td>
  	<td>
  		{state}
  		<span id="orderButton" style="display:none;">
  			<!-- 订单的状态按钮操作 -->
  			<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="caozuo(1);">同意，并已汇款完毕</botton>
  			<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="caozuo(2);">拒绝</botton>
  		</span>
  	</td>
  </tr>
  <tr>
  	<td >申请时间</td>
  	<td>{addtime}</td>
  </tr>
  <tr>
  	<td >汇款账号</td>
  	<td>{card}</td>
  </tr>
  <tr>
  	<td >收款人姓名</td>
  	<td>{username}</td>
  </tr>
  <tr>
  	<td >收款人电话</td>
  	<td>{phone}</td>
  </tr>
</table>

<script type="text/javascript">
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//操作， 传入 1已通过并汇款，2已拒绝
function caozuo(state){
	msg.loading('操作中');
	post('/plugin/api/sell/store/tixian/audit.json',{"state":state, "id":sellTiXianLog.id}, function(data){
		msg.close();
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(data.result == 1){
			msg.success('操作成功');
			location.reload();
		}else{
			msg.failure(data.info);
		}
	});
}

//列表的模版
var orderTemplate = document.getElementById("tbody").innerHTML;
function templateReplace(item){
	return orderTemplate
			.replace(/\{id\}/g, item.id)
			.replace(/\{userid\}/g, item.userid)
			.replace(/\{money\}/g, item.money/100)
			.replace(/\{state\}/g, state[item.state])
			.replace(/\{addtime\}/g, item.addtime != null ? formatTime(item.addtime,'Y-M-D h:m:s') : '')
			.replace(/\{card\}/g, item.card)
			.replace(/\{username\}/g, item.username)
			.replace(/\{phone\}/g, item.phone)
			;
}

msg.loading('加载中');
var sellTiXianLog;
var id = getUrlParams('id'); //提现记录id
post('/plugin/api/sell/store/tixian/detail.json?id='+id,{},function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//登录成功
		sellTiXianLog	= data.sellTiXianLog;

		document.getElementById("tbody").innerHTML = templateReplace(sellTiXianLog);
		if(sellTiXianLog.state == 0){
			document.getElementById('orderButton').style.display = '';
		}

	}

});
</script>

<jsp:include page="../../../../store/common/foot.jsp"></jsp:include>