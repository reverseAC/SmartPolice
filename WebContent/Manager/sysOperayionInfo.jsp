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
<!-- <link rel="stylesheet" type="text/css" href="style/auditVendorReg.css"> -->
<link rel="stylesheet" type="text/css" href="style/indexRightM.css">
<link rel="stylesheet" type="text/css" href="style/sysOperationInfo.css">
<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
<script type="text/javascript " src="js_m/tuline.js"></script>
<script type="text/javascript " src="js_m/Chart.js"></script>
</head>
<body style="overflow-x: hidden;">

	<div class="main_body clearfix">
		<div class="content">
			<div class="position">
				<span></span>
				<h3>当前位置：</h3>
				<p>
					<a href="#">系统信息管理</a><a href="#">&gt系统运行信息</a>
				</p>
			</div>
			<!-- 需要添加的从这里开始 -->

			<div class="body_inf">
				<div class="body_inf_span">
					<h3>系统信息统计</h3>
				</div>
				<div class="body_inf_ul">
					<ul class="body_ul_color padd">
						<li><p>
								当前在线人数：<a href="#"> <s:property value="#session.count" />
								</a>人
							</p></li>
						<li><p>
								界面用户数：<a href="#"><s:property
										value="#session.SysStatInfo.getViewusernum()" /></a>人
							</p></li>
						<li><p>
								登录APP数：<a href="#"><s:property
										value="#session.SysStatInfo.getLoginusernum()" /></a>人
							</p></li>
					</ul>
					<ul>
						<li style="color: #00679a;"><p>
								打开文件数：<a href="#"><s:property
										value="#session.SysStatInfo.getOpenFileNum()" /></a>人
							</p></li>
						<li style="color: #00679a;"><p>
								设备登录数：<a href="#"><s:property
										value="#session.SysStatInfo.getLogindevnum()" /></a>人
							</p></li>
						<li>同时达到最大登录设备数：<a href="#"> <s:property
									value="#session.SysCfgInfo.getMaxLoginDevices()" />
						</a>个
						</li>
					</ul>
					<ul>
						<li>同时达到最大登录用户数：<a href="#"> <s:property
									value="#session.SysCfgInfo.getMaxLoginUsers()" />
						</a>人
						</li>
						<li>得到报文数：<a href="#"><s:property
									value="#session.SysStatInfo.getReceicvenum()" /></a>条
						</li>
						<li>发送报文数：<a href="#"><s:property
									value="#session.SysStatInfo.getSendnum()" /></a>条
						</li>
					</ul>
					<p class="body_inf_ul_4">
						<a href="sysdaily.jsp" target="Mcontent">系统日志 &gt&gt</a>
					</p>
				</div>
				<!-- 新添加内容 -->
				<div style="width: 80%; height: 450px;">
					<div id="body_left"
						style="height: 84%; background: #efefef; float: left;">
						<div id="one" class="num" onClick="show(1)">
							<span>配置信息</span>
						</div>
						<div id="two" class="num" onClick="show(2)">
							<span>cpu</span>
						</div>
						<div id="three" class="num" onClick="show(3)">
							<span>网络速度</span>
						</div>
						<div id="four" class="num" onClick="show(4)">
							<span>磁盘利用率</span>
						</div>
						<div id="five" class="num" onClick="show(5)">
							<span>网络设备</span>
						</div>
						<div id="six" class="num" onClick="show(6)">
							<span>内存</span>
						</div>
					</div>

					<div id="body_right"
						style="position: absolute; font-size: 16px; left: 20em; width: 58.25em; height: 28.125em;">
						<div id="body_show_1"
							style="width: 100%; height: 100%; display: block; line-height: 2">
							<div class="body_right_bottom">
								<ul>


									<li><span class="body_right_bottom_lspan">用户名:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.username" /></span></li>
									<li><span class="body_right_bottom_lspan">本地IP地址:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.ip" /></span></li>
									<li><span class="body_right_bottom_lspan">本地主机名:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.locahost" /></span></li>
									<li><span class="body_right_bottom_lspan">计算机名:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.computername" /></span></li>
									<li><span class="body_right_bottom_lspan">操作系统:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.os" /></span></li>
									<li><span class="body_right_bottom_lspan">系统名称:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.osName" /></span></li>
									<li><span class="body_right_bottom_lspan">系统构架:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.arch" /></span></li>
									<li><span class="body_right_bottom_lspan">系统版本:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.osversion" /></span></li>

								</ul>
							</div>
						</div>
						<div id="body_show_2"
							style="width: 100%; height: 100%; display: none;">
							<!-- 画图 -->
							<div style="margin-left: 10%;">
								<span>cpu名称</span>
							</div>
							<div style="margin-left: 10%; width: 80%; height: 60%;">
								<li style="margin-left: 28%;">利用率图像</li>
								<div style="height: 250px; width: 400px">
									<div>
										<span style="font-size: 10px; padding-left: 10px;">利用率(百分比)</span>
									</div>
									<canvas id="chartjs-0" width="400px" height="250px"></canvas>
								</div>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">cpu总量MHZ:</span><span
										class="body_right_bottom_rspan"  ><s:property
												value="#session.SysStatInfo.cpuAll" /></span></li>
									<li><span class="body_right_bottom_lspan">cpu系统使用率:</span><span
										class="body_right_bottom_rspan"  id="cpuSystemUse"><s:property
												value="#session.SysStatInfo.cpuSysUsed" /></span></li>
									<li><span class="body_right_bottom_lspan">cpu用户使用率:</span><span
										class="body_right_bottom_rspan"  id="cpuPersonUse"><s:property
												value="#session.SysStatInfo.cpuUserUsed" /></span></li>
									<li><span class="body_right_bottom_lspan" >cpu总使用率:</span><span
										class="body_right_bottom_rspan" id="cpuUseAll"> <s:property                     
												value="#session.SysStatInfo.cpuUsedAll" /> </span></li>
									<li><span class="body_right_bottom_lspan">cpu类别:</span><span
										class="body_right_bottom_rspan"><s:property
												value="#session.SysStatInfo.cpuType" /></span></li>
								</ul>
							</div>
						</div>


						<div id="body_show_3"
							style="width: 100%; height: 100%; display: none;">
							<div style="margin-left: 10%;">
								<div>
									<span>网络方式</span>
								</div>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>wifi</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>以太网</span>
							</div>
							<!-- WiFi方面 -->

							<div style="float: left">
								<div style="margin-left: 10%; width: 80%; height: 60%;">
									<li style="text-align: center;">wifi速度</li>
									<div style="height: 15.625em; width: 18.75em">
										<div>
											<span style="font-size: 10px; padding-left: 10px;">利用率(百分比)</span>
										</div>
										<canvas id="chartjs-3" width="18.75em" height="15.625em"></canvas>
									</div>
								</div>
							</div>
							<!-- 以太网方面 -->
							<div style="float: left">
								<div style="margin-left: 10%; width: 80%; height: 60%;">
									<li style="text-align: center;">以太网速度</li>
									<div style="height: 15.625em; width: 18.75em">
										<div>
											<span style="font-size: 10px; padding-left: 10px;">利用率(百分比)</span>
										</div>
										<canvas id="chartjs-4" width="18.75em" height="15.625em"></canvas>
									</div>
								</div>
							</div>
						</div>


						<div id="body_show_4"
							style="width: 100%; height: 100%; display: none;">
							<div style="margin-left: 10%;">
								<span>磁盘名称</span>
							</div>
							<div style="margin-left: 10%; width: 80%; height: 60%;">
								<li style="margin-left: 28%;">利用率图像</li>
								<div style="height: 250px; width: 400px">
									<div>
										<span style="font-size: 10px; padding-left: 10px;">利用率(百分比)</span>
									</div>
									<canvas id="chartjs-2" width="400px" height="250px"></canvas>
								</div>
							</div>
						</div>
						<div id="body_show_5"
							style="width: 100%; height: 100%; display: none; line-height: 1.6">
							<div style="position: relative; top: 3em; margin-left: 10%;">
								<span>网络设备名</span><span
									style="display: inline-block; margin-left: 10%;">enth1</span>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">IP地址:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
									<li><span class="body_right_bottom_lspan">子网掩码:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
								</ul>
							</div>

							<div style="position: relative; top: 3em; margin-left: 10%;">
								<span>网络设备名</span><span
									style="display: inline-block; margin-left: 10%;">enth2</span>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">IP地址:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
									<li><span class="body_right_bottom_lspan">子网掩码:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
								</ul>
							</div>

							<div style="position: relative; top: 3em; margin-left: 10%;">
								<span>网络设备名</span><span
									style="display: inline-block; margin-left: 10%;">enth3</span>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">IP地址:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
									<li><span class="body_right_bottom_lspan">子网掩码:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
								</ul>
							</div>


							<div style="position: relative; top: 3em; margin-left: 10%;">
								<span>网络设备名</span><span
									style="display: inline-block; margin-left: 10%;">enth4</span>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">IP地址:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
									<li><span class="body_right_bottom_lspan">子网掩码:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
								</ul>
							</div>

							<div style="position: relative; top: 3em; margin-left: 10%;">
								<span>网络设备名</span><span
									style="display: inline-block; margin-left: 10%;">enth5</span>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">IP地址:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
									<li><span class="body_right_bottom_lspan">子网掩码:</span><span
										class="body_right_bottom_rspan">0.0.0.0</span></li>
								</ul>
							</div>

						</div>
						<div id="body_show_6"
							style="width: 100%; height: 100%; display: none;">
							<!-- 画图 -->
							<div style="margin-left: 10%;">
								<span>内存</span><span>内存类别</span>
							</div>
							<div style="margin-left: 10%; width: 80%; height: 60%;">
								<li style="margin-left: 28%;">利用率图像</li>
								<div style="height: 250px; width: 400px">
									<div>
										<span style="font-size: 10px; padding-left: 10px;">利用率(百分比)</span>
									</div>
									<canvas id="chartjs-1" width="400px" height="250px"></canvas>
								</div>
							</div>
							<div class="body_right_bottom">
								<ul>
									<li><span class="body_right_bottom_lspan">内存总量:</span><span
										class="body_right_bottom_rspan"  id="memAll"><s:property
												value="#session.SysStatInfo.memAll" />K</span></li>
									<li><span class="body_right_bottom_lspan">内存使用量:</span><span
										class="body_right_bottom_rspan"  id="memused"><s:property
												value="#session.SysStatInfo.memused" />K</span></li>
									<li><span class="body_right_bottom_lspan">内存空闲量:</span><span
										class="body_right_bottom_rspan" id="memFree"><s:property
												value="#session.SysStatInfo.memFree" />K</span></li>
									<li><span class="body_right_bottom_lspan" id="">内存使用百分比:</span> <span
										class="body_right_bottom_rspan" id="mem"><s:property
												value="#session.SysStatInfo.mem" />%</span> </li>

								</ul> 
							</div>
						</div>
					</div>
				</div>
	
				<iframe name="frame" id="frame" style="display:none" src="sysOperayionInfo.jsp"></iframe>
				<script>
					
					//********************************************************************************
					
					window.onload = function() {
						
						setInterval(
								function() {
									//alert($("#frame").contents().find("#usetwo").text());      
									//①①①①①①①①①①①①①①①①①-------CPU
									//cpuSystemUse使用率
									var cpuSystemUse=$("#frame").contents().find("#cpuSystemUse").text(); //读取cpu系统使用率的值
									$("#cpuSystemUse").text(cpuSystemUse);        //改变页面数据
									
									//cpuPersonUer使用率
									var cpuPersonUse=$("#frame").contents().find("#cpuPersonUse").text(); //读取cpu系统使用率的值
									$("#cpuPersonUse").text(cpuPersonUse);        //改变页面数据
									
									//cpu总使用率
									var cpuUseAll=$("#frame").contents().find("#cpuUseAll").text(); //读取cpu总使用率的值，0.5s读取一次attr("value")
									$("#cpuUseAll").text(cpuUseAll);        //改变页面数据
									cpuUseAll=cpuUseAll.split("%");																//读取数值
									cpu(parseFloat(cpuUseAll[0]));														//传入cpu图标
									
									//②②②②②②②②②②②②②②②②②--------内存
									//内存总量 memAll 
									var memAll=$("#frame").contents().find("#memAll").text(); 
									$("#memAll").text(memAll);        
						
									//内存使用量 memused 
									var memused=$("#frame").contents().find("#memused").text(); 
									$("#memused").text(memused);    
									
									//内存空闲量 memFree
									var memFree=$("#frame").contents().find("#memFree").text(); 
									$("#memFree").text(memFree);        
									
									//内存使用百分比mem
									var mem=$("#frame").contents().find("#mem").text(); 
									$("#mem").text(mem);        
									mem=mem.split("%");																//读取数值
									neicun(parseFloat(mem[0]));	
									
									//③③③③③③③③③③③③③③③③③---------假数据
									var sum1 = Math.random() * 100;
									var sum4 = Math.random() * 100;
									chipan(sum1);
									inter(sum4);
									
									
									frame.window.location.reload();                                   //刷新框架
								
								}, 1000);
					}	  
				</script>

			</div>
			<!--body_inf完 -->
		</div>
		<!-- content fr完 -->
	</div>
	<!-- main_body clearfix完 -->
</body>
</html>