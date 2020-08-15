<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>

<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="编辑图片"/>
</jsp:include>

<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" name="id" id="imgId">
	<input type="hidden" name="goodsid" id="goodsid">


	<div class="layui-form-item">
		<label class="layui-form-label" id="label_columnName">图片排序</label>
		<div class="layui-input-block">
			<input type="number" name="rank" id="rank" class="layui-input" value="" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			数字越小越靠前</div>
	</div>
	
		<!-- 标题图片、封面图片。若是使用，可以在 栏目管理 中，编辑栏目时，有个 信息录入的选项卡，找到 标题图片，点击 使用 即可。若是自己添加的输入模型，请保留 id="sitecolumn_editUseTitlepic" ,不然栏目设置中的是否使用图集功能将会失效！ -->
		<div class="layui-form-item" id="icon_div">
			<label class="layui-form-label" id="label_columnName">列表图片</label>
			<div class="layui-input-block">
				<input name="imageUrl" id="titlePicInput" type="text" autocomplete="off" placeholder="点击右侧添加" class="layui-input" style="padding-right: 120px;">
				<button type="button" class="layui-btn" id="uploadImagesButton" style="float: right;margin-top: -38px;">
					<i class="layui-icon layui-icon-upload"></i>
				</button>
				<a href="" id="titlePicA" style="float: right;margin-top: -38px;margin-right: 60px;" title="预览原始图片" target="_black">
					<img id="titlePicImg" style="height: 36px;max-width: 57px; padding-top: 1px;" alt="预览原始图片">
				</a>
				<input class="layui-upload-file" type="file" name="fileName">
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
			,url: '/shop/store/api/common/uploadImage.json' //上传接口
			,field: 'image'
			,accept: 'file'
			,done: function(res){
				//上传完毕回调
				//loadClose();
				msg.close();
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
			,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				parent.msg.loading('上传中..');
			}
		});
		
		//上传图片,图集，v4.6扩展
		//upload.render(uploadExtendPhotos);
	});

	
			
	function commit() {
		var d = $("form").serialize();
		//表单序列化
		parent.msg.loading("保存中");
		$.post("/shop/store/api/goods/goodsImageSave.json", d, function (result) {
			parent.msg.close();
			var obj = JSON.parse(result);
			if(obj.result == '1'){
				parent.parent.msg.success("操作成功");
				parent.layer.close(index);	//关闭当前窗口
				parent.location.reload();	//刷新父窗口列表
			}else if(obj.result == '0'){
				parent.msg.failure(obj.info);
			}else{
				parent.msg.failure("修改失败");
			}
		}, "text");
		
		return false;
	}
//商品id
var goodsId = getUrlParams('goodsId');
var id = getUrlParams('id');
//获取轮播图列表信息
msg.loading('加载中');
post('/shop/store/api/goods/getGoodsImage.json?id=' + id +'&goodId=' + goodsId ,"",function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result != '1'){
		document.getElementById('titlePicImg').style.display='none';
		document.getElementById('goodsid').value = goodsId;
	}else{
		//登录成功
		obj = data.goodsImage;
		document.getElementById('titlePicA').href = obj.imageUrl;
		document.getElementById('rank').value = obj.rank;
		document.getElementById('titlePicInput').value = obj.imageUrl;
		document.getElementById('titlePicImg').src = obj.imageUrl + '?x-oss-process=image/resize,h_38';
		document.getElementById('titlePicA').href = obj.imageUrl;
		document.getElementById('imgId').value = obj.id;
		document.getElementById('goodsid').value = obj.goodsid;
	}
});
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>