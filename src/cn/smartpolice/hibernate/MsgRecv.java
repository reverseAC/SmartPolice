package cn.smartpolice.hibernate;



import java.util.Date;

/**
 * 消息接收记录表   
 * @author 刘超
 *
 */
public class MsgRecv {

	private int recvid;
	private int messageid;//消息编号
	private int senduserid;//无用
	private int recvuserid;
	private String msgtype;
	private int state; //0为未读    1为已读
	private Date recvtime;
	private int recvtype; //接受者类型 0厂商1用户2管理员
	public int getRecvtype() {
		return recvtype;
	}
	public void setRecvtype(int recvtype) {
		this.recvtype = recvtype;
	}
	public int getRecvid() {
		return recvid;
	}
	public void setRecvid(int recvid) {
		this.recvid = recvid;
	}
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public int getSenduserid() {
		return senduserid;
	}
	public void setSenduserid(int senduserid) {
		this.senduserid = senduserid;
	}
	public int getRecvuserid() {
		return recvuserid;
	}
	public void setRecvuserid(int recvuserid) {
		this.recvuserid = recvuserid;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getRecvtime() {
		return recvtime;
	}
	public void setRecvtime(Date recvtime) {
		this.recvtime = recvtime;
	}


	
}
