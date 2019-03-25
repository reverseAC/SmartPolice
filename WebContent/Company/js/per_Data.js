/**
 * 
 */
// alert("hello!");
window.onload=function(){
	var navs=document.querySelectorAll(".nav_two>li");
	//console.log(navs);
    var divs=document.querySelectorAll(".divs>div");
    //console.log(divs);
    var last=navs[0];
    for(var i=0;i<navs.length;i++){
    	navs[i].index=i;//添加索引值
    	navs[i].onclick=function(){
    		divs[last.index].style.display="none";
    		divs[this.index].style.display="block";
    		last=this;
    		
    	};
    }
    }
$(document).ready(function(){
	/* 一个中文两个字符 */
	function getLength(str){
		return str.replace(/[^\x00-xff]/g,"xx").length;
	}
	/* 判断是否是相同字符 */
	function findStr(str,n){
    	var tmp= 0;
    	for(var i = 0;i<str.length;i++){
    		if(str.charAt(i)==n){
    			tmp++;
    		}
    	}
    	return tmp;
    }
	
//	var birthday=$("#birthday");
	var qq=$("#qq");
	var habit=$("#habit");
	var motto=$("#motto");
	var pwd0=$("#pwd0");
	var tel0=$("#tel0");
	var tel1=$("#tel1");
	var pwd1=$("#pwd1");
	var email0=$("#email0");
	var email1=$("#email1");
	var pwd2=$("#pwd2");
	var pwd3=$("#pwd3");
	var pwd4=$("#pwd4");
	var sp=document.getElementsByClassName('msg');
	var spb=document.getElementsByClassName('spb');
	var qq_bool="false";
	var habit_bool="false";
	var motto_bool="false";
	var acount1=$("#count1");
	var acount2=$("#count2");
	var acount3=$("#count3");
	var acount4=$("#count4");
	var acount5=$("#count5");
	var acount6=$("#count6");
	var acount7=$("#count7");
	var acount8=$("#count8");
	var acount9=$("#count9");
	var aEm=document.getElementsByTagName('em');
	//正则判断
	
	//............................................................................生日判断
	$(function () {
		$.ms_DatePicker({
	            YearSelector: ".sel_year",
	            MonthSelector: ".sel_month",
	            DaySelector: ".sel_day"
	    });
		$.ms_DatePicker();
	}); 
	//............................................................................QQ号
	qq.focus(function(){
		sp[0].style.display="inline";
		sp[0].innerHTML='<i class="ati"></i>以正确的格式书写';
		acount1.parent().parent().css("visibility","visible");
	})

	$("#qq").bind('input propertychange', function() {
    		//字符数 
        	qq_length=getLength(this.value);
        	acount1.text(qq_length+'个字符');
        	if(qq_length==0){
        		acount1.parent().parent().css("visibility","hidden");
        	}   
        	var re_s=/[^0-9]/;
    		if(re_s.test(this.value)){
    			sp[0].innerHTML='<i class="err"></i>含有非法字符';
    		}
    		else if(qq_length>10){
    			sp[0].innerHTML='<i class="err"></i>qq位数大于10位';
    		}
    		else if(0<qq_length&&qq_length<5){
    			sp[0].innerHTML='<i class="err"></i>qq位数小于5位';
    		}
    		else if(this.value==""){
    			sp[0].innerHTML='<i class="err"></i>不能为空';
    		}
    		else {
    			sp[0].innerHTML='<i class="ok"></i>';
    			qq_bool="true";
    		}
    	});
	qq.blur(function(){
		var re_s=/[^0-9]/;
		acount1.parent().parent().css("visibility","hidden");
		if(this.value==""){
			sp[0].innerHTML='<i class="err"></i>不能为空';
		}
		else if(qq_bool=="true"){
			sp[0].innerHTML='<i class="ok"></i>';
		}
		
	})
	//............................................................................地址
	addressInit('Select1', 'Select2', 'Select3');//插件调用
	//............................................................................爱好
	habit.focus(function(){
		sp[1].style.display="inline";
		sp[1].innerHTML='<i class="ati"></i>至少输入一个爱好，爱好之间用"，"分隔';
	})
	$("#habit").bind('input propertychange', function() {
		var re_t=/[^\w\u4e00-\u9fa5,，]/;
		if(re_t.test(this.value)){
			sp[1].innerHTML='<i class="err"></i>含有非法字符';
		}
		else if(this.value==""){
			sp[1].innerHTML='<i class="err"></i>不能为空';
		}
		else{
			sp[1].innerHTML='<i class="ok"></i>';
			habit_bool="true";
		}
	})
	habit.blur(function(){
		
		if(this.value==""){
			sp[1].innerHTML='<i class="err"></i>不能为空';
		}
		else if(habit_bool=="true"){
			sp[1].innerHTML='<i class="ok"></i>';
		}
	})
	//............................................................................座右铭
	motto.focus(function(){
		sp[2].style.display="inline";
		sp[2].innerHTML='<i class="ati"></i>输入座右铭';
	})
	$("#motto").bind('input propertychange', function() {
		var re_t=/[^\u4e00-\u9fa5，,<《》>.。？、/?:；：;"'“’{}！!]/;
		if(re_t.test(this.value)){
			sp[2].innerHTML='<i class="err"></i>含有非法字符';
		}
		else if(this.value==""){
			sp[2].innerHTML='<i class="err"></i>不能为空';
		}
		else{
			sp[2].innerHTML='<i class="ok"></i>';
			
		}
	})
	motto.blur(function(){
		var re_t=/[^\u4e00-\u9fa5，,<《》>.。？、/?:；：;"'“’{}！!]/;
		if(this.value==""){
			sp[2].innerHTML='<i class="err"></i>不能为空';
		}
		else if(motto_bool=="true"){
			sp[2].innerHTML='<i class="ok"></i>';
			
		}
	})
	
	setInterval(function(){
		if(sp[0].innerHTML=='<i class="ok"></i>'&&sp[1].innerHTML=='<i class="ok"></i>'&&sp[2].innerHTML=='<i class="ok"></i>'){
   		 $("#inbutton1").attr("disabled", false);
   	}},1000);
	
	
	
	pwd0.focus(function(){
		sp[3].style.display="inline";
		sp[3].innerHTML='<i class="ati"></i>输入密码';
		acount2.css("visibility","visible");
	})
	pwd0.bind('input propertychange', function() {
			pwd0_length=getLength(this.value);
    	acount2.text(pwd0_length+'个字符');
	})
	pwd0.blur(function(){
		acount2.css("visibility","hidden");
	if(this.value==""){
		sp[3].innerHTML='<i class="err"></i>不能为空';
	}
	else if(pwd0_length<6){
		sp[3].innerHTML='<i class="err"></i>密码位数少于6位';
	}
	else{
		sp[3].innerHTML='<i class="ok"></i>';
	}
})
   tel0.focus(function(){
	   sp[4].style.display="inline";
		sp[4].innerHTML='<i class="ati"></i>输入当前绑定手机号';
		acount3.css("visibility","visible");
   })
   tel0.bind('input propertychange', function() {
	   var re_t=/[^0-9]/;
			tel0_length=getLength(this.value);
    	acount3.text(tel0_length+'个字符');
    	if(re_t.test(this.value)){
    		sp[4].innerHTML='<i class="err"></i>含有非法字符';
    	}
    	else if(tel0_length!=11){
    		sp[4].innerHTML='<i class="err"></i>号码应为11位';
    	}
    	else{
    		sp[4].innerHTML='<i class="ok"></i>';
    	}
	})
	tel0.blur(function(){
		acount3.css("visibility","hidden");
		if(this.value.length==11){
			sp[4].innerHTML='<i class="ok"></i>';
		}
	})
	 tel1.focus(function(){
		acount4.css("visibility","visible");
   })
	tel1.bind('input propertychange', function() {
	   var re_t=/[^0-9]/;
			tel1_length=getLength(this.value);
    	acount4.text(tel1_length+'个字符');
	})
	tel1.blur(function(){
		acount4.css("visibility","hidden");
   })
	pwd1.focus(function(){
		acount5.css("visibility","visible");
		sp[5].style.display="inline";
		sp[5].innerHTML='<i class="ati"></i>输入密码';
	})
	pwd1.bind('input propertychange',function(){
		pwd1_length=getLength(this.value);
	acount5.text(pwd1_length+'个字符');
	})
	pwd1.blur(function(){
		acount5.css("visibility","hidden");
	if(this.value==""){
		sp[5].innerHTML='<i class="err"></i>不能为空';
	}
	else if(pwd1_length<6){
		sp[5].innerHTML='<i class="err"></i>密码位数少于6位';
	}
	else{
		sp[5].innerHTML='<i class="ok"></i>';
	}
})
email0.focus(function(){
	acount6.css("visibility","visible");
		sp[6].style.display="inline";
		sp[6].innerHTML='<i class="ati"></i>输入当前绑定邮箱';
})
email0.bind('input propertychange',function(){
	email0_length=getLength(this.value);
	acount6.text(email0_length+'个字符');
})
email0.blur(function(){
		/* 邮箱格式@ */
	acount6.css("visibility","hidden");
	var re_m=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		 if(this.value==""||this.value==null){
				sp[6].innerHTML='<i class="err"></i>邮箱不能为空'
			}
		else if(!re_m.test(this.value)){
			sp[6].innerHTML='<i class="err"></i>邮箱格式不正确'
		}
		else{
			sp[6].innerHTML='<i class="ok"></i>'
		}
	})
	email1.focus(function(){
	acount7.css("visibility","visible");
		sp[7].style.display="inline";
		sp[7].innerHTML='<i class="ati"></i>输入新邮箱';
})
email1.bind('input propertychange',function(){
	email1_length=getLength(this.value);
	acount7.text(email1_length+'个字符');
})
email1.blur(function(){
		/* 邮箱格式@ */
	acount7.css("visibility","hidden");
	var re_m=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		 if(this.value==""||this.value==null){
				sp[7].innerHTML='<i class="err"></i>邮箱不能为空'
			}
		else if(!re_m.test(this.value)){
			sp[7].innerHTML='<i class="err"></i>邮箱格式不正确'
		}
		else{
			sp[7].innerHTML='<i class="ok"></i>'
		}
	})
	
	setInterval(function(){
		if(sp[5].innerHTML=='<i class="ok"></i>'&&sp[6].innerHTML=='<i class="ok"></i>'&&sp[7].innerHTML=='<i class="ok"></i>'){
   		 $("#inbutton5").attr("disabled", false);
   	}},1000);
	
	//。。。。。。。。。。。。。。。修改密码
	pwd2.focus(function(){
		acount8.css("visibility","visible");
		sp[8].style.display="inline";
		sp[8].innerHTML='<i class="ati"></i>输入密码';
	})
	pwd2.bind('input propertychange',function(){
		pwd2_length=getLength(this.value);
	acount8.text(pwd2_length+'个字符');
	})
	pwd2.blur(function(){
		acount8.css("visibility","hidden");
	if(this.value==""){
		sp[8].innerHTML='<i class="err"></i>不能为空';
	}
	else if(pwd2_length<6){
		sp[8].innerHTML='<i class="err"></i>密码位数少于6位';
	}
	else{
		sp[8].innerHTML='<i class="ok"></i>';
	}
})
pwd3.focus(function(){
		sp[9].style.display="inline";
		sp[9].innerHTML='<i class="ati"></i>6-16个字符，请使用数字或者字母组合';
	})
	pwd3.bind('input propertychange', function() {
		//大于5个为中等难度
		var pwd3_length=getLength(this.value);
		if (pwd3_length>5) {
            aEm[1].className="em_active";
            pwd4.attr("disabled",false);
        }else{
            aEm[1].className="";
        }
		//大于10个“强”
        if(pwd3_length>10){
        	aEm[2].className="em_active";
        }else{
        	aEm[2].className="";
        }
	})
	pwd3.blur(function(){
		var m=findStr(this.value,this.value[0]);
    	var re_n=/[^\d]/g;//正则判断，不能全为数字
    	var re_t=/[^a-zA-Z]/g;//不能全为字母
    	var re_sp=/[\s]/;//不能含有空格
    	//不能为空
			if(this.value==""){
				sp[9].innerHTML='<i class="err"></i>密码不能为空'
			}
			// 不能为相同字符
			else if(m==this.value.length){
				sp[9].innerHTML='<i class="err"></i>密码不能全为相同字符'
			}
			// 长度应为6-16个字符
			else if(this.value.length<6||this.value.length>16){
				sp[9].innerHTML='<i class="err"></i>长度应为6-16个字符'
			}
			else if(!re_n.test(this.value)){
				sp[9].innerHTML='<i class="err"></i>密码不能全为数字'
			}
			else if(!re_t.test(this.value)){
				sp[9].innerHTML='<i class="err"></i>密码不能全为字母'
			}
			else if(re_sp.test(this.value)){
				sp[9].innerHTML='<i class="err"></i>密码不能存在空格'
			}
			else{
				sp[9].innerHTML='<i class="ok"></i>'
			}
	})
	pwd4.focus(function(){
		
		sp[10].style.display="inline";
		sp[10].innerHTML='<i class="ati"></i>请再次输入密码';
	})
	
	
	pwd4.blur(function(){
		if(this.value!=pwd3.val()){
    		sp[10].innerHTML='<i class="err"></i>两次输入的密码不一样，请重新输入'
    	}
    	else{
    		sp[10].innerHTML='<i class="ok"></i>'
    	}
	})
	
	setInterval(function(){
		if(sp[8].innerHTML=='<i class="ok"></i>'&&sp[8].innerHTML=='<i class="ok"></i>'&&sp[10].innerHTML=='<i class="ok"></i>'){
   		 $("#inbutton7").attr("disabled", false);
   	}},1000);
});




	    
		    
		    
		    
		    
		    
		    