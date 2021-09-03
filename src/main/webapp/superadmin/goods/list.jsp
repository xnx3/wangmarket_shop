<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/wm/common/head.jsp">
	<jsp:param name="title" value="商品列表"/>
</jsp:include>
<style type="text/css">
.layui-table img {
    max-width: 25px;
    max-height:25px;
}
.toubu_xnx3_search_form {
    padding-top: 0px;
    padding-bottom: 10px;
}
</style>
<script src="/<%=Global.CACHE_FILE %>GoodsType_putaway.js"></script>

<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="商品名称"/>
		<jsp:param name="iw_name" value="title"/>
	</jsp:include>

       <a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>    
</form>

<table class="layui-table iw_table">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">商品名</th>
		<th style="text-align:center;">缩略图</th>
		<th style="text-align:center;">库存</th>
		<th style="text-align:center;">上下架</th>
		<th style="text-align:center;">已售数量</th>
		<th style="text-align:center;">价格</th>   
    </tr> 
  </thead>
  <tbody>
	<tr v-for="item in list">
		<td style="text-align:center;">{{item.id}}</td>
		<td>{{item.title}}</td>
		<td style="text-align:center;">
			<a :href='item.titlepic'target="_black" class='img'><img :src='item.titlepic'/></a>
		</td>
		<td style="text-align:center;">{{item.inventory}}</td>
		<td style="text-align:center;">{{putaway[item.putaway]}}</td> 
		<td style="text-align:center;">{{item.sale}}</td>
		<td style="text-align:center;">{{item.price}}</td>
	</tr>
  </tbody>
</table>

<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp"></jsp:include>

<script type="text/javascript">

//刚进入这个页面，自动加载第一页的数据
wm.list(1,'/shop/superadmin/goods/list.json');
</script>

<jsp:include page="/wm/common/foot.jsp"></jsp:include>