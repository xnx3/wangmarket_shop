<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../iw/common/head.jsp">
	<jsp:param name="title" value="设置"/>
</jsp:include>

<div id="shifouqiyong" class="layui-form" style="padding-top:10%; text-align:center;">
	<span id="qiyongtishi">是否启用推广领奖品功能</span> &nbsp;&nbsp;&nbsp;
	<input type="checkbox" id="switchInputId" name="isUse" value="1" lay-filter="isUse" lay-skin="switch" lay-text="开启|关闭" <c:if test="${award.isUse == 1}">checked</c:if>>

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
		<h2>第一步：注册CNZZ账号</h2>
		去 CNZZ 注册账号。注册网址：<br>
		<a href="https://passport.umeng.com/login?appId=cnzz" target="_black">https://passport.umeng.com/login?appId=cnzz</a><br>
	
		<h2>第二步：添加站点</h2>
		添加一个站点,也就是将你的网站填写上。点击下面链接添加添加站点<br>
		<a href="https://web.umeng.com/main.php?c=site&a=add" target="_black">https://web.umeng.com/main.php?c=site&a=add</a><br>
		
		<h2>第三步：取得CNZZ中的 站点ID</h2>
		访问下面的链接，获取到CNZZ 的 站点ID<br>
		<a href="https://web.umeng.com/main.php?c=site&a=add" target="_black">https://web.umeng.com/main.php?c=site&a=add</a><br>
		<img src="//cdn.weiunity.com/site/341/news/e99a5dd5bb6143fdbc20de78eb431ee0.png" style="width:500px;" /><br>
		如上图，将您获取到的 站点ID，填写上：<br>
		<div class="layui-form-item" style=" padding-top: 10px; margin-bottom: 0px; ">
		    <div class="layui-input-inline" style="width:300px;">
		      <input type="text" name="code" id="cnzz_siteid" placeholder="CNZZ 的 站点 ID" value="${cnzz.cnzzSiteid }"  class="layui-input" style="width:100%">
		    </div>
		    <button class="layui-btn layui-btn-primary" onclick="update_cnzz_siteid();">保存</button>
		    <div style=" font-size: 13px; color: gray; padding-top:15px;">
		    	填写如：1276100000
		    </div>
		</div>
		
		<h2>第四步：查看密码：无密码</h2>
		同样第三步，站点ID 下，有个 开启查看密码服务，选上：无需密码 ,如下图所示<br>
		<img src="//cdn.weiunity.com/site/341/news/c184bc2d8cd84471a2244549cdc29ecc.png" style="width:500px;" /><br>
		<br/>
		按照以上步骤下来，便配置完毕！您的网站已经有了CNZZ统计功能了。再点开 CNZZ访问统计 插件看看，就能看到统计界面了。
		<br/>
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
function update_cnzz_siteid(){
	var value = document.getElementById('cnzz_siteid').value;
	if(!value){
		parent.iw.msgFailure("请设置值");
		return;
	}

	parent.iw.loading("保存中...");
	$.post("updateCode.do?cnzzSiteid="+value, function (result) { 
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