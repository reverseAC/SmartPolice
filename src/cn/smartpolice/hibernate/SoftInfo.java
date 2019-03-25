package cn.smartpolice.hibernate;

import java.util.Date;

public class SoftInfo {

	private int softid; // 软件编号
	private int type; // 0前端设备、1监控app、2管理app、3插件
	private String name; // 软件名称
	private String version; // 版本
	private String serial; // 软件序列号，唯一标识
	private Date date; // 上传时间
	private int uploadid; // 上传者
	private String md5; // Md5摘要信息
	private long size; // 软件大小
	private String url; // 软件存放地址
	private String state;
	
	public int getSoftid() {
		return softid;
	}
	public void setSoftid(int softid) {
		this.softid = softid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getUploadid() {
		return uploadid;
	}
	public void setUploadid(int uploadid) {
		this.uploadid = uploadid;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
