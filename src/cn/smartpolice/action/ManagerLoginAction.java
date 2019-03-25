package cn.smartpolice.action;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.smartpolice.hibernate.AdminLog;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.webservice.ManagerInfoService;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UIUserInfo;

/**
 * 管理员动作类
 * 
 * 
 * */

@SuppressWarnings("serial")
@Controller("managerLoginAction")
@Scope("prototype")
public class ManagerLoginAction extends ActionSupport{
	@Resource(name="managerInfoService")
	private ManagerInfoService managerInfoService;

	public ManagerInfoService getManagerInfoService() {
		return managerInfoService;
	}

	public void setManagerInfoService(ManagerInfoService managerInfoService) {
		this.managerInfoService = managerInfoService;
	}
	
	
	public String login() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		ServletContext application=request.getServletContext();
		Map<String, String> managerId=new HashMap<String, String>();
		HttpServletResponse response =ServletActionContext.getResponse();
		System.out.println("地址："+request.getContextPath());
		System.out.println("得到id："+session.getId());
		int viewusernum=SysInfo.getSysStatInfo().getViewusernum();
		String username=request.getParameter("user");
		String pwd=request.getParameter("pswd");
		String flag = request.getParameter("remember");//判断是否记住密码
//		MessageDigest md5=MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
//       String password = base64en.encode(md5.digest(pwd2.getBytes("utf-8")));     
//       System.out.println(password);
//		System.out.println("ssss");
		session.setAttribute("SysStatInfo", SysInfo.getSysStatInfo());//放入系统信息
		session.setAttribute("SysCfgInfo", SysInfo.getSysCfgInfo());//放入系统信息
		
		session.setAttribute("username", username);
		//获取上次登录时间
		//向cookie中放值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=	sdf.format(new Date());
		Cookie myCookie=new Cookie("lasttime",date);
		myCookie.setMaxAge(60*60*24*30);
		response.addCookie(myCookie);

		//取值
		Cookie allCookie[]= request.getCookies();
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		         String keyname=  allCookie[i].getName();
		         if(keyname.equals("lasttime")){
		        	 System.out.println("keyname:"+keyname+",value:"+allCookie[i].getValue());
		        	 session.setAttribute("LastAccessedTime",allCookie[i].getValue());
		         }
		      }
		 }
		

		System.out.println("管理员登录");
		if(username!=null && pwd!=null && !username.equals("") && !pwd.equals("")){
			ManagerInfo manager=this.managerInfoService.login(username);
			session.setAttribute("manager",manager);
			if(manager!=null){
				if(username.equals(manager.getUserName()) && pwd.equals(manager.getPassword())){
					 //创建两个Cookie对象
					Cookie nameCookie = new Cookie("username", username);
					Cookie pwdCookie = new Cookie("password", pwd);
					if ("yes".equals(flag)) {
				             //设置Cookie的有效期为3天
				            nameCookie.setMaxAge(60*60*24*3);
				            pwdCookie.setMaxAge(60*60*24*3);
				            response.addCookie(nameCookie);
				            response.addCookie(pwdCookie);
					 }else{		
						 //清空nameCookie 和 pwdCookie
								nameCookie = new Cookie("username",null);
								nameCookie.setMaxAge(0);
							    pwdCookie =new Cookie("password",null);
								pwdCookie.setMaxAge(0);
								response.addCookie(nameCookie);
						        response.addCookie(pwdCookie);
						 }
					int id = manager.getManagerId();
					session.setAttribute("managerid", id);
					UIUserInfo info = new UIUserInfo();
					info.setIp(getClientIP());
					System.out.println(info.getIp());
					info.setName(manager.getName());
					info.setType("manager");
					info.setLoginDate(new Date());
					info.setId(manager.getManagerId());

					SysInfo.setuIUserInfo(info);
					AdminLog log = new AdminLog();
					log.setManagerid(id);
					log.setActiontype("1");
					log.setIntime(new Date());
					int logid = this.managerInfoService.login(log);
					info.setLogid(logid);
					//登录之后 人数就增加
					if(application.getAttribute("managerId")!=null){
						managerId=(Map<String, String>) application.getAttribute("managerId");
						if(managerId.get(session.getId())==null){
							managerId.put(session.getId(), session.getId());
							SysInfo.getSysStatInfo().setViewusernum(++viewusernum);	
						}
					}else{
						managerId.put(session.getId(), session.getId());
						SysInfo.getSysStatInfo().setViewusernum(++viewusernum);	
					}
					application.setAttribute("managerId", managerId);
					//登录之后 人数就增加
//					SysInfo.getSysStatInfo().setViewusernum(++viewusernum);	
					//在线人数
					int count= SysInfo.getSysStatInfo().getLogindevnum()+SysInfo.getSysStatInfo().getLoginusernum()+SysInfo.getSysStatInfo().getViewusernum();
					session.setAttribute("count", count);
					return SUCCESS;

				} else {
					this.addActionError("登陆用户名或者密码错误");
					return ERROR;
				}
				
				} else {
					this.addActionError("该用户不存在");
					return ERROR;
				}
		} else {
			this.addActionError("用户名和密码不能为空");
			return ERROR;
		}
	}
	
	public String logout(){
		AdminLog out = new AdminLog();
	 	out.setActiontype("0");
	 	out.setManagerid(SysInfo.getuIUserInfo().getId());
	 	out.setOuttime(new Date());
	 	int logid = SysInfo.getuIUserInfo().getLogid();
	 	this.managerInfoService.logout(out,logid);
	 	HttpServletRequest request=ServletActionContext.getRequest();

	 	ServletContext application=request.getServletContext();
		HashMap<String, String> managerId=(HashMap<String, String>) application.getAttribute("managerId");
		HttpSession sessional=request.getSession();
		managerId.remove(sessional.getId());
		application.setAttribute("managerId", managerId);
			Map session = ActionContext.getContext().getSession();  
		 	session.clear();
		 	int viewusernum=SysInfo.getSysStatInfo().getViewusernum();
		 	SysInfo.getSysStatInfo().setViewusernum(--viewusernum);
		 	int count= SysInfo.getSysStatInfo().getLogindevnum()+SysInfo.getSysStatInfo().getLoginusernum()+SysInfo.getSysStatInfo().getViewusernum();
		
	       // session.remove("manager"); 
	      System.out.println("session已被删除");
	        return SUCCESS;  
	
		
	}
	public String getClientIP() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String ip = request.getHeader("x-forwarded-for");
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("X-Real-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}

		return ip;
		}
	public String security(){
		System.out.println("----------密保问题验证----------");
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		//获取用户名
		String username=request.getParameter("username");
		if(username!=null&&!username.equals("")){
			//执行查询
			ManagerInfo manager=this.managerInfoService.login(username);
			
			session.setAttribute("manager",manager);
			if(manager!=null){
				//接收问题答案
				String uniMaster=request.getParameter("uniMaster");
				String primarySch=request.getParameter("primarySch");
				//检查合法性
				if(uniMaster!=null&&primarySch!=null&&!uniMaster.equals("")&&!primarySch.equals("")){
					//进行对比
					if(uniMaster.equals(manager.getAnswer1())&&primarySch.equals(manager.getAnswer2())){
						System.out.println("找回的用户名为："+manager.getUserName());
						System.out.println("找回的密码为:"+manager.getPassword());
						return SUCCESS;
					}else{
						this.addActionError("密保答案错误");
						return ERROR;
					}
				}else{
					this.addActionError("密保答案不能为空");
					return ERROR;
				}
			}else{
				this.addActionError("用户名不存在");
				return ERROR;
			}
		}else{
			this.addActionError("用户名不能为空");
			return ERROR;
		}
	}
	
	public String saveNewPwdM(){
		System.out.println("----------存入新密码----------");
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		//接收密码
		String Pas=request.getParameter("Pas");
		String resPas=request.getParameter("resPas");
		//取的id号
		ManagerInfo manager=(ManagerInfo) session.getAttribute("manager");
		//检查合法性
		if(!Pas.equals("")&&Pas!=null&&!resPas.equals("")&&resPas!=null){
			
			if(Pas.equals(resPas)){
				int id=manager.getManagerId();
				System.out.println(Pas);
				System.out.println(resPas);
				System.out.println(id);
				//执行存储操作
				String str=this.managerInfoService.updatePassword(id, Pas);
				return SUCCESS;
			}else{
				this.addActionError("密码不相同");
				return ERROR;
			}
		}else{
			this.addActionError("请输入完整密码");
			return ERROR;
		}
		
		
	}

}
