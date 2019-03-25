<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="style/reset.css">
	<link rel="stylesheet" type="text/css" href="style/common.css">
	<link rel="stylesheet" type="text/css" href="style/notifyServer.css"> 
	<script type="text/javascript" src="js_m/jquery-1.9.1.min.js"></script>
	<!-- <script type="text/javascript" src="js_m/notifyServer.js"></script> -->
</head>
<body>
  <div class="main_body clearfix">
    	<div class="content server_con fr">
    		<div class="position">
    			<span></span>
    			<h3>当前位置：</h3>
    			<p><a href="#">服务器管理</a><a href="#">&gt添加服务器</a></p>
    		</div>
    	<!-- 需要添加的从这里开始 -->
    	<div class="right_message">
    	<div class="right_inf_top">
    	   <span>添加服务器</span>
    	</div>
    	
    	<div class="upload_info fr">
        	<div class="list_margin">
        		<form action="addServer">
        			<ul class="upload_list upload_lists">
        			 <li class="upload_one">
        				<i>服务器类型:</i>
        				<input type="text" id="device_name" class="same same_img1" name="type" value="" autocomplete='off'>
        				<!-- <span class="prompt"></span> -->
        				<strong></strong>
        			 </li>
        			<li class="upload_one upload_one_xin">
        			    
        				<i>内存大小:</i>
        				<input type="text" id="device_num" class="same same_img2" name="memory" value="" autocomplete='off'>
        				<!-- <span class="prompt"></span> -->
        				<strong></strong>
        			</li>
        			
        			<li class="upload_one upload_one_xin">
        			    
        				<i>IP:</i>
        				<input type="text" id="device_IP" class="same same_img2" name="ip" value="" autocomplete='off'>
        				<!-- <span class="prompt"></span> -->
        				<strong></strong>
        			</li>
        			<li class="upload_one">
        			    
        				<i>port:</i>
        				<input type="text" id="device_port" class="same same_img2" name="port" value="" autocomplete='off'>
        				<!-- <span class="prompt"></span> -->
        				<strong></strong>
        			</li>
        			
        			<li class="upload_one upload_one_xin">
        			
        		        <i>CPU型号:</i>
        				<input type="text" id="device_CPU" class="same same_img3" name="CPU" value="" autocomplete='off'>
        				<!-- <span class="prompt"></span> -->	
        				<strong></strong>
        			</li>
        			
        				<li class="upload_one">
        		    <i class="upload_one_i">管理员id:</i>
        		    <textarea rows="2" cols="58" name="manager" id="introduce" class="same" autocomplete='off'></textarea>
        				<!-- <span class="prompt"></span> -->	
        			</li>
        			
        			
        			<li class="upload_one">
        		    <i class="upload_one_i">服务器描述:</i>
        		    <textarea rows="6" cols="40" name="demo" id="introduce_text" class="same" autocomplete='off'></textarea>
        				<!-- <span class="prompt"></span> -->	
        			</li>
        			<li class="btn">
        				<i></i>
        				<input type="submit" value="添加" class="submit1" id="upload_intro" autocomplete='off'/>
        				<!-- <input type="reset" value="重置" class="reset" name="res" id="res_intro"> -->
        				<!-- <input type="button" value="返回" class="btn_back submit2"> -->
        			</li>
        		</ul>
        		</form>
        		</div>
    	</div>
    	
    	
    	</div><!-- right_message完 -->
    </div><!-- content fr完 -->
    
 </div><!-- main_body clearfix完 -->
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
	    	var bdv2 = $("<span class='a0'>0web服务器，1数据库服务器，2文件服务器</span>");
	    	$(this).parent().append(bdv2);
	    	$(this).parent().find(".different").remove();
	    	$(this).css("background","#fff");
	    }
	    if($(this).is("#device_num")){
	    	var bdv2 = $("<span class='a0'>正确填写大于6位的内存大小</span>");
	    	$(this).parent().append(bdv2);
	    	$(this).parent().find(".different").remove();
	    	$(this).css("background","#fff");
	    }
	    if($(this).is("#device_CPU")){
	    	var bdv2 = $("<span class='a0'>正确输入CPU型号</span>");
	    	$(this).parent().append(bdv2);
	    	$(this).parent().find(".different").remove();
	    	$(this).css("background","#fff");
	    }
	    if($(this).is("#introduce")){
	    	var bdv2 = $("<span class='a0'>感谢你的描述</span>");
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
			
			$(this).css("background","rgba(255, 255, 255, 0.33)");//边框颜色
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value>=3){
				var bdv1=$("<span class='different'><img src='icon_m/false.png' class='different_img'>服务器类型只能为0/1/2</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#device_num")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value.length<6){
				var bdv1=$("<span class='different'><img src='icon_m/false.png'  class='different_img'>内存大小不得小于6位!</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#device_IP")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value.length<6){
				var bdv1=$("<span class='different'><img src='icon_m/false.png'  class='different_img'>id长度不得小于6位!</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#device_port")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value.length<6){
				var bdv1=$("<span class='different'><img src='icon_m/false.png'  class='different_img'>port长度不得小于6位!</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#device_CPU")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value.length<6){
				var bdv1=$("<span class='different'><img src='icon_m/false.png' class='different_img'>请检查CPU型号小于6位或者有误！</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#introduce")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''){
				var bdv1=$("<span class='different'><img src='icon_m/false.png'  class='different_img'>请输入管理员名称!</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		if($(this).is("#introduce_text")){
			$(this).css("background","rgba(255, 255, 255, 0.33)");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value.length<6){
				var bdv1=$("<span class='different'><img src='icon_m/false.png'  class='different_img'>服务器描述不得少于六个字!</span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
		}
		
		
		if($(this).is("#device_type")){
			$(this).parent().find("strong").remove();
			var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
			$(this).parent().append(bdv1);
		}
		if($(this).is("#uploadings")){
			if(this.value.length>0){
				$(this).parent().find("strong").remove();
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
				$(this).parent().append(bdv1);
			}
			
		}
		if($(this).is("#introduce")){
			$(this).css("border","1px solid #D4D4D4");
			$(this).parent().find("strong").remove();
			if(this.value==''||this.value==null){
				var bdv1=$("<span class='different'> </span>");
					 $(this).parent().append(bdv1);
			}else{
				var bdv1=$("<span class='different'><img src='icon_m/true.png'></span>");
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