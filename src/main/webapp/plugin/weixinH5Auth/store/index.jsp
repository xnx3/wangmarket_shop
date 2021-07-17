<%@page import="com.xnx3.j2ee.util.SystemUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../store/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<div id="shifouqiyong" class="layui-form" style="padding-top:10%; text-align: left; padding-left: 20px;">
	<div id="qiyongtishi" style="display:none;">您当前没有设置微信公众号！无法启用微信授权自动登录功能。
		<br/>要使用此功能，请先
		<button onclick="parent.loadUrl('/store/paySet/index.jsp');" style="padding:3px;padding-left:5px; padding-right:5px; cursor:pointer;">点击进入设置</button>，
		<br/>其中的是否开启微信支付的方式，设置为开启，然后填写您的 微信公众号 AppId、微信公众号 AppSecret 这两项。
		<br/>如果您没有公众号，可以选择使用免认证的。如下图所示
		<br/><img src="/plugin/weixinH5Auth/images/store_index_demo.png" style="width:500px;" />
	</div>
	<style>
	.shiyongbuzhou{
		padding:20px; text-align: left; opacity: 0.9;
	}
	.shiyongbuzhou h2{
		padding-bottom:9px; padding-top:20px;
	}
	</style>
	<!-- 默认隐藏 -->
	<div class="shiyongbuzhou" id="kaiqitext" style="display:none; ">
		<h2>请输入微信授权自动登录成功后，跳转到的 url 网址</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
			<div class="layui-input-inline" style="width:400px;">
				<input type="text" name="url" id="url" placeholder="请输入登录成功后跳转到的url页面" value=""  class="layui-input" style="width:400px;">
			</div>
			<button class="layui-btn layui-btn-primary" onclick="gainUrl();">获得微信自动登录的url</button>
			<div style=" font-size: 13px; color: gray; padding-top:10px;">
				填写如： http://shopdemo.wang.market/index.html?a=1&b=2
			</div>
		</div>
		<div id="generateUrl" style="padding-top:25px;"></div>
	</div>
	<div id="zijigongzhonghao" style="display:none; padding-left:20px; padding-top:100px;">
		<h2>注意，您用的是自己的公众号，需要设置网页授权域名：</h2>
		1. 登录您的微信公众号，找到左侧菜单的 开发 - 接口权限	，打开，
		<br/>2. 接口权限中找到  网页服务 - 网页授权 - 网页授权获取用户基本信息 ，点击右侧的 修改 ，
		<br/>3. 进入修改页面后，找到其中的 网页授权域名 这一项，点击设置，吧你域名 <%=request.getServerName() %> 设置上
		<img src="/plugin/weixinH5Auth/images/wangyeshouquanshezhi.jpg" style="width:70%;" />
	</div>
</div>
<script type="text/javascript">
msg.loading('加载中');
var payset;
var store;
post('/plugin/api/weixinH5Auth/store/index.json',{},function(data){
	msg.close();	//关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//登录成功
		payset	= data.payset;
		store = data.store;
		if(payset.useWeixinServiceProviderPay == '0'){
			//使用自己配置的公众号，那么需要再公众号上设定网页授权获取用户基本信息的域名
			document.getElementById('zijigongzhonghao').style.display='';
		}
		if(data.setgongzhonghoa == true){
			//已配置了微信，可以使用授权一键登录
			document.getElementById('kaiqitext').style.display='';	//显示功能操作
		}else{
			//未配置，无法使用
			document.getElementById('qiyongtishi').style.display='';	//显示提示
		}
	}
});
function gainUrl(){
	var url = document.getElementById('url').value;
	//替换？ &
	url = url.replace(/\?/g, '_3F').replace(/&/g, '_26');
	document.getElementById('generateUrl').innerHTML = '<h3 style="padding-bottom:5px;">生成的url：</h3>'+
					window.location.origin+'/plugin/weixinH5Auth/hiddenAuthJump.do?storeid='+store.id+'&url='+url+
					'<br/><br/>您可以将此生成的url，放到到微信公众号中、或做成二维码供其他人扫码使用、或发送给群聊或者朋友，别人打开即可自动登录进去，跳转到您所设置的'+url;
	//设置缓存
	localStorage.setItem("wangmarket_shop_weixinH5Auth_url", url);
}
try{
	url = localStorage.getItem("wangmarket_shop_weixinH5Auth_url");
	if(url != null){
		document.getElementById('url').value = url;
		gainUrl();
	}
}catch(err){
	console.log(err); 
}
</script>
<jsp:include page="../../../store/common/foot.jsp"></jsp:include>