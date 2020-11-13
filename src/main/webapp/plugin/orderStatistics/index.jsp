<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../store/common/head.jsp">
	<jsp:param name="title" value="订单统计"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>
</head>
<body>

	显示的内容
	
<div id="list"></div>

</body>
<script type="text/javascript">
msg.loading('加载中');
var data = {
	startTime:0,
	endTime:9999999999
}
post('/plugin/orderStatistics/statistics.json',data,function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result != '1'){
		msg.failure(data.info);
	}else {
		//成功，将统计数据在页面显示出来
		
		console.log(data);
		var html = '';
		for(var i = 0; i<data.list.length; i++){
			var item = data.list[i];
			html = html + '<div>状态：'+state[item.state]+'，订单数：'+item.orderNumber+'，总金额：'+(item.money/100)+'元</div>';
		}
		document.getElementById('list').innerHTML = html;
		
	}

});
</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>
