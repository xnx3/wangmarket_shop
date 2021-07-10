<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="name" value="轮播图列表"/>
</jsp:include>

<script src="/<%=Global.CACHE_FILE %>CarouselImage_type.js"></script>
<div style="height:10px;"></div>
<jsp:include page="/wm/common/list/formSearch_formStart.jsp" ></jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="轮播图名称" />
		<jsp:param name="iw_name" value="name" />
	</jsp:include>
	<jsp:include page="/wm/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="类型"/>
		<jsp:param name="iw_name" value="type"/>
		<jsp:param name="iw_type" value="select"/>
	</jsp:include>
	<a class="layui-btn" href="javascript:wm.list(1);" style="margin-left: 15px;">搜索</a>
	<a href="javascript:editItem(0,'');" class="layui-btn layui-btn-normal" style="float: right; margin-right:10px;">添加轮播图</a>
</form>

<style type="text/css">
.layui-table img {
    max-width: 49px;
    max-height:29px;
}
</style>
<table class="layui-table iw_table">
 <thead>
    <tr>
		<th>ID</th>
		<th>排序</th>
		<th>名称</th>
		<th>缩略图</th>
		<th>类型</th>
		<th>跳转内容</th> 
		<th>操作</th>  
    </tr> 
 </thead>
 <tbody>
	<tr v-for="item in list">
		<td>{{item.id}}</td>
		<td>{{item.rank}}</td>
		<td>{{item.name}}</td>
		<td>
			<a :href='item.imageUrl'target="_black"><img :src='item.imageUrl'/></a>
		</td>
		<td>{{type[item.type]}}</td>
		<td>{{item.imgValue}}</td>
		<td style="width: 100px;">
			<botton class="layui-btn layui-btn-sm" :onclick="'editItem('+item.id+', \''+item.name+'\');'" style="margin-left: 3px;">修改</botton>
			<botton class="layui-btn layui-btn-sm" :onclick="'deleteItem('+item.id+', \''+item.name+'\');'" style="margin-left: 3px;">删除</botton>
		</td>
	</tr>
 </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="/wm/common/page.jsp" />

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
		checkLogin(res);	//验证登录状态。如果未登录，那么跳转到登录页面
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

//根据id删除
function deleteItem(id,name){
	var dtp_confirm = layer.confirm('确定要删除轮播图“'+name+'”？删除后不可恢复！', {
	  btn: ['删除','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.msg.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/store/api/carouselImage/delete.json?id='+id, function(data){
		    parent.msg.close();    //关闭“操作中”的等待提示
		    checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
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

//修改
function editItem(id, name){
	layer.open({
		type: 2, 
		title:id > 0? '修改轮播图&nbsp;[&nbsp;'+name+'&nbsp;]&nbsp;':'添加轮播图', 
		area: ['580px', '450px'],
		shadeClose: true, //开启遮罩关闭
		content: '/store/carouselImage/edit.jsp?id='+id
	});
}
//刚进入这个页面，加载第一页的数据
wm.list(1,'/shop/store/api/carouselImage/list.json');
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>