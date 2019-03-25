/**
 * 
 */
$(document).ready(function(){
	$(".main_left>a").click(function(){		
		if($(this).children().hasClass('active')){
			$(this).children().removeClass('active');
		}else{
			$(this).children().addClass('active');
			$(this).parent().siblings().find('.left_list_item').children().removeClass('active');
		}
		$(this).next().slideToggle().parent().siblings().find('ul').slideUp();
		
		
		
	
	})
	function changeNode(mainNode){
		if(mainNode){
			if(mainNode.css("background-image").indexOf("icon_m/nav_one1.png")>=0){
				mainNode.css("background-image","url('icon_m/nav_one.png')");
			}else{
				mainNode.css("background-image","url('icon_m/nav_one1.png')");
			}
		}
	}
	$(".main_left ul li a").click(function(){
		
		
		var chioce=$(this).children();
		$(".main_left ul li a").children().css("background-image","url('icon_m/nav_two1.png')");
		chioce.css("background-image","url('icon_m/mtchioce.png')");
		
		/*位置*/
		/*var rpo=$(parent.Mcontent.document).find(".position p");
		rpo.html('')
		var lpopp=$(this).parent().parent().prev().text();
		
		
		var lpoc=$(this).text();
		
		rpo.append('<a href="#" class="one_nav_info">'+lpopp+'>'+'</a>'+'<a href="#" class="dy_info">'+lpoc+'</a>');
	    */
		/*alert(rpo.text())*/
		
	})
	
	
	

	
})

