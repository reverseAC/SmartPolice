package cn.smartpolice.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.RelateWait;
import cn.smartpolice.hibernate.UserInfo;
import cn.smartpolice.netdao.DeviceDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONObject;
/**
 * @author 刘培煊
 * cmd=4 
 * 数据查询协议
 */

public class ProtocolQuery extends ProtocolBase {
	
//		@Resource(name="messageDao")
//		private MessageDao messageDao;
//		@Resource(name="userDao")
//		private UserDao userDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	UserDao userDao = (UserDao) ac.getBean("userDao");
	MessageDao messageDao = (MessageDao) ac.getBean("messageDao");
	
	  	String username;//请求者账号，8-20个字符
	    int code;//数据查询代码 
	  //数据查询需要的参数 目前只是在查询用户id时候用到了
	    //0的时候表示查询未读联通消息时候的id   1表示查询未读联系人通知时候Waitcontacted的id
	    //2表示查询未读联系人通知时候WaitMaster的id 
	    //3表示查询关联通知时候 Apply的id  4表示查询关联通知时候 Share的id
	    String para;
	    int errorPktState; // 标记不同错误报文
	    String info;
	    int num;//查询消息数量
	    int id1;//需要查询的id
	    JSONObject json0 = new JSONObject();//json对象
		JSONObject json1 = new JSONObject();//json对象
	 
	    int a=0;//报警消息统计type=1
		int b=0;//平台消息统计type=2
		int c=0;//聊天消息统计type=3
		int d=0;//未读联系人通知数
		int e=0;//未读关联人通知数
		int all=0;//所有消息
		
	@Override
	void ParsePktProto(PacketInfo packetInfo) {
		// TODO Auto-generated method stub
		super.revPacket = packetInfo;
		
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = revPacket.getDatapos(); i < revPacket.getMessage().length; i++) {
			stringBuffer.append((char) revPacket.getMessage()[i]);
				}
		
		String datas = stringBuffer.toString();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		String data = jsonAnalysis.getValue(datas, "DATA");
		username = jsonAnalysis.getValue(data, "USER");
		code = Integer.parseInt(jsonAnalysis.getValue(data, "CODE"));
		para = jsonAnalysis.getValue(data, "PARA");
		
		this.ExecProto();
		}
		
	@Override
	void ExecProto() {
		// 找到节点
		UserNode userNode = null;
		// 通过报文sort判断是dev还是app
		if (revPacket.getSort() == ConstParam.SORT_2) {
			userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
		}
		if (revPacket.getSort() == ConstParam.SORT_0) {
			userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
		}
		UserDao ud = userDao;
		UserInfo user = ud.findAppuserByName(username);
		int id = user.getUserID();
		// int id=Integer.parseInt(user);
		// 需要查询的id
		if (!para.isEmpty()) {
			UserInfo user1 = ud.findAppuserByName(username);
			id1 = user1.getUserID();
		}
		// MsgDao sd=new MsgDao();
		MessageDao md = messageDao;
		HashMap<String, List<MsgRecv>> mrList = md.SelectMessageOfUnReadMsg(id, 0);
		// List<MsgRecv> list =sd.findMsgRecvByUser(id) ;
		// 查询未读联系人通知数
		HashMap<String, List<ContactWait>> cwList0 = md.SelectMessageOfContactWaitMaster(id);
		HashMap<String, List<ContactWait>> cwList1 = md.SelectMessageOfContactWaitcontacted(id);
		// 查询未读关联通知数
		HashMap<String, List<RelateWait>> rwList0 = md.SelectMessageOfShare(id);
		HashMap<String, List<RelateWait>> rwList1 = md.SelectMessageOfApply(id);
		// 得到id这个用户的key
		String key0 = mrList.keySet().iterator().next();
		String key1 = cwList0.keySet().iterator().next();
		String key2 = cwList1.keySet().iterator().next();
		String key3 = rwList0.keySet().iterator().next();
		String key4 = rwList1.keySet().iterator().next();

		// 信息统计
		for (int i = 0; i < mrList.get(key0).size(); i++) {
			int type = Integer.parseInt(mrList.get(key0).get(i).getMsgtype());
			if (type == 0)
				a++;
			if (type == 1)
				b++;
			if (type == 2)
				c++;
		}

		d = cwList0.get(key1).size() + cwList1.get(key2).size();
		e = rwList0.get(key3).size() + rwList1.get(key4).size();
		all = mrList.get(key0).size();

		// 数据查询应答
		if (revPacket.getType() == ConstParam.TYPE_1) {
			/*
			 * if(code==32){ json2.clear(); json2.put("ID", id1); }
			 */
			if (code == 0) {
				num = all;
				// json2.put("ALL", all);
				// info="'ALL':'"+all+"'";
			} else if (code == 1) {
				num = a;
				// json2.put("ALARM", a);
				// info="'ALARM':'"+a+"'";
			} else if (code == 2) {
				num = c;
				// json2.put("CHAT", c);
				// info="'CHAT':'"+c+"'";
			} else if (code == 4) {
				num = b;
				// json2.put("NOTICE", b);
				// info="'':'"+b+"'";
			} else if (code == 8) {
				num = d;
				// json2.put("CONTACTWAIT", d);
				// info="'CONTACTWAIT':'"+d+"'";
			} else if (code == 16) {
				num = e;
				// json2.put("RELATEWAIT", e);
				// info="'RELATEWAIT':'"+e+"'";
			} else {
				byte[] errorPacket = PackPkt(ConstParam.ERROR_PKT_STATE_1);
				SendPkt(errorPacket);
				userNode.setLastPacketInfo(errorPacket);// 更新缓存
			}

			byte[] packet = PackPkt(2);
			SendPkt(packet);
			userNode.setLastPacketInfo(packet);// 更新缓存

		}
	}

	@Override
	byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_4);
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());//返回报文的ack与发送过来的报文seq一致
		msg.setSid(ConstParam.SERVER_ID);
		String packetBody = null;
		switch (i) {
		case 1: // ret = -1
			if(revPacket.getType() == ConstParam.TYPE_1){
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", "-1");
				json1.put("INFO", "There is no need to query unread messages");
				json0.put("DATA", json1);
				packetBody =json0.toString();
				//packetBody = "{'DATA':{'RET':'-1','INFO':'There is no need to query unread messages'}}"; //请求失败
			}
			break;
			
		case 2: // ret = 0
			if (revPacket.getType() == ConstParam.TYPE_2) {
				msg.setType(ConstParam.TYPE_2);
				json1.clear();
				json1.put("RET", num);
				if(code==32)
					json1.put("ID", id1);
				json0.put("DATA", json1);
				packetBody =json0.toString();
				//packetBody = "{'DATA':{'RET':'0','INFO':{"+info+"}}}"; 
			}
			break;
	}
		msg.setData(packetBody);
		return msg.createMessage(msg);
	}

	@Override
	void SendPkt(byte[] sendPacket) {
		// TODO Auto-generated method stub
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}
	

}
