package cn.smartpolice.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.webservice.ManagerAdminService;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.SysInfo;


@SuppressWarnings("serial")
@Controller("managerAdminAction")
@Scope("prototype")
public class ManagerAdminAction extends ActionSupport{

	@Resource(name="managerAdminService")
	private ManagerAdminService managerAdminService;

	public ManagerAdminService getManagerAdminService() {
		return managerAdminService;
	}

	public void setManagerAdminService(ManagerAdminService managerAdminService) {
		this.managerAdminService = managerAdminService;
	}
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	
	//查找全部厂商
	public String allCompany(){
		List allCompany = this.managerAdminService.findAllCompany();
		
		/*System.out.println(allCompany);*/
		session.setAttribute("allCompany", allCompany);
		System.out.println(allCompany.size());
		System.out.println(allCompany.get(0).toString());
		
		return SUCCESS;
	}
	//查找全部前端设备
	public String allDevice(){
		List allDevice = this.managerAdminService.findAllDevice();
		session.setAttribute("allDevice", allDevice);
		System.out.println(allDevice);
		return "success";
	}
	
	//查找全部移动设备
	public String allMoveDevice(){
		List allMoveDevice = this.managerAdminService.findAllMoveDevice();
		session.setAttribute("allMoveDevice", allMoveDevice);
//		System.out.println(allMoveDevice);
		return "success";
	}
	//查找全部APP用户
	public String allUserinfo(){
		List allUserinfo = this.managerAdminService.findAllUserinfo();
		session.setAttribute("allUserinfo", allUserinfo);
		System.out.println("alluserinfo"+allUserinfo);
		return "success";
	}
	//查找全部服务器信息
		public String allWebservice(){
			List allWebservice = this.managerAdminService.findAllWebservice();
			session.setAttribute("allWebservice", allWebservice);
			System.out.println(allWebservice);
			return "success";
		}
		//查找全部设备报警消息
		public String allMsg_alarms(){
					List allmsg_alarms = this.managerAdminService.findAllMsg_alarms();
					session.setAttribute("allmsg_alarms", allmsg_alarms);
					System.out.println("allmsg_alarms"+allmsg_alarms);
					return "success";
				}
		//查找全部聊天消息
		public String allMsg_chat(){
					List allmsg_chat = this.managerAdminService.findAllMsg_chat();
//					List list1 = (List) allmsg_chat.get(0);
//					List list2 = (List) allmsg_chat.get(1);
//					session.setAttribute("list1", list1);
					session.setAttribute("allmsg_chat", allmsg_chat);
					System.out.println("allmsg_chat"+allmsg_chat);
				
					return "success";
				}
		//查找全部通知消息
				public String allMsg_notice(){
							List allmsg_notice = this.managerAdminService.findAllMsg_notice();
							session.setAttribute("allmsg_notice", allmsg_notice);
							System.out.println("allmsg_notice"+allmsg_notice);
							return "success";
						}
		//查找全部反馈消息
				public String allComment(){
							List allcomment = this.managerAdminService.findAllComment();
							session.setAttribute("allcomment", allcomment);
							System.out.println("allcomment"+allcomment);
							return "success";
						}
				
		//查看厂商的详细信息		
			public String CompanyDetail1(){
					
					//HttpServletRequest request = ServletActionContext.getRequest();
					String auditid1 = request.getParameter("id");
					int companyid = Integer.valueOf(auditid1); 
					List allCompanyInfo=this.managerAdminService.CompanyDetail1(companyid); 
					session.setAttribute("allCompanyInfo", allCompanyInfo);
					System.out.println("allCompanyInfo"+allCompanyInfo);
					return "success";
				   }
			
			//查看前端设备的详细信息		
			public String PredeviceDetailOfManager(){
					
					HttpServletRequest request = ServletActionContext.getRequest();
					String auditid1 = request.getParameter("id");
					int auditid = Integer.valueOf(auditid1); 
					List allPredeviceInfo=this.managerAdminService.PredeviceDetailOfManager(auditid); 
					session.setAttribute("allPredeviceInfo", allPredeviceInfo);
					System.out.println("allPredeviceInfo"+allPredeviceInfo);
					return "success";
				   }
			/*
			 //这个查询可以直接用前端设备的查询
			//查看移动设备的详细信息
			public String MovedeviceDetailOfManager(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String auditid1 = request.getParameter("id");
				int auditid = Integer.valueOf(auditid1); 
				List allPredeviceInfo=this.managerAdminService.PredeviceDetailOfManager(auditid); 
				session.setAttribute("allPredeviceInfo1", allPredeviceInfo);
				System.out.println("allPredeviceInfo1"+allPredeviceInfo);
				return "success";
			   }
			*/
			//查看注册用户的详细信息
			public String UserDetail1(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String userid1 = request.getParameter("id");
				int userid = Integer.valueOf(userid1); 
				List UserInfo=this.managerAdminService.UserDetail1(userid); 
				session.setAttribute("UserInfo", UserInfo);
				System.out.println("UserInfo"+UserInfo);
				return "success";
			   }
			
			//查看服务器的详细信息
			public String ServiceOfChecked(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String serviceid1 = request.getParameter("id");
				int serviceid = Integer.valueOf(serviceid1); 
				List allServiceInfo=this.managerAdminService.ServiceOfChecked(serviceid); 
				session.setAttribute("allServiceInfo", allServiceInfo);
				System.out.println("allServiceInfo"+allServiceInfo);
				return "success";
			   }
			//查看报警消息的详细信息
			public String All_msg_alarms(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String alarmid1 = request.getParameter("id");
				int alarmid = Integer.valueOf(alarmid1); 
				List allalarmmsgInfo=this.managerAdminService.All_msg_alarms(alarmid); 
				session.setAttribute("allalarmmsgInfo", allalarmmsgInfo);
				System.out.println("allalarmmsgInfo"+allalarmmsgInfo);
				return "success";
			   }
			
			//查看聊天消息的详细信息
			public String All_msg_chat(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String chatid1 = request.getParameter("id");
				int chatid = Integer.valueOf(chatid1); 
				List allchatmsgInfo=this.managerAdminService.All_msg_chat(chatid); 
				session.setAttribute("allchatmsgInfo", allchatmsgInfo);
				System.out.println("allchatmsgInfo"+allchatmsgInfo);
				return "success";
			   }
			
			//查看通知消息的详细信息
			public String All_msg_notice(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String noticeid1 = request.getParameter("id");
				int noticeid = Integer.valueOf(noticeid1); 
				List allnoticemsgInfo=this.managerAdminService.All_msg_notice(noticeid); 
				session.setAttribute("allnoticemsgInfo", allnoticemsgInfo);
				System.out.println("allnoticemsgInfo"+allnoticemsgInfo);
				return "success";
			   }
			
			//查看反馈消息的详细信息
			public String Allcomment(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String commentid1 = request.getParameter("id");
				int commentid = Integer.valueOf(commentid1); 
				List allcommentInfo=this.managerAdminService.Allcomment(commentid); 
				session.setAttribute("allcommentInfo", allcommentInfo);
				System.out.println("allcommentInfo"+allcommentInfo);
				return "success";
			   }

			//删除厂商用户信息
			public String removeCompanyUser(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.removeCompanyUser(id);
				return "success";
			}
			//删除前端设备
			public String Removedevice(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.Removedevice(id);
				return "success";
			}	
			//删除用户信息
			public String Removesuer(){
				System.out.println("删除用户信息");
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.Removesuer(id);
				return "success";
			}
			//删除移动设备信息
			public String RemoveMovedevice(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.RemoveMovedevice(id);
				return "success";
			}
			//删除报警消息
			public String RemoveMsg_alarm(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.RemoveMsg_alarm(id);
				return "success";
			}
			//删除聊天消息
			public String RemoveMsg_chat(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.RemoveMsg_chat(id);
				return "success";
			}

			//删除通知消息
			public String RemoveMsg_notice(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.RemoveMsg_notice(id);
				return "success";
			}
			//删除反馈消息
			public String RemoveMsg_comment(){
				HttpServletRequest request = ServletActionContext.getRequest();
				String id1 = request.getParameter("id");
				int id = Integer.valueOf(id1);
				this.managerAdminService.RemoveMsg_comment(id);
				return "success";
			}
			//登录设备详细
			public String allLogDev(){
				
				HttpServletRequest request = ServletActionContext.getRequest();
				String devid1 = request.getParameter("id");
				int devid = Integer.valueOf(devid1); 
				List devInfo=this.managerAdminService.allLogDev(devid); 
				session.setAttribute("devInfo", devInfo);
				return "success";
			   }
			//登录用户详情
			public String LogUserAll(){
				HttpServletRequest request = ServletActionContext.getRequest();
				System.out.println("执行方法");
				String devid1 = request.getParameter("id");
				System.out.println("得到的id:"+devid1);
				int devid = Integer.valueOf(devid1); 
				List userInfo=this.managerAdminService.logUserAll(devid); 
				session.setAttribute("logUserAll", userInfo);
				return "success";
			}	
			
			
			//本月新增设备	
			public String FindAddDev(){
			int count=0;
				List findAddDev = this.managerAdminService.FindAddDev();
	
				Date date1 = new Date();
				int month1=date1.getMonth();
				//long t1= date1.getTime();
				for(Object object :findAddDev) {  
		            Map obj = (Map) object;  
		            Date date =(Date) obj.get("date");  
		        // long t2=date.getTime();
		            int month2=date.getMonth();
		            System.out.println(month2);
		            if(month1==month2){
					count++;
		            }
				}
					
						session.setAttribute("addDevNum", count);
						return "success";
			}
			//本月新增用户
			public String FindAddUser(){
				int count=0;
					List findAddUser = this.managerAdminService.FindAddUser();
							Date date1 = new Date();
							int month1=date1.getMonth();
							//long t1= date1.getTime();
							for(Object object :findAddUser) {  
					            Map obj = (Map) object;  
					            Date date =(Date) obj.get("regdate");  
					        // long t2=date.getTime();
					            int month2=date.getMonth();
					            System.out.println(month2);
					            if(month1==month2){
								count++;
					            }
							}
						
							session.setAttribute("addUserNum", count);
							return "success";
				}
		
			
			public String removeLogUser(){
				System.out.println("执行");
				HttpServletRequest request= ServletActionContext.getRequest();
				int userid= Integer.parseInt(request.getParameter("id"));
				System.out.println("登录用户id "+userid);
				return "success";
			}
			
		public String updateQues(){
			System.out.println("----------密保修改----------");
			HttpServletRequest request= ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String answer="";
			int num=0;
			//获取答案和答案的编号
			if(request.getParameter("answer1")!=null){
				answer=request.getParameter("answer1");
				num=1;
			}else if(request.getParameter("answer2")!=null){
				answer=request.getParameter("answer2");
				num=2;
			}
			System.out.println("修改问题"+num);
			//验证合法性
			if(!answer.equals("")&&answer!=null){
				//取的用户的id号
				ManagerInfo manager=(ManagerInfo) session.getAttribute("manager");
				int id=manager.getManagerId();
				System.out.println("输入的答案为:"+answer);
				System.out.println("用户id号为:"+id);
				//执行更新操作
				String mark = this.managerAdminService.updateQues(id, num, answer);
				if(mark.equals("success")){
					System.out.println("修改成功...");
				}
				return "success";
			}else{
				System.out.println("数据有误，退出修改...");
				return "error";
			}
		}
		
		public void getManNum(){
			System.out.println("----------注册状态获取----------");
			HttpServletRequest request= ServletActionContext.getRequest();
			
			//未审核
			int unexamine=15;
			//通过
			int pass=16;
			//未通过
			int notPass=24;
			//冻结
			int frozen=21;
			
			//标记表单的名字和状态
			String table="";
			String tableDate="";
			int type=0;
			table=(String) request.getAttribute("table");
			if(request.getAttribute("type")!=null){
				type=(int) request.getAttribute("type");
			}
			
			//过滤
			if(table.equals("company")){
				tableDate="company_user";
			}else if(table.equals("device")){
				tableDate="device_inf";
			}else if(table.equals("user")){
				tableDate="user_inf";
			}
			
			//获取状态的值
			unexamine=this.managerAdminService.getStatNum(0,tableDate,type);
			pass=this.managerAdminService.getStatNum(1,tableDate,type);
			notPass=this.managerAdminService.getStatNum(-1,tableDate,type);
			frozen=this.managerAdminService.getStatNum(2,tableDate,type);
			
			System.out.println(table+"信息如下:");
			System.out.println("type为："+type);
			System.out.println("未审核："+unexamine);
			System.out.println("通过："+pass);
			System.out.println("未通过："+notPass);
			System.out.println("冻结："+frozen);
			//传值
			request.setAttribute("unexamine", unexamine);
			request.setAttribute("pass", pass);
			request.setAttribute("notPass", notPass);
			request.setAttribute("frozen", frozen);
			System.out.println("获取状态action启动完毕...");
//			System.out.println(session.getAttribute("examine"));
			
		}
}
