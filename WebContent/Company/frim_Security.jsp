<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/loginRegistered.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/firm_security.js"></script>
<title>添加密保问题</title>
</head>
<body >
 <!-- onload="startTime()" -->
 
    <div class="">
    	<div id="head_background">
       		 <div class="logo_left fl"></div>
       		 <div class="fr">
       		 	<div class="wel_right">
       		 		<p>欢迎您注册平台！</p>
       		 	</div>
       		 	<div class="problem">
       		 	 	<p>有什么可以帮助您的吗？<a href="#">点击这里</a></p>
       		 	</div>
       		 </div>
    	</div>
    	<div class="back_line"></div>
    	<!-- 这里开始 -->
    	
    	<div class="login_list_back">
    	<br>
		<br>
    	<div class="login_smart_back">
    	
    		<div class="form_logo_til clearfix">
    			<img alt="" src="icon/login_registered.png" class="form_logo">
    			<h3>欢迎用户添加密保问题</h3>
    		</div>
    		 <br>
			 <br>
			 <br>
    		<form action="sec_ques" class="registered_lists" method="post">
    			<!-- 用户名称 -->
    			<div class="reg_login">
    				<label>
    					<span>密保问题：</span>
    					<b> 我的大学班主任是谁？ </b>
<!--     					<input type="text" class="text" autocomplete='off' id="secuitypos_1"/> -->
    				</label>
    				<p class="msg">
    					<i class="ati"></i>4-40个字符，一个汉字为两个字符
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count1" class="count">0个字符</b>
    				</label>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span>密保答案：</span>
    					<input type="text" class="text" autocomplete='off' id="secuityanswer_1" name="sec_answer1"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>0-16个字符，一个汉字为两个字符
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count2" class="count">0个字符</b>
    				</label>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span>密保问题：</span>
    					<b>我的小学叫什么名字？ </b>
<!--     					<input type="text" class="text" autocomplete='off' id="secuitypos_2"/> -->
    				</label>
    				<p class="msg">
    					<i class="ati"></i>4-40个字符，一个汉字为两个字符
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count3" class="count">0个字符</b>
    				</label>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span>密保答案：</span>
    					<input type="text" class="text" autocomplete='off' id="secuityanswer_2" name="sec_answer2"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>4-40个字符，一个汉字为两个字符
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count4" class="count">0个字符</b>
    				</label>
    			</div>
    			<div class="reg_login tex">
    				<label>
    					<input type="submit" class="btn submitBtn" value="提交" id="in_submit"/>
    				</label>
    			</div>
    		</form>
    	</div>
    	</div>
    </div>
</body>
</html>