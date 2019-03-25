package cn.smartpolice.workbean;

import java.util.Date;

/**
 * 传输文件信息（链表队列）
 * @author 刘超
 *
 */
public class FileNodeInfo {
	private String fileName;
	private String fileOpenObj;
	private String MD5;
	private String fileSize;
	private boolean type;
	private Date reqDatel;
	private long fileTransObjId;
	private boolean fileTransObjType;
	private String time;
	private String url;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileOpenObj() {
		return fileOpenObj;
	}
	public void setFileOpenObj(String fileOpenObj) {
		this.fileOpenObj = fileOpenObj;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public  String getFileSize() {
		return fileSize;
	}
	public  void setFileSize( String fileSize) {
		this.fileSize = fileSize;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public Date getReqDatel() {
		return reqDatel;
	}
	public void setReqDatel(Date reqDatel) {
		this.reqDatel = reqDatel;
	}
	public long getFileTransObjId() {
		return fileTransObjId;
	}
	public void setFileTransObjId(long fileTransObjId) {
		this.fileTransObjId = fileTransObjId;
	}
	public boolean isFileTransObjType() {
		return fileTransObjType;
	}
	public void setFileTransObjType(boolean fileTransObjType) {
		this.fileTransObjType = fileTransObjType;
	}
	
}
