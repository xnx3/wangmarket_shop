<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="订单流程设置"/>
</jsp:include>

<style>
body{padding-left: 30px;}
</style>

<div style="height: 30px"></div>

<div>
	订单支付超时时间
  	<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
	    <div class="layui-input-inline" style="width:300px;">
	      <input type="number" name="notPayTimeout" id="notPayTimeout" placeholder="单位是秒" class="layui-input" style="width:100%">
	    </div>
	    <button class="layui-btn layui-btn-primary" onclick="updateState('notPayTimeout',document.getElementById('notPayTimeout').value)">保存</button>
	    <div style=" font-size: 13px; color: gray; padding-top:15px;">
	    	订单创建后，超过多长时间未支付，就自动变为支付超时已取消。这里单位是秒
	    </div>
	</div>
</div>
</br>
<hr/>
</br>

<div>
	 订单是否有配送中这个状态
	<span id="distribution"></span>

	<div style="color: gray;font-size: 12px">
		<p>有的商家在商城上的精力比较少，每次去送还要将那个订单点开，点一下配送，对商家来说造成了负担。</p>
		<p>如果没有配送中这个状态，那么用户下单-付款后，是已支付，商家可以直接去送货，送完后可以将已支付的订单状态直接变为已完成。</p>
		<p>如果有配送中这个状态，用户支付完成后，商家去送货时还要找到用户订单，点击订单的配送按钮，将订单变为配送中，再去送货，送货完成后再点击完成。中间会多一步，去送货时要先将用户订单变为配送中的操作。</p>
	</div>
</div>
</br>
<hr/>
</br>
<div>
	用户是否可退款
	<span id="refund"></span>

	<div style="color: gray ;font-size: 12px;font-size: 12px">
		<p>如果用户可退款，那么用户端会有退款按钮，用户可以点击退款按钮发起退款。</p>
		<p>如果用户不可退款，那么用户端不会有退款按钮，用户是不可以退款的</p>
	</div>
	</br>
</div>
</br>
</br>

<script type="text/javascript">
//更改操作
function updateState(name,value) {
	parent.msg.loading("更改中");    //显示“操作中”的等待提示
	$.post('/shop/store/api/orderRule/save.json?name=' + name + '&value=' + value , function(data){
		parent.msg.close();    //关闭“操作中”的等待提示
	    if(data.result == '1'){
	        parent.msg.success('操作成功');
	        window.location.reload();	//刷新当前页
	     }else if(data.result == '0'){
	         parent.msg.failure(data.info);
	     }else{
	        parent.msg.failure();
	     }
	});
}
msg.loading('加载中');
var orderRule;

post('/shop/store/api/orderRule/index.json','',function(data){
msg.close();    //关闭“更改中”的等待提示
checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result == '1'){
		orderRule = data.orderRule;
		//订单支付超时时间
		document.getElementById('notPayTimeout').value = orderRule.notPayTimeout;
	//订单是否有配送中这个状态
	if(orderRule.distribution == 0){
		document.getElementById('distribution').innerHTML = '&nbsp当前已关闭<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState(\'distribution\',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>';
	}else if(orderRule.distribution == 1){
		document.getElementById('distribution').innerHTML = '&nbsp当前已开启<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState(\'distribution\',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>';
	}
	//用户是否可退款
	if(orderRule.refund == 0){
	document.getElementById('refund').innerHTML =  '当前已关闭<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState(\'refund\',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>';
	}else if(orderRule.refund == 1){
	document.getElementById('refund').innerHTML =  '当前已开启<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState(\'refund\',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>';
	}
	}
});
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>