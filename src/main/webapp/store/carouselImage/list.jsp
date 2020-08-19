<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="name" value="轮播图列表"/>
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
<script src="/<%=Global.CACHE_FILE %>CarouselImage_type.js"></script>
<div style="height:10px;"></div>
<jsp:include page="../common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="轮播图名称" />
		<jsp:param name="iw_name" value="name" />
	</jsp:include>

<a class="layui-btn" href="javascript:list(1);" style="margin-left: 15px;">搜索</a>
	  <a class="layui-btn layui-btn-normal" onclick="addOrUpdate(0)" style=""><i class="layui-icon" style="font-size: 14px;float:right;">添加轮播图</i></a>

</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
        <th style="text-align:center;">排序</th>
        <th style="text-align:center;">名称</th>
         <th style="text-align:center;">缩略图</th>
        <th style="text-align:center;">类型</th>
        <th style="text-align:center;">跳转内容</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="list">
		<tr>
			<td style="text-align:center;">{id}</td>
			<td style="text-align:center;">{rank}</td>
			<td style="text-align:center;">{name}</td>
			<td style="text-align:center;">
			<a  href='{imageUrl}'target="_black"><img src ='{imageUrl}'/></a>
			</td>
			<td style="text-align:center;" id="typeA">
				{type}
			</td>
			<td style="text-align:center;">{imgValue}</td>
			
			<td style="text-align:center;width: 200px;">
		 	    <!-- 修改轮播图 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('{id}')" style=""><i class="layui-icon">&#xe642;</i></a>
			
		 	  <!-- 删除轮播图 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('{id}')" style=""><i class="layui-icon">&#xe640;</i></a>
			</td>
		</tr>
  </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../common/page.jsp" />

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

//修改信息 id：轮播图id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该轮播图？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/store/api/carouselImage/delete.json?id=' + id, function(data){
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


// 跳转添加或者修改页面
function addOrUpdate(notice_id){
	if(notice_id == 0) {
		notice_id = '';
	}
	 layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '500px'],
		shadeClose: true, //开启遮罩关闭
		content: '/store/carouselImage/edit.jsp?id=' + notice_id
	});	 
}
var type = new Array();
type['1'] = '打开一个商品';
type['3'] = '打开一个页面';
type['2'] = '打开某个分类';
/*页面上输出选择框的所有option，显示到页面上*/
function writeSelectAllOptionFortype(selectValue) {
	writeSelectAllOptionFortype_(selectValue, '所有', false);
}
function writeSelectAllOptionFortype_(selectValue, firstTitle, required) {
	var content = "";
	if (selectValue == '') {
		content = content + '<option value="" selected="selected">' + firstTitle + '</option>';
	} else {
		content = content + '<option value="">' + firstTitle + '</option>';
	}
	for (var p in type) {
		if (p == selectValue) {
			content = content + '<option value="' + p + '" selected="selected">' + type[p] + '</option>';
		} else {
			content = content + '<option value="' + p + '">' + type[p] + '</option>';
		}
	}
	document.write('<select name=type ' + (required ? 'required' : '') + ' lay-verify="type" lay-filter="type" id="type">' + content + '</select>');
}
//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){

	return orderTemplate.replace(/\{id\}/g, item.id)
			.replace(/\{name\}/g, item.name)
			.replace(/\{imgValue\}/g, item.imgValue)
			.replace(/\{rank\}/g, item.rank)
			.replace(/\{imageUrl\}/g, item.imageUrl)
			.replace(/\{type\}/g,type[item.type])
			;
}
/**
 * 获取分类列表数据
 * @param currentPage 要查看第几页，如传入 1
 */
function list(currentPage){
	var data = {
		'currentPage':currentPage,
		'everyNumber':'2',	//这里每页显示2条数据
		'name':document.getElementById('name').value,
	};
	msg.loading('加载中');
	post('/shop/store/api/carouselImage/list.json' ,data,function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//判断是否登录

		//已登陆
		if(data.result == '0'){
			msg.failure(data.info);
		}else if(data.result == '1'){
			//成功
			//列表
			var html = '';
			for(var index in data.list){
				var item = data.list[index];

				html = html + templateReplace(item);
			}
			document.getElementById("list").innerHTML = html;
			//分页
			page.render(data.page);
		}

	});
}
//刚进入这个页面，加载第一页的数据
list(1);
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>