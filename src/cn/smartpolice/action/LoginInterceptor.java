package cn.smartpolice.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.ManagerInfo;
/*
 * 自定义拦截器
 * 未登录用户不可以操作其他页面
 */
public class LoginInterceptor extends MethodFilterInterceptor{

	@Override
	public String doIntercept(ActionInvocation  invocation) throws Exception {
		  // 取得请求相关的ActionContext实例
		Map session = ActionContext.getContext().getSession();  
        ManagerInfo user=(ManagerInfo) session.get("manager");
        CompanyUser firm = (CompanyUser)session.get("CompanyUser");
        HttpServletRequest request= ServletActionContext.getRequest();
        String url = request.getRequestURL().toString();
        System.out.println(url);
        String returnString=null;
        if(url.contains("Company")){
        	 if (firm != null) {             	
                 return invocation.invoke();  
             }  else{// if(firm!=null)
            	 returnString = "Clogin";
             	System.out.println("需要登录");             
             }
        }
        if(url.contains("Manager")){
        	 if (user != null) {             	
                 return invocation.invoke();  
             }  else{// if(firm!=null)
            	 returnString = "Mlogin";
             	System.out.println("需要登录");             
             }
        }
        System.out.println("拦截器工作"+request.getRequestURL());
        // 如果没有登陆，即用户名不存在，都返回重新登陆     
		return returnString;
       
  
    }


	
	

}
