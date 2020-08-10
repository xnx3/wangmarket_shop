<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<jsp:include page="../../iw/common/head.jsp">
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
    <tbody id="tbody">
    <c:forEach var="item" items="${list }">
        <tr>
            <td style="text-align:center;">${item.id }</td>
            <td style="text-align:center;">${item.contacts }</td>
            <td style="text-align:center;">${item.phone }</td>
            <td style="text-align:left">${item.address }</td>
            <td style="text-align:center;">
                <c:if test="${item.addtime != null }">
                    <x:time linuxTime="${item.addtime}" format="yyyy-MM-dd HH:mm:ss"></x:time>
                </c:if>
            </td>
        </tr>

    </c:forEach>
    </tbody>
</table>
<!-- 通用分页跳转 -->
<jsp:include page="../../iw/common/page.jsp" />

<jsp:include page="../../iw/common/foot.jsp"></jsp:include>