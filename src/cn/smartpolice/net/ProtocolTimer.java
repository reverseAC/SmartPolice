package cn.smartpolice.net;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import cn.smartpolice.hibernate.ContactWait;
import cn.smartpolice.hibernate.DeviceInfo;
import cn.smartpolice.hibernate.MsgAlarm;
import cn.smartpolice.hibernate.MsgChat;
import cn.smartpolice.hibernate.MsgNotice;
import cn.smartpolice.hibernate.MsgRecv;
import cn.smartpolice.hibernate.RelateWait;
import cn.smartpolice.netdao.DeviceDao;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.protocol.ConstParam;
import cn.smartpolice.protocol.PacketMsg;
import cn.smartpolice.workbean.AppNode;
import cn.smartpolice.workbean.DevNode;
import cn.smartpolice.workbean.FileNodeInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ProtocolTimer {
	@Resource(name="messageDao")
	private MessageDao messageDao;

	public void timerTasks(){
		int IntervalTime=10*1000;//间隔时间
		ScheduledExecutorService  executorService = Executors.newScheduledThreadPool(4);
		executorService.scheduleAtFixedRate( new Runnable(){
			@Override
		public void run() {
				int userNum = 0; //统计登录用户数
				int devNum = 0; //统计设备登录数
				Date CurrentTime = new Date();
				//检查dev队列中是否有节点超时
				for(UserNode userNode : SysInfo.getUserNodeQueue()){
					//最近接收报文时间和当前时间差3分钟
					if (userNode instanceof DevNode && userNode.getState() == ConstParam.LOGIN_STATE_2) {
						++devNum;
					}
					if (userNode instanceof AppNode && userNode.getState() == ConstParam.LOGIN_STATE_2) {
						++userNum;
					}
					long interval = (CurrentTime.getTime()-userNode.getRevPktDate().getTime())/1000;
					
					if(interval>1800){ 
						SysInfo.getInstance().removeUserNode(userNode); //删除节点
						System.out.println("节点超时被删除："+userNode.getAccount());
					}
				}
				SysInfo.getSysStatInfo().setLogindevnum(devNum);
				SysInfo.getSysStatInfo().setLoginusernum(userNum);
			}
		}, 0, IntervalTime,TimeUnit.MILLISECONDS );
		
		executorService.scheduleAtFixedRate( new Runnable(){
			@Override
			public void run() {

				Sigar sigar = new Sigar(); 
		        org.hyperic.sigar.CpuInfo[] infos;
				try {
					infos = sigar.getCpuInfoList();
				
		        CpuPerc cpuList[] = null; 
		        cpuList = sigar.getCpuPercList(); 
		        String cpuAll = null;
		        double cpuUserUsed = 0;
		        double cpuSysUsed = 0;
		        double cpuUsedAll = 0;
		        String cpuType = null;
		        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用 
		            org.hyperic.sigar.CpuInfo info = infos[i]; 
		            cpuAll =  info.getMhz()+"";
		            cpuType = info.getModel();
		            cpuUserUsed +=cpuList[i].getUser();
		            cpuSysUsed+=cpuList[i].getSys();
		            cpuUsedAll +=cpuList[i].getCombined();	            
		        }
		        Mem mem = sigar.getMem(); 
		       
		        long memt = mem.getTotal() / 1024L;
		        long memf = mem.getFree() / 1024L;
		        long memu = mem.getUsed() / 1024L;
		        double memp = (double)memu/(double)memt*100;
		        String mems = String.format("%.2f",memp);
		        SysInfo.getSysStatInfo().setMemAll(""+memt);
		        SysInfo.getSysStatInfo().setMemused(""+memu);
		        SysInfo.getSysStatInfo().setMemFree(""+memf);
		        SysInfo.getSysStatInfo().setMem(""+mems);
		        SysInfo.getSysStatInfo().setCpuAll(cpuAll);
		        SysInfo.getSysStatInfo().setCpuSysUsed(CpuPerc.format(cpuSysUsed/infos.length));
		        SysInfo.getSysStatInfo().setCpuType(cpuType);
		        SysInfo.getSysStatInfo().setCpuUserUsed( CpuPerc.format(cpuUserUsed/infos.length));
		        SysInfo.getSysStatInfo().setCpuUsedAll(CpuPerc.format(cpuUsedAll/infos.length));
				} catch (SigarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
				
	}, 0, 1000,TimeUnit.MILLISECONDS );

		
//		executorService.scheduleAtFixedRate( new Runnable(){
//			@Override
//			public void run() {
//				List<DeviceInfo> devinfo = new DeviceDao().findAllDevInf();
//				System.out.println("List长度"+devinfo.size());
//				for(DeviceInfo devinf:devinfo){
//					// ip和port需要是局部变量，得到应答的报文会变化（掉线的情况）
//					String ip = "127.0.1";
//					int port = 8899;
//					DevNode devNode = new DevNode(); // 创建节点
//					Random rando = new Random();
//					int random = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
//					devNode.setLink(random);
//					devNode.setIp(ip);
//					devNode.setPort(port);
//					devNode.setAccount(devinf.getUsername());
//					devNode.setId(devinf.getDeviceid());
//					devNode.setRevPktDate(new Date());
//
//					// 刚创建的节点的登录状态置为0，表示还未验证
//					devNode.setState(ConstParam.LOGIN_STATE_0);
//					// 将节点信息添加到报文中 仅仅为了在ProtocolLogin中取获取节点id (可优化) 下同
//
//					// 将节点添加到队列 在登录处理中从全局队列中取 下同
//					SysInfo.getInstance().addUserNode(devNode);
//					System.out.println("创建节点:");
//					System.out.println(devNode);
//				}
//			}
//		}, 0, 1000*1,TimeUnit.MILLISECONDS );
		
		executorService.scheduleAtFixedRate( new Runnable(){//消息通知定时器
			@Override
			public void run() {
				for (UserNode userNode : SysInfo.getUserNodeQueue()) {
					JSONObject data = new JSONObject(); //DATA域
					JSONArray infoArray = new JSONArray(); //INFO数组域
					JSONObject dataChild = new JSONObject(); //DATA子域
					JSONObject infoChild = new JSONObject(); //INFO子域
					JSONObject contentChild = new JSONObject(); //CONTENT子域
					MessageDao dao = messageDao;
					if (dao.SelectMessageOfUnReadMsg(userNode.getId(), 0) != null) {
						HashMap<String, List<MsgRecv>> msg = dao.SelectMessageOfUnReadMsg(userNode.getId(), 0);// 传0是查出该用户所有未读消息
						String sign = msg.keySet().iterator().next();
						MsgAlarm alarm = new MsgAlarm();
						MsgChat chat = new MsgChat();
						MsgNotice notice = new MsgNotice();
						List<MsgRecv> msgList = msg.get(sign);
						for (int j = 0; j < msgList.size(); j++) {
							if (msgList.get(j).getMsgtype().equals("1")) {
								// 查询报警消息
								contentChild.put("SORT", alarm.getSort());
								contentChild.put("LEVEL", alarm.getLevel());
								contentChild.put("ATTACH", alarm.getUrl());								
								infoChild.put("CONTENT", contentChild);		
								infoChild.put("USER", alarm.getDeviceid());
								infoChild.put("CLASS", 1);
								infoChild.put("DATE",alarm.getTime());
								infoArray.add(infoChild);
							}
							if (msgList.get(j).getMsgtype().equals("2")) {
								// 查询聊天消息\
								chat = dao.SelectMessageOfChat(msgList.get(j).getMessageid());
								contentChild.put("MSG", chat.getMsg());
								contentChild.put("TYPE", chat.getType());
								contentChild.put("COLOR", chat.getColor());								
								infoChild.put("CONTENT", contentChild);		
								infoChild.put("USER", chat.getSendid());
								infoChild.put("CLASS", 2);
								infoChild.put("DATE", chat.getSendtime());
								infoArray.add(infoChild);
							}
							if (msgList.get(j).getMsgtype().equals("4")) {
								// 查询通知消息
								notice = dao.SelectMessageOfNotice(msgList.get(j).getMessageid());
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
						dataChild.put("INFO", infoArray);
						dataChild.put("NUM", infoArray.size());	
						dataChild.put("SIGN",sign);
						data.put("DATA", dataChild);
						
						PacketMsg packet = new PacketMsg();
						packet.setSid(ConstParam.SERVER_ID);
						packet.setCmd(ConstParam.CMD_5);
						packet.setType(ConstParam.TYPE_4);
						packet.setData(data.toString());
						int seq;
						while(true){
							Random rando = new Random();
							seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
							if(seq != userNode.getSntPktId()){
								userNode.setSntPktId(seq);
								break;
							}
						}		
						packet.setSeq(seq);
						packet.setAck(0);
						packet.setOpt(ConstParam.OPT_8);
						byte[] packets = packet.createMessage(packet);
						data.clear();
						infoArray.clear();
						dataChild.clear();
						infoChild.clear();
						contentChild.clear();
						userNode.getIoSession().write(IoBuffer.wrap(packets));
					}
					if (dao.SelectMessageOfContactWaitcontacted(userNode.getId()) != null) {

						ContactWait contactWait = new ContactWait();
						HashMap<String, List<ContactWait>> contacted = dao
								.SelectMessageOfContactWaitcontacted(userNode.getId());
						String sign = contacted.keySet().iterator().next();
						List<ContactWait> contactWaitList = contacted.get(sign);
						for (int i = 0; i < contactWaitList.size(); i++) {
							contactWait = contactWaitList.get(i);						
							contentChild.put("MSG", contactWait.getMessage());
							contentChild.put("KIND", contactWait.getKind());							
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", contactWait.getMasterId());
							infoChild.put("CLASS", 8);
							infoChild.put("DATE", contactWait.getApplyTime().getTime());							
							infoArray.add(infoChild);					
						}
						dataChild.put("INFO", infoArray);
						dataChild.put("NUM", infoArray.size());		
						dataChild.put("SIGN", sign);
						data.put("DATA", dataChild);

						PacketMsg packet = new PacketMsg();
						packet.setSid(ConstParam.SERVER_ID);
						packet.setCmd(ConstParam.CMD_5);
						packet.setType(ConstParam.TYPE_4);
						packet.setData(data.toString());
						int seq;
						while(true){
							Random rando = new Random();
							seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
							if(seq != userNode.getSntPktId()){
								userNode.setSntPktId(seq);
								break;
							}
						}		
						packet.setSeq(seq);
						packet.setAck(0);
						packet.setOpt(ConstParam.OPT_8);
						byte[] packets = packet.createMessage(packet);
						data.clear();
						infoArray.clear();
						dataChild.clear();
						infoChild.clear();
						contentChild.clear();
						userNode.getIoSession().write(IoBuffer.wrap(packets));
					}
					if (dao.SelectMessageOfContactWaitMaster(userNode.getId()) != null) {

						ContactWait contactWait = new ContactWait();
						HashMap<String, List<ContactWait>> master = dao
								.SelectMessageOfContactWaitMaster(userNode.getId());
						String sign = master.keySet().iterator().next();
						List<ContactWait> contactWaitList = master.get(sign);
						for (int i = 0; i < contactWaitList.size(); i++) {
							contactWait = contactWaitList.get(i);
							contactWait = contactWaitList.get(i);						
							contentChild.put("MSG", contactWait.getMessage());
							contentChild.put("KIND", contactWait.getKind());							
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", contactWait.getMasterId());
							infoChild.put("CLASS", 8);
							infoChild.put("DATE", contactWait.getApplyTime().getTime());							
							infoArray.add(infoChild);	

						}
						dataChild.put("INFO", infoArray);
						dataChild.put("NUM", infoArray.size());		
						dataChild.put("SIGN", sign);
						data.put("DATA", dataChild);
						PacketMsg packet = new PacketMsg();
						packet.setSid(ConstParam.SERVER_ID);
						packet.setCmd(ConstParam.CMD_5);
						packet.setType(ConstParam.TYPE_4);
						packet.setData(data.toString());
						int seq;
						while(true){
							Random rando = new Random();
							seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
							if(seq != userNode.getSntPktId()){
								userNode.setSntPktId(seq);
								break;
							}
						}		
						packet.setSeq(seq);
						packet.setAck(0);
						packet.setOpt(ConstParam.OPT_8);
						byte[] packets = packet.createMessage(packet);
						data.clear();
						infoArray.clear();
						dataChild.clear();
						infoChild.clear();
						contentChild.clear();
						userNode.getIoSession().write(IoBuffer.wrap(packets));
					}
					if (dao.SelectMessageOfApply(userNode.getId()) != null) {
						RelateWait relateWait = new RelateWait();
						HashMap<String, List<RelateWait>> apply = dao.SelectMessageOfApply(userNode.getId());
						String sign = apply.keySet().iterator().next();
						List<RelateWait> relateWaitList = apply.get(sign);
						for (int i = 0; i < relateWaitList.size(); i++) {
							relateWait = relateWaitList.get(i);
							contentChild.put("MSG", relateWait.getMessage());
							contentChild.put("KIND", relateWait.getKind());
							contentChild.put("PUSER", relateWait.getDeviceId());
							contentChild.put("DATE", relateWait.getAckTime().getTime());
							contentChild.put("RIGHT", relateWait.getApplyRight());							
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", relateWait.getApplyId());
							infoChild.put("CLASS", 16);
							infoChild.put("DATE", relateWait.getAckTime().getTime());							
							infoArray.add(infoChild);			
						}
						dataChild.put("INFO", infoArray);
						dataChild.put("NUM", infoArray.size());		
						dataChild.put("SIGN", sign);
						data.put("DATA", dataChild);						
						PacketMsg packet = new PacketMsg();
						packet.setSid(ConstParam.SERVER_ID);
						packet.setCmd(ConstParam.CMD_5);
						packet.setType(ConstParam.TYPE_4);
						packet.setData(data.toString());
						int seq;
						while(true){
							Random rando = new Random();
							seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
							if(seq != userNode.getSntPktId()){
								userNode.setSntPktId(seq);
								break;
							}
						}		
						packet.setSeq(seq);
						packet.setAck(0);
						packet.setOpt(ConstParam.OPT_8);
						byte[] packets = packet.createMessage(packet);
						userNode.getIoSession().write(IoBuffer.wrap(packets));
					}
					if (dao.SelectMessageOfShare(userNode.getId()) != null) {
						RelateWait relateWait = new RelateWait();
						HashMap<String,List<RelateWait>> share = dao.SelectMessageOfShare(userNode.getId());
						String sign = share.keySet().iterator().next();
						List<RelateWait> contactWaitList = share.get(sign);
						for (int i = 0; i < contactWaitList.size(); i++) {
							relateWait = contactWaitList.get(i);
							contentChild.put("MSG", relateWait.getMessage());
							contentChild.put("KIND", relateWait.getKind());
							contentChild.put("PUSER", relateWait.getDeviceId());
							contentChild.put("DATE", relateWait.getAckTime().getTime());
							contentChild.put("RIGHT", relateWait.getApplyRight());							
							infoChild.put("CONTENT", contentChild);		
							infoChild.put("USER", relateWait.getApplyId());
							infoChild.put("CLASS", 16);
							infoChild.put("DATE", relateWait.getAckTime().getTime());
							infoArray.add(infoChild);
						}
						dataChild.put("INFO", infoArray);
						dataChild.put("NUM", infoArray.size());		
						dataChild.put("SIGN", sign);
						data.put("DATA", dataChild);	
						PacketMsg packet = new PacketMsg();
						packet.setSid(ConstParam.SERVER_ID);
						packet.setCmd(ConstParam.CMD_5);
						packet.setType(ConstParam.TYPE_4);
						packet.setData(data.toString());
						int seq;
						while(true){
							Random rando = new Random();
							seq = rando.nextInt(999999) % 900000 + 100000; // 产生一个6位随机数
							if(seq != userNode.getSntPktId()){
								userNode.setSntPktId(seq);
								break;
							}
						}		
						packet.setSeq(seq);
						packet.setAck(0);
						packet.setOpt(ConstParam.OPT_8);
						byte[] packets = packet.createMessage(packet);
						userNode.getIoSession().write(IoBuffer.wrap(packets));
					}
				}
			}
		}, 0, IntervalTime,TimeUnit.MILLISECONDS );
		
	}
}
