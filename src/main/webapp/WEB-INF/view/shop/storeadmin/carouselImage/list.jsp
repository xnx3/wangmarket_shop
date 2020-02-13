<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="name" value="轮播图列表"/>
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
<script src="/<%=Global.CACHE_FILE %>CarouselImageType_type.js"></script>
<div style="height:10px;"></div>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="轮播图名称" />
		<jsp:param name="iw_name" value="name" />
	</jsp:include>
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
	  <a class="layui-btn layui-btn-normal" onclick="addOrUpdate(0)" style=""><i class="layui-icon" style="font-size: 14px;">添加轮播图</i></a>

<div style="color: gray;margin-right: 40px;" class = "head1">
	<div class = "head3"><i class="layui-icon">&#xe640;</i>:删除&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3"><i class="layui-icon">&#xe64a;</i>:上传图片&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3"><i class="layui-icon">&#xe642;</i>:编辑&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3"><i class="layui-icon">&#xe642;</i>轮播图建议尺寸:414px*154px&nbsp;&nbsp;&nbsp;&nbsp;</div>
	<div class = "head3" style="color: red;">操作按钮提示:&nbsp;&nbsp;</div>
</div>
</form>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
  <thead>
    <tr>
		<th style="text-align:center;">ID</th>
        <th style="text-align:center;">名称</th>
         <th style="text-align:center;">缩略图</th>
        <th style="text-align:center;">类型</th>
        <th style="text-align:center;">排序</th>
        <th style="text-align:center;">操作</th>
    </tr> 
  </thead>
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.name }</td>
			<td style="text-align:center;">
			<a  href="${item.imageUrl}" target="_black"><img src = '${item.imageUrl }' /></a>
			</td>
			<td style="text-align:center;">
				<script type="text/javascript">document.write(type['${item['type']}']);</script>
			</td>
			
			<td style="text-align:center;">${item.rank }</td>
			<td style="text-align:center;width: 200px;">
		 	    <!-- 修改新闻信息 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('${item.id }')" style=""><i class="layui-icon">&#xe642;</i></a>	 
		 	    <!-- 修改图片 -->
		 	   <a class="layui-btn layui-btn-sm uploadImg" lay-data = "{url: '/admin/carouselImage/uploadImg.do?slideshow_id=${item.id}'}" style="margin-left: 0;margin-top: 3px;"><i class="layui-icon">&#xe64a;</i></a>
			
		 	  <!-- 删除新闻 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('${item.id }')" style=""><i class="layui-icon">&#xe640;</i></a>
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

//修改信息 id：轮播图id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该轮播图？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.iw.loading("删除中");    //显示“操作中”的等待提示
		$.post('/admin/carouselImage/delete.do?id=' + id, function(data){
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


// 跳转添加或者修改页面
function addOrUpdate(notice_id){
	if(notice_id == 0) {
		notice_id = '';
	}
	 layer.open({
		type: 2, 
		title:'编辑页面', 
		area: ['500px', '400px'],
		shadeClose: true, //开启遮罩关闭
		content: '/admin/carouselImage/toAddPage.do?id=' + notice_id
	});	 
}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>