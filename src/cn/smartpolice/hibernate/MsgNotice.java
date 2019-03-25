package cn.smartpolice.hibernate;

import java.util.Date;

public class MsgNotice {
		private int noticeid;
		private String title;
		private Date sendtime;
		private String msg;
		private int sendid;
		private int recvtype;//接受者类型 
		private String sets;//编码方式
		public int getNoticeid() {
			return noticeid;
		}
		public void setNoticeid(int noticeid) {
			this.noticeid = noticeid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Date getSendtime() {
			return sendtime;
		}
		public void setSendtime(Date sendtime) {
			this.sendtime = sendtime;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public int getSendid() {
			return sendid;
		}
		public void setSendid(int sendid) {
			this.sendid = sendid;
		}
		public int getRecvtype() {
			return recvtype;
		}
		public void setRecvtype(int recvtype) {
			this.recvtype = recvtype;
		}
		public String getSets() {
			return sets;
		}
		public void setSets(String sets) {
			this.sets = sets;
		}
}