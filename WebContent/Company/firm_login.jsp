<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/loginPage.css" />
<script type="text/javascript" src="Company/js/jquery-3.1.1.min.js"></script>
<title>登录智能平台</title>
</head>
<body>
<%
     String username = "";
     String password = "";
     String isChecked="";
     //获取当前站点的所有Cookie
     Cookie[] cookies = request.getCookies();
     if(cookies!=null){
   	  for (int i = 0; i < cookies.length; i++) {
	    	 //对cookies中的数据进行遍历，找到用户名、密码的数据
	         if ("username".equals(cookies[i].getName())) {
	             username = cookies[i].getValue();
	         } else if ("password".equals(cookies[i].getName())) {
	             password = cookies[i].getValue();
	         }
	     }
     }
    //记住密码复选框的勾是否勾上
     if(username!=""&&password!=""){
    	 isChecked="checked";
     }
 %>

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
    	<div class="big_back clearfix">
    		<div class="small_back ">
    			<div class="left_loginlogo fl">
    				<img alt="" src="icon/login_logo.png">
    			</div>
    			<div class="login_line fl"></div>
    			<form class="right_float fl" id="" action="firmLogin"  onsubmit='return cpmlhx();' method='post' name='turn'>
    				<div class="right_logininfo">
    					<h2>登录</h2>
    					<ul>
    						
    						<li>
    							<span>用户名：</span>
    							<input class="" type="text"  id="men" name="men" value="<%=username%>">
    							
    							
    							<!-- <p class="prompt_ dy_info">请登录</p>-->
    							
    							<!-- 动态加载i标签 -->
    						</li>
    						<li>
    							<span>密码：</span>
    							<input class="reset_icon_i" type="password" id="psd" name="psd" value="<%=password%>">
    							
    							
    							<!-- <p class="prompt_dy_info">请登录</p> -->
    						</li>
    						<li>
    							<div class="login_btn clearfix">
    								<div class="remember_password"><input type="checkbox"  id="remember" name="remember"  value="yes" <%=isChecked%>>记住密码</div>
    								
    							    <input type="submit" id="" class="login_button" value="登录">
    							    
    				    		    <p>免费<a href="firm_register.jsp">注册</a>！||<a href="loginFPwd.jsp">忘记密码</a>？</p>
    							</div>
    						</li>
    					</ul>
    				   
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
		<p>你的密码错误！请重新填写</p>
	</div>
	<input class="pr_btn" type="button" value="确定">
</div>



</body>
<script type="text/javascript">
/*<!-- <script type="text/javascript"> -->*/


window.onload=function(){
	var message=document.getElementById("message");
	if(message.innerHTML=="null"){
		message.style.display='none';
	}
	else
		{
		message.style.display='inline';
		}
}

<%-- function(e) { 
	var manager='<%=session.getAttribute("message")%>';
	if(manager=='null'){
	     window.addEventListener('popstate', function () {
	         history.pushState('forward', null, document.URL);
	         window.history.forward(1);
	     });
	
	       window.history.pushState('forward', null, document.URL); //在IE中必须得有这两行
	       window.history.forward(1); 
	}
} --%>

// 	$(".login_button").click(function(){
// 		var use = $("#men").val();
// 		var psd = $("#psd").val();
// 		if(use=="123" && psd=="123"){/* 判断用户输入的账号和密码关系 ，演示如果正确就跳转*/
			
// 			window.document.turn.action="indexC.jsp";
// 			return true;
// 		}else{
// 			$(".prompt_form").css("display","block"); 		
// 				var khp = $(window).height();/* 可见高度 */
// 			var kwp = $(window).width();/* 可见宽度 */
//  			var mw = $(".prompt_form").width();
// 			var mh = $(".prompt_form").height();
// 			var zh=(khp-mh)/2;
//  			var zw=(kwp-mw)/2;
//  			$(".prompt_form").css({"top":zh,"left":zw});			
//  			$(".mask").css("display","block");			
// 			return false;
//  		}			
//  	})
//  	$(".prompt_title span").click(function(){
//  		$(".mask").css("display","none");
//  		$(".prompt_form").css("display","none");
//  	})
//  	$(".pr_btn").click(function(){
//  		$(".mask").css("display","none");
//  		$(".prompt_form").css("display","none");
//  	})
</script>
</html>