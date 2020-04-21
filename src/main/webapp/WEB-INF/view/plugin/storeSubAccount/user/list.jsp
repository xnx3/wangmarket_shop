<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
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
  <tbody>
  	<c:forEach items="${list}" var="user">
  		<tr>
          <td style="width:58px; cursor: pointer;">${user.username }</td>
          <td style="width:100px;"><x:time linuxTime="${user.regtime }" format="yy-MM-dd hh:mm"></x:time></td>
          <td style="width:100px;"><x:time linuxTime="${user.lasttime }" format="yy-MM-dd hh:mm"></x:time></td>
          <td style="width: 100px;">
          		<botton class="layui-btn layui-btn-sm" onclick="updatePassword(${user.id },'${user.username }');" style="margin-left: 3px;">改密码</botton>
          		<botton class="layui-btn layui-btn-sm" onclick="menu(${user.id },'${user.username }');" style="margin-left: 3px;">权限</botton>
          		<botton class="layui-btn layui-btn-sm" onclick="deleteUser(${user.id }, '${user.username }');" style="margin-left: 3px;">删除</botton>
          </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp"></jsp:include>

<script type="text/javascript">
//根据id删除用户
function deleteUser(id,name){
	var dtp_confirm = layer.confirm('确定要删除子用户“'+name+'”？删除后不可恢复！', {
	  btn: ['删除','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		
		parent.iw.loading("删除中");    //显示“操作中”的等待提示
		$.post('deleteUser.do?userid='+id, function(data){
		    parent.iw.loadClose();    //关闭“操作中”的等待提示
		    if(data.result == '1'){
		        parent.iw.msgSuccess('操作成功');
		        window.location.reload();	//刷新当前页
		     }else if(data.result == '0'){
		         parent.iw.msgFailure(data.info);
		     }else{
		         parent.iw.msgFailure();
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
		content: 'edit.do?userid='+id
	});
}

//给我子用户修改密码
function updatePassword(userid, name){
	layer.prompt({
		formType: 0,
		value: '',
		title: '给'+name+'改密码，请输入新密码',
	}, function(value, index, elem){
		parent.iw.loading("更改中");    //显示“更改中”的等待提示
		$.post(
		    "updatePassword.do", 
		    { "newPassword": value, userid:userid }, 
		    function(data){
		        parent.iw.loadClose();    //关闭“更改中”的等待提示
		        if(data.result != '1'){
		            parent.iw.msgFailure(data.info);
		        }else{
		            parent.iw.msgSuccess();
					location.reload();
		        }
		    }, 
		"json");
		
	});
}
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>