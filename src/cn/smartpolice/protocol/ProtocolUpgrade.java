package cn.smartpolice.protocol;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smartpolice.hibernate.SoftInfo;
import cn.smartpolice.netdao.MessageDao;
import cn.smartpolice.netdao.SoftDao;
import cn.smartpolice.netdao.UserDao;
import cn.smartpolice.tools.JsonAnalysis;
import cn.smartpolice.workbean.PacketInfo;
import cn.smartpolice.workbean.SysInfo;
import cn.smartpolice.workbean.UserNode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 刘培煊 cmd=11 升级查询协议
 */

public class ProtocolUpgrade extends ProtocolBase {
//	@Resource(name = "softDao")
//	private SoftDao softDao;
	
	ApplicationContext ac = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
	SoftDao softDao = (SoftDao) ac.getBean("softDao");
	
	String version;
	int serial;// 0前端设备、1监控app、2管理app、3插件
	int n;// 有n个需要更新
	String info;// 需要更新的软件的信息
	int errorPktState;// 标记返回错误报文类型
	JSONObject json0 = new JSONObject();// json对象
	JSONObject json1 = new JSONObject();// json对象
	JSONObject json2 = new JSONObject();// json对象
	JSONArray array = new JSONArray();// json数组
	ArrayList<SoftInfo> SoftInfQueue = new ArrayList<SoftInfo>(); // 软件队列

	public void ParsePktProto(PacketInfo packetInfo) {
		System.out.println("upgrade报文解析");
		super.revPacket = packetInfo;
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = revPacket.getDatapos(); i < revPacket.getMessage().length; i++) {
			stringBuffer.append((char) revPacket.getMessage()[i]);
		}
		String datas = stringBuffer.toString();
		JsonAnalysis jsonAnalysis = new JsonAnalysis();
		String data = jsonAnalysis.getValue(datas, "DATA");
		serial = Integer.parseInt(jsonAnalysis.getValue(data, "SERIAL"));
		version = jsonAnalysis.getValue(data, "VERSION");

		this.ExecProto();
	}

	public void ExecProto() {
		// 找到节点
		UserNode userNode = null;
		// 通过报文sort判断是dev还是app
		if (revPacket.getSort() == ConstParam.SORT_2) {
			userNode = SysInfo.getInstance().getDevNodeById(revPacket.getSid());
		}
		if (revPacket.getSort() == ConstParam.SORT_0) {
			userNode = SysInfo.getInstance().getAppNodeById(revPacket.getSid());
		}

		// 查询应答
		if (revPacket.getType() == ConstParam.TYPE_1) {
			SoftDao sd = softDao;
			List<SoftInfo> list = sd.findSoft(serial);
			if (list.isEmpty()) { // 查询请求 如果没有这个软件类型 发送失败信息
				errorPktState = ConstParam.ERROR_PKT_STATE_1;
				byte[] errorPacket = PackPkt(ConstParam.ERROR_PKT_STATE_1);
				SendPkt(errorPacket);
				userNode.setLastPacketInfo(errorPacket);// 更新缓存
			} else {
				SoftInfQueue.clear();// 清空队列
				SoftInfQueue.addAll(list);
				list.clear();// 清空队列 下面作为临时队列使用
				int x = SoftInfQueue.size();
				DecimalFormat df = new DecimalFormat("0.0000");

				float m0 = Float.parseFloat(version);
				for (int p = 0; p < x; p++) {
					float n0 = Float.parseFloat(SoftInfQueue.get(p).getVersion());
					if (m0 < n0) {
						list.add(SoftInfQueue.get(p));// 比版本高的就加入临时队列
					}
				}

				SoftInfQueue.clear();// 清空队列
				SoftInfQueue.addAll(list);// 加入新版本
				n = SoftInfQueue.size();
				System.out.println(n);
				if (n > 0) {
					// 有n个需要更新
					// StringBuffer sb = new StringBuffer();

					for (int q = 0; q < n; q++) {
						// info="'NAME':'"+SysInfo.getSoftInfQueue().get(q).getName()+"','VERSION':'"+SysInfo.getSoftInfQueue().get(q).getVersion()+"','URL':'"+SysInfo.getSoftInfQueue().get(q).getUrl()+"',";
						// sb.append(info);
						json2.put("NAME", SoftInfQueue.get(q).getName());
						json2.put("VERSION", SoftInfQueue.get(q).getVersion());
						json2.put("URL", SoftInfQueue.get(q).getUrl());
						array.add(json2);
					}
					// SysInfo.getSoftInfQueue().clear();
					// info=sb.toString();
					// info=info.substring(0,
					// info.length()-1);//除去最后拼接json后的一个逗号

					byte[] Packet = PackPkt(3);
					SendPkt(Packet);
					userNode.setLastPacketInfo(Packet);// 更新缓存
				} else if (n == 0) {
					// 当前为最新版本
					byte[] errorPacket = PackPkt(ConstParam.ERROR_PKT_STATE_2);
					SendPkt(errorPacket);
					userNode.setLastPacketInfo(errorPacket);// 更新缓存
				}
			}
		}
	}

	byte[] PackPkt(int i) {
		PacketMsg msg = new PacketMsg();
		msg.setCmd(ConstParam.CMD_11);
		msg.setOpt(ConstParam.OPT_16);
		msg.setAck(revPacket.getSeq());// 返回报文的ack与发送过来的报文seq一致
		msg.setSid(ConstParam.SERVER_ID);
		String packetBody = null;

		switch (i) {
		case 1: // ret = -1
			// packetBody = "{'DATA':{'RET':'-1','INFO':'The request failed'}}";
			// //请求失败
			msg.setType(ConstParam.TYPE_2);
			json1.put("RET", "-1");
			json1.put("INFO", "The request failed");
			json0.put("DATA", json1);
			packetBody = json0.toString();
			break;

		case 2: // ret = 0
			// packetBody = "{'DATA':{'RET':'0','INFO':'Is the latest
			// version'}}"; //已经是最新版本
			msg.setType(ConstParam.TYPE_2);
			json1.put("RET", "0");
			json1.put("INFO", "Is the latest version");
			json0.put("DATA", json1);
			packetBody = json0.toString();
			break;

		case 3: // ret = 1
			// packetBody = "{'DATA':{'RET':"+n+",'INFO':{"+info+"}}}";
			json1.put("RET", n);
			json1.put("INFO", array);
			json0.put("DATA", json1);
			packetBody = json0.toString();
			msg.setType(ConstParam.TYPE_2);
			array.clear();
			break;
		}
		msg.setData(packetBody);

		return msg.createMessage(msg);
	}

	public void SendPkt(byte[] sendPacket) {
		// TODO Auto-generated method stub
		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));// 发送报文
	}

}
