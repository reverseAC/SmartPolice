<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/adminDeviceDetailed.css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div class="main">
        	<div class="position">
        		<h3>当前位置：</h3>
        		<p><a href="#">厂商首页</a><a href="runInfo.jsp" target="Rcontent">&gt平台运行信息</a><a href="runInfoDeinfo.jsp" target="Rcontent">&gt平台详细信息</a><a href="#">&gt详细信息</a></p>
        	</div>
        	<!-- 这里开始改变，每个页面的不同从这里开始编写 -->
        	
        	<div class="operation">
        		<ul class="operating">
        			<li><a href="#">删除</a></li>
        			<li><a href="#">导出</a></li>
        			<li><a href="#">打印</a></li>
        		</ul>
        		
        		<!-- 详细信息 -->
        		<ul class="detailed_list">
        			<li>
        				<i>产品名称：</i>
        				<div class="detailed_content">智能家居</div>
        			</li>
        			<li>
        				<i>在线人数：</i>
        				<div class="detailed_content">23424</div>
        			</li><li>
        				<i>总注册数：</i>
        				<div class="detailed_content">54355</div>
        			</li>
        			<li>
        				<i></i>
        				<div class="detailed_content">
        					<input type="button" class="the_editor" id="the_editor" value="编 辑">
        					<input type="button" class="re_back" id="re_back" value="返 回">
        				</div>
        			</li>
        		</ul>
        		</div>
        	</div>
</body>
<script type="text/javascript">
	/* 表格行间隔色 */
	$(document).ready(function(){
		odd={"background":"#fff"};
		even={"background":"#f9f9f9"};
		odd_even(".detailed_list",odd,even);
	});
	//返回上一页
	$(".re_back").click(function(){
		window.location.href="runInfoDeinfo.jsp";
	});
	function odd_even(clas,odd,even){
		$(clas).find("li").each(function(index,element){
			if(index%2==1){
				$(this).css(odd);
			}else{
				$(this).css(even);
			}
		})
	}
	
</script>
</html>