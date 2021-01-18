<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../store/common/head.jsp">
	<jsp:param name="title" value="商家列表"/>
</jsp:include>


<jsp:include page="../../store/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="../../store/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="店铺名字"/>
		<jsp:param name="iw_name" value="name"/>
	</jsp:include>
	<jsp:include page="../../store/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系人"/>
		<jsp:param name="iw_name" value="contacts"/>
	</jsp:include>
	<jsp:include page="../../store/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="联系电话"/>
		<jsp:param name="iw_name" value="phone"/>
	</jsp:include>

<a class="layui-btn" href="javascript:list(1);" style="margin-left: 15px;">搜索</a>
	<a href="add.do" class="layui-btn layui-btn-normal" style="float: right; margin-right:10px;">开通店铺</a>
</form>

<table class="layui-table iw_table">
	<thead>
		<tr>
			<th>编号ID</th>
			<th>用户ID</th>
			<th>店铺名</th>
			<th>销量</th>
			<th>联系人</th>
			<th>电话</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="list">
			<tr>
				<td  onclick="storeDetailsView('{id}');" style="cursor: pointer; width:55px;">{id}</td>
				<td onclick="userView('{userid}');" style="cursor: pointer; width:55px;">{userid}</td>
				<td onclick="storeDetailsView('{id}');" style="cursor: pointer;">{name}</td>
				<td>{sale}</td>
				<td>{contacts}</td>
				<td>{phone}</td>
				<td style="width:100px;">{addtime}</td>
				<td style="width:100px"><a href="javascript:adminUpdateStorePassword('{userid}');" class="layui-btn" style="">重置密码</a></td>
			</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../store/common/page.jsp" ></jsp:include>

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
 //修改店铺密码
function adminUpdateStorePassword(uid){
	layer.prompt({
		formType: 0,
		value: '',
		title: '请输入新密码'
	}, function(value,index){
		layer.close(index);
		msg.loading('更改中...');
		$.post("/user/updateShopPassword.do",
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
//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{userid\}/g, item.userid)
			.replace(/\{name\}/g, item.name.length > 20 ? item.name.substring(0,20)+'...' : item.name)
			.replace(/\{addtime\}/g, formatTime(item.addtime,'Y-M-D h:m:s'))
			.replace(/\{alarmNum\}/g, item.alarmNum)
			;
}

/**
 * 获取店铺列表数据
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'50',	//这里每页显示20条数据
		'name':document.getElementById('name').name,
		'contacts':document.getElementById('contacts').value,
		'phone':document.getElementById('phone').value,

	};

	msg.loading('加载中');
	post('/shop/superadmin/store/list.json' ,data,function(data){
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


<jsp:include page="../../store/common/foot.jsp"></jsp:include>