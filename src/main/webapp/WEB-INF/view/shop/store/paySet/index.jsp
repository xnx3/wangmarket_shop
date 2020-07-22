<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="支付设置首页"/>
</jsp:include>

<style>
body{padding-left: 30px;}
.title{font-size: 16px;font-weight: bold; padding-top:3px;}
#Ali,#WX{
	padding-left:20px;
}
</style>

<div style="height: 30px"></div>
<!-- 线下支付 -->

<div>
	<span class="title">是否开启线下支付的方式？</span>
	当前已
  	<c:if test="${paySet.usePrivatePay == 0}">关闭
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('private',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>	
  	</c:if>
  	<c:if test="${paySet.usePrivatePay == 1}">开启
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('private',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>	
	</c:if>
	<div style="color: gray;font-size: 12px">
	<p>说明：开启后，当用户下单进行支付时,可以不必在线支付,即可创建有效</p>
	<p>订单。您可以联系客户将钱私下发红包给您、或者您给可以将货送到了，</p>
	<p>再让客户付钱给您。</p>
	</div>
</div>
</br></br>
<!-- 支付宝支付 -->
<div>
	<span class="title">是否开启支付宝支付的方式？</span>
	当前已
  	<c:if test="${paySet.useAlipay == 0}">关闭
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('alipay',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>	
  	</c:if>
  	<c:if test="${paySet.useAlipay == 1}">开启
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('alipay',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>	
	</c:if>
	<div style="color: gray ;font-size: 12px;font-size: 12px">
	<p>说明：开启后，当用户下单进行支付时，可以通过支付宝进行在线支付</p>
	</div>
	</br>
	
	<c:if test="${paySet.useAlipay == 0}">
	<div id="Ali" style="display: none;">
  	</c:if>
  	<c:if test="${paySet.useAlipay == 1}">
	<div id="Ali" style="display: block;">
	</c:if>
		<span style="color: gray">RSA2密钥;PKCS8格式</span>
		</br>APP应用的id: &nbsp;&nbsp; ${paySet.alipayAppId }
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('alipayAppId','${paySet.alipayAppId}','APP应用的id编辑')"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		
		</br>APP应用的私钥:&nbsp;&nbsp;<x:substring maxLength="10" text="${paySet.alipayAppPrivateKey }" more="******"></x:substring>
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('alipayAppPrivateKey','','APP应用的私钥编辑')"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		
		</br>支付宝公钥证书路径:&nbsp;&nbsp;<x:substring maxLength="10" text="${paySet.alipayCertPublicKeyRSA2 }"></x:substring>
		 <a class="layui-btn layui-btn-xs aa" lay-data = "{url: '/shop/store/paySet/uploadCrt.do?name=alipayCertPublicKeyRSA2'}" style="margin-top: 3px;">编辑</a>	 
		
		</br>支付宝的根证书:&nbsp;&nbsp;${paySet.alipayRootCert }
		 <a class="layui-btn layui-btn-xs aa" lay-data = "{url: '/shop/store/paySet/uploadCrt.do?name=alipayRootCert'}" style="margin-top: 3px;">编辑</a>	 
		
		</br>支付宝的应用公钥证书:&nbsp;&nbsp;${paySet.alipayAppCertPublicKey }
		 <a class="layui-btn layui-btn-xs aa" lay-data = "{url: '/shop/store/paySet/uploadCrt.do?name=alipayAppCertPublicKey2'}" style="margin-top: 3px;">编辑</a>	 
		
	</div>
</div>
</br>
</br>
<!-- 微信支付 -->
<div>
	<span class="title">是否开启微信支付的方式？</span>
	当前已
  	<c:if test="${paySet.useWeixinPay == 0}">关闭
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('weixinPay',1);" style="margin-left: 3px;margin-top:-1px;">开启</botton>	
  	</c:if>
  	<c:if test="${paySet.useWeixinPay == 1}">开启
  		 <botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('weixinPay',0);" style="margin-left: 3px;margin-top:-1px;">关闭</botton>
	</c:if>
	<div style="color: gray;font-size: 12px">
		<p>说明：开启后，当用户下单进行支付时，可以通过微信进行在线支付</p>
	</div>
	
	<c:if test="${paySet.useWeixinPay == 0}">
	<div id="WX" style="display: none;">
  	</c:if>
  	<c:if test="${paySet.useWeixinPay == 1}">
	<div id="WX" style="display: block;">
	</c:if>

	
	<span class="title">是否已进行了微信公众号的300元认证? </span>
	当前使用 
	<c:if test="${paySet.useWeixinServiceProviderPay == 0}">
		已花钱认证
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('weixinServiceProviderPay',1);" style="margin-left: 3px;margin-top:-1px;">点击切换使用免认证费的</botton>
		<div style="color: gray;font-size: 12px">
			<p>
				说明：要使用微信支付，要有一个已认证的微信公众号-服务号。这个公众号的认证需要每年花费300元。
			</p>
		</div>
	</c:if>
	<c:if test="${paySet.useWeixinServiceProviderPay == 1}">
		未花钱认证
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="updateState('weixinServiceProviderPay',0);" style="margin-left: 3px;margin-top:-1px;">点击切换使用300元认证的</botton>
		<div style="color: gray;font-size: 12px">
			<p>
				我方与微信合作，可以让你节省这每年300元的费用。不过建议还是使用每年300认证那种
			</p>
		</div>
	</c:if>
	
	<!-- 是否使用我们作为服务商 -->
	<c:if test="${paySet.useWeixinServiceProviderPay == 0}">
		</br>微信公众号 AppId:&nbsp;&nbsp;${paySet.weixinOfficialAccountsAppid }
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinOfficialAccountsAppid','${paySet.weixinOfficialAccountsAppid}','微信公众号 AppId');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		</br>微信公众号 AppSecret:&nbsp;&nbsp; <x:substring maxLength="10" text="${paySet.weixinOfficialAccountsAppSecret }" more="******"></x:substring> 
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinOfficialAccountsAppSecret','','微信公众号 AppSecret');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		<!--  
			</br>微信公众号 token:&nbsp;&nbsp;${paySet.weixinOfficialAccountsToken }
			<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinOfficialAccountsToken','${paySet.weixinOfficialAccountsToken}','微信公众号 token（约定好的固定的token）');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		-->
		</br>商户号:${paySet.weixinMchId }
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinMchId','${paySet.weixinMchId}','商户号编辑');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		
		</br>商户key:&nbsp;&nbsp;<x:substring maxLength="10" text="${paySet.weixinMchKey }" more="******"></x:substring>
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinMchKey','','商户key编辑');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="tishiweixinMchKey();" style="margin-left: 3px;margin-top:-1px;">如何获取商户key方法</botton>
	</c:if>
	<c:if test="${paySet.useWeixinServiceProviderPay == 1}">
		</br>商户号:&nbsp;&nbsp;<x:substring maxLength="10" text="${paySet.weixinSerivceProviderSubMchId }"></x:substring>
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinSerivceProviderSubMchId','${paySet.weixinSerivceProviderSubMchId}','商自己的商户号');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		此处请联系您的上级服务人员开通
	</c:if>
	
	
	</br>
	</br>
	</br>
	<span class="title">微信小程序参数设置（如果您使用微信小程序，需要设置以下参数）</span>
		<!-- 如果您使用的是微信 -->
		</br>微信小程序appid:&nbsp;&nbsp;${paySet.weixinAppletAppid }
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinAppletAppid','${paySet.weixinAppletAppid}','商微信小程序appid编辑');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		</br>微信小程序appsecret:&nbsp;&nbsp; <x:substring maxLength="10" text="${paySet.weixinAppletAppSecret }" more="******"></x:substring> 
		<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="addOrUpdate('weixinAppletAppSecret','','商微信小程序appsecret编辑');" style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		
	</div>
</div>

<script type="text/javascript">
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

function updateState(name1,value1) {
	parent.msg.loading("更改中");    //显示“操作中”的等待提示
	$.post('/shop/store/paySet/update.do?name=' + name1 + '&value=' + value1 , function(data){
	    parent.msg.close();    //关闭“操作中”的等待提示
	    if(data.result == '1'){
	        parent.msg.success('操作成功');
	        window.location.reload();	//刷新当前页
	     }else if(data.result == '0'){
	         parent.msg.failure(data.info);
	     }else{
	         parent.msg.failure();
	     }
	});
}

//id,商家id，value当前值，name修改那个属性，text编辑标题内容
function addOrUpdate(name1,value1,text){
	layer.prompt({
		  formType: 2,
		  value: value1,
		  title: text,
		  area: ['300px', '100px'] //自定义文本域宽高
		}, function(value, index, elem){
		  parent.msg.loading("操作中"); 
		  $.post('/shop/store/paySet/update.do?name=' + name1 + '&value=' + value , function(data){
			    parent.msg.close();    //关闭“操作中”的等待提示
			    if(data.result == '1'){
			        parent.msg.success('操作成功');
			        window.location.reload();	//刷新当前页
			     }else if(data.result == '0'){
			         parent.msg.failure(data.info);
			     }else{
			         parent.msg.failure();
			     }
			});
		});
}

function tishiweixinMchKey() {
	layer.open({
		  title: '获取商户key'
		  ,content: '在微信商户平台-帐户中心-左侧API安全-API密钥-点击设置API密钥这个里面设置的KEY，自己设置，不是自动生成'
		});     
		  
}

layui.use('upload', function(){
	var upload = layui.upload;
	
	//执行实例
	var uploadInst = upload.render({
	  elem: '.aa' //绑定元素
	 ,field : 'file'
	 ,exts:'crt'
	 ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		 msg.loading('上传中...');
		}
	,done: function(res){
		iw.loadClose();
		if(res.result == '1'){
			parent.msg.success("上传成功");
			parent.layer.close(index);	//关闭当前窗口
			location.reload();	//刷新父窗口列表
		}else if(res.result == '0'){
			parent.msg.failure(res.info);
		}else{
			parent.msg.failure("上传失败");
		}
	}
	,error: function(){
		     
	}
	});
});
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  