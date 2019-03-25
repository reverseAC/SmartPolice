//图片真实路径
function imgshow(){
	var imgshow=document.getElementById("imgshow");
	 var oFReader = new FileReader();
	    var file = document.getElementById('fileimg').files[0];
	    oFReader.readAsDataURL(file);
	    oFReader.onloadend = function(oFRevent){
	        var src = oFRevent.target.result;
	        if(fileimg.value!="")
	        {
	        	imgshow.src=src;
	        }
	    }
}
//显示窗口
function visible(i){
	var one=document.getElementById("one");
	var two=document.getElementById("two");
	one.style.display="none";
	two.style.display="none";
	switch(i){
	case 1:one.style.display="block";break;
	case 2:two.style.display="block";break;
	default:alert(i);
	}
}
//关闭
function stopbox(id){
	document.getElementById(id).style.display="none";
	
}


//修改
$(document).ready(function(){
	
	
	$(".info_operation").click(
			function prev(){
		
	   var prev=$(this).prev(); 
	  $(".modify_info").val(" ");
	    window.prev = prev;  
	    
		$(".prompt_form").css("display","block");
		var khp = $(window).height();/* 可见高度 */
		var kwp = $(window).width();/* 可见宽度 */
		var mw = $(".prompt_form").width();
		var mh = $(".prompt_form").height();
		var zh=(khp-mh)/2;
		var zw=(kwp-mw)/2;
		$(".prompt_form").css({"top":zh,"left":zw});			
		$(".mask").css("display","block");			
	
	})
	$(".prompt_title span").click(function(){
		$(".mask").css("display","none");
		$(".prompt_form").css("display","none");
	})
	$(".pr_btn").click(function(){
		
		if(confirm("是否确认修改")){
			var val= $(".modify_info").val();		
			prev.text(val);					
			$(".mask").css("display","none");
			$(".prompt_form").css("display","none");
		}else{
			$(".mask").css("display","none");
			$(".prompt_form").css("display","none");
		}
			
			
			
		})
	
	
})