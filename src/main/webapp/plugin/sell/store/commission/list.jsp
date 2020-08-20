<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="../../../../store/common/head.jsp">
	<jsp:param name="name" value="佣金产生记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellCommissionLog_transfer_state.js"></script>
<style type="text/css">
.layui-table img {
    max-width: 49px;
    max-height:49px;
}
.toubu_xnx3_search_form {
    padding-top: 0px;
    padding-bottom: 10px;
}
</style>

<div style="height:10px;"></div>
<jsp:include page="../../../../store/common/list/formSearch_formStart.jsp" ></jsp:include>

<jsp:include page="../../../../store/common/list/formSearch_input.jsp">
	<jsp:param name="iw_label" value="是否已结算"/>
	<jsp:param name="iw_name" value="transfer_state"/>
	<jsp:param name="iw_type" value="select"/>

</jsp:include>

<a class="layui-btn" href="javascript:list(1);" style="margin-left: 15px;">搜索</a>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">记录编号</th>
		<th style="text-align:center;">收入佣金</th>
		<th style="text-align:center;">订单id</th>
        <th style="text-align:center;">用户id</th>
        <th style="text-align:center;">时间</th>
        <th style="text-align:center;">是否结算</th>
    </tr>
  </thead>
  <tbody id="tbody">

		<tr>
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{money}</td>
			<td style="text-align:center;">{orderid}</td>
			<td style="text-align:center;">{userid}</td>
			<td style="text-align:center;">{addtime}</td>
			<td style="text-align:center;">
				{transferState}
			</td>
		</tr>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../../store/common/page.jsp" />

<script>

//列表的模版
var orderTemplate = document.getElementById("tbody").innerHTML;
function templateReplace(item){
	return orderTemplate
			.replace(/\{id\}/g, item.id)
			.replace(/\{money\}/g, item.money/100)
			.replace(/\{orderid\}/g, item.orderid)
			.replace(/\{userid\}/g, item.userid)
			.replace(/\{transferState\}/g, transfer_state[item.transferState])
			.replace(/\{addtime\}/g,item.addtime != null ? formatTime(item.addtime,'Y-M-D h:m:s') : '')
			;
}

function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'30',	//这里每页显示15条数据
		'transfer_state':document.getElementById('transfer_state').value,

	};
	msg.loading('加载中');

	post('/plugin/api/sell/store/commission/list.json',data,function(data){
		msg.close();    //关闭“更改中”的等待提示
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

		if(data.result !== '1'){
			var html = '';
			for(var index in data.list){
				var item = data.list[index];
				//只显示已选中的商品
				html = html + templateReplace(item);
			}
			document.getElementById("tbody").innerHTML = html;
			//分页
			page.render(data.page);


		}

	});
}
//刚进入这个页面，加载第一页的数据
list(1);
</script>
<jsp:include page="../../../../store/common/foot.jsp"></jsp:include>