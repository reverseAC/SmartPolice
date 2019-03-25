<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>前端设备统计</title>
    <link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<link rel="stylesheet" type="text/css" href="style/indexRightM.css">	
    <link rel="stylesheet" type="text/css" href="style/statVendorReg.css"> 
	<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js_m/echarts.js"></script>
    <script type="text/javascript" src="js_m/zhu-m.js"></script>
</head>
<body>
<% request.setAttribute("table","device");
   request.setAttribute("type",1);%>
	<s:action name="obtain"></s:action>
	<%
      	Integer unexamine=(Integer)request.getAttribute("unexamine");
		Integer pass=(Integer)request.getAttribute("pass");
		Integer notPass=(Integer)request.getAttribute("notPass");
		Integer frozen=(Integer)request.getAttribute("frozen");
      	System.out.println("值传过来了...");
    %>
   <input type="hidden" name="not_pass " value=<%=notPass %> id="notPass"> <!--未通过  -->
   <input type="hidden" name="unexamine" value=<%=unexamine %> id="unexamine"> <!--未审核  -->
   <input type="hidden" name="pass" value=<%=pass %> id="pass">  <!--通过  -->
   <input type="hidden" name="frozen" value=<%=frozen %> id="frozen"> <!-- 冻结 -->
  <div class="main_body clearfix">
    	<div class="content">
    		<div class="position">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">前端设备管理</a><a href="#">&gt前端设备统计</a></p>               
    		</div>
            <div>
           
            <ul class="putin">      
                 <li>
                   <!-- <img src="icon_m/daochu.png"/>  -->
                   <span class="daochu daochu_dayin"></span>
                    <a  href="#">导出</a>
                 </li>  
                 <li> 
                     <!-- <img src="icon_m/dayin.png">  -->
                      <span class="dayin daochu_dayin"></span>           
                     <a  href="#">打印</a>
                 </li>
            </ul>
      <div class="stat_different">
            <div class="account">
             <span>前端设备数据统计</span>
             <span class="search search_two">
             <form  name="input" action="" method="get">
             <input type="text" name=" " class="inputtext" />
             <input type="submit"  class="submit"  value="搜索" />
             </form>
             </span>
           </div> 
            
              <div id="notice"  class="notice">
                   <div id="notice_tit" class="notice_tit">
                   
                       <ul id="tab_t">
                           <li class="select">柱状图</li>
                           <li class="select1">饼状图</li>
                       </ul>
                   </div>
                   
                   <div id="tab_c" class="notice_noc" >
                         <div  id="containerz" style="width: 600px; height: 390px; margin: 0px auto; "></div>
                         <div class="active0" id="containerb" style="width: 600px; height: 390px; margin: 0px auto;display:none;"></div>
                   </div>
              </div>  
              </div>
       </div>    
    </div>
</div>
</body>
</script>
</html>