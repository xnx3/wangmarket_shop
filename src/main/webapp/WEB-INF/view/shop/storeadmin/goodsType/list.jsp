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
<script src="/<%=Global.CACHE_FILE %>SlidesShowType_type.js"></script>
<div style="height:10px;"></div>
<jsp:include page="../../../iw/common/list/formSearch_formStart.jsp" ></jsp:include>

	<jsp:include page="../../../iw/common/list/formSearch_input.jsp">
		<jsp:param name="iw_label" value="分类名字" />
		<jsp:param name="iw_name" value="name" />
	</jsp:include>
	
	<input class="layui-btn iw_list_search_submit" type="submit" value="搜索" />
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
  <tbody id="tbody">
	<c:forEach var="item" items="${list }">
		<tr>
			<td style="text-align:center;">${item.id }</td>
			<td style="text-align:center;">${item.rank }</td>
			<td style="text-align:center;">${item.title }</td>
			<td style="text-align:center;">
			<a  href="${item.icon}" target="_black"><img src = '${item.icon }' /></a>
			</td>
			<td style="text-align:center;width: 200px;">
		 	    <!-- 修改新闻信息 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="addOrUpdate('${item.id }')" style=""><i class="layui-icon">&#xe642;</i></a>	 
			
		 	  <!-- 删除新闻 -->
		 	   <a class="layui-btn layui-btn-sm" onclick="deleteMes('${item.id }')" style=""><i class="layui-icon">&#xe640;</i></a>
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

//修改信息 id：goodType.id
function deleteMes(id){
	var dtp_confirm = layer.confirm('确定要删除该商品分类？', {
		  btn: ['确认','取消'] //按钮
	}, function(){
		layer.close(dtp_confirm);
		parent.iw.loading("删除中");    //显示“操作中”的等待提示
		$.post('/shop/storeadmin/goodsType/delete.do?id=' + id, function(data){
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
		content: '/shop/storeadmin/goodsType/toEditPage.do?id=' + id
	});	 
}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>