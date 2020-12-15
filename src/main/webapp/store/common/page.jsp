<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type='text/javascript' src='/module/page/page.js'></script>
<script>
page.upNextPageNumber = 2;	//上几页、下几页，显示的数。如这里是2，则向上会显示2页
</script>
<!-- 这是日志列表页面底部的分页显示 -->
<style>
	#page{
		width:100%;
		padding-top:15px;
		padding-bottom: 20px;
	}
	#page ul{
		width: 100%;
		text-align: center;
	}
	#page ul li{
		display: inline-block;
	    vertical-align: middle;
	    border: 1px solid #e2e2e2;
	    background-color: #fff;
	    padding-right: 1px;
    	padding-left: 1px;
	}
	#page ul .xnx3_page_currentPage, #page ul .xnx3_page_currentPage a{
		background-color: #009688;
		color:#fff;
	}
	#page ul li a{
	 	padding: 0 15px;
	 	height: 30px;
	    line-height: 30px;
	    background-color: #fff;
	    color: #333;
	 }
</style>

<!-- id的值不能改 -->
<div id="page">
	<ul>
		<!-- 判断当前页面是否是列表页第一页，若不是第一页，那会显示首页、上一页的按钮。 id的值不能改 -->
		<span id="firstPage">
			<li><a href="javascript:list(1);">首页</a></li>
			<li><a href="javascript:list('{upPageNumber}');">上一页</a></li>
		</span>
		
		<!-- 输出上几页的连接按钮。id的值不能改 --> 
		<span id="upList">
			<li><a href="{href}">{title}</a></li>
		</span>
		
		<!-- 当前页面，当前第几页 -->
		<li class="xnx3_page_currentPage"><a href="#">{currentPageNumber}</a></li>
		
		<!-- 输出下几页的连接按钮。 id的值不能改 --> 
		<span id="nextList">
			<li><a href="{href}">{title}</a></li>
		</span>
		
		<!-- 判断当前页面是否是列表页最后一页，若不是最后一页，那会显示下一页、尾页的按钮。 id的值不能改 -->
		<span id="lastPage">
			<li><a href="javascript:list('{nextPageNumber}');">下一页</a></li>
			<li><a href="javascript:list('{lastPageNumber}');">尾页</a></li>
		</span>
		
		<li style="margin-right:30px;border:0px; padding-top:5px;">共{allRecordNumber}条，{currentPageNumber}/{lastPageNumber}页</li>
	</ul>
</div>