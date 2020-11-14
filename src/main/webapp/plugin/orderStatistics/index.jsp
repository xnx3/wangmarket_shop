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
<body style="padding: 15px;">
<div style="margin-top: 20px; margin-bottom: 10px;" class="layui-block">
	<label style="text-align: left;" class="layui-form-label">日期范围</label>
	<div class="layui-input-inline">
		<input type="text" class="layui-input" id="start"
			placeholder="开始日期" name="startTime" lay-key="1">
	</div>
	&nbsp;-&nbsp;
	<div class="layui-input-inline">
		<input type="text" name="endTime" class="layui-input" id="end"
			placeholder="结束日期" lay-key="2">
	</div>
	<button onclick="search();" class="layui-btn iw_list_search_submit" type="submit" >搜索</button>	
</div>

	<table class="layui-table iw_table" lay-filter="demo" id="ddtable">
			<thead>
				<tr>
					<th>状态</th>
					<th>订单数</th>
					<th>总金额</th>
				</tr>
			</thead>
	</table>	
</body>
<script type="text/javascript">
layui.use('laydate', function(){
  var laydate = layui.laydate;
 	 //常规用法
	var start1 = laydate.render({
      elem: '#start'
     ,done: function(value, date){
    	 $('#start').val(value);
      //更新结束日期的最小日期
      end1.config.min = lay.extend({}, date, {
      month: date.month - 1
      });        
    	//自动弹出结束日期的选择器
      	end1.config.elem[0].focus();
    	  }
    });
	var end1 = laydate.render({
      elem: '#end'
   	  ,done: function(value, date){
   		$('#end').val(value);
        //更新开始日期的最大日期
       start1.config.max = lay.extend({}, date, {
          month: date.month - 1
       });
   }
	});  
});

function search(){
	  //msg.loading('加载中');
    var data = {
    	startTime:$("#start").val(),
    	endTime:$("#end").val()
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
    		html = html + '<thead><tr><th>状态</th><th>订单数</th><th>总金额</th></tr></thead>';
    		for(var i = 0; i<data.list.length; i++){
    			var item = data.list[i];
    			html = html + '<tr><td>'+state[item.state]+'</td><td>'+item.orderNumber+'</td><td>'+(item.money/100)+'元</td></tr>';
    		}
    		document.getElementById('ddtable').innerHTML = html;
    		
    		if(data.list.length < 1){
    			msg.info('此时间段无任何订单');
    		}
    	}

    });    
}
</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>
