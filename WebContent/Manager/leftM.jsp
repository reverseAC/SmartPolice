<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>leftM</title>
<link rel="stylesheet" type="text/css" href="style/reset.css">
<link rel="stylesheet" type="text/css" href="style/common.css">
<link rel="stylesheet" type="text/css" href="style/leftM.css">
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js_m/leftM.js"></script>
</head>
<body>
<div class="nav">
    	<ul><!--  -->
    		<li class="main_left">
    			<a class="left_list_item" href="#">
    				<span></span>厂商信息管理
    			</a>
				<ul>
					<li><a href="manager_audit_company.jsp" target="Mcontent"><i></i>厂商信息审核</a></li>
					<li><a href="manager_admin_company.jsp" target="Mcontent"><i></i>厂商信息管理</a></li>
					<li><a href="manager_stat_company.jsp" target="Mcontent"><i></i>注册厂商统计</a></li>
				</ul>
    		</li>
            <li class="main_left">
    			<a class="left_list_item" href="#"><span></span>前端设备管理</a>
				<ul>
					<li ><a href="manager_audit_device.jsp"  target="Mcontent"><i></i>前端设备审核</a></li>
					<li><a href="manager_admin_device.jsp"  target="Mcontent"><i></i>前端设备管理</a></li>
					<li ><a href="manager_stat_device.jsp"  target="Mcontent"><i></i>前端设备统计</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>移动监控管理</a>
				<ul>
					<li><a href="manager_audit_movedevice.jsp" target="Mcontent"><i></i>移动监控审核</a></li>
					<li><a href="manager_admin_movedevice.jsp"  target="Mcontent"><i></i>移动监控管理</a></li>
					<li ><a href="manager_stat_movedevice.jsp"  target="Mcontent"><i></i>移动监控统计</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>注册用户管理</a>
				<ul>
					<li><a href="manager_admin_userinfo.jsp"  target="Mcontent"><i></i>用户信息管理</a></li>
					<li ><a href="manager_stat_userInfo.jsp"  target="Mcontent"><i></i>注册用户统计</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>系统信息管理</a>
				<ul>
					<li><a href="manager_stat_logindev.jsp"  target="Mcontent"><i></i>登录设备信息</a></li>
					<li><a href="manager_stat_loginuser.jsp"  target="Mcontent"><i></i>登录用户信息</a></li>
					<li ><a href="sysOperayionInfo.jsp"  target="Mcontent"><i></i>系统运行信息</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>服务器管理</a>
				<ul>
					<li><a href="manager_admin_webservice.jsp" target="Mcontent"><i></i>服务器信息管理</a></li>
					<li><a href="notifyServer.jsp" target="Mcontent"><i></i>增添服务器</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>消息管理</a>
				<ul>
					<li><a href="manager_admin_msg_alarms.jsp" target="Mcontent"><i></i>警报消息管理</a></li>
					<li><a href="manager_admin_msg_chat.jsp" target="Mcontent"><i></i>聊天消息管理</a></li>
					<li><a href="manager_admin_msg_notice.jsp" target="Mcontent"><i></i>通知消息管理</a></li>
				</ul>
    		</li>
    		<li class="main_left">
    			<a class="left_list_item" href="#"><span></span>平台管理</a>
				<ul>
					<li><a href="manager_admin_comment.jsp" target="Mcontent"><i></i>反馈信息管理</a></li>
					<li><a href="notifyIssued.jsp" target="Mcontent"><i></i>平台通知发布</a></li>
					
				</ul>
    		</li>
    	</ul>
    </div>
</body>
</html>