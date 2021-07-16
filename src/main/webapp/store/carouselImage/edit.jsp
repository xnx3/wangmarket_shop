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
			图片的名称
		</div>
	</div>
	<!-- 标题图片、封面图片。若是使用，可以在 栏目管理 中，编辑栏目时，有个 信息录入的选项卡，找到 标题图片，点击 使用 即可。若是自己添加的输入模型，请保留 id="sitecolumn_editUseTitlepic" ,不然栏目设置中的是否使用图集功能将会失效！ -->
	<div class="layui-form-item" id="icon_div">
		<label class="layui-form-label" id="label_columnName">图片</label>
		<div class="layui-input-block">
			<input name="imageUrl" id="titlePicInput" type="text" autocomplete="off" placeholder="点击右侧添加" class="layui-input"  style="padding-right: 120px;">
			<button type="button" class="layui-btn" id="uploadImagesButton" style="float: right;margin-top: -38px;">
				<i class="layui-icon layui-icon-upload"></i>
			</button>
			<a id="titlePicA" style="float: right;margin-top: -38px;margin-right: 60px;" title="预览原始图片" target="_black">
				<img id="titlePicImg"  onerror="this.style.display='none';" style="height: 36px;max-width: 57px; padding-top: 1px;" alt="预览原始图片">
			</a>
			<input class="layui-upload-file" type="file" name="fileName">
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

layui.use('upload', function(){
	var upload = layui.upload;
	//上传图片,封面图
	//upload.render(uploadPic);
	upload.render({
		elem: "#uploadImagesButton" //绑定元素
		,url: '/shop/store/api/common/uploadImage.json?token='+shop.getToken() //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			msg.close();
			//loadClose();
			checkLogin(res);	//验证登录状态。如果未登录，那么跳转到登录页面
			if(res.result == 1){
				try{
					document.getElementById("titlePicInput").value = res.url;
					document.getElementById("titlePicA").href = res.url;
					document.getElementById("titlePicImg").src = res.url;
					document.getElementById("titlePicImg").style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				}catch(err){}
				parent.msg.success("上传成功");
			}else{
				parent.msg.failure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.msg.close();
			parent.msg.failure();
		}
		,before: function(obj){	//obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.msg.loading('上传中..');
		}
	});
	//上传图片,图集，v4.6扩展
	//upload.render(uploadExtendPhotos);
});
/* if($("#typeValue").val() == ''){
	msg.failure("请输入排序");
	return ;
} */
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
	post("/shop/store/api/carouselImage/save.json?"+d,{}, function (date) {
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
		document.getElementById('titlePicImg').style.display='none';
		writeSelectAllOptionFortype_('','请选择类型', true);
	}else {
		//登录成功
		obj = data.carouselImage;
		document.getElementById('id').value = obj.id;
		document.getElementById('name').value = obj.name;
		document.getElementById('titlePicImg').src = obj.imageUrl + '?x-oss-process=image/resize,h_38'
		document.getElementById('titlePicA').href = obj.imageUrl;
		document.getElementById('titlePicInput').value = obj.imageUrl;
		document.getElementById('imgValue').value = obj.imgValue;
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
	}
});
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>