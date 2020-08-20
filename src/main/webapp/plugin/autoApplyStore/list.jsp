<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../store/common/head.jsp">
    <jsp:param name="name" value="开店申请"/>
</jsp:include>
<div style="height:10px;"></div>

<table class="aui-table-responsive layui-table iw_table" style="color: black;font-size: 14px;">
    <thead>
    <tr>
        <th style="text-align:center;">ID</th>
        <th style="text-align:center;">姓名</th>
        <th style="text-align:center;">电话</th>
        <th style="text-align:center;">微信</th>
        <th style="text-align:center;">申请时间</th>
    </tr>
    </thead>
    <tbody id="list">

        <tr>
            <td style="text-align:center;">{id}</td>
            <td style="text-align:center;">{contacts}</td>
            <td style="text-align:center;">{phone}</td>
            <td style="text-align:left">{address}</td>
            <td style="text-align:center;">{addtime}</td>
        </tr>


    </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../store/common/page.jsp" />

<script type="text/javascript">


//列表的模版
var orderTemplate = document.getElementById("list").innerHTML;
function templateReplace(item){
    return orderTemplate.replace(/\{id\}/g, item.id)
        .replace(/\{contacts\}/g, item.contacts)
        .replace(/\{phone\}/g, item.phone)
        .replace(/\{address\}/g, item.address)
        .replace(/\{addtime\}/g,item.addtime != null ? formatTime(item.addtime,'Y-M-D h:m:s') : '')
        ;
}
function list(currentPage){
    var data = {
        'currentPage':currentPage,
        'everyNumber':'15',	//这里每页显示15条数据
    };
msg.loading('加载中');
var storelist;

post('/plugin/api/autoApplyStore/store/list.json',data,function(data){
    msg.close();    //关闭“更改中”的等待提示
    checkLogin(data);	//验证登录状态。如果未登录，那么跳转到登录页面

    if(data.result !== '1'){
        storelist = data.storelist;
        var html = '';
        for(var index in data.storelist){
            var item = data.storelist[index];
            //只显示已选中的商品
            html = html + templateReplace(item);
        }
        document.getElementById("list").innerHTML = html;
        //分页
        page.render(data.page);


    }

});
}
//刚进入这个页面，加载第一页的数据
list(1);
</script>

<jsp:include page="../../store/common/foot.jsp"></jsp:include>