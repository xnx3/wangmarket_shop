<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 下面这个title参数，也就是在点击最左侧功能插件下的 CNZZ访问统计 进入后的页面，参数设置 子菜单上面的 “CNZZ访问统计” 这个便是。如果title传入的是空字符串，那么就不会显示，子菜单会顶到最顶部 -->
<jsp:include page="../../../store/common/adminSubMenu/head.jsp">
	<jsp:param name="title" value="二级分销"/>
</jsp:include>
<!-- 子菜单开始，下面的每个li便是一个菜单项 -->
<li class="layui-nav-item">
	<a href="javascript:loadUrl('/plugin/sell/store/set.jsp');" >参数设置</a>
</li>
<li class="layui-nav-item">
	<a href="javascript:loadUrl('/plugin/sell/store/commission/list.jsp');" >佣金记录</a>
</li>
<li class="layui-nav-item">
	<a href="javascript:loadUrl('/plugin/sell/store/tixian/list.jsp');" >提现申请</a>
</li>
<!-- 子菜单结束，下面的每个li便是一个菜单项 -->
<jsp:include page="../../../store/common/adminSubMenu/foot.jsp" />
<!-- 进入这个页面后，最右侧内容区域默认打开的链接地址 -->
<script>loadUrl('/plugin/sell/store/set.jsp');</script>