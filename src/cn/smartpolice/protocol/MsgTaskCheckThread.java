package cn.smartpolice.protocol;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.CompanyUser;
import cn.smartpolice.hibernate.ManagerInfo;
import cn.smartpolice.hibernate.MsgAlarm;
import cn.smartpolice.hibernate.MsgChat;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.RelateInfo;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.netdao.ManagerDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.MessageDaoImpl;
import cn.smartpolice.netdao.RelateDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.netdao.UserDaoImpl;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.MsgTask;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 消息推送检查线程 检查消息推送任务队列 若队列不为null则根据任务node信息，在消息发送记录表中添加记录，并在此线程中给在线的节点发送未读消息通知
 * 
 * @author maxiaolin
 *
 */
public class MsgTaskCheckThread implements Runnable {
	int sign;
	int messageid;

//	@Resource(name="relateDao")
//	private RelateDao relateDao;
//	
//	@Resource(name="messageDao")
//	private MessageDao messageDao;
//	
//	@Resource(name="userDao")
//	private UserDao userDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	UserDao userDao = (UserDao) ac.getBean("userDao");
	RelateDao relateDao = (RelateDao) ac.getBean("relateDao");
	MessageDao messageDao = (MessageDao) ac.getBean("messageDao");
	ManagerDao managerDao = (ManagerDao) ac.getBean("managerDao");
	
	public void run() {

		while (!SysInfo.getMsgTaskQueue().isEmpty()) {
			System.out.println("消息线程开始");
			MsgTask msgTask = SysInfo.getMsgTaskQueue().get(0);
			JsonAnalysis jsonAnalysis = new JsonAnalysis();
			try {

				if (msgTask.getmType() == 1) {
					MsgAlarm alarm = new MsgAlarm();
					alarm.setDeviceid(msgTask.getSendUserID());
					alarm.setTime(new Date(msgTask.getmDate()));
					String sort = jsonAnalysis.getValue(msgTask.getContent(), "SORT");
					if (sort != null) {
						alarm.setSort(Integer.parseInt(sort));
					}
					String level = jsonAnalysis.getValue(msgTask.getContent(), "LEVEL");
					if (level != null) {
						alarm.setLevel(Integer.parseInt(level));// 级别
					}
					String attach = jsonAnalysis.getValue(msgTask.getContent(), "ATTACH");
					if (attach != null) {
						alarm.setUrl(attach);// 得到URL
					}
					// 存入报警消息表,并且返回消息编号
					messageid = messageDao.insertMessageOfAlarm(alarm);
					// 将消息通知推送给前端设备关联人
					if (msgTask.getRevUserID() == 1) {
						// 查询关联关系表得到所有关联人id
						List list = relateDao.findUserIdByDeviceId(msgTask.getSendUserID());

						for (int i = 0; i < list.size(); i++) {
							Integer recvuserid = (Integer) list.get(i);
							// 存入消息接收记录表，返回SIGN
							MsgRecv msgRecv = new MsgRecv();
							msgRecv.setMessageid(messageid);
							msgRecv.setSenduserid(msgTask.getSendUserID());
							msgRecv.setRecvuserid(recvuserid);
							msgRecv.setMsgtype("1");//
							msgRecv.setState(0); // 标记为未读
							msgRecv.setRecvtime(new Date(msgTask.getmDate()));
							sign = messageDao.insertMessageOfMsgRecv(msgRecv);
							UserNode userNode = SysInfo.getInstance().getAppNodeById(recvuserid);
							if (userNode != null) {
								byte[] packPkt = PackPkt(msgTask, alarm, userNode);
								userNode.getIoSession().write(IoBuffer.wrap(packPkt));
								userNode.setLastPacketInfo(packPkt);// 更新缓存
							}
						}
						SysInfo.getInstance().removeMsgTask(msgTask);
					} else {
						SysInfo.getInstance().removeMsgTask(msgTask);
						System.out.println("接收者id不正确，已删除节点");
					}
				}
				// type==2为聊天消息
				else if (msgTask.getmType() == 2) {
					if(msgTask.getRevUserID()>10000){
					MsgChat chat = new MsgChat();
					String msg = jsonAnalysis.getValue(msgTask.getContent(), "MSG");
					String type = jsonAnalysis.getValue(msgTask.getContent(), "TYPE");// 字形
					String color = jsonAnalysis.getValue(msgTask.getContent(), "COLOR");
					chat.setColor(color);
					chat.setMsg(msg);
					chat.setType(type);
					chat.setSendid(msgTask.getSendUserID());
					chat.setSendtime(new Date(msgTask.getmDate()));
					chat.setRecvid(msgTask.getRevUserID());
					// 插入聊天消息表,返回消息编号
					messageid = messageDao.insertMessageOfChat(chat);

					MsgRecv msgRecv = new MsgRecv();
					msgRecv.setMessageid(messageid);
					msgRecv.setSenduserid(msgTask.getSendUserID());
					msgRecv.setRecvuserid(msgTask.getRevUserID());
					msgRecv.setMsgtype("2");// 2为聊天消息
					msgRecv.setState(0);
					msgRecv.setRecvtime(new Date(msgTask.getmDate()));
					sign = messageDao.insertMessageOfMsgRecv(msgRecv);// 将未读消息插入消息记录表中，返回SIGN
					UserNode userNode = SysInfo.getInstance().getAppNodeById(msgTask.getRevUserID());
					System.out.println(userNode != null);
					if (userNode != null) {
						byte[] packPkt = PackPkt(msgTask, chat, userNode);
						userNode.getIoSession().write(IoBuffer.wrap(packPkt));
						userNode.setLastPacketInfo(packPkt);// 更新缓存
					}
					SysInfo.getInstance().removeMsgTask(msgTask);
					}else{
						SysInfo.getInstance().removeMsgTask(msgTask);//如果该消息是聊天消息，但接受者id不大于一万，直接删除该消息，无效
					}
				}
				
				else if (msgTask.getmType() == 4) {//0 所有APP用户；2 所有设备管理员；3 厂商设备用户；4管理员；5全体所有成员
					MsgNotice notice = new MsgNotice();
					notice.setSendid(msgTask.getSendUserID());
					notice.setSendtime(new Date(msgTask.getmDate()));
					String title = jsonAnalysis.getValue(msgTask.getContent(), "TITLE");
					String msg = jsonAnalysis.getValue(msgTask.getContent(), "MSG");
					String set = jsonAnalysis.getValue(msgTask.getContent(), "SET");
					notice.setMsg(msg);
					notice.setTitle(title);
					notice.setSets(set);
					if (msgTask.getRevUserID() == 0) {// 系统通告，发送给所有APP用户
						notice.setRecvtype(1);
						
						messageid = messageDao.insertMessageOfNotice(notice);
						System.out.println(messageid);
						List<UserInfo> user = userDao.findAppuser();//查询所有APP用户id
						MsgRecv msgRecv = new MsgRecv();
						for (int i = 0; i < user.size(); i++) {
							msgRecv.setMessageid(messageid);
							msgRecv.setMsgtype("4");
							msgRecv.setRecvtime(new Date(msgTask.getmDate()));
							msgRecv.setState(0);
							msgRecv.setSenduserid(msgTask.getSendUserID());
							msgRecv.setRecvuserid(user.get(i).getUserID());
							sign = messageDao.insertMessageOfMsgRecv(msgRecv);
							UserNode userNode = SysInfo.getInstance().getAppNodeById(user.get(i).getUserID());
							if (userNode != null) {
								byte[] packPkt = PackPkt(msgTask, notice, userNode);
								userNode.getIoSession().write(IoBuffer.wrap(packPkt));
								userNode.setLastPacketInfo(packPkt);// 更新缓存
							}
						}
						SysInfo.getInstance().removeMsgTask(msgTask);
					}else if(msgTask.getRevUserID() == 2){//设备管理员
						MsgRecv msgRecv = new MsgRecv();
						notice.setRecvtype(2);
						messageid = messageDao.insertMessageOfNotice(notice);
						List list= relateDao.selectMangerOfDevice();
						for (int i = 0; i < list.size(); i++) {
							msgRecv.setMessageid(messageid);
							msgRecv.setRecvtime(new Date(msgTask.getmDate()));
							msgRecv.setState(0);
							msgRecv.setSenduserid(msgTask.getSendUserID());
							msgRecv.setRecvuserid((int)list.get(i));
							msgRecv.setMsgtype("2");
							sign = messageDao.insertMessageOfMsgRecv(msgRecv);
							UserNode userNode = SysInfo.getInstance().getAppNodeById((int)list.get(i));
							if (userNode != null) {
								byte[] packPkt = PackPkt(msgTask, notice, userNode);
								userNode.getIoSession().write(IoBuffer.wrap(packPkt));
								userNode.setLastPacketInfo(packPkt);// 更新缓存
							}
						}
						SysInfo.getInstance().removeMsgTask(msgTask);
					}else if(msgTask.getRevUserID() == 3) {//厂商设备用户
						MsgRecv msgRecv = new MsgRecv();
						notice.setRecvtype(3);
						msgRecv.setMsgtype("3");
						System.out.println("1");
						messageid = messageDao.insertMessageOfNotice(notice);
						System.out.println("2");
						List list= userDao.selectAllOfCompanyUser();
						System.out.println("3");
						for (int i = 0; i < list.size(); i++) {
							msgRecv.setMessageid(messageid);
							msgRecv.setRecvtime(new Date(msgTask.getmDate()));
							msgRecv.setState(0);
							msgRecv.setSenduserid(msgTask.getSendUserID());
							System.out.println("...............");
							msgRecv.setRecvuserid((int)list.get(i));
							System.out.println("4");
							sign = messageDao.insertMessageOfMsgRecv(msgRecv);
							System.out.println("5");
							UserNode userNode = SysInfo.getInstance().getAppNodeById((int)list.get(i));
							if (userNode != null) {
								byte[] packPkt = PackPkt(msgTask, notice, userNode);
								userNode.getIoSession().write(IoBuffer.wrap(packPkt));
								userNode.setLastPacketInfo(packPkt);// 更新缓存
							}
						}
						SysInfo.getInstance().removeMsgTask(msgTask);
					}else if(msgTask.getRevUserID() == 4) {//管理员
						MsgRecv msgRecv = new MsgRecv();
						notice.setRecvtype(4);
						msgRecv.setMsgtype("4");
						messageid = messageDao.insertMessageOfNotice(notice);
						List list= managerDao.selectAllOfManager();
						for (int i = 0; i < list.size(); i++) {
							msgRecv.setMessageid(messageid);
							msgRecv.setRecvtime(new Date(msgTask.getmDate()));
							msgRecv.setState(0);
							msgRecv.setSenduserid(msgTask.getSendUserID());
							msgRecv.setRecvuserid((int)list.get(i));
							sign = messageDao.insertMessageOfMsgRecv(msgRecv);
							UserNode userNode = SysInfo.getInstance().getAppNodeById((int)list.get(i));
							if (userNode != null) {
								byte[] packPkt = PackPkt(msgTask, notice, userNode);
								userNode.getIoSession().write(IoBuffer.wrap(packPkt));
								userNode.setLastPacketInfo(packPkt);// 更新缓存
							}
						}
						SysInfo.getInstance().removeMsgTask(msgTask);
					}else if(msgTask.getRevUserID() == 5) {//全体所有成员
						
					}else{
						System.out.println("不支持的消息类型,删除该消息");
						SysInfo.getMsgTaskQueue().remove(msgTask);
					}
				} else {
					System.out.println("不支持的消息类型,删除该消息");
					SysInfo.getMsgTaskQueue().remove(msgTask);
				}
			} catch (Exception e) {
				System.out.println("捕获异常，删除节点"+e.getMessage());
			} finally {
				SysInfo.getMsgTaskQueue().remove(msgTask);
			}
		}
	}

	public byte[] PackPkt(MsgTask msgTask, Object c, UserNode userNode) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_5);
		msg.setType(ConstParam.TYPE_4);
		msg.setOpt(ConstParam.OPT_8);
		msg.setSid(ConstParam.SERVER_ID);
		int seq;
		while (true) {
			Random rando = new Random();
			seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
			if (seq != userNode.getSntPktId()) {
				userNode.setSntPktId(seq);//产生一个不与上次主动发送报文时相同的发送序号
				break;
			}
		}
		msg.setSeq(seq);

		MsgAlarm alarm = new MsgAlarm();
		MsgChat chat = new MsgChat();
		MsgNotice notice = new MsgNotice();
		JSONObject data = new JSONObject();
		JSONArray infoArray = new JSONArray();
		JSONObject dataChild = new JSONObject();
		JSONObject infoChild = new JSONObject();
		JSONObject contentChild = new JSONObject();
		if (msgTask.getmType() == 1) {//构造报警消息
			alarm = (MsgAlarm) c;
			contentChild.put("SORT", alarm.getSort());
			contentChild.put("LEVEL", alarm.getLevel());
			contentChild.put("ATTACH", alarm.getUrl());
			infoChild.put("CONTENT", contentChild);
			infoChild.put("USER", alarm.getDeviceid());
			infoChild.put("CLASS", 1);
			infoChild.put("DATE", alarm.getTime().getTime());
			infoArray.add(infoChild);
		}
		if (msgTask.getmType() == 2) {//构造聊天消息
			chat = (MsgChat) c;
			contentChild.put("MSG", chat.getMsg());
			contentChild.put("TYPE", chat.getType());
			contentChild.put("COLOR", chat.getColor());
			infoChild.put("CONTENT", contentChild);
			infoChild.put("USER", chat.getSendid());
			infoChild.put("CLASS", 2);
			infoChild.put("DATE", chat.getSendtime());
			infoArray.add(infoChild);
		}
		if (msgTask.getmType() == 4) {//构造通知消息
			notice = (MsgNotice) c;
			contentChild.put("MSG", notice.getMsg());
			contentChild.put("TITLE", notice.getTitle());
			contentChild.put("SET", notice.getSets());
			infoChild.put("CONTENT", contentChild);
			infoChild.put("USER", notice.getSendid());
			infoChild.put("CLASS", 4);
			infoChild.put("DATE", notice.getSendtime());
			infoArray.add(infoChild);
		}
		dataChild.put("INFO", infoArray);
		dataChild.put("NUM", infoArray.size());
		dataChild.put("SIGN", sign);
		data.put("DATA", dataChild);
		msg.setData(data.toString());
		data.clear();
		infoArray.clear();
		dataChild.clear();
		infoChild.clear();
		contentChild.clear();
		return msg.createMessage(msg);
	}

}
