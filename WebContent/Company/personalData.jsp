<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/personalData.css">
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/per_Data.js"></script> 
	<script src="js/jsAddress.js"></script>
	<script src="js/birthday.js"></script>
    <title>修改个人资料</title>
    
    <style type="text/css">
    </style>
</head>
<body>
<div class="main clearfix">
	<div class="position">
        <h3>当前位置：</h3>
        <p><a href="#">个人中心</a><a href="#">&gt;信息修改</a></p>
    </div>
    
	<!-- 左侧小导航 -->
	<div class="nav_left">
		<ul class="nav_group fl">
			<li class="nav_item">
			
			    <img src="icon/person.png" class="nav_item_img" >
			     <span>个人资料</span>
				<ul class="nav_two">
				<li><a href="#">个人资料</a></li>
				    <li><a href="#">修改个人资料</a></li>
					<li><a href="#">修改头像</a></li>
				</ul>
			</li>
			<li class="nav_item">
			
				<img src="icon/account.png" class="nav_item_img"  />
				<span>账号设置</span>
				<ul class="nav_two" id="process">
					<li><a href="#">修改手机</a></li>
					<li><a href="#">修改邮箱</a></li>
					<li><a href="#">修改密码</a></li>
<!-- 					<li><a href="#">绑定登录账号</a></li> -->
				</ul>
			</li>
			
			<li class="nav_item" >
			
				<img src="icon/privacy.png" class="nav_item_img" />
				<span>隐私设置</span>
				
				<ul class="nav_two" id="process">
<!-- 					<li><a href="#">访问限制</a></li> -->
<!-- 					<li><a href="#">动态筛选</a></li> -->
					<li><a href="#">通知筛选</a></li>
				</ul>
			</li>
			
		</ul>
	</div>
	<!-- 右侧内容 -->
	<div class="divs">
	<div class="per_Data fr0" style="display:block">
	<ul>
	<li class="form_item"><label><span>用户名：</span></label><p>1</p></li>
	<li class="form_item"><label><span>生日：</span></label><p>2</p></li>
	<li class="form_item"><label><span>QQ：</span></label><p>3</p></li>
	<li class="form_item"><label><span>手机：</span></label><p>4</p></li>
	<li class="form_item"><label><span>邮箱：</span></label><p>5</p></li>
	<li class="form_item"><label><span>通讯地址：</span></label><p>6</p></li>
	<li class="form_item"><label><span>爱好：</span></label><p>7</p></li>
	<li class="form_item"><label><span>座右铭：</span></label><p>8</p></li>
	<li></li>
	</ul>
	</div>
	  <div class="per_Data fr1"  id="mys">
		  <form action="firmAdminChangeInfo" class="form_group">
	   		<ul class="form_Data">
	   			<li class="form_item">
	   				<label><span>生日：</span></label>
	   	<div class="demo">
   		<p>
        <select id="sel_year" name="year"><option value="0">--</option><option value="2017">2017</option><option value="2016">2016</option><option value="2015">2015</option><option value="2014">2014</option><option value="2013">2013</option><option value="2012">2012</option><option value="2011">2011</option><option value="2010">2010</option><option value="2009">2009</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option><option value="1904">1904</option><option value="1903">1903</option><option value="1902">1902</option><option value="1901">1901</option><option value="1900">1900</option></select>年
        <select id="sel_month"name="month"><option value="0">--</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option></select>月
        <select id="sel_day"name="day"><option value="0">--</option></select>日
      	</p>
	</div>
	   			</li>
	   			<div class="reg_login">
    				<label>
    					<span></span>
    					<b class="count">0个字符</b>
    				</label>
    			</div>
	   			<li class="form_item">
	   				<label><span>QQ：</span></label>
	   				<input type="text" class="text_item" name="qq" id="qq" autocomplete='off' >
	   				<span class="msg"><i></i>以正确的格式书写</span>
	   			</li>
	   			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count1" class="count">0个字符</b>
    				</label>
    			</div>
	   			<li class="form_item">
	   				<label><span>通讯地址：</span></label>
	   				<div>
	   				省：<select id="Select1" name="cmbProvince"></select>
	   				市：<select id="Select2" name="cmbCity"></select>
	   				区：<select id="Select3" name="cmbArea"></select>
	   				</div>
	   			</li>
	   			<div class="reg_login">
    				<label>
    					<span></span>
    					<b id="count1" class="count">0个字符</b>
    				</label>
    			</div>
	   			<li class="form_item">
	   				<label><span>爱好：</span></label>
	   				<input type="text" class="text_item" name="habit" id="habit" autocomplete='off' >
	   				<span class="msg"><i></i>输入至少一个爱好</span>
	   			</li>
	   			<div class="reg_login">
    				<label>
    					<span></span>
    					<b  class="count">0个字符</b>
    				</label>
    			</div>
	   			<li class="form_item">
	   				<label><span>座右铭：</span></label>
	   				<input type="text" class="text_item" name="motto" id="motto" autocomplete='off' >
	   				<span class="msg"><i></i>输入座右铭</span>
	   			</li>
	   			<div class="reg_login">
    				<label>
    					<span></span>
    					<b class="count">0个字符</b>
    				</label>
    			</div>
	   		</ul>
	   		<input type="submit" value="保存修改" class="inbutton"  id="inbutton1" disabled>  
	   		<input type="reset" value="取消" class="inbutton"  id="inbutton2">
	     	</form>
	    </div><!-- fr1完 -->

	<div class="per_Data fr2" >
			
			    <img src="icon/head_img.png" class="head_img"  id="head_img">
			    <form action="" method="post">
				<a class="file" onchange="xuan()">选择图像<input type="file" class="local_img" id="fileup"></a>
			</form>
			    <script>  function xuan(){
			    	var oFReader = new FileReader();
			   	    var file = document.getElementById('fileup').files[0];
			   	    oFReader.readAsDataURL(file);
			   	    oFReader.onloadend = function(oFRevent){
			   	        var src = oFRevent.target.result;
			   	        if(fileup.value!=""){
			   	        	document.getElementById("head_img").src=src;
			   	        	 }
			   	    }
			    };</script>
	    </div><!-- per_Data fr2wan-->
	  
	<div class="per_Data fr3"  >
             <div id="Modify_ph">
             	<form action="" method="post">
             	<ul>
             	<li><label><span>当前密码：</span></label>
             		<input type="text" name="pwd0" autocomplete='off'  id="pwd0"><span class="msg"><i></i></span></li>
             	<span ><b class="spb" id="count2">0个字符</b></span>
             	<li><label><span>原手机号码：</span></label>
             		<input type="text" name="tel0" autocomplete='off'  id="tel0"><span class="msg"><i></i></span></li>
             		<span ><b class="spb"  id="count3">0个字符</b></span>
             	<li><label><span>新手机号码：</span></label>
             		<input type="text" name="tel1" autocomplete='off' id="tel1">
             		<input type="button" value="获取验证码"  style="width:17%; height: 28px; margin-left: 3px;margin-top: 0px;" class="fr3_but_ver">
             		</li>
             		<span ><b class="spb"  id="count4">0个字符</b></span>
             	<li><label><span>短信验证码：</span></label>
             		<input type="text" name="pwd3" autocomplete='off' ></li>
             	</ul>
             		<input type="submit" value="保存修改" class="inbutton"   id="inbutton3"disabled>  
	   		<input type="reset" value="取消" class="inbutton"  id="inbutton4">
             	</form>
             </div>

	    </div><!-- fr3完 -->

	    <div class="per_Data fr4" >
             <div id="mail">
             	<form action="" method="post">
             	<ul>
             	<li><label><span>当前密码：</span></label>
             		<input type="text" name="pwd1"  autocomplete='off'  id="pwd1">
             		<span class="msg"><i></i></span>
             		</li>
             		<span ><b class="spb"  id="count5">0个字符</b></span>
             	<li><label><span>原邮箱：</span></label>
             		<input type="text" name="email0" autocomplete='off'  id="email0">
             		<span class="msg"><i></i></span>
             	</li>
             	<span ><b class="spb"  id="count6">0个字符</b></span>
             	<li><label><span>新邮箱：</span></label>
             		<input type="text" name="email1" autocomplete='off'  id="email1">
             		<span class="msg"><i></i></span>
             		</li>
             		<span ><b class="spb"  id="count7">0个字符</b></span>
             	</ul>
             		<input type="submit" value="保存修改" class="inbutton"  id="inbutton5" disabled>  
	   		<input type="reset" value="取消" class="inbutton"  id="inbutton6">
             	</form>
             </div>
	    </div><!-- fr4完 -->

	    <div class="per_Data fr5" >
             <div id="password">
             	<form action="" method="post">
             	<ul>
             	<li><label><span>当前密码：</span></label>
             		<input type="text" name="pwd2" id="pwd2">
             		<span class="msg"><i></i></span>
             	</li>
             	<span><b class="spb"  id="count8">0个字符</b></span>
             	<li><label><span>修改密码：</span></label>
             		<input type="text" name="pwd3" id="pwd3">
             		<span class="msg"><i></i></span>
             	</li>
    				<span class="lasem">
    					<em class="em_active">弱</em><em>中</em><em>强</em>
    				</span>
    				<br>
             	<li>
             	<label><span>确认密码：</span></label>
             		<input type="text" name="pwd4" id="pwd4" disabled>
             		<span class="msg"><i></i></span>
             	</li>
             	<span ><b class="spb"  id="count9">0个字符</b></span>
             	</ul>
             		<input type="submit" value="保存修改" class="inbutton"  id="inbutton7"disabled>  
	   		<input type="reset" value="取消" class="inbutton"  id="inbutton8">
             	</form>
             </div>
	    </div><!-- fr5完 -->
<div class="per_Data fr9"  >
	             <form>
	                <h2>通知筛选:</h2>
	             	<input name="limit" type="button"  value="系统通知" /> 
                    <input name="limit" type="button" value="关联人通知"  />
                    <input name="limit" type="button" value="好友通知"  /> 
                    <input name="limit" type="button" value="自定义"  /><br/>
                    <span>
                    <input type="submit" value="保存修改" class="inbutton">  
	   		<input type="reset" value="取消" class="inbutton">
             		</span>
               </form>
        </div><!-- fr9完 -->
    </div><!-- divs完 -->
   
</div><!-- main clearfix完 -->
</body>	
<script type="text/javascript">  

</script> 
</html>