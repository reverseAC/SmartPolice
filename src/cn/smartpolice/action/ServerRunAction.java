package cn.smartpolice.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.ServerInfo;
import cn.smartpolice.hibernate.SystemRunInfo;
import cn.smartpolice.webservice.AddServersService;
import cn.smartpolice.webservice.SystemRunInfoService;
import cn.smartpolice.webservice.SystemRunInfoServiceImpl;
import cn.smartpolice.workbean.SysInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.ws.api.message.Message;

@SuppressWarnings("serial")
@Controller("serverRunAction")
@Scope("prototype")

public class ServerRunAction extends ActionSupport {
	
	private ServerInfo addServers;
	
//	private PlatMsgAnnounce platMsgAnnounce;
	  
	private SystemRunInfo systemRunInfo;
	
	@Resource(name = "addServersService")
	private AddServersService addServersService;
	
//	@Resource(name = "platMsgAnnounceService")
//	private PlatMsgAnnounceService platMsgAnounceService;
//	
	@Resource(name  = "systemRunInfoService")
	private SystemRunInfoService systemRunInfoService;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	HttpSession session = request.getSession();
	
	public ServerInfo getAddServers() {
		return addServers;
	}

	public void setAddServers(ServerInfo addServers) {
		this.addServers = addServers;
	}

/*	public PlatMsgAnnounce getPlatMsgAnnounce() {
		return platMsgAnnounce;
	}

	public void setPlatMsgAnnounce(PlatMsgAnnounce platMsgAnnounce) {
		this.platMsgAnnounce = platMsgAnnounce;
	}*/

	public SystemRunInfo getSystemRunInfo() {
		return systemRunInfo;
	}

	public void setSystemRunInfo(SystemRunInfo systemRunInfo) {
		this.systemRunInfo = systemRunInfo;
	}

	public AddServersService getAddServersService() {
		return addServersService;
	}

	public void setAddServersService(AddServersService addServersService) {
		this.addServersService = addServersService;
	}

/*	public PlatMsgAnnounceService getPlatMsgAnounceService() {
		return platMsgAnounceService;
	}

	public void setPlatMsgAnounceService(PlatMsgAnnounceService platMsgAnounceService) {
		this.platMsgAnounceService = platMsgAnounceService;
	}*/

	public SystemRunInfoService getSystemRunInfoService() {
		return systemRunInfoService;
	}

	public void setSystemRunInfoService(SystemRunInfoService systemRunInfoService) {
		this.systemRunInfoService = systemRunInfoService;
	}
	
	public String AddServers(){
		 addServersService.AddServers(addServers);
		 return "addservers";
	}
	
	public String PlatMsgAnounce(){
//		platMsgAnounceService.AddPlatMsgToDB(platMsgAnnounce);
		return "platmsganounce";
	}
	
	public String SystemRunInfo(){
		List info = systemRunInfoService.getSystemRunInfoFromDB();
		session.setAttribute("systemRunInfo", info);
		return "systemruninfo";
	}
	//查找所有服务器信息
	public String AllServers(){
		List allServers=systemRunInfoService.AllServers();
		session.setAttribute("allServers",allServers);
		System.out.println(allServers);
		return "success";
	}
	//服务器详细信息
	public String ServerInfo(){
		String serverid1 = request.getParameter("id");
		int serverid = Integer.valueOf(serverid1); 
		List serverInfo=this.systemRunInfoService.ServerInfo(serverid);
		session.setAttribute("serverInfo", serverInfo);
		System.out.println("serverInfo"+serverInfo);
		
		return "success";
	}
	//删除服务器
	public String RemoveServer(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		int id = Integer.valueOf(id1);
		this.systemRunInfoService.RemoveServer(id);
		return "success";
	}
	//增添服务器信息
	public String AddServer(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();	
		String type = request.getParameter("type");
		
		String CPU = request.getParameter("CPU");
		String memory = request.getParameter("memory");
		String demo = request.getParameter("demo");
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		String managerid= request.getParameter("manager");
		ServerInfo server=new ServerInfo();
		System.out.println(SysInfo.getuIUserInfo().getId());
		System.out.println(managerid.equals(SysInfo.getuIUserInfo().getId()+""));
		System.out.println("方法执行"+"c.."+CPU+"t.."+type+"m.."+memory+"d.."+demo+"i.."+ip+"p.."+port+"mi.."+managerid);
		System.out.println("管理员id为"+SysInfo.getuIUserInfo().getId());
		if(type!=null&&type!=""&&CPU!=null&&CPU!=""&&memory!=null&&memory!=""&&demo!=null&&demo!=""&&ip!=null&&ip!=""&&port!=null&&port!=""&&managerid.equals(SysInfo.getuIUserInfo().getId()+"")){
		server.setCPU(CPU);
		server.setDemo(demo);
		server.setMemory(Integer.parseInt(memory));
		server.setType(Integer.parseInt(type));
		server.setIp(ip);
		server.setPort(Integer.parseInt(port));
		server.setManager(Integer.parseInt(managerid));
		this.addServersService.AddServers(server);	
	}
		
		return "success";
	}
	
	//获取系统日志
	public String SystemLog(){
		System.out.println("serverrunaction");
		List systemLog=this.systemRunInfoService.SystemLog();
		session.setAttribute("systemLog",systemLog);
		System.out.println(systemLog);		
		return "systemLog";
	}

	public String DeleteSystemLog(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		System.out.println("id1=" + id1);
		int Imanageid = Integer.valueOf(id1);
		this.systemRunInfoService.deleSystemLog(Imanageid);
		return "success";
	}

}
