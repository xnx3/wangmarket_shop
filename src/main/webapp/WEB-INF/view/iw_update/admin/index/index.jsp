<%@page import="com.xnx3.wangmarket.Authorization"%>
<%@page import="com.xnx3.j2ee.entity.User"%>
<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="管理后台"/>
</jsp:include>
<script src="${STATIC_RESOURCE_PATH}js/fun.js"></script>
<script>
var masterSiteUrl = '<%=Global.get("MASTER_SITE_URL") %>'; 
</script>
<script src="/js/admin/commonedit.js?v=<%=Global.VERSION %>"></script>

<style>
body{margin: 0;padding: 0px;height: 100%;overflow: hidden;}
#editPanel{
	position: absolute;
    top: 0px;
    width:150px;
}
#editPanel span{
	width:100%;
}

.menu{
	width:150px;
	height:100%;
	background-color: #393D49;
	position: absolute;
}
.menu ul li{
	cursor: pointer;
}

/*左侧的一级菜单的图标*/
.firstMenuIcon{
	font-size:16px;
	padding-right:8px;
	font-weight: 700;
}
/*左侧的一级菜单的文字描述*/
.firstMenuFont{
	
}

/* 二级菜单 */
.menu .layui-nav-item .layui-nav-child .subMenuItem{
	padding-left:48px;
	font-size: 13px;
}
</style>

<div id="leftMenu" class="layui-nav layui-nav-tree layui-nav-side menu">
	<ul class="">
		<shiro:hasPermission name="adminUser"> 
			<li class="layui-nav-item">
				<a href="javascript:loadUrl('/admin/user/list.do');">
					<i class="layui-icon firstMenuIcon">&#xe612;</i>
					<span class="firstMenuFont">用户管理</span>
				</a>
			</li>
		</shiro:hasPermission>
		<li class="layui-nav-item">
			<a href="javascript:loadUrl('/shop/superadmin/store/list.do');">
				<i class="layui-icon firstMenuIcon">&#xe612;</i>
				<span class="firstMenuFont">店铺管理</span>
			</a>
		</li>
		<li class="layui-nav-item">
			<a href="javascript:loadUrl('/superadmin/goods/list.jsp');">
				<i class="layui-icon firstMenuIcon">&#xe612;</i>
				<span class="firstMenuFont">商品管理</span>
			</a>
		</li>
		<li class="layui-nav-item">
			<a href="javascript:loadUrl('/superadmin/order/list.jsp');">
				<i class="layui-icon firstMenuIcon">&#xe612;</i>
				<span class="firstMenuFont">订单管理</span>
			</a>
		</li>
		<shiro:hasPermission name="adminLog"> 
			<li class="layui-nav-item" id="rizhitongji" style="display:none;">
				<a href="javascript:;">
					<i class="layui-icon firstMenuIcon">&#xe62c;</i>
					<span class="firstMenuFont">日志统计</span>
				</a>
				<dl class="layui-nav-child">
					<shiro:hasPermission name="adminLogList">
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/log/list.do');">用户动作</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminLog">
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/requestLog/fangwenList.do');">访问记录</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminSmsLog"> 
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/smslog/list.do');">手机验证</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminPayLog"> 
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/payLog/list.do');">在线支付</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminLogCartogram">
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/log/cartogram.do');">动作统计</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminLog">
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/requestLog/fangwentongji.do');">访问统计</a></dd>
					</shiro:hasPermission>
				</dl>
			</li>
		</shiro:hasPermission>
		<script>
		if('${useSLS}' == 'true'){
			try{
				//代理后台是没有这个按钮id的
				document.getElementById('rizhitongji').style.display='';
			}catch(e){}
		}
		</script>
		<shiro:hasPermission name="adminRole"> 
			<li class="layui-nav-item" id="adminRole">
				<a href="javascript:;">
					<i class="layui-icon firstMenuIcon">&#xe628;</i>
					<span class="firstMenuFont">权限管理</span>
				</a>
				<dl class="layui-nav-child">
					<shiro:hasPermission name="adminRoleRoleList">
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/role/roleList.do');">角色管理</a></dd>
					</shiro:hasPermission>
					<shiro:hasPermission name="adminRolePermissionList"> 
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/role/permissionList.do');">资源管理</a></dd>
					</shiro:hasPermission>
				</dl>
			</li>
			<script>
				try{
					if('<%=Global.getInt("ROLE_ADMIN_SHOW") %>' == '0'){
						document.getElementById('adminRole').style.display = 'none';
					}
				}catch(e){}
			
			</script>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="adminSystem"> 
			<li class="layui-nav-item">
				<a href="javascript:;">
					<i class="layui-icon firstMenuIcon">&#xe614;</i>
					<span class="firstMenuFont">系统管理</span>
				</a>
				<dl class="layui-nav-child">
					<shiro:hasPermission name="adminOnlineUserList">
						<!-- 
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/user/onlineUserList.do');">在线会员</a></dd>
						 -->
					</shiro:hasPermission>
					<shiro:hasPermission name="adminSystemVariable"> 
						<dd><a class="subMenuItem" href="javascript:loadUrl('/admin/system/variableList.do?orderBy=lasttime_ASC');">系统变量</a></dd>
					</shiro:hasPermission>
				</dl>
			</li>
		</shiro:hasPermission>
		
		<li class="layui-nav-item">
			<a href="javascript:adminUpdatePassword();" id="xiugaimima">
				<i class="layui-icon firstMenuIcon">&#xe642;</i>
				<span class="firstMenuFont">更改密码</span>
			</a>
		</li>


		<li class="layui-nav-item" id="plugin" style="display:none;">
			<a href="javascript:;">
				<i class="layui-icon firstMenuIcon">&#xe857;</i>
				<span class="firstMenuFont">功能插件</span>
			</a>
			<dl class="layui-nav-child" id="plugin_submenu">${pluginMenu }</dl>
		</li>
		<script>
			/* if(document.getElementById('plugin_submenu').innerHTML.length > 5){
				document.getElementById('plugin').style.display = '';
			 } */
		</script>
		

		<li class="layui-nav-item">
			<a href="/user/logout.do">
				<i class="layui-icon firstMenuIcon">&#xe633;</i>
				<span class="firstMenuFont">退出登陆</span>
			</a>
		</li>
		
		
		<!-- 未授权用户，请尊重作者劳动成果，保留我方版权标示及链接！授权参见：http://www.wang.market/price.html -->
		<% if(Authorization.copyright){ %>
		<li class="layui-nav-item" style="position: absolute;bottom: 0px; text-align:center;">
			<a href="http://www.wang.market" target="_black">
				<span class="firstMenuFont">power by 网市场</span>
			</a>
		</li>
		<% } %>
		
	</ul>
</div>


<div id="content" style="width: 100%;height:100%;position: absolute;left: 150px;word-wrap: break-word;border-right: 150px;box-sizing: border-box; border-right-style: dotted;">
	<iframe name="iframe" id="iframe" frameborder="0" style="width:100%;height:100%;box-sizing: border-box;"></iframe>
</div>

<script>
layui.use('element', function(){
  var element = layui.element;
});



/**
 * 在主体内容区域iframe中加载制定的页面
 * url 要加载的页面的url
 */
function loadUrl(url){
	document.getElementById("iframe").src=url;
}

//加载登录后的默认页面
loadUrl('/shop/superadmin/index/welcome.do');


function adminUpdatePassword(){
	layer.prompt({
		  formType: 0,
		  value: '',
		  title: '请输入旧密码'
	}, function(value1, index, elem){
		layer.close(index);
		layer.prompt({
			  formType: 0,
			  value: '',
			  title: '请输入新密码'
		}, function(value, index, elem){
			layer.close(index);
			iw.loading('更改中...');
			$.post("/shop/superadmin/index/updatePassword.do", { "newPassword": value,"oldPassword":value1},
				function(data){
					iw.loadClose();
					if(data.result != '1'){
						msg.failure(data.info);
					}else{
						msg.success('修改成功！新密码：'+value);
					}
				}
			, "json");
		});
	});
	
}

//右侧弹出提示
function rightTip(){
	layer.open({
	  title: '演示站点提示文字',offset: 'rb', shadeClose:true, shade:0
	  ,area: ['500px', 'auto']
	  ,btn: ['我知道了'] //可以无限个按钮
	  ,content:  '若我方对你有用，我们愿与各行业进行合作、资源交换！网站可由代理平台在线开通，或由用户自己自助开通完全无人干预！<a href="http://www.wang.market/index.html#join" target="_black" style="text-decoration: underline;color: blue;">合作方式</a><br/>'+
	   			'若您只是想要个此类网站，你可关注我们微信公众号： wangmarket'+
	   			'<div style="text-align:center;"><img src="${STATIC_RESOURCE_PATH}image/weixin_gzh.png" style="width:150px; height:150px;" /></div>'+
	   			'回复“要网站”即可免费得到一个跟此一样的网站。无任何广告！'+
	   			'另外您有什么问题、资源交换、各种合作意向，都可关注后跟我们在线沟通咨询<br/>'+
	   			'我们官网：<a href="http://www.wang.market" target="_black" style="text-decoration: underline;color: blue;">www.wang.market</a><br/>'+
	   			'我的微信：xnx3com &nbsp;&nbsp;&nbsp;QQ：921153866 <br/>'+
	   			'本程序已在GitHub开源：<a href="https://github.com/xnx3/wangmarket" target="_black" style="text-decoration: underline;color: blue;">github.com/xnx3/wangmarket</a><br/>'+
	   			'<div style="padding-top:35px;color: lightcoral; padding-left: 35px;">以高精尖技术压缩建站成本，以超低价甚至免费享受高端体验。<br/>网·市场，让每个人都有自己的网站，让价格不再是阻碍的门槛！</div>'
	  
	});
}
//只有用户名带有ceshi的才会弹出合作联系的提示。当然，如果是已授权的用户，是不弹出这个带有版权的说明的
if('${user.username}'.indexOf('ceshi') > -1){
	<% if(Authorization.copyright){ %>
		setTimeout("rightTip()",2000);
	<% } %>
}
</script>


</body>
</html>
${pluginAppendHtml}