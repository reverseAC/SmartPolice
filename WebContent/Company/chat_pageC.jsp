<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天窗口</title>
	<link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
<!-- 	<link rel="stylesheet" type="text/css" href="css/auditVendorReg.css"> -->
	<link rel="stylesheet" type="text/css" href="css/chat_pageC.css">
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/dragC.js"></script>
</head>
<body>
	<div class="chat_mian" id="chat_mian">
        <div class="chat_left">
        	<div style="position: relative; z-index: 1;">
	            <div class="chat_close" id="chat_close"></div>
	            <div class="chat_group_btn" id="chat_group_btn"></div>
	        </div>
        	<div class="chat_header">
        		<h3>张 三@123</h3>
        	</div>
        	<div class="chat_content">
        		<div class=""></div>
        		<div class="chat_input">
        			<ul class="package">
        				<li></li>
        			</ul>
        			<div class="chat_item">
        				<input type="text" class="text">
        				<ul class="btn_group fr">
        					<li><input type="button" value="发送" class="btn_china"></li>
        					<li><span class="btn_english">Enter</span></li>
        				</ul>
        			</div>
        		</div>
        	</div>
        </div>
        <div class="chat_right">
        	<div class="manager_header" id="manager_header">
        		<span></span>
        		<h4>管理员名称</h4>
        	</div>
        	<div class="manager_group">
        		<span></span>
        		<span id="add_person"></span>
        	</div>
        	<div class="add_group" id="add_group">
        		<input type="text" class="add_text">
        		<input type="button" class="add_btn" value="添加">
        	</div>
        	<ul class="person_group">
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        	</ul>
        	<!-- <ul class="person_group1">
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        		<li>
        			<div class="person_item clearfix">
        				<span></span>
	        			<h4>张三</h4>
	        			<p class="time">11:45</p>
        			</div>
        		</li>
        	</ul> -->
        </div>
        <script type="text/javascript">
        	
        
        </script>
    </div> 
</body>
</html>