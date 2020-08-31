<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../store/common/head.jsp">
	<jsp:param name="name" value="订单列表"/>
</jsp:include>
<style type="text/css">
.remarktr {
	background:#f2f2f2;
}
.toubu_xnx3_search_form {
    padding-top: 0px;
    padding-bottom: 10px;
	}
</style>
<div style="height:10px;"></div>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>
<jsp:include page="../../store/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../store/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单号" />
		<jsp:param name="iw_name" value="no" />
	</jsp:include>
	
	<jsp:include page="../../store/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include>

<a class="layui-btn" href="javascript:list(1);" style="margin-left: 15px;">搜索</a>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">订单号</th>
        <th style="text-align:center;">付款金额</th>
        <th style="text-align:center;">状态</th>
        <th style="text-align:center;">下单时间</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="list">
	  <tr>
		  <td style="text-align:center;">{id}</td>
		  <td style="text-align:center;">{no}</td>
		  <td style="text-align:center;">{payMoney}元</td>
		  <td style="text-align:center;">
			  {orderType}
		  </td>
		  <td style="text-align:center;">
			  {addtime}
		  </td>
		  <td style="text-align:center;">
			  <a class="layui-btn layui-btn-sm" onclick="seeGoods('{id}')" style=""><i class="layui-icon">详情</i></a>
		  </td>
	  </tr>
	<%--<c:forEach var="item" items="${list }">
			<tr>
				<td style="text-align:center;">${item.id }</td>
				<td style="text-align:center;">${item.no }</td>
				<td style="text-align:center;">${item.payMoney/100 }元</td>
				<td style="text-align:center;">
				<script type="text/javascript">document.write(state['${item['state']}']);</script>
				</td>
				<td style="text-align:center;">
					<c:if test="${item.addtime != null }">
						<x:time linuxTime="${item.addtime}" format="yy-MM-dd HH:mm:ss"></x:time>
					</c:if>
				</td>
				<td style="text-align:center;">
					<a class="layui-btn layui-btn-sm" onclick="seeGoods('${item.id }')" style=""><i class="layui-icon">详情</i></a>
				</td>
			</tr>

	</c:forEach>--%>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../store/common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//跳转添加或者修改页面 id 订单id
function seeGoods(id){
	 layer.open({
		type: 2, 
		title:'查看订单详情', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/superadmin/order/orderDetails.jsp?id=' + id
	});	 
}


//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){

	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{no\}/g, item.no)
			.replace(/\{payMoney\}/g, item.payMoney/100)
			.replace(/\{addtime\}/g, formatTime(item.addtime,'Y-M-D h:m:s'))
			.replace(/\{orderType\}/g, state[item.state])
			;
}


/**
 * 获取订单列表数据
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'15',	//这里每页显示2条数据
		'no':document.getElementById('no').value,
		'state':document.getElementById('state').value,
	};
	msg.loading('加载中');
	post('/shop/superadmin/order/list.json' ,data,function(data){
		msg.close();    //关闭“更改中”的等待提示
		checkLogin(data);	//判断是否登录

		//已登陆
		if(data.result == '0'){
			msg.failure(data.info);
		}else if(data.result == '1'){
			//列表
			var html = '';
			for(var index in data.list){
				var item = data.list[index];
				//只显示已选中的商品
				html = html + templateReplace(item);
			}
			document.getElementById("list").innerHTML = html;
			//分页
			page.render(data.page);
		}

	});
}
//刚进入这个页面，加载第一页的数据
list(1);
</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>