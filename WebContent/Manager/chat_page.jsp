<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天窗口</title>
	<link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<link rel="stylesheet" type="text/css" href="style/auditVendorReg.css">
	<link rel="stylesheet" type="text/css" href="style/chat_page.css">
	<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js_m/drag.js"></script>
	<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>  
</head>
<body>
	<s:action name="ManegerChat" ></s:action>
	<div class="chat_mian" id="chat_mian">
        <div class="chat_left">
        	<div style="position: relative; z-index: 1;">
	            <div class="chat_close" id="chat_close"></div>
	            <div class="chat_group_btn" id="chat_group_btn" ></div>
	        </div>
        	<div class="chat_header">
        		<h3 id="name">张 三@123</h3>
        	</div>
        	<div class="chat_content" >
    	    	<select multiple="multiple" id="rightContent"></select>
        	</div>
        	<div class="chat_input">
       			<ul class="package">
       				<li></li>
       			</ul>
       			<div class="chat_item">
       			<form action="" method="post"  target="nm_iframe">
       				<input type="text" class="text" id="rightText">
       				<ul class="btn_group fr">
       					<li><input type="submit" value="发送" class="btn_china"  onclick="rsend()" autocomplete='off'></li>
       					<li><span class="btn_english">Enter</span></li>
       				</ul>
       				</form>
       				  
       			</div>
        	</div>
        	<iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>  
        </div>
        <div class="chat_right">
        	<div class="manager_header" id="manager_header">
        		<span></span>
        		<h4>管理员名称</h4>
        	</div>
        	<div class="manager_group">
        		<span></span>
        		<span id="add_person"></span>
        	</div>
        	<div class="add_group" id="add_group">
        		<input type="text" class="add_text">
        		<input type="button" class="add_btn" value="添加">
        	</div>
        	<div style="position:absolute; height:290px; width:200px; overflow:auto; ">
        	<ul class="person_group" >
        	<!--  -->
        	<s:iterator value="#session.list" id="pdl">
        	<li >
        			<div class="person_item clearfix" id="id" ondblclick="nameclick(this.id)">
	        			<a href="javascript:void();"  class="n"><span></span><s:property value="#pdl.name"/></a>
	        			<p class="time" ><s:property value="#pdl.time"/></p>
        			</div>
        			<script>
        	document.getElementById("id").id=md5(<s:property value="#pdl.id"/>);
        	</script>
        		</li>
        	</s:iterator>
        		</ul>
        	</div>
        </div>
        
    </div> 
</body>
</html>