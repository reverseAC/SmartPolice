<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>headerM</title>
<link rel="stylesheet" type="text/css" href="style/reset.css">
<link rel="stylesheet" type="text/css" href="style/common.css">
<link rel="stylesheet" type="text/css" href="style/headerM.css">
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
</head>
<body>

<div class="head">
    	<div class="logo"></div>
    	<div class="left_title fr">
    		<ul>
    			<li><span class="help"></span><a href="#">帮助</a></li>
   		<%--  <li class="exit"><span class="out"></span><a href="javascript:parent.window.location.href='loginPageM.jsp';">退出</a></li> --%>
   		 <li class="exit"><span class="out"></span><a href="javascript:;" onclick="tuichu()">退出</a></li>
    		</ul>
    	</div>
    </div>
    <div class="menu_time clearfix">
    	<div class="left_time fl">
    		<p>您上次登录的时间是：<span  id=""><s:property value="#session.LastAccessedTime"/></span></p>
        	<p>当前用户：<a href="#"><s:property value="#session.username"/></a></p>
        	<!-- <p>字号：<a href="#">大</a><a href="#">中</a><a href="#">小</a></p> -->
    	</div>
    	<div class="menu fr">
    	    	<ul class="fl">
    	    		<li><a href="indexRightM.jsp" target="Mcontent"><i></i>返回首页</a></li>
    	    		<li><a href="personalCenter.jsp" target="Mcontent"><i></i>个人中心</a></li>
    	    		<li  class="shua" ><a href="javascript:window.parent.Mcontent.location.reload();"><i></i>刷新</a></li>
    	    		<li><a href="systemSettings.jsp" target="Mcontent"><i></i>设置</a></li>
    	    		<li ><a href="chat_page.jsp" target="Mcontent"><i></i>聊天</a></li>
    			</ul>
    		
    	 </div> 		
    </div>
    <div class="blue_line"></div>
</body>
<script type="text/javascript">
/* 获取左菜单项，点击header时，能清除左侧菜单选中状态 */
var lchioce=$(self.parent.frames["Lcontent"].document).find(".main_left li a i");
var chat=$(self.parent.frames["Mcontent"].document).find("#main_body");
$(".menu li").click(function(){
	/* alert(lchioce.length) */
	lchioce.css("background-image","url('icon_m/nav_two1.png')");
	
});

/* $(".left_title li.exit").click(function(){
	if (confirm("您确定要退出吗？")) { 
		window.open('loginPageM.jsp');
		} else {} 
	
}) */
 
<%  
//获取正确的路径 
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
function tuichu() {
	parent.window.location.href= "<%=basePath%>/manager-logout.action"; 
		}  


</script>
</html>