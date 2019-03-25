<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传软件信息</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/issueDevice.css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div class="main">
   	<div class="position">
   		<h3>当前位置：</h3>
   		<p><a href="#">软件管理</a><a href="#">&gt上传软件信息</a></p>
   	</div>		
    <!-- 这里开始改变，每个页面的不同从这里开始编写 -->
    <div class="conversion_different"> 
    	<div class="upload_info fr">
    		<div class="list_margin">
    		<form action="f_uploadSoftinfo"  method="post" enctype="multipart/form-data">
    			<ul class="upload_list">
    			<li class="upload_one">
    				<i>软件名称：</i>
    				<input type="text" id="device_name" class="same" name="name" value="">
    				<!-- <span class="prompt"></span> -->
    				<strong>*</strong>
    			</li>
    			<li class="upload_one">
    				<i>软件序列号：</i>
    				<input type="text" id="device_num" class="same" name="serial" value="">
    				<!-- <span class="prompt"></span> -->
    				<strong>*</strong>
    			</li>
    			<li class="upload_one">
    				<i>版本：</i>
    				<input type="text" id="device_num" class="same" name="version" value="">
    				<!-- <span class="prompt"></span> -->
    				<strong>*</strong>
    			</li>
    			<li class="upload_one">
    				<i>软件类型：</i>
    				<select name="type" id="device_type" class="same select">
    					<option value="0">前端设备</option>
    					<option value="1">监控APP</option>
    					<option value="2">管理APP</option>
    					<option value="3">插件</option>
    				</select>
    				<!-- <span class="prompt"></span> -->
    				<strong>*</strong>
    			</li>
    			<li class="upload_one">
    				<i>上传软件：</i>
    				<input type="file" name="file" id="uploadings" class="same none_line" onchange="" class="upload_file"/>
    				<!-- <span class="prompt"></span> -->
    				<strong>*</strong>
    			</li>
    			<!-- <li class="upload_one">
    				<i class="text_area">软件介绍：</i>
    				<textarea rows="5" cols="38" name="" id="introduce" class="same" ></textarea>
    				<span class="prompt"></span>
    			</li> -->
    			<li class="btn">
    				<i></i>
    				<input type="submit" value="上传" class="submit1" id="upload_intro"/>
    				<input type="reset" value="重置" class="reset" name="res" id="res_intro">
    				<input type="button" value="返回" class="btn_back">
    			</li>
    		</ul>
    	</form>
    	</div>
     </div>
    </div>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		/* $(".same").each(function(){
			var bdv = $("<strong>*</strong>");
			$(this).parent().append(bdv);
		}); */
		//获取各个焦点
		$(":input").focus(function(){
		    if($(this).is("#device_name")){
		    	var bdv2 = $("<span class='a0'>正确填写大于3位且小于12位的软件名称。</span>");
		    	$(this).parent().append(bdv2);
		    	$(this).parent().find(".different").remove();
		    	$(this).css("border","1px solid #666");
		    }
		    if($(this).is("#device_num")){
		    	var bdv2 = $("<span class='a0'>正确填写大于3位且小于12位的设备序列号。</span>");
		    	$(this).parent().append(bdv2);
		    	$(this).parent().find(".different").remove();
		    	$(this).css("border","1px solid #666");
		    }
		    if($(this).is("#introduce")){
		    	var bdv2 = $("<span class='a0'>请认真填写产品的简介。</span>");
		    	$(this).parent().append(bdv2);
		    	$(this).parent().find(".different").remove();
		    	$(this).css("border","1px solid #666");
		    }
		});
		//失去某个焦点
		$(":input").blur(function(){
			$(this).parent().find(".different").remove();
			$(this).parent().find(".a0").remove();
			if($(this).is("#device_name")){
				$(this).css("border","1px solid #D4D4D4");//边框颜色
				$(this).parent().find("strong").remove();
				if(this.value==''||this.value.length<6){
					var bdv1=$("<span class='different'>软件名不得小于6位</span>");
   					 $(this).parent().append(bdv1);
				}else{
					var bdv1=$("<span class='different'><img src='icon/true_list.png'></span>");
    				$(this).parent().append(bdv1);
				}
			}
			if($(this).is("#device_num")){
				$(this).css("border","1px solid #D4D4D4");
				$(this).parent().find("strong").remove();
				if(this.value==''||this.value.length<6){
					var bdv1=$("<span class='different'>序列号不得小于6位</span>");
   					 $(this).parent().append(bdv1);
				}else{
					var bdv1=$("<span class='different'><img src='icon/true_list.png'></span>");
    				$(this).parent().append(bdv1);
				}
			}
			if($(this).is("#device_type")){
				$(this).parent().find("strong").remove();
				var bdv1=$("<span class='different'><img src='icon/true_list.png'></span>");
				$(this).parent().append(bdv1);
			}
			if($(this).is("#uploadings")){
				if(this.value.length>0){
					$(this).parent().find("strong").remove();
					var bdv1=$("<span class='different'><img src='icon/true_list.png'></span>");
					$(this).parent().append(bdv1);
				}
				
			}
			if($(this).is("#introduce")){
				$(this).css("border","1px solid #D4D4D4");
				$(this).parent().find("strong").remove();
				if(this.value==''||this.value==null){
					var bdv1=$("<span class='different'>设备介绍不能为空！</span>");
   					 $(this).parent().append(bdv1);
				}else{
					var bdv1=$("<span class='different'><img src='icon/true_list.png'></span>");
    				$(this).parent().append(bdv1);
				}
			}
		})
		
		$("#res_intro").click(function(){
			/* $("input").val(" "); */
			$('#biaodan')[0].reset();
			/* $("#biaodan .upload_one").append("<strong>*</strong>") */
			var reset_dif=$(this).parent().siblings();
			reset_dif.find(".different").html('');
			reset_dif.find("strong").html('');
			reset_dif.append("<strong>*</strong>");
			
		})												
	})																		
</script>
</html>