<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sysdaily</title>
    <link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<!--  <link rel="stylesheet" type="text/css" href="style/auditVendorReg.css">-->
	<link rel="stylesheet" type="text/css" href="style/indexRightM.css">	
	<link rel="stylesheet" type="text/css" href="style/sysdaily.css">	
	<link rel="stylesheet" type="text/css" href="style/table.css" />
    <script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="js_m/table.js"></script>

<script type="text/javascript">
        //全局变量
        var numCount;       //数据总数量
        var columnsCounts;  //数据列数量
        var pageCount;      //每页显示的数量
        var pageNum;        //总页数
        var currPageNum ;   //当前页数

        //页面标签变量
        var blockTable;
        var preSpan;
        var firstSpan;
        var nextSpan;
        var lastSpan;
        var pageNumSpan;
        var currPageSpan;



        window.onload=function(){
            //页面标签变量
            blockTable = document.getElementById("blocks");
            preSpan = document.getElementById("spanPre");
            firstSpan = document.getElementById("spanFirst");
            nextSpan = document.getElementById("spanNext");
            lastSpan = document.getElementById("spanLast");
            pageNumSpan = document.getElementById("spanTotalPage");
            currPageSpan = document.getElementById("spanPageNum");

            numCount = document.getElementById("blocks").rows.length - 1;       //取table的行数作为数据总数量（减去标题行1）
          /*  alert(numCount)*/
            columnsCounts = blockTable.rows[0].cells.length;
            pageCount = 5;
            pageNum = parseInt(numCount/pageCount);
            if(0 != numCount%pageCount){
                pageNum += 1;
            }

            firstPage();
        };
    </script>
</head>
<body>
<s:action name="systemLog"></s:action>
  <div class="main_body clearfix">
    	<div class="content">
    		<div class="position">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">系统信息管理</a><a href="sysOperayionInfo.jsp">&gt系统运行信息</a><a href="#">&gt系统日志</a></p>
    		</div>
    	<!-- 需要添加的从这里开始 -->
    	
    	<div class="body_inf">
    	<div class="body_inf_span">
    		<h3>系统日志</h3>
    	</div>
    	<div class="operation fr">
     		<ul class="operating">
     			
     			<li><a href="#">删除</a></li>
     			<li><a href="#">导出</a></li>
     			<li><a href="#">打印</a></li>
     		</ul>
        </div><!--operation fr壳-->
    	<div class="heads"> 
    	   <form class="sr">
    	   <span>
    	        <input type="checkbox"  class="srchoose">
    	        <lable> 只显示在线用户</lable>
    	     </span>
    	    <input type="text" value="" class="srcontent">
    	    <input type="button" value="查询" class="search">   
    	   </form>     
    	</div>
    	
    	<div class="table">
    	    <table id="blocks" class="table table-striped" style="margin-top:25px">
    	         <tr>
    	            <th>选择</th>
     				<th>序号</th>
     				<th>登陆用户</th>
     				<th>访问开始时间</th>
     				<th>访问结束时间</th>     			     				
     				<th>IP地址</th>
     				<th>职员</th>
     				<th>状态</th>
     				<th>操作</th>     				
     			</tr>
     			<tbody>
     				<s:iterator value="#session.systemLog" id="pdl">
	     				<tr> 
						  	<td><input type="checkbox" name="" id=""></td>
							<td><s:property value="#pdl.Imanageid"/></td>
	                		<td><s:property value="#pdl.managerid"/></td>
	                		<td><s:property value="#pdl.starttime"/></td>
	                		<td><s:property value="#pdl.endtime"/></td>
	                		<td><s:property value="#pdl.ipadr"/></td>
	                		<td><s:property value="#pdl.staff"/></td>
	                		<td><s:property value="#pdl.state"/></td>
               				<td>
			     				<ul>
										<li><a href="deleteSystemLog.action?id=<s:property value="#pdl.Imanageid"/>">
										<b class="form_icon_del"></b>删除</a></li>
									
										<li><a href="manager_admin_company.all.jsp?id=<s:property value="#pdl.companyid"/>">
										<b 	class="form_icon_check"></b>查看</a></li> 
								</ul>
							</td>
				        </tr>
			        </s:iterator>
     			</tbody>	  	        	    
    	    </table>    	
    	</div><!--table壳-->
    	
    		 <div id="pagiDiv"  style="width:70%;">
                       <span id="spanFirst">第一页</span>
                       <span id="spanPre">上一页</span>
                       <span id="spanNext">下一页</span>
                       <span id="spanLast">最后一页</span>
                                                                              第<span id="spanPageNum"></span>页/共<span id="spanTotalPage"></span>页
             </div>
    	
        
    
      
    	</div><!--body_inf完 -->
    </div><!-- content fr完 -->
    </div><!-- main_body clearfix完 -->
</body>
</html>