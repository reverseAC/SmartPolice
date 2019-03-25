package cn.smartpolice.hibernate;

import java.util.Date;

public class manage {
	private int Imanageid;
	private int managerid;
	private Date starttime;
	private Date endtime;
	private String ipadr;
	private String staff;
	private String state;
	public int getImanageid() {
		return Imanageid;
	}
	public void setImanageid(int imanageid) {
		Imanageid = imanageid;
	}
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getIpadr() {
		return ipadr;
	}
	public void setIpadr(String ipadr) {
		this.ipadr = ipadr;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "manage [Imanageid=" + Imanageid + ", managerid=" + managerid
				+ ", starttime=" + starttime + ", intendtime=" + endtime
				+ ", ipadr=" + ipadr + ", staff=" + staff + ", state=" + state
				+ "]";
	}
}
