<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">
	
    <link rel="stylesheet" type="text/css" href="style/auditVendorReg.css">
<link rel="stylesheet" type="text/css" href="style/table.css" />
    <script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="js_m/table.js"></script>
    <script type="text/javascript" src="js_m/print.js"></script><!-- div id -->
	<script type="text/javascript" src="js_m/tableToExcel.js"></script><!-- table id -->

<script type="text/javascript">
      
    </script>
</head>
<body>
<s:action name="allServers" ></s:action>
<div class="main_body">
    	<div class="content">
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">服务器管理</a><a href="#">&gt服务器信息管理</a></p>
    		</div>
    	<!-- 需要添加的从这里开始 -->
    		<div class="operation ">
        		<ul class="operating">
        		<!-- 	<li><a href="#"><b></b>删除</a></li> -->
        				<li><a href="#" onclick="method1('blocks');"><b></b>导出</a></li>
						<li><a href="#" onclick="printdiv('form');"><b></b>打印</a></li>
        		<!-- 	<li><a href="#"><b></b>批量通过</a></li> -->
        			<li><a href="javascript:window.location.href='batchHandleWebservice.action?ids='+chk();"><b></b>批量删除</a></li>
        			
        		</ul>
        		<div class="table_header_title"><h3>服务器管理信息</h3></div>
        		<div class="search fr">
        			<form action="serverInfo" method="post">
        				<input type="text" name="id" class="search_box">
        				<input type="button" value="搜索" class="button">
        			</form>       		
        		</div>
        		<!-- 表格 -->
        		<div class="form" id="form">
        			<table id="blocks" class="table table-striped" style="margin-top:25px">
        				<tr>
        					<th>选择</th>
        					<th>序号</th>
        					<th>服务器类型</th>
        					<th>内存大小</th>
        					<th>CPU型号</th>
        					
        				    <th>管理者</th>
        					<th>操作</th>
        				</tr>
        				<tbody>
					<s:iterator value="#session.allServers" id="pdl" > 
								<tr>
								   <td><input type="checkbox" name="ids" id="ids" value="<s:property value="#pdl.serverid"/>"></td>
									<td><s:property  value="#pdl.serverid"/></td>
			                		<td><s:property value="#pdl.type"/></td>
			                		<td><s:property value="#pdl.memory"/></td>
			                		<td><s:property value="#pdl.CPU"/></td>
			                		<td><s:property value="#pdl.name"/></td>
			                		
			                		<td>
								<ul>
									<li><a href="removeServer.action?id=<s:property value="#pdl.serverid"/>">
									<b class="form_icon_del"></b>删除</a></li>
								<!-- 	<li><a href="#"><b class="form_icon_mod"></b>修改</a></li> -->
									<li><a href="manager_admin_webservice.all.jsp?id=<s:property value="#pdl.serverid"/>">
										<b 	class="form_icon_check"></b>查看</a></li> 
								</ul>
							</td>
			                	</tr>
			              </s:iterator> 
			        
			                </tbody>
        				<!-- <tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>01</td>
        					<td>智能表</td>
        					<td>2014.05.19</td>
        					<td>2016.05.19</td>
        					
        					<td>1</td>
        					
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="manager_admin_webservice.all.jsp"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        				<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>02</td>
        					<td>智能表</td>
        					<td>2014.05.10</td>
        					<td>2016.05.19</td>
        				   
        					<td>1</td>
        					
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="#"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        				<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>03</td>
        					<td>智能表</td>
        					<td>2014.05.18</td>
        					<td>2016.05.19</td>
        					
        					<td>1</td>
        					
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="#"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        				<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>04</td>
        					<td>智能表</td>
        					<td>2014.05.19</td>
        					<td>2016.05.19</td>
        					
        					<td>1</td>
        					
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="#"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        				<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>05</td>
        					<td>智能表</td>
        					<td>2014.05.09</td>
        					<td>2016.05.19</td>
        					
        					<td>1</td>
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="#"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        					<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>06</td>
        					<td>智能表</td>
        					<td>2014.05.09</td>
        					<td>2016.05.19</td>
        					
        					<td>1</td>
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="#"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr> -->
        			</table>
        	        <div id="pagiDiv"  style="width:70%;">
                       <span id="spanFirst">第一页</span>
                       <span id="spanPre">上一页</span>
                       <span id="spanNext">下一页</span>
                       <span id="spanLast">最后一页</span>
                                                                              第<span id="spanPageNum"></span>页/共<span id="spanTotalPage"></span>页
                    </div>
        		</div>
        	</div>
        </div>
    </div>
</body>

</html>