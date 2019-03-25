<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>登录设备信息管理</title>
    <link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">	
	<link rel="stylesheet" type="text/css" href="style/auditVendorReg.css">
	<link rel="stylesheet" type="text/css" href="style/table.css" />
    <script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="js_m/table.js"></script>
    <script type="text/javascript" src="js_m/print.js"></script><!-- div id -->
	<script type="text/javascript" src="js_m/tableToExcel.js"></script><!-- table id -->

<script type="text/javascript">
      
    </script>
</head>

<body>
<s:action name="StatLogdev" ></s:action>
 <div class="main_body">
    	<div class="content fr">
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">系统信息管理</a><a href="#">&gt登录设备信息</a></p>
    		</div>
    	<!-- 需要添加的从这里开始 -->
    		<div class="operation ">
        		<ul class="operating">
        			<li><a href="#"><b></b>删除</a></li>
        			<li><a href="#" onclick="method1('blocks');"><b></b>导出</a></li>
					<li><a href="#" onclick="printdiv('form');"><b></b>打印</a></li>
        			<li><a href="#"><b></b>批量通过</a></li>
        			<li><a href="#"><b></b>批量删除</a></li>
        			
        		</ul>
        		<div class="table_header_title"><h3>登录设备信息</h3></div>
        		<div class="search fr">
        			<form action="" method="post">
        				<input type="text" value="" class="search_box">
        				<input type="button" value="搜索" class="button">
        			</form>       		
        		</div>
        		<!-- 表格 -->
        		<div class="form" id="form">
        			<table id="blocks" class="table table-striped" style="margin-top:25px">
        				<tr>
        					<th>选择</th>
        					<th>序号</th>
        					<th>设备名称</th>
        					<th>设备类型</th>
        					<th>设备序列号</th>
        					<th>上次登录时间</th>
        					<th>当前状态</th>
        					<th>操作</th>
        				</tr>
        				<tr>
        					<s:iterator value="#session.logDev" id="pdl" > 
								<tr>
								   <td><input type="checkbox" name="" id=""></td>
									<td><s:property  value="#pdl.deviceid"/></td>
			                		<td><s:property value="#pdl.username"/></td>
			                		<td><s:property value="#pdl.type"/></td>
			                		<td><s:property value="#pdl.serial"/></td>
			                		<td><s:property value=""/></td>
			                		<td><s:property value="#pdl.state"/></td>
			                		<td>
								<ul>
									<li><a href="StopLogDev.action?id=<s:property value="#pdl.deviceid"/>">
									<b class="form_icon_del"></b>停用</a></li>
									<li><a href="StartLogDev.action?id=<s:property value="#pdl.deviceid"/>">
									<b class="form_icon_del"></b>启用</a></li>
									<li><a href="manager_stat_logindev.all.jsp?id=<s:property value="#pdl.deviceid"/>">
										<b 	class="form_icon_check"></b>查看</a></li> 
								</ul>
							</td>
			                	</tr>
			              </s:iterator> 
      				</tr>
        			</table>
        		    <div id="pagiDiv"  style="width:70%;">
                       <span id="spanFirst">第一页</span>
                       <span id="spanPre">上一页</span>
                       <span id="spanNext">下一页</span>
                       <span id="spanLast">最后一页</span>
                                                                              第<span id="spanPageNum"></span>页/共<span id="spanTotalPage"></span>页
                    </div>
        		</div>
        	</div>
        </div>
      </div>

</body>

</html>