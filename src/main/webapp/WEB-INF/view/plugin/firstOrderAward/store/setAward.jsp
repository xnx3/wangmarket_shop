<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<div id="shifouqiyong" class="layui-form" style="padding-top:10%; text-align:center;">
	<span id="qiyongtishi">是否启用推广领奖品功能</span> &nbsp;&nbsp;&nbsp;
	<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭" <c:if test="${award.isUse == 1}">checked</c:if>>
	<div style="color:gray; padding:5px; text-align: left; padding-left: 20px;">
		用户A推荐给用户B，当用户B首单消费成功后，系统会自动给用户A会以0元的价格购买某件商品并成功下单。
		<br/>注意：
		<br/>1. 用户B的这个首单必须是支付金额大于0元的
		<br/>2. 商城前端本身要有推广功能
	</div>
	
	<style>
	.shiyongbuzhou{
		padding:20px; text-align: left; opacity: 0.9;
	}
	.shiyongbuzhou h2{
		padding-bottom:9px; padding-top:20px;
	}
	</style>
	<!-- 默认隐藏 -->
	<div class="shiyongbuzhou" id="kaiqitext" style="display:none;">
		<h2>第一步：通过左侧菜单，进入 商品管理</h2>
		也可以<a href="javascript:parent.loadUrl('/shop/store/goods/list.do');">点此快速进入</a><br>
		
		<h2>第二步：找到要赠送的商品，记下其编号</h2>
		比如，要将白菜这个商品作为奖品，也就是当用户A推荐给用户B，用户B首单消费成功后，用户A将以0元的价格获得白菜这个商品。如下图所示，如果将白菜作为奖品，那么你需要记下白菜的ID编号，如下图演示的是 15<br>
		<img src="/plugin/firstOrderAward/images/setaward_2.png" style="max-width: 700px;" />
		
		<h2>第三步：将上面取得的商品ID填入</h2>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
		    <div class="layui-input-inline" style="width:300px;">
		      <input type="number" name="goodsid" id="goodsid" placeholder="商品的编号" value="${award.goodsid }"  class="layui-input" style="width:100%">
		    </div>
		    <button class="layui-btn layui-btn-primary" onclick="update_goodsid();">保存</button>
		    <div style=" font-size: 13px; color: gray; padding-top:15px;">
		    	填写如：15
		    </div>
		</div>
		
	</div>
		
</div>


<script type="text/javascript">
layui.use('form', function(){
	var form = layui.form;
	
	form.on('switch(isUse)', function(data){
		useChange(data.elem.checked);
		updateUse(data.elem.checked? '1':'0');	//将改动同步到服务器，进行保存
		
	});
	
	//美化是否启用的开关控件
	$(".layui-form-switch").css("marginTop","-2px");
});

//是否使用的开关发生改变触发  use  true:开启使用状态
function useChange(use){
	if(use){
		//使用
		//$(".kefuSetInfo").css("opacity","1.0");
		document.getElementById('kaiqitext').style.display = '';
	}else{
		//不使用
		//$(".kefuSetInfo").css("opacity","0.3");
		
		document.getElementById('kaiqitext').style.display = 'none';
	}
}
useChange('${award.isUse}' == 1);

//修改当前是否使用
function updateUse(value){
	parent.iw.loading('修改中');
	$.post("updateIsUse.do?isUse="+value, function(data){
	    parent.iw.loadClose();    //关闭“操作中”的等待提示
	    if(data.result == '1'){
	        parent.iw.msgSuccess('操作成功');
	     }else if(data.result == '0'){
	         parent.iw.msgFailure(data.info);
	     }else{
	         parent.iw.msgFailure();
	     }
	});
}

//保存code
function update_goodsid(){
	var value = document.getElementById('goodsid').value;
	if(!value){
		parent.iw.msgFailure("请设置值");
		return;
	}

	parent.iw.loading("保存中...");
	$.post("updateGoodsid.do?goodsid="+value, function (result) { 
       	parent.iw.loadClose();
       	var obj = JSON.parse(result);
       	if(obj.result == '1'){
       		parent.iw.msgSuccess("设置成功");
       	}else if(obj.result == '0'){
       		parent.iw.msgFailure(obj.info);
       	}else{
       		parent.iw.msgFailure("出错");
       	}
	}, "text");
}

</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  