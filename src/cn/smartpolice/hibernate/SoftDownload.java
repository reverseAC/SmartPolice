package cn.smartpolice.hibernate;

import java.sql.Date;

public class SoftDownload {

	private int sdId;
	private String serial;
	private int userId;
	private Date date;  //完成时间
	
	public int getSdId() {
		return sdId;
	}
	public void setSdId(int sdId) {
		this.sdId = sdId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}