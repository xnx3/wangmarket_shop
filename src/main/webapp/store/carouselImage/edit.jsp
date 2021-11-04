<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="轮播图列表"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>CarouselImage_type.js"></script>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" id="id" name="id" >
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">标题</label>
		<div class="layui-input-block">
			<input type="text" name="name" id="name" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			轮播图的名称
		</div>
	</div>
	<div class= "layui-form-item" >
		<label class="layui-form-label">图片</label>
		<div class="layui-input-block">
			<jsp:include page="/wm/common/edit/form_uploadImage.jsp">
				<jsp:param name="wm_name" value="imageUrl"/>
				<jsp:param name="wm_value" value="${carouselImage.imageUrl}"/>
				<jsp:param name="wm_api_url" value="/shop/store/api/common/uploadImage.json"/>
			</jsp:include>
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">类型</label>
		<div class="layui-input-block" id="carouselImageType" >
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="typeContext">跳转内容</label>
		<div class="layui-input-block">
			<input type="text" name="imgValue" id="imgValue" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			跳转的内容，url的路径如:http://baidu.com 商品的id如:1
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">排序</label>
		<div class="layui-input-block">
			<input type="number" name="rank" id="rank" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			数字越小越靠前
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
		msg.failure("请输入标题");
		return ;
	}
	if($("#rank").val() == ''){
		msg.failure("请输入排序");
		return ;
	}
	//表单序列化
	parent.msg.loading("保存中");
	post("/shop/store/api/carouselImage/save.json",wm.getJsonObjectByForm($("form")), function (date) {
		parent.msg.close();
		checkLogin(date);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(date.result == '1'){
			parent.parent.msg.success("操作成功");
			parent.layer.close(index);	//关闭当前窗口
			parent.location.reload();	//刷新父窗口列表
		}else if(date.result == '0'){
			parent.msg.failure(date.info);
		}else{
			parent.msg.failure("修改失败");
		}
	}, "text");
	return false;
}
layui.use(['form', 'element'], function(){
	var form = layui.form;
		//当类型发生变动改变
	form.on('select(type)', function (data) {
		if(document.getElementById("type").options[1].selected == true){
			document.getElementById("typeContext").innerText = '商品ID';
		}
		if(document.getElementById("type").options[2].selected == true){
			document.getElementById("typeContext").innerText = '分类ID';
		}
		if(document.getElementById("type").options[3].selected == true){
			document.getElementById("typeContext").innerText = '页面URL';
		}
	});
});
var type = new Array();
type['1'] = '打开一个商品';
type['3'] = '打开一个页面';
type['2'] = '打开某个分类';
/*页面上输出选择框的所有option，显示到页面上*/
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
	document.getElementById('carouselImageType').innerHTML = '<select name=type ' + (required ? 'required' : '') + ' lay-verify="type" lay-filter="type" id="type">' + content + '</select>';
	layui.use('form', function() {
		var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
		form.render();
	});
}
msg.loading('加载中');
var id = getUrlParams('id');
var obj;
post('/shop/store/api/carouselImage//getCarouselImage.json?id='+ id,{},function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.carouselImage == null){
		writeSelectAllOptionFortype_('','请选择类型', true);
	}else {
		//登录成功
		obj = data.carouselImage;
		document.getElementById('id').value = obj.id;
		document.getElementById('name').value = obj.name;
		document.getElementById('rank').value = obj.rank;
		document.getElementById('typeContext').innerHTML = '商品ID';
		if (obj.type == 1){
			document.getElementById('typeContext').innerHTML = '商品ID';

		}else if(obj.type == 2){
			document.getElementById('typeContext').innerHTML = '分类ID';

		}else if(obj.type == 3){
			document.getElementById('typeContext').innerHTML = '页面URL';
		}
		writeSelectAllOptionFortype_(obj.type,'请选择类型', true);
		//将接口获取到的数据自动填充到 form 表单中
		wm.fillFormValues($('form'), data.carouselImage);
	}
});
</script>
<jsp:include page="../common/foot.jsp"></jsp:include>