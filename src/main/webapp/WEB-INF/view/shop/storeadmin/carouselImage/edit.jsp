<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="用户列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>CarouselImageType_type.js"></script>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" value="${item.id }" name="id" >
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">标题</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="name" class="layui-input" value="${item.name }" >
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">类型</label>
		<div class="layui-input-block">
				<script type="text/javascript">writeSelectAllOptionFortype_('${item.type }','请选择类型', true);</script>
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">排序</label>
		<div class="layui-input-block">
			<input type="text" name="rank" id="rank" class="layui-input" value="${item.rank }" >
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">跳转内容</label>
		<div class="layui-input-block">
			<input type="text" name="imgValue" id="imgValue" class="layui-input" value="${item.imgValue }" >
		</div>
	</div>
	
	<div class="layui-form-item">
		<div class="layui-input-block">
			<a class="layui-btn" onclick="commit()">立即保存</a>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>
<script>

	//自适应弹出层大小
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
	// 提交修改添加信息
	function commit() {
		var d = $("form").serialize();
		if($("#title").val() == ''){
			iw.msgFailure("请输入标题");
			return ;
		}
		if($("#rank").val() == ''){
			iw.msgFailure("请输入排序");
			return ;
		}
		//表单序列化
		parent.iw.loading("保存中");
		$.post("/admin/carouselImage/save.do", d, function (result) {
			parent.iw.loadClose();
			var obj = JSON.parse(result);
			if(obj.result == '1'){
				parent.parent.iw.msgSuccess("操作成功");
				parent.layer.close(index);	//关闭当前窗口
				parent.location.reload();	//刷新父窗口列表
			}else if(obj.result == '0'){
				parent.iw.msgFailure(obj.info);
			}else{
				parent.iw.msgFailure("修改失败");
			}
		}, "text");
		
		return false;
	}
			

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>