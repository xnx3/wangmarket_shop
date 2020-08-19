<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="短信接口"/>
</jsp:include>

<style>
body{padding-left: 30px;}
.title{font-size: 16px;font-weight: bold; padding-top:3px;}
#Ali,#WX{
	padding-left:20px;
}
h3{
	padding-top:20px;
	padding-bottom:5px;
	font-weight: 500;
}
</style>

<div style="height: 30px"></div>

<div>
	<span class="title">是否开启短信功能？</span>
	当前已<span id="useSms"></span>

	<div style="color: gray;font-size: 12px">
		<p>说明：短信验证码的发送、短信通知等。开启短信接口，才可以对用户发送短信。
			<span style="padding-left:20px;"><a href="http://sms.leimingyun.com" style="text-decoration: underline; color: blue;" target="_black">短信开通及短信条数充值点此查看</a></span>
		</p>
	</div>
</div>

<div id="smsMessage">

	<h3>发送短信接口参数设置</h3>
	uid: &nbsp;&nbsp; {uid}
	<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="inputUpdate('uid',smsSet.uid,'uid')"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
		
	</br>密码:&nbsp;&nbsp;{password}
	<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="inputUpdate('password','','密码');"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
	
	<div>
		当前短信剩余条数：
		<span id="balance">获取中...</span>
		<span></span>	
	</div>
	<script>
		$.post('/shop/store/api/sms/getBalance.json',function(data){
			var info = data.info;
			if(data.result == 1){
				//成功获取到了条数
				var number = data.info;
				if(number < 100){
					info = number + '条(注意，短信剩余不多了，请尽快充值)';
				}else{
					info = number + '条';
				}
				info = info + '<span style="padding-left:20px;"><a href="http://sms.leimingyun.com" style="text-decoration: underline; color: blue;">立即充值</a></span>';
			}
			document.getElementById('balance').innerHTML = info;
		});
	</script>
	
	
	</br>
	<h3>发送频率控制</h3>
	
	某个ip一天最多能发多少条短信:&nbsp;&nbsp;{quotaDayIp}
	<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="inputUpdate('quotaDayIp',smsSet.quotaDayIp,'可发送的最大短信条数');"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
	
	</br>某个手机号一天最多能发多少条短信:&nbsp;&nbsp;{quotaDayPhone}
	<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="inputUpdate('quotaDayPhone',smsSet.quotaDayPhone,'可发送的最大短信条数');"  style="margin-left: 3px;margin-top:-1px;">编辑</botton>
	
</div>

<script type="text/javascript">
//自适应弹出层大小
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//更改    name 实体类中的字段名，   value：值
function update(name,value) {
	parent.msg.loading("更改中");    //显示“操作中”的等待提示
	var data = {"name":name,"value":value};
	$.post('/shop/store/api/sms/update.json', data,function(data){
	    parent.msg.close();    //关闭“操作中”的等待提示
	    if(data.result == '1'){
	        parent.msg.success('操作成功');
	        window.location.reload();	//刷新当前页
	     }else if(data.result == '0'){
	         parent.msg.failure(data.info);
	     }else{
	         parent.msg.failure('操作失败');
	     }
	});
}


//弹出输入框，让用户输入文本后在进行更改. name:实体类的name    value 默认值，    shuoming 显示出来的说明文字
function inputUpdate(name,value, shuoming){
	layer.prompt({
	  formType: 2,
	  value: value,
	  title: shuoming,
	  area: ['300px', '60px'] //自定义文本域宽高
	}, function(value, index, elem){
	  update(name, value);
	});
}

//列表的模版
var smsMessageTemplate = document.getElementById("smsMessage").innerHTML;
function smsMessageReplace(item){

	return smsMessageTemplate
			.replace(/\{uid\}/g, item.uid)
			.replace(/\{password\}/g, item.password)
			.replace(/\{quotaDayIp\}/g, item.quotaDayIp)
			.replace(/\{quotaDayPhone\}/g, item.quotaDayPhone)
			;
}

msg.loading('加载中');
var smsSet;

post('/shop/store/api/sms/index.json','',function(data){
	msg.close();    //关闭“更改中”的等待提示
	checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result == '1'){
		smsSet =  data.smsSet;

		//是否开启短信功能
		if(smsSet.useSms == 0){
		document.getElementById('useSms').innerHTML = '&nbsp关闭<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="update(\'useSms\',\'1\');" style="margin-left: 3px;margin-top:-1px;">开启</botton>';
		document.getElementById('smsMessage').style.display = 'none';
		}
		if(smsSet.useSms == 1) {
			document.getElementById('smsMessage').innerHTML = smsMessageReplace(smsSet);
			document.getElementById('useSms').innerHTML = '&nbsp开启<botton class="layui-btn layui-btn-xs layui-btn-primary" onclick="update(\'useSms\',\'0\');" style="margin-left: 3px;margin-top:-1px;">关闭</botton>';
			document.getElementById('smsMessage').style.display = 'block';
		}

	}
});
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>