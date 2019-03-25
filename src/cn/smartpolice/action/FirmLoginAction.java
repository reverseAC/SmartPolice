package cn.smartpolice.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;
import cn.smartpolice.dao.DeviceInfoDao;
import cn.smartpolice.hibernate.CompanyInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;
import cn.smartpolice.webservice.FirmInfoService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import antlr.collections.List;

/**
 * 厂商动作类
 * 
 * */
@SuppressWarnings("serial")
@Controller("firmLoginAction")
@Scope("prototype")
public class FirmLoginAction extends ActionSupport {
	@Resource(name="firmInfoService")
	
	private FirmInfoService firmInfoService;
	
	public FirmInfoService getFirmInfoService() {
		return firmInfoService;
	}
	public void setFirmInfoService(FirmInfoService firmInfoService) {
		this.firmInfoService = firmInfoService;
	}
	HttpServletRequest request= ServletActionContext.getRequest();
	HttpSession session=request.getSession();
	private Map<String,Object>application;
	
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * 厂商用户登录
	 * 
	 */
	@SuppressWarnings("unused")
	public String login() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String username=request.getParameter("men");
		String pwd=request.getParameter("psd");
		String flag = request.getParameter("remember");//判断是否记住密码
		ActionContext context = ActionContext.getContext();
		application = context.getApplication();
		HttpServletResponse response =ServletActionContext.getResponse();
//		MessageDigest md5=MessageDigest.getInstance("MD5");
//      BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
//       String password = base64en.encode(md5.digest(pwd2.getBytes("utf-8")));     
//       System.out.println(password);
       
		System.out.println("厂商登录");
		try {
		if(username!=null && pwd!=null && !username.equals("") && !pwd.equals("")){
			CompanyUser firm=this.firmInfoService.login(username);
			session.setAttribute("CompanyUser",firm);
			session.setAttribute("username", username);
			if(firm!=null){

			int id1=firm.getUserId();
			session.setAttribute("id", id1);
			System.out.println(id1);
								
			if(username.equals(firm.getUserName()) && pwd.equals(firm.getPassword())){
					

				session.setAttribute("name",firm.getName()); //得到厂商登录的名字
				int companyid=firm.getCompanyId().getCompanyId();
				java.util.List list=this.firmInfoService.devicepas(companyid);//厂商登录，返回已审核设备数								
				session.setAttribute("device_unpass",list.size());					
				session.setAttribute("username", username);
				CompanyUser firm1=this.firmInfoService.login(username);   //得到厂商对象
				System.out.print(firm.getUserId());
				java.util.List list1=this.firmInfoService.comment(firm);  //根据厂商返回反馈信息数		
				session.setAttribute("comment", list1.size());	
				System.out.print(firm.getUserName());					
				java.util.List list2=this.firmInfoService.soft_unpass(firm1.getCompanyId().getCompanyId());
				session.setAttribute("soft_unpass", list2.size());		
				return SUCCESS;
			} else{
			String message="账号或者密码错误";
			session.setAttribute("message", message);
			//application.put("message", message);
			System.out.print(message);
			return ERROR;
			}				
		} else {
			String message="账号不存在";
			session.setAttribute("message", message);
			//application.put("message", message);
			System.out.print(message);
			return ERROR;}							
		} else 	{
			String message="账号或密码不能为空";
			
			CompanyUser firm1=this.firmInfoService.login(username);
			java.util.List list1=this.firmInfoService.soft_unpass(firm1.getCompanyId().getCompanyId());
			
			
			application.put("message", message);
			session.setAttribute("message", message);
			//System.out.print(message);
				return ERROR;}

		}catch (Exception e) {
			System.out.println("抛出了异常"+e);
			System.out.print(message);
			return ERROR;
		}
		
	}
	
	
	//用户注销方法	
	public String logout(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.clear();
		return SUCCESS;		
	}
	

	/*
		 * 方法功能：厂商用户注册
		 */
		public String firmregister() throws NoSuchAlgorithmException, UnsupportedEncodingException{
			
			System.out.println("-----厂商用户注册-----");
			
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String username = request.getParameter("Username");
			String name = request.getParameter("Name");
			String pwd = request.getParameter("Password");
			String repwd=request.getParameter("rePassword");
			System.out.println(username);
			if(pwd!=null&&repwd!=null&&pwd.equals(repwd)){
				pwd=repwd;
			}else{
				session.setAttribute("mark", "flase");
				System.out.println("密码不一样，注册失败...");
				this.addActionError("密码不相同，注册失败...");
				return ERROR;
//				return SUCCESS;
			}
			
//			//md5加密
//			MessageDigest md5=MessageDigest.getInstance("MD5");
//	        BASE64Encoder base64en = new BASE64Encoder();
//	        //加密后的字符串
//	       String pwd1=base64en.encode(md5.digest(pwd.getBytes("utf-8")));
			int companyid=0;
			String email= request.getParameter("Email");
			String number = request.getParameter("Number");
			String position = request.getParameter("Position");
			String companyid1 = request.getParameter("CompanyId");
			if(companyid1!=null){
				companyid = Integer.valueOf(companyid1);
			}else{
				this.addActionError("公司ID为空");
				return ERROR;
			}
			String state="0";
			//String string=this.firmInfoService.checkuser(username);//验证用户名

				CompanyUser firm=new CompanyUser();
				//根据companyid的值在companyinfo中查找
				CompanyInfo firm1=this.firmInfoService.register(companyid);
				CompanyUser firm2=this.firmInfoService.login(username);
				if (firm2 != null) {
					System.out.println("用户名已有");
					this.addActionError("用户名已存在");
					return ERROR;//这里在页面上的响应应该是提示用户，现在只是跳转到注册页面
					
				}
				if (firm1 == null) {
					System.out.println("没有这个厂商");//在这里响应应该是先提醒用户没有厂商然后在跳转到厂商注册页面，现在没有厂商注册
					this.addActionError("没有这个厂商");
					return ERROR;
				}else {
					firm.setPassword(pwd);
					firm.setUserName(username);
					firm.setName(name);
					firm.setNumber(number);
					firm.setPosition(position);
					firm.setState(state);
					firm.setEmail(email);
					firm.setCompanyId(firm1);
					
					this.firmInfoService.register(firm);
					session.setAttribute("Username", username);
					return SUCCESS;
				}
			
			}


}

	

