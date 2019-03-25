<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>indexRightM</title>
<link rel="stylesheet" type="text/css" href="style/reset.css">
<link rel="stylesheet" type="text/css" href="style/common.css">
<link rel="stylesheet" type="text/css" href="style/indexRightM.css">
<link rel="stylesheet" type="text/css" href="style/chat_page.css">
<script type="text/javascript" src="js_m/jquery-3.1.1.min.js"></script>
</head>
<body>

<s:action name="managerAuditCompany" ></s:action>
<s:action name="managerAuditDevice" ></s:action>
<s:action name="managerAuditMoveDevice" ></s:action>
<s:action name="FindAddDev" ></s:action>
<s:action name="FindAddUser" ></s:action>
	<div class="main_body clearfix">
		<div class="index_content">
			<div class="position">
				<span></span>
				<h3>当前位置：</h3>
				<p>
					<a href="#">首页</a>
				</p>
			</div>
			<!-- 需要添加的从这里开始 -->
			<div class="manager_differnt margin_top_1">
				<div class="main_content">
					<div class="content_head">
						<h3>
							<b class="title1"></b><a href="#"><s:property value="#session.username"/></a>,欢迎您登录智能监控管理平台！
						</h3>
					</div>
					<div class="content_lists">
						<h4>
							<b></b>您有待处理的事项：
						</h4>
						<p>
							<i class="list-icon"></i>您有<a href="#">(<s:property  value="#session.auditCompany.size()"/>)</a>个厂商信息待审核，<a
								href="manager_audit_company.jsp">点击查看</a>
						</p>
						<p>
							<i class="list-icon"></i>您有<a href="#">(<s:property  value="#session.auditDevice.size()"/>)</a>个前端设备待审核，<a
								href="manager_audit_device.jsp">点击查看</a>
						</p>
						<p>
							<i class="list-icon"></i>您有<a href="#">(<s:property  value="#session.auditMoveDevice.size()"/>)</a>个移动监控待审核，<a
								href="manager_audit_movedevice.jsp">点击查看</a>
						</p>
					</div>
				</div>

				<div class="main_content">
					<div class="content_head">
						<h3>
							<b class="title2"></b><a href="#"></a>平台信息
						</h3>
					</div>
					<div class="content_lists clearfix">
						<ul>
							<li><b class="online_num"></b>
								<div class="fl">
									<h4>在线人数</h4>
									<p>
										<a href=""><s:property value="#session.count"/></a>
									</p>
									<p>
										<a href="">查看详情 &gt &gt</a>
									</p>
								</div></li>
							<li><b class="add_num"></b>
								<div class="fl">
									<h4>新增人数</h4>
									<p>
										<a href="">本月：<s:property  value="#session.addUserNum"/></a>
									</p>
									<p>
										<a href="">查看详情 &gt &gt</a>
									</p>
								</div></li>
							<li><b class="add_equip"></b>
								<div class="fl">
									<h4>新增设备</h4>
									<p>
										<a href="">本月：<s:property  value="#session.addDevNum"/></a>
									</p>
									<p>
										<a href="">查看详情 &gt &gt</a>
									</p>
								</div></li>
						</ul>
					</div>
				</div>
				<div class="main_content">
					<div class="content_head">
						<h3>
							<b class="title3"></b><a href="#"></a>关于版本
						</h3>
					</div>
					<div class="content_lists">
						<h4>
							<b></b>您当前的版本：1.0.0（暂无更新）
						</h4>
						<p>想了解更多，请登录：</p>
						<p>
							<a href="#">监控系统网：www.jiankong.com</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div id="main_body"></div>
	</div>
</body>
<script type="text/javascript">
/* $("#main_body").load("chat_page.jsp div.chat_mian",function(a,status,c){
	if(status=="error"){
		$(".chat_mian").text("加载失败")
	}
	$.getScript("js_m/drag.js");
}) */
</script>
</html>