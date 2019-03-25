package cn.smartpolice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.MsgAlarm;
import cn.smartpolice.hibernate.MsgChat;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.MsgTask;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 消息传递协议 cmd=5
 * 
 * @author 马晓林
 * 
 */
public class ProtocolMessage extends ProtocolBase {

//	@Resource(name="messageDao")
//	private MessageDao messageDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	MessageDao messageDao = (MessageDao) ac.getBean("messageDao");
	
	int num;
	ArrayList<String> users;
	ArrayList<String> mTypes;
	ArrayList<String> mDates;
	ArrayList<String> contents;
	ArrayList<String> dests;

	String askMsgType;
	int askMsgNum;

	String ret;
	String info;

	String sign;
	JSONObject data = new JSONObject();
	JSONArray infoArray = new JSONArray();
	JSONObject dataChild = new JSONObject();
	JSONObject infoChild = new JSONObject();
	JSONObject contentChild = new JSONObject();
	/**
	 * 服务器推送type为8和16的关联人通知消息
	 * 
	 * @param state
	 * @param dest
	 */

	public static byte[] pushMsgNitice(UserNode userNode,String masterId, byte kind, String msg, Date date,String sign) {
		JSONObject data = new JSONObject();
		JSONArray infoArray = new JSONArray();
		JSONObject dataChild = new JSONObject();
		JSONObject infoChild = new JSONObject();
		JSONObject contentChild = new JSONObject();
		
		contentChild.put("MSG", msg);
		contentChild.put("KIND", kind);
		
		infoChild.put("CONTENT", contentChild);		
		infoChild.put("USER", masterId);
		infoChild.put("CLASS", 8);
		infoChild.put("DATE", date.getTime());
		
		infoArray.add(infoChild);
		dataChild.put("INFO", infoArray);
		dataChild.put("NUM", 1);		
		dataChild.put("SIGN", sign);
		data.put("DATA", dataChild);

		PacketMsg packetMsg = new PacketMsg();
		packetMsg.setData(data.toString());
		data.clear();
		infoArray.clear();
		dataChild.clear();
		infoChild.clear();
		contentChild.clear();
		int seq;
		while(true){
			Random rando = new Random();
			seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
			if(seq != userNode.getSntPktId()){
				userNode.setSntPktId(seq);
				break;
			}
		}		
		packetMsg.setSeq(seq);
		packetMsg.setCmd(ConstParam.CMD_5);
		packetMsg.setType(ConstParam.TYPE_4);
		packetMsg.setOpt(ConstParam.OPT_8);
		packetMsg.setSid(ConstParam.SERVER_ID);
		
		return packetMsg.createMessage(packetMsg);
		

	}

	public static byte[] pushMsgNitice(UserNode userNode,String id, String deviceId, Date date, byte kind, byte right, String pass,
			String msg,String sign) {
		JSONObject data = new JSONObject();
		JSONArray infoArray = new JSONArray();
		JSONObject dataChild = new JSONObject();
		JSONObject infoChild = new JSONObject();
		JSONObject contentChild = new JSONObject();
		
		contentChild.put("MSG", msg);
		contentChild.put("KIND", kind);
		contentChild.put("PUSER", kind);
		contentChild.put("DATE", kind);
		contentChild.put("RIGHT", kind);
		contentChild.put("PASS", kind);
		
		infoChild.put("CONTENT", contentChild);		
		infoChild.put("USER", id);
		infoChild.put("CLASS", 16);
		infoChild.put("DATE", date.getTime());
		
		infoArray.add(infoChild);
		dataChild.put("INFO", infoArray);
		dataChild.put("NUM", 1);		
		dataChild.put("SIGN", sign);
		data.put("DATA", dataChild);
		PacketMsg packetMsg = new PacketMsg();
		packetMsg.setData(data.toString());
		data.clear();
		infoArray.clear();
		dataChild.clear();
		infoChild.clear();
		contentChild.clear();
		int seq;
		while(true){
			Random rando = new Random();
			seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
			if(seq != userNode.getSntPktId()){
				userNode.setSntPktId(seq);
				break;
			}
		}		
		packetMsg.setSeq(seq);
		packetMsg.setCmd(ConstParam.CMD_5);
		packetMsg.setType(ConstParam.TYPE_4);
		packetMsg.setOpt(ConstParam.OPT_8);
		packetMsg.setSid(ConstParam.SERVER_ID);
		return packetMsg.createMessage(packetMsg);
	}

	@Override
	void ParsePktProto(PacketInfo packetInfo) {
		// TODO Auto-generated method stub
		System.out.println("消息推送");
		this.revPacket = packetInfo;
		String data = packetInfo.getData();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		// 消息推送请求
		if (revPacket.getType() == 1) {
			num = Integer.parseInt(jsonAnalysis.getValue(data, "NUM"));
			System.out.println(num);
			users = new ArrayList<String>(num);
			mTypes = new ArrayList<String>(num);
			mDates = new ArrayList<String>(num);
			contents = new ArrayList<String>(num);
			dests = new ArrayList<String>(num);
			String INFO = jsonAnalysis.getValue(data, "INFO");
			JSONArray jsonArr = JSONArray.fromObject(INFO);
			for (int i = 0; i < num; i++) {
				users.add(jsonArr.getJSONObject(i).getString("USER"));
				mTypes.add(jsonArr.getJSONObject(i).getString("CLASS"));
				mDates.add(jsonArr.getJSONObject(i).getString("DATE"));
				contents.add(jsonArr.getJSONObject(i).getString("CONTENT"));
				dests.add(jsonArr.getJSONObject(i).getString("DEST"));
			}
		}

		// 未读消息请求
		if (revPacket.getType() == ConstParam.TYPE_3) {
			askMsgType = jsonAnalysis.getValue(data, "CLASS");
			if(jsonAnalysis.getValue(data, "NUM")!=null) {
			askMsgNum = Integer.parseInt(jsonAnalysis.getValue(data, "NUM"));
			}else{
				askMsgNum=-1;
			}
		}

		if (revPacket.getType() == ConstParam.TYPE_5) {
			ret = jsonAnalysis.getValue(data, "RET");
			info = jsonAnalysis.getValue(data, "INFO");
		}
		this.ExecProto();
	}

	@Override
	void ExecProto() {
		UserNode userNode = null;
		// 通过报文sort判断是dev还是app
		if (revPacket.getSort() == ConstParam.SORT_2) {
			userNode = revPacket.getDevNode();
		}
		if (revPacket.getSort() == ConstParam.SORT_0) {
			userNode = revPacket.getAppNode();
			System.out.println("这边的节点："+userNode);
		}
		// 消息推送
		if (revPacket.getType() == ConstParam.TYPE_1) {
			for (int i = 0; i < num; i++) {
				if (users.get(i) != null && mTypes.get(i) != null && mDates.get(i) != null && contents.get(i) != null
						&& dests.get(i) != null) {// attach可为空
					MsgTask msgTask = new MsgTask();
					msgTask.setMsgNum(num);
					msgTask.setSendUserID(Integer.parseInt(users.get(i)));
					msgTask.setmType(Integer.parseInt(mTypes.get(i)));
					msgTask.setmDate(Long.valueOf(mDates.get(i)));
					msgTask.setContent(contents.get(i));
					msgTask.setRevUserID(Integer.parseInt(dests.get(i))); // 若存在接收者，则revID为接收者ID,若不存在接收者，revID为0
					SysInfo.getInstance().addMsgTask(msgTask);
					SysInfo.getInstance().msgTaskThreadCheck();
					byte[] unReadMsg = PackPkt(2);
					SendPkt(unReadMsg);
					userNode.setLastPacketInfo(unReadMsg);// 更新缓存

				} else {
					byte[] unReadMsg = PackPkt(3);// info信息不完整
					SendPkt(unReadMsg);
					userNode.setLastPacketInfo(unReadMsg);// 更新缓存
				}
			}

		}
		if (revPacket.getType() == ConstParam.TYPE_5) {// 消息接收表的更新
			/**
			 * 返回代码，0表示正确收到信息；1 重复收到；-1表示失败 RET = -1：错误原因 RET=其他值：SIGN值
			 */
			if (ret.equals("0")) {
				// 调用方法，传入SIGN和sid更新表
				messageDao.UpdateMessageOfSign(revPacket.getSid(), info);
			}
			if (ret.equals("1")) {
				//
				messageDao.UpdateMessageOfSign(revPacket.getSid(), info);
			}
			if (ret.equals("-1")) {
				// 当失败时能怎么办呢？？？
			}

		}

		// 未读消息请求
		if (revPacket.getType() == ConstParam.TYPE_3) {
			if (askMsgNum == 0) {
				if (askMsgType.equals(ConstParam.MessageType_0)) { // 所有类型
					HashMap<String, List<MsgRecv>> msg = messageDao
							.SelectMessageOfUnReadMsg(revPacket.getSid(), askMsgNum);
					if (msg != null) {
						
						sign = msg.keySet().iterator().next();
						MsgAlarm alarm = new MsgAlarm();
						MsgChat chat = new MsgChat();
						MsgNotice notice = new MsgNotice();
						List<MsgRecv> msgList = msg.get(sign);
						System.out.println("sign"+sign);				
						for (int i = 0; i < msgList.size(); i++) {
							System.out.println("list:"+msgList.size());
							if (msgList.get(i).getMsgtype().equals(ConstParam.MessageType_1)) {
								// 查询报警消息
								System.out.println("报警");
								alarm = messageDao.SelectMessageOfAlarm(msgList.get(i).getMessageid());
								contentChild.put("SORT", alarm.getSort());
								contentChild.put("LEVEL", alarm.getLevel());
								contentChild.put("ATTACH", alarm.getUrl());								
								infoChild.put("CONTENT", contentChild);		
								infoChild.put("USER", alarm.getDeviceid());
								infoChild.put("CLASS", 1);
								infoChild.put("DATE",alarm.getTime());
								infoArray.add(infoChild);
							}
							if (msgList.get(i).getMsgtype().equals(ConstParam.MessageType_2)) {
								// 查询聊天消息
								System.out.println("聊天");
								chat = messageDao.SelectMessageOfChat(msgList.get(i).getMessageid());
								contentChild.put("MSG", chat.getMsg());
								contentChild.put("TYPE", chat.getType());
								contentChild.put("COLOR", chat.getColor());								
								infoChild.put("CONTENT", contentChild);		
								infoChild.put("USER", chat.getSendid());
								infoChild.put("CLASS", 2);
								infoChild.put("DATE", chat.getSendtime());
								infoArray.add(infoChild);
							}
							if (msgList.get(i).getMsgtype().equals(ConstParam.MessageType_4)) {
								// 查询通知消息
								System.out.println("通知");
								System.out.println(msgList.get(i).getMessageid());
								notice = messageDao.SelectMessageOfNotice(msgList.get(i).getMessageid());
								contentChild.put("MSG", notice.getMsg());
								contentChild.put("TITLE",notice.getTitle());
								contentChild.put("SET", notice.getSets());								
								infoChild.put("CONTENT", contentChild);		
								infoChild.put("USER", notice.getSendid());
								infoChild.put("CLASS", 4);
								infoChild.put("DATE", notice.getSendtime());
								infoArray.add(infoChild);
							}
						}
						byte[] unReadMsg1 = PackPkt(ConstParam.SENT_PKT_TYPE_1);
						SendPkt(unReadMsg1);
						userNode.setLastPacketInfo(unReadMsg1);// 更新缓存
						msg.clear();// 手动清空
					} else {// 发送 没有未读消息的通知
						byte[] noUnReadMsg = PackPkt(ConstParam.SENT_PKT_TYPE_4);
						userNode.setLastPacketInfo(noUnReadMsg);// 更新缓存
						SendPkt(noUnReadMsg);

					}
				}
				else{
					byte[] error = PackPkt(ConstParam.SENT_PKT_TYPE_5);
					userNode.setLastPacketInfo(error);// 更新缓存
					SendPkt(error);
				}

			} else {// 当num不为0时
				MsgAlarm alarm = new MsgAlarm();
				MsgChat chat = new MsgChat();
				MsgNotice notice = new MsgNotice();
				if (askMsgType.equals(ConstParam.MessageType_1)) {
					if (messageDao.SelectMessageOfUnReadAlarm(revPacket.getSid(), askMsgNum) != null) {
						HashMap<String, List<MsgAlarm>> msgList = messageDao
								.SelectMessageOfUnReadAlarm(revPacket.getSid(), askMsgNum);
						sign = msgList.keySet().iterator().next();
						List<MsgAlarm> alarms = msgList.get(sign);
						for (int i = 0; i < alarms.size(); i++) {
							alarm = alarms.get(i);
							contentChild.put("SORT", alarm.getSort());
							contentChild.put("LEVEL", alarm.getLevel());
							contentChild.put("ATTACH", alarm.getUrl());								
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", alarm.getDeviceid());
							infoChild.put("CLASS", 1);
							infoChild.put("DATE",alarm.getTime());
							infoArray.add(infoChild);
						}
						byte[] unReadMsg1 = PackPkt(ConstParam.SENT_PKT_TYPE_1);
						SendPkt(unReadMsg1);
						userNode.setLastPacketInfo(unReadMsg1);// 更新缓存
						msgList.clear();
					} else {
						byte[] noUnReadMsg = PackPkt(ConstParam.SENT_PKT_TYPE_4);
						userNode.setLastPacketInfo(noUnReadMsg);// 更新缓存
						SendPkt(noUnReadMsg);
					}
				}

				else if (askMsgType.equals(ConstParam.MessageType_2)) {
					if (messageDao.SelectMessageOfUnreadChat(revPacket.getSid(), askMsgNum) != null) {
						HashMap<String, List<MsgChat>> msgList = messageDao
								.SelectMessageOfUnreadChat(revPacket.getSid(), askMsgNum);
						sign = msgList.keySet().iterator().next();
						List<MsgChat> chats = msgList.get(sign);
						for (int i = 0; i < chats.size(); i++) {
							// 查询聊天消息
							chat = chats.get(i);
							contentChild.put("MSG", chat.getMsg());
							contentChild.put("TYPE", chat.getType());
							contentChild.put("COLOR", chat.getColor());								
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", chat.getSendid());
							infoChild.put("CLASS", 2);
							infoChild.put("DATE", chat.getSendtime());
							infoArray.add(infoChild);
						}
						byte[] unReadMsg1 = PackPkt(ConstParam.SENT_PKT_TYPE_1);
						SendPkt(unReadMsg1);
						userNode.setLastPacketInfo(unReadMsg1);// 更新缓存
						msgList.clear();
					} else {
						byte[] noUnReadMsg = PackPkt(ConstParam.SENT_PKT_TYPE_4);
						userNode.setLastPacketInfo(noUnReadMsg);// 更新缓存
						SendPkt(noUnReadMsg);
					}
				}

				else if (askMsgType.equals(ConstParam.MessageType_4)) {
					HashMap<String, List<MsgNotice>> msgList = messageDao
							.SelectMessageOfUnReadNotice(revPacket.getSid(), askMsgNum);
					if (msgList != null) {
						
						sign = msgList.keySet().iterator().next();
						List<MsgNotice> notices = msgList.get(sign);
						System.out.println("size:"+notices.size());
						for (int i = 0; i < notices.size(); i++) {
							// 查询通知消息
							notice = notices.get(i);
							contentChild.put("MSG", notice.getMsg());
							contentChild.put("TITLE",notice.getTitle());
							contentChild.put("SET", notice.getSets());								
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", notice.getSendid());
							infoChild.put("CLASS", 4);
							infoChild.put("DATE", notice.getSendtime());
							infoArray.add(infoChild);
							byte[] unReadMsg1 = PackPkt(ConstParam.SENT_PKT_TYPE_1);
							SendPkt(unReadMsg1);
							userNode.setLastPacketInfo(unReadMsg1);// 更新缓存
							msgList.clear();
						}
					} else {
						byte[] noUnReadMsg = PackPkt(ConstParam.SENT_PKT_TYPE_4);
						userNode.setLastPacketInfo(noUnReadMsg);// 更新缓存
						SendPkt(noUnReadMsg);
					}
				}
				else{
					byte[] error = PackPkt(ConstParam.SENT_PKT_TYPE_5);
					userNode.setLastPacketInfo(error);// 更新缓存
					SendPkt(error);
				}
			}
		}
	}

	byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_5);
		msg.setSid(ConstParam.SERVER_ID);
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());// 返回报文的ack与发送过来的报文seq一致
		if (i == 1) {
			msg.setType(ConstParam.TYPE_4);			
		}
		if (i == 2 || i == 3) {
			msg.setType(ConstParam.TYPE_2);			
		}
		switch (i) {
		case 1:
			dataChild.put("INFO", infoArray);
			dataChild.put("NUM", infoArray.size());		
			dataChild.put("SIGN", sign);
			data.put("DATA", dataChild);
			break;
		case 2:
			dataChild.put("INFO", "OK");
			dataChild.put("RET", 0);		
			data.put("DATA", dataChild);
			break;
		case 3:
			dataChild.put("INFO", "info message is not completely!");
			dataChild.put("RET", -1);		
			data.put("DATA", dataChild);
			break;
		case 4:
			dataChild.put("INFO", "没有查到此类型消息");
			dataChild.put("RET", -1);		
			data.put("DATA", dataChild);
			break;
		case 5:
			dataChild.put("INFO", "不提供此类查询方式");
			dataChild.put("RET", -1);		
			data.put("DATA", dataChild);
		}
		msg.setData(data.toString());
		data.clear();
		infoArray.clear();
		dataChild.clear();
		infoChild.clear();
		contentChild.clear();
		return msg.createMessage(msg);
	}

	@Override
	void SendPkt(byte[] sendPacket) {
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));
	}

}
