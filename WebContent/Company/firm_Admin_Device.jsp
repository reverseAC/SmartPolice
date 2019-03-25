<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备信息管理</title>
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
<script src="js/table/Device.js"></script><!-- 在其中导入数据 -->
 
</head>
<body>
<div class="main">
   	<div class="position">
   		<h3>当前位置：</h3>
   		<p>
   			<a href="#">设备管理</a>
   			<a href="#">&gt设备信息管理</a>
   		</p>
   	</div>
    <!-- 这里开始改变，每个页面的不同从这里开始编写 -->
    <div class="conversion_different"> 	
     	<div class="operation fr">
     		<ul class="operating">
     			
     			<li><a href="#" id="delete">删除</a></li>
     			<li><a href="#">导出</a></li>
     			<li><a href="#">打印</a></li>
     			<li><a href="firm_up_device.jsp" target="_self">添加</a></li>
     		</ul>
     		<div class="search fr">
     			<form action="" method="post">
     				<input type="text"  class="search_box" />
     				<input type="button" value="确定" class="button" />
     			</form>       		
     		</div>
     		<!-- 表格 -->
     		<div class="form">
     		     <div class="panel-body" style="padding-bottom: 0px;">    		
		             <table id="tb_departments" data-reorderable-columns="true" ></table>
		             	<%-- <tbody>
					<s:iterator value="#session.allCompany" id="pdl" > 
								<tr>
								   <td><input type="checkbox" name="" id=""></td>
									<td><s:property  value="#pdl.COMPANYID"/></td>
			                		<td><s:property value="#pdl.NAME1"/></td>
			                		<td><s:property value="#pdl.TYPE"/></td>
			                		<td><s:property value="#pdl.ADDRESS"/></td>
			                		<td><s:property value="#pdl.NAME"/></td>
			                		<td><s:property value="#pdl.STATE"/></td>
			                		<td>
								<ul>
									<li><a href="ManagerRemoveCompanyUser.action?id=<s:property value="#pdl.USERID"/>">
									<b class="form_icon_del"></b>删除</a></li>
								<!-- 	<li><a href="#"><b class="form_icon_mod"></b>修改</a></li> -->
									<li><a href="manager_admin_company.all.jsp?id=<s:property value="#pdl.COMPANYID"/>">
										<b 	class="form_icon_check"></b>查看</a></li> 
								</ul>
							</td>
			                	</tr>
			              </s:iterator> 
			        
			                </tbody> --%>
	        </div>
               
               
     		</div>
     	</div>
     </div>
  </div>
</body>
   <script>
   
   </script>
</html>