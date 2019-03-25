window.onload=function(){
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
		//对象定义
		var oName=document.getElementById("username");
		var oUse=document.getElementById("resname");
		var comp=document.getElementById("companyId");
		var postion=document.getElementById("position");
		var pwd=document.getElementById("pwd");
		var pwd2=document.getElementById("pwd2");
		var email=document.getElementById("email");
		var tel=document.getElementById("tel");
		
		var code_btn=$("#text_code");
		var aP=document.getElementsByClassName('msg');
		var name_msg=aP[0];
		var use_msg=aP[1];
		var comp_msg=aP[2];
		var position_msg=aP[3];
		var pwd_msg=aP[4];
		var pwd2_msg=aP[5];
		var email_msg=aP[6];
		var tel_msg=aP[7];
		var code_msg=aP[8];
		var acount1=document.getElementById('count1');
		var acount2=document.getElementById('count2');
		var acount3=document.getElementById('count3');
		var acount4=document.getElementById('count4');
		var acount5=document.getElementById('count5');
		var aEm=document.getElementsByTagName('em');
		
		
		  
		//正则定义
		var re = /[^\w\u4e00-\u9fa5]/g;/* 判断非法字符 */
		var re1=/[^\u4e00-\u9fa5]/;
	    /* 用户名称 */
		
	    oName.onfocus=function(){   	
	    	name_msg.style.display="block";
	    	name_msg.innerHTML='<i class="ati"></i>5-25个字符，一个汉字为两个字符,不能有空格'
	    }
	    $("#username").bind('input propertychange', function() {
    		//字符数 
    		acount1.style.visibility="visible";
        	use_length=getLength(this.value);
        	acount1.innerHTML=use_length+'个字符';
        	if(use_length==0){
        		acount1.style.visibility="hidden";
        	}   
    	});
	    oName.onblur=function(){
	    	/* 非法字符 */
	    	name_length=getLength(this.value);
	    	if(re.test(this.value)){
	    		name_msg.innerHTML='<i class="err"></i>含有非法字符'
	    	}
	    	/* 不能为空*/
			else if(this.value==''||this.value==null){
	    		name_msg.innerHTML='<i class="err"></i>不能为空'
	    	}
	    	
	    	/* 长度不能超25个字符 */
			else if(name_length>25){
	    		name_msg.innerHTML='<i class="err"></i>长度超过25个字符'
	    	}
	    	//长度不能小于6个字符
	    	else if(name_length<5){
	    		name_msg.innerHTML='<i class="err"></i>长度小于5个字符'
	    	}
	    	//OK
	    	else{
	    		name_msg.innerHTML='<i class="ok"></i>'
	    			
	    	}
	    }
	    	/* 真实姓名 */
	    	oUse.onfocus=function(){
	    		use_msg.style.display="block";
	    		use_msg.innerHTML='<i class="ati"></i>4-12个字符，一个汉字为两个字符,不能有空格'
	    	}
	    	$("#resname").bind('input propertychange', function() {
	    		//字符数 
	    		acount2.style.visibility="visible";
	        	use_length=getLength(this.value);
	        	acount2.innerHTML=use_length+'个字符';
	        	if(use_length==0){
	        		acount2.style.visibility="hidden";
	        	}   
	    	});
	    	oUse.onblur=function(){
	        	/* 非法字符 */
	        	if(re1.test(this.value)){
	        		use_msg.innerHTML='<i class="err"></i>含有非法字符'
	        	}
	        	/* 不能为空*/
	    		else if(this.value==''||this.value==null){
	    			use_msg.innerHTML='<i class="err"></i>不能为空'
	        	}
	        	/* 长度不能超25个字符 */
	    		else if(use_length>12){
	    			use_msg.innerHTML='<i class="err"></i>长度超过12个字符'
	        	}
	        	//长度不能小于6个字符
	        	else if(use_length<4){
	        		use_msg.innerHTML='<i class="err"></i>长度小于4个字符'
	        	}
	        	//OK
	        	else{
	        		use_msg.innerHTML='<i class="ok"></i>'
	        	}
			}
	    	/*公司Id*/
	    	comp.onfocus=function(){
	    		comp_msg.style.display="block";
	    		comp_msg.innerHTML='<i class="ati"></i>数字id'
	    	}
	    	$("#companyId").bind('input propertychange', function() {
	    		//字符数 
	    		acount4.style.visibility="visible";
	        	comp_length=getLength(this.value);
	        	acount4.innerHTML=comp_length+'个字符';
	        	if(comp_length==0){
	        		acount4.style.visibility="hidden";
	        	}   
	    	});
	    	comp.onblur=function(){
	        	/* 非法字符 */
	    		var re_r=/[^0-9]/;
	        	if(re_r.test(this.value)){
	        		comp_msg.innerHTML='<i class="err"></i>含有非法字符'
	        	}
	        	/* 不能为空*/
	    		else if(this.value==''||this.value==null){
	    			comp_msg.innerHTML='<i class="err"></i>不能为空'
	        	}
	        	/* 长度不能超12个字符 */
	    		else if(comp_length>12){
	    			comp_msg.innerHTML='<i class="err"></i>长度超过12个字符'
	        	}
	        	//OK
	        	else{
	        		comp_msg.innerHTML='<i class="ok"></i>'
	        	}
			}
	    	/*公司职位*/
	    	position.onfocus=function(){
	    		position_msg.style.display="block";
	    		position_msg.innerHTML='<i class="ati"></i>4-12个字符的职位'
	    	}
	    	$("#position").bind('input propertychange', function() {
	    		//字符数 
	    		acount5.style.visibility="visible";
	    		position_length=getLength(this.value);
	        	acount5.innerHTML=position_length+'个字符';
	        	if(position_length==0){
	        		acount5.style.visibility="hidden";
	        	}   
	    	});
	    	position.onblur=function(){
	        	/* 非法字符 */
	    		
	        	if(re1.test(this.value)){
	        		position_msg.innerHTML='<i class="err"></i>含有非法字符'
	        	}
	        	/* 不能为空*/
	    		else if(this.value==''||this.value==null){
	    			position_msg.innerHTML='<i class="err"></i>不能为空'
	        	}
	        	/* 长度不能超12个字符 */
	    		else if(position_length>12){
	    			position_msg.innerHTML='<i class="err"></i>长度超过12个字符'
	        	}
	        	//长度不能小于5个字符
	        	else if(position_length<4){
	        		position_msg.innerHTML='<i class="err"></i>长度小于4个字符'
	        	}
	        	//OK
	        	else{
	        		position_msg.innerHTML='<i class="ok"></i>'
	        	}
			}
	    	/* 密码 */
			pwd.onfocus=function(){
	    		pwd_msg.style.display="block";
	    		pwd_msg.innerHTML='<i class="ati"></i>6-16个字符，请使用数字或者字母组合，不能单独使用数字或者字母'
	    	}
			$("#pwd").bind('input propertychange', function() {
				//大于5个为中等难度
				if (this.value.length>5) {
		            aEm[1].className="em_active";
		            pwd2.removeAttribute("disabled","");
		            pwd2_msg.style.display="block";
		        }else{
		            aEm[1].className="";
		            pwd2.setAttribute("disabled" ,"");
		            pwd2_msg.style.display="none";
		        }
				//大于10个“强”
	            if(this.value.length>10){
	            	aEm[2].className="em_active";
	            }else{
	            	aEm[2].className=""
	            }
	    	})
			pwd.onblur=function(){
				var m=findStr(pwd.value,pwd.value[0]);
	        	var re_n=/[^\d]/g;//正则判断，不能全为数字
	        	var re_t=/[^a-zA-Z]/g;//不能全为字母
	        	var re_sp=/[\s]/;//不能含有空格
	        	//不能为空
	   			if(this.value==""){
	   				pwd_msg.innerHTML='<i class="err"></i>密码不能为空'
	   			}
	   			// 不能为相同字符
	   			else if(m==this.value.length){
	   				pwd_msg.innerHTML='<i class="err"></i>密码不能全为相同字符'
	   			}
	   			// 长度应为6-16个字符
	   			else if(this.value.length<6||this.value.length>16){
	   				pwd_msg.innerHTML='<i class="err"></i>长度应为6-16个字符'
	   			}
	   			else if(!re_n.test(this.value)){
	   				pwd_msg.innerHTML='<i class="err"></i>密码不能全为数字'
	   			}
	   			else if(!re_t.test(this.value)){
	   				pwd_msg.innerHTML='<i class="err"></i>密码不能全为字母'
	   			}
	   			else if(re_sp.test(this.value)){
	   				pwd_msg.innerHTML='<i class="err"></i>密码不能存在空格'
	   			}
	   			else{
	   				pwd_msg.innerHTML='<i class="ok"></i>'
	   			}
	    	}
			/* 确认密码 */
			pwd2.onblur=function(){
				if(this.value!=pwd.value){
	        		pwd2_msg.innerHTML='<i class="err"></i>两次输入的密码不一样，请重新输入'
	        	}
	        	else{
	        		pwd2_msg.innerHTML='<i class="ok"></i>'
	        	}
	    	}
			/* 邮箱 */
			email.onfocus=function(){
				email_msg.style.display="block";
				email_msg.innerHTML='<i class="ati"></i>请按有@字符的邮箱格式填写'
			}
			email.onblur=function(){
				/* 邮箱格式@ */
				var re_m=/[\w]*@.+.[a-zA-Z]{2,4}$/;
				
				if(!re_m.test(this.value)){
					email_msg.innerHTML='<i class="err"></i>邮箱格式不正确'
				}else if(this.value==""||this.value==null){
					email_msg.innerHTML='<i class="err"></i>邮箱不能为空'
				}
				else{
					email_msg.innerHTML='<i class="ok"></i>'
				}
			}
			tel.onfocus=function(){
				tel_msg.style.display="block";
				tel_msg.innerHTML='<i class="ati"></i>请填写11位的电话号码'
			}
			$("#tel").bind('input propertychange', function() {
				acount3.style.visibility="visible";
	        	tel_length=getLength(this.value);
	        	acount3.innerHTML=tel_length+'个字符';
	        	if(tel_length==0){
	        		acount3.style.visibility="hidden";
	        	}  
			})
			tel.onblur=function(){
				
				var re_t=/[\W]/;
				if(re_t.test(this.value))
					{
					tel_msg.innerHTML='<i class="err"></i>存在非数字字符'
					}
				else if(this.value==""){
					tel_msg.innerHTML='<i class="err"></i>联系方式不能为空'
				}else if(this.value.length<11){
					tel_msg.innerHTML='<i class="err"></i>电话号码小于11位'
				}else if(this.value.length>11){
					tel_msg.innerHTML='<i class="err"></i>电话号码大于11位'
				}
				else{
					tel_msg.innerHTML='<i class="ok"></i>'
				}
			}
			
	    /* 验证码 */
	    var code; 
	    function createCode(){ 
	    	
			code="";
			var codeLength=4;
			var  btn_code= document.getElementById('btn_code');
			btn_code.value="";
			var selectChar = new Array(1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');
			for(var i = 0;i < codeLength;i++){
				var charIndex = Math.floor(Math.random()*60);
				code+=selectChar[charIndex];
			}
			if(code.length!=codeLength){
				createCode();
			}
			btn_code.value=code;
		}
	    code_msg.style.display="block";
		createCode();
		var code_btn=document.getElementById('code_btn');
		
		code_btn.onclick=function(){
			createCode();
		}
		
	    $('#text_code').bind('input propertychange', function(){
	    
	    	if(this.value==""){
	    		code_msg.innerHTML='<i class="err"></i>验证码不能为空'
	    	}
	    	else if(this.value!==code.toLowerCase()){
	    		code_msg.innerHTML='<i class="err"></i>验证码不正确'
	    			
	    		
	    	}else{
	    		code_msg.innerHTML='<i class="ok"></i>'
	    	}
	    });
	    setInterval(function(){
	    	
	    	if($("#text1").is(':checked'))
	    	{
		    	  if(name_msg.innerHTML=='<i class="ok"></i>'&&
						   use_msg.innerHTML=='<i class="ok"></i>'&&
						   comp_msg.innerHTML=='<i class="ok"></i>'&&
						   position_msg.innerHTML=='<i class="ok"></i>'&&
						   pwd_msg.innerHTML=='<i class="ok"></i>'&&
						   pwd2_msg.innerHTML=='<i class="ok"></i>'&&
						   email_msg.innerHTML=='<i class="ok"></i>'&&
						   tel_msg.innerHTML=='<i class="ok"></i>'&&
						   code_msg.innerHTML=='<i class="ok"></i>')
					   {
		    		  $("#in_submit").attr("disabled", false);
					   }
			    };
	    },1000);
	  
	   
	   
}