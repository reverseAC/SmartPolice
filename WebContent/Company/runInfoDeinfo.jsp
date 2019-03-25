<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@ taglib uri="/struts-tags" prefix="s"%> --%>
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
<script src="js/getUrlData.js"></script>
<script src="js/table/runInfoDeinfo.js"></script><!-- 在其中导入数据 -->
</head>
<body>
  <div class="main">
      <div class="position">
          <h3>当前位置：</h3>
          <p><a href="#">厂商首页</a><a href="runInfo.jsp" target="Rcontent">&gt;平台运行信息</a><a href="#">&gt;平台详细信息</a></p>
      </div>
       
	  <div class="conversion_different">
	      <div class="operation fr">
	      <ul class="operating">	        
	         <li><a href="#">导出</a></li>
	         <li><a href="#">打印</a></li>
	      </ul>
	      <div class="search fr">
	         <form action="" method="post">
	             <input type="text" value="模糊搜索" class="search_box">
	        	 <input type="button" value="确定" class="button">
	         </form>       		
	      </div>
	      <!-- 表格 -->
	      <div class="form">
	             <!-- <span class="span_th">前端设备类型信息表</span> -->
	            <div class="panel-body" style="padding-bottom: 0px;">    		
		              <table id="tb_departments" data-reorderable-columns="true"></table>
	            </div>
	       </div>
	  </div>
</div>
</body>


</html>