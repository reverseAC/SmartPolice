<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>前端设备审核</title>
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
<s:action name="managerAdminMoveDevice" ></s:action>
  <div class="main_body">
    	<div class="content fr">
    		<div class="position ">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">移动监控管理</a><a href="#">&gt移动监控管理</a></p>
    		</div>
    	<!-- 需要添加的从这里开始 -->
    		<div class="operation ">
        		<ul class="operating">
        			<li><a href="#"><b></b>删除</a></li>
       			    <li><a href="#" onclick="method1('blocks');"><b></b>导出</a></li>
					<li><a href="#" onclick="printdiv('form');"><b></b>打印</a></li>
        	<!-- 		<li><a href="#"><b></b>批量通过</a></li> -->
        			<li><a href="javascript:window.location.href='batchHandlePredevice.action?at=delete&ids='+chk();"><b></b>批量删除</a></li>
        			
        		</ul>
        		<div class="table_header_title"><h3>移动监控管理</h3></div>
        		<div class="search fr">
        			<form action="PredeviceDetailOfManager" method="post">
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
        					<th>设备名称</th>
        					<th>设备类型</th>
        					<th>设备条形码</th>
        					<th>厂商名称</th>
        					<th>状态</th>
        					<th>操作</th>
        				</tr>
        				<tbody>
					<s:iterator value="#session.allMoveDevice" id="pdl" > 
								<tr>
								      <td><input type="checkbox" name="ids" id="ids" value="<s:property value="#pdl.auditid"/>"></td>
									<td><s:property  value="#pdl.auditid"/></td>
			                		<td><s:property value="#pdl.devicename"/></td>
			                		<td><s:property value="#pdl.type"/></td>
			                		<td><s:property value="#pdl.code"/></td>
			                		<td><s:property value="#pdl.name"/></td>
			                		<td><s:property value="#pdl.state"/></td>
			                		<td>
								<ul>
									<li><a href="ManagerRemoveMovedevice.action?id=<s:property value="#pdl.auditid"/>">
									<b class="form_icon_del"></b>删除</a></li>
								<!-- 	<li><a href="#"><b class="form_icon_mod"></b>修改</a></li> -->
									<li><a href="manager_admin_movedevice.all.jsp?id=<s:property value="#pdl.auditid"/>">
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
        					<td>智能类</td>
        					<td>1233-2324-2341</td>
        					
        					<td>张 三</td>
        					<td>已审核</td>
        					<td>
        						<ul>
        							<li><a href="#"><b class="form_icon_del"></b>删除</a></li>
        							<li><a href="#"><b class="form_icon_mod"></b>修改</a></li>
        							<li><a href="manager_admin_movedevice.all.jsp"><b class="form_icon_check"></b>查看</a></li>
        						</ul>
        					</td>
        				</tr>
        				<tr>
        					<td>
        						<input type="checkbox" name="" id="">
        					</td>
        					<td>02</td>
        					<td>智能表</td>
        					<td>智能类</td>
        					
        					<td>1233-2324-2341</td>
        					<td>张 三</td>
        					<td>已审核</td>
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
        					<td>智能类</td>
        					
        					<td>1233-2324-2341</td>
        					<td>张 三</td>
        					<td>已审核</td>
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
        					<td>智能类</td>
        					
        					<td>1233-2324-2341</td>
        					<td>张 三</td>
        					<td>已审核</td>
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
        					<td>智能类</td>
        					
        					<td>1233-2324-2341</td>
        					<td>张 三</td>
        					<td>已审核</td>
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
        					<td>智能类</td>
        					
        					<td>1233-2324-2341</td>
        					<td>张 三</td>
        					<td>已审核</td>
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
    	
    	
    	<!-- 结束 -->
    </div>
    </div>
</body>

</html>