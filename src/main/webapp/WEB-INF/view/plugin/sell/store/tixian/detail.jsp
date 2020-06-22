<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../../iw/common/head.jsp">
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
<table class="layui-table iw_table"  style="color: black;font-size: 14px;">
  <tr>
  	<td style="width:120px;">编号</td>
  	<td>${log.id}</td>
  </tr>
  <tr>
  	<td style="width:120px;">提现用户id</td>
  	<td>${log.userid}</td>
  </tr>
  <tr>
  	<td >订单金额</td>
  	<td>
  		${log.money/100}元
  	</td>
  </tr>
  <tr>
  	<td >当前状态</td>
  	<td>
  		<script>document.write(state[${log.state}]);</script>
  		<span id="orderButton" style="display:none;">
  			<!-- 订单的状态按钮操作 -->
  			<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="caozuo(1);">同意，并已汇款完毕</botton>
  			<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="caozuo(2);">拒绝</botton>
  			<script>
  				if('${log.state}' == '0'){
  					document.getElementById('orderButton').style.display = '';
  				}
  			</script>
  		</span>
  	</td>
  </tr>
  <tr>
  	<td >申请时间</td>
  	<td><x:time linuxTime="${log.addtime }"></x:time></td>
  </tr>
  <tr>
  	<td >汇款账号</td>
  	<td>${log.card}</td>
  </tr>
  <tr>
  	<td >收款人姓名</td>
  	<td>${log.username}</td>
  </tr>
  <tr>
  	<td >收款人电话</td>
  	<td>${log.phone}</td>
  </tr>
</table>

<script type="text/javascript">
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//操作， 传入 1已通过并汇款，2已拒绝
function caozuo(state){
	msg.loading('操作中');
	request.post('audit.json',{"state":state, "id":${log.id}}, function(data){
		msg.close();
		if(data.result == 1){
			msg.success('操作成功');
			location.reload();
		}else{
			msg.failure(data.info);
		}
	});
}
</script>

<jsp:include page="../../../../iw/common/foot.jsp"></jsp:include>