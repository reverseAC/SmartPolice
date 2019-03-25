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
//		var secpo_1=document.getElementById("secuitypos_1");
//		var secpo_2=document.getElementById("secuitypos_2");
		var secan_1=document.getElementById("secuityanswer_1");
		var secan_2=document.getElementById("secuityanswer_2");
		var aP=document.getElementsByClassName('msg');
		var secpo_1_msg=aP[0];
		var secan_1_msg=aP[1];
		var secpo_2_msg=aP[2];
		var secan_2_msg=aP[3];
		var acount1=document.getElementById('count1');
		var acount2=document.getElementById('count2');
		var acount3=document.getElementById('count3');
		var acount4=document.getElementById('count4');
		//密保问题
	    //密保问题1
//	    secpo_1.onfocus=function(){
//	    	secpo_1_msg.style.display="block";
//	    	secpo_1_msg.innerHTML='<i class="ati"></i>4-40个字符的问题'
//    	}
//    	$("#secuitypos_1").bind('input propertychange', function() {
//    		//字符数 
//    		acount1.style.visibility="visible";
//    		var secpo_1_length=getLength(this.value);
//        	acount1.innerHTML=secpo_1_length+'个字符';
//        	if(secpo_1_length==0){
//        		acount1.style.visibility="hidden";
//        	}   
//    	});
//    	secpo_1.onblur=function(){
//        	/* 非法字符 */
//    		
//        	
//        	/* 不能为空*/
//    		 if(this.value==''||this.value==null){
//    			secpo_1_msg.innerHTML='<i class="err"></i>不能为空'
//        	}
//        	/* 长度不能超40个字符 */
//    		else if(secpo_1_length>40){
//    			secpo_1_msg.innerHTML='<i class="err"></i>长度超过40个字符'
//        	}
//        	//长度不能小于4个字符
//        	else if(secpo_1_length<4){
//        		secpo_1_msg.innerHTML='<i class="err"></i>长度小于4个字符'
//        	}
//        	//OK
//        	else{
//        		secpo_1_msg.innerHTML='<i class="ok"></i>'
//        	}
//		}
//    	
//    	//密保问题二
//    	 secpo_2.onfocus=function(){
// 	    	secpo_2_msg.style.display="block";
// 	    	secpo_2_msg.innerHTML='<i class="ati"></i>4-40个字符的问题'
//     	}
//     	$("#secuitypos_2").bind('input propertychange', function() {
//     		//字符数 
//     		acount3.style.visibility="visible";
//     		var secpo_2_length=getLength(this.value);
//         	acount3.innerHTML=secpo_2_length+'个字符';
//         	if(secpo_2_length==0){
//         		acount3.style.visibility="hidden";
//         	}   
//     	});
//     	secpo_2.onblur=function(){
//         	/* 非法字符 */
//     		
//         	
//         	/* 不能为空*/
//     		 if(this.value==''||this.value==null){
//     			secpo_2_msg.innerHTML='<i class="err"></i>不能为空'
//         	}
//         	/* 长度不能超40个字符 */
//     		else if(secpo_2_length>40){
//     			secpo_2_msg.innerHTML='<i class="err"></i>长度超过40个字符'
//         	}
//         	//长度不能小于4个字符
//         	else if(secpo_2_length<4){
//         		secpo_2_msg.innerHTML='<i class="err"></i>长度小于4个字符'
//         	}
//         	//OK
//         	else{
//         		secpo_2_msg.innerHTML='<i class="ok"></i>'
//         	}
// 		}
     	//密保答案一
     	 secan_1.onfocus=function(){
     		secan_1_msg.style.display="block";
     		secan_1_msg.innerHTML='<i class="ati"></i>1-20个字符的答案'
     	}
     	$("#secuityanswer_1").bind('input propertychange', function() {
     		//字符数 
     		acount2.style.visibility="visible";
     		 secan_1_length=getLength(this.value);
         	acount2.innerHTML=secan_1_length+'个字符';
         	if(secan_1_length==0){
         		acount2.style.visibility="hidden";
         	}   
     	});
     	secan_1.onblur=function(){
         	/* 非法字符 */
         	/* 不能为空*/
     		 if(this.value==''||this.value==null){
     			secan_1_msg.innerHTML='<i class="err"></i>不能为空'
         	}
         	/* 长度不能超20个字符 */
     		else if(secan_1_length>20){
     			secan_1_msg.innerHTML='<i class="err"></i>长度超过20个字符'
         	}
     		/* 长度不能少于1个字符 */
     		else if(secan_1_length<1){
         		secan_1_msg.innerHTML='<i class="err"></i>长度小于1个字符'
         	}
         	//OK
         	else{
         		secan_1_msg.innerHTML='<i class="ok"></i>'
         	}
 		}
     	
     	//密保答案二
     	
    	 secan_2.onfocus=function(){
    		secan_2_msg.style.display="block";
    		secan_2_msg.innerHTML='<i class="ati"></i>1-20个字符的答案'
    	}
    	$("#secuityanswer_2").bind('input propertychange', function() {
    		//字符数 
    		acount4.style.visibility="visible";
    		 secan_2_length=getLength(this.value);
        	acount4.innerHTML=secan_2_length+'个字符';
        	if(secan_2_length==0){
        		acount4.style.visibility="hidden";
        	}   
    	});
    	secan_2.onblur=function(){
        	/* 非法字符 */
    		
        	
        	/* 不能为空*/
    		 if(this.value==''||this.value==null){
    			secan_2_msg.innerHTML='<i class="err"></i>不能为空'
        	}
        	/* 长度不能超20个字符 */
    		else if(secan_2_length>20){
    			secan_2_msg.innerHTML='<i class="err"></i>长度超过20个字符'
        	}
    		 /* 长度不能少于1个字符 */
    		else if(secan_2_length<1){
         		secan_2_msg.innerHTML='<i class="err"></i>长度小于1个字符'
         	}
        	
        	//OK
        	else{
        		secan_2_msg.innerHTML='<i class="ok"></i>'
        	}
		}
	   
		
}