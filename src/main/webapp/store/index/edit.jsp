<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="商品分类"/>
</jsp:include>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-left: -60px; margin-bottom: 10px;">
	<div id="region"  style="display: none">
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">省份</label>
			<div class="layui-input-block">
				<input type="text" name="province" id="province" class="layui-input" value="" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
				比如:山东省
			</div>
		</div>
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">市</label>
			<div class="layui-input-block">
				<input type="text" name="city" id="city" class="layui-input" value="" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
				比如:潍坊市
			</div>
		</div>
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">区</label>
			<div class="layui-input-block">
				<input type="text" name="district" id="district" class="layui-input" value="" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
				比如:寒亭区
			</div>
		</div>
	</div>
	<div id="position" style="display: none">
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">经度</label>
			<div class="layui-input-block">
				<input type="text" name="longitude" id="longitude" class="layui-input" value="" >
			</div>
		</div>
		<div class="layui-form-item" id="xnx3_editMode">
			<label class="layui-form-label" id="columnEditMode">纬度</label>
			<div class="layui-input-block">
				<input type="text" name="latitude" id="latitude" class="layui-input" value="" >
			</div>
			<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
				<a target="_black" href="https://lbs.qq.com/tool/getpoint/"><u>获取经纬度</u></a>
			</div>
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
		if($("#type").val() == 2){
			if($("#city").val() == ''){
				msg.failure("请输入城市");
				return ;
			}
			if($("#province").val() == ''){
				msg.failure("请输入省会");
				return ;
			}
			if($("#district").val() == ''){
				msg.failure("请输入区");
				return ;
			}
		}
		if($("#type").val() == 1){
			if($("#longitude").val() == ''){
				msg.failure("请输入经度");
				return ;
			}
			if($("#latitude").val() == ''){
				msg.failure("请输入纬度");
				return ;
			}
		}
//表单序列化
parent.msg.loading("保存中");
post("/shop/store/api/store/save.json?"+d, {}, function (result) {
parent.msg.close();
checkLogin(result);	//验证登录状态。如果未登录，那么跳转到登录页面
	// var obj = JSON.parse(result);
	if(result.result == '1'){
		parent.parent.msg.success("操作成功");
		parent.layer.close(index);	//关闭当前窗口
		parent.location.reload();	//刷新父窗口列表
	}else if(result.result == '0'){
		parent.msg.failure(result.info);
	}else{
		parent.msg.failure("修改失败");
		}
	}, "text");
		return false;
}
var type = getUrlParams('type');
var longitude = getUrlParams('longitude');
var latitude = getUrlParams('latitude');
var province = decodeURI(getUrlParams('province'));
var city = decodeURI(getUrlParams('city'));
var district = decodeURI(getUrlParams('district'));
	if(type == 1){
		document.getElementById("position").style.display="";
		document.getElementById("longitude").value=longitude;
		document.getElementById("latitude").value=latitude;
	}else if(type == 2){
		document.getElementById("region").style.display="";
		document.getElementById("province").value=province;
		document.getElementById("city").value=city;
		document.getElementById("district").value=district;
	}
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>