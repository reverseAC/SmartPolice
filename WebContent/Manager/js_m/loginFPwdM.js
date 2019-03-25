var code;
	function createCode(){ 
		code="";
		var codeLength=4;
		var code_value = document.getElementById('code_value');
		var find_code = document.getElementById('find_code');
		code_value.innerHTML="";
		find_code.innerHTML="";
		var selectChar = new Array(1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');
		for(var i = 0;i < codeLength;i++){
			var charIndex = Math.floor(Math.random()*60);
			code+=selectChar[charIndex];
		}
		if(code.length!=codeLength){
			createCode();
		}
		code_value.innerHTML=code;
		find_code.innerHTML=code;
	}
	window.onload=function(){
		var aInput=document.getElementsByTagName('input');
		var aP=document.getElementsByClassName('msg_f');
		var use = aInput[0];
		var validation =aInput[1];
		var code_num = aInput[2];
		var useId=aInput[4]; 
		var answer1=aInput[5];
		var answer2=aInput[6];
		var validation2=aInput[7];
		use.onfocus=function(){
			aP[0].style.display="block";
		}
		use.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[0].innerHTML='用户名不能为空'
		    }else{
		    	aP[0].innerHTML='ok'
		    }					
		}

		useId.onfocus=function(){
			aP[3].style.display="block";
		}
		useId.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[3].innerHTML='用户名不能为空'
		    }else{
		    	aP[3].innerHTML='ok'
		    }					
		} 

		answer1.onfocus=function(){
			aP[4].style.display="block";
		}
		answer1.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[4].innerHTML='答案不能为空'
		    }else{
		    	aP[4].innerHTML='ok'
		    }					
		}

		answer2.onfocus=function(){
			aP[5].style.display="block";
		}
		answer2.onblur=function(){
			if(this.value==null||this.value==""){
		    	aP[5].innerHTML='答案不能为空'
		    }else{
		    	aP[5].innerHTML='ok'
		    }					
		}

				
		/* code */
		
		aP[1].style.display="block";
		createCode();
		var code_btn=document.getElementById('code_btn');
		var find_id=document.getElementById('find_id');
		code_btn.onclick=function(){
			createCode();
		}
		find_id.onclick=function(){
			createCode();
		}
		
		$('#find_text').bind('input propertychange', function() {
			
			if(this.value==""){
				aP[1].innerHTML='验证码输入不能为空'
			}else if(this.value!==code.toLocaleLowerCase()){
				aP[1].innerHTML='验证码输入错误，换一张'
			}else{
				aP[1].innerHTML='验证码输入正确'
			}

		});
		aP[6].style.display="block";
		// document.getElementById("ques").disabled = disabled;
		createCode();
		var code_btn=document.getElementById('code_btn');
		var find_id=document.getElementById('find_id');
		code_btn.onclick=function(){
			createCode();
		}
		find_id.onclick=function(){
			createCode();
		}
		$('#find_pro').bind('input propertychange', function() {
			if(this.value==""){
				aP[6].innerHTML='验证码输入不能为空'
			}else if(this.value!==code.toLocaleLowerCase()){
				aP[6].innerHTML='验证码输入错误，换一张'
			}else{
				/* document.getElementById("ques").disabled = false; */
				$("#ques").attr("disabled", false);
				aP[6].innerHTML='验证码输入正确'
			}

		});
		
		
		
		/* 这里只是添加验证码正确就可以跳转到相应的页面（重新设置密码页面） */
		$(".useBtn").click(function(){
			if(validation.value==code){
				$(".reset_pwd1").css("display","none");
				$(".reset_pwd2").css("display","block");
				$(".login_position").find(".pwd_in").css("color","#333");
			}
		});
		$(".findBtn").click(function(){
			var f=$("#find_pro").val();
			if(f==code){
				$(".reset_pwd3").css("display","none");
				$(".reset_pwd2").css("display","block");
				$(".login_position").find(".pwd_in").css("color","#333")
			}
		});
		$(".use_in").click(function(){
			alert()
			var v=$(".use_in").text()
			if(v=="->填写用户名"){
				$(".reset_pwd1").css("display","block");
				$(".reset_pwd2").css("display","none");
				$(".login_position").find(".pwd_in").css("color","#80A1E1")
			}else{
				$(".reset_pwd3").css("display","block");
				$(".reset_pwd2").css("display","none");
				$(".login_position").find(".pwd_in").css("color","#80A1E1")
			}
		});
		$(".pwdBtnBack").click(function(){
			if($(".use_in").text()=="->填写用户名"){
				$(".reset_pwd1").css("display","block");
				$(".reset_pwd2").css("display","none");
				$(".login_position").find(".pwd_in").css("color","#80A1E1")
			}else{
				$(".reset_pwd3").css("display","block");
				$(".reset_pwd2").css("display","none");
				$(".login_position").find(".pwd_in").css("color","#80A1E1")
			}
			
		})
		$(".find_problem").click(function(){
			$(".reset_pwd3").css("display","block");
			$(".reset_pwd1").css("display","none");
			$(".login_position").find(".pwd_in").css("color","#333");
			$(".login_position").html('<h3>找回密码</h3><p class="use_in">-&gt密保问题</p><p class="pwd_in">-&gt设置新密码</p>')
		});
		$(".default").click(function(){
			$(".reset_pwd1").css("display","block");
			$(".reset_pwd3").css("display","none");
			$(".login_position").find(".pwd_in").css("color","#333");
			$(".login_position").html('<h3>找回密码</h3><p class="use_in">-&gt填写用户名</p><p class="pwd_in">-&gt设置新密码</p>')
		});
		
	 } 
	