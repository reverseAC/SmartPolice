package cn.smartpolice.workbean;

import java.util.Date;

/**
 * 消息队列节点对象
 * @author 刘超
 *
 */
public class MsgTask {

	private int msgNum; //消息个数
	private int sendUserID; //产生消息者账号
	
	private int mType;  //class消息类型
	private long mDate; //毫秒
	private String content; //消息内容
	private int revUserID; //接受消息者ID

	@Override
	public String toString() {
		return "MsgTask [msgNum=" + msgNum + ", sendUserID=" + sendUserID + ", mType=" + mType + ", mDate=" + mDate
				+ ", content=" + content + ", revUserID=" + revUserID + "]";
	}
	public int getmType() {
		return mType;
	}
	public void setmType(int mType) {
		this.mType = mType;
	}

	public long getmDate() {
		return mDate;
	}
	public void setmDate(long mDate) {
		this.mDate = mDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	//private PacketInfo packetInfo; //未读消息报文
	public int getMsgNum() {
		return msgNum;
	}
	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}
	public int getSendUserID() {
		return sendUserID;
	}
	public void setSendUserID(int sendUserID) {
		this.sendUserID = sendUserID;
	}
	public int getRevUserID() {
		return revUserID;
	}
	public void setRevUserID(int revUserID) {
		this.revUserID = revUserID;
	}
//	public PacketInfo getPacketInfo() {
//		return packetInfo;
//	}
//	public void setPacketInfo(PacketInfo packetInfo) {
//		this.packetInfo = packetInfo;
//	}
}
