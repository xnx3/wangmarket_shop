<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="name" value="图片列表"/>
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
<jsp:include page="../common/list/formSearch_formStart.jsp" ></jsp:include>

	<a class="layui-btn layui-btn-sm" onclick="addOrUpdate(0)" style="margin-left: 10px;"><i class="layui-icon">添加图片</i></a>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
		<th style="text-align:center;">排序</th>
         <th style="text-align:center;">缩略图</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="list">
		<tr>
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{rank}</td>
			<td style="text-align:center;">
			<a  href="{imageUrl}" target="_black"><img src = '{imageUrl}' /></a>
			</td>
			<td style="text-align:center;width: 200px;">

		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('{id}')" style="margin-left: 0;"><i class="layui-icon">&#xe642;</i></a>

		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('{id}')" style=""><i class="layui-icon">&#xe640;</i></a>
			</td>
		</tr>
  </tbody>
</table>

<script type="text/javascript">

//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

layui.use('upload', function(){
	var upload = layui.upload;
	
	//执行实例
	var uploadInst = upload.render({
	  elem: '.uploadImg' //绑定元素
	 ,field : 'file'
	 ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		 msg.loading('上传中...');
		}
	,done: function(res){
	  	msg.close();
		if(res.result == '1'){
			parent.msg.success("上传成功");
			window.location.reload();	//刷新当前页
		}else if(res.result == '0'){
			parent.msg.failure(res.info);
		}else{
			parent.msg.failure("上传失败");
		}
	}
	,error: function(){
		     
	}
	});
});

//修改信息 id：商品id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该商品图片？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/store/api/goods/deleteImg.json?id=' + id, function(data){
		    parent.msg.close();    //关闭“操作中”的等待提示
		    if(data.result == '1'){
		        parent.msg.success('操作成功');
		        window.location.reload();	//刷新当前页
		     }else if(data.result == '0'){
		         parent.msg.failure(data.info);
		     }else{
		         parent.msg.failure();
		     }
		});
	}, function(){
		
	});
}

// 跳转添加或者修改页面 id 图片id
function addOrUpdate(id){
	if(id == 0) {
		id = '';
	}
	 layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '300px'],
		shadeClose: true, //开启遮罩关闭
		content: '/store/goods/editImg.jsp?goodsId='+ goodsId +'&id=' + id
	});	 
}

//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{rank\}/g, item.rank)
			.replace(/\{imageUrl\}/g, item.imageUrl)
			;
}


//商品id
var goodsId = getUrlParams('id');

//获取轮播图列表信息
msg.loading('加载中');
post('/shop/store/api/goods/imgList.json?id=' + goodsId ,"",function(data){
msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result == '1'){
		//列表
		var html = '';
		for(var index in data.list){
			var item = data.list[index];

			html = html + templateReplace(item);
		}
		document.getElementById("list").innerHTML = html;
	}

});

</script>

<jsp:include page="../common/foot.jsp"></jsp:include>