<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="编辑"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>GoodsType_typeid.js"></script>
<script src="/<%=Global.CACHE_FILE %>GoodsType_putaway.js"></script>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" value="${item.id }" name="id" >
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">标题 <i class="layui-icon" style="color: red;size:">*</i> </label>
		<div class="layui-input-block">
			<input type="text" name="title" id="title" class="layui-input" value="${item.title }"  >
		</div>
	</div>
	
	<!-- 标题图片、封面图片。若是使用，可以在 栏目管理 中，编辑栏目时，有个 信息录入的选项卡，找到 标题图片，点击 使用 即可。若是自己添加的输入模型，请保留 id="sitecolumn_editUseTitlepic" ,不然栏目设置中的是否使用图集功能将会失效！ -->
	<div class="layui-form-item" id="icon_div">
		<label class="layui-form-label" id="label_columnName">缩略图</label>
		<div class="layui-input-block">
			<input name="titlepic" id="titlePicInput" type="text" autocomplete="off" placeholder="点击右侧添加" class="layui-input" value="${item.titlepic }" style="padding-right: 120px;">
			<button type="button" class="layui-btn" id="uploadImagesButton1" style="float: right;margin-top: -38px;">
				<i class="layui-icon layui-icon-upload"></i>
			</button>
			<a href="${item.titlepic  }" id="titlePicA" style="float: right;margin-top: -38px;margin-right: 60px;" title="预览原始图片" target="_black">
				<img id="titlePicImg" src="${item.titlepic  }?x-oss-process=image/resize,h_38" onerror="this.style.display='none';" style="height: 36px;max-width: 57px; padding-top: 1px;" alt="预览原始图片">
			</a>
			<input class="layui-upload-file" type="file" name="fileName">
		</div>
	</div>
	
	${text}
<script type="text/javascript" src="/js/admin/cms/news_extend_photos.js"></script>
	
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">库存数量<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="inventory" id="inventory" class="layui-input" value="${item.inventory }" >
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">警告数量<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="alarmNum" id="alarmNum" class="layui-input" value="${item.alarmNum }" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		库存量低于这个数，会通知商家告警，提醒商家该加库存了</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">商品分类<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<script type="text/javascript">writeSelectAllOptionFortypeid_('${item.typeid }','请选择分类', true);</script>
			
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">上下架<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<script type="text/javascript">writeSelectAllOptionForputaway_('${item.putaway }','请选择上下架', true);</script>
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		设置下架用户将看不到</div>
	</div>
	
	<%-- <div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">定时上架</label>
		<div class="layui-input-block">
			<input type="text" name="online_countdown" id="onlineCountdown" class="layui-input time" <c:if test = "${item.onlineCountdown != null && item.onlineCountdown != 0}">value='<x:time linuxTime="${item.onlineCountdown }" format="yyyy-MM-dd HH:mm:ss"></x:time>'</c:if> >
		</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">定时下架</label>
		<div class="layui-input-block">
			<input type="text" name="soldout_countdown" id="soldoutCountdown" class="layui-input time" <c:if test = "${item.soldoutCountdown != null && item.soldoutCountdown != 0}">value='<x:time linuxTime="${item.soldoutCountdown }" format="yyyy-MM-dd HH:mm:ss"></x:time>'</c:if> >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		时间到了，商品下架用户将看不到</div>
	</div>
	 --%>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode" >假售数量<span style="color: red">*</span></label>
		<div class="layui-input-block">
			<input type="number" name="fakeSale" id="fakeSale" class="layui-input" value="${item.fakeSale }" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		用户看到的销量=真是销量+虚拟售数量</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">计量单位<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="text" name="units" id="units" class="layui-input" value="${item.units }" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		计量，单位。如个、斤、条等。</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">价格<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="price_" id="price" class="layui-input" value="${item.price/100}" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		单位：元,只显示两位小数</div>
	</div>
	
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">原价<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="original_price" id="originalPrice" class="layui-input" value="${item.originalPrice/100 }" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
		单位：元，只显示两位小数，在用户页面多一条斜线</div>
	</div>
	
	
	<!-- 内容编辑方式，当独立页面时才会有效，才会显示。选择是使用内容富文本编辑框编辑，还是使用模板的方式编辑 -->
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">商品描述:</label>
		<div class="layui-input-block">
			<textarea class="layui-input" id="myEditor" name="detail" style="height: auto; padding-left: 0px; border: 0px;">${data.detail}</textarea>
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
		,url: '/shop/storeadmin/common/uploadImage.do' //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			loadClose();
			if(res.result == 1){
				try{
					document.getElementById("titlePicInput").value = res.url;
					document.getElementById("titlePicA").href = res.url;
					document.getElementById("titlePicImg").src = res.url;
					document.getElementById("titlePicImg").style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				}catch(err){}
				parent.iw.msgSuccess("上传成功");
			}else{
				parent.iw.msgFailure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.iw.loadClose();
			parent.iw.msgFailure();
		}
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.iw.loading('上传中..');
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
		var d = $("form").serialize();
		console.log("------");
		console.log(d);
		
		 if($("#title").val() == ''){
			iw.msgFailure("请输入标题");
			return ;
		} 
		if($("#inventory").val() == ''){
			iw.msgFailure("请输入库存数量");
			return ;
		}
		if($("#alarmNum").val() == ''){
			iw.msgFailure("请输入告警数量");
			return ;
		}
		if($("#putaway").val() == ''){
			iw.msgFailure("请选择上下架状态");
			return ;
		}
		if($("#putaway").val() == ''){
			iw.msgFailure("请选择状态");
			return ;
		}
		if($("#units").val() == ''){
			iw.msgFailure("请输入计量单位");
			return ;
		}
		if($("#fakeSale").val() == ''){
			iw.msgFailure("请输入假的已售数量");
			return ;
		}
		if($("#price").val() == ''){
			iw.msgFailure("请输入价格");
			return ;
		}
		if($("#originalPrice").val() == ''){
			iw.msgFailure("请输入原价");
			return ;
		}
		if($("#userBuyRestrict").val() == ''){
			iw.msgFailure("请输入购买限制");
			return ;
		}
		var price = $("#price").val();
		var originalPrice = $("#originalPrice").val();
		d = d + "&price=" + parseInt(price*100);
		d = d + "&originalPrice=" + parseInt(originalPrice*100);
		//表单序列化
		parent.iw.loading("保存中");
		$.post("/shop/storeadmin/goods/save.do", d, function (result) {
			parent.iw.loadClose();
			var obj = JSON.parse(result);
			if(obj.result == '1'){
				parent.parent.iw.msgSuccess("操作成功");
				window.location.href = '/shop/storeadmin/goods/list.do';
			}else if(obj.result == '0'){
				parent.iw.msgFailure(obj.info);
			}else{
				parent.iw.msgFailure("修改失败");
			}
		}, "text");
		
		return false;
	}
	
var uploadExtendPhotos = {
		elem: '.uploadImagesButton' //绑定元素
		,url: '/shop/storeadmin/common/uploadImage.do'  //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			loadClose();
			
			var key = this.item[0].name;	//拿到传递参数的key，也就是 extend.photos 中，数组某项的下表
			
			if(res.result == 1){
				try{
					document.getElementById("titlePicInput"+key).value = res.url;
					document.getElementById("titlePicA"+key).href = res.url;
					document.getElementById("titlePicImg"+key).src = res.url;
					document.getElementById("titlePicImg"+key).style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				}catch(err){}
				parent.iw.msgSuccess("上传成功");
			}else{
				parent.iw.msgFailure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.iw.loadClose();
			parent.iw.msgFailure();
		}
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.iw.loading('上传中..');
		}
	};
	
	
			
var upload;
layui.use('upload', function(){
	upload = layui.upload;
	//上传图片,封面图
	//upload.render(uploadPic);
	upload.render({
		elem: "#uploadImagesButton" //绑定元素
		,url: '/shop/storeadmin/common/uploadImage.do'  //上传接口
		,field: 'image'
		,accept: 'file'
		,done: function(res){
			//上传完毕回调
			loadClose();
			if(res.result == 1){
				try{
					document.getElementById("titlePicInput").value = res.url;
					document.getElementById("titlePicA").href = res.url;
					document.getElementById("titlePicImg").src = res.url;
					document.getElementById("titlePicImg").style.display='';	//避免新增加的文章，其titlepicImg是隐藏的
				}catch(err){}
				parent.iw.msgSuccess("上传成功");
			}else{
				parent.iw.msgFailure(res.info);
			}
		}
		,error: function(index, upload){
			//请求异常回调
			parent.iw.loadClose();
			parent.iw.msgFailure();
		}
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			parent.iw.loading('上传中..');
		}
	});
	
	//上传图片,图集，v4.6扩展
	upload.render(uploadExtendPhotos);
});

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>