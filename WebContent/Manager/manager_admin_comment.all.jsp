<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/reset.css" />
<link rel="stylesheet" type="text/css" href="style/common.css" />
<link rel="stylesheet" type="text/css" href="style/manager_all.css" />
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js_m/print.js"></script><!-- div id -->
<script type="text/javascript" src="js_m/tableToExcel.js"></script><!-- table id -->
</head>
<body>
<s:action name="Allcomment" ></s:action>
<div class="main main_body">
	<div class="content index_content">
			<div class="position">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p>
    				<a href="#">平台管理</a> <a href="manager_admin_comment.jsp">&gt反馈信息管理</a><a href="#">&gt查看详情</a>  			   
    			</p>
    		</div>
        
        	<!-- 这里开始改变，每个页面的不同从这里开始编写 -->
        	
        	<div class="operation">
        		<ul class="operating">
        			<!-- <li><a href="#"><b></b>删除</a></li> -->
        			<li><a href="#" onclick="method1('blocks');"><b></b>导出</a></li>
        			<li><a href="#" onclick="printdiv('form');"><b></b>打印</a></li>
        			<!-- <li><a href="#"><b></b>批量通过</a></li>
        			<li><a href="#"><b></b>批量删除</a></li> -->
        			
        		</ul>
        		<!-- 详细信息 -->
        			<s:iterator value="#session.allcommentInfo" id="aci" > 
        		<ul class="detailed_list">
        			<li>
        				<i>序号：</i>
        				<div class="detailed_content" id='form'>
						<s:property value="#aci.commentid"/>
						</div>
        			</li>
        			<li>
        				<i>反馈者：</i>
        				<div class="detailed_content">
						<s:property value="#aci.name"/>
						</div>
        			</li><li>
        				<i>反馈时间：</i>
        				<div class="detailed_content">
						<s:property value="#aci.date"/>
						</div>
        			</li><li>
        				<i>反馈类型：</i>
        				<div class="detailed_content">
						<s:property value="#aci.type"/>
						</div>
        			</li><li>
        				<i>标题：</i>
        			<div class="detailed_content">
						<s:property value="#aci.title"/>
						</div>
        			</li>
        			<li>
        				<i>反馈内容：</i>
        				<div class="detailed_content">
						<s:property value="#aci.content"/>
						</div>
        			</li>
        			<li>
        				<i></i>
        				<div class="detailed_content">
        					<input type="button" class="the_editor" id="" value="编 辑">
        					<input type="button" class="re_back" id="" value="返 回">
        				</div>
        			</li>
        		</ul>
        		</s:iterator>
        		</div>
        	</div>
       </div>
</body>
<script type="text/javascript">
					/* 表格行间隔色 */
					$(document).ready(function(){
						odd={"background":"#fff"};
						even={"background":"#f9f9f9"};
						odd_even(".detailed_list",odd,even);
					})
					function odd_even(clas,odd,even){
						$(clas).find("li").each(function(index,element){
							if(index%2==1){
								$(this).css(odd);
							}else{
								$(this).css(even);
							}
						})
					}	
					$(".re_back").click(function(){
						window.history.go(-1);
					})
		<%-- var examine=<%=request.getAttribute("examine")%>; --%>
</script>
</html>