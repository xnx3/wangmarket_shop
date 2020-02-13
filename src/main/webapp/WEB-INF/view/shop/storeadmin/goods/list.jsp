<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="商品列表"/>
</jsp:include>
<style type="text/css">
	
	.layui-table img {
    max-width: 49px;
    max-height:49px;
}
	.head1 .head3 {
	float:right;
}
</style>
<script src="/<%=Global.CACHE_FILE %>GoodsType_typeid.js"></script>
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
<div style="color: gray;margin-right: 10px; margin-top: -10px;" class = "head1">
	<div class = "head3"><i class="layui-icon">&#xe640;</i>:删除&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3"><i class="layui-icon">&#xe642;</i>:编辑&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3" style="color: red;">操作按钮提示:&nbsp;&nbsp;&nbsp;&nbsp;</div>
</div>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">所属分类</th>
        <th style="text-align:center;">标题</th>
         <th style="text-align:center;">缩略图</th>
         <th style="text-align:center;">备注</th>
         <th style="text-align:center;">库存</th>
         <th style="text-align:center;">告警数量</th>
         <th style="text-align:center;">上下架状态</th>
        <th style="text-align:center;">定时上架</th>
        <th style="text-align:center;">定时下架</th>
        <th style="text-align:center;">已售数量</th>
        <th style="text-align:center;">价格</th>
        <th style="text-align:center;">供应商</th>
        <th style="text-align:center;">轮播图</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">
			<script type="text/javascript">document.write(typeid['${item['typeid']}']);</script>
			</td>
			<td style="text-align:center;">${item.title }</td>
			<td style="text-align:center;">
			<a  href="${item.titlepic}" target="_black"><img src = '${item.titlepic }' /></a>
			</td>
			<td style="text-align:center;">${item.note }</td>
			<td style="text-align:center;">${item.inventory }</td>
			<td style="text-align:center;">${item.alarmNum }</td>
			<td style="text-align:center;">
				<c:if test="${item.putaway == 0}">已下架</c:if>
				<c:if test="${item.putaway == 1}">上架中</c:if>
			</td>
			
			<td style="text-align:center;">
				<c:if test="${item.onlineCountdown != null  && item.onlineCountdown != 0}">
					<x:time linuxTime="${item.onlineCountdown }" format="yyyy-MM-dd HH:mm:ss"></x:time>
				</c:if>
			</td>
			<td style="text-align:center;">
				<c:if test="${item.soldoutCountdown != null && item.soldoutCountdown != 0  }">
					<x:time linuxTime="${item.soldoutCountdown }" format="yyyy-MM-dd HH:mm:ss"></x:time>
				</c:if>
			</td>
			<td style="text-align:center;">${item.sale }</td>
			<td style="text-align:center;">${item.price /100}</td>
			<td style="text-align:center;">${item.supplier }</td>
			<td style="text-align:center;">
		 	   <a class="layui-btn layui-btn-sm" onclick="imgList('${item.id }')" style=""><i class="layui-icon">添加</i></a>	 
			</td>
			<td style="text-align:center;width: 200px;">
			
		 	  <%--  <a class="layui-btn layui-btn-sm" onclick="detail('${item.id }')" style=""><i class="layui-icon">详情</i></a>	  --%>
		 	    <!-- 添加描述 -->
		 	    <!-- 修改新闻信息 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('${item.id }')" style="margin-left: 0;"><i class="layui-icon">&#xe642;</i></a>	 
		 	    <!-- 修改图片 -->
			
		 	  <!-- 删除新闻 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('${item.id }')" style="margin-left: 0"><i class="layui-icon">&#xe640;</i></a>
			</td>
		</tr>
	</c:forEach>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../../iw/common/page.jsp" />

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
		$.post('/admin/goods/delete.do?id=' + id, function(data){
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

//跳转到商品详情的编辑 id:商品id
function detail(id){
	window.location.href = '/admin/goods/toDetailPage.do?id=' + id;
}

//跳转到商品图片列表的编辑 id:商品id
function imgList(id){
	layer.open({
		type: 2, 
		title:'轮播图', 
		area: ['800px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/goods/imgList.do?id=' + id
	});	 
}

// 跳转添加或者修改页面 id 商品id
function addOrUpdate(id){
	if(id == 0) {
		id = '';
	}
	window.location.href = '/admin/goods/toEditPage.do?id=' + id;
	 /* layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/goods/toEditPage.do?id=' + id
	});	  */
}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>