function getByClass(clsName,parent){
  var oParent=parent?document.getElementById(parent):document,
      eles=[],
      elements=oParent.getElementsByTagName('*');

  for(var i=0,l=elements.length;i<l;i++){
    if(elements[i].className==clsName){
      eles.push(elements[i]);
    }
  }
  return eles;
}

window.onload=drag;

function drag(){
   var oTitle=getByClass('chat_header','chat_mian')[0];
   var mTitle=document.getElementById('manager_header');
   // 拖曳
   oTitle.onmousedown=fnDown;
   mTitle.onmousedown=fnDown;
   // 关闭
   var oClose=document.getElementById('chat_close');
   oClose.onclick=function(){
   	  document.getElementById('chat_mian').style.display='none';
   }
   /*document.getElementById('add_person').onclick=function add(){
	   if(document.getElementById('add_group').style.display != "block"){
		   document.getElementById('add_group').style.display = "block"
	   }else{
		   document.getElementById('add_group').style.display = "none"
	   }
   }*/
   $("#add_person").click(function(){
	   $(".add_group").slideToggle();
   });
   $(".chat_group_btn").click(function(){
	   $(".chat_group_btn").toggleClass('chat_group_btn_on');
	   $(".chat_right").fadeToggle(10);
   })
}

function fnDown(event){
  event = event || window.event;
  var oDrag=document.getElementById('chat_mian'),
      // 光标按下时光标和面板之间的距离
      disX=event.clientX-oDrag.offsetLeft,
      disY=event.clientY-oDrag.offsetTop;
  // 移动
  document.onmousemove=function(event){
  	event = event || window.event;
  	fnMove(event,disX,disY);
  }
  // 释放鼠标
  document.onmouseup=function(){
  	document.onmousemove=null;
  	document.onmouseup=null;
  }
}

function fnMove(e,posX,posY){
  var oDrag=document.getElementById('chat_mian'),
      l=e.clientX-posX,
      t=e.clientY-posY,
      winW=document.documentElement.clientWidth || document.body.clientWidth,
      winH=document.documentElement.clientHeight || document.body.clientHeight,
      maxW=winW-oDrag.offsetWidth-10,
      maxH=winH-oDrag.offsetHeight;
  if(l<0){
    l=0;
  }else if(l>maxW){
    l=maxW;
  }
  if(t<0){
    t=10;
  }else if(t>maxH){
    t=maxH;
  }
  oDrag.style.left=l+'px';
  oDrag.style.top=t+'px';
}
document.onkeydown=function(event){
	  var e = event || window.event || arguments.callee.caller.arguments[0];
	  if(e&&e.keyCode==13){
		  rsend();
	  }
	}; 
function rsend(){
	var text = document.getElementById("rightText").value;
	if(text.trim() != ""){
		var rightContent = document.getElementById("rightContent");
		var option = document.createElement("option");
		var len = getboeAbstractLength(text);
		var width = 280 + "px";
		option.style.width = len * 7.5+ 4 + "px";
		option.style.marginLeft = 350 - (len * 7.5+ 4) - 72 +"px";
		option.innerHTML = text;
		rightContent.appendChild(option);
		document.getElementById("rightText").value = "";
	}
}

function getboeAbstractLength(str)
{
    var sTmpStr,sTmpChar;
    var sOriLenth=0;
    var sReLenth=0;
    
    sTmpStr = new String(str);
    sOriLenth = sTmpStr.length;
    
    for(var i=0; i < sOriLenth; i++)
    {
      sTmpChar = sTmpStr.charAt(i);
      if(escape(sTmpChar).length > 4)//汉字
      {
          sReLenth += 2;
      }
      else if(sTmpChar != '\r')//换行
      {
          sReLenth++;
      }
    }

    return sReLenth;   
}

function nameclick(id){
	var fname=document.getElementById(id).getElementsByClassName("n")[0];
	var name=document.getElementById("name");
	name.innerHTML=fname.innerHTML;
} 
function time(){
	
	var s=document.getElementById("id_1").getElementsByTagName("p")[0];
}

