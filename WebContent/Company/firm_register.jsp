<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/loginRegistered.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/firm_register.js"></script>
<title>注册智能平台系统</title>
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
    	<div class="login_smart_back">
    		<div class="form_logo_til clearfix">
    			<img alt="" src="icon/login_registered.png" class="form_logo">
    			<h3>欢迎注册智能平台用户</h3>
    		</div>
    		<form action="firmregister" class="registered_lists" method="post">
    			<!-- 用户名称 -->
    			<div class="reg_login">
    				<label>
    					<span>用户名称：</span>
    					<input type="text" class="text" autocomplete='off' id="username" name="Username"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>5-25个字符，一个汉字为两个字符，推荐使用英文名
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count1" class="count">0个字符</b>
    				</label>
    			</div>
    			<!-- 真实姓名 -->
    			<div class="reg_login">
    				<label>
    					<span>真实姓名：</span>
    					<input type="text" class="text" autocomplete='off' id="resname" name="Name"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>5-25个字符，一个汉字为两个字符，推荐使用英文名
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count2" class="count">0个字符</b>
    				</label>
    			</div>
    			<!-- 公司id -->
    			<div class="reg_login">
    				<label>
    					<span>公司ID：</span>
    					<input type="text" class="text" autocomplete='off' id="companyId" name="CompanyId"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>数字id
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count4"class="count">0个字符</b>
    				</label>
    			</div>
    			<!-- 职位 -->
    			<div class="reg_login">
    				<label>
    					<span>公司职位：</span>
    					<input type="text" class="text" autocomplete='off' id="position" name="Position"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>5-16个数字组成的id
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count5"class="count">0个字符</b>
    				</label>
    			</div>
    			<!-- 用户密码： -->
    			<div class="reg_login">
    				<label>
    					<span>注册密码：</span>
    					<input type="password" class="text" autocomplete='off' id="pwd" name="Password"/>
    				</label>
    				<p class="msg">
    					<i class="err"></i>建议5-25中英文组合
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<em class="em_active">弱</em><em>中</em><em>强</em>
    				</label>
    			</div>
    			<!-- 确认密码 -->
    			<div class="reg_login">
    				<label>
    					<span>确认密码：</span>
    					<input type="password" class="text" autocomplete='off' id="pwd2" name="rePassword"/>
    				</label>
    				<p class="msg">
    					<i class="ati"></i>请再输入一次
    				</p>
    			</div>
    			<!-- 用户邮箱 -->
    			<div class="reg_login reg_m_top">
    				<label>
    					<span>注册邮箱：</span>
    					<input type="text" class="text" autocomplete='off' id="email" name="Email"/>
    				</label>
    				<p class="msg">
    					<i class="err"></i>不符合邮箱格式
    				</p>
    			</div>
    			
    			<!-- 联系方式 -->
    			<div class="reg_login reg_m_top">
    				<label>
    					<span>手机号码：</span>
    					<input type="text" class="text" autocomplete='off' id="tel" name="Number"/>
    				</label>
    				<p class="msg">
    					<i class="err"></i>必须是11位数字
    				</p>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count3" class="count"> 0个字符</b>
    				</label>
    			</div>
    			<div class="reg_login">
    				<label>
    					<span>验证码：</span>
    					<input type="text" class="text text_code" autocomplete='off' id="text_code"/>
    					<input type="button"class="btn_code" id="btn_code">
    					
    				</label>
    				<p class="msg">
    					<i class="ati"></i><b id="code_btn">看不清，换一张</b>
    				</p>
    			</div>
    			<div class="reg_login reg_m_top tex">
    				<label >
    					<input type="checkbox" class="text1" id="text1"/>
    					我已阅读<a href="#">《注册平台协议》</a>
    				</label>
    				<h4 style="color:red;"><s:actionerror /></h4>
    			</div>
    			<div class="reg_login tex">
    				<label>
    					
    					<input type="submit" class="btn submitBtn" value="注册" id="in_submit" disabled/>
    					
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
		<p></p> 
	</div>
	<input class="pr_btn" type="button" value="确定">
</div>
</body>

</html>