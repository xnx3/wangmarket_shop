<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="登录"/>
</jsp:include>

<style>
.myForm{
	margin: 0 auto;
    width: 495px;
    height: 360px;
    border-width: 1px 1px 1px 1px;
    padding: 0px;
    border-radius: 20px;
    overflow: hidden;
    -webkit-box-shadow: 0 0 10px #e2e2e2;
    -moz-box-shadow: 0 0 10px #e2e2e2;
    box-shadow: 0 0 10px #e2e2e2;
    position: absolute;
    left: 50%;
    top: 50%;
    margin-left: -248px;
    margin-top: -181px;
}

@media screen and (max-width:600px){
	.myForm{
		margin: 0 auto;
	    width: 100%;
	    height: 100%;
	    border-width: 0px;
	    padding: 0px;
	    border-radius: 0px;
	    overflow: auto;
	    -webkit-box-shadow: 0 0 0px #e2e2e2;
	    -moz-box-shadow: 0 0 0px #e2e2e2;
	    box-shadow: 0 0 0px #e2e2e2;
	    position: static;
	    left: 0px;
	    top: 0px;
	    margin-left: 0px;
	    margin-top: 0px;
	}
}

.touming{
	background: rgba(0,190,150,0.1) none repeat scroll !important;
}
.baisetouming{
	background: rgba(250,250,250,0.1) none repeat scroll !important;
}
</style>

<!-- 背景 -->

<form class="layui-form layui-elem-quote layui-quote-nm myForm">
  <div class="layui-form-item touming" style="height: 70px;background-color: #eeeeee;line-height: 70px;text-align: center;font-size: 25px;color: #3F4056;">
    云商城 平台登陆
  </div>
  <div style="padding: 30px 50px 40px 0px;">
  	<div class="layui-form-item">
	    <label class="layui-form-label">用户名</label>
	    <div class="layui-input-block">
	      <input type="text" name="username" id="username" required  lay-verify="required" value="" placeholder="请输入 用户名/邮箱" autocomplete="off" class="layui-input baisetouming">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"> 密 码&nbsp;&nbsp; </label>
	    <div class="layui-input-block">
	      <input type="password" name="password" id="password" required lay-verify="required" value="" placeholder="请输入密码" autocomplete="off" class="layui-input baisetouming">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"> 验证码 </label>
	    <div class="layui-input-inline">
	      <input type="text" name="code" id="codeInput" required lay-verify="required" placeholder="请输入右侧验证码" autocomplete="off" class="layui-input baisetouming">
	    </div>
	    <div class="layui-form-mid layui-word-aux" style="padding-top: 3px;padding-bottom: 0px;"><img id="code" src="/captcha.do" onclick="reloadCode();" style="height: 29px;width: 110px; cursor: pointer;" /></div>
	  </div>
	  <div class="layui-form-item" style="display:none">
	    <label class="layui-form-label">记住密码</label>
	    <div class="layui-input-block">
	      <input type="checkbox" name="switch" lay-skin="switch">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <a class="layui-btn" style="opacity:0.6; margin-right: 50px;" onclick="login();">立即登陆</a>
	      <button type="reset" class="layui-btn layui-btn-primary baisetouming" style="width: 90px;">重置</button>
	    </div>
	  </div>
  </div>
</form>
 
<!--[if IE]>
	<div style="position: absolute;bottom: 0px;padding: 10px;text-align: center;width: 100%;background-color: yellow;">建议使用<a href="https://www.baidu.com/s?wd=Chrome" target="_black" style="text-decoration:underline">Chrome(谷歌)</a>、<a href="https://www.baidu.com/s?wd=Firefox" target="_black" style="text-decoration:underline">Firefox(火狐)</a>浏览器，IE浏览器会无法操作！！！</div>
<![endif]-->
<script>
//重新加载验证码
function reloadCode(){
	var code=document.getElementById('code');
	code.setAttribute('src','/captcha.do?token='+shop.getToken());
	//这里必须加入随机数不然地址相同我发重新加载
}

/**
 * 点击登录按钮后执行登录操作
 */
function login(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var code = document.getElementById('codeInput').value;
	
	if(username.length == 0){
		msg.failure('请输入用户名');
		return;
	}
	if(password.length == 0){
		msg.failure('请输入密码');
		return;
	}
	if(code.length == 0){
		msg.failure('请输入验证码');
		return;
	}
	
	msg.loading('登录中');
	post('shop/store/api/login/login.json',{"username":username, "password":password, "code":code},function(data){
		msg.close();    //关闭“更改中”的等待提示
		if(data.result != '1'){
			msg.failure(data.info);
			//登录失败，那么验证码也已经使用过了，重新刷新验证码
			reloadCode();
		}else{
			//登录成功
			msg.success('登录成功', function(){
				window.location.href="/store/index/index.jsp";
			});
		}
	});
}


//调用接口获取一个新的token，也就是相当于获取一个Session id
post(shop.api.login.token,{},function(data){
	shop.setToken(data.info);
	reloadCode();
});
</script>

</body>
</html>