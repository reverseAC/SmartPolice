package cn.smartpolice.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import cn.smartpolice.hibernate.CommentInfo;
import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.DeviceAudit;
import cn.smartpolice.hibernate.SoftInfo;
import cn.smartpolice.webservice.FirmAdminService;
import cn.smartpolice.webservice.FirmInfoService;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;

@SuppressWarnings("serial")
@Controller("firmAdminAction")
@Scope("prototype")

public class FirmAdminAction extends ActionSupport {
	/**
	 * 上传文件的action必须要这三个属性 还有分别的三个setter方法
	 */
	private File file;// 用于接收上传时的临时文件
	private String fileFileName;// 上传文件的文件名
	private String fileContentType;// 上传文件的信息

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	@Resource(name = "firmAdminService")
	private FirmAdminService firmAdminService;
	@Resource(name = "firmInfoService")
	private FirmInfoService firmInfoService;

	public FirmInfoService getFirmInfoService() {
		return firmInfoService;
	}

	public void setFirmInfoService(FirmInfoService firmInfoService) {
		this.firmInfoService = firmInfoService;
	}

	public FirmAdminService getFirmAdminService() {
		return firmAdminService;
	}

	public void setFirmAdminService(FirmAdminService firmAdminService) {
		this.firmAdminService = firmAdminService;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	public String firmAdminSelf() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		if (id1 != null) {
			int id = Integer.valueOf(id1);
			List firmInfo = this.firmAdminService.firmAdminSelf(id);
			session.setAttribute("firmInfo", firmInfo);
			System.out.println("id" + id);
			System.out.println("firmInfo" + firmInfo);
		} else {
			int id = (int) session.getAttribute("id2");
			List firmInfo = this.firmAdminService.firmAdminSelf(id);
			session.setAttribute("firmInfo", firmInfo);
			System.out.println("id" + id);
			System.out.println("firmInfo" + firmInfo);
		}
		return "success";

	}

	public String firmAdminChangeInfo() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		List f = (List) session.getAttribute("firmInfo");
		HashMap hp = (HashMap) f.get(0);
		int id = (int) hp.get("userid");
		String number = request.getParameter("number");
		String email = request.getParameter("email");

		if (number != null && !number.equals("") && !number.equals(hp.get("number"))) {
			String firmInfo = this.firmAdminService.updateNumber(id, number);

		}

		if (email != null && !email.equals("") && !email.equals(hp.get("email"))) {
			String firmInfo = this.firmAdminService.updateEmail(id, email);

		}
		session.setAttribute("id2", id);
		System.out.println("id:" + id);
		return "success";

	}

	/*
	 * 修改密码
	 * 
	 */

	public String firmAdimnPassword() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		List f = (List) session.getAttribute("firmInfo");
		HashMap hp = (HashMap) f.get(0);
		int id = (int) hp.get("userid");// 登录厂商

		String pwd1 = request.getParameter("pwd1");
		String pwd2 = request.getParameter("pwd2");
		String pwd3 = request.getParameter("Rpwd");
		if (pwd1 != null && hp.get("password").equals(pwd1) && pwd2 != null && !pwd2.equals("") && pwd3 != null
				&& !pwd3.equals("") && pwd2.equals(pwd3)) {
			String firmInfo = this.firmAdminService.updatPassword(id, pwd2);
			request.setAttribute("backMsg", "密码修改成功！");

		}
		return "success";
	}

	/*
	 * 忘记密码
	 * 
	 */

	public String security() {
		System.out.println("----------密保问题验证----------");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		// 获取用户名
		String username = request.getParameter("username");
		if (username != null && !username.equals("")) {
			// 执行查询
			CompanyUser firm = this.firmInfoService.login(username);

			session.setAttribute("firm", firm);
			if (firm != null) {
				// 接收问题答案
				String uniMaster = request.getParameter("answer1");
				String primarySch = request.getParameter("answer2");
				// 检查合法性
				if (uniMaster != null && primarySch != null && !uniMaster.equals("") && !primarySch.equals("")) {
					// 进行对比
					if (uniMaster.equals(firm.getAnswer1()) && primarySch.equals(firm.getAnswer2())) {
						System.out.println("找回的用户名为：" + firm.getUserName());
						System.out.println("找回的密码为:" + firm.getPassword());
						return "success";
					} else {
						this.addActionError("密保答案错误");
						return "error";
					}
				} else {
					this.addActionError("密保答案不能为空");
					return "error";
				}
			} else {
				this.addActionError("用户名不存在");
				return "error";
			}
		} else {
			this.addActionError("用户名不能为空");
			return "error";
		}
	}

	public String saveNewPwd() {
		System.out.println("----------存入新密码----------");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		// List f=(List) session.getAttribute("firmInfo");
		// HashMap hp=(HashMap) f.get(0);
		//
		// int id= (int) hp.get("userid");// 登录厂商
		CompanyUser firm = (CompanyUser) session.getAttribute("firm");
		int id = firm.getUserId();
		String Pas = request.getParameter("Pas");
		String resPas = request.getParameter("resPas");
		if (!Pas.equals("") && Pas != null && !resPas.equals("") && resPas != null) {
			System.out.println("输入的密码为:" + Pas);
			System.out.println("确认的密码为:" + resPas);
			System.out.println("用户id为：" + id);
			if (Pas.equals(resPas)) {
				// 执行存储操作
				String firmInfo = this.firmAdminService.updatPassword(id, Pas);
				return SUCCESS;
			} else {
				this.addActionError("密码不相同");
				return ERROR;
			}
		} else {
			this.addActionError("请输入完整密码");
			return ERROR;
		}
	}

	/*
	 * 设备信息
	 * 
	 */

	public String firmAdminDevice() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("设备信息action");
		System.out.println("d到这");
		int id = (int) session.getAttribute("id");// 登录厂商
		String limit1 = request.getParameter("limit");
		String offset1 = request.getParameter("offset");
		int limit;
		int offset;
		if (limit1 != null && offset1 != null) {
			limit = Integer.parseInt(limit1);
			offset = Integer.parseInt(offset1);
		}
		List deviceInfo = this.firmAdminService.firmAdminDevice(id);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < deviceInfo.size(); i++) {
			Map map = (Map) deviceInfo.get(i);
			System.out.println(map.toString());
			JSONObject ob = new JSONObject();
			ob.put("name", map.get("name"));
			ob.put("devicename", map.get("devicename"));
			ob.put("auditid", map.get("auditid"));
			ob.put("state", map.get("state"));
			ob.put("code", map.get("code"));
			ob.put("date", map.get("date").toString());
			ob.put("type", map.get("type"));
			System.out.println(ob.toJSONString());
			arr.add(ob);
		}
		JSONObject ob = new JSONObject();
		ob.put("total", deviceInfo.size());
		ob.put("rows", arr);
		System.out.println(ob.toString());
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.println(ob.toString());
		out.flush();
		out.close();

		return null;

	}
	/*
	 * 设备详细信息
	 * 
	 */

	public String firmAdmindeviceInfo() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id1 = request.getParameter("id");
		int id = Integer.parseInt(id1);                                      
		System.out.println("设备详细信息："+id1);

		List deviceInfo = this.firmAdminService.firmAdmindeviceInfo(id);

		session.setAttribute("deviceInfo", deviceInfo);
		System.out.println("deviceInfo" + deviceInfo);
		return "success";
	}
	/*
	 * 厂商用户移除设备信息
	 * 
	 */

	public String firmRemoveDevice() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id1 = request.getParameter("id");
		int id = Integer.valueOf(id1);

		this.firmAdminService.firmRemoveDevice(id);

		return "success";
	}

	/*
	 *软件信息管理
	 * 
	 */
	public String firmAdminSoft() throws UnsupportedEncodingException{
		System.out.println("------------查看所有的软件信息------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		int id=(int) session.getAttribute("id");// 登录厂商
		//获取一页显示的个数
		String limit1=request.getParameter("limit");
		int limit=Integer.parseInt(limit1);
		//获取已经显示的个数，然后除以一页的个数就是页数
		String offset1=request.getParameter("offset");
		int offset=Integer.parseInt(offset1)/limit;
		//初始化最大的读取个数
		int max=0;
		System.out.println("已经进来了...");
		System.out.println("页数为"+offset);
		System.out.println("页面大小为"+limit);
		//获取数据库中所有的数据
		List <SoftInfo>allsoft=this.firmAdminService.firmAdminSoft(id); 
		if(allsoft!=null){
			System.out.println("成功取得"+allsoft.size()+"条数据...");
		}else{
			System.out.println("查询失败...");
		}
		
		JSONArray arr=new JSONArray();
		//判定最大的读取个数
		if(offset*limit+limit<=allsoft.size()){
			max=offset*limit+limit;
		}else{
			max=allsoft.size();
		}
		//读取数据
		for(int i=offset*limit;i<max;i++){
			JSONObject ob=new JSONObject();
			//将单独的数据装进json数据
			Map map=(Map)allsoft.get(i);
			ob.put("softid", map.get("softid"));
			ob.put("name", map.get("name"));
			ob.put("type", map.get("type"));
			ob.put("date", map.get("date").toString());
			ob.put("serial", map.get("serial"));
			ob.put("username", map.get("username"));
			ob.put("state", map.get("state"));
			//装进数组
			arr.add(ob);
		}
		JSONObject ob=new JSONObject();
		//放置数据
		ob.put("rows", arr);
		//放置所有的数据个数
		ob.put("total", allsoft.size());
		String returndata=ob.toString();
		System.out.println("成功转换"+returndata.length()+"大小的数据...");
		//转换编码
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out=ServletActionContext.getResponse().getWriter();
			out.println(returndata);
	        out.flush(); 
	        out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return "success";
	}
	
	/*
	 * 厂商用户移除软件信息
	 * 
	 */
	
public String firmRemoveSoft(){
	System.out.println("------------删除指定软件信息------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id1 = request.getParameter("id");
		int id = Integer.valueOf(id1); 
		
		this.firmAdminService.firmRemoveSoft(id); 
	
	
		return "success";
	}

	/**
	 * 
	 * 厂商用户批量删除软件信息
	 */

	public String firmAllRemove(){
		System.out.println("------------批量删除软件信息------------");
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		String str=request.getParameter("id");
		String[] array=str.split(",");
		for(int i=0;i<array.length;i++){
			int id=Integer.parseInt(array[i]);
			this.firmAdminService.firmRemoveSoft(id); 
		}
		return SUCCESS;
	}
	
	/*
	 *软件详细信息
	 * 
	 */
	public String firmAdminSoftInfo(){
		System.out.println("------------查看指定软件信息------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id1 = request.getParameter("id");
//		int id=(int)session.getAttribute("id");
		System.out.println(id1);
		int id = Integer.valueOf(id1); 
		
		List<SoftInfo> softInfo=this.firmAdminService.firmAdminSoftInfo(id); 
		session.setAttribute("softInfo", softInfo);
		System.out.println("已经进来了...");
		System.out.println("softInfo"+softInfo);
		
		return "success";
	}
	
	/**
	 * 
	 * 搜寻指定软件信息
	 */
	public String findSoftInfo(){
		System.out.println("------------搜寻指定软件信息------------");
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		String search=request.getParameter("search");
		System.out.println("接收到内容"+search);
		int id=Integer.parseInt(search);
		SoftInfo info=this.firmAdminService.findSoftInfo(id);
		if(info!=null){
			System.out.println("找到数据为"+info.getSoftid()+" "+info.getName());
		}else{
			System.out.println("未找到数据");
		}
		return SUCCESS;
	}


	/*
	 * 厂商通知信息
	 * 
	 */
	public String firmAdminmsg() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");// 登录厂商
		List firmmsg = this.firmAdminService.firmAdminmsg(id);
		session.setAttribute("firmmsg", firmmsg);
		System.out.println("firmmsg" + firmmsg);
		return "success";
	}

	public String firmAdminMsgInfo() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id1 = request.getParameter("id");
		int id = Integer.valueOf(id1);
		System.out.println("id:" + id);
		List msgInfo = this.firmAdminService.firmAdminMsgInfo(id);
		session.setAttribute("msgInfo", msgInfo);
		System.out.println("msgInfo" + msgInfo);

		return "success";
	}

	/*
	 * 这个方法用于上传设备
	 */
	public String uploadPredeviceinfo() throws IOException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		int uploadid = (int) session.getAttribute("id");// 登录厂商
		System.out.println("id=" + uploadid);
		String name = request.getParameter("name");  //设备名称
		String code = request.getParameter("code");  //设备条形码
		String type = request.getParameter("type");  //设备类型
		String demo = request.getParameter("demo");   //设备描述
		
		long softsize = file.length();
		File destFile = new File("D:/workspace/" + fileFileName);// 上传文件存放位置
		try {
			// 工具类
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = destFile.getPath();
		String MD5 = null;
		FileInputStream in = new FileInputStream(destFile);
		try {
			MD5 = DigestUtils.md5Hex(IOUtils.toByteArray(in));
			IOUtils.closeQuietly(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("name:" + name + "___" + "code:" + code + "___" + "type:" + type + "___" +
		"size:" + softsize + "___" + "url:" + url + "___" + "___" + "md5:" + MD5);

		Date date = new Date();
		DeviceAudit audit = new DeviceAudit();
//		audit.setCheckdate(date);   //审核时间
		audit.setCheckId(1);  //审核管理员id  1填充
		audit.setCode(code);
		audit.setCompanyId(uploadid);   //厂商id  1填充
		audit.setDate(date);
		audit.setDemo(demo);
		audit.setDeviceName(name);
		audit.setState("0");
		audit.setType(type);
		
		this.firmAdminService.uploadPredeviceinfo(audit);
		return "success";

		}

	/*
	 * 
	 * 这个方法用于上传软件信息
	 * 
	 */

	public String uploadSoftinfo() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		int uploadid = (int) session.getAttribute("id");// 登录厂商
		System.out.println("id=" + uploadid);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String version = request.getParameter("version");
		String serial = request.getParameter("serial");
		long softsize = file.length();
		File destFile = new File("D:/workspace/" + fileFileName);// 上传文件存放位置
		try {
			// 工具类
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = destFile.getPath();
		String MD5 = null;
		FileInputStream in = new FileInputStream(destFile);
		try {
			MD5 = DigestUtils.md5Hex(IOUtils.toByteArray(in));
			IOUtils.closeQuietly(in);
		} catch (IOException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("name:" + name + "___" + "type:" + type + "___" + "version:" + version + "___" + "serial:"
				+ serial + "___" + "size:" + softsize + "___" + "url:" + url + "___" + "___" + "md5:" + MD5);

		Date date = new Date();
		SoftInfo soft = new SoftInfo();
		soft.setDate(date);
		soft.setMd5(MD5);
		soft.setSize(softsize);
		soft.setState("0");
		soft.setName(name);
		soft.setSerial(serial);
		soft.setType(Integer.parseInt(type));
		soft.setUploadid(uploadid);
		soft.setUrl(url);
		soft.setVersion(version);
		this.firmAdminService.uploadSoftinfo(soft);
		return "success";
	}

	/*
	 * 这个方法用于反馈消息
	 * 
	 * 
	 */
	public String firmfeedback() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("id");// 登录厂商
		String title = request.getParameter("title");
		String type1 = request.getParameter("type");
		int type = Integer.valueOf(type1);
		String content = request.getParameter("text");
		String hadle = "0";
		CommentInfo comment = new CommentInfo();
		CompanyUser firm1 = (CompanyUser) session.getAttribute("CompanyUser");
		System.out.println(firm1);
		comment.setUserId(firm1);
		comment.setTitle(title);
		comment.setContent(content);
		comment.setDate(new Date());
		comment.setHandle(hadle);
		comment.setType(type);
		this.firmAdminService.feedback(comment);// 这个异常暂时没有解决
		return "success";
	}
	public String comment() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("进入action");
		int id = (int) session.getAttribute("id");// 登录厂商
		String limit1 = request.getParameter("limit");
		String offset1 = request.getParameter("offset");
		int limit;
		int offset;
		if (limit1 != null && offset1 != null) {
			limit = Integer.parseInt(limit1);
			offset = Integer.parseInt(offset1);
		}
		CompanyUser user = (CompanyUser) session.getAttribute("CompanyUser");
		System.out.println("ok");
		List comment = this.firmAdminService.comment(user);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < comment.size(); i++) {
			Map map = (Map) comment.get(i);
			System.out.println(map.toString());
			JSONObject ob = new JSONObject();
			ob.put("commentid", map.get("commentid"));
			ob.put("userid", map.get("userid"));
			ob.put("type", map.get("type"));
			ob.put("date", map.get("date").toString());
			ob.put("title", map.get("title"));
			ob.put("content", map.get("content"));
			ob.put("handle", map.get("handle"));
			ob.put("replyid", map.get("replyid"));
			ob.put("replydate", map.get("replydate").toString());
			ob.put("reply", map.get("reply"));
			System.out.println(ob.toJSONString());
			arr.add(ob);
		}
		JSONObject ob = new JSONObject();
		ob.put("total", comment.size());
		ob.put("rows", arr);
		System.out.println(ob.toString());
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.println(ob.toString());
		out.flush();
		out.close();

		return null;

	}
	public String removeComment(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		System.out.println(id);
		this.firmInfoService.removeComments(Integer.parseInt(id));
		return "success";
	}
	/*
	 * 批量删除反馈消息
	 */
	public String removeComments(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("进入action");
		String[] id = request.getParameterValues("id");// 登录厂商
		String[] userids = null;
		for(int i=0;i<id.length;i++){
			userids= id[0].split(",");			
		}
		for(int i=0;i<userids.length;i++){
			this.firmInfoService.removeComments(Integer.parseInt(userids[i]));
		}
		return "success";
	}
/**
 * 查看某一条反馈消息的回复
 * @return
 */
	public String checkComment() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		System.out.println(id);
		this.firmInfoService.checkComment(Integer.parseInt(id));
		return "success";
	}
	public String removeDeviceAll(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("进入action");
		String[] id = request.getParameterValues("id");// 登录厂商
		String[] userids = null;
		for(int i=0;i<id.length;i++){
			userids= id[0].split(",");			
		}
		for(int i=0;i<userids.length;i++){
			System.out.println(userids[i]);
			this.firmInfoService.removeDeviceAll(Integer.parseInt(userids[i]));
		}
		return "success";
	}
	/**
	 * 厂商运行信息
	 * @throws IOException 
	 */
	public String runInfo() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("进入action");
		int id = (int) session.getAttribute("id");// 登录厂商
		String limit1 = request.getParameter("limit");
		String offset1 = request.getParameter("offset");
		int limit;
		int offset;
		if (limit1 != null && offset1 != null) {
			limit = Integer.parseInt(limit1);
			offset = Integer.parseInt(offset1);
		}
		List runInfoAudit = this.firmAdminService.runInfoAudit(id);
		JSONArray arr = new JSONArray();
		int count=0;
		for (int i = 0; i < runInfoAudit.size(); i++) {
			Map map = (Map) runInfoAudit.get(i);
			int auditid = (int) map.get("auditid");
			String code = (String)map.get("code");
			String devicename = (String)map.get("devicename");
			List runInfoDevice = this.firmAdminService.runInfoDevice(code);
			for(int j=0;j<runInfoDevice.size();j++){
				Map map2 = (Map) runInfoDevice.get(j);
				int deviceid = (int) map2.get("deviceid");
				UserNode userNode = SysInfo.getInstance().getDevNodeById(deviceid);
				if(userNode!=null){
					count++;
				}
			}
			
			JSONObject ob = new JSONObject();
			ob.put("Id", auditid);
			ob.put("title", devicename);
			ob.put("login", runInfoDevice.size());
			ob.put("number", count);
			arr.add(ob);
		}
		
		
		
		JSONObject ob = new JSONObject();
		ob.put("total", runInfoAudit.size());
		ob.put("rows", arr);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;

	}
	public String runInfoDeinfo() throws IOException {
		int iid  = -1;
		String id = request.getParameter("id");//得到auditID
		System.out.println("得到id"+id);
		if(id!=null){
			iid = Integer.parseInt(id);
		}
		List code = this.firmAdminService.runInfoDeinfo(iid);
		JSONArray arr = new JSONArray();
		List runInfoDeinfo2 = null;
		for(int i=0;i<code.size();i++){
			Map Mcode = (Map) code.get(i);
			String Scode =  Mcode.get("code").toString();
			System.out.println("code:"+Scode);
			runInfoDeinfo2 = this.firmAdminService.runInfoDeinfo2(Scode);
			for(int j=0;j<runInfoDeinfo2.size();j++){
				Map map = (Map) runInfoDeinfo2.get(j);
				System.out.println(map.toString());
				int deviceid = (int)map.get("deviceid");
				String serial = (String)map.get("serial");
				System.out.println(serial);
				String type = map.get("type").toString();
				System.out.println(type);
				String state = map.get("state").toString();
				System.out.println(state);
				String date = map.get("date").toString();
				JSONObject ob = new JSONObject();
				ob.put("Id", deviceid);
				if(state.equals("1")){
					ob.put("state", "已激活");
					System.out.println(state+"相等");
				} else {
					ob.put("state", "未激活");
				}
				UserNode userNode = SysInfo.getInstance().getDevNodeById(deviceid);
				if(userNode!=null && userNode.getState()==2){
					ob.put("online", "在线");	
				} else {
					ob.put("online", "未在线");
					}
				ob.put("serial", serial);//序列号
				ob.put("type", type);//所属类型
				ob.put("date", date);//注册时间
				int count = this.firmAdminService.logtimes(deviceid);
				ob.put("times", count);//登录次数
				arr.add(ob);				
			}
		}
		JSONObject ob = new JSONObject();
		ob.put("total", runInfoDeinfo2.size());
		ob.put("rows", arr);
		System.out.println(ob.toString());
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.println(ob.toString());
		out.flush();
		out.close();
		return null;
	}

}
