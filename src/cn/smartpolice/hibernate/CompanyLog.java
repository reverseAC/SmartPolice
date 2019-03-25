package cn.smartpolice.hibernate;

import java.util.Date;

public class CompanyLog {
	
	
	private int logId;
	private Date date;
	private int companyid;
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
		
	
}
