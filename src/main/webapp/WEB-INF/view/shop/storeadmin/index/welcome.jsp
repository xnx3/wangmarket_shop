<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="代理欢迎页面"/>
</jsp:include>


<style>
.iw_table tbody tr .iw_table_td_view_name{
	width:50%;
	padding-left:25%;
}
</style>

<div style="text-align:center; font-size:29px; padding-top:35px; padding-bottom: 10px;">
	欢迎使用 <%=Global.get("SITE_NAME") %> 云商城系统
</div>

<table class="layui-table iw_table" lay-even lay-skin="nob" style="margin:3%; width:94%;">
	<tbody>
		<tr>
			<td class="iw_table_td_view_name">店铺名称</td>
			<td>${store.name }</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺状态</td>
			<td>${store.state }
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">开店时间</td>
			<td>
				<x:time linuxTime="${store.addtime }"></x:time>
			</td>
		</tr>
		
		

    </tbody>
</table>


<script type="text/javascript">


//Jquery layer 提示
$(function(){
	//延长期限按钮
	//延长期限按钮
	var yanchangriqi_tipindex = 0;
	$("#yanchangriqi").hover(function(){
		yanchangriqi_tipindex = layer.tips('点击按钮联系我们，为您延长使用期限', '#yanchangriqi', {
			tips: [2, '#0FA6A8'], //还可配置颜色
			time:0,
			tipsMore: true,
			area : ['200px' , 'auto']
		});
	},function(){
		layer.close(yanchangriqi_tipindex);
	})
	
})


//服务于上级代理显示的窗口
function getTr(name, value){
	if(typeof(value) == 'undefined' || value == null || value.length == 0){
		//忽略
		return "";
	}else{
		return '<tr><td style="width:45px;">'+name+'</td><td>'+value+'</td></tr>';
	}
}
//弹出其上级代理的信息
function jumpParentAgency(){
	content = '<table class="layui-table" style="margin:0px;"><tbody>'
			+getTr('名称', '${parentAgency.name}')
			+getTr('QQ', '${parentAgency.qq}')
			+getTr('手机', '${parentAgency.phone}')
			+getTr('地址', '${parentAgency.address}')
			+'</tbody></table>';
	
	layer.open({
    type: 1
    ,title: '我的上级信息'
    ,content: content
    ,shade: false
    ,resize: false
  });
}

//在线充值，充值 money
function onlineCharge(){
	var onlineChargeLayerIndex = layer.prompt({
		formType: 0,
		value: '1',
		title: '请输入充值金额，单位：元',
	}, function(value, index, elem){
		parent.parent.iw.loading("加载中");
		parent.setTimeout("parent.iw.loadClose()", 2000);
		layer.close(onlineChargeLayerIndex);
		window.open("/plugin/resourceStatistics/agencyadmin/onlineCharge.do?money="+value, "_blank");
	});
}

/**
 * 查看计费明细说明
 */
function costinfo(){
	layer.open({
		type: 2, 
		title:'计费明细', 
		area: ['600px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: '/plugin/resourceStatistics/costinfo.do'
	});
}

/**
 * 查看资金变动明细
 */
function costLogList(){
	layer.open({
		type: 2, 
		title:'资金变动明细', 
		area: ['500px', '700px'],
		shadeClose: true, //开启遮罩关闭
		content: 'costLog/list.do'
	});
}

//代理开通15日内，登录会弹出网站快速开通的视频说明
try {
	var currentTime = Date.parse( new Date() ).toString();
	currentTime = currentTime.substr(0,10);
	if(currentTime - ${user.regtime } < 1296000){
		//多窗口模式，层叠置顶
		layer.open({
		  type: 2 //此处以iframe举例
		  ,title: '90秒学会，快速开通网站视频教程'
		  ,area: ['390px', '100%']
		  ,shade: 0
		  ,offset: 'rb'
		  ,maxmin: true
		  ,content: '${AGENCYUSER_FIRST_USE_EXPLAIN_URL}'
		  ,zIndex: layer.zIndex //重点1
		});
	}
} catch(error) {}
</script>

<script type="text/javascript">
//得到当前版本号，用于版本更新后提醒更新内容
versionUpdateRemind('<%=Global.VERSION %>');
</script>
<!-- 版本提示结束 -->

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  