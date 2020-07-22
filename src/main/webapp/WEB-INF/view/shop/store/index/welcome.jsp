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
			<td class="iw_table_td_view_name">storeid</td>
			<td>
				${store.id } 
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺名称</td>
			<td>
				${store.name } 
				&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="addOrUpdate('${store.id }','${store.name }','name','编辑店铺名称')" style="margin-left: 0;">
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺图标</td>
			<td>
				&nbsp;&nbsp;&nbsp;
				<a href="${store.head}" target="_black">
					<img src = '${store.head }' style="40px;height:40px"/>
				    &nbsp;&nbsp;&nbsp;
				</a>
				<a class="layui-btn layui-btn-xs uploadImg" lay-data = "{url: '/shop/store/index/uploadImg.do?storeId=${store.id}'}" style="\">
					<i class="layui-icon layui-icon-upload"></i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">当前状态</td>
			<td>
				<c:if test="${store.state == 1 }">营业中</c:if>
				<c:if test="${store.state == 2 }">已打烊</c:if>
				<c:if test="${store.state == 3 }">审核中</c:if>
				&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="stateUpdate('${store.id }')" >
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			 <td class="iw_table_td_view_name">店家联系人姓名</td>
			 <td>${store.contacts }
			     &nbsp;&nbsp;&nbsp;
			     <a class="layui-btn layui-btn-xs" onclick="addOrUpdate('${store.id }','${store.contacts }','contacts','编辑姓名')" style="margin-left: 0;">
			        <i class="layui-icon">&#xe642;</i>
			     </a>
			 </td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店家联系电话</td>
			<td>${store.phone }
			    &nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="addOrUpdate('${store.id }','${store.phone }','phone','编辑电话')" style="margin-left: 0;">
			        <i class="layui-icon">&#xe642;</i>
			    </a>
		    </td>	
		</tr>
		<tr>
			<td class="iw_table_td_view_name">所在经纬度</td>
			<td>${store.longitude },${store.latitude }
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="toEditPage('${store.id }',1)" style="margin-left: 0;">
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">所在区域</td>
			<td>${store.province }${store.city }${store.district }
			   &nbsp;&nbsp;&nbsp; 
			   <a class="layui-btn layui-btn-xs" onclick="toEditPage('${store.id }',2)" style="margin-left: 0;">
			      <i class="layui-icon">&#xe642;</i>
			   </a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店家地址</td>
			<td>${store.address }
			   &nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="addOrUpdate('${store.id }','${store.address }','address','编辑地址')" style="margin-left: 0;">
			       <i class="layui-icon">&#xe642;</i>
			    </a>
		  </td>	
		</tr>
		<tr>
			<td class="iw_table_td_view_name">开通时间</td>
			<td>
				<c:if test="${store.addtime != null }"> <x:time linuxTime="${store.addtime }"></x:time></c:if>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺公告</td>
			<td>
				${storeData.notice }
				&nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="updateStoreData('notice', document.getElementById('notice').innerHTML,'编辑公告')" style="margin-left: 0;">
			  	   <i class="layui-icon">&#xe642;</i>
			    </a>
			    <div style="display:none;" id="notice">${storeData.notice}</div>
			</td>
		</tr>
    </tbody>
</table>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
//id:商家id,type：1修改经纬度，2是修改区域
function toEditPage(id,type) {
	layer.open({
	type: 2, 
	title:'编辑页面', 
	area: ['400px', '400px'],
	shadeClose: true, //开启遮罩关闭
	content: '/shop/store/index/toEditPage.do?storeId=' + id + '&type=' + type
	});	  
}

//id,商家id，value当前值，name修改那个属性，text编辑标题内容
function addOrUpdate(id,value,name,text){
	layer.prompt({
		  formType: 2,
		  value: value,
		  title: text,
		  area: ['300px', '100px'] //自定义文本域宽高
		}, function(value, index, elem){
		  parent.msg.loading("操作中"); 
		  $.post('/shop/store/index/save.do?storeId='+id +'&'+name+'=' + value, function(data){
			    parent.iw.loadClose();    //关闭“操作中”的等待提示
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
/*
 * 编辑StoreData的字段
 * @param name 字段的名字
 * @param value 字段默认值
 * @param text 弹出窗口的提示文字
 * @author 管雷鸣
 */
function updateStoreData(name, value,text){
	layer.prompt({
	  formType: 2,
	  value: value,
	  title: text,
	  area: ['300px', '100px'] //自定义文本域宽高
	}, function(value, index, elem){
	  parent.msg.loading("操作中"); 
	  var data;
	  if(name == 'notice'){
	  	data = {'notice':value};
	  }
	  $.post('/shop/store/index/saveStoreData.do',data,  function(data){
		    parent.iw.loadClose();    //关闭“操作中”的等待提示
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

//id,商家id，
function stateUpdate(id) {
	var value = 2;
	layer.confirm('修改店铺状态', {
		  btn: ['营业中','已打烊'] //按钮
		}, function(){
			$.post('/shop/store/index/save.do?storeId='+id +'&state=1', function(data){
			    parent.iw.loadClose();    //关闭“操作中”的等待提示
			    if(data.result == '1'){
			        parent.msg.success('操作成功');
			        window.location.reload();	//刷新当前页
			     }else if(data.result == '0'){
			         parent.msg.failure(data.info);
			     }else{
			         parent.msg.failure();
			     }
			});
		}, function(){
			$.post('/shop/store/index/save.do?storeId='+id +'&state=2', function(data){
			    parent.iw.loadClose();    //关闭“操作中”的等待提示
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
	console.log(value);
}

layui.use('upload', function(){
	var upload = layui.upload;
	
	//执行实例
	var uploadInst = upload.render({
		  elem: '.uploadImg' //绑定元素
		 ,field : 'file'
		 ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			 msg.loading('上传中...');
			}
		,done: function(res){
			iw.loadClose();
			if(res.result == '1'){
				parent.msg.success("上传成功");
				 window.location.href = '/shop/store/index/welcome.do';
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