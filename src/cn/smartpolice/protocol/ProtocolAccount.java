package cn.smartpolice.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.DeviceLog;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.hibernate.UserLog;
import cn.smartpolice.netdao.DevLogDao;
import cn.smartpolice.netdao.DeviceDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.RelateDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.netdao.UserLogDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.DevNode;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
/**
 * @author maxiaolin
 * cmd=2  账户管理协议
 * 注销账号还有很多东西未定义，故没有优化
 */
public class ProtocolAccount extends ProtocolBase {
	
//	@Resource(name="deviceDao")
//	private DeviceDao deviceDao;
//	
//	@Resource(name="devLogDao")
//	private DevLogDao devLogDao;
//	
//	@Resource(name="userDao")
//	private UserDao userDao;
//	
//	@Resource(name="userLogDao")
//	private UserLogDao userLogDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	UserDao userDao = (UserDao) ac.getBean("userDao");
	DeviceDao deviceDao = (DeviceDao) ac.getBean("deviceDao");
	DevLogDao devLogDao = (DevLogDao) ac.getBean("devLogDao");
	UserLogDao userLogDao = (UserLogDao) ac.getBean("userLogDao");

	String snum;
	String sver;
	String user;
	String password;
	String info;
	int errorPktState;// 标记返回错误报文类型

	int sid; // 查询时需要返回的sid
	// dev的info需要的信息
	String devSerial;
	String devCode;
	String devLongitude;
	String devLatitude;
	int devMaxConLimit;
	String devType;
	String devMphone;
	// app的info需要的信息
	String appAuthority;
	String appName;
	Date appBirth;
	Boolean appSex;
	String appMail;
	String appType;
	String appMphone;
    boolean infoComplete = false;
	//注销成功返回日期
	Date offDate;
	UserNode userNode;
	void ParsePktProto(PacketInfo packetInfo) {
		// TODO Auto-generated method stub
		System.out.print("Account报文解析");
		super.revPacket = packetInfo;
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		String data = packetInfo.getData();

		snum = jsonAnalysis.getValue(data, "SNUM");
		sver = jsonAnalysis.getValue(data, "SVER");
		user = jsonAnalysis.getValue(data, "USER");
		password = jsonAnalysis.getValue(data, "PASSWORD");
		info = jsonAnalysis.getValue(data, "INFO");
		// dev注册信息解析
		if(revPacket.getSort() == ConstParam.SORT_2){userNode = revPacket.getDevNode();}//获得消息节点
		if(revPacket.getSort() == ConstParam.SORT_0){userNode = revPacket.getAppNode();}
		
		if (revPacket.getSort() == ConstParam.SORT_2 && info != null && revPacket.getType() != ConstParam.TYPE_5) {
			devSerial = jsonAnalysis.getValue(info, "SEQ");
			devCode = jsonAnalysis.getValue(info, "BARCODE");
			devLongitude = jsonAnalysis.getValue(info, "LONGI");
			devLatitude = jsonAnalysis.getValue(info, "LATI");
			if (jsonAnalysis.getValue(info, "MAX") != null) {
				devMaxConLimit = Integer.parseInt(jsonAnalysis.getValue(info, "MAX"));//容错处理，若MAX为空，使用Integer.parseInt()时将抛出异常
			} else {
				devMaxConLimit = -1;
			}
			devType = jsonAnalysis.getValue(info, "CLASS");
			devMphone = jsonAnalysis.getValue(info, "PHONE");
			if (devSerial != null && devCode != null && devLongitude != null && devLatitude != null && devType != null
					&& devMphone != null && devMaxConLimit != -1) {//判断info是否完整，在报文注册时有用
				infoComplete = true;
			}
		}
		// app注册信息解析
		if (revPacket.getSort() == ConstParam.SORT_0 && info != null && revPacket.getType() != ConstParam.TYPE_5) {
			appAuthority = jsonAnalysis.getValue(info, "LIMIT");
			appName = jsonAnalysis.getValue(info, "NAME");
			if(jsonAnalysis.getValue(info, "BIRTH")!=null){
			try {
				appBirth = new SimpleDateFormat("yyyy-MM-dd").parse(jsonAnalysis.getValue(info, "BIRTH"));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			}
			if (jsonAnalysis.getValue(info, "SEX")==null){
				appSex=null;
			}
			else if(jsonAnalysis.getValue(info, "SEX").equals("1")) {
				appSex = true;
			} else {
				appSex = false;
			}
			appMail = jsonAnalysis.getValue(info, "EMAIL");
			appType = jsonAnalysis.getValue(info, "CLASS");
			appMphone = jsonAnalysis.getValue(info, "PHONE");
			if(appAuthority!=null && appName!=null && appBirth!=null && appMail!=null && appType!=null && appMphone!=null){
				infoComplete = true;//判断info是否完整，在报文注册时有用
			}
		}
		this.ExecProto();
	}

	
	

	public void devCloseAccount(){}
	public void appCloseAccount(){}
	public void ExecProto() {

		// 注册协议
		if (revPacket.getType() == ConstParam.TYPE_1) {
			System.out.println("账号注册报文");
				// dev
				if (revPacket.getSort() == ConstParam.SORT_2) {
					System.out.println("设备注册");
					devReg();
				}
				// app
				if (revPacket.getSort() == ConstParam.SORT_0) {
					appReg();
				}			
		}
		
		if (revPacket.getType() == ConstParam.TYPE_3) {
			System.out.println("修改账号请求报文");
			// dev
			if (revPacket.getSort() == ConstParam.SORT_2) {
				devModify();				
			}
			// app
			if (revPacket.getSort() == ConstParam.SORT_0) {
				appModify();
		}
	}
		// 注销协议
		if (revPacket.getType() == ConstParam.TYPE_5) {

			try {
				// 解码得到info中的phone
				BASE64Decoder decoder = new BASE64Decoder();
				String mphone = new String(decoder.decodeBuffer(info));
				byte[] phoneBytes = mphone.getBytes();
				// dev
				if (revPacket.getSort() == ConstParam.SORT_2) {
					DeviceInfo dev = deviceDao.findDevByID(revPacket.getSid());
					// 存在dev且state！=0
					if (dev != null && Integer.parseInt(dev.getState()) != 0) {
						String password = dev.getPassword();
						String phone = dev.getMphone();
						byte[] passwordBytes = password.getBytes();
						for (int i = 0; i < phoneBytes.length; i++) {
							phoneBytes[i] = (byte) (phoneBytes[i] ^ passwordBytes[i]);
							System.out.println(phoneBytes[i]);
						}
						String phonenum = new String(phoneBytes);
						System.out.println("phonenum=" + phonenum);
						if (phonenum.equals(phone)) {
							// 记录到devlog表中
							DeviceLog devLog = new DeviceLog();
							devLog.setDeviceid(revPacket.getSid());
							devLog.setDate(new Date());
							String ip = ((InetSocketAddress) revPacket.getIoSession().getRemoteAddress()).getAddress()
									.getHostAddress();
							int port = ((InetSocketAddress) revPacket.getIoSession().getRemoteAddress()).getPort();
							devLog.setIpaddr(ip);
							devLog.setPort(port);
							devLog.setOperate(ConstParam.LOGIN_OPERATE_2);
							devLogDao.insertDevLogInf(devLog);
							// 置device_inf表中state=0
							deviceDao.changeStateToZero(revPacket.getSid());
							// 删除全局数据中的节点
							DevNode devNode = (DevNode) SysInfo.getInstance().getDevNodeById(revPacket.getSid());
							SysInfo.getInstance().removeUserNode(devNode);
							// 获取注销成功日期
							offDate = new Date();
							// 返回注销成功报文
							byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);
							SendPkt(successPacket);
							userNode.setLastPacketInfo(successPacket);
						} else { // 电话不对 验证失败
							byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
							SendPkt(errorPacket);
							userNode.setLastPacketInfo(errorPacket);
						}
					} else {
						// dev == null不处理 state=0返回已注销
						if (Integer.parseInt(dev.getState()) == 0) {
							// 通过查找operate=-1和deviceid 两个条件一起查找
							offDate = devLogDao.findDevLogByDevIdAndOperate(revPacket.getSid()).getDate();
							byte[] packet = PackPkt(ConstParam.SENT_PKT_TYPE_2);
							SendPkt(packet);
							userNode.setLastPacketInfo(packet);
						}
					}
				}
				// app
				if (revPacket.getSort() == ConstParam.SORT_0) {
					UserInfo app = userDao.findAppuserByID(revPacket.getSid());
					// 存在dev且state！=0
					if (app != null && Integer.parseInt(app.getState()) != 0) {
						String password = app.getPassword();
						String phone = app.getMphone();
						byte[] passwordBytes = password.getBytes();
						for (int i = 0; i < phoneBytes.length; i++) {
							phoneBytes[i] = (byte) (phoneBytes[i] ^ passwordBytes[i]);
						}
						String phonenum = new String(phoneBytes);
						System.out.println("phonenum=" + phonenum);
						if (phonenum.equals(phone)) {
							// 记录到userlog表中
							UserLog userLog = new UserLog();
							userLog.setUserId(revPacket.getSid());
							userLog.setDate(new Date());
							String ip = ((InetSocketAddress) revPacket.getIoSession().getRemoteAddress()).getAddress()
									.getHostAddress();
							int port = ((InetSocketAddress) revPacket.getIoSession().getRemoteAddress()).getPort();
							userLog.setIpaddr(ip);
							userLog.setPort(port);
							userLog.setOperate(ConstParam.LOGIN_OPERATE_2);
							userLogDao.insertUserLogInf(userLog);
							// 置device_inf表中state=0
							userDao.changeStateToZero(revPacket.getSid());
							// 删除全局数据中的节点
							AppNode appNode = (AppNode) SysInfo.getInstance().getAppNodeById(revPacket.getSid());
							SysInfo.getInstance().removeUserNode(appNode);
							// 获取注销成功日期
							offDate = new Date();
							// 返回注销成功报文
							byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);
							SendPkt(successPacket);
							userNode.setLastPacketInfo(successPacket);
						} else { // 电话不对 验证失败
							byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
							SendPkt(errorPacket);
							userNode.setLastPacketInfo(errorPacket);
						}
					} else {
						// app == null不处理 state=0返回已注销
						if (Integer.parseInt(app.getState()) == 0) {
							// 通过查找operate=-1和deviceid 两个条件一起查找
							offDate = userLogDao.findDevLogByAppIdAndOperate(revPacket.getSid()).getDate();
							byte[] packet = PackPkt(ConstParam.SENT_PKT_TYPE_2);
							SendPkt(packet);
							userNode.setLastPacketInfo(packet);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
}


	public byte[] PackPkt(int i) {
		// TODO Auto-generated method stub
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_2);
		// 不同的请求报文 返回的应答应答不一样
		//注册请求
		if (revPacket.getType() == ConstParam.TYPE_1) {
			msg.setType(ConstParam.TYPE_2); // type=2
		}
		//注销请求
		if(revPacket.getType() == ConstParam.TYPE_5){
			msg.setType(ConstParam.TYPE_6);
		}
		// 账号修改
		if (revPacket.getType() == ConstParam.TYPE_3) {
			msg.setType(ConstParam.TYPE_4);
		}
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());
		String info;
		int ret;
		JSONObject data = new JSONObject();
		JSONObject dataChild = new JSONObject();
		switch (i) {
		case 1: // ret=-1
			if(revPacket.getType() == ConstParam.TYPE_1) {//ret=-1 表示账号申请失败，返回错误原因
				if (errorPktState == ConstParam.ERROR_PKT_STATE_0) {
					ret = -1;
					info = "not exist in database";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
				if (errorPktState == ConstParam.ERROR_PKT_STATE_1) {
					ret = -1;
					info = "password is wrong";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
				if (errorPktState == ConstParam.ERROR_PKT_STATE_2) {
					ret = -1;
					info = "info is not complete";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
				if (errorPktState == ConstParam.ERROR_PKT_STATE_3) {
					ret = -1;
					info = "username is not right！";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
			}
			
			if(revPacket.getType() == ConstParam.TYPE_5){//注销账号应答
				ret = -1;
				info = "log off confirmate fail";
				dataChild.put("RET", ret);
				dataChild.put("INFO", info);
			}
			
			if (revPacket.getType() == ConstParam.TYPE_3) {//修改账号应答
				if (errorPktState == ConstParam.ERROR_PKT_STATE_0) {
					ret = -1;
					info = "not exist in database";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
				if (errorPktState == ConstParam.ERROR_PKT_STATE_1) {
					ret = -1;
					info = "username is not right！";
					dataChild.put("RET", ret);
					dataChild.put("INFO", info);
				}
			}
			break;

		case 2: // ret=1
			if(revPacket.getType() == ConstParam.TYPE_1){//账号已经存在
				ret = 1;
				dataChild.put("RET", ret);
				dataChild.put("INFO", ""+sid);
			}
			if(revPacket.getType() == ConstParam.TYPE_5){//账号已经注销
				ret = 1;
				dataChild.put("RET", ret);
				dataChild.put("INFO", offDate.getTime());
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {//账号已经变更过
				ret = 1;
				dataChild.put("RET", ret);
				dataChild.put("INFO", ""+sid);
			}
			break;

		case 3: // ret=0
			if(revPacket.getType() == ConstParam.TYPE_1){   //注册成功
				ret = 0;
				dataChild.put("RET", ret);
				dataChild.put("INFO", ""+sid);
			}
			if(revPacket.getType() == ConstParam.TYPE_5){   //注销成功
				ret = 0;
				dataChild.put("RET", ret);
				dataChild.put("INFO",  offDate.getTime());
			}
			if (revPacket.getType() == ConstParam.TYPE_3) { // 通过变更
				ret = 0;
				dataChild.put("RET", ret);
				dataChild.put("INFO",  ""+sid);
			}
			break;

		}
		data.put("DATA", dataChild);
		msg.setData(data.toString());
		data.clear();
		dataChild.clear();
		return msg.createMessage(msg);
	}

	public void SendPkt(byte[] sendPacket) {
		// TODO Auto-generated method stub
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}
	
	public void devReg(){
		System.out.println("进入函数");
		if(info == null){
			System.out.println("info为null");
			DeviceInfo deviceInf = deviceDao.findDevByName(user);
			// 不存在该username
			if (deviceInf == null) {
				errorPktState = ConstParam.ERROR_PKT_STATE_0; // 标记发送第一种错误报文（数据库中不存在该记录）
				byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
				SendPkt(errorPacket);
			} else {
				sid = deviceInf.getDeviceid();
				if (!password.equals(deviceInf.getPassword())) {
					errorPktState = ConstParam.ERROR_PKT_STATE_1; // 存在该记录,但是密码不正确
					byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
					SendPkt(errorPacket);
				} else {
					byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);
					SendPkt(successPacket);
				}
			}
		}else{
			System.out.println("info不为null");
			if (infoComplete) {
				System.out.println("信息完整");
				if (revPacket.getSort() == 2) {
					System.out.println("bingo");
					DeviceInfo deviceInf = deviceDao.findDevByName(user);
					System.out.println("bingo2");
					if (deviceInf != null) {
						sid = deviceInf.getDeviceid();
						byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);
						SendPkt(successPacket);
					} else {
						DeviceInfo dev = new DeviceInfo();
						dev.setSerial(devSerial);
						dev.setCode(devCode);
						dev.setUsername(user);
						dev.setPassword(password);
						dev.setState("1");// 0禁止 1激活
						dev.setLongitude(devLongitude);
						dev.setLatitude(devLongitude);
						dev.setDate(new Date());
						dev.setType(devType);
						dev.setMaxconlimit(devMaxConLimit);
						dev.setMphone(devMphone);
						sid = deviceDao.insertDevInf(dev);
						byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);
						SendPkt(successPacket);
					}
				}
		}else {
			System.out.println("信息不完整");
			errorPktState = ConstParam.ERROR_PKT_STATE_2;
			byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
			SendPkt(errorPacket);
		}
	}
}

	public void appReg() {
		if (info == null) {
			// 通过user查询数据库是否存在该用户
			UserInfo userInf = userDao.findAppuserByName(user);
			// 不存在该username
			if (userInf == null) {
				errorPktState = ConstParam.ERROR_PKT_STATE_0; // 标记发送第一种错误报文（数据库中不存在该记录）
				byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
				SendPkt(errorPacket);
			} else {
				sid = userInf.getUserID();
				if (!password.equals(userInf.getPassword())) {
					errorPktState = ConstParam.ERROR_PKT_STATE_1; // 存在该记录,但是密码不正确
					byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
					SendPkt(errorPacket);
				} else {
					byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);
					SendPkt(successPacket);
				}
			}
		} else {
			if (infoComplete) {
				UserInfo userInf = userDao.findAppuserByName(user);
				if (userInf != null) {
					sid = userInf.getUserID();
					byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);
					SendPkt(successPacket);
				} else {
					UserInfo app = new UserInfo();
					app.setUserName(user);
					app.setPassword(password);
					app.setSets("1");// 1添加关联不确认；2添加联系人不确认；4
										// 禁止添加关联；8禁止添加联系人
					app.setRegDate(new Date());
					app.setAuthority(appAuthority);
					app.setState("1");// 0未激活 1激活使用
					app.setName(appName);
					app.setBirth(appBirth);
					app.setSex(appSex);
					app.setType(appType);
					app.setMail(appMail);
					app.setMphone(appMphone);
					sid = userDao.insertAppuser(app);
					byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);
					SendPkt(successPacket);
				}
			} else {
				errorPktState = ConstParam.ERROR_PKT_STATE_2;
				byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
				SendPkt(errorPacket);
			}
		}
	}
	
	public void appModify(){
		int same = 0;
		int change = 0;
		// 通过user查询数据库是否存在该用户
		UserInfo userInf = userDao.findAppuserByID(revPacket.getSid());
		// 不存在该用户
		if (userInf == null) {
			errorPktState = ConstParam.ERROR_PKT_STATE_0; // 标记发送第一种错误报文（数据库中不存在该记录）
			byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
			SendPkt(errorPacket);
			userNode.setLastPacketInfo(errorPacket);
		} else if (!userInf.getUserName().equals(user)) {
			errorPktState = ConstParam.ERROR_PKT_STATE_1; // 标记发送第二种错误报文（用户名和数据库中的不匹配）
			byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
			SendPkt(errorPacket);
			userNode.setLastPacketInfo(errorPacket);
		} else {
			if (appAuthority != null) {
				if (userInf.getAuthority().equals(appAuthority)) {
					++same;
				} else {
					userInf.setAuthority(appAuthority);
					++change;
				}
			}
			if (appBirth != null) {
				if (userInf.getBirth().equals(appBirth)) {
					++same;
				} else {
					userInf.setBirth(appBirth);
					++change;
				}
			}
			if (appMail != null) {
				if (userInf.getMail().equals(appMail)) {
					++same;
				} else {
					userInf.setMail(appMail);
					++change;
				}
				if (appMphone != null) {
					if (userInf.getMphone().equals(appMphone)) {
						++same;
					} else {
						userInf.setMphone(appMphone);
						++change;
					}
				}
			}
			if (appName != null) {
				if (userInf.getName().equals(appName)) {
					++same;
				} else {
					userInf.setName(appName);
					++change;
				}
			}
			if (appType != null) {
				if (userInf.getType().equals(appType)) {
					++same;
				} else {
					userInf.setType(appType);
					++change;
				}
			}
			if (appSex != null) {
				if (userInf.getSex() == appSex) {
					++same;
				} else {
					userInf.setSex(appSex);
					++change;
				}
			}
			if (password != null) {
				if (userInf.getPassword().equals(password)) {
					++same;
				} else {
					userInf.setPassword(password);
					++change;
				}
			}
			System.out.println("共有" + change + "个信息更改");
			if (change == 0 && same != 0) {// 返回RET=1，表明数据已经更新过
				sid = userInf.getUserID();
				byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);// 通过变更
				SendPkt(successPacket);
				userNode.setLastPacketInfo(successPacket);
			} else {
				userDao.updateAppuser(userInf);
				sid = userDao.findAppuserByName(user).getUserID();
				byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);// 通过变更
				SendPkt(successPacket);
				userNode.setLastPacketInfo(successPacket);
			}
		}
	}
	
	public void devModify(){
		DeviceInfo deviceInf = deviceDao.findDevByID(revPacket.getSid());
		int same = 0;
		int change = 0;
		// 不存在该账号
		if (deviceInf == null) {
			errorPktState = ConstParam.ERROR_PKT_STATE_0; // 标记发送第一种错误报文（数据库中不存在该记录）
			byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
			SendPkt(errorPacket);
			userNode.setLastPacketInfo(errorPacket);
		} else if (!deviceInf.getUsername().equals(user)) {
			errorPktState = ConstParam.ERROR_PKT_STATE_1; // 标记发送第二种错误报文（用户名和数据库中的不匹配）
			byte[] errorPacket = PackPkt(ConstParam.SENT_PKT_TYPE_1);
			SendPkt(errorPacket);
			userNode.setLastPacketInfo(errorPacket);
		} else {

			if (devSerial != null) {
				if (deviceInf.getSerial().equals(devSerial)) {
					++same;
				} else {
					deviceInf.setSerial(devSerial);
					++change;
				}
			}
			if (devCode != null) {
				if (deviceInf.getCode().equals(devCode)) {
					++same;
				} else {
					deviceInf.setCode(devCode);
					++change;
				}
			}
			if (devLongitude != null) {
				if (deviceInf.getLongitude().equals(devLongitude)) {
					++same;
				} else {
					++change;
					deviceInf.setLongitude(devLongitude);
				}

			}
			if (devLatitude != null) {
				if (deviceInf.getLatitude().equals(devLatitude)) {
					++same;
				} else {
					++change;
					deviceInf.setLatitude(devLatitude);
				}

			}
			if (devType != null) {
				if (deviceInf.getType().equals(devType)) {
					++same;
				} else {
					++change;
					deviceInf.setType(devType);
				}
			}
			if (devMaxConLimit != -1 && deviceInf.getMaxconlimit() != devMaxConLimit) {
				deviceInf.setMaxconlimit(devMaxConLimit);
				++change;
			} else {
				++same;
			}
			if (devMphone != null) {
				if (deviceInf.getMphone().equals(devMphone)) {
					++same;
				} else {
					++change;
					deviceInf.setMphone(devMphone);
				}
			}
			if (password != null) {
				if (deviceInf.getPassword().equals(password)) {
					++same;
				} else {
					++change;
					deviceInf.setPassword(password);
				}
				// deviceInf.setState("1");//密码已更改，需要密码验证
			}
			System.out.println("共有" + change + "个信息更改");
			if (change == 0 && same != 0) {
				sid = deviceInf.getDeviceid();
				byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_2);// 通过变更
				SendPkt(successPacket);
				userNode.setLastPacketInfo(successPacket);
			} else {
				deviceDao.updateDevInf(deviceInf);
				sid = deviceDao.findDevByName(user).getDeviceid();
				byte[] successPacket = PackPkt(ConstParam.SENT_PKT_TYPE_3);// 通过变更
				SendPkt(successPacket);
				userNode.setLastPacketInfo(successPacket);
			}
		}
	}
}
