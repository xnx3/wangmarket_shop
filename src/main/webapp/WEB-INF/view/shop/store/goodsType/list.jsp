<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="商品分类列表"/>
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
<div style="height:10px;"></div>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="分类名字" />
		<jsp:param name="iw_name" value="title" />
	</jsp:include>

	<a class="layui-btn" href="javascript:list(1);">搜索</a>
	<a class="layui-btn layui-btn-normal" onclick="addOrUpdate(0)" style=""><i class="layui-icon" style="font-size: 14px;">添加商品分类</i></a>

</form>
<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
        <th style="text-align:center;">排序</th>
        <th style="text-align:center;">分类名字</th>
         <th style="text-align:center;">图标</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="list">
  		<!-- list 这个div里面的html内容，其实就是购物车中，多个商品中，其中某一个商品的模版。当购物车中有多个商品时，会复制出多个 div id="item{id}" 来 
			其中可用的变量：
			{id} 这个分类的id
			{rank} 分类的排序
			{title} 分类的名字
			{icon} 分类的图标，图片url
		-->
		<tr>
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{rank}</td>
			<td style="text-align:center;">{title}</td>
			<td style="text-align:center;">
			<a  href="{icon}" target="_black"><img src = '{icon}' /></a>
			</td>
			<td style="text-align:center;width: 200px;">
		 	    <!-- 修改分类信息 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('{id}')" style=""><i class="layui-icon">&#xe642;</i></a>	 
			
		 	  <!-- 删除分类 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('{id}')" style=""><i class="layui-icon">&#xe640;</i></a>
			</td>
		</tr>
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
			msg.loading('上传中...');
		}
	,done: function(res){
		msg.close();
		if(res.result == '1'){
			parent.msg.success("上传成功");
			parent.layer.close(index);	//关闭当前窗口
			location.reload();	//刷新父窗口列表
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

//修改信息 id：goodType.id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该商品分类？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/store/goodsType/delete.do?id=' + id, function(data){
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


// 跳转添加或者修改页面 id 商品分类id
function addOrUpdate(id){
	if(id == 0) {
		id = '';
	}
	 layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '400px'],
		shadeClose: true, //开启遮罩关闭
		content: '/shop/store/goodsType/toEditPage.do?id=' + id
	});	 
}

</script>

<script>
//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
	return orderTemplate.replace(/\{id\}/g, item.id)
				.replace(/\{title\}/g, item.title)
				.replace(/\{rank\}/g, item.rank)
				.replace(/\{icon\}/g, item.icon)
				;
}

/**
 * 获取订单列表数据
 * @param state 搜索的订单的状态，多个用,分割 传入如 'generate_but_no_pay,pay_timeout_cancel'
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage, 
		'everyNumber':'15',	//这里每页显示2条数据
		'title':document.getElementById('title').value,
	};
	msg.loading('加载中');
	post('/shop/store/api/goodsType/list.json' ,data,function(data){
		msg.close();    //关闭“更改中”的等待提示
		
		if(data.result == '2'){
			//未登录
			msg.info('请先登录', function(){
				window.location.href="/shop/store/login/login.do";
			});
		}else{
			//已登陆
			if(data.result == '0'){
				msg.failure(data.info);
			}else if(data.result == '1'){
				//成功
				
				//列表
				var html = '';
				for(var index in data.list){
					var item = data.list[index];
					//只显示已选中的商品
					html = html + templateReplace(item);
				}
				document.getElementById("list").innerHTML = html;
				//分页
				page.render(data.page);
			}
		}
	});
}
//刚进入这个页面，加载第一页的数据
list(1);
</script>
<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>