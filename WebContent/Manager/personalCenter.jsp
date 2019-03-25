<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="style/reset.css">
<link rel="stylesheet" type="text/css" href="style/common.css">	
<link rel="stylesheet" type="text/css" href="style/personal.css">
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js_m/personalCenter.js"></script>
</head>
<body>
<div class="content">
	<div class="box" id="one">
	<div class="box_div">
			<form action="modify" method="post" >
			
			<!-- <label>昵称</label>
			<input type="text" autocomplete='off' name="nickname"><br> -->
			<label>姓名</label>
			<input type="text" autocomplete='off' name="name"><br>
			<%-- <label>性别</label>
			<select name="gender"><option value="男">男</option><option value="女">女</option></select><br> --%>
			<label>邮箱</label>
			<input type="text" autocomplete='off' name="mail"><br>
			<label>联系方式</label>
			<input type="text" autocomplete='off' name="phone"><br>
			<!-- <label>所在地</label>
			<input type="text" autocomplete='off' name="location"> -->
			<div class="submit">
			
			<input type="submit" value="提交">
			<input type="reset" value="关闭" onClick="stopbox('one')">
			</div>
			</form>
			</div></div>
			<div class="box" id="two">
			<div class="box_div">
				<form action="modifyPass" method="post" >
				<label>密码</label>
			<input type="text" autocomplete='off' name="pass"><br>
			<label>绑定手机</label>
			<input type="text" autocomplete='off' name="telephone"><br>
			<div class="submit">
			<div >
			<input type="submit" value="提交">
			<input type="reset" value="关闭" onClick="stopbox('two')">
			</div>
			</div>
				</form>
				</div>
			</div>
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">个人中心</a></p>
    		</div>
	<div class="center_title">
		<!-- <h3>个人中心</h3> -->
		<div class="center_back">
			<div class="p_title">个人信息</div>
			<div class="update-info"><input type="button" value="编辑"  onclick="visible(1)"></div>
		
			<div class="center_con clearfix">
				<div class="peronal_img fl"><img alt="" id="imgshow" src="icon_m/img_1.png">
				<a class="file" onchange="imgshow()">选择文件<input type="file"  id="fileimg"></a>
				</div>
				<ul class="personal_list fl">
									
					<li><i>姓名：</i><span class="ponsal_info"><s:property value="#session.manager.getName()"/></span></li>				
					<li><i>邮箱：</i><span class="ponsal_info">
					<s:property value="#session.manager.getEmail()"/>					
					<li><i>联系方式：</i><span class="ponsal_info"><s:property value="#session.manager. getNumber()"/></li>
					
				</ul>
			</div>
		</div>
	</div>
	
	<div class="center_title">
		<!-- <h3>个人中心</h3> -->
		<div class="center_back">
			<div class="p_title">账号管理</div>
			<div class="update-info"><input type="button" value="编辑"  onclick="visible(2)"></div>
			<div class="center_con clearfix">
				<!-- <div class="peronal_img fl"><img alt="" src=""></div> -->
				<ul class="personal_list fl">
					<li><i>账号：</i><span class="ponsal_info"><s:property value="#session.manager.getUserName()"/></span></li>
					<li><i>密码：</i><span class="ponsal_info">********</span>
					<li><i>绑定手机：</i><span class="ponsal_info">
					<s:property value="#session.manager. getNumber()"/></span>
					
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div> 
<div class="prompt_form">
	<div class="prompt_title">
		<h2>修改信息</h2>
	<span></span> 
	</div>
	<div class="prompt_con">
		<p>请输入：</p>
		<input type="text" class="modify_info" name="info">
	</div>
	<input class="pr_btn" type="button" value="确定">
</div>
</body>
</html>