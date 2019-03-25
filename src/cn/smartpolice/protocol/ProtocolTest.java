package cn.smartpolice.protocol;

import org.apache.mina.core.buffer.IoBuffer;

import cn.smartpolice.workbean.PacketInfo;


/**
 * 测试
 * 
 * @author 刘超
 *
 */
public class ProtocolTest extends ProtocolBase {

	PacketInfo revPacket;
	

	@Override
	public void ParsePktProto(PacketInfo packetInfo) {
		// TODO Auto-generated method stub
		this.revPacket = packetInfo;
		this.ExecProto();
	}

	@Override
	public void ExecProto() {
		
		// TODO Auto-generated method stub
		byte[] packet = PackPkt(1);
		SendPkt(packet);
	}

	@Override
	public byte[] PackPkt(int i) {
		// TODO Auto-generated method stub
		PacketMsg msg = new PacketMsg();
		msg.setCmd(0);
		msg.setType(2);
		msg.setOpt(16);
		msg.setAck(revPacket.getSeq());
		msg.setSid(1);
		return msg.createMessage(msg);
	}

	@Override
	public void SendPkt(byte[] sendPacket) {
		// TODO Auto-generated method stub

		revPacket.getIoSession().write(IoBuffer.wrap(sendPacket));
	}

}
