<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/reset.css" />
<link rel="stylesheet" type="text/css" href="style/common.css" />
<link rel="stylesheet" type="text/css" href="style/loginFPwdM.css" />
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
<script src="js_m/loginFPwdM.js"></script>
<title>智能平台系统</title>
</head>
<body>
 <!-- onload="startTime()" -->
    <div class="">
    	<div id="head_background">
       		 <div class="logo_left fl"></div>
       		 <div class="fr">
       		 	<div class="wel_right">
       		 		<p>欢迎您选择登录平台！</p>
       		 	</div>
       		 	<div class="problem">
       		 	 	<p>有什么可以帮助您的吗？<a href="#">点击这里</a></p>
       		 	</div>
       		 </div>
    	</div>
    	<div class="back_line"></div>
    	<!-- 这里开始编写 -->
    	<div class="big_back clearfix">
    		<!-- 个人中心的左侧导航栏（现在尚未设计），
    		             其中个人中心包括的内容：1、真实信息填写（信息完善）
    		                           2、密码找回
    		                            -->
    		<div class="login_form">
    		     <div class="login_position">
    		    	<h3>找回密码</h3>
    		    	<p class="use_in">-&gt填写用户名</p>
    		    	<p class="pwd_in">-&gt设置新密码</p>
    		    </div>
    			<!-- <div class="forget_chioce">
    				<span>填写帐户名</span>
    				<span>设置新密码</span>
    				<span>完成</span>
    			</div> -->
    			<form action=""  class="reset_pwd1 reset_item">
    				<div class="login_list">
    					<label>   					
    						<span>邮箱/手机：</span>
    						<input type="text" value="" class="text"/>   					
    					</label>
    					<p class="msg_f"><i class="ati_f"></i>请输入您的邮箱/验证手机</p>
    				</div>
    			<div class="login_list">
    				<label>   					
    					<span>验证码：</span>
    					<input type="text" value="" class="text1" id="find_text"/>
    					<b class="w_random" id="code_value">2 3 5 9</b>   					
    				</label>
    				<p class="msg_f" id="code_btn"><i class="ati_f"></i>看不清？换一张</p>
    			</div>
    			<div class="login_list">
    				<label>   					
    					<span>发送验证码：</span>
    					<input type="text" value="" class="text1" placeholder="验证码"/>
    					<b class="w_random">点击获取</b>   					
    				</label>
    				<p class="msg_f"><i class="ati_f"></i>看不清？换一张</p>
    			</div>
    			<div class="login_list">
    				<label>   					
    					<span></span>
    					<input type="button" value="下一步" class="text btn useBtn"/>   					
    				</label>
    				<h6><s:actionerror /></h6>
    			</div>
    			<div class="login_list">
    				<label>   					
    					<span></span>
    					<i class="on">默认方式</i>
    					<i class="find_problem">密保找回</i>  					
    				</label>
    				
    			</div>
    		</form>
    		
    		<!-- 密保 -->
    		<form action="sec-question"  class="reset_pwd3" method="post">
    				<div class="login_list">
    					<label>   					
    						<span>账号：</span>
    						<input type="text" value="" class="text" name="username"/> 				
    					</label>
    					<p class="msg_f"><i class="ati_f"></i>请输入您的账号</p> 	
    				</div>
    				<div class="login_list">
    					<label>   					
    						<span>密保问题1：</span>
    						<p>我的大学班主任是谁？</p>  					
    					</label>	
    				</div>
    				<div class="login_list">
    					<label>   					
    						<span>我的答案：</span>
    						<input type="text" value="" class="text" name="uniMaster"/>   					
    					</label>
						<p class="msg_f"><i class="ati_f"></i>请输入答案</p> 		
    				</div>
    				<div class="login_list">
    					<label>   					
    						<span>密保问题2：</span>
    						<p>我的小学叫什么名字？</p>   					
    					</label>	
    				</div>
    				<div class="login_list">
    					<label>   					
    						<span>我的答案：</span>
    						<input type="text" value="" class="text" name="primarySch"/>   					
    					</label>
						<p class="msg_f"><i class="ati_f"></i>请输入答案</p> 	
    				</div>
	    			<div class="login_list">
	    				<label>   					
	    					<span>验证码：</span>
	    					<input type="text" value="" class="text1" id="find_pro" />
	    					<b class="w_random" id="find_code">2 3 5 9</b>   					
	    				</label>
	    				<p class="msg_f find_f" id="find_id"><i class="ati_f"></i>看不清？换一张</p>
	    			</div>
    			
	    			<div class="login_list">
	    				<label>   					
	    					<span></span>
	    					<input type="submit" value="下一步" class="text btn findBtn" id="ques" disabled="disabled"/>   					
	    				</label>
	    				<h6><s:actionerror /></h6>
	    			</div>
	    			
	    			<div class="login_list">
	    				<label>   					
	    					<span></span>
	    					<i class="default">默认方式</i>
    					    <i class="on">密保找回</i>
	    				</label>
	    				
	    			</div>
    		</form>
    		</div>
    	</div>
    </div>
<div class="mask"></div> 
<div class="prompt_form">
	<div class="prompt_title">
		<h2>信息提示</h2>
		<span></span>
	</div>
	<div class="prompt_con">
		<!-- <p>注册失败！</p> -->
	</div>
	<input class="pr_btn" type="button" value="确定">
</div>   
</body>
<script type="text/javascript">
	
</script>




</html>