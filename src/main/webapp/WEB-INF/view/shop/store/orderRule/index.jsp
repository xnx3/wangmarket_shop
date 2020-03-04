<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="订单流程设置"/>
</jsp:include>

<style>
body{padding-left: 30px;}
</style>

<div style="height: 30px"></div>

<div>
	 订单是否有配送中这个状态
  	<c:if test="${orderRule.distribution == 0}">
  		 当前已关闭
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('distribution',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>	
  	</c:if>
  	<c:if test="${orderRule.distribution == 1}">
  		当前已开启
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('distribution',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>	
	</c:if>
	<div style="color: gray;font-size: 12px">
		<p>有的商家在商城上的精力比较少，每次去送还要将那个订单点开，点一下配送，对商家来说造成了负担。</p>
		<p>如果没有配送中这个状态，那么用户下单-付款后，是已支付，商家可以直接去送货，送完后可以将已支付的订单状态直接变为已完成。</p>
		<p>如果有配送中这个状态，用户支付完成后，商家去送货时还要找到用户订单，点击订单的配送按钮，将订单变为配送中，再去送货，送货完成后再点击完成。中间会多一步，去送货时要先将用户订单变为配送中的操作。</p>
	</div>
</div>
</br></br>
<div>
	用户是否可退款
  	<c:if test="${orderRule.refund == 0}">
  		当前已关闭
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('refund',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>	
  	</c:if>
  	<c:if test="${orderRule.refund == 1}">
  		当前已开启
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('refund',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>	
	</c:if>
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
	$.post('/shop/store/orderRule/save.do?name=' + name + '&value=' + value , function(data){
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

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  