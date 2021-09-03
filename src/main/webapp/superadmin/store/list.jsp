<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/wm/common/head.jsp">
	<jsp:param name="title" value="商家列表"/>
</jsp:include>

<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="店铺名字"/>
		<jsp:param name="iw_name" value="name"/>
	</jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系人"/>
		<jsp:param name="iw_name" value="contacts"/>
	</jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系电话"/>
		<jsp:param name="iw_name" value="phone"/>
	</jsp:include>

	<a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>

 	<a  href="javascript:edit(0,'');" class="layui-btn layui-btn-normal" style="float: right; margin-right:10px;">开通店铺</a> 
</form>

<table class="layui-table iw_table">
  <thead>
    <tr>
		<th style="text-align:center;">编号ID</th>
		<th style="text-align:center;">用户ID</th>
		<th style="text-align:center;">店铺名</th>
		<th style="text-align:center;">销量</th>
		<th style="text-align:center;">联系人</th>
		<th style="text-align:center;">电话</th>
		<th style="text-align:center;">创建时间</th>   
		<th style="text-align:center;">操作</th> 
    </tr> 
  </thead>
  <tbody>
	<tr v-for="item in list">
		<td :onclick="'storeDetailsView('+item.id+');'" style="cursor: pointer; width:55px;">{{item.id}}</td>
		<td :onclick="'userView('+item.userid+');'" style="cursor: pointer; width:55px;">{{item.userid}}</td>
		<td :onclick="'storeDetailsView('+item.id+');'" style="cursor: pointer;">{{item.name}}</td>
		<td>{{item.sale}}</td>
		<td>{{item.contacts}}</td>
		<td>{{item.phone}}</td>
		<td>{{formatTime(item.addtime,'Y-M-D h:m:s')}}</td>
		<td><botton class="layui-btn layui-btn-sm" :onclick="'adminUpdateStorePassword('+item.userid+');'" style="margin-left: 3px;">重置密码</botton></td>
	</tr>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" ></jsp:include>

<script>
//查看用户信息
function userView(id){
	layer.open({
		type: 2,
		title:'查看用户信息',
		area: ['460px', '630px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/user/view.do?id='+id
	});
}

//查看店铺信息
function storeDetailsView(id){
	layer.open({
		type: 2,
		title:'查看商店信息',
		area: ['460px', '532px'],
		shadeClose: true, //开启遮罩关闭
		content: '/superadmin/store/storeDetailsView.jsp?id='+id
	});
}
//开通店铺
function edit(id){
	layer.open({
		type: 2,
		title:'开通店铺',
		area: ['760px', '280px'],
		shadeClose: true, //开启遮罩关闭
		content: '/superadmin/store/edit.jsp?id='+id
	});
}

 //修改店铺密码
function adminUpdateStorePassword(uid){
	layer.prompt({
		formType: 0,
		value: '',
		title: '请输入新密码'
	}, function(value,index){
		layer.close(index);
		msg.loading('更改中...');
		$.post("/shop/superadmin/store/updateShopPassword.json?userid="+uid,
				{ "newPassword": value,"userId": uid},
				function(data){
					msg.close()
					if(data.result != '1'){
						msg.failure(data.info);
					}else{
						msg.success('修改成功!');
					}
				}
				, "json");
	});
}

//刚进入这个页面，加载第一页的数据
wm.list(1,'/shop/superadmin/store/list.json');

</script>

<jsp:include page="/wm/common/foot.jsp"></jsp:include>