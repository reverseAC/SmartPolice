package cn.smartpolice.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cn.smartpolice.tools.JsonAnalysis;
/**
 * 
 * @author maxiaolin
 * 发送报文封装类
 *
 */
public class PacketMsg {
	private int cmd;
	private int type;
	private int opt;
	private int sid;
	private int seq;
	private int ack;	
	private String data;
	

	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOpt() {
		return opt;
	}
	public void setOpt(int opt) {
		this.opt = opt;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getAck() {
		return ack;
	}
	public void setAck(int ack) {
		this.ack = ack;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}


	public byte[] createMessage(PacketMsg msg) {
		ByteArrayOutputStream ips  = new ByteArrayOutputStream();
		DataOutputStream dis = new DataOutputStream(ips);
		byte[] packets = null;
		byte[] byte1;		
		String packetBodyJson = null;
			try {
			byte1 = "ZNAF".getBytes("UTF-8");			
			dis.write(byte1);
			dis.write((byte) msg.cmd);
			dis.write((byte) msg.type);
			dis.write((byte) msg.opt);
			dis.write((byte) ConstParam.SORT_3);
			dis.writeInt(ConstParam.SERVER_ID);
			dis.writeInt(msg.getSeq());
			dis.writeInt(msg.getAck());
			if(msg.data != null){
				packetBodyJson = new JsonAnalysis().getJsonByObject(msg.data);
				byte[] packetBodyJsonByte = null;
				if( String.valueOf(ProtocolEncryption.keyseq)!=null){//判断其data是否需要加密
				//判断其dec算法的key长度是否符合大于8个字节
				if(!(ProtocolEncryption.encry==2&&ProtocolEncryption.password.getBytes().length<8))
				packetBodyJson=ProtocolEncryption.EncrySend(packetBodyJson);				
				packetBodyJsonByte = packetBodyJson.getBytes("UTF-8");				
				dis.write(packetBodyJsonByte);
				}
			}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		packets = ips.toByteArray();
		System.out.println(this.toString());
		return packets;
	}
	@Override
	public String toString() {
		return "发送的PacketMsg： [cmd=" + cmd + ", type=" + type + ", opt=" + opt + ", sid=" + sid + ", seq=" + seq + ", ack="
				+ ack + ", data=" + data + "]";
	}
}
	
	


