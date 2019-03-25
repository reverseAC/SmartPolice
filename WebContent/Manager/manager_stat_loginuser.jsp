<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>厂商信息管理</title>
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
<s:action name="StatLogUser" ></s:action>
<div class="main_body">
    	<div class="content fr">
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">系统信息管理</a><a href="#">&gt登录用户信息</a></p>
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
        		<div class="table_header_title"><h3>登录用户信息</h3></div>
        		<div class="search fr">
        			<form action="" method="post">
        				<input type="text" value="搜索" class="search_box">
        				<input type="button" value="确定" class="button">
        			</form>       		
        		</div>
        		<!-- 表格 -->
        		<div class="form" id="form">
        			<table id="blocks" class="table table-striped" style="margin-top:25px">
        				<tr>
        					<th>选择</th>
        					<th>序号</th>
        					<th>用户名</th>
        					<th>注册时间</th>
        					<th>最近登录时间</th>
        					
        				    <th>当前状态</th>
        					<th>操作</th>
        				</tr>
        				<tr>
        				<s:iterator value="#session.logUser" id="usr" >
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td><s:property  value="#usr.userID"/></td>
        					<td><s:property  value="#usr.name"/></td>
        					<td><s:property  value="#usr.regDate"/></td>
        					<td><s:property  value=""/></td>
        					
        					<td>在线</td>
        					
        					<td>
        						<ul>
        							<li><a href="ManagerRemoveLogUser.action?id=<s:property value="#usr.userID"/>">
        							<b class="form_icon_mod"></b>踢除</a></li>
        							<li><a href="manager_stat_loginuser.all.jsp?id=<s:property value="#usr.userID"/>"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
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
    	
    	
    	
    	<!-- 结束 -->
    </div>
    </div>
</body>

</html>