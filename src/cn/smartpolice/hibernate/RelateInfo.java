package cn.smartpolice.hibernate;

import java.util.Date;

/**
 * 关联人信息relate_inf表映射
 *
 */
public class RelateInfo {

	private int relateId;//关联编号
	private int userId;//用户编号
	private int deviceId;//设备编号
	private Date setTime;//关联时间
	private String authority;//关联关系：0代表管理权限，1为操控权限，2为查看权限，3为接收权限
	private String alias;//别名
	private String state;//：0待审核，1通过，-1拒绝
	private String passWord;//密码
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getRelateId() {
		return relateId;
	}

	public void setRelateId(int relateId) {
		this.relateId = relateId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public RelateInfo() {
		super();
	}
	
	public boolean equals(Object arg0){//重写equals
		RelateInfo relateInf =(RelateInfo) arg0;
		return setTime.toString().equals(relateInf.setTime.toString());	
	}
	
	public int hashCode(){
		return setTime.hashCode();
	}
}
