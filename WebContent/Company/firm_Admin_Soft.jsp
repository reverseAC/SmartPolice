<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>软件信息管理</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-table.min.css"rel="stylesheet">
	
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/runInfo.css" />
<script src="js/table/jquery.min.js"></script>
<script src="js/table/bootstrap.min.js"></script>
<script src="js/table/layer.min.js"></script>
<script src="js/table/bootstrap-table.min.js"></script>
<script src="js/table/bootstrap-table-zh-CN.min.js"></script>

<script src="js/table/Soft.js"></script><!-- 在其中导入数据 -->
</head>
<body>

        	
        <div class="main">
        	<div class="position">
        		<h3>当前位置：</h3>
        		<p><a href="#">软件管理</a><a href="#">&gt软件信息管理</a></p>
        	</div>
        	<!-- 这里开始改变，每个页面的不同从这里开始编写 -->
   			<div class="conversion_different">     	
	        	<div class="operation fr">
	        		<ul class="operating">
	        			<li><a href="#" id="delete">删除</a></li>
     					<li><a href="#">导出</a></li>
     					<li><a href="#">打印</a></li>
     					<!-- <li><a href="#">送审</a></li> -->
     					<li><a href="./firm_up_soft.jsp">添加</a></li>
	        		</ul>
	        		<div class="search fr">
	        			<form action="findSoftInfo" method="post">
	        			<!-- <form action="firmSoftTable" method="post"> -->
	        				<input type="text" value="输入软件id进行搜索" class="search_box" name="search">
	        				<input type="submit" value="确定" class="button">
	        			</form>       		
	        		</div>
	        		<!-- 表格 -->
	        		<div class="form">
	                      <div class="panel-body" style="padding-bottom: 0px;">    		
		                    <table id="tb_departments" data-reorderable-columns="true"></table>
	                    </div>
	        		</div>
	           </div>
	        </div>
	      </div>
</body>

</html>