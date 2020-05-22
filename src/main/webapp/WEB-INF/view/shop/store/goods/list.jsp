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
<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
	<jsp:param name="iw_label" value="商品分类"/>
	<jsp:param name="iw_name" value="typeid"/>
	<jsp:param name="iw_type" value="select"/>
</jsp:include> 
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
	<a class="layui-btn layui-btn-normal" onclick="addOrUpdate(0)" style=""><i class="layui-icon" style="font-size: 14px;">添加商品</i></a>
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
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">
			<script type="text/javascript">document.write(typeid['${item["typeid"]}']);</script>
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
				 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updatePutWay('${item.id }');" style="margin-left: 3px;margin-top:-1px;">更改</botton>	
				</c:if>
				<c:if test="${item.putaway == 1}">上架
				 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updatePutWay('${item.id }');" style="margin-left: 3px;margin-top:-1px;">更改</botton>	
				</c:if>
			</td>
			
			<td style="text-align:center;">${item.sale }</td>
			<td style="text-align:center;">${item.price /100}</td>
			<td width="50" onclick="updateRank('${item.id }', '${item.rank }', '${item.title }');" style="cursor:pointer;">${item.rank }</td>
			<td style="text-align:center;width: 200px;">
		 	   <a class="layui-btn layui-btn-sm" onclick="imgList('${item.id }')" style="margin-left: 0;"><i class="layui-icon">轮播图</i></a>	 
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('${item.id }')" style="margin-left: 0;"><i class="layui-icon">&#xe642;</i></a>	 
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('${item.id }')" style="margin-left: 0"><i class="layui-icon">&#xe640;</i></a>
			</td>
			
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

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
parent.layer.iframeAuto(index);


layui.use('upload', function(){
	var upload = layui.upload;
	
	//执行实例
	var uploadInst = upload.render({
	  elem: '.uploadImg' //绑定元素
	 ,field : 'file'
	 ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			iw.loading('上传中...');
		}
	,done: function(res){
		iw.loadClose();
		if(res.result == '1'){
			parent.iw.msgSuccess("上传成功");
			parent.layer.close(index);	//关闭当前窗口
			location.reload();	//刷新父窗口列表
		}else if(res.result == '0'){
			parent.iw.msgFailure(res.info);
		}else{
			parent.iw.msgFailure("上传失败");
		}
	}
	,error: function(){
		     
	}
	});
});

//修改信息 id：商品id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该商品？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.iw.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/store/goods/delete.do?id=' + id, function(data){
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
//修改信息 id：商品id
function updatePutWay(id){
	
	parent.iw.loading("更改中");    //显示“操作中”的等待提示
	$.post('/shop/store/goods/updatePutaway.do?id=' + id, function(data){
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
	
}

//跳转到商品详情的编辑 id:商品id
function detail(id){
	window.location.href = '/shop/store/goods/toDetailPage.do?id=' + id;
}

//跳转到商品图片列表的编辑 id:商品id
function imgList(id){
	layer.open({
		type: 2, 
		title:'轮播图', 
		area: ['800px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/shop/store/goods/imgList.do?id=' + id
	});	 
}

// 跳转添加或者修改页面 id 商品id
function addOrUpdate(id){
	if(id == 0) {
		id = '';
	}
	window.location.href = '/shop/store/goods/toEditPage.do?id=' + id;
	 /* layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/goods/toEditPage.do?id=' + id
	});	  */
}

/**
 * 更改某项的排序
 * @param id 要修改的 某项的id编号，要改那一项
 * @param rank 当前的排序序号，默认值
 * @param name 栏目名字，仅仅只是提示作用
 */
function updateRank(id,rank,title){
	layer.prompt({title: '请输入排序数字，数字越小越靠前', formType: 3, value: ''+rank}, function(text, index){
		parent.msg.loading("保存中");    //显示“操作中”的等待提示
		$.post('updateRank.do?id='+id+'&rank='+text, function(data){
		    parent.msg.close();    //关闭“操作中”的等待提示
		    if(data.result == '1'){
		        //由最外层发起提示框
				parent.msg.success('操作成功');
				//刷新当前页
				window.location.reload();	
		     }else if(data.result == '0'){
		         parent.msg.failure(data.info);
		     }else{
		         parent.msg.failure('操作失败');
		     }
		});
		
	});
}
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>