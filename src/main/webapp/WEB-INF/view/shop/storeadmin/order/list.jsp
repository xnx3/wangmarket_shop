<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="订单列表"/>
</jsp:include>
<style type="text/css">
.remarktr {
	background:#f2f2f2;
}
</style>
<div style="height:10px;"></div>
<script src="/<%=Global.CACHE_FILE %>OrderState_state.js"></script>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单号" />
		<jsp:param name="iw_name" value="no" />
	</jsp:include>
	
	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">订单号</th>
        <th style="text-align:center;">用户ID</th>
         <th style="text-align:center;">总金额</th>
        <th style="text-align:center;">实际支付的金额</th>
        <th style="text-align:center;">状态</th>
        <th style="text-align:center;">创建时间</th>
        <th style="text-align:center;">支付时间</th>
        <th style="text-align:center;">订单内商品</th>
        <th style="text-align:center;">退款记录</th>
        <!-- <th style="text-align:center;">操作</th> -->
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
			<tr>
				<td style="text-align:center;">${item.id }</td>
				<td style="text-align:center;">${item.no }</td>
				<td style="text-align:center;">${item.userid }</td>
				<td style="text-align:center;">${item.totalMoney }</td>
				<td style="text-align:center;">${item.payMoney }</td>
				<td style="text-align:center;">
				<script type="text/javascript">document.write(state['${item['state']}']);</script>
				</td>
				<td style="text-align:center;">
					<c:if test="${item.addtime != null }">
						<x:time linuxTime="${item.addtime}" format="yy-MM-dd HH:mm:ss"></x:time>
					</c:if>
				</td>
				<td style="text-align:center;">
					<c:if test="${item.payTime != null }">
						<x:time linuxTime="${item.payTime}" format="yy-MM-dd HH:mm:ss"></x:time>
					</c:if>
					<c:if test="${item.payTime == null }">
						未支付
					</c:if>
				</td>				
				<td style="text-align:center;">
					<a class="layui-btn layui-btn-sm" onclick="seeGoods('${item.id }')" style=""><i class="layui-icon">&#xe702;</i></a>
				</td>
				<td style="text-align:center;">
					<c:if test="${item.state.equals('refund') }">
						<a class="layui-btn layui-btn-sm" onclick="refund('${item.id }')" style=""><i class="layui-icon">&#xe702;</i></a>
					</c:if>
				</td>
			</tr>
			
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

function change(flag){
	console.log("222");
	console.log(flag);
	if(flag == true){
		 console.log("11111");
		 flag = flase;
	}
}

//跳转添加或者修改页面 id 订单id
function seeGoods(id){
	 layer.open({
		type: 2, 
		title:'查看订单商品', 
		area: ['700px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/order/orderGoodsList.do?id=' + id 
	});	 
}
//跳转添加或者修改页面 id 订单id
function refund(id){
	 layer.open({
		type: 2, 
		title:'查看商品退款记录', 
		area: ['700px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/order/refundLog.do?id=' + id 
	});	 
}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>