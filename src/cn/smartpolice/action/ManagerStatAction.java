package cn.smartpolice.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.webservice.ManagerStatService;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.DevNode;
import cn.smartpolice.workbean.MsgTask;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@Controller("managerStatAction")
@Scope("prototype")
public class ManagerStatAction {

	@Resource(name = "managerStatService")
	private ManagerStatService managerStatService;

	public ManagerStatService getManagerStatService() {
		return managerStatService;
	}

	public void setManagerStatService(ManagerStatService managerStatService) {
		this.managerStatService = managerStatService;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	/*
	 * 显示厂商用户注册情况报表
	 */
	public String StatCompany() {

		List list = managerStatService.getCompanyData();// 获得所有图表数据
		session.setAttribute("list", list);

		return "success";

	}

	/*
	 * 显示前端设备注册情况报表
	 */
	public String StatDevice() {

		List list = managerStatService.getDeviceData();// 获得所有图表数据
		session.setAttribute("list", list);

		return "success";

	}

	public String StatMoveDevice() {

		List list = managerStatService.getMoveDeviceData();// 获得所有图表数据
		session.setAttribute("list", list);

		return "success";

	}

	public String StatUserinfo() {

		List list = managerStatService.getUserinfo();// 获得所有图表数据
		session.setAttribute("list", list);

		return "success";

	}

	// 登录设备信息
	public String StatLogdev() {
		Set<UserNode> userNodeQueue = SysInfo.getUserNodeQueue();
		List<Integer> devIds = new ArrayList<Integer>();
		for (UserNode userNode : userNodeQueue) {
			if (userNode instanceof DevNode) {
				DevNode devNode = (DevNode) userNode;
				if (devNode.getState() == 2) {// 在线
					devIds.add(devNode.getId());
				}
			}
		}
		List devList = this.managerStatService.StatLogdev(devIds);
		session.setAttribute("logDev", devList);
		/*
		 * if (packetInfo.getSort() == ConstParam.SORT_2) { UserNode userNode =
		 * SysInfo.getInstance().getDevNodeById(packetInfo.getSid()); }
		 */
		return "success";
	}

	public String notifyIssued() {
		

		String title;
		String object;
		String content;
		
		title = request.getParameter("title");
		object = request.getParameter("object");
		content = request.getParameter("content");
		if(title!=null&&!title.equals("")&&content!=null&&!content.equals("")){
		Date date = new Date();
		JSONObject data = new JSONObject();
		data.put("TITLE", title);
		data.put("MSG", content);
		data.put("SET", "UTF-8");
		/*
		 * <option value="1">App用户</option> 
		 * <option value="2">厂商用户</option>
		 * <option value="3">设备管理员</option> 
		 * <option value="4">管理员</option>
		 * <option value="5">全体成员</option>
		 */
		MsgTask msgTask = new MsgTask();
		msgTask.setContent(data.toString());
		msgTask.setmDate(date.getTime());
		msgTask.setMsgNum(1);
		msgTask.setmType(4);
		int managerID = SysInfo.getuIUserInfo().getId();
		msgTask.setSendUserID(managerID);
		/*
		 * 接收者类别(<100)：
				0 所有APP用户；1前端设备关联人；2 所有设备管理员；3 厂商设备用户；4管理员；5全体所有成员
			由协议定义
		 */
		if(object.equals("1")) {
			msgTask.setRevUserID(0);
			System.out.println("执行");
			}else if(object.equals("2")){
				msgTask.setRevUserID(3);
			}else if(object.equals("3")){
				msgTask.setRevUserID(2);
			}else if(object.equals("4")){
				msgTask.setRevUserID(4);
			}else if(object.equals("5")){
				msgTask.setRevUserID(5);
			}else{
				System.out.println("不支持");
			}
			SysInfo.getMsgTaskQueue().add(msgTask);
			SysInfo.getInstance().msgTaskThreadCheck();			
		
		
			return "success";
	}
		return "error";
	}

	public String StatLogUser() {
		Set<UserNode> userNodeQueue = SysInfo.getUserNodeQueue();
		List<Integer> userIds = new ArrayList<Integer>();
		for (UserNode userNode : userNodeQueue) {
			if (userNode instanceof AppNode) {
				AppNode appNode = (AppNode) userNode;
				if (appNode.getState() == 2) {// 在线
					userIds.add(appNode.getId());
				}
			}
		}
		List userList = this.managerStatService.StatLogUser(userIds);
		session.setAttribute("logUser", userList);
		/*
		 * if (packetInfo.getSort() == ConstParam.SORT_2) { UserNode userNode =
		 * SysInfo.getInstance().getDevNodeById(packetInfo.getSid()); }
		 */
		return "success";
	}

}
