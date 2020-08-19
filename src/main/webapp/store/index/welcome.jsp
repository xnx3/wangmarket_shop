<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="代理欢迎页面"/>
</jsp:include>
<style>
.iw_table tbody tr .iw_table_td_view_name{
	width:50%;
	padding-left:25%;
}
</style>

<div style="text-align:center; font-size:29px; padding-top:35px; padding-bottom: 10px;">
	欢迎使用 云商城系统
</div>

<table class="layui-table iw_table" lay-even lay-skin="nob" style="margin:3%; width:94%;">
	<tbody>
		<tr>
			<td class="iw_table_td_view_name">storeid</td>
			<td id="storeId">
				加载中...
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺名称</td>
			<td>
				<div id="storeName" style="float: left">
					加载中...
				</div>
				&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="addOrUpdate(store.name,'name','编辑店铺名称')" style="margin-left: 0;">
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺图标</td>
			<td>
				&nbsp;&nbsp;&nbsp;
				<a id="storeImgLink" target="_black">
					<img id="storeImg" style="width:40px;height:40px;float: left"/>
				    &nbsp;&nbsp;&nbsp;
				</a>
				<a class="layui-btn layui-btn-xs uploadImg" style="margin-top: 2.5%">
					<i class="layui-icon layui-icon-upload"></i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">当前状态</td>
			<td>
				<div id="storeState" style="float: left">
				加载中...
				</div>
				&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="stateUpdate()" >
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			 <td class="iw_table_td_view_name">店家联系人姓名</td>
			 <td>
				 <div id="storeContacts" style="float: left">
					 加载中...
				 </div>
			     &nbsp;&nbsp;&nbsp;
			     <a class="layui-btn layui-btn-xs" onclick="addOrUpdate(store.contacts,'contacts','编辑姓名')" style="margin-left: 0;">
			        <i class="layui-icon">&#xe642;</i>
			     </a>
			 </td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店家联系电话</td>
			<td>${store.phone }
				<div id="storePhone" style="float: left">
					加载中...
				</div>
			    &nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="addOrUpdate(store.phone,'phone','编辑电话')" style="margin-left: 0;">
			        <i class="layui-icon">&#xe642;</i>
			    </a>
		    </td>	
		</tr>
		<tr>
			<td class="iw_table_td_view_name">所在经纬度</td>
			<td>
				<div id="storeLongitudeLatitude" style="float: left">
					加载中...
				</div>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="layui-btn layui-btn-xs" onclick="toEditPage(1)" style="margin-left: 0;">
				   <i class="layui-icon">&#xe642;</i>
				</a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">所在区域</td>
			<td>
				<div id="storeRegion" style="float: left">
					加载中...
				</div>
			   &nbsp;&nbsp;&nbsp; 
			   <a class="layui-btn layui-btn-xs" onclick="toEditPage(2)" style="margin-left: 0;">
			      <i class="layui-icon">&#xe642;</i>
			   </a>
			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店家地址</td>
			<td>
				<div id="storeAddress" style="float: left">
					加载中...
				</div>
			   &nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="addOrUpdate(store.address,'address','编辑地址')" style="margin-left: 0;">
			       <i class="layui-icon">&#xe642;</i>
			    </a>
		  </td>	
		</tr>
		<tr>
			<td class="iw_table_td_view_name">开通时间</td>
			<td id="storeAddtime">

			</td>
		</tr>
		<tr>
			<td class="iw_table_td_view_name">店铺公告</td>
			<td>
					<div id="storeNotice" style="float: left">
						加载中...
					</div>
				&nbsp;&nbsp;&nbsp;
			    <a class="layui-btn layui-btn-xs" onclick="updateStoreData('notice', document.getElementById('notice').innerHTML,'编辑公告')" style="margin-left: 0;">
			  	   <i class="layui-icon">&#xe642;</i>
			    </a>
			    <div style="display:none;" id="notice"></div>
			</td>
		</tr>
    </tbody>
</table>
<script type="text/javascript">
//id:商家id,type：1修改经纬度，2是修改区域
function toEditPage(type) {
	var province = encodeURI(encodeURI(store.province));
	var city = encodeURI(encodeURI(store.city));
	var district = encodeURI(encodeURI(store.district));
	layer.open({
	type: 2, 
	title:'编辑页面', 
	area: ['400px', '400px'],
	shadeClose: true, //开启遮罩关闭
	content: '/store/index/edit.jsp?type=' + type  + '&longitude=' + store.longitude  + '&latitude=' + store.latitude
		+ '&province=' + province + '&city=' + city + '&district=' + district
	});	  
}
//value当前值，name修改那个属性，text编辑标题内容
function addOrUpdate(value,name,text){
	layer.prompt({
		  formType: 2,
		  value: value,
		  title: text,
		  area: ['300px', '100px'] //自定义文本域宽高
		}, function(value, index, elem){
		  parent.msg.loading("操作中");
		  $.post('/shop/store/api/store/save.json?'+name+'=' + value, function(data){
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
	  $.post('/shop/store/api/store/saveStoreData.json',data,  function(data){
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

//id,商家id，
function stateUpdate() {
	var value = 2;
	layer.confirm('修改店铺状态', {
		  btn: ['营业中','已打烊'] //按钮
		}, function(){
			$.post('/shop/store/api/store/save.json?state=1', function(data){
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
		}, function(){
			$.post('/shop/store/api/store/save.json?state=2', function(data){
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
	console.log(value);
}

layui.use('upload', function(){
	var upload = layui.upload;
	
	//执行实例
	var uploadInst = upload.render({
		  elem: '.uploadImg' //绑定元素
		 ,field : 'file'
		 ,url: '/shop/store/api/store/uploadImg.json'
		 ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			 msg.loading('上传中...');
			}
		,done: function(res){
			msg.close();
			if(res.result == '1'){
				parent.msg.success("上传成功");
				 window.location.href = '/store/index/welcome.jsp';
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

msg.loading('加载中');
var store;
post('/shop/store/api/store/getStore.json',{},function(data){
msg.close();    //关闭“更改中”的等待提示
checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

	if(data.result != '1'){
		msg.failure(data.info);
	}else{
		//登录成功
		store = data.store;
		document.getElementById('storeId').innerHTML = store.id;
		document.getElementById('storeName').innerHTML = store.name;
		document.getElementById('storeImg').src = store.head;
		document.getElementById('storeImgLink').href = store.head;
		document.getElementById('storeContacts').innerHTML = store.contacts;
		var state = store.state;
		if (state == 0) {
			document.getElementById('storeState').innerHTML =	'审核中';
		}else if(state == 1){
			document.getElementById('storeState').innerHTML =	'营业中';
		}else if(state == 2){
			document.getElementById('storeState').innerHTML =	'已打烊';
		}
		document.getElementById('storePhone').innerHTML = store.phone;
		document.getElementById('storeLongitudeLatitude').innerHTML =  store.longitude+' , '+store.latitude;
		document.getElementById('storeRegion').innerHTML = store.province+store.city+store.district;
		document.getElementById('storeAddress').innerHTML = store.address;
		document.getElementById('storeAddtime').innerHTML = formatTime(store.addtime,'Y-M-D h:m:s');
		document.getElementById('storeNotice').innerHTML = store.notice;
		document.getElementById('notice').innerHTML = store.notice;
	}
});
</script>

<jsp:include page="../common/foot.jsp"></jsp:include>