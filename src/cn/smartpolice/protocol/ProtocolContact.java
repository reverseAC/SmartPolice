package cn.smartpolice.protocol;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.ContactInfo;
import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.netdao.ContactDao;
import cn.smartpolice.netdao.ContactWaitDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.RelateDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.protocol.ProtocolMessage;

/**
 * cmd = 10; 联系人管理协议
 */
public class ProtocolContact extends ProtocolBase {
	
//	@Resource(name="userDao")
//	private UserDao userDao;
//	
//	@Resource(name="cotactDao")
//	private ContactDao contactDao;
//	
//	@Resource(name="contactWaitDao")
//	private ContactWaitDao contactWaitDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	UserDao userDao = (UserDao) ac.getBean("userDao");
	ContactDao contactDao = (ContactDao) ac.getBean("contactDao");
	ContactWaitDao contactWaitDao = (ContactWaitDao) ac.getBean("contactWaitDao");
	
	String user;// 申请者账号
	String cuser;// 联系人账号
	String alias;// 联系人别名
	String groupname;// 组名
	String message;// 留言
	String info;
	String contact;//关联的人
	String pass;
	byte from;//获取标记
	int userid;//被关联用户ID

	int errorPktState;
	byte kind = 0;
	
	String sign;
	
	JSONArray jsonArray =  new JSONArray();
	
	Date contactDate;// 添加时间
	Date deleteDate;// 脱离时间

	void ParsePktProto(PacketInfo packetInfo) {
		System.out.print("进入到cmd = 10");
		ShowPacket(packetInfo);
		super.revPacket = packetInfo;
		String data = revPacket.getData();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		if(revPacket.getType() == ConstParam.TYPE_1){// 添加联系人
			user = jsonAnalysis.getValue(data, "USER");
			cuser = jsonAnalysis.getValue(data, "CUSER");
			alias = jsonAnalysis.getValue(data, "ALIAS");
			groupname = jsonAnalysis.getValue(data, "GROUPNAME");
			message = jsonAnalysis.getValue(data, "MESSAGE");
		}
		if(revPacket.getType() == ConstParam.TYPE_3){//脱离联系人
			user = jsonAnalysis.getValue(data, "USER");
			cuser = jsonAnalysis.getValue(data, "CUSER");
			pass = jsonAnalysis.getValue(data, "PASS");
		}
		if(revPacket.getType() == ConstParam.TYPE_5){//获取联系人
			user = jsonAnalysis.getValue(data, "USER");
			from = Byte.parseByte(jsonAnalysis.getValue(data, "FROM"));
		}
		if(revPacket.getType() == ConstParam.TYPE_7){//联系人通告
			userid = Integer.parseInt(jsonAnalysis.getValue(data, "USERID"));
			kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
			message = jsonAnalysis.getValue(data, "MESSAGE");
		}
		if(revPacket.getType() == ConstParam.TYPE_9){//变更联系人
			user = jsonAnalysis.getValue(data, "USER");
			kind = Byte.parseByte(jsonAnalysis.getValue(data, "KIND"));
			alias = jsonAnalysis.getValue(data, "ALIAS");
			groupname = jsonAnalysis.getValue(data, "GROUPNAME");
		}
		this.ExecProto();
	}

	public void ExecProto() {
		System.out.print("进入");
		// 添加联系人请求

		if (revPacket.getType() == ConstParam.TYPE_1) {
			// 查询联系人表，是否存在
			System.out.println("进去type=1");;
			int userid = userDao.findAppuserByName(user).getUserID();//自己ID
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
//			ContactInfo contactInf = new ContactDao().findContactByuser(userid, cuserid);
//			if (contactInf == null || contactInf.getState() == "0" || contactInf.getState() == "-1" ) {//OK
//				// 通过查询用户信息表
//				UserInfo cuserInf = new UserDao().findAppuserByName(cuser);// 联系用户
//				if (cuserInf == null) {//OK
//					System.out.println("不是用户");
//					errorPktState = ConstParam.CONTACT_ERROR_0;  // 标记发送第一种错误报文（用户信息表中不存在该cuser
//					byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
//					SendPkt(errorPacket);
//					userNode.setLastPacketInfo(errorPacket);
			UserInfo cuserInf = userDao.findAppuserByName(cuser);// 联系用户
			if (cuserInf != null) {//OK
				// 通过查询用户信息表
				System.out.println("是用户");
				int cuserid = cuserInf.getUserID();//对方ID
				ContactInfo contactInf = contactDao.selectInfOfByMasteridAndContactid(userid, cuserid);
				if (contactInf != null) {//OK
					System.out.println("已存在联系人");
					errorPktState = ConstParam.CONTACT_1;
					byte[] successPacket = PackPkt(ConstParam.CONTACT_1);// 
					SendPkt(successPacket);
					userNode.setLastPacketInfo(successPacket);// 更新缓存
				} else {
					// 获取set，判断set&8
					System.out.println("不是联系人");
					if (Integer.parseInt(cuserInf.getSets()) == 8) {//OK
						errorPktState = ConstParam.CONTACT_2;
						byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);// 标记发送第一种错误报文,对方禁止添加联系人
						SendPkt(errorPacket);
						System.out.println("对方禁止添加");
					} else {
						System.out.println("cuserInf.getSet()：" + cuserInf.getSets());
						UserInfo userInf = userDao.findAppuserByName(user);// 查找
						AppNode cuserNode = SysInfo.getInstance().getAppNodeByAccount(cuser);//查询对方节点
						if(cuserNode != null){//判断是否在线
							System.out.println("对方在线");	
							if (Integer.parseInt(cuserInf.getSets()) == 2) {
								System.out.println("set的值为：2");
								ContactInfo contactinf = new ContactInfo();
								System.out.println(contactinf);
								contactDate = new Date();
								contactinf.setMasterId(userid);
								contactinf.setContactedId(cuserid);
								contactinf.setAlias(alias);
								contactinf.setGroupName(groupname);
								contactinf.setSetTime(contactDate);
								contactinf.setState("1");//联系人状态为1
								contactDao.insertInfOfContact(contactinf);
								
								ContactInfo contactinf2 = new ContactInfo();
								contactinf2.setMasterId(cuserid);
								contactinf2.setContactedId(userid);
								contactinf2.setState("1");
								contactinf2.setSetTime(contactDate);
								contactDao.insertInfOfContact(contactinf2);
								
								System.out.println("加入成功");
								
								ContactWait contactWait = new ContactWait();
								Date applytime = new Date();
								contactWait.setMasterId(userid);
								contactWait.setContactedId(userid);
								contactWait.setGroupName(groupname);;
								contactWait.setApplyTime(applytime);
								contactWait.setState("4");//添加状态
								contactWait.setKind("4");//添加通知
								contactWait.setMessage(message);
								sign = contactWaitDao.insertInfOfContactWait(contactWait);
								System.out.println("加入成功");
									
								byte[] successPkt = PackPkt(ConstParam.CONTACT_0);
								SendPkt(successPkt);
								userNode.setLastPacketInfo(successPkt);// 更新缓存
								
								byte[] packPkt1 = ProtocolMessage.pushMsgNitice(userNode,cuser,(byte)4,message,applytime,sign);
								cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));//发送给对方
								cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
								System.out.println("直接添加");
							} else {
								System.out.println("等待添加");
								ContactInfo contactinf = new ContactInfo();
								System.out.println(contactinf);
								contactDate = new Date();
								contactinf.setMasterId(userid);
								contactinf.setContactedId(cuserid);
								contactinf.setAlias(alias);
								contactinf.setGroupName(groupname);
								contactinf.setSetTime(contactDate);
								contactinf.setState("0");//联系人状态为0
								contactDao.insertInfOfContact(contactinf);
								
								ContactInfo contactinf2 = new ContactInfo();
								contactinf2.setMasterId(cuserid);
								contactinf2.setContactedId(userid);
								contactinf2.setSetTime(contactDate);
								contactinf2.setState("0");
								contactDao.insertInfOfContact(contactinf2);
								
								System.out.println("加入成功");

								ContactWait contactWait = new ContactWait();
								Date applytime = new Date();
								contactWait.setMasterId(userid);
								contactWait.setContactedId(cuserid);
								contactWait.setGroupName(groupname);
								contactWait.setApplyTime(applytime);
								contactWait.setState("1");//请求状态
								contactWait.setKind("0");//请求确认
								contactWait.setMessage(message);;
								sign = contactWaitDao.insertInfOfContactWait(contactWait);
	
								byte[] waitPkt = PackPkt(ConstParam.CONTACT_0);// 
								SendPkt(waitPkt);
								userNode.setLastPacketInfo(waitPkt);// 更新缓存
	
								byte[] packPkt1 = ProtocolMessage.pushMsgNitice(userNode,cuser,(byte)4,message,applytime,sign);
								cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));//发送给对方
								cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
								System.out.println("等待确认");
							}
						}else{//没在线
							System.out.println("对方没在线");
							if (Integer.parseInt(cuserInf.getSets()) == 2) {
								System.out.println("直接添加");								
								ContactInfo contactinf = new ContactInfo();
								System.out.println(contactinf);
								contactDate = new Date();
								contactinf.setMasterId(userid);
								contactinf.setContactedId(cuserid);
								contactinf.setAlias(alias);
								contactinf.setGroupName(groupname);
								contactinf.setSetTime(contactDate);
								contactinf.setState("1");//联系人状态为1
								contactDao.insertInfOfContact(contactinf);
								
								ContactInfo contactinf2 = new ContactInfo();
								contactinf2.setMasterId(cuserid);
								contactinf2.setContactedId(userid);
								contactinf2.setState("1");
								contactinf2.setSetTime(contactDate);
								contactDao.insertInfOfContact(contactinf2);
								
								System.out.println("加入成功");
								ContactWait contactWait = new ContactWait();
								Date applytime = new Date();
								contactWait.setMasterId(userid);
								contactWait.setContactedId(cuserid);
								contactWait.setGroupName(groupname);
								contactWait.setApplyTime(applytime);
								contactWait.setState("4");
								contactWait.setKind("4");//添加通知
								contactWait.setMessage(message);;
								contactWaitDao.insertInfOfContactWait(contactWait);
								System.out.println("加入等待表成功");
								
								byte[] successPkt = PackPkt(ConstParam.CONTACT_0);
								SendPkt(successPkt);
								userNode.setLastPacketInfo(successPkt);// 更新缓存
								//没在线不用通知，等定时器来查
								System.out.println("添加成功");
							}else {
								System.out.println("等待添加");
								ContactInfo contactinf = new ContactInfo();
								contactDate = new Date();
								contactinf.setMasterId(userid);
								contactinf.setContactedId(cuserid);
								contactinf.setAlias(alias);
								contactinf.setGroupName(groupname);
								contactinf.setSetTime(contactDate);
								contactinf.setState("0");//联系人状态为0
								contactDao.insertInfOfContact(contactinf);
								System.out.println("加入成功");
								
								ContactInfo contactinf2 = new ContactInfo();
								contactinf2.setMasterId(cuserid);
								System.out.println(userid);
								contactinf2.setContactedId(userid);
								contactinf2.setState("0");
								contactinf2.setSetTime(contactDate);
								contactDao.insertInfOfContact(contactinf2);
								
								ContactWait contactWait = new ContactWait();
								System.out.println("contactWait创建好了");
								Date applytime = new Date();
								contactWait.setMasterId(userid);
								contactWait.setContactedId(cuserid);
								contactWait.setGroupName(groupname);
								contactWait.setApplyTime(applytime);
								contactWait.setState("1");//请求状态
								contactWait.setKind("0");//请求确认
								contactWait.setMessage(message);;
								sign = contactWaitDao.insertInfOfContactWait(contactWait);
	
								byte[] waitPkt = PackPkt(ConstParam.CONTACT_0);//
								SendPkt(waitPkt);
								userNode.setLastPacketInfo(waitPkt);// 更新缓存
								System.out.println("等待确认");
							}
						}					
					}
				}
			} else {
				System.out.println("不是用户");
				errorPktState = ConstParam.CONTACT_0;  // 标记发送第一种错误报文（用户信息表中不存在该cuser
				byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
				SendPkt(errorPacket);
				userNode.setLastPacketInfo(errorPacket);
			}
		}

		// 脱离联系人请求
		if (revPacket.getType() == ConstParam.TYPE_3) {
			int userid = userDao.findAppuserByName(user).getUserID();//自己ID
			int cuserid = userDao.findAppuserByName(cuser).getUserID();//对方ID
			// 查询是否是联系人
			System.out.println("进入type3");
			ContactInfo contactInf1 = contactDao.selectInfOfByMasteridAndContactid(userid, cuserid);
			ContactInfo contactInf2 = contactDao.selectInfOfByMasteridAndContactid(cuserid, userid);
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			if (contactInf1 == null && contactInf2 == null) {
				System.out.println("不是联系人");
				errorPktState = ConstParam.ERROR_PKT_STATE_0; // 
				byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
				SendPkt(errorPacket);
			} else {
				System.out.println("是联系人");
//				UserInfo userInf = new UserDao().findAppuserByName(user);// 查询用户
//				System.out.println("userInf.getPassword()为:" + userInf.getPassword());
//				System.out.println("pass为:" + pass);
//				if (userInf.getPassword().equals(pass)) {// 判断密码相等不
					contactDao.updateInfOfContact(contactInf1,(byte)8,"-1");
					contactDao.updateInfOfContact(contactInf2,(byte)8,"-1");
					deleteDate = new Date();
					AppNode cuserNode = SysInfo.getInstance().getAppNodeByAccount(cuser);
					if(cuserNode != null && cuserNode.getState() == 2){//对方在线	
						ContactWait contactWait = new ContactWait();
						contactWait.setMasterId(userid);
						contactWait.setContactedId(cuserid);
						contactWait.setGroupName(null);
						contactWait.setApplyTime(deleteDate);
						contactWait.setGroupName(null);
						contactWait.setState("4");//请求状态
						contactWait.setKind("3");//删除通知
						contactWait.setMessage(null);
						sign = contactWaitDao.insertInfOfContactWait(contactWait);
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode,cuser,(byte)3,message,deleteDate,sign);
						cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));//发送给对方
						cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}//不在线定时器查
					System.out.println("删除成功");
					byte[] deleteSuccessPacket = PackPkt(ConstParam.CONTACT_0);
					SendPkt(deleteSuccessPacket);
					userNode.setLastPacketInfo(deleteSuccessPacket);// 更新缓存
					deleteDate = null;
//				} else {
//					System.out.println("pass与userInf.getPassword()不相等");
//					errorPktState = ConstParam.ERROR_PKT_STATE_1; 
//					byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);// 
//					SendPkt(errorPacket);
//					userNode.setLastPacketInfo(errorPacket);// 更新缓存
//				}
			}
		}

		// 获取联系人
		if (revPacket.getType() == ConstParam.TYPE_5) {
			int n;// 联系人个数
			System.out.print("进入TYPE=5\n");
			int userid = userDao.findAppuserByName(user).getUserID();
			System.out.println("user为：" + user);
			List<ContactInfo> contactInfall = (List<ContactInfo>) contactDao.selectInfOfContactByMasterid(userid,from,-1);
			System.out.println("获取到了所有联系人\n");
			List<ContactInfo> contactInf = (List<ContactInfo>) contactDao.selectInfOfContactByMasterid(userid,from,40);
			System.out.println("获取到了进40个联系人\n");
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			if (contactInf.size() != 0) {
				System.out.println("有联系人:");
				n = contactInfall.size();
				System.out.println("联系人有" + n);
				for (int i = 0; i < contactInf.size(); i++) {
					System.out.println("进入for循环");
					ContactInfo cuserInf = contactInf.get(i);
					JSONObject contact = new  JSONObject();
					contact.put("ALIAS", cuserInf.getAlias());
					contact.put("ACCOUNT",String.valueOf(cuserInf.getContactedId()));
					contact.put("GROUP",cuserInf.getGroupName());
					System.out.println(contact);		
					jsonArray.add(contact);		
				}
				byte[] contactinf = PackPkt(ConstParam.FOUNDCONTACT);
				SendPkt(contactinf);
				userNode.setLastPacketInfo(contactinf);// 更新缓存
				
				jsonArray.clear();
				
				n = n - 40;					
				if(n <= 0){
					System.out.println("没有更多联系人了");	
				}else{
					System.out.println("还有" + n + -40*from + "个联系人");	
				}
			} else {
				System.out.print("没有联系人");
				errorPktState = ConstParam.SENT_PKT_TYPE_3; //
				byte[] errorPacket = PackPkt(ConstParam.CONTACT_2);
				SendPkt(errorPacket);
				userNode.setLastPacketInfo(errorPacket);// 更新缓存
			}
		}

		// 联系人通告
		if(revPacket.getType() == ConstParam.TYPE_7){
			System.out.println("进入到TYPE = 7");
			Date date = new Date();
			//int cuserid = new UserDao().findAppuserByID(revPacket.getSid()).getName();//查询自己id
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());//自己的节点	
			AppNode cuserNode = SysInfo.getInstance().getAppNodeById(userid);//对方的节点
			if(kind == 1){//确认
				System.out.println("kind = 1");
				ContactInfo contactInf = contactDao.selectContactByUseridAndState(revPacket.getSid(),userid);//判断userid在contactinf是否有状态为0的
				if(contactInf == null){
					byte[] nullPacket = PackPkt(ConstParam.CONTACT_2);
					SendPkt(nullPacket);
					userNode.setLastPacketInfo(nullPacket);// 更新缓存
				}else{
					contactDao.updateInfOfContact(contactInf,(byte) 8,"1");
					ContactWait contactWait = contactWaitDao.selectInfOfContactWaitByMasteridAndContactid(revPacket.getSid(),userid,(byte) 2);//过来的user是被添加的，cuser才是主动的
					sign = contactWaitDao.updateInfOfContactWait(contactWait,Byte.valueOf("3"));
					System.out.println(sign);
					if(cuserNode != null ){
						System.out.println("对方在线");
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode,cuser,kind,message,date,sign);
						cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));//发送给对方
						cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}

					byte[] successPacket = PackPkt(ConstParam.NOTICE_0);
					SendPkt(successPacket);
					userNode.setLastPacketInfo(successPacket);// 更新缓存
				}
			}
			if(kind == 2){//否认
				System.out.println("kind = 2");
				ContactInfo contactInf = contactDao.selectContactByUseridAndState(revPacket.getSid(),userid);//判断userid是否有状态为2的
				if(contactInf == null){
					byte[] nullPacket = PackPkt(ConstParam.CONTACT_2);
					SendPkt(nullPacket);
					userNode.setLastPacketInfo(nullPacket);// 更新缓存
				}else{
					contactDao.updateInfOfContact(contactInf,(byte)8,"-1");
					ContactWait contactWait = contactWaitDao.selectInfOfContactWaitByMasteridAndContactid(revPacket.getSid(),userid,(byte)2);//查询是被添加的为2的记录
					sign = contactWaitDao.updateInfOfContactWait(contactWait,Byte.valueOf("3"));
					if(cuserNode != null ){
						System.out.println("对方在线");
						byte[] packPkt1 = ProtocolMessage.pushMsgNitice(cuserNode,cuser,kind,message,date,sign);
						cuserNode.getIoSession().write(IoBuffer.wrap(packPkt1));//发送给对方
						cuserNode.setLastPacketInfo(packPkt1);// 更新缓存
					}
					byte[] successPacket = PackPkt(ConstParam.NOTICE_0);
					SendPkt(successPacket);
					userNode.setLastPacketInfo(successPacket);// 更新缓存
				}
			}
		}
		
		// 变更联系人
		if (revPacket.getType() == ConstParam.TYPE_9) {
			// 查询联系人表
			System.out.println("进入到TYPE=9");
			int cuserid = userDao.findAppuserByName(user).getUserID();//对方ID
			ContactInfo contactInf = contactDao.selectInfOfByMasteridAndContactid(userid, cuserid);
			System.out.println(user);
			System.out.println(kind);
			System.out.println(alias);
			System.out.println(groupname);
			AppNode userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
			if((kind & 0x01) == 1){
				System.out.println("变更别名");
				contactDao.updateInfOfContact(contactInf,kind,alias);
				byte[] updatesuccessPacket = PackPkt(ConstParam.CHANGE);// 
				SendPkt(updatesuccessPacket);
				userNode.setLastPacketInfo(updatesuccessPacket);// 更新缓存
			}
			if((kind >> 1 & 0x01) == 2){
				System.out.println("变更组名");
				contactDao.updateInfOfContact(contactInf,kind,groupname);
				byte[] updateSuccessPacket = PackPkt(ConstParam.CHANGE);// 
				SendPkt(updateSuccessPacket);
				userNode.setLastPacketInfo(updateSuccessPacket);// 更新缓存
			}	
		}
	}
	
	public byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_10);
		msg.setSid(ConstParam.SERVER_ID);
		msg.setOpt(ConstParam.OPT_8);
		if (revPacket.getType() == ConstParam.TYPE_1) {//关联请求
			msg.setType(ConstParam.TYPE_2); // type = 2
		}		
		if (revPacket.getType() == ConstParam.TYPE_3) {//脱离请求
			msg.setType(ConstParam.TYPE_4); // type = 4
		}
		if (revPacket.getType() == ConstParam.TYPE_5) {
			msg.setType(ConstParam.TYPE_6); // type=6
		}
		if (revPacket.getType() == ConstParam.TYPE_7) {
			msg.setType(ConstParam.TYPE_8); // type=8
		}
		if (revPacket.getType() == ConstParam.TYPE_9) {
			msg.setType(ConstParam.TYPE_10); // type=10
		}
		String packetBody = null;
		switch (i) {
		case 0: // ret = 0
			if (revPacket.getType() == ConstParam.TYPE_1) {
				packetBody = "{'DATA':{'RET':'0','INFO':'" + contactDate + "'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {
				packetBody = "{'DATA':{'RET':'0','INFO':'" + deleteDate + "'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_5) {
				packetBody = "{'DATA':{'RET':'1','INFO':'"+ cuser + " " + alias + " " + groupname + "'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_7) {
				packetBody = "{'DATA':{'RET':'0','INFO':'confirmation '}}";//
			}
			if (revPacket.getType() == ConstParam.TYPE_9) {
				packetBody = "{'DATA':{'RET':'0','INFO':'add a success'}}";//
			}
			break;
		case 1:// ret = 1
			if (revPacket.getType() == ConstParam.TYPE_1) {
				packetBody = "{'DATA':{'RET':'1','INFO':'" + contactDate + "'}}";
			}
			if(revPacket.getType() == ConstParam.TYPE_5) {
				packetBody = "{'DATA':{'RET':'1','INFO':'" + jsonArray + "'}}";
			}
			break;
		case 2:// ret = -1
			if (revPacket.getType() == ConstParam.TYPE_1) {
				if (errorPktState == ConstParam.CONTACT_0) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'There is no this cuser'}}";
				}
				if (errorPktState == ConstParam.CONTACT_1) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'Account has been associated'}}";
				}
				if (errorPktState == ConstParam.CONTACT_2) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'This user refused to add anyone'}}";//
				}
			}
			if (revPacket.getType() == ConstParam.TYPE_3) {
				if (errorPktState == ConstParam.ERROR_PKT_STATE_0) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'There is no this cuser'}}"; // 
				}
				if (errorPktState == ConstParam.ERROR_PKT_STATE_1) {
					packetBody = "{'DATA':{'RET':'-1','INFO':'pass is wrong'}}"; // 
				}
			}
			if (revPacket.getType() == ConstParam.TYPE_5) {
				packetBody = "{'DATA':{'RET':'-1','INFO':'There is no contact'}}";
			}
			if(revPacket.getType() == ConstParam.TYPE_7){
				packetBody = "{'DATA':{'RET':'-1','INFO':'There is no shenqing'}}";
			}
			if (revPacket.getType() == ConstParam.TYPE_9) {
				packetBody = "{'DATA':{'RET':'-1','INFO':'Add failure'}}";//
			}
			break;
		}
		msg.setData(packetBody);
		return msg.createMessage(msg);
	}

	public void SendPkt(byte[] sendPacket) {
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));//
	}
	
	//show报文信息
	public void ShowPacket(PacketInfo packetInfo) {
		System.out.print("cmd=" + packetInfo.getCmd());
		System.out.print("type=" + packetInfo.getType());
		System.out.print("opt=" + packetInfo.getOpt());
		System.out.print("sort=" + packetInfo.getSort());
		System.out.print("sid=" + packetInfo.getSid());
		System.out.print("seq=" + packetInfo.getSeq());
		System.out.println("ack=" + packetInfo.getAck());
	}
}
