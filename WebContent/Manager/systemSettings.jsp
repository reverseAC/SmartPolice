<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置</title>
<link rel="stylesheet" type="text/css" href="style/reset.css">
<link rel="stylesheet" type="text/css" href="style/common.css">	
<link rel="stylesheet" type="text/css" href="style/personal.css">
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
</head>
<body>
	<div class="content">
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">设置</a></p>
    		</div>
	<div class="center_title">
		<!-- <h3>个人中心</h3> -->
		<div class="center_back">
			<div class="p_title">常规设置</div>
			<div class="center_con clearfix">
				<ul class="personal_list fl">
					<li><i>字号：</i>大<input type="radio" class="font_size" name="font">中<input type="radio" class="font_size" name="font" checked="checked">小<input type="radio" class="font_size" name="font"></li>
					<li><i>预览语言：</i>中文<input type="radio" class="font_size" name="language" class="font_size" name="font" checked="checked">English<input type="radio" class="font_size" name="language"></li>
					
					<li><span class="setting_help">关于我们与帮助</span></li>
					
				</ul>
			</div>
		</div>
	</div>
	<div class="center_title">
		<!-- <h3>个人中心</h3> -->
		<div class="center_back">
			<div class="p_title">账号与安全</div>
			<div class="center_con clearfix">
				<!-- <div class="peronal_img fl"><img alt="" src=""></div> -->
				<ul class="personal_list fl">
					<li><i>账号：</i><span class="ponsal_info">123145436</span></li>
					<li><i>保密问题1：</i><span class="ponsal_info">*******</span><span class="info_operation" id="info_operation1">修改</span></li>
					<li><i>保密问题2：</i><span class="ponsal_info">******</span><span class="info_operation" id="info_operation2">修改 </span></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="mask" id="mark1"></div> 
<div class="prompt_form" id="prompt_form1">
	<div class="prompt_title">
		<h2>修改信息</h2>
		<span id="prompt_title1"></span>
	</div>
	<form action="updateQues">
	<div class="prompt_con">
		<p>请输入：</p>
		<input type="text" class="modify_info" name="answer1" id="modify_info1">
	</div>
	<input class="pr_btn" type="submit" value="确定" id="pr_btn1">
	</form>
</div>

<div class="mask" id="mark2"></div> 
<div class="prompt_form" id="prompt_form2">
	<div class="prompt_title">
		<h2>修改信息</h2>
		<span id="prompt_title2"></span>
	</div>
	<form action="updateQues">
	<div class="prompt_con">
		<p>请输入：</p>
		<input type="text" class="modify_info" name="answer2" id="modify_info2">
	</div>
	<input class="pr_btn" type="submit" value="确定" id="pr_btn2">
	</form>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		
		// var answer1=document.getElementById('answer1');
		// var answer2=document.getElementById('answer2');
		//弹窗1
		$("#info_operation1").click(function prev(){
		   var prev=$(this).prev();
		    window.prev = prev;
		   
		    
			$("#prompt_form1").css("display","block");
			var khp = $(window).height();/* 可见高度 */
			var kwp = $(window).width();/* 可见宽度 */
			var mw = $("#prompt_form1").width();
			var mh = $("#prompt_form1").height();
			var zh=(khp-mh)/2;
			var zw=(kwp-mw)/2;
			$("#prompt_form1").css({"top":zh,"left":zw});			
			$("#mark1").css("display","block");		
		
		})
		$("#prompt_title1").click(function(){
			$("#mark1").css("display","none");
			$("#prompt_form1").css("display","none");
		})
		$("#pr_btn1").click(function(){
			    var val= $("#modify_info1").val();			
				prev.text(val);					
				$("#mask1").css("display","none");
				$("#prompt_form1").css("display","none");
			})

		//弹窗2
		$("#info_operation2").click(function prev(){
		   var prev=$(this).prev();
		    window.prev = prev;
		   
		    
			$("#prompt_form2").css("display","block");
			var khp = $(window).height();/* 可见高度 */
			var kwp = $(window).width();/* 可见宽度 */
			var mw = $("#prompt_form2").width();
			var mh = $("#prompt_form2").height();
			var zh=(khp-mh)/2;
			var zw=(kwp-mw)/2;
			$("#prompt_form2").css({"top":zh,"left":zw});			
			$("#mark2").css("display","block");		
		
		})
		$("#prompt_title2").click(function(){
			$("#mark2").css("display","none");
			$("#prompt_form2").css("display","none");
		})
		$("#pr_btn2").click(function(){
			    var val= $("#modify_info2").val();			
				prev.text(val);					
				$("#mask2").css("display","none");
				$("#prompt_form2").css("display","none");
			})

		
		
		
	})
</script>
</html>