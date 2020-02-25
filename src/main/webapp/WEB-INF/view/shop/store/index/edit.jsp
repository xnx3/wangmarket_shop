<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="商品分类"/>
</jsp:include>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" value="${item.id }" name="storeId" >
	<input type="hidden" value="${type }" name="type" id="type" >
	<c:if test="${type == 2 }">
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">省份</label>
			<div class="layui-input-block">
				<input type="text" name="province" id="province" class="layui-input" value="${item.province }" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			比如:山东省</div>
		</div>
		
		
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">市</label>
			<div class="layui-input-block">
				<input type="text" name="city" id="city" class="layui-input" value="${item.city }" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			比如:潍坊市</div>
		</div>
		
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">区</label>
			<div class="layui-input-block">
				<input type="text" name="district" id="district" class="layui-input" value="${item.district }" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			比如:寒亭区</div>
		</div>
	</c:if>
	
	<c:if test="${type == 1 }">
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">经度</label>
			<div class="layui-input-block">
				<input type="text" name="longitude" id="longitude" class="layui-input" value="${item.longitude }" >
			</div>
		</div>
		
		
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">纬度</label>
			<div class="layui-input-block">
				<input type="text" name="latitude" id="latitude" class="layui-input" value="${item.latitude }" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		<a  target="_black" href="https://lbs.qq.com/tool/getpoint/"><u>获取经纬度</u></a></div>
		</div>
	</c:if>
	
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
		if($("#type").val() == 2){
			if($("#city").val() == ''){
				iw.msgFailure("请输入城市");
				return ;
			}
			if($("#province").val() == ''){
				iw.msgFailure("请输入省会");
				return ;
			}
			if($("#district").val() == ''){
				iw.msgFailure("请输入区");
				return ;
			}
		}
		
		if($("#type").val() == 1){
			if($("#longitude").val() == ''){
				iw.msgFailure("请输入经度");
				return ;
			}
			if($("#latitude").val() == ''){
				iw.msgFailure("请输入纬度");
				return ;
			}
		}
		//表单序列化
		parent.iw.loading("保存中");
		$.post("/shop/store/index/save.do", d, function (result) {
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