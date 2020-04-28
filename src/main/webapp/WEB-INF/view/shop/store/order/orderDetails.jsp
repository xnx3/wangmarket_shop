<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="订单内商品列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>

<div style="margin-top: 10px; text-align: center;">
<h2>订单信息</h2>
<table class="layui-table iw_table"  style="color: black;font-size: 14px;">
  <tr>
  	<th style="text-align:center;width:80px;">id</th>
  	<th>${order.id}</th>
  </tr>
  <tr>
  	<th style="text-align:center;width:80px;">订单号</th>
  	<th>${order.no}</th>
  </tr>
  <tr>
  	<th style="text-align:center;width:80px;">订单金额</th>
  	<th>${order.totalMoney}</th>
  </tr>
  <tr>
  	<th style="text-align:center;width:80px;">应付金额</th>
  	<th>${order.payMoney}</th>
  </tr>
  <tr>
  	<th style="text-align:center; width:80px;">订单状态</th>
  	<th>
  		<script type="text/javascript">document.write(state['${order['state']}']);</script>
  		<span id="orderButton"><!-- 订单的状态按钮操作 --></span>
  	</th>
  </tr>
  <tr>
  	<th style="text-align:center; width:80px;">备注</th>
  	<th>${order.remark}</th>
  </tr>
</table>

<h2>配送信息</h2>
<table class="layui-table iw_table"  style="color: black;font-size: 14px;">
  <tr>
  	<th style="text-align:center; width:80px;">收货人</th>
  	<th>${address.username}</th>
  </tr>
  <tr>
  	<th style="text-align:center; width:80px;">联系电话</th>
  	<th>${address.phone}</th>
  </tr>
  <tr>
  	<th style="text-align:center; width:80px;">收货地址</th>
  	<th>${address.address}</th>
  </tr>
  <tr>
  	<th style="text-align:center; width:80px;">经纬度</th>
  	<th>${address.longitude},${address.latitude}</th>
  </tr>
</table>

<h2>订单内商品</h2>
<table class="layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">商品名字</th>
		<th style="text-align:center;">商品单价</th>
		<th style="text-align:center;">数量</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${goodsList }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.title }</td>
			<td style="text-align:center;">${item.price/100}/${item.units }</td>
			<td style="text-align:center;">${item.number }</td>
		</tr>
	</c:forEach>
  </tbody>
</table>


</div>
<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//确认收货
function receiveGoods(){
	msg.loading('操作中');
	request.post('receiveGoods.json',{"orderid":${order.id}}, function(data){
		msg.close();
		if(data.result == 1){
			msg.success('操作成功');
			location.reload();
		}else{
			msg.failure(data.info);
		}
	});
}
//退单相关操作，传1则是同意，传入0则是拒绝
function refund(action){
	var api = action == '1' ? 'refundAllow.json':'refundReject.json';
	msg.loading('操作中');
	request.post(api,{"orderid":${order.id}}, function(data){
		msg.close();
		if(data.result == 1){
			msg.success('操作成功');
			location.reload();
		}else{
			msg.failure(data.info);
		}
	});
}

//显示订单相关操作的功能按钮
function showOrderButton(){
	var html = '';
	switch('${order['state']}'){
		case 'pay':
			//已支付，线上支付
			html = '<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="receiveGoods();">确认收货</botton>';
			break;
		case 'private_pay':
			//已支付，线下支付
			html = '<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="receiveGoods();">确认收货</botton>';
			break;
		case 'distribution_ing':
			//配送中
			html = '<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="receiveGoods();">确认收货</botton>';
			break;
		case 'refund_ing':
			//退单中，用户申请退单
			html = '<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="refund(1);">同意</botton>';
			html = html + '<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="refund(0);">拒绝</botton>';
			break;
			
			
		default:
			console.log('default');
	}
	document.getElementById("orderButton").innerHTML = html;
}
showOrderButton();
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>