<%@page import="com.xnx3.j2ee.entity.User"%>
<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../../iw/common/head.jsp">
    <jsp:param name="title" value="会员资料信息"/>
</jsp:include>
<script src="/<%=Global.CACHE_FILE %>Role_role.js"></script>
<script src="/<%=Global.CACHE_FILE %>User_isfreeze.js"></script>

<table class="layui-table iw_table">
    <tbody>
    <tr>
        <td class="iw_table_td_view_name">店铺编号</td>
        <td>${s.id }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺名字</td>
        <td>${s.name }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺所属用户</td>
        <td>${s.userid }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺图片</td>
        <td>
            <img src="${s.head }" style="height: 40px" width="60px">
         </td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺公告</td>
        <td>${st.notice }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺状态</td>
        <td>
            <c:choose>
                <c:when test="${s.state == 0}">
                    审核中
                </c:when>
                <c:when test="${s.state == 1}">
                    营业中
                </c:when>
                <c:otherwise>
                    已打烊
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺店家联系人</td>
        <td>${s.contacts }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺店家联系电话</td>
        <td>${s.phone }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺地址</td>
        <td>${s.address }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">城市</td>
        <td>${s.city }</td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">店铺添加时间</td>
        <td><x:time linuxTime="${s.addtime }"></x:time></td>
    </tr>
    <tr>
        <td class="iw_table_td_view_name">总销量</td>
        <td>${s.sale }</td>
    </tr>

    </tbody>
</table>
<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>