<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>indexC</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="Company/js/jquery-3.1.1.min.js"></script>
</head>

<frameset rows="134,*" border="0">
	<!-- top -->
	<frame src="firm_admin_header.jsp" name="Hcontent"></frame>
	<frameset cols="246,*" border="0" >
		<!-- left -->
		<frame src="leftC.jsp" scrolling="NO" name="Lcontent"></frame>
		<!-- right -->
		<frame src="indexRightC.jsp" name="Rcontent" scrolling="Auto"></frame>
	</frameset>
</frameset>
</html>