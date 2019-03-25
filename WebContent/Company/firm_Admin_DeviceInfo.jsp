<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/adminDeviceDetailed.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>
<body>

<div class="main">
        	<div class="position">
        		<h3>当前位置：</h3>
        		<p><a href="#">设备管理</a>
        		   <a href="firm_Admin_Device.jsp" target="Rcontent">&gt;设备信息管理</a>
        		   <a href="#">&gt;设备详细信息</a>
        		</p>
        	</div>
        	<!-- 这里开始改变，每个页面的不同从这里开始编写 -->
        	
        	<div class="operation">
        		<ul class="operating">
        			<li><a href="#">删除</a></li>
        			<li><a href="#">导出</a></li>
        			<li><a href="#">打印</a></li>
        		</ul>
        		<s:iterator value="#session.deviceInfo" id="api" > 
        		<!-- 详细信息 -->
        		<ul class="detailed_list">
        			<li>
        				<i>设备名称：</i>
        				<div class="detailed_content"><s:property value="#api.devicename"/></div>
        			</li>
        			<li>
        				<i>设备类型：</i>
        				<div class="detailed_content"><s:property value="#api.type"/></div>
        			</li><li>
        				<i>设备序列号：</i>
        				<div class="detailed_content"><s:property value="#api.code"/></div>
        			</li><li>
        				<i>申请时间：</i>
        				<div class="detailed_content"><s:property value="#api.date"/></div>
        			</li><li>
        				<i>上传者：</i>
        				<div class="detailed_content"><s:property value="#api.name"/></div>
        			</li><li>
        				<i>状态：</i>
        				<div class="detailed_content"><s:property value="#api.state"/></div>
        			</li><li>
        				<%--
        				<li>
        				<i>上传设备：</i>
        				<div class="detailed_content"><s:property value="#api.type"/></div>
        			</li><i>设备登录次数：</i>
        				<div class="detailed_content"><s:property value="#api.devicename"/></div>
        			</li> <li>
        				<i>设备登录总时长：</i>
        				<div class="detailed_content"><s:property value="#api.devicename"/></div>
        			</li> --%>
        			<li>
        				<i>设备描述：</i>
        				<div class="detailed_content"><s:property value="#api.demo"/></div>
        			</li>
        			<li>
        				<i></i>
        				<div class="detailed_content">
        					<input type="button" class="the_editor" id="the_editor" value="编 辑">
        					<input type="button" class="re_back" id="re_back" value="返 回">
        				</div>
        			</li>
        		</ul>
        		</s:iterator>
        		</div>
        	</div>
</body>
<script type="text/javascript">
					/* 表格行间隔色 */
					$(document).ready(function(){
						odd={"background":"#fff"};
						even={"background":"#f9f9f9"};
						odd_even(".detailed_list",odd,even);
						$("#re_back").click(function(){
							window.open("firm_Admin_Device.jsp","_self");
						})
					})
					function odd_even(clas,odd,even){
						$(clas).find("li").each(function(index,element){
							if(index%2==1){
								$(this).css(odd);
							}else{
								$(this).css(even);
							}
						})
					}	
				</script>
</html>