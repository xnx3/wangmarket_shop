<%@ page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="编辑"/>
</jsp:include>
<script>
msg.loading('加载中');

//同步加载js
wm.load.synchronizesLoadJs(shop.host+'shop/store/api/goodsType/getGoodsTypeJs.json?token='+shop.getToken());
</script>
<script src="/<%=Global.CACHE_FILE %>GoodsType_putaway.js"></script>
<style type="text/css" >
	.layui-btn-xs{
	height:30px;
	font-size:20px;
	}
</style>
<form id="form" class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" name="id" id="id" >
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">标题<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="text" name="title" id="title" class="layui-input"  >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			商品的名称，如：西瓜
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">列表图片</label>
		<div class="layui-input-block">
			<jsp:include page="/wm/common/edit/form_uploadImage.jsp">
				<jsp:param name="wm_name" value="titlepic"/>
				<jsp:param name="wm_value" value="${inputGoods.titlepic}"/>
				<jsp:param name="wm_api_url" value="/shop/store/api/common/uploadImage.json"/>
			</jsp:include>
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">库存数量<i class="layui-icon" style="color: red;size:">*</i></label>
		<div class="layui-input-block">
			<input type="number" name="inventory" id="inventory" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			剩余的数量，如：10
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
			单位：元,只显示两位小数
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">原价</label>
		<div class="layui-input-block">
			<input type="number" name="original_price" id="originalPrice" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			单位：元，只显示两位小数，在用户页面多一条斜线
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">上下架</label>
		<div class="layui-input-block" id="flag">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			设置下架用户将看不到，默认为上架
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">警告数量</label>
		<div class="layui-input-block">
			<input type="number" name="alarmNum" id="alarmNum" class="layui-input">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			库存量低于这个数，会通知商家告警，提醒商家该加库存了，默认为0
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode" >假售数量</label>
		<div class="layui-input-block">
			<input type="number" name="fakeSale" id="fakeSale" class="layui-input" >
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			用户看到的销量=真是销量+虚拟售数量，默认为0
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">计量单位</label>
		<div class="layui-input-block">
			<input type="text" name="units" id="units" class="layui-input">
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			计量，单位。如个、斤、条等。默认个
		</div>
	</div>
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label" id="columnEditMode">简介</label>
		<div class="layui-input-block">
			<textarea rows="2" cols="30" name="intro" id="intro" class="layui-textarea" style="height: auto;"maxlength="40"></textarea>
		</div>
		<div class="explain" style="font-size: 12px;color: gray;padding-top: 3px;padding-left: 110px;">
			商品的简介,最多40字
		</div>
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

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#soldoutCountdown'	//指定元素
		,type: 'datetime'
		,format: 'yyyy-MM-dd HH:mm:ss'
	});
});
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#onlineCountdown'	//指定元素
		,type: 'datetime'
		,format: 'yyyy-MM-dd HH:mm:ss'
	});
});
// 提交修改添加信息
function commit() {
	var date = wm.getJsonObjectByForm($('#form'));
	date.price = Math.round($("#price").val()*100);	//价格
	date.originalPrice = Math.round($("#originalPrice").val()*100);	//原价
	date.token = wm.token.get();	//token
	console.log(date);
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
	$.post("/shop/store/api/goods/saveAll.json",date,function(result){
	//post("shop/store/api/goods/saveAll.json", date, function (result) {
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
	});
	return false;
}
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
<!-- 实例化编辑器 -->
var ue;
function initUE(){
	document.getElementById("myEditor").innerHTML = goodsData.detail;
	var ueditorText = goodsData.detail;
	ue = UE.getEditor('myEditor',{
		autoHeightEnabled: true,
		autoFloatEnabled: true,
		initialFrameHeight:460  
	});
	//对编辑器的操作最好在编辑器ready之后再做
	ue.ready(function() {
		document.getElementById("myEditor").style.height='auto';
	});
} 
//加载要修改的商品信息
var id = getUrlParams('id');
var	goods;
var goodsData={'detail':''};
if(id.length > 0){
	//修改商品
	//获取商品信息
	msg.loading('加载中');
	post('/shop/store/api/goods/getGoods.json?id=' + id ,{},function(data){
		msg.close();    //关闭“更改中”的等待提示
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(data.goods == null){
			writeSelectAllOptionFortypeid_('','请选择分类', true);
			writeSelectAllOptionForputaway_('','请选择上下架', true);
			return;
		}else{
			//成功
			goods = data.goods;
			goodsData = data.goodsData;
			document.getElementById("id").value = goods.id;
			document.getElementById("title").value = goods.title;
			document.getElementById("price").value = goods.price/100;
			document.getElementById("originalPrice").value = goods.originalPrice/100;
			document.getElementById("inventory").value = goods.inventory;
			document.getElementById("alarmNum").value = goods.alarmNum;
			document.getElementById("fakeSale").value = goods.fakeSale;
			document.getElementById("units").value = goods.units;
			document.getElementById("intro").innerHTML = goods.intro;
			writeSelectAllOptionFortypeid_(goods.typeid,'请选择分类', true);
			writeSelectAllOptionForputaway_(goods.putaway,'请选择上下架', true);
			//将接口获取到的数据自动填充到 form 表单中
			wm.fillFormValues($('form'), data.goods);
		}
		initUE();
	});
}else{
	//添加商品
	writeSelectAllOptionFortypeid_('','请选择分类', true);
	writeSelectAllOptionForputaway_('','请选择上下架', true);
	initUE();
}

</script>
<jsp:include page="../common/foot.jsp"></jsp:include>