<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("title", request.getParameter("title")); %>
<jsp:include page="/wm/common/head.jsp">
	<jsp:param name="title" value="${title }"/>
</jsp:include>

<!-- 商城及接口相关 -->
<script src="https://res.weiunity.com/shop/shop.js"></script>
<script type="text/javascript">
shop.host = window.location.origin+'/';
/**
 * 网络请求，都是用此
 * api 请求的api接口，可以传入如 api.login_token
 * data 请求的数据，如 {"goodsid":"1"} 
 * func 请求完成的回调，传入如 function(data){}
 */
function post(api, data, func){
	data['storeid'] = shop.storeid;
	if(api.indexOf('http:') == -1 && api.indexOf('/') != 0){
		api = '/' + api;
	}
	wm.post(api, data, func);
}

//如果未登录，那么拦截，弹出提示，并跳转到登录页面。 覆盖 wm head.jsp 中的此方法
function checkLogin(data){
	if(data.result == '2'){
		//未登录
		msg.info('请先登录', function(){
			window.location.href="/store/login/login.jsp";
		});
	}
}
</script>
</head>
<body>