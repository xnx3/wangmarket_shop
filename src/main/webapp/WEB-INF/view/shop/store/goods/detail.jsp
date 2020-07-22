<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="商品列表"/>
</jsp:include>

<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" value="${id}" name="id" >
	
	
	<!-- 内容编辑方式，当独立页面时才会有效，才会显示。选择是使用内容富文本编辑框编辑，还是使用模板的方式编辑 -->
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">商品描述:</label>
		<div class="layui-input-block">
			<textarea class="layui-input" id="myEditor" name="detail" style="height: auto; padding-left: 0px; border: 0px;">${item.detail}</textarea>
		</div>
	</div>
	
	<div class="layui-form-item">
		<div class="layui-input-block">
			<a class="layui-btn" onclick="commit()">立即保存</a>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>
	
<!-- 配置文件 -->
<script type="text/javascript" src="/module/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/module/ueditor/ueditor.all.js"></script>
<script>

	<!-- 实例化编辑器 -->
	var ueditorText = document.getElementById('myEditor').innerHTML;
	var ue = UE.getEditor('myEditor',{
		autoHeightEnabled: true,
		autoFloatEnabled: true,
		initialFrameHeight:460  
	});
	//对编辑器的操作最好在编辑器ready之后再做
	ue.ready(function() {
		document.getElementById("myEditor").style.height='auto';
	});
	
	
	//自适应弹出层大小
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.iframeAuto(index);
	
	
	// 提交修改添加信息
	function commit() {
		var d = $("form").serialize();
		if($("#detail").val() == ''){
			msg.failure("请输入描述");
			return ;
		}
		
		//表单序列化
		parent.msg.loading("保存中");
		$.post("/admin/goods/saveDetail.do" , d, function (result) {
			parent.msg.close();
			var obj = JSON.parse(result);
			if(obj.result == '1'){
				parent.msg.success('操作成功');
				parent.layer.close(index);	//关闭当前窗口
				window.location.href = '/admin/goods/list.do';
			}else if(obj.result == '0'){
				parent.msg.failure(obj.info);
			}else{
				parent.msg.failure("修改失败");
			}
		}, "text");
		
		return false;
	}
			

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>