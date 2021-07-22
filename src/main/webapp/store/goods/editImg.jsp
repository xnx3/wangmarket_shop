<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="id" value="编辑图片"/>
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
			数字越小越靠前
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">列表图片</label>
		<div class="layui-input-block">
			<jsp:include page="/wm/common/edit/form_uploadImage.jsp">
				<jsp:param name="wm_name" value="imageUrl"/>
				<jsp:param name="wm_value" value="${imageUrl }"/>
				<jsp:param name="wm_api_url" value="/shop/store/api/common/uploadImage.json"/>
			</jsp:include>
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

function commit() {
	var d = $("form").serialize();
	//表单序列化
	parent.msg.loading("保存中");
	post("/shop/store/api/goods/goodsImageSave.json?"+d, {}, function (result) {
		parent.msg.close();
		checkLogin(result);	//验证登录状态。如果未登录，那么跳转到登录页面
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
//商品id
var goodsId = getUrlParams('goodsId');
var id = getUrlParams('id');
//获取轮播图列表信息
msg.loading('加载中');
post('/shop/store/api/goods/getGoodsImage.json?id=' + id +'&goodId=' + goodsId ,{},function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面
	if(data.result != '1'){
		document.getElementById('goodsid').value = goodsId;
	}else{
		//登录成功
		obj = data.goodsImage;
		document.getElementById('rank').value = obj.rank;
		document.getElementById('imgId').value = obj.id;
		document.getElementById('goodsid').value = obj.goodsid;
		//将接口获取到的数据自动填充到 form 表单中
		wm.fillFormValues($('form'), data.goodsImage);
	}
});
</script>
<jsp:include page="../common/foot.jsp"></jsp:include>