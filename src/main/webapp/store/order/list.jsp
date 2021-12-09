<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="订单列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Order_state.js"></script>

<div style="height:10px;"></div>
<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单号" />
		<jsp:param name="iw_name" value="no" />
	</jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="订单状态"/>
		<jsp:param name="iw_name" value="state"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include>
	<label style="text-align: left;" class="layui-form-label">日期范围</label>
		<div class="layui-input-inline">
			<jsp:include page="/wm/common/edit/form_date.jsp">
			<jsp:param name="wm_name" value="startTime"/>
			<jsp:param name="wm_value" value="${entity.selecttime }"/>
			<jsp:param name="wm_type" value="date"/>
		    </jsp:include>
		</div>
		&nbsp;-&nbsp;
		<div class="layui-input-inline">
			<jsp:include page="/wm/common/edit/form_date.jsp">
			<jsp:param name="wm_name" value="endTime"/>
			<jsp:param name="wm_value" value="${entity.selecttime }"/>
			<jsp:param name="wm_type" value="date"/>
		    </jsp:include>
		</div>
	<a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>
</form>

<table class="aui-table-responsive layui-table iw_table">
	<thead>
		<tr>
			<th>ID</th>
			<th>订单号</th>
			<th>付款金额</th>
			<th>状态</th>
			<th>下单时间</th>
			<th>操作</th>
		</tr> 
	</thead>
	<tbody>
		<tr v-for="item in list">
			<td>{{item.id}}</td>
			<td>{{item.no}}</td>
			<td>{{item.payMoney/100}}元</td>
			<td>{{state[item.state]}}</td>
			<td>{{formatTime(item.addtime,'Y-M-D h:m:s')}}</td>
			<td style="width: 100px;">
				<botton class="layui-btn layui-btn-sm" :onclick="'seeGoods('+item.id+', \''+item.no+'\');'" style="margin-left: 3px;">详情</botton>
			</td> 
		</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" />

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
function change(flag){
	console.log("222");
	console.log(flag);
	if(flag == true){
		 console.log("11111");
		 flag = flase;
	}
}
//跳转添加或者修改页面 id 订单id
function seeGoods(id,no){
	 layer.open({
		type: 2,
		title:'查看订单详情',
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/store/order/orderDetails.jsp?id=' + id
	});
}
//跳转添加或者修改页面 id 订单id
function refund(id,no){
	 layer.open({
		type: 2,
		title:'查看商品退款记录',
		area: ['700px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/order/refundLog.do?id=' + id
	});
}

//刚进入这个页面，加载第一页的数据
wm.list(1,'/shop/store/api/order/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>