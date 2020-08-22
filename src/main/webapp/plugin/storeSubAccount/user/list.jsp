<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="子用户列表"/>
</jsp:include>


<a href="javascript:menu(0,'');" id="add_first_permission_aTag" class="layui-btn layui-btn-normal" style="float: right; margin:10px;"><i class="layui-icon">&#xe654;</i>&nbsp;开通子用户</a>

<table class="layui-table iw_table" style="margin-top:12px;">
  <thead>
    <tr>
        <th>登陆用户名</th>
        <th>账户开通时间</th>
        <th>最后上线时间</th>
        <th>操作</th>    
    </tr> 
  </thead>
  <tbody id="tbody">
   		<tr>
          <td style="width:58px; cursor: pointer;">{username}</td>
          <td style="width:100px;">{regtime}</td>
          <td style="width:100px;">{lasttime}</td>
          <td style="width: 100px;">
          		<botton class="layui-btn layui-btn-sm" onclick="updatePassword('{id}','{username}');" style="margin-left: 3px;">改密码</botton>
          		<botton class="layui-btn layui-btn-sm" onclick="menu('{id}','{username}');" style="margin-left: 3px;">权限</botton>
          		<botton class="layui-btn layui-btn-sm" onclick="deleteUser('{id}','{username}');" style="margin-left: 3px;">删除</botton>
          </td>
      </tr>
   </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../store/common/page.jsp"></jsp:include>

<script type="text/javascript">
//根据id删除用户
function deleteUser(id,name){
	var dtp_confirm = layer.confirm('确定要删除子用户“'+name+'”？删除后不可恢复！', {
	  btn: ['删除','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		$.post('/plugin/api/storeSubAccount/user/deleteUser.json?userid='+id, function(data){
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

	}, function(){
	});
}


/**
 * 添加账户/修改权限
 * id 要修改的子用户的userid
 * username 要修改用户的登陆的username
 */
function menu(id,username){
	layer.open({
		type: 2, 
		title: id<1 ? '添加子用户':'修改用户【 '+username+' 】的管理权限', 
		area: ['1000px', id>0? '630px':'750px'],
		shadeClose: true, //开启遮罩关闭
		content: '/plugin/storeSubAccount/user/edit.jsp?userid='+id
		// content: '/plugin/api/storeSubAccount/user/edit.do?userid='+id
	});
}

//给我子用户修改密码
function updatePassword(userid, name){
	layer.prompt({
		formType: 0,
		value: '',
		title: '给'+name+'改密码，请输入新密码',
	}, function(value, index, elem){
		parent.msg.loading("更改中");    //显示“更改中”的等待提示
		$.post(
		    "/plugin/api/storeSubAccount/user/updatePassword.json",
		    { "newPassword": value, userid:userid }, 
		    function(data){
		        parent.msg.close();    //关闭“更改中”的等待提示
		        if(data.result != '1'){
		            parent.msg.failure(data.info);
		        }else{
		            parent.msg.success("修改成功");
					location.reload();
		        }
		    }, 
		"json");
		
	});
}
//列表的模版
var orderTemplate = document.getElementById("tbody").innerHTML;
function templateReplace(item){
    return orderTemplate
        .replace(/\{id\}/g, item.id)
        .replace(/\{username\}/g, item.username)
        .replace(/\{regtime\}/g,item.regtime != null ? formatTime(item.regtime,'Y-M-D h:m:s') : '')
        .replace(/\{lasttime\}/g,item.lasttime != null ? formatTime(item.lasttime,'Y-M-D h:m:s') : '')
        ;
}
function list(currentPage){
    msg.loading('加载中');

    post('/plugin/api/storeSubAccount/user/list.json',{},function(data){
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

<jsp:include page="../../../store/common/foot.jsp"></jsp:include>