<%@page import="com.xnx3.j2ee.util.SystemUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../../iw/common/head.jsp">
	<jsp:param name="title" value="登录跳转"/>
</jsp:include>


<script type="text/javascript">
	msg.loading('登录中');
	window.location.href="/store/index/index.jsp?token=${token}";
</script>

<jsp:include page="../../../iw/common/foot.jsp"></jsp:include>  