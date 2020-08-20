<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="../../../../store/common/head.jsp">
	<jsp:param name="name" value="提现记录列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>SellTixianLog_state.js"></script>
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
		<jsp:param name="iw_label" value="状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">编号</th>
		<th style="text-align:center;">结算佣金</th>
        <th style="text-align:center;">用户id</th>
        <th style="text-align:center;">申请时间</th>
        <th style="text-align:center;">状态</th>
    </tr> 
  </thead>
  <tbody id="tbody">
 		<tr style="cursor:pointer;" onclick="detail({id});">
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{money}元</td>
			<td style="text-align:center;">{userid}</td>
			<td style="text-align:center;">{addtime}</td>
			<td style="text-align:center;">{state}</td>
		</tr>
   </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../../store/common/page.jsp" />

<script>
//查看详情
function detail(id){
	 layer.open({
		type: 2, 
		title:'查看提现记录详情', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/plugin/sell/store/tixian/detail.jsp?id=' + id
	});	 
}


//列表的模版
var orderTemplate = document.getElementById("tbody").innerHTML;
function templateReplace(item){
  	return orderTemplate
			.replace(/\{id\}/g, item.id)
			.replace(/\{money\}/g, item.money/100)
			.replace(/\{userid\}/g, item.userid)
			.replace(/\{state\}/g,state[item.state])
			.replace(/\{addtime\}/g,item.addtime != null ? formatTime(item.addtime,'Y-M-D h:m:s') : '')
			;
}

function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'30',	//这里每页显示15条数据
		'state':document.getElementById('state').value,

	};
	msg.loading('加载中');
	var storelist;

	post('/plugin/api/sell/store/tixian/list.json',data,function(data){
		msg.close();    //关闭“更改中”的等待提示
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

		if(data.result !== '1'){
			storelist = data.list;
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