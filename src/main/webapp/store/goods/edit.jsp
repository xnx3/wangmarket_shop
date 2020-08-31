<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="编辑"/>
</jsp:include>
<script src="/shop/store/api/goodsType/getGoodsTypeJs.json"></script>
<script src="/<%=Global.CACHE_FILE %>GoodsType_putaway.js"></script>

<style type="text/css" >
	.layui-btn-xs{
	height:30px;
	font-size:20px;
	}
</style >
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" name="id" id="id" >
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">标题 <i class="layui-icon" style="color: red;size:">*</i> </label>
		<div class="layui-input-block">
			<input type="text" name="title" id="title" class="layui-input"  >
		</div>
	</div>
	
	<!-- 标题图片、封面图片。若是使用，可以在 栏目管理 中，编辑栏目时，有个 信息录入的选项卡，找到 标题图片，点击 使用 即可。若是自己添加的输入模型，请保留 id="sitecolumn_editUseTitlepic" ,不然栏目设置中的是否使用图集功能将会失效！ -->
	<div class="layui-form-item" id="icon_div">
		<label class="layui-form-label" id="label_columnName">缩略图</label>
		<div class="layui-input-block">
			<input name="titlepic" id="titlepic" type="text" autocomplete="off" placeholder="点击右侧添加" class="layui-input"  style="padding-right: 120px;">
			<button type="button" class="layui-btn" id="uploadImagesButton1" style="float: right;margin-top: -38px;">
				<i class="layui-icon layui-icon-upload"></i>
			</button>
			<a  id="titlePicA" style="float: right;margin-top: -38px;margin-right: 60px;" title="预览原始图片" target="_black">
				<img id="titlePicImg"  onerror="this.style.display='none';" style="height: 36px;max-width: 57px; padding-top: 1px;" alt="预览原始图片">
			</a>
			<input class="layui-upload-file" type="file" name="fileName">
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">库存数量<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="inventory" id="inventory" class="layui-input" >
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">商品分类</label>
		<div class="layui-input-block" id="goodsType">

		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">价格</label>
		<div class="layui-input-block">
			<input type="number" name="price_" id="price" class="layui-input">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		单位：元,只显示两位小数</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">原价</label>
		<div class="layui-input-block">
			<input type="number" name="original_price" id="originalPrice" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		单位：元，只显示两位小数，在用户页面多一条斜线</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">上下架</label>
		<div class="layui-input-block" id="flag">

		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		设置下架用户将看不到，默认为上架</div>
	</div>
	
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">警告数量</label>
		<div class="layui-input-block">
			<input type="number" name="alarmNum" id="alarmNum" class="layui-input">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		库存量低于这个数，会通知商家告警，提醒商家该加库存了，默认为0</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode" >假售数量</label>
		<div class="layui-input-block">
			<input type="number" name="fakeSale" id="fakeSale" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		用户看到的销量=真是销量+虚拟售数量，默认为0</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">计量单位</label>
		<div class="layui-input-block">
			<input type="text" name="units" id="units" class="layui-input">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		计量，单位。如个、斤、条等。默认个</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">简介</label>
		<div class="layui-input-block">
			<textarea rows="2" cols="30" name="intro" id="intro" class="layui-input" style="height: auto; padding-left: 0px; border: 1px solid;"
			maxlength="40"></textarea>
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		商品的简介,最多40字</div>
	</div>
	
	
	<!-- 内容编辑方式，当独立页面时才会有效，才会显示。选择是使用内容富文本编辑框编辑，还是使用模板的方式编辑 -->
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">商品描述:</label>
		<div class="layui-input-block">
			<textarea class="layui-input" id="myEditor" name="detail" style="height: auto; padding-left: 0px; border: 0px;"></textarea>
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
	

layui.use('upload', function(){
	var upload = layui.upload;
	//上传图片,封面图
	//upload.render(uploadPic);
	upload.render({
		elem: "#uploadImagesButton1" //绑定元素
		,url: '/shop/store/api/common/uploadImage.json' //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			parent.msg.close();
			checkLogin(res);	//验证登录状态。如果未登录，那么跳转到登录页面
			if(res.result == 1){
				try{
					document.getElementById("titlepic").value = res.url;
					document.getElementById("titlePicA").href = res.url;
					document.getElementById("titlePicImg").src = res.url;
					document.getElementById("titlePicImg").style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				}catch(err){
					console.log(err);
				}
				parent.msg.success("上传成功");
			}else{
				parent.msg.failure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.msg.close();
			parent.msg.failure('异常');
		}
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.msg.loading('上传中');
		}
	});
	
	//上传图片,图集，v4.6扩展
	//upload.render(uploadExtendPhotos);
});
	
layui.use('laydate', function(){
	  var laydate = layui.laydate;

	  //执行一个laydate实例
	  laydate.render({
		elem: '#soldoutCountdown' //指定元素
		,type: 'datetime'
		,format: 'yyyy-MM-dd HH:mm:ss'
	  });
	});

layui.use('laydate', function(){
	  var laydate = layui.laydate;

	  //执行一个laydate实例
	  laydate.render({
		elem: '#onlineCountdown' //指定元素
		,type: 'datetime'
		,format: 'yyyy-MM-dd HH:mm:ss'
	  });
	});

// 提交修改添加信息
function commit() {
	var date = {};
	date.title = $("#title").val();	//标题
	date.inventory = $("#inventory").val();	//库存
	date.price = Math.round($("#price").val()*100);	//价格
	date.originalPrice = Math.round($("#originalPrice").val()*100);	//原价
	date.intro = $("#intro").val()	//简介
	date.alarmNum = $("#alarmNum").val() //警告数量
	date.fakeSale = $("#fakeSale").val() //假售数量
	date.units = $("#units").val()	//单位
	date.typeid = $("#typeid").val() //分类
	date.titlepic = $("#titlepic").val() //图片url
	date.id = $("#id").val() //商品id
	date.detail = ue.getContent(); //商品详情

	if(date.title == ''){
		msg.failure("请输入标题");
		return ;
	}
	if(date.inventory == ''){
		msg.failure("请输入库存数量");
		return ;
	}
	if(date.price == ''){
		msg.failure("请输入价格");
		return ;
	}
	if(date.originalPrice == ''){
		msg.failure("请输入原价");
		return ;
	}
	if(date.intro.length > 40){
		msg.failure('简介限制40个字符之内');
		return;
	}

	//表单序列化
	parent.msg.loading('保存中');
	post("shop/store/api/goods/saveAll.json", date, function (result) {
		parent.msg.close();
		checkLogin(result);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(result.result == '1'){
			parent.msg.success("操作成功");
			window.location.href = '/store/goods/list.jsp';
		}else if(result.result == '0'){
			parent.msg.failure(result.info);
		}else{
			parent.msg.failure('修改失败');

		}
	}, "text");

	return false;
}

var uploadExtendPhotos = {
	elem: '.uploadImagesButton' //绑定元素
	,url: '/shop/store/api/common/uploadImage.json'  //上传接口
	,field: 'image'
	,accept: 'file'
	,done: function(res){
		//上传完毕回调
		parent.msg.close();
		checkLogin(res);	//验证登录状态。如果未登录，那么跳转到登录页面

		var key = this.item[0].name;	//拿到传递参数的key，也就是 extend.photos 中，数组某项的下表

		if(res.result == 1){
			try{
				document.getElementById("titlepic"+key).value = res.url;
				document.getElementById("titlePicA"+key).href = res.url;
				document.getElementById("titlePicImg"+key).src = res.url;
				document.getElementById("titlePicImg"+key).style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				parent.msg.success('上传成功');
			}catch(err){
				console.log(err);
				parent.msg.failure(err);
			}
		}else{
			parent.msg.failure(res.info);
		}
	}
	,error: function(index, upload){
		//请求异常回调
		parent.msg.close();
		parent.msg.failure('异常');
	}
	,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		parent.msg.loading('上传中');
	}
};
	
	
			
var upload;
layui.use('upload', function(){
	upload = layui.upload;
	//上传图片,封面图
	//upload.render(uploadPic);
	upload.render({
		elem: "#uploadImagesButton" //绑定元素
		,url: '/shop/store/api/common/uploadImage.json'  //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			//loadClose();
			msg.close();
			checkLogin(res);	//验证登录状态。如果未登录，那么跳转到登录页面
			if(res.result == 1){
				try{
					document.getElementById("titlepic").value = res.url;
					document.getElementById("titlePicA").href = res.url;
					document.getElementById("titlePicImg").src = res.url;
					document.getElementById("titlePicImg").style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
					parent.msg.success('上传成功');
				}catch(err){
					console.log(err);
					parent.msg.failure(err);
				}
			}else{
				parent.msg.failure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.msg.close();
			parent.msg.failure('异常');
		}
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.msg.loading('上传中');
		}
	});
	
	//上传图片,图集，v4.6扩展
	upload.render(uploadExtendPhotos);
});

function writeSelectAllOptionFortypeid_(selectValue, firstTitle, required) {
	var content = "";
	if (selectValue == '') {
		content = content + '<option value="" selected="selected">' + firstTitle + '</option>';
	} else {
		content = content + '<option value="">' + firstTitle + '</option>';
	}
	for (var p in typeid) {
		if (p == selectValue) {
			content = content + '<option value="' + p + '" selected="selected">' + typeid[p] + '</option>';
		} else {
			content = content + '<option value="' + p + '">' + typeid[p] + '</option>';
		}
	}
	document.getElementById('goodsType').innerHTML='<select name=typeid ' + (required ? 'required': '') + ' lay-verify="typeid" lay-filter="typeid" id="typeid">' + content + '</select>';

	layui.use('form', function() {
		var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
		form.render();
	});
}
var putaway = new Array();
putaway['0'] = '已下架';
putaway['1'] = '上架中';
	/*页面上输出选择框的所有option，显示到页面上*/
function writeSelectAllOptionForputaway_(selectValue, firstTitle, required) {
	var content = "";
	if (selectValue == '') {
		content = content + '<option value="" selected="selected">' + firstTitle + '</option>';
	} else {
		content = content + '<option value="">' + firstTitle + '</option>';
	}
	for (var p in putaway) {
		if (p == selectValue) {
			content = content + '<option value="' + p + '" selected="selected">' + putaway[p] + '</option>';
		} else {
			content = content + '<option value="' + p + '">' + putaway[p] + '</option>';
		}
	}
	document.getElementById('flag').innerHTML ='<select name=putaway ' + (required ? 'required': '') + ' lay-verify="putaway" lay-filter="putaway" id="putaway">' + content + '</select>';
	layui.use('form', function() {
		var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
		form.render();
	});
}
var id = getUrlParams('id');
var	goods;
//获取商品信息
msg.loading('加载中');
post('/shop/store/api/goods/getGoods.json?id=' + id ,"",function(data){
msg.close();    //关闭“更改中”的等待提示
checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.goods == null){
		document.getElementById('titlePicImg').style.display='none';
		writeSelectAllOptionFortypeid_('','请选择分类', true);
		writeSelectAllOptionForputaway_('','请选择上下架', true);
	return;
	}else{
		//成功
		goods = data.goods;
		var goodsData = data.goodsData;
		document.getElementById("id").value = goods.id;
		document.getElementById("title").value = goods.title;
		document.getElementById('titlePicImg').src = goods.titlepic + '?x-oss-process=image/resize,h_38';
		document.getElementById('titlePicA').href = goods.titlepic;
		document.getElementById("titlepic").value = goods.titlepic;
		document.getElementById("price").value = goods.price/100;
		document.getElementById("originalPrice").value = goods.originalPrice/100;
		document.getElementById("inventory").value = goods.inventory;
		document.getElementById("alarmNum").value = goods.alarmNum;
		document.getElementById("fakeSale").value = goods.fakeSale;
		document.getElementById("units").value = goods.units;
		document.getElementById("intro").innerHTML = goods.intro;
		document.getElementById("myEditor").innerHTML = goodsData.detail;
		writeSelectAllOptionFortypeid_(goods.typeid,'请选择分类', true);
		writeSelectAllOptionForputaway_(goods.putaway,'请选择上下架', true);
	}
});



</script>

<jsp:include page="../common/foot.jsp"></jsp:include>