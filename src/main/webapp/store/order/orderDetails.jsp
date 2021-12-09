<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="name" value="订单内商品列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>

<style>
	h2{
		padding-top: 12px;
	    padding-bottom: 5px;
	}
</style>

<div style="margin-top: 10px; text-align: center;">
	<h2>订单信息</h2>
	<botton class="layui-btn layui-btn-sm" id="printButton" onclick="printOrder();" style="position: absolute; right: 30px;top: 18px;display:none;">打印小票</botton>
</div>

</div>

<table class="layui-table iw_table"  style="color: black;font-size: 14px;" id="orderDate">
	<tr>
		<th style="text-align:center;width:80px;">id</th>
		<th>{id}</th>
	</tr>
	<tr>
		<th style="text-align:center;width:80px;">订单号</th>
		<th>{no}</th>
	</tr>
	<tr>
		<th style="text-align:center;width:80px;">订单金额</th>
		<th>{totalMoney}元</th>
	</tr>
	<tr>
		<th style="text-align:center;width:80px;">应付金额</th>
		<th>{payMoney}元</th>
	</tr>
	 <tr>
		<th style="text-align:center; width:80px;">订单状态</th>
		<th>
			{state}
			<span id="orderButton"><!-- 订单的状态按钮操作 --></span>
		</th>
	</tr>
	<tr>
		<th style="text-align:center; width:80px;">备注</th>
		<th>{remark}</th>
	</tr>
</table>

<h2>配送信息</h2>
<table class="layui-table iw_table"  style="color: black;font-size: 14px;" id="orderAddress">
	<tr>
		<th style="text-align:center; width:80px;">收货人</th>
		<th>{username}</th>
	</tr>
	<tr>
		<th style="text-align:center; width:80px;">联系电话</th>
		<th>{phone}</th>
	</tr>
	<tr>
		<th style="text-align:center; width:80px;">收货地址</th>
		<th>{address}</th>
	</tr>
	<tr>
		<th style="text-align:center; width:80px;">经纬度</th>
		<th>{longitude},{latitude}</th>
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
		<tr>
			<td style="text-align:center;">{goodsid}</td>
			<td style="text-align:center;">{title}</td>
			<td style="text-align:center;">{priceUnits}</td>
			<td style="text-align:center;">{number}</td>
		</tr>
	</tbody>
</table>
</div>
<script src="https://res.zvo.cn/print/print.min.js" type="text/javascript"></script>
<script  type="text/javascript">
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//确认收货
function receiveGoods(){
	msg.loading('操作中');
	post('/shop/store/api/order/receiveGoods.json',{"orderid":id}, function(data){
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
//退单相关操作，传1则是同意，传入0则是拒绝
function refund(action){
	var api = action == '1' ? 'refundAllow.json':'refundReject.json';
	msg.loading('操作中');
	post('/shop/store/api/order/'+api,{"orderid":id}, function(data){
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
//显示订单相关操作的功能按钮
function showOrderButton(state){
	var html = '';
	switch(state){
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
//列表的模版
var orderTemplate = document.getElementById("orderDate").innerHTML;
function orderDate(order){
	return orderTemplate.replace(/\{id\}/g, order.id)
		.replace(/\{no\}/g, order.no)
		.replace(/\{totalMoney\}/g, order.totalMoney/100)
		.replace(/\{payMoney\}/g, order.payMoney/100)
		.replace(/\{remark\}/g, order.remark)
		.replace(/\{state\}/g, state[order.state])
		;
}
//列表的模版
var orderAddressTemplate = document.getElementById("orderAddress").innerHTML;
function orderAddressDate(orderAddress){
	return orderAddressTemplate
		.replace(/\{username\}/g, orderAddress.username)
		.replace(/\{phone\}/g, orderAddress.phone)
		.replace(/\{address\}/g, orderAddress.address)
		.replace(/\{longitude\}/g, orderAddress.longitude)
		.replace(/\{latitude\}/g, orderAddress.latitude)
		;
}
//列表的模版
var goodsListTemplate = document.getElementById("tbody").innerHTML;
function goodsListDate(item){
	return goodsListTemplate
		.replace(/\{goodsid\}/g, item.goodsid)
		.replace(/\{title\}/g, item.title)
		.replace(/\{priceUnits\}/g, item.price/100+'/'+item.units)
		.replace(/\{number\}/g, item.number)
		;
}

msg.loading('加载中');
var id = getUrlParams('id');
var detail;	//订单详情接口返回的数据
post('/shop/store/api/order/detail.json?orderid='+ id,{},function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result == '1'){
		detail = data;
		
		//登录成功
		document.getElementById("orderDate").innerHTML = orderDate(data.order);
		showOrderButton(data.order.state);
		document.getElementById("orderAddress").innerHTML = orderAddressDate(data.orderAddress);
		//列表
		var html = '';
		for(var index in data.goodsList){
			var item = data.goodsList[index];
			html = html + goodsListDate(item);
		}
		document.getElementById("tbody").innerHTML = html;
		
		//判断是否显示打印按钮
		if(data.orderRule.print == '1'){
			document.getElementById("printButton").style.display = '';
		}
	}
});

//打印订单信息，打印小票
function printOrder(){
	var goods = "";
		for( var i = 0; i < detail.goodsList.length ; i++) {
			 var pinjie = '<div style="display: flex;padding: 6px;">'+
			 '<div style="flex: 1;max-width: calc(50% + 20px);line-height: 1.2;">' + detail.goodsList[i].title + '</div>'+
			 '<div style="flex: 1;max-width: calc(50% + 20px);line-height: 1.2;">' + detail.goodsList[i].number + '</div>'+
			 '</div>';
			 goods = goods + pinjie;
			};
	var printHtml = '<div style="width:100%;">'+
					'<div style="text-align:center; font-size:22px;">' + detail.store.name +'</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">订单号：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">'+detail.order.no+'</div>'+
					'</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">金额：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">'+(detail.order.totalMoney)/1000+'元</div>'+
					'</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">备注：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">' + detail.order.remark + '</div>'+
					'</div>'+
					'<div style="display: flex;padding: 6px;">======================================</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% + 20px);line-height: 1.2;">商品名：</div>'+
						'<div style="flex: 1;max-width: calc(50% + 20px);line-height: 1.2;">数量</div>'+
					'</div>'+
					goods +
					'<div style="display: flex;padding: 6px;">======================================</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">收货人：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">' + detail.orderAddress.username + '</div>'+
					'</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">联系电话：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">' + detail.orderAddress.phone + '</div>'+
					'</div>'+
					'<div style="display: flex;padding: 6px;">'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">收货地址：</div>'+
						'<div style="flex: 1;max-width: calc(50% - 20px);line-height: 1.2;">' + detail.orderAddress.address + '</div>'+
					'</div>'
	printJS({
		printable: printHtml,
		type: 'raw-html'
	})
}

</script>
<jsp:include page="../common/foot.jsp"></jsp:include>