<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="/store/common/head.jsp">
	<jsp:param name="title" value="商品分类"/>
</jsp:include>
<form  class="layui-form" action="" style="padding:20px; padding-top:35px; margin-bottom: 10px;">
	<input type="hidden" name="id" value="${goodsType.id }">
	<div class="layui-form-item" id="xnx3_editMode">
		<label class="layui-form-label">标题</label>
		<div class="layui-input-block">
			<input type="text" name="title" class="layui-input" value="${goodsType.title }">
			<div class="explain">分类的名称</div>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">缩略图</label>
		<div class="layui-input-block">
			<jsp:include page="/wm/common/edit/form_uploadImage.jsp">
				<jsp:param name="wm_name" value="icon"/>
				<jsp:param name="wm_value" value="${goodsType.icon }"/>
				<jsp:param name="wm_api_url" value="/shop/store/api/common/uploadImage.json"/>
			</jsp:include>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">排序</label>
		<div class="layui-input-block">
			<input type="text" name="rank" class="layui-input" value="${goodsType.rank }">
			<div class="explain">排序规则：数字越小排列越靠前</div>
		</div>
	</div>
	<div class="layui-form-item" style="padding-top:15px;">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>
<script>
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

layui.use(['form', 'layedit', 'laydate'], function(){
	var form = layui.form;
	//监听提交
	form.on('submit(demo1)', function(data){
		parent.parent.msg.loading("保存中");
		var postDate = wm.getJsonObjectByForm($("form"));	//提交的表单数据
		wm.post('/shop/store/api/goodsType/save.json', postDate, function (obj) { 
			parent.parent.msg.close();
			checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
			if(obj.result == '1'){
				parent.parent.msg.success("操作成功")
				parent.layer.close(index);
				parent.location.reload();	//刷新父窗口
			}else if(obj.result == '0'){
				parent.parent.msg.failure(obj.info);
			}else{
				parent.parent.msg.failure(result);
			}
		});
		return false;
	});
});
//当前网址get传入的参数
var id = wm.getUrlParams('id');	
if(id != null && id.length > 0 && id != '0'){
	//get传入要修改id了，那就加载要修改的这项的数据
	msg.loading('加载中');
	wm.post('/shop/store/api/goodsType/getGoodsType.json?id='+id,{},function(data){
		msg.close();		//关闭“加载中”的等待提示
		checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
		if(data.result == '1'){
			//获取数据成功
			//将接口获取到的数据自动填充到 form 表单中
			wm.fillFormValues($('form'), data.goodsType);
		}else{
			//接口返回失败，弹出失败提示
			msg.failure(data.info);
		}
	});
}
</script>
<jsp:include page="/wm/common/foot.jsp"></jsp:include>