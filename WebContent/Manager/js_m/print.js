/**
 * 在所需该功能的页面引入该js文件
 * 给table标签赋值id
 * 在使用打印功能处的a标签中添加：onclick="printdiv('form');"
 * form为table标签的id
 */
var divname = document.getElementById("form");
function printdiv(divName)
{
	alert("打印");
var headstr = "<html><head><title></title></head><body>";
var footstr = "</body>";
var newstr = document.getElementById(divName).innerHTML;
var oldstr = document.body.innerHTML;
document.body.innerHTML = headstr+newstr+footstr;
window.print(); 
document.body.innerHTML = oldstr;
return false;
}