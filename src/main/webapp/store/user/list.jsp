<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="name" value="用户列表"/>
</jsp:include>


<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">用户ID</th>
        <th style="text-align:center;">登录用户名</th>
         <th style="text-align:center;">注册时间</th>
         <th style="text-align:center;">注册IP</th>
    </tr> 
  </thead>
  <tbody id="list">

		<tr>
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{username}</td>
			<td style="text-align:center;">
				{regtime}
			</td>
			<td style="text-align:center;">{regip}</td>
		</tr>

  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
parent.layer.iframeAuto(index);

//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{username\}/g, item.username)
			.replace(/\{regtime\}/g, formatTime(item.regtime,'Y-M-D h:m:s'))
			.replace(/\{regip\}/g, item.regip)
			;
}
/**
 * 获取分类列表数据
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'10',	//这里每页显示2条数据
	};

msg.loading('加载中');
post('/shop/store/api/user/list.json' ,data,function(data){
msg.close();    //关闭“更改中”的等待提示
checkLogin(data);	//判断是否登录

	//已登陆
	if(data.result == '0'){
		msg.failure(data.info);
	}else if(data.result == '1'){
		//成功

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

<jsp:include page="../common/foot.jsp"></jsp:include>