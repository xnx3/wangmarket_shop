<%@page import="com.xnx3.j2ee.entity.User"%>
<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../store/common/head.jsp">
    <jsp:param name="title" value="会员资料信息"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Role_role.js"></script>
<script src="/<%=Global.CACHE_FILE %>User_isfreeze.js"></script>

<table class="layui-table iw_table">
    <tbody id="tbody">
    <tr>
        <td class="iw_table_td_view_name">店铺编号</td>
        <td>{id}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺名字</td>
        <td>{name}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺所属用户</td>
        <td>{userid}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺图片</td>
        <td>
            <img src="{head}" style="height: 40px" width="60px">
         </td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺公告</td>
        <td>{notice}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺状态</td>
        <td id="state">

        </td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺店家联系人</td>
        <td>{contacts}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺店家联系电话</td>
        <td>{phone}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺地址</td>
        <td>{address}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">城市</td>
        <td>{city}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺添加时间</td>
        <td>{addtime}</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">总销量</td>
        <td>{sale}</td>
    </tr>

    </tbody>
</table>

<script>
//列表的模版
var goodsListTemplate = document.getElementById("tbody").innerHTML;
function goodsListDate(store,storeData){
    return goodsListTemplate
        .replace(/\{id\}/g, store.id)
        .replace(/\{name\}/g, store.name)
        .replace(/\{userid\}/g, store.userid)
        .replace(/\{head\}/g, store.head)
        .replace(/\{notice\}/g, storeData.notice)
        .replace(/\{contacts\}/g, store.contacts)
        .replace(/\{phone\}/g, store.phone)
        .replace(/\{address\}/g, store.address)
        .replace(/\{city\}/g, store.city)
        .replace(/\{addtime\}/g, formatTime(store.addtime,'Y-M-D h:m:s'))
        .replace(/\{sale\}/g, store.sale)
        ;
}

msg.loading('加载中');
var id = getUrlParams('id');

post('/shop/superadmin/store/storeDetailsView.json?id='+ id,{},function(data){
    msg.close();    //关闭“更改中”的等待提示
    checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

    if(data.result != '1'){
        msg.failure(data.info);
    }else {
        //登录成功
        //列表
        var html = '';
        html = html + goodsListDate(item.store,item.storeData);
        document.getElementById("tbody").innerHTML = html;

        if(item.store.state == 0){
            document.getElementById("state").innerHTML = '审核中';
        }else if(item.store.state == 1){
            document.getElementById("state").innerHTML = '营业中';
        }else {
            document.getElementById("state").innerHTML = '已打烊';
        }
    }
});
</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>