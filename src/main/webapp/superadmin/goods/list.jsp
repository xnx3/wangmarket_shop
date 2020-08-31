<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../store/common/head.jsp">
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
<script src="/shop/store/api/goodsType/getGoodsTypeJs.json"></script>
<div style="height:10px;"></div>
<jsp:include page="../../store/common/list/formSearch_formStart.jsp" ></jsp:include>

<jsp:include page="../../store/common/list/formSearch_input.jsp">
	<jsp:param name="iw_label" value="标题" />
	<jsp:param name="iw_name" value="title" />
</jsp:include>

<a class="layui-btn" href="javascript:list(1);" style="margin-left: 15px;">搜索</a>
<div style="float: right; " class="layui-form">
	<%--<script type="text/javascript"> orderBy('id_ASC=编号,inventory_DESC=库存降序,inventory_ASC=库存升序,sale_DESC=已售数量降序,sale_ASC=已售数量升序,price_DESC=价格降序,price_ASC=价格升序'); </script>--%>
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
	<tbody id="list">
	<tr>
		<td style="text-align:center;">{id}</td>
		<td style="text-align:center;">
			{typeid}
		</td>
		<td style="text-align:left;">
			<x:substring maxLength="12" text='{title}'></x:substring>
		</td>
		<td style="text-align:center;">
			<a  href="{titlepic}" target="_black"><img src = '{titlepic}' /></a>
		</td>
		<td style="text-align:center;">{inventory}</td>
		<td style="text-align:center;">{alarmNum}</td>
		<td style="text-align:center;">
			{mark}
		</td>
		<td style="text-align:center;">{sale}</td>
		<td style="text-align:center;">{price}</td>
		<td width="50">{rank}</td>
	</tr>
	</tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../store/common/page.jsp" />
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

//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{title\}/g, item.title)
			// .replace(/\{typeid\}/g, item.typeid == null && typeof(typeid[item.typeid]) == 'undefined' ? "无": typeid[item.typeid]) //所属分类，将typeid 转为 文字说明
			.replace(/\{titlepic\}/g, item.titlepic)
			.replace(/\{inventory\}/g, item.inventory)
			.replace(/\{alarmNum\}/g, item.alarmNum)
			.replace(/\{mark\}/g, item.putaway == 0 ? "下架":"上架") //判断商品是否上下架，0 下架  1 上架
			.replace(/\{sale\}/g, item.sale)
			.replace(/\{price\}/g, item.price/100)
			.replace(/\{rank\}/g, item.rank)
			;
}
var dataTest = '{"result":1,"info":"成功","list":[{"id":6,"title":"松崎 卡尺高精度电子数显游标卡尺223","inventory":748,"alarmNum":1,"putaway":1,"sale":9,"fakeSale":1,"units":"斤","price":10000,"originalPrice":800,"addtime":1581919619,"updatetime":1597889731,"storeid":1,"typeid":5,"titlepic":"//cdn.shop.leimingyun.com/store/1/images/19d21648e1ce47179d726c2516503977.jpg","isdelete":0,"userBuyRestrict":0,"rank":0,"intro":"","version":57},{"id":7,"title":"零食混合坚果恰恰果仁干果小黄袋","inventory":529,"alarmNum":1,"putaway":1,"sale":13,"fakeSale":1,"units":"斤","price":100000,"originalPrice":800,"addtime":1581920717,"updatetime":1597894572,"storeid":1,"typeid":4,"titlepic":"//cdn.shop.leimingyun.com/store/1/images/ed1c4f9e107547f7b775181d281b7834.jpg","isdelete":0,"userBuyRestrict":0,"rank":0,"intro":"","version":37},{"id":11,"title":"圣菲火（SHENGFEIHUO）手电筒小型","inventory":1003,"alarmNum":2,"putaway":1,"sale":11,"fakeSale":20,"units":"个","price":1,"originalPrice":2300,"addtime":1583049951,"updatetime":1584708088,"storeid":1,"typeid":2,"titlepic":"//cdn.shop.leimingyun.com/store/1/images/026645215ea64274aebddbc05736679b.jpg","isdelete":0,"userBuyRestrict":0,"rank":0,"intro":null,"version":15},{"id":10,"title":"沙发","inventory":262,"alarmNum":4,"putaway":1,"sale":26,"fakeSale":0,"units":"个","price":1,"originalPrice":800,"addtime":1582005476,"updatetime":1586060094,"storeid":1,"typeid":3,"titlepic":"//cdn.shop.leimingyun.com/store/1/images/9a1511fa5d8548f1b02cc7db68f3af72.jpg","isdelete":0,"userBuyRestrict":0,"rank":2,"intro":null,"version":36}],"typeList":[{"id":1,"title":"农副馆","icon":"//cdn.shop.leimingyun.com/store/1/images/0505abd48c0e4283b06e85442858ff5c.png","rank":1,"storeid":1,"isdelete":0},{"id":2,"title":"休闲零食","icon":"//cdn.shop.leimingyun.com/store/1/images/f9b133c7a2014d8c8e1829643ed2a52a.png","rank":2,"storeid":1,"isdelete":0},{"id":3,"title":"酒水乳饮","icon":"//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/65589a47d76e43c4b0ee6f0c37aa1da8.jpg","rank":3,"storeid":1,"isdelete":0},{"id":4,"title":"粮油调味","icon":"//wangmarket1577678564.oss-cn-qingdao.aliyuncs.com/goodsType/7cc9e91476804996b3baee627ee4f36e.jpg","rank":4,"storeid":1,"isdelete":0},{"id":5,"title":"餐食料理","icon":"//cdn.shop.leimingyun.com/store/1/images/4e49c1d7f7cc41748e654a7335e487dd.png","rank":5,"storeid":1,"isdelete":0},{"id":8,"title":"水果","icon":"//cdn.shop.leimingyun.com/store/1/images/5bb4bffd242e4c1eb6bcdd27fd48d27a.gif","rank":1,"storeid":1,"isdelete":1},{"id":9,"title":"水果","icon":"//cdn.shop.leimingyun.com/store/1/images/7824f6aeb2f04aeb9d746d54e79bc5a2.gif","rank":1,"storeid":1,"isdelete":1},{"id":10,"title":"水果","icon":"//cdn.shop.leimingyun.com/store/1/images/3680d37afce24ed894cbd785668fce81.gif","rank":0,"storeid":1,"isdelete":1}],"page":{"limitStart":0,"allRecordNumber":4,"currentPageNumber":1,"everyNumber":15,"lastPageNumber":1,"nextPage":"http://localhost:9090/shop/superadmin/goods/list.json?currentPage=1","upPage":"http://localhost:9090/shop/superadmin/goods/list.json?currentPage=1","lastPage":"http://localhost:9090/shop/superadmin/goods/list.json?currentPage=1","firstPage":"http://localhost:9090/shop/superadmin/goods/list.json?currentPage=1","haveNextPage":false,"haveUpPage":false,"currentLastPage":true,"currentFirstPage":true,"upPageNumber":1,"nextPageNumber":1,"upList":[],"nextList":[]}}'
var parse = JSON.parse(dataTest);
/**
 * 获取商品列表数据
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'2',	//这里每页显示2条数据
		'title':document.getElementById('title').value,
	};

	msg.loading('加载中');
	post('/shop/superadmin/goods/list.json' ,data,function(data){
		msg.close();    //关闭“更改中”的等待提示
		checkLogin(parse);	//判断是否登录

		//已登陆
		if(parse.result == '0'){
			// msg.failure(data.info);
			msg.failure("aaaaaa");
		}else if(parse.result == '1'){
			//成功
			msg.failure("bbbbbb");
			//列表
			var html = '';
			for(var index in parse.list){
				var item = parse.list[index];
				//只显示已选中的商品
				 html = html + templateReplace(item);
			}
			document.getElementById("list").innerHTML = html;
			//分页
			page.render(parse.page);

		}
	});
}
//刚进入这个页面，加载第一页的数据
 list(1);

</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>