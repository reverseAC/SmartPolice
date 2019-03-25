<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>indexRightC</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/indexRightC.css" />
</head>
<body>
<!-- <iframe src="indexC.jsp" style="height:100%;width%;border-width:0px;"></iframe> -->
  <div class="main">
        	<div class="position">
        		<h3>当前位置：</h3>
        		<p><a href="#">厂商首页</a></p>
        	</div>
            <div class="conversion_different">
        	<div class="info_details">
        		<h3>消息详情</h3>
        		<ul>
        			<li><a href="#">提交未审核设备数（<%=session.getAttribute("device_unpass")%>）</a></li>
        		    <li><a href="#">提交未审核软件数（<%=session.getAttribute("soft_unpass")%>）</a></li>
        		</ul>
        	</div>
        	<div class="dynamic_platform fl">
        		<div class="dynamic_line">
        			<h3>平台动态</h3>
        			<ul>
        				<li><a href="#">未读信息</a></li>
        		    	<li><a href="#">（<%=session.getAttribute("comment")%>）</a></li>
        			</ul>
        		</div>
        	</div>
        	<div class="affiliate fr">
        		<div class="dynamic_line">
        			<h3>加盟厂商</h3>
        			<ul>
        				<li><a href="#">厂商相关信息（1）</a></li>
        			</ul>
        		</div>
        	</div>
        	
        </div>
      </div>
</body>
</html>