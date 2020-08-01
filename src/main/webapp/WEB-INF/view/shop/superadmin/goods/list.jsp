<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ page import="com.xnx3.j2ee.Global"%>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="商品列表"/>
</jsp:include>
<style type="text/css">
	.layui-table img {
		max-width: 49px;
		max-height:49px;
	}
	.toubu_xnx3_search_form {
		padding-top: 0px;
		padding-bottom: 10px;
	}
</style>
<script src="/shop/store/goodsType/getGoodsTypeJs.do"></script>
<div style="height:10px;"></div>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
	<jsp:param name="iw_label" value="标题" />
	<jsp:param name="iw_name" value="name" />
</jsp:include>

<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
<div style="float: right; " class="layui-form">
	<script type="text/javascript"> orderBy('id_ASC=编号,inventory_DESC=库存降序,inventory_ASC=库存升序,sale_DESC=已售数量降序,sale_ASC=已售数量升序,price_DESC=价格降序,price_ASC=价格升序'); </script>
</div>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
	<thead>
	<tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">所属分类</th>
		<th style="text-align:center;">商品名</th>
		<th style="text-align:center;">缩略图</th>
		<th style="text-align:center;">库存</th>
		<th style="text-align:center;">告警数量</th>
		<th style="text-align:center;">上下架</th>
		<th style="text-align:center;">已售数量</th>
		<th style="text-align:center;">价格</th>
		<th style="text-align:center;">排序</th>
	</tr>
	</thead>
	<tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center; cursor: pointer;"onclick="storeView(${item['storeid']});">${item.id }</td>
			<td style="text-align:center;">
				<c:forEach var="itemType" items="${typeList }">
					<c:if test="${item['typeid'] == itemType['id']}">
						${itemType['title']}
					</c:if>
				</c:forEach>
			</td>
			<td style="text-align:left;">
				<x:substring maxLength="12" text="${item.title }"></x:substring>
			</td>
			<td style="text-align:center;">
				<a href="${item.titlepic}" target="_black"><img src = '${item.titlepic }' /></a>
			</td>
			<td style="text-align:center;">${item.inventory }</td>
			<td style="text-align:center;">${item.alarmNum }</td>
			<td style="text-align:center;">
				<c:if test="${item.putaway == 0}">下架
				</c:if>
				<c:if test="${item.putaway == 1}">上架
				</c:if>
			</td>
			<td style="text-align:center;">${item.sale }</td>
			<td style="text-align:center;">${item.price /100}</td>
			<td width="50">${item.rank }</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" />
<div style="color: gray;margin-top: -10px; text-align:right;">
	<span style="padding-right:20px;">操作按钮提示</span>
	<span style="padding-right:20px;"><i class="layui-icon">&#xe642;</i>&nbsp;:&nbsp;编辑</span>
	<span style="padding-right:20px;"><i class="layui-icon">&#xe640;</i>&nbsp;:&nbsp;删除</span>
</div>

<script type="text/javascript">
	//查看店铺详情信息
	function storeView(id){
		layer.open({
			type: 2,
			title:'店铺详情信息',
			area: ['460px', '630px'],
			shadeClose: true, //开启遮罩关闭
			content: '/shop/superadmin/store/storeDetailsView.do?id='+id
		});
	}

	//跳转到商品详情的编辑 id:商品id
	function detail(id){
		window.location.href = '/shop/store/goods/toDetailPage.do?id=' + id;
	}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>