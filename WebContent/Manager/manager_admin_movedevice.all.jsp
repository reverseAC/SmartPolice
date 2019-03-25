<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/reset.css" />
<link rel="stylesheet" type="text/css" href="style/common.css" />
<link rel="stylesheet" type="text/css" href="style/manager_all.css" />
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
</head>
<body>
<s:action name="PredeviceDetailOfManager" ></s:action>
<div class="main main_body">
	<div class="content index_content">
			<div class="position">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p>
    				<a href="#">移动监控管理</a> <a href="manager_admin_movedevice.jsp">&gt移动监控管理</a><a href="#">&gt查看详情</a>  			   
    			</p>
    		</div>
        
        	<!-- 这里开始改变，每个页面的不同从这里开始编写 -->
        	
        	<div class="operation">
        		<ul class="operating">
        			<!-- <li><a href="#"><b></b>删除</a></li> -->
        			<li><a href="#"><b></b>导出</a></li>
        			<li><a href="#"><b></b>打印</a></li>
        			<!-- <li><a href="#"><b></b>批量通过</a></li>
        			<li><a href="#"><b></b>批量删除</a></li> -->
        			
        		</ul>
        		<!-- 详细信息 -->
        		<s:iterator value="#session.allPredeviceInfo" id="api" > 
        		<ul class="detailed_list">
        			<li>
        				<i>序号：</i>
        			<div class="detailed_content">
						<s:property value="#api.auditid"/>
						</div>
        			</li>
        			<li>
        				<i>设备名称：</i>
        			<div class="detailed_content">
						<s:property value="#api.devicename"/>
						</div>
        			</li>
        			<li>
        				<i>设备类型：</i>
        				<div class="detailed_content">
						<s:property value="#api.type"/>
						</div>
        			</li><li>
        				<i>设备序列号：</i>
        				<div class="detailed_content">
						<s:property value="#api.code"/>
						</div>
        			</li><li>
        				<i>状态：</i>
        			<div class="detailed_content">
						<s:property value="#api.state"/>
						</div>
        			</li>
        			<li>
        				<i>设备描述：</i>
        			<div class="detailed_content">
						<s:property value="#api.demo"/>
						</div>
        			</li>
        			<li>
        				<i></i>
        				<div class="detailed_content">
        					<input type="button" class="the_editor" id="" value="编 辑">
        					<input type="button" class="re_back" id="" value="返 回">
        				</div>
        			</li>
        		</ul>
        		</s:iterator>
        		</div>
        	</div>
       </div>
</body>
<script type="text/javascript">
					/* 表格行间隔色 */
					$(document).ready(function(){
						odd={"background":"#fff"};
						even={"background":"#f9f9f9"};
						odd_even(".detailed_list",odd,even);
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
					$(".re_back").click(function(){
						window.history.go(-1);
					})
				</script>
</html>