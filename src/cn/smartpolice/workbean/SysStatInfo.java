package cn.smartpolice.workbean;

import java.util.Date;

/**
 * 系统统计信息
 * @author 刘超
 *
 */


public class SysStatInfo {
	
	private int receicvenum;//收到报文数
	private int sendnum;//发送报文数
	private int loginusernum;//登录用户数
	private int logindevnum;//登录设备数
	private int viewusernum;//界面用户数
	private int ordernum;//序号
	private int openFileNum;//打开文件数
	private Date serverStartDate;//系统启动时间
	private String username;
	private String ip;
	private String locahost;
	private String computername;
	private String os;
	private String osName;
	private String arch;
	private String osversion;
	private String cpuAll;
	private String cpuUserUsed;
	private String cpuSysUsed;
	private String cpuUsedAll;
	private String cpuType;
	private String memAll;
	private String memused;
	private String memFree;
	private String mem;
	

	public String getMemAll() {
		return memAll;
	}
	public void setMemAll(String memAll) {
		this.memAll = memAll;
	}
	public String getMemused() {
		return memused;
	}
	public void setMemused(String memused) {
		this.memused = memused;
	}
	public String getMemFree() {
		return memFree;
	}
	public void setMemFree(String memFree) {
		this.memFree = memFree;
	}
	public String getMem() {
		return mem;
	}
	public void setMem(String mem) {
		this.mem = mem;
	}
	public String getCpuAll() {
		return cpuAll;
	}
	public void setCpuAll(String cpuAll) {
		this.cpuAll = cpuAll;
	}
	public String getCpuUserUsed() {
		return cpuUserUsed;
	}
	public void setCpuUserUsed(String cpuUserUsed) {
		this.cpuUserUsed = cpuUserUsed;
	}
	public String getCpuSysUsed() {
		return cpuSysUsed;
	}
	public void setCpuSysUsed(String cpuSysUsed) {
		this.cpuSysUsed = cpuSysUsed;
	}
	public String getCpuUsedAll() {
		return cpuUsedAll;
	}
	public void setCpuUsedAll(String cpuUsedAll) {
		this.cpuUsedAll = cpuUsedAll;
	}

	public String getCpuType() {
		return cpuType;
	}
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocahost() {
		return locahost;
	}
	public void setLocahost(String locahost) {
		this.locahost = locahost;
	}
	public String getComputername() {
		return computername;
	}
	public void setComputername(String computername) {
		this.computername = computername;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getOsversion() {
		return osversion;
	}
	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}
	
	public int getReceicvenum() {
		return receicvenum;
	}
	public void setReceicvenum(int receicvenum) {
		this.receicvenum = receicvenum;
	}
	public int getSendnum() {
		return sendnum;
	}
	public void setSendnum(int sendnum) {
		this.sendnum = sendnum;
	}
	public int getLoginusernum() {
		return loginusernum;
	}
	public void setLoginusernum(int loginusernum) {
		this.loginusernum = loginusernum;
	}
	public int getLogindevnum() {
		return logindevnum;
	}
	public void setLogindevnum(int logindevnum) {
		this.logindevnum = logindevnum;
	}
	public int getViewusernum() {
		return viewusernum;
	}
	public void setViewusernum(int viewusernum) {
		this.viewusernum = viewusernum;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public int getOpenFileNum() {
		return openFileNum;
	}
	public void setOpenFileNum(int openFileNum) {
		this.openFileNum = openFileNum;
	}
	public Date getServerStartDate() {
		return serverStartDate;
	}
	public void setServerStartDate(Date serverStartDate) {
		this.serverStartDate = serverStartDate;
	}
}
	

