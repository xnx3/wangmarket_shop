<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>未发现对应网站</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
 	<meta name="author" content="管雷鸣">
  <body>
  	
  	<style>
  		.content{
  			width: 600px;
  			min-height:80%;
		    margin: 0 auto;
		    box-shadow: rgba(0, 0, 0, 0.06) 0px 1px 10px 2px;
		    padding: 30px;
		    margin-top: 90px;
  		}
  		.title{
  			border-bottom: 1px solid #eee;
		    padding-top: 20px;
		    padding-left: 10px;
		    padding-bottom: 20px;
		    font-size: 28px;
  		}
  		.info{
  			padding: 25px;
  		}
  		.content ul li{
  			list-style-type: decimal;
  			padding-left:10px;
  			padding-bottom:4px;
  		}
  		.content ul li img{
  			max-width:250px;
  			padding:4px;
  			padding-left:40px;
  		}
  		
  		@media only screen and (max-width: 700px) {
  			.content{
  				width:auto;
  				margin-top: 0px;
  				box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 0px 0px;
  			}
  			
  		}
  	</style>
    <div class="content">
    	<div class="title">
    		域名未绑定
    	</div>
    	<div class="info">
    		您的域名已解析到我们的服务器，但是未绑定到指定网站！请按照以下步骤，进行绑定
    	</div>
    	<ul>
    		<li>您想将域名绑定到哪个网站？使用这个网站的账号跟密码，登陆 <a href="<%=Global.get("MASTER_SITE_URL") %>login.do" target="_black">网站管理后台</a></li>
    		<li>
    			找到左侧菜单的 “系统管理” 功能， 点开，找到其下的 “网站设置”
    			<div>
    				<img src="//res.weiunity.com/image/wangzhanshezhi.jpg" />
    			</div>
    		</li>
    		<li>
    			点击 “网站设置”后，弹出如下窗口，点击如图所示的 “绑定域名”，进行绑定
    			<div>
    				<img src="//res.weiunity.com/image/bangdingyuming.jpg" />
    			</div>
    		</li>
    	</ul>
    	
    </div>
  </body>
</html>
