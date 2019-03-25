/**
 * 
 */
//。。。。。。。。问题，active样式无法长期保存
$(document).ready(function(){
	$(".menu_li").click(function(){
		/*if($(this).children().css("background-image").indexOf("icon_m/nav_one.png")>0){
			
			$(this).children().css("background-image","url('icon/nav_one1.png')");
		}else{
			$(this).children().css("background-image","url('icon/nav_one.png')");
		}*/
		if($(this).children().hasClass('active')){
			$(this).children().removeClass('active');
		}else{
			$(this).children().addClass("active");
			$(this).parent().siblings().find('.menu_li').children().removeClass('active');
		}
		$(this).next().slideToggle().parent().siblings().find('ul').slideUp();
	});
	
	$(".menu_main ul li a").click(function(){
		$(".menu_main ul li a").css({"color":"#3971AA"})
		$(this).css({"color":"#000"})
	});
	

	/*$(".menu_main ul li").each(function(){
		
		$(this).click(function(){
			
			$(".menu_main ul li").css({"background":""});
			$(".menu_main ul li").find("a").css({"color":"#444","borderTop":"1px solid #ccc"});
			$(this).css({"background":"#fff"});
			$(this).find("a").css({"color":"#000","border":"none"});
		});
		
	})*/
})












