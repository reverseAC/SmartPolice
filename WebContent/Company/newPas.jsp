<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/loginFPwd.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<!-- <script type="text/javascript" src="js/index.js"></script> -->
<!-- <script type="text/javascript" src="js/index.js"></script> -->
</head>
<body>
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
    		   <!--  class="reset_pwd2" -->
    		   
    		    <form action="save-new-pwd" method="post" name="form1">
    				<div class="login_list">
    					<label>   					
    						<span>新密码：</span>
    						<input type="password"  class="text" name="Pas"/>   					
    					</label>
    					<p class="msg_f"><i class="ati_f"></i>请输入新密码</p>
    				</div>
    			<div class="login_list">
    				<label>   					
    					<span>确认密码：</span>
    					<input type="password"  class="text" name="resPas"/>
    								
    				</label>
    				<p class="msg_f"><i class="ati_f"></i>两次密码不一样，请重新输入</p>
    			</div>
    			<div class="login_list">
    				<label>   					
    					<span></span>
    					<input type="submit" value="提交" class="text1 btn pwdBtn" disabled="disabled" id="sub_pas"/> 
    					<a href="loginFPwd.jsp">
    					<input type="button" value="返回" class="text1 btn pwdBtnBack"/>  
    					</a>					
    				</label>
    			<h6><s:actionerror /></h6>
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
					<input class="pr_btn" type="submit" value="确定">
				</div> 	
    		</form>
    		
    		</div>
    	</div>
    </div>
</body>
<script type="text/javascript">
	window.onload=function(){
		var aInput=document.getElementsByTagName('input');
		var aP=document.getElementsByClassName('msg_f');
		var re_pwd=aInput[0];
		var re_pwd1= aInput[1];
		re_pwd.onfocus=function(){
			aP[0].style.display="block";
		}
		re_pwd.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[0].innerHTML='密码不能为空'
		    }else{
		    	aP[0].innerHTML='ok'
		    }				
		}
				
		re_pwd.onfocus=function(){
			aP[0].style.display="block";
			aP[0].innerHTML='请使用数字或者字母组合'
		}
		re_pwd.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[0].innerHTML='新密码不能为空'
		    }else{
		    	aP[0].innerHTML='ok'
		    }
		}
		re_pwd1.onfocus=function(){
			aP[1].style.display="block";
			aP[1].innerHTML='重新输入密码'
		}
		re_pwd1.onblur=function(){
			if(this.value!=re_pwd.value){
				aP[1].innerHTML='两次密码不一样，请重新输入'
			}else{
				aP[1].innerHTML='ok'
				document.getElementById("sub_pas").disabled = false;
			}
		
		}
			
	 } 
	/* 提示框弹出（以后这里需要添加条件判断才能成立） */
	$(".pwdBtn").click(function(){
		$(".prompt_form").css("display","block");
		var khp = $(window).height();/* 可见高度 */
		var kwp = $(window).width();/* 可见宽度 */
		var mw = $(".prompt_form").width();
		var mh = $(".prompt_form").height();
		var zh=(khp-mh)/2;
		var zw=(kwp-mw)/2;
		$(".prompt_form").css({"top":zh,"left":zw});			
		$(".mask").css("display","block");
		$(".prompt_con").html('<p>修改成功!</p>')
		return false;
	})
	$(".prompt_title span").click(function(){
		$(".mask").css("display","none");
		$(".prompt_form").css("display","none");
	})
	$(".pr_btn").click(function(){
		$(".mask").css("display","none");
		$(".prompt_form").css("display","none");
	})

</script>
</html>