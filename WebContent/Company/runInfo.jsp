<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>runInfo</title>
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

<script src="js/table/runInfotable.js"></script><!-- 在其中导入数据 -->
</head>
<body>
  <div class="main">
      <div class="position">
          <h3>当前位置：</h3>
          <p><a href="#">厂商首页</a><a href="#">&gt平台运行信息</a></p>
      </div>
       
	  <div class="conversion_different">
	      <div class="operation fr">
	      <ul class="operating">	        
	         <li><a href="#">导出</a></li>
	         <li><a href="#">打印</a></li>
	      </ul>
	      <div class="search fr">
	         <form action="SystemRunInfo" method="">
	             <input type="text" placeholder="模糊搜索" class="search_box">
	        	 <input type="button" value="确定" class="button">
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